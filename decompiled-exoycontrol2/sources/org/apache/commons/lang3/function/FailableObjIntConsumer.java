package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableObjIntConsumer;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableObjIntConsumer<T, E extends Throwable> {
    public static final FailableObjIntConsumer NOP = new FailableObjIntConsumer() { // from class: org.apache.commons.lang3.function.FailableObjIntConsumer$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableObjIntConsumer
        public final void accept(Object obj, int i) throws Throwable {
            FailableObjIntConsumer.CC.lambda$static$0(obj, i);
        }
    };

    void accept(T t, int i) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableObjIntConsumer$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, E extends Throwable> {
        static {
            FailableObjIntConsumer failableObjIntConsumer = FailableObjIntConsumer.NOP;
        }

        public static /* synthetic */ void lambda$static$0(Object obj, int i) throws Throwable {
        }

        public static <T, E extends Throwable> FailableObjIntConsumer<T, E> nop() {
            return FailableObjIntConsumer.NOP;
        }
    }
}
