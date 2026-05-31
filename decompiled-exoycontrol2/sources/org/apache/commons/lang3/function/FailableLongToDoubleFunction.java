package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableLongToDoubleFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableLongToDoubleFunction<E extends Throwable> {
    public static final FailableLongToDoubleFunction NOP = new FailableLongToDoubleFunction() { // from class: org.apache.commons.lang3.function.FailableLongToDoubleFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableLongToDoubleFunction
        public final double applyAsDouble(long j) {
            return FailableLongToDoubleFunction.CC.lambda$static$0(j);
        }
    };

    double applyAsDouble(long j) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableLongToDoubleFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableLongToDoubleFunction failableLongToDoubleFunction = FailableLongToDoubleFunction.NOP;
        }

        public static /* synthetic */ double lambda$static$0(long j) throws Throwable {
            return 0.0d;
        }

        public static <E extends Throwable> FailableLongToDoubleFunction<E> nop() {
            return FailableLongToDoubleFunction.NOP;
        }
    }
}
