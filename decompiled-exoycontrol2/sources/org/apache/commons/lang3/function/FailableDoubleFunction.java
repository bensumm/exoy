package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableDoubleFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableDoubleFunction<R, E extends Throwable> {
    public static final FailableDoubleFunction NOP = new FailableDoubleFunction() { // from class: org.apache.commons.lang3.function.FailableDoubleFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableDoubleFunction
        public final Object apply(double d) {
            return FailableDoubleFunction.CC.lambda$static$0(d);
        }
    };

    R apply(double d) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableDoubleFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<R, E extends Throwable> {
        static {
            FailableDoubleFunction failableDoubleFunction = FailableDoubleFunction.NOP;
        }

        public static /* synthetic */ Object lambda$static$0(double d) throws Throwable {
            return null;
        }

        public static <R, E extends Throwable> FailableDoubleFunction<R, E> nop() {
            return FailableDoubleFunction.NOP;
        }
    }
}
