package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import org.apache.commons.lang3.function.FailableObjDoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableObjDoubleConsumer<T, E extends Throwable> {
    public static final FailableObjDoubleConsumer NOP = new FailableObjDoubleConsumer() { // from class: org.apache.commons.lang3.function.FailableObjDoubleConsumer$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableObjDoubleConsumer
        public final void accept(Object obj, double d) throws Throwable {
            FailableObjDoubleConsumer.CC.lambda$static$0(obj, d);
        }
    };

    void accept(T t, double d) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableObjDoubleConsumer$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, E extends Throwable> {
        static {
            FailableObjDoubleConsumer failableObjDoubleConsumer = FailableObjDoubleConsumer.NOP;
        }

        public static /* synthetic */ void lambda$static$0(Object obj, double d) throws Throwable {
        }

        public static <T, E extends Throwable> FailableObjDoubleConsumer<T, E> nop() {
            return FailableObjDoubleConsumer.NOP;
        }
    }
}
