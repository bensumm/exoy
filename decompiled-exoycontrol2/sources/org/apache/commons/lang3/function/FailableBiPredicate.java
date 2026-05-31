package org.apache.commons.lang3.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.lang.Throwable;
import java.util.Objects;
import org.apache.commons.lang3.function.FailableBiPredicate;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface FailableBiPredicate<T, U, E extends Throwable> {
    public static final FailableBiPredicate FALSE = new FailableBiPredicate() { // from class: org.apache.commons.lang3.function.FailableBiPredicate$$ExternalSyntheticLambda2
        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public /* synthetic */ FailableBiPredicate and(FailableBiPredicate failableBiPredicate) {
            return FailableBiPredicate.CC.$default$and(this, failableBiPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public /* synthetic */ FailableBiPredicate negate() {
            return FailableBiPredicate.CC.$default$negate(this);
        }

        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public /* synthetic */ FailableBiPredicate or(FailableBiPredicate failableBiPredicate) {
            return FailableBiPredicate.CC.$default$or(this, failableBiPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public final boolean test(Object obj, Object obj2) {
            return FailableBiPredicate.CC.lambda$static$0(obj, obj2);
        }
    };
    public static final FailableBiPredicate TRUE = new FailableBiPredicate() { // from class: org.apache.commons.lang3.function.FailableBiPredicate$$ExternalSyntheticLambda3
        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public /* synthetic */ FailableBiPredicate and(FailableBiPredicate failableBiPredicate) {
            return FailableBiPredicate.CC.$default$and(this, failableBiPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public /* synthetic */ FailableBiPredicate negate() {
            return FailableBiPredicate.CC.$default$negate(this);
        }

        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public /* synthetic */ FailableBiPredicate or(FailableBiPredicate failableBiPredicate) {
            return FailableBiPredicate.CC.$default$or(this, failableBiPredicate);
        }

        @Override // org.apache.commons.lang3.function.FailableBiPredicate
        public final boolean test(Object obj, Object obj2) {
            return FailableBiPredicate.CC.lambda$static$1(obj, obj2);
        }
    };

    FailableBiPredicate<T, U, E> and(FailableBiPredicate<? super T, ? super U, E> failableBiPredicate);

    FailableBiPredicate<T, U, E> negate();

    FailableBiPredicate<T, U, E> or(FailableBiPredicate<? super T, ? super U, E> failableBiPredicate);

    boolean test(T t, U u) throws Throwable;

    /* JADX INFO: renamed from: org.apache.commons.lang3.function.FailableBiPredicate$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC<T, U, E extends Throwable> {
        static {
            FailableBiPredicate failableBiPredicate = FailableBiPredicate.FALSE;
        }

        public static /* synthetic */ boolean lambda$static$0(Object obj, Object obj2) throws Throwable {
            return false;
        }

        public static /* synthetic */ boolean lambda$static$1(Object obj, Object obj2) throws Throwable {
            return true;
        }

        public static <T, U, E extends Throwable> FailableBiPredicate<T, U, E> falsePredicate() {
            return FailableBiPredicate.FALSE;
        }

        public static <T, U, E extends Throwable> FailableBiPredicate<T, U, E> truePredicate() {
            return FailableBiPredicate.TRUE;
        }

        public static FailableBiPredicate $default$and(final FailableBiPredicate _this, final FailableBiPredicate failableBiPredicate) {
            Objects.requireNonNull(failableBiPredicate);
            return new FailableBiPredicate() { // from class: org.apache.commons.lang3.function.FailableBiPredicate$$ExternalSyntheticLambda1
                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate and(FailableBiPredicate failableBiPredicate2) {
                    return FailableBiPredicate.CC.$default$and(this, failableBiPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate negate() {
                    return FailableBiPredicate.CC.$default$negate(this);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate or(FailableBiPredicate failableBiPredicate2) {
                    return FailableBiPredicate.CC.$default$or(this, failableBiPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return FailableBiPredicate.CC.$private$lambda$and$2(_this, failableBiPredicate, obj, obj2);
                }
            };
        }

        public static /* synthetic */ boolean $private$lambda$and$2(FailableBiPredicate _this, FailableBiPredicate failableBiPredicate, Object obj, Object obj2) throws Throwable {
            return _this.test(obj, obj2) && failableBiPredicate.test(obj, obj2);
        }

        public static FailableBiPredicate $default$negate(final FailableBiPredicate _this) {
            return new FailableBiPredicate() { // from class: org.apache.commons.lang3.function.FailableBiPredicate$$ExternalSyntheticLambda4
                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate and(FailableBiPredicate failableBiPredicate) {
                    return FailableBiPredicate.CC.$default$and(this, failableBiPredicate);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate negate() {
                    return FailableBiPredicate.CC.$default$negate(this);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate or(FailableBiPredicate failableBiPredicate) {
                    return FailableBiPredicate.CC.$default$or(this, failableBiPredicate);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return FailableBiPredicate.CC.$private$lambda$negate$3(_this, obj, obj2);
                }
            };
        }

        public static /* synthetic */ boolean $private$lambda$negate$3(FailableBiPredicate _this, Object obj, Object obj2) throws Throwable {
            return !_this.test(obj, obj2);
        }

        public static FailableBiPredicate $default$or(final FailableBiPredicate _this, final FailableBiPredicate failableBiPredicate) {
            Objects.requireNonNull(failableBiPredicate);
            return new FailableBiPredicate() { // from class: org.apache.commons.lang3.function.FailableBiPredicate$$ExternalSyntheticLambda0
                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate and(FailableBiPredicate failableBiPredicate2) {
                    return FailableBiPredicate.CC.$default$and(this, failableBiPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate negate() {
                    return FailableBiPredicate.CC.$default$negate(this);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public /* synthetic */ FailableBiPredicate or(FailableBiPredicate failableBiPredicate2) {
                    return FailableBiPredicate.CC.$default$or(this, failableBiPredicate2);
                }

                @Override // org.apache.commons.lang3.function.FailableBiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return FailableBiPredicate.CC.$private$lambda$or$4(_this, failableBiPredicate, obj, obj2);
                }
            };
        }

        public static /* synthetic */ boolean $private$lambda$or$4(FailableBiPredicate _this, FailableBiPredicate failableBiPredicate, Object obj, Object obj2) throws Throwable {
            return _this.test(obj, obj2) || failableBiPredicate.test(obj, obj2);
        }
    }
}
