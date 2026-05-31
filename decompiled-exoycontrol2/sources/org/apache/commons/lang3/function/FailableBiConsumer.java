package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableBiConsumer;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableBiConsumer<T, U, E extends Throwable> {
    public static final FailableBiConsumer NOP = new FailableBiConsumer() { // from class: org.apache.commons.lang3.function.FailableBiConsumer$$ExternalSyntheticLambda1
        @Override // org.apache.commons.lang3.function.FailableBiConsumer
        public final void accept(Object obj, Object obj2) throws Throwable {
            FailableBiConsumer.CC.lambda$static$0(obj, obj2);
        }

        @Override // org.apache.commons.lang3.function.FailableBiConsumer
        public /* synthetic */ FailableBiConsumer andThen(FailableBiConsumer failableBiConsumer) {
            return FailableBiConsumer.CC.$default$andThen(this, failableBiConsumer);
        }
    };

    void accept(T t, U u) throws Throwable;

    FailableBiConsumer<T, U, E> andThen(FailableBiConsumer<? super T, ? super U, E> failableBiConsumer);

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableBiConsumer$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, U, E extends Throwable> {
        static {
            FailableBiConsumer failableBiConsumer = FailableBiConsumer.NOP;
        }

        public static /* synthetic */ void lambda$static$0(Object obj, Object obj2) throws Throwable {
        }

        public static <T, U, E extends Throwable> FailableBiConsumer<T, U, E> nop() {
            return FailableBiConsumer.NOP;
        }

        public static FailableBiConsumer $default$andThen(final FailableBiConsumer _this, final FailableBiConsumer failableBiConsumer) {
            Objects.requireNonNull(failableBiConsumer);
            return new FailableBiConsumer() { // from class: org.apache.commons.lang3.function.FailableBiConsumer$$ExternalSyntheticLambda0
                @Override // org.apache.commons.lang3.function.FailableBiConsumer
                public final void accept(Object obj, Object obj2) throws Throwable {
                    FailableBiConsumer.CC.$private$lambda$andThen$1(_this, failableBiConsumer, obj, obj2);
                }

                @Override // org.apache.commons.lang3.function.FailableBiConsumer
                public /* synthetic */ FailableBiConsumer andThen(FailableBiConsumer failableBiConsumer2) {
                    return FailableBiConsumer.CC.$default$andThen(this, failableBiConsumer2);
                }
            };
        }

        public static /* synthetic */ void $private$lambda$andThen$1(FailableBiConsumer _this, FailableBiConsumer failableBiConsumer, Object obj, Object obj2) throws Throwable {
            _this.accept(obj, obj2);
            failableBiConsumer.accept(obj, obj2);
        }
    }
}
