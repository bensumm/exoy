package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableLongToIntFunction;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableLongToIntFunction<E extends Throwable> {
    public static final FailableLongToIntFunction NOP = new FailableLongToIntFunction() { // from class: org.apache.commons.lang3.function.FailableLongToIntFunction$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableLongToIntFunction
        public final int applyAsInt(long j) {
            return FailableLongToIntFunction.CC.lambda$static$0(j);
        }
    };

    int applyAsInt(long j) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableLongToIntFunction$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableLongToIntFunction failableLongToIntFunction = FailableLongToIntFunction.NOP;
        }

        public static /* synthetic */ int lambda$static$0(long j) throws Throwable {
            return 0;
        }

        public static <E extends Throwable> FailableLongToIntFunction<E> nop() {
            return FailableLongToIntFunction.NOP;
        }
    }
}
