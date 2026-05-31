package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableFunction<T, R, E extends Throwable> {
    public static final FailableFunction NOP = new FailableFunction() { // from class: org.apache.commons.lang3.function.FailableFunction$$ExternalSyntheticLambda2
        @Override // org.apache.commons.lang3.function.FailableFunction
        public /* synthetic */ FailableFunction andThen(FailableFunction failableFunction) {
            return FailableFunction.CC.$default$andThen(this, failableFunction);
        }

        @Override // org.apache.commons.lang3.function.FailableFunction
        public final Object apply(Object obj) {
            return FailableFunction.CC.lambda$static$0(obj);
        }

        @Override // org.apache.commons.lang3.function.FailableFunction
        public /* synthetic */ FailableFunction compose(FailableFunction failableFunction) {
            return FailableFunction.CC.$default$compose(this, failableFunction);
        }
    };

    <V> FailableFunction<T, V, E> andThen(FailableFunction<? super R, ? extends V, E> failableFunction);

    R apply(T t) throws Throwable;

    <V> FailableFunction<V, R, E> compose(FailableFunction<? super V, ? extends T, E> failableFunction);

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, R, E extends Throwable> {
        static {
            FailableFunction failableFunction = FailableFunction.NOP;
        }

        public static /* synthetic */ Object lambda$identity$1(Object obj) throws Throwable {
            return obj;
        }

        public static /* synthetic */ Object lambda$static$0(Object obj) throws Throwable {
            return null;
        }

        public static <T, E extends Throwable> FailableFunction<T, T, E> identity() {
            return new FailableFunction() { // from class: org.apache.commons.lang3.function.FailableFunction$$ExternalSyntheticLambda0
                @Override // org.apache.commons.lang3.function.FailableFunction
                public /* synthetic */ FailableFunction andThen(FailableFunction failableFunction) {
                    return FailableFunction.CC.$default$andThen(this, failableFunction);
                }

                @Override // org.apache.commons.lang3.function.FailableFunction
                public final Object apply(Object obj) {
                    return FailableFunction.CC.lambda$identity$1(obj);
                }

                @Override // org.apache.commons.lang3.function.FailableFunction
                public /* synthetic */ FailableFunction compose(FailableFunction failableFunction) {
                    return FailableFunction.CC.$default$compose(this, failableFunction);
                }
            };
        }

        public static <T, R, E extends Throwable> FailableFunction<T, R, E> nop() {
            return FailableFunction.NOP;
        }

        public static FailableFunction $default$andThen(final FailableFunction _this, final FailableFunction failableFunction) {
            Objects.requireNonNull(failableFunction);
            return new FailableFunction() { // from class: org.apache.commons.lang3.function.FailableFunction$$ExternalSyntheticLambda1
                @Override // org.apache.commons.lang3.function.FailableFunction
                public /* synthetic */ FailableFunction andThen(FailableFunction failableFunction2) {
                    return FailableFunction.CC.$default$andThen(this, failableFunction2);
                }

                @Override // org.apache.commons.lang3.function.FailableFunction
                public final Object apply(Object obj) {
                    return failableFunction.apply(_this.apply(obj));
                }

                @Override // org.apache.commons.lang3.function.FailableFunction
                public /* synthetic */ FailableFunction compose(FailableFunction failableFunction2) {
                    return FailableFunction.CC.$default$compose(this, failableFunction2);
                }
            };
        }

        public static FailableFunction $default$compose(final FailableFunction _this, final FailableFunction failableFunction) {
            Objects.requireNonNull(failableFunction);
            return new FailableFunction() { // from class: org.apache.commons.lang3.function.FailableFunction$$ExternalSyntheticLambda3
                @Override // org.apache.commons.lang3.function.FailableFunction
                public /* synthetic */ FailableFunction andThen(FailableFunction failableFunction2) {
                    return FailableFunction.CC.$default$andThen(this, failableFunction2);
                }

                @Override // org.apache.commons.lang3.function.FailableFunction
                public final Object apply(Object obj) {
                    return _this.apply(failableFunction.apply(obj));
                }

                @Override // org.apache.commons.lang3.function.FailableFunction
                public /* synthetic */ FailableFunction compose(FailableFunction failableFunction2) {
                    return FailableFunction.CC.$default$compose(this, failableFunction2);
                }
            };
        }
    }
}
