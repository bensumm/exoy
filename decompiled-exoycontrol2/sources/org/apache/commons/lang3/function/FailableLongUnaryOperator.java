package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableLongUnaryOperator;

/* JADX INFO: loaded from: classes2.dex */
public interface FailableLongUnaryOperator<E extends Throwable> {
    public static final FailableLongUnaryOperator NOP = new FailableLongUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableLongUnaryOperator$$ExternalSyntheticLambda3
        @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
        public /* synthetic */ FailableLongUnaryOperator andThen(FailableLongUnaryOperator failableLongUnaryOperator) {
            return FailableLongUnaryOperator.CC.$default$andThen(this, failableLongUnaryOperator);
        }

        @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
        public final long applyAsLong(long j) {
            return FailableLongUnaryOperator.CC.lambda$static$0(j);
        }

        @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
        public /* synthetic */ FailableLongUnaryOperator compose(FailableLongUnaryOperator failableLongUnaryOperator) {
            return FailableLongUnaryOperator.CC.$default$compose(this, failableLongUnaryOperator);
        }
    };

    FailableLongUnaryOperator<E> andThen(FailableLongUnaryOperator<E> failableLongUnaryOperator);

    long applyAsLong(long j) throws Throwable;

    FailableLongUnaryOperator<E> compose(FailableLongUnaryOperator<E> failableLongUnaryOperator);

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableLongUnaryOperator$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableLongUnaryOperator failableLongUnaryOperator = FailableLongUnaryOperator.NOP;
        }

        public static /* synthetic */ long lambda$identity$1(long j) throws Throwable {
            return j;
        }

        public static /* synthetic */ long lambda$static$0(long j) throws Throwable {
            return 0L;
        }

        public static <E extends Throwable> FailableLongUnaryOperator<E> identity() {
            return new FailableLongUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableLongUnaryOperator$$ExternalSyntheticLambda2
                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public /* synthetic */ FailableLongUnaryOperator andThen(FailableLongUnaryOperator failableLongUnaryOperator) {
                    return FailableLongUnaryOperator.CC.$default$andThen(this, failableLongUnaryOperator);
                }

                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public final long applyAsLong(long j) {
                    return FailableLongUnaryOperator.CC.lambda$identity$1(j);
                }

                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public /* synthetic */ FailableLongUnaryOperator compose(FailableLongUnaryOperator failableLongUnaryOperator) {
                    return FailableLongUnaryOperator.CC.$default$compose(this, failableLongUnaryOperator);
                }
            };
        }

        public static <E extends Throwable> FailableLongUnaryOperator<E> nop() {
            return FailableLongUnaryOperator.NOP;
        }

        public static FailableLongUnaryOperator $default$andThen(final FailableLongUnaryOperator _this, final FailableLongUnaryOperator failableLongUnaryOperator) {
            Objects.requireNonNull(failableLongUnaryOperator);
            return new FailableLongUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableLongUnaryOperator$$ExternalSyntheticLambda0
                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public /* synthetic */ FailableLongUnaryOperator andThen(FailableLongUnaryOperator failableLongUnaryOperator2) {
                    return FailableLongUnaryOperator.CC.$default$andThen(this, failableLongUnaryOperator2);
                }

                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public final long applyAsLong(long j) {
                    return failableLongUnaryOperator.applyAsLong(_this.applyAsLong(j));
                }

                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public /* synthetic */ FailableLongUnaryOperator compose(FailableLongUnaryOperator failableLongUnaryOperator2) {
                    return FailableLongUnaryOperator.CC.$default$compose(this, failableLongUnaryOperator2);
                }
            };
        }

        public static FailableLongUnaryOperator $default$compose(final FailableLongUnaryOperator _this, final FailableLongUnaryOperator failableLongUnaryOperator) {
            Objects.requireNonNull(failableLongUnaryOperator);
            return new FailableLongUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableLongUnaryOperator$$ExternalSyntheticLambda1
                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public /* synthetic */ FailableLongUnaryOperator andThen(FailableLongUnaryOperator failableLongUnaryOperator2) {
                    return FailableLongUnaryOperator.CC.$default$andThen(this, failableLongUnaryOperator2);
                }

                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public final long applyAsLong(long j) {
                    return _this.applyAsLong(failableLongUnaryOperator.applyAsLong(j));
                }

                @Override // org.apache.commons.lang3.function.FailableLongUnaryOperator
                public /* synthetic */ FailableLongUnaryOperator compose(FailableLongUnaryOperator failableLongUnaryOperator2) {
                    return FailableLongUnaryOperator.CC.$default$compose(this, failableLongUnaryOperator2);
                }
            };
        }
    }
}
