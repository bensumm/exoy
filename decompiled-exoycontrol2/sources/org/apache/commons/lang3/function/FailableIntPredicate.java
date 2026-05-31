package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableIntPredicate;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableIntPredicate<E extends Throwable> {
    public static final FailableIntPredicate FALSE = new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda1
        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public /* synthetic */ FailableIntPredicate and(FailableIntPredicate failableIntPredicate) {
            return FailableIntPredicate.CC.$default$and(this, failableIntPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public /* synthetic */ FailableIntPredicate negate() {
            return FailableIntPredicate.CC.$default$negate(this);
        }

        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public /* synthetic */ FailableIntPredicate or(FailableIntPredicate failableIntPredicate) {
            return FailableIntPredicate.CC.$default$or(this, failableIntPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public final boolean test(int i) {
            return FailableIntPredicate.CC.lambda$static$0(i);
        }
    };
    public static final FailableIntPredicate TRUE = new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda2
        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public /* synthetic */ FailableIntPredicate and(FailableIntPredicate failableIntPredicate) {
            return FailableIntPredicate.CC.$default$and(this, failableIntPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public /* synthetic */ FailableIntPredicate negate() {
            return FailableIntPredicate.CC.$default$negate(this);
        }

        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public /* synthetic */ FailableIntPredicate or(FailableIntPredicate failableIntPredicate) {
            return FailableIntPredicate.CC.$default$or(this, failableIntPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public final boolean test(int i) {
            return FailableIntPredicate.CC.lambda$static$1(i);
        }
    };

    FailableIntPredicate<E> and(FailableIntPredicate<E> failableIntPredicate);

    FailableIntPredicate<E> negate();

    FailableIntPredicate<E> or(FailableIntPredicate<E> failableIntPredicate);

    boolean test(int i) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableIntPredicate$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<E extends Throwable> {
        static {
            FailableIntPredicate failableIntPredicate = FailableIntPredicate.FALSE;
        }

        public static /* synthetic */ boolean lambda$static$0(int i) throws Throwable {
            return false;
        }

        public static /* synthetic */ boolean lambda$static$1(int i) throws Throwable {
            return true;
        }

        public static <E extends Throwable> FailableIntPredicate<E> falsePredicate() {
            return FailableIntPredicate.FALSE;
        }

        public static <E extends Throwable> FailableIntPredicate<E> truePredicate() {
            return FailableIntPredicate.TRUE;
        }

        public static FailableIntPredicate $default$and(final FailableIntPredicate _this, final FailableIntPredicate failableIntPredicate) {
            Objects.requireNonNull(failableIntPredicate);
            return new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda3
                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate and(FailableIntPredicate failableIntPredicate2) {
                    return FailableIntPredicate.CC.$default$and(this, failableIntPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate negate() {
                    return FailableIntPredicate.CC.$default$negate(this);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate or(FailableIntPredicate failableIntPredicate2) {
                    return FailableIntPredicate.CC.$default$or(this, failableIntPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public final boolean test(int i) {
                    return FailableIntPredicate.CC.$private$lambda$and$2(_this, failableIntPredicate, i);
                }
            };
        }

        public static /* synthetic */ boolean $private$lambda$and$2(FailableIntPredicate _this, FailableIntPredicate failableIntPredicate, int i) throws Throwable {
            return _this.test(i) && failableIntPredicate.test(i);
        }

        public static FailableIntPredicate $default$negate(final FailableIntPredicate _this) {
            return new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda0
                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate and(FailableIntPredicate failableIntPredicate) {
                    return FailableIntPredicate.CC.$default$and(this, failableIntPredicate);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate negate() {
                    return FailableIntPredicate.CC.$default$negate(this);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate or(FailableIntPredicate failableIntPredicate) {
                    return FailableIntPredicate.CC.$default$or(this, failableIntPredicate);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public final boolean test(int i) {
                    return FailableIntPredicate.CC.$private$lambda$negate$3(_this, i);
                }
            };
        }

        public static /* synthetic */ boolean $private$lambda$negate$3(FailableIntPredicate _this, int i) throws Throwable {
            return !_this.test(i);
        }

        public static FailableIntPredicate $default$or(final FailableIntPredicate _this, final FailableIntPredicate failableIntPredicate) {
            Objects.requireNonNull(failableIntPredicate);
            return new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda4
                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate and(FailableIntPredicate failableIntPredicate2) {
                    return FailableIntPredicate.CC.$default$and(this, failableIntPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate negate() {
                    return FailableIntPredicate.CC.$default$negate(this);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public /* synthetic */ FailableIntPredicate or(FailableIntPredicate failableIntPredicate2) {
                    return FailableIntPredicate.CC.$default$or(this, failableIntPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableIntPredicate
                public final boolean test(int i) {
                    return FailableIntPredicate.CC.$private$lambda$or$4(_this, failableIntPredicate, i);
                }
            };
        }

        public static /* synthetic */ boolean $private$lambda$or$4(FailableIntPredicate _this, FailableIntPredicate failableIntPredicate, int i) throws Throwable {
            return _this.test(i) || failableIntPredicate.test(i);
        }
    }
}
