package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: classes.dex */
class SpringAnimation extends AnimationDriver {
    private static final double MAX_DELTA_TIME_SEC = 0.064d;
    private static final double SOLVER_TIMESTEP_SEC = 0.001d;
    private int mCurrentLoop;
    private final PhysicsState mCurrentState;
    private double mDisplacementFromRestThreshold;
    private double mEndValue;
    private double mInitialVelocity;
    private int mIterations;
    private long mLastTime;
    private double mOriginalValue;
    private boolean mOvershootClampingEnabled;
    private double mRestSpeedThreshold;
    private double mSpringDamping;
    private double mSpringMass;
    private boolean mSpringStarted;
    private double mSpringStiffness;
    private double mStartValue;
    private double mTimeAccumulator;

    private static class PhysicsState {
        double position;
        double velocity;

        private PhysicsState() {
        }
    }

    SpringAnimation(ReadableMap readableMap) {
        PhysicsState physicsState = new PhysicsState();
        this.mCurrentState = physicsState;
        physicsState.velocity = readableMap.getDouble("initialVelocity");
        resetConfig(readableMap);
    }

    @Override // com.facebook.react.animated.AnimationDriver
    public void resetConfig(ReadableMap readableMap) {
        this.mSpringStiffness = readableMap.getDouble("stiffness");
        this.mSpringDamping = readableMap.getDouble("damping");
        this.mSpringMass = readableMap.getDouble("mass");
        this.mInitialVelocity = this.mCurrentState.velocity;
        this.mEndValue = readableMap.getDouble("toValue");
        this.mRestSpeedThreshold = readableMap.getDouble("restSpeedThreshold");
        this.mDisplacementFromRestThreshold = readableMap.getDouble("restDisplacementThreshold");
        this.mOvershootClampingEnabled = readableMap.getBoolean("overshootClamping");
        int i = readableMap.hasKey("iterations") ? readableMap.getInt("iterations") : 1;
        this.mIterations = i;
        this.mHasFinished = i == 0;
        this.mCurrentLoop = 0;
        this.mTimeAccumulator = 0.0d;
        this.mSpringStarted = false;
    }

    @Override // com.facebook.react.animated.AnimationDriver
    public void runAnimationStep(long j) {
        long j2 = j / 1000000;
        if (!this.mSpringStarted) {
            if (this.mCurrentLoop == 0) {
                this.mOriginalValue = this.mAnimatedValue.mValue;
                this.mCurrentLoop = 1;
            }
            PhysicsState physicsState = this.mCurrentState;
            double d = this.mAnimatedValue.mValue;
            physicsState.position = d;
            this.mStartValue = d;
            this.mLastTime = j2;
            this.mTimeAccumulator = 0.0d;
            this.mSpringStarted = true;
        }
        advance((j2 - this.mLastTime) / 1000.0d);
        this.mLastTime = j2;
        this.mAnimatedValue.mValue = this.mCurrentState.position;
        if (isAtRest()) {
            int i = this.mIterations;
            if (i == -1 || this.mCurrentLoop < i) {
                this.mSpringStarted = false;
                this.mAnimatedValue.mValue = this.mOriginalValue;
                this.mCurrentLoop++;
                return;
            }
            this.mHasFinished = true;
        }
    }

    private double getDisplacementDistanceForState(PhysicsState physicsState) {
        return Math.abs(this.mEndValue - physicsState.position);
    }

    private boolean isAtRest() {
        return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringStiffness == 0.0d);
    }

    private boolean isOvershooting() {
        return this.mSpringStiffness > 0.0d && ((this.mStartValue < this.mEndValue && this.mCurrentState.position > this.mEndValue) || (this.mStartValue > this.mEndValue && this.mCurrentState.position < this.mEndValue));
    }

    private void advance(double d) {
        double dSin;
        double dSin2;
        if (isAtRest()) {
            return;
        }
        double d2 = MAX_DELTA_TIME_SEC;
        if (d <= MAX_DELTA_TIME_SEC) {
            d2 = d;
        }
        this.mTimeAccumulator += d2;
        double d3 = this.mSpringDamping;
        double d4 = this.mSpringMass;
        double d5 = this.mSpringStiffness;
        double d6 = -this.mInitialVelocity;
        double dSqrt = d3 / (Math.sqrt(d5 * d4) * 2.0d);
        double dSqrt2 = Math.sqrt(d5 / d4);
        double dSqrt3 = Math.sqrt(1.0d - (dSqrt * dSqrt)) * dSqrt2;
        double d7 = this.mEndValue - this.mStartValue;
        double d8 = this.mTimeAccumulator;
        if (dSqrt < 1.0d) {
            double dExp = Math.exp((-dSqrt) * dSqrt2 * d8);
            double d9 = dSqrt * dSqrt2;
            double d10 = d6 + (d9 * d7);
            double d11 = d8 * dSqrt3;
            dSin2 = this.mEndValue - ((((d10 / dSqrt3) * Math.sin(d11)) + (Math.cos(d11) * d7)) * dExp);
            dSin = ((d9 * dExp) * (((Math.sin(d11) * d10) / dSqrt3) + (Math.cos(d11) * d7))) - (((Math.cos(d11) * d10) - ((dSqrt3 * d7) * Math.sin(d11))) * dExp);
        } else {
            double dExp2 = Math.exp((-dSqrt2) * d8);
            double d12 = this.mEndValue - (((((dSqrt2 * d7) + d6) * d8) + d7) * dExp2);
            dSin = dExp2 * ((d6 * ((d8 * dSqrt2) - 1.0d)) + (d8 * d7 * dSqrt2 * dSqrt2));
            dSin2 = d12;
        }
        this.mCurrentState.position = dSin2;
        this.mCurrentState.velocity = dSin;
        if (isAtRest() || (this.mOvershootClampingEnabled && isOvershooting())) {
            if (this.mSpringStiffness > 0.0d) {
                double d13 = this.mEndValue;
                this.mStartValue = d13;
                this.mCurrentState.position = d13;
            } else {
                double d14 = this.mCurrentState.position;
                this.mEndValue = d14;
                this.mStartValue = d14;
            }
            this.mCurrentState.velocity = 0.0d;
        }
    }
}
