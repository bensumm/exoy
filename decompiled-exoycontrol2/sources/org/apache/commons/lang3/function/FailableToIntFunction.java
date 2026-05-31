package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableToIntFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableToIntFunction<T, E extends Throwable> {
    public static final FailableToIntFunction NOP = new FailableToIntFunction() { // from class: org.apache.commons.lang3.function.FailableToIntFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableToIntFunction
        public final int applyAsInt(Object obj) {
            return FailableToIntFunction.CC.lambda$static$0(obj);
        }
    };

    int applyAsInt(T t) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableToIntFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, E extends Throwable> {
        static {
            FailableToIntFunction failableToIntFunction = FailableToIntFunction.NOP;
        }

        public static /* synthetic */ int lambda$static$0(Object obj) throws Throwable {
            return 0;
        }

        public static <T, E extends Throwable> FailableToIntFunction<T, E> nop() {
            return FailableToIntFunction.NOP;
        }
    }
}
