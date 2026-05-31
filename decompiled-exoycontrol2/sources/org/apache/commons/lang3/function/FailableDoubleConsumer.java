package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableDoubleConsumer;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableDoubleConsumer<E extends Throwable> {
    public static final FailableDoubleConsumer NOP = new FailableDoubleConsumer() { // from class: org.apache.commons.lang3.function.FailableDoubleConsumer$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.FailableDoubleConsumer
        public final void accept(double d) throws Throwable {
            FailableDoubleConsumer.CC.lambda$static$0(d);
        }

        @Override // org.apache.commons.lang3.function.FailableDoubleConsumer
        public /* synthetic */ FailableDoubleConsumer andThen(FailableDoubleConsumer failableDoubleConsumer) {
            return FailableDoubleConsumer.CC.$default$andThen(this, failableDoubleConsumer);
        }
    };

    void accept(double d) throws Throwable;

    FailableDoubleConsumer<E> andThen(FailableDoubleConsumer<E> failableDoubleConsumer);

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableDoubleConsumer$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableDoubleConsumer failableDoubleConsumer = FailableDoubleConsumer.NOP;
        }

        public static /* synthetic */ void lambda$static$0(double d) throws Throwable {
        }

        public static <E extends Throwable> FailableDoubleConsumer<E> nop() {
            return FailableDoubleConsumer.NOP;
        }

        public static FailableDoubleConsumer $default$andThen(final FailableDoubleConsumer _this, final FailableDoubleConsumer failableDoubleConsumer) {
            Objects.requireNonNull(failableDoubleConsumer);
            return new FailableDoubleConsumer() { // from class: org.apache.commons.lang3.function.FailableDoubleConsumer$$ExternalSyntheticLambda1
                @Override // org.apache.commons.lang3.function.FailableDoubleConsumer
                public final void accept(double d) throws Throwable {
                    FailableDoubleConsumer.CC.$private$lambda$andThen$1(_this, failableDoubleConsumer, d);
                }

                @Override // org.apache.commons.lang3.function.FailableDoubleConsumer
                public /* synthetic */ FailableDoubleConsumer andThen(FailableDoubleConsumer failableDoubleConsumer2) {
                    return FailableDoubleConsumer.CC.$default$andThen(this, failableDoubleConsumer2);
                }
            };
        }

        public static /* synthetic */ void $private$lambda$andThen$1(FailableDoubleConsumer _this, FailableDoubleConsumer failableDoubleConsumer, double d) throws Throwable {
            _this.accept(d);
            failableDoubleConsumer.accept(d);
        }
    }
}
