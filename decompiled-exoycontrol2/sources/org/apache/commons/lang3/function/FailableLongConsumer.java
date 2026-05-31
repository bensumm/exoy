package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableLongConsumer;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableLongConsumer<E extends Throwable> {
    public static final FailableLongConsumer NOP = new FailableLongConsumer() { // from class: org.apache.commons.lang3.function.FailableLongConsumer$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableLongConsumer
        public final void accept(long j) throws Throwable {
            FailableLongConsumer.CC.lambda$static$0(j);
        }

        @Override // org.apache.commons.lang3.function.FailableLongConsumer
        public /* synthetic */ FailableLongConsumer andThen(FailableLongConsumer failableLongConsumer) {
            return FailableLongConsumer.CC.$default$andThen(this, failableLongConsumer);
        }
    };

    void accept(long j) throws Throwable;

    FailableLongConsumer<E> andThen(FailableLongConsumer<E> failableLongConsumer);

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableLongConsumer$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableLongConsumer failableLongConsumer = FailableLongConsumer.NOP;
        }

        public static /* synthetic */ void lambda$static$0(long j) throws Throwable {
        }

        public static <E extends Throwable> FailableLongConsumer<E> nop() {
            return FailableLongConsumer.NOP;
        }

        public static FailableLongConsumer $default$andThen(final FailableLongConsumer _this, final FailableLongConsumer failableLongConsumer) {
            Objects.requireNonNull(failableLongConsumer);
            return new FailableLongConsumer() { // from class: org.apache.commons.lang3.function.FailableLongConsumer$$ExternalSyntheticLambda1
                @Override // org.apache.commons.lang3.function.FailableLongConsumer
                public final void accept(long j) throws Throwable {
                    FailableLongConsumer.CC.$private$lambda$andThen$1(_this, failableLongConsumer, j);
                }

                @Override // org.apache.commons.lang3.function.FailableLongConsumer
                public /* synthetic */ FailableLongConsumer andThen(FailableLongConsumer failableLongConsumer2) {
                    return FailableLongConsumer.CC.$default$andThen(this, failableLongConsumer2);
                }
            };
        }

        public static /* synthetic */ void $private$lambda$andThen$1(FailableLongConsumer _this, FailableLongConsumer failableLongConsumer, long j) throws Throwable {
            _this.accept(j);
            failableLongConsumer.accept(j);
        }
    }
}
