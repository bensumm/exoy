package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableLongFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableLongFunction<R, E extends Throwable> {
    public static final FailableLongFunction NOP = new FailableLongFunction() { // from class: org.apache.commons.lang3.function.FailableLongFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableLongFunction
        public final Object apply(long j) {
            return FailableLongFunction.CC.lambda$static$0(j);
        }
    };

    R apply(long j) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableLongFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<R, E extends Throwable> {
        static {
            FailableLongFunction failableLongFunction = FailableLongFunction.NOP;
        }

        public static /* synthetic */ Object lambda$static$0(long j) throws Throwable {
            return null;
        }

        public static <R, E extends Throwable> FailableLongFunction<R, E> nop() {
            return FailableLongFunction.NOP;
        }
    }
}
