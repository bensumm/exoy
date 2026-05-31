package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableToDoubleFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableToDoubleFunction<T, E extends Throwable> {
    public static final FailableToDoubleFunction NOP = new FailableToDoubleFunction() { // from class: org.apache.commons.lang3.function.FailableToDoubleFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableToDoubleFunction
        public final double applyAsDouble(Object obj) {
            return FailableToDoubleFunction.CC.lambda$static$0(obj);
        }
    };

    double applyAsDouble(T t) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableToDoubleFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, E extends Throwable> {
        static {
            FailableToDoubleFunction failableToDoubleFunction = FailableToDoubleFunction.NOP;
        }

        public static /* synthetic */ double lambda$static$0(Object obj) throws Throwable {
            return 0.0d;
        }

        public static <T, E extends Throwable> FailableToDoubleFunction<T, E> nop() {
            return FailableToDoubleFunction.NOP;
        }
    }
}
