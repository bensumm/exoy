package kotlin.time;

import com.facebook.react.uimanager.ViewProps;
import kotlin.Metadata;
import org.apache.commons.lang3.ClassUtils;

/* JADX INFO: compiled from: TimeSources.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", ViewProps.OVERFLOW, "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
    }

    @Override // kotlin.time.AbstractLongTimeSource
    /* JADX INFO: renamed from: read, reason: from getter */
    protected long getReading() {
        return this.reading;
    }

    /* JADX INFO: renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m2000plusAssignLRDsOJo(long duration) {
        long j;
        long jM1911toLongimpl = Duration.m1911toLongimpl(duration, getUnit());
        if (jM1911toLongimpl != Long.MIN_VALUE && jM1911toLongimpl != Long.MAX_VALUE) {
            long j2 = this.reading;
            j = j2 + jM1911toLongimpl;
            if ((jM1911toLongimpl ^ j2) >= 0 && (j2 ^ j) < 0) {
                m1999overflowLRDsOJo(duration);
            }
        } else {
            double dM1908toDoubleimpl = this.reading + Duration.m1908toDoubleimpl(duration, getUnit());
            if (dM1908toDoubleimpl > 9.223372036854776E18d || dM1908toDoubleimpl < -9.223372036854776E18d) {
                m1999overflowLRDsOJo(duration);
            }
            j = (long) dM1908toDoubleimpl;
        }
        this.reading = j;
    }

    /* JADX INFO: renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m1999overflowLRDsOJo(long duration) {
        throw new IllegalStateException("TestTimeSource will overflow if its reading " + this.reading + DurationUnitKt.shortName(getUnit()) + " is advanced by " + ((Object) Duration.m1914toStringimpl(duration)) + ClassUtils.PACKAGE_SEPARATOR_CHAR);
    }
}
