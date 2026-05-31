package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableDoubleToIntFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableDoubleToIntFunction<E extends Throwable> {
    public static final FailableDoubleToIntFunction NOP = new FailableDoubleToIntFunction() { // from class: org.apache.commons.lang3.function.FailableDoubleToIntFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableDoubleToIntFunction
        public final int applyAsInt(double d) {
            return FailableDoubleToIntFunction.CC.lambda$static$0(d);
        }
    };

    int applyAsInt(double d) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableDoubleToIntFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableDoubleToIntFunction failableDoubleToIntFunction = FailableDoubleToIntFunction.NOP;
        }

        public static /* synthetic */ int lambda$static$0(double d) throws Throwable {
            return 0;
        }

        public static <E extends Throwable> FailableDoubleToIntFunction<E> nop() {
            return FailableDoubleToIntFunction.NOP;
        }
    }
}
