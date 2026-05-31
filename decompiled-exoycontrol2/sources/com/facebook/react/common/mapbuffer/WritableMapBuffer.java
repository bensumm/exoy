package com.facebook.react.common.mapbuffer;

import android.util.SparseArray;
import com.facebook.react.common.mapbuffer.MapBuffer;
import com.reactcommunity.rndatetimepicker.RNConstants;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;

/* JADX INFO: compiled from: WritableMapBuffer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\n\b\u0007\u0018\u0000 *2\u00020\u0001:\u0002*+B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0003J\u0010\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00192\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0013\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u001fH\u0003¢\u0006\u0002\u0010 J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\"H\u0096\u0002J\u0016\u0010#\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0001J\u0016\u0010#\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u000bJ\u0016\u0010#\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0012J\u0016\u0010#\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0004J\u0016\u0010#\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u001bJ\u0018\u0010%\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\tH\u0002J(\u0010&\u001a\u0002H'\"\u0006\b\u0000\u0010'\u0018\u00012\u0006\u0010\f\u001a\u00020\u00042\b\u0010$\u001a\u0004\u0018\u00010\tH\u0082\b¢\u0006\u0002\u0010(J\u0014\u0010)\u001a\u00020\u001d*\u00020\t2\u0006\u0010\f\u001a\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/facebook/react/common/mapbuffer/WritableMapBuffer;", "Lcom/facebook/react/common/mapbuffer/MapBuffer;", "()V", "count", "", "getCount", "()I", "values", "Landroid/util/SparseArray;", "", "contains", "", "key", "entryAt", "Lcom/facebook/react/common/mapbuffer/MapBuffer$Entry;", "offset", "getBoolean", "getDouble", "", "getInt", "getKeyOffset", "getKeys", "", "getMapBuffer", "getMapBufferList", "", "getString", "", "getType", "Lcom/facebook/react/common/mapbuffer/MapBuffer$DataType;", "getValues", "", "()[Ljava/lang/Object;", "iterator", "", "put", RNConstants.ARG_VALUE, "putInternal", "verifyValue", "T", "(ILjava/lang/Object;)Ljava/lang/Object;", "dataType", "Companion", "MapBufferEntry", "ReactAndroid_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
public final class WritableMapBuffer implements MapBuffer {
    private final SparseArray<Object> values = new SparseArray<>();

    public final WritableMapBuffer put(int key, boolean value) {
        return putInternal(key, Boolean.valueOf(value));
    }

    public final WritableMapBuffer put(int key, int value) {
        return putInternal(key, Integer.valueOf(value));
    }

    public final WritableMapBuffer put(int key, double value) {
        return putInternal(key, Double.valueOf(value));
    }

    public final WritableMapBuffer put(int key, String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return putInternal(key, value);
    }

    public final WritableMapBuffer put(int key, MapBuffer value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return putInternal(key, value);
    }

    private final WritableMapBuffer putInternal(int key, Object value) {
        IntRange kEY_RANGE$ReactAndroid_release = MapBuffer.INSTANCE.getKEY_RANGE$ReactAndroid_release();
        int first = kEY_RANGE$ReactAndroid_release.getFirst();
        boolean z = false;
        if (key <= kEY_RANGE$ReactAndroid_release.getLast() && first <= key) {
            z = true;
        }
        if (!z) {
            throw new IllegalArgumentException("Only integers in [0;65535] range are allowed for keys.".toString());
        }
        this.values.put(key, value);
        return this;
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public int getCount() {
        return this.values.size();
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public boolean contains(int key) {
        return this.values.get(key) != null;
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public int getKeyOffset(int key) {
        return this.values.indexOfKey(key);
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public MapBuffer.Entry entryAt(int offset) {
        return new MapBufferEntry(offset);
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public MapBuffer.DataType getType(int key) {
        Object value = this.values.get(key);
        if (!(value != null)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        Intrinsics.checkNotNullExpressionValue(value, "value");
        return dataType(value, key);
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public boolean getBoolean(int key) {
        Object obj = this.values.get(key);
        if (!(obj != null)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        if (!(obj instanceof Boolean)) {
            throw new IllegalStateException(("Expected " + Boolean.class + " for key: " + key + ", found " + obj.getClass() + " instead.").toString());
        }
        return ((Boolean) obj).booleanValue();
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public int getInt(int key) {
        Object obj = this.values.get(key);
        if (!(obj != null)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        if (!(obj instanceof Integer)) {
            throw new IllegalStateException(("Expected " + Integer.class + " for key: " + key + ", found " + obj.getClass() + " instead.").toString());
        }
        return ((Number) obj).intValue();
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public double getDouble(int key) {
        Object obj = this.values.get(key);
        if (!(obj != null)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        if (!(obj instanceof Double)) {
            throw new IllegalStateException(("Expected " + Double.class + " for key: " + key + ", found " + obj.getClass() + " instead.").toString());
        }
        return ((Number) obj).doubleValue();
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public String getString(int key) {
        Object obj = this.values.get(key);
        if (!(obj != null)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        if (!(obj instanceof String)) {
            throw new IllegalStateException(("Expected " + String.class + " for key: " + key + ", found " + obj.getClass() + " instead.").toString());
        }
        return (String) obj;
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public MapBuffer getMapBuffer(int key) {
        Object obj = this.values.get(key);
        if (!(obj != null)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        if (!(obj instanceof MapBuffer)) {
            throw new IllegalStateException(("Expected " + MapBuffer.class + " for key: " + key + ", found " + obj.getClass() + " instead.").toString());
        }
        return (MapBuffer) obj;
    }

    @Override // com.facebook.react.common.mapbuffer.MapBuffer
    public List<MapBuffer> getMapBufferList(int key) {
        Object obj = this.values.get(key);
        if (!(obj != null)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        if (!(obj instanceof List)) {
            throw new IllegalStateException(("Expected " + List.class + " for key: " + key + ", found " + obj.getClass() + " instead.").toString());
        }
        return (List) obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final /* synthetic */ <T> T verifyValue(int key, Object value) {
        if (!(value != 0)) {
            throw new IllegalArgumentException(("Key not found: " + key).toString());
        }
        Intrinsics.reifiedOperationMarker(3, "T");
        if (value instanceof Object) {
            return value;
        }
        StringBuilder sb = new StringBuilder("Expected ");
        Intrinsics.reifiedOperationMarker(4, "T");
        sb.append(Object.class);
        sb.append(" for key: ");
        sb.append(key);
        sb.append(", found ");
        sb.append(value.getClass());
        sb.append(" instead.");
        throw new IllegalStateException(sb.toString().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MapBuffer.DataType dataType(Object obj, int i) {
        if (obj instanceof Boolean) {
            return MapBuffer.DataType.BOOL;
        }
        if (obj instanceof Integer) {
            return MapBuffer.DataType.INT;
        }
        if (obj instanceof Double) {
            return MapBuffer.DataType.DOUBLE;
        }
        if (obj instanceof String) {
            return MapBuffer.DataType.STRING;
        }
        if (obj instanceof MapBuffer) {
            return MapBuffer.DataType.MAP;
        }
        throw new IllegalStateException("Key " + i + " has value of unknown type: " + obj.getClass());
    }

    /* JADX INFO: renamed from: com.facebook.react.common.mapbuffer.WritableMapBuffer$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: WritableMapBuffer.kt */
    @Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\t\u001a\u00020\nH\u0096\u0002J\t\u0010\u000b\u001a\u00020\u0002H\u0096\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"com/facebook/react/common/mapbuffer/WritableMapBuffer$iterator$1", "", "Lcom/facebook/react/common/mapbuffer/MapBuffer$Entry;", "count", "", "getCount", "()I", "setCount", "(I)V", "hasNext", "", "next", "ReactAndroid_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    public static final class AnonymousClass1 implements Iterator<MapBuffer.Entry>, KMappedMarker {
        private int count;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        AnonymousClass1() {
        }

        public final int getCount() {
            return this.count;
        }

        public final void setCount(int i) {
            this.count = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.count < WritableMapBuffer.this.values.size();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public MapBuffer.Entry next() {
            WritableMapBuffer writableMapBuffer = WritableMapBuffer.this;
            int i = this.count;
            this.count = i + 1;
            return writableMapBuffer.new MapBufferEntry(i);
        }
    }

    @Override // java.lang.Iterable
    public Iterator<MapBuffer.Entry> iterator() {
        return new AnonymousClass1();
    }

    /* JADX INFO: compiled from: WritableMapBuffer.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lcom/facebook/react/common/mapbuffer/WritableMapBuffer$MapBufferEntry;", "Lcom/facebook/react/common/mapbuffer/MapBuffer$Entry;", "index", "", "(Lcom/facebook/react/common/mapbuffer/WritableMapBuffer;I)V", "booleanValue", "", "getBooleanValue", "()Z", "doubleValue", "", "getDoubleValue", "()D", "intValue", "getIntValue", "()I", "key", "getKey", "mapBufferValue", "Lcom/facebook/react/common/mapbuffer/MapBuffer;", "getMapBufferValue", "()Lcom/facebook/react/common/mapbuffer/MapBuffer;", "stringValue", "", "getStringValue", "()Ljava/lang/String;", "type", "Lcom/facebook/react/common/mapbuffer/MapBuffer$DataType;", "getType", "()Lcom/facebook/react/common/mapbuffer/MapBuffer$DataType;", "ReactAndroid_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    private final class MapBufferEntry implements MapBuffer.Entry {
        private final int index;
        private final int key;
        private final MapBuffer.DataType type;

        public MapBufferEntry(int i) {
            this.index = i;
            this.key = WritableMapBuffer.this.values.keyAt(i);
            Object objValueAt = WritableMapBuffer.this.values.valueAt(i);
            Intrinsics.checkNotNullExpressionValue(objValueAt, "values.valueAt(index)");
            this.type = WritableMapBuffer.this.dataType(objValueAt, getKey());
        }

        @Override // com.facebook.react.common.mapbuffer.MapBuffer.Entry
        public int getKey() {
            return this.key;
        }

        @Override // com.facebook.react.common.mapbuffer.MapBuffer.Entry
        public MapBuffer.DataType getType() {
            return this.type;
        }

        @Override // com.facebook.react.common.mapbuffer.MapBuffer.Entry
        public boolean getBooleanValue() {
            int key = getKey();
            Object objValueAt = WritableMapBuffer.this.values.valueAt(this.index);
            if (!(objValueAt != null)) {
                throw new IllegalArgumentException(("Key not found: " + key).toString());
            }
            if (!(objValueAt instanceof Boolean)) {
                throw new IllegalStateException(("Expected " + Boolean.class + " for key: " + key + ", found " + objValueAt.getClass() + " instead.").toString());
            }
            return ((Boolean) objValueAt).booleanValue();
        }

        @Override // com.facebook.react.common.mapbuffer.MapBuffer.Entry
        public int getIntValue() {
            int key = getKey();
            Object objValueAt = WritableMapBuffer.this.values.valueAt(this.index);
            if (!(objValueAt != null)) {
                throw new IllegalArgumentException(("Key not found: " + key).toString());
            }
            if (!(objValueAt instanceof Integer)) {
                throw new IllegalStateException(("Expected " + Integer.class + " for key: " + key + ", found " + objValueAt.getClass() + " instead.").toString());
            }
            return ((Number) objValueAt).intValue();
        }

        @Override // com.facebook.react.common.mapbuffer.MapBuffer.Entry
        public double getDoubleValue() {
            int key = getKey();
            Object objValueAt = WritableMapBuffer.this.values.valueAt(this.index);
            if (!(objValueAt != null)) {
                throw new IllegalArgumentException(("Key not found: " + key).toString());
            }
            if (!(objValueAt instanceof Double)) {
                throw new IllegalStateException(("Expected " + Double.class + " for key: " + key + ", found " + objValueAt.getClass() + " instead.").toString());
            }
            return ((Number) objValueAt).doubleValue();
        }

        @Override // com.facebook.react.common.mapbuffer.MapBuffer.Entry
        public String getStringValue() {
            int key = getKey();
            Object objValueAt = WritableMapBuffer.this.values.valueAt(this.index);
            if (!(objValueAt != null)) {
                throw new IllegalArgumentException(("Key not found: " + key).toString());
            }
            if (!(objValueAt instanceof String)) {
                throw new IllegalStateException(("Expected " + String.class + " for key: " + key + ", found " + objValueAt.getClass() + " instead.").toString());
            }
            return (String) objValueAt;
        }

        @Override // com.facebook.react.common.mapbuffer.MapBuffer.Entry
        public MapBuffer getMapBufferValue() {
            int key = getKey();
            Object objValueAt = WritableMapBuffer.this.values.valueAt(this.index);
            if (!(objValueAt != null)) {
                throw new IllegalArgumentException(("Key not found: " + key).toString());
            }
            if (!(objValueAt instanceof MapBuffer)) {
                throw new IllegalStateException(("Expected " + MapBuffer.class + " for key: " + key + ", found " + objValueAt.getClass() + " instead.").toString());
            }
            return (MapBuffer) objValueAt;
        }
    }

    private final int[] getKeys() {
        int size = this.values.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.values.keyAt(i);
        }
        return iArr;
    }

    private final Object[] getValues() {
        int size = this.values.size();
        Object[] objArr = new Object[size];
        for (int i = 0; i < size; i++) {
            Object objValueAt = this.values.valueAt(i);
            Intrinsics.checkNotNullExpressionValue(objValueAt, "values.valueAt(it)");
            objArr[i] = objValueAt;
        }
        return objArr;
    }

    static {
        MapBufferSoLoader.staticInit();
    }
}
