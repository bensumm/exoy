package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableToIntBiFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableToIntBiFunction<T, U, E extends Throwable> {
    public static final FailableToIntBiFunction NOP = new FailableToIntBiFunction() { // from class: org.apache.commons.lang3.function.FailableToIntBiFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableToIntBiFunction
        public final int applyAsInt(Object obj, Object obj2) {
            return FailableToIntBiFunction.CC.lambda$static$0(obj, obj2);
        }
    };

    int applyAsInt(T t, U u) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableToIntBiFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, U, E extends Throwable> {
        static {
            FailableToIntBiFunction failableToIntBiFunction = FailableToIntBiFunction.NOP;
        }

        public static /* synthetic */ int lambda$static$0(Object obj, Object obj2) throws Throwable {
            return 0;
        }

        public static <T, U, E extends Throwable> FailableToIntBiFunction<T, U, E> nop() {
            return FailableToIntBiFunction.NOP;
        }
    }
}
