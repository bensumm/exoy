package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableIntFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableIntFunction<R, E extends Throwable> {
    public static final FailableIntFunction NOP = new FailableIntFunction() { // from class: org.apache.commons.lang3.function.FailableIntFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableIntFunction
        public final Object apply(int i) {
            return FailableIntFunction.CC.lambda$static$0(i);
        }
    };

    R apply(int i) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableIntFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<R, E extends Throwable> {
        static {
            FailableIntFunction failableIntFunction = FailableIntFunction.NOP;
        }

        public static /* synthetic */ Object lambda$static$0(int i) throws Throwable {
            return null;
        }

        public static <R, E extends Throwable> FailableIntFunction<R, E> nop() {
            return FailableIntFunction.NOP;
        }
    }
}
