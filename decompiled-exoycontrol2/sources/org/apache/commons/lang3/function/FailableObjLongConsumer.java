package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableObjLongConsumer;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableObjLongConsumer<T, E extends Throwable> {
    public static final FailableObjLongConsumer NOP = new FailableObjLongConsumer() { // from class: org.apache.commons.lang3.function.FailableObjLongConsumer$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableObjLongConsumer
        public final void accept(Object obj, long j) throws Throwable {
            FailableObjLongConsumer.CC.lambda$static$0(obj, j);
        }
    };

    void accept(T t, long j) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableObjLongConsumer$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, E extends Throwable> {
        static {
            FailableObjLongConsumer failableObjLongConsumer = FailableObjLongConsumer.NOP;
        }

        public static /* synthetic */ void lambda$static$0(Object obj, long j) throws Throwable {
        }

        public static <T, E extends Throwable> FailableObjLongConsumer<T, E> nop() {
            return FailableObjLongConsumer.NOP;
        }
    }
}
