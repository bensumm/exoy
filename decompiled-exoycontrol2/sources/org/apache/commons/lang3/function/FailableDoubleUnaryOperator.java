package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableDoubleUnaryOperator;

/* JADX INFO: loaded from: classes2.dex */
public interface FailableDoubleUnaryOperator<E extends Throwable> {
    public static final FailableDoubleUnaryOperator NOP = new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
        public /* synthetic */ FailableDoubleUnaryOperator andThen(FailableDoubleUnaryOperator failableDoubleUnaryOperator) {
            return FailableDoubleUnaryOperator.CC.$default$andThen(this, failableDoubleUnaryOperator);
        }

        @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
        public final double applyAsDouble(double d) {
            return FailableDoubleUnaryOperator.CC.lambda$static$0(d);
        }

        @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
        public /* synthetic */ FailableDoubleUnaryOperator compose(FailableDoubleUnaryOperator failableDoubleUnaryOperator) {
            return FailableDoubleUnaryOperator.CC.$default$compose(this, failableDoubleUnaryOperator);
        }
    };

    FailableDoubleUnaryOperator<E> andThen(FailableDoubleUnaryOperator<E> failableDoubleUnaryOperator);

    double applyAsDouble(double d) throws Throwable;

    FailableDoubleUnaryOperator<E> compose(FailableDoubleUnaryOperator<E> failableDoubleUnaryOperator);

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableDoubleUnaryOperator failableDoubleUnaryOperator = FailableDoubleUnaryOperator.NOP;
        }

        public static /* synthetic */ double lambda$identity$1(double d) throws Throwable {
            return d;
        }

        public static /* synthetic */ double lambda$static$0(double d) throws Throwable {
            return 0.0d;
        }

        public static <E extends Throwable> FailableDoubleUnaryOperator<E> identity() {
            return new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda1
                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public /* synthetic */ FailableDoubleUnaryOperator andThen(FailableDoubleUnaryOperator failableDoubleUnaryOperator) {
                    return FailableDoubleUnaryOperator.CC.$default$andThen(this, failableDoubleUnaryOperator);
                }

                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    return FailableDoubleUnaryOperator.CC.lambda$identity$1(d);
                }

                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public /* synthetic */ FailableDoubleUnaryOperator compose(FailableDoubleUnaryOperator failableDoubleUnaryOperator) {
                    return FailableDoubleUnaryOperator.CC.$default$compose(this, failableDoubleUnaryOperator);
                }
            };
        }

        public static <E extends Throwable> FailableDoubleUnaryOperator<E> nop() {
            return FailableDoubleUnaryOperator.NOP;
        }

        public static FailableDoubleUnaryOperator $default$andThen(final FailableDoubleUnaryOperator _this, final FailableDoubleUnaryOperator failableDoubleUnaryOperator) {
            Objects.requireNonNull(failableDoubleUnaryOperator);
            return new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda2
                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public /* synthetic */ FailableDoubleUnaryOperator andThen(FailableDoubleUnaryOperator failableDoubleUnaryOperator2) {
                    return FailableDoubleUnaryOperator.CC.$default$andThen(this, failableDoubleUnaryOperator2);
                }

                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    return failableDoubleUnaryOperator.applyAsDouble(_this.applyAsDouble(d));
                }

                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public /* synthetic */ FailableDoubleUnaryOperator compose(FailableDoubleUnaryOperator failableDoubleUnaryOperator2) {
                    return FailableDoubleUnaryOperator.CC.$default$compose(this, failableDoubleUnaryOperator2);
                }
            };
        }

        public static FailableDoubleUnaryOperator $default$compose(final FailableDoubleUnaryOperator _this, final FailableDoubleUnaryOperator failableDoubleUnaryOperator) {
            Objects.requireNonNull(failableDoubleUnaryOperator);
            return new FailableDoubleUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableDoubleUnaryOperator$$ExternalSyntheticLambda3
                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public /* synthetic */ FailableDoubleUnaryOperator andThen(FailableDoubleUnaryOperator failableDoubleUnaryOperator2) {
                    return FailableDoubleUnaryOperator.CC.$default$andThen(this, failableDoubleUnaryOperator2);
                }

                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    return _this.applyAsDouble(failableDoubleUnaryOperator.applyAsDouble(d));
                }

                @Override // org.apache.commons.lang3.function.FailableDoubleUnaryOperator
                public /* synthetic */ FailableDoubleUnaryOperator compose(FailableDoubleUnaryOperator failableDoubleUnaryOperator2) {
                    return FailableDoubleUnaryOperator.CC.$default$compose(this, failableDoubleUnaryOperator2);
                }
            };
        }
    }
}
