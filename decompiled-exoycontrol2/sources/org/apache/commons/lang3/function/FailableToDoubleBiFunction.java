package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableToDoubleBiFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableToDoubleBiFunction<T, U, E extends Throwable> {
    public static final FailableToDoubleBiFunction NOP = new FailableToDoubleBiFunction() { // from class: org.apache.commons.lang3.function.FailableToDoubleBiFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableToDoubleBiFunction
        public final double applyAsDouble(Object obj, Object obj2) {
            return FailableToDoubleBiFunction.CC.lambda$static$0(obj, obj2);
        }
    };

    double applyAsDouble(T t, U u) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableToDoubleBiFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, U, E extends Throwable> {
        static {
            FailableToDoubleBiFunction failableToDoubleBiFunction = FailableToDoubleBiFunction.NOP;
        }

        public static /* synthetic */ double lambda$static$0(Object obj, Object obj2) throws Throwable {
            return 0.0d;
        }

        public static <T, U, E extends Throwable> FailableToDoubleBiFunction<T, U, E> nop() {
            return FailableToDoubleBiFunction.NOP;
        }
    }
}
