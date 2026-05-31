package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableIntToDoubleFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableIntToDoubleFunction<E extends Throwable> {
    public static final FailableIntToDoubleFunction NOP = new FailableIntToDoubleFunction() { // from class: org.apache.commons.lang3.function.FailableIntToDoubleFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableIntToDoubleFunction
        public final double applyAsDouble(int i) {
            return FailableIntToDoubleFunction.CC.lambda$static$0(i);
        }
    };

    double applyAsDouble(int i) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableIntToDoubleFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableIntToDoubleFunction failableIntToDoubleFunction = FailableIntToDoubleFunction.NOP;
        }

        public static /* synthetic */ double lambda$static$0(int i) throws Throwable {
            return 0.0d;
        }

        public static <E extends Throwable> FailableIntToDoubleFunction<E> nop() {
            return FailableIntToDoubleFunction.NOP;
        }
    }
}
