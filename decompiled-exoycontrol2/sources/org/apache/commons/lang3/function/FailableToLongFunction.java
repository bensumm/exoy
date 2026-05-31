package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableToLongFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableToLongFunction<T, E extends Throwable> {
    public static final FailableToLongFunction NOP = new FailableToLongFunction() { // from class: org.apache.commons.lang3.function.FailableToLongFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableToLongFunction
        public final long applyAsLong(Object obj) {
            return FailableToLongFunction.CC.lambda$static$0(obj);
        }
    };

    long applyAsLong(T t) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableToLongFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, E extends Throwable> {
        static {
            FailableToLongFunction failableToLongFunction = FailableToLongFunction.NOP;
        }

        public static /* synthetic */ long lambda$static$0(Object obj) throws Throwable {
            return 0L;
        }

        public static <T, E extends Throwable> FailableToLongFunction<T, E> nop() {
            return FailableToLongFunction.NOP;
        }
    }
}
