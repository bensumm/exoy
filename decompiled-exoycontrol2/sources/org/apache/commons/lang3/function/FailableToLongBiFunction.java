package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableToLongBiFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableToLongBiFunction<T, U, E extends Throwable> {
    public static final FailableToLongBiFunction NOP = new FailableToLongBiFunction() { // from class: org.apache.commons.lang3.function.FailableToLongBiFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableToLongBiFunction
        public final long applyAsLong(Object obj, Object obj2) {
            return FailableToLongBiFunction.CC.lambda$static$0(obj, obj2);
        }
    };

    long applyAsLong(T t, U u) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableToLongBiFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, U, E extends Throwable> {
        static {
            FailableToLongBiFunction failableToLongBiFunction = FailableToLongBiFunction.NOP;
        }

        public static /* synthetic */ long lambda$static$0(Object obj, Object obj2) throws Throwable {
            return 0L;
        }

        public static <T, U, E extends Throwable> FailableToLongBiFunction<T, U, E> nop() {
            return FailableToLongBiFunction.NOP;
        }
    }
}
