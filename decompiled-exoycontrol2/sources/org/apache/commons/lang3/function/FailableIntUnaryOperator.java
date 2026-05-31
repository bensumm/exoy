package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableIntUnaryOperator;

/* JADX INFO: loaded from: classes2.dex */
public interface FailableIntUnaryOperator<E extends Throwable> {
    public static final FailableIntUnaryOperator NOP = new FailableIntUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableIntUnaryOperator$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
        public /* synthetic */ FailableIntUnaryOperator andThen(FailableIntUnaryOperator failableIntUnaryOperator) {
            return FailableIntUnaryOperator.CC.$default$andThen(this, failableIntUnaryOperator);
        }

        @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
        public final int applyAsInt(int i) {
            return FailableIntUnaryOperator.CC.lambda$static$0(i);
        }

        @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
        public /* synthetic */ FailableIntUnaryOperator compose(FailableIntUnaryOperator failableIntUnaryOperator) {
            return FailableIntUnaryOperator.CC.$default$compose(this, failableIntUnaryOperator);
        }
    };

    FailableIntUnaryOperator<E> andThen(FailableIntUnaryOperator<E> failableIntUnaryOperator);

    int applyAsInt(int i) throws Throwable;

    FailableIntUnaryOperator<E> compose(FailableIntUnaryOperator<E> failableIntUnaryOperator);

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableIntUnaryOperator$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableIntUnaryOperator failableIntUnaryOperator = FailableIntUnaryOperator.NOP;
        }

        public static /* synthetic */ int lambda$identity$1(int i) throws Throwable {
            return i;
        }

        public static /* synthetic */ int lambda$static$0(int i) throws Throwable {
            return 0;
        }

        public static <E extends Throwable> FailableIntUnaryOperator<E> identity() {
            return new FailableIntUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableIntUnaryOperator$$ExternalSyntheticLambda1
                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public /* synthetic */ FailableIntUnaryOperator andThen(FailableIntUnaryOperator failableIntUnaryOperator) {
                    return FailableIntUnaryOperator.CC.$default$andThen(this, failableIntUnaryOperator);
                }

                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public final int applyAsInt(int i) {
                    return FailableIntUnaryOperator.CC.lambda$identity$1(i);
                }

                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public /* synthetic */ FailableIntUnaryOperator compose(FailableIntUnaryOperator failableIntUnaryOperator) {
                    return FailableIntUnaryOperator.CC.$default$compose(this, failableIntUnaryOperator);
                }
            };
        }

        public static <E extends Throwable> FailableIntUnaryOperator<E> nop() {
            return FailableIntUnaryOperator.NOP;
        }

        public static FailableIntUnaryOperator $default$andThen(final FailableIntUnaryOperator _this, final FailableIntUnaryOperator failableIntUnaryOperator) {
            Objects.requireNonNull(failableIntUnaryOperator);
            return new FailableIntUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableIntUnaryOperator$$ExternalSyntheticLambda3
                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public /* synthetic */ FailableIntUnaryOperator andThen(FailableIntUnaryOperator failableIntUnaryOperator2) {
                    return FailableIntUnaryOperator.CC.$default$andThen(this, failableIntUnaryOperator2);
                }

                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public final int applyAsInt(int i) {
                    return failableIntUnaryOperator.applyAsInt(_this.applyAsInt(i));
                }

                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public /* synthetic */ FailableIntUnaryOperator compose(FailableIntUnaryOperator failableIntUnaryOperator2) {
                    return FailableIntUnaryOperator.CC.$default$compose(this, failableIntUnaryOperator2);
                }
            };
        }

        public static FailableIntUnaryOperator $default$compose(final FailableIntUnaryOperator _this, final FailableIntUnaryOperator failableIntUnaryOperator) {
            Objects.requireNonNull(failableIntUnaryOperator);
            return new FailableIntUnaryOperator() { // from class: org.apache.commons.lang3.function.FailableIntUnaryOperator$$ExternalSyntheticLambda2
                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public /* synthetic */ FailableIntUnaryOperator andThen(FailableIntUnaryOperator failableIntUnaryOperator2) {
                    return FailableIntUnaryOperator.CC.$default$andThen(this, failableIntUnaryOperator2);
                }

                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public final int applyAsInt(int i) {
                    return _this.applyAsInt(failableIntUnaryOperator.applyAsInt(i));
                }

                @Override // org.apache.commons.lang3.function.FailableIntUnaryOperator
                public /* synthetic */ FailableIntUnaryOperator compose(FailableIntUnaryOperator failableIntUnaryOperator2) {
                    return FailableIntUnaryOperator.CC.$default$compose(this, failableIntUnaryOperator2);
                }
            };
        }
    }
}
