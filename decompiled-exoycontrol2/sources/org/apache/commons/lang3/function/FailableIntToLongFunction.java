package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableIntToLongFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableIntToLongFunction<E extends Throwable> {
    public static final FailableIntToLongFunction NOP = new FailableIntToLongFunction() { // from class: org.apache.commons.lang3.function.FailableIntToLongFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableIntToLongFunction
        public final long applyAsLong(int i) {
            return FailableIntToLongFunction.CC.lambda$static$0(i);
        }
    };

    long applyAsLong(int i) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableIntToLongFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableIntToLongFunction failableIntToLongFunction = FailableIntToLongFunction.NOP;
        }

        public static /* synthetic */ long lambda$static$0(int i) throws Throwable {
            return 0L;
        }

        public static <E extends Throwable> FailableIntToLongFunction<E> nop() {
            return FailableIntToLongFunction.NOP;
        }
    }
}
