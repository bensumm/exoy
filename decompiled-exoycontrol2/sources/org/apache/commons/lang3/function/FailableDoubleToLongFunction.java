package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableDoubleToLongFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableDoubleToLongFunction<E extends Throwable> {
    public static final FailableDoubleToLongFunction NOP = new FailableDoubleToLongFunction() { // from class: org.apache.commons.lang3.function.FailableDoubleToLongFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableDoubleToLongFunction
        public final int applyAsLong(double d) {
            return FailableDoubleToLongFunction.CC.lambda$static$0(d);
        }
    };

    int applyAsLong(double d) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableDoubleToLongFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableDoubleToLongFunction failableDoubleToLongFunction = FailableDoubleToLongFunction.NOP;
        }

        public static /* synthetic */ int lambda$static$0(double d) throws Throwable {
            return 0;
        }

        public static <E extends Throwable> FailableDoubleToLongFunction<E> nop() {
            return FailableDoubleToLongFunction.NOP;
        }
    }
}
