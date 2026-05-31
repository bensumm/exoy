package com.facebook.react.uimanager;

import android.os.SystemClock;
import android.view.View;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import com.facebook.yoga.YogaDirection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class UIImplementation {
    protected final EventDispatcher mEventDispatcher;
    private long mLastCalculateLayoutTime;
    protected LayoutUpdateListener mLayoutUpdateListener;
    private final int[] mMeasureBuffer;
    private final NativeViewHierarchyOptimizer mNativeViewHierarchyOptimizer;
    private final UIViewOperationQueue mOperationsQueue;
    protected final ReactApplicationContext mReactContext;
    protected final ShadowNodeRegistry mShadowNodeRegistry;
    private final ViewManagerRegistry mViewManagers;
    private volatile boolean mViewOperationsEnabled;
    protected Object uiImplementationThreadLock;

    public interface LayoutUpdateListener {
        void onLayoutUpdated(ReactShadowNode reactShadowNode);
    }

    public void onHostDestroy() {
    }

    UIImplementation(ReactApplicationContext reactApplicationContext, ViewManagerRegistry viewManagerRegistry, EventDispatcher eventDispatcher, int i) {
        this(reactApplicationContext, viewManagerRegistry, new UIViewOperationQueue(reactApplicationContext, new NativeViewHierarchyManager(viewManagerRegistry), i), eventDispatcher);
    }

    protected UIImplementation(ReactApplicationContext reactApplicationContext, ViewManagerRegistry viewManagerRegistry, UIViewOperationQueue uIViewOperationQueue, EventDispatcher eventDispatcher) {
        this.uiImplementationThreadLock = new Object();
        ShadowNodeRegistry shadowNodeRegistry = new ShadowNodeRegistry();
        this.mShadowNodeRegistry = shadowNodeRegistry;
        this.mMeasureBuffer = new int[4];
        this.mLastCalculateLayoutTime = 0L;
        this.mViewOperationsEnabled = true;
        this.mReactContext = reactApplicationContext;
        this.mViewManagers = viewManagerRegistry;
        this.mOperationsQueue = uIViewOperationQueue;
        this.mNativeViewHierarchyOptimizer = new NativeViewHierarchyOptimizer(uIViewOperationQueue, shadowNodeRegistry);
        this.mEventDispatcher = eventDispatcher;
    }

    protected ReactShadowNode createRootShadowNode() {
        ReactShadowNodeImpl reactShadowNodeImpl = new ReactShadowNodeImpl();
        if (I18nUtil.getInstance().isRTL(this.mReactContext)) {
            reactShadowNodeImpl.setLayoutDirection(YogaDirection.RTL);
        }
        reactShadowNodeImpl.setViewClassName("Root");
        return reactShadowNodeImpl;
    }

    protected ReactShadowNode createShadowNode(String str) {
        return this.mViewManagers.get(str).createShadowNodeInstance(this.mReactContext);
    }

    public final ReactShadowNode resolveShadowNode(int i) {
        return this.mShadowNodeRegistry.getNode(i);
    }

    protected final ViewManager resolveViewManager(String str) {
        return this.mViewManagers.getViewManagerIfExists(str);
    }

    UIViewOperationQueue getUIViewOperationQueue() {
        return this.mOperationsQueue;
    }

    public void updateRootView(int i, int i2, int i3) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            FLog.w(ReactConstants.TAG, "Tried to update non-existent root tag: " + i);
            return;
        }
        updateRootView(node, i2, i3);
    }

    public void updateRootView(ReactShadowNode reactShadowNode, int i, int i2) {
        reactShadowNode.setMeasureSpecs(i, i2);
    }

    public <T extends View> void registerRootView(T t, int i, ThemedReactContext themedReactContext) {
        synchronized (this.uiImplementationThreadLock) {
            final ReactShadowNode reactShadowNodeCreateRootShadowNode = createRootShadowNode();
            reactShadowNodeCreateRootShadowNode.setReactTag(i);
            reactShadowNodeCreateRootShadowNode.setThemedContext(themedReactContext);
            themedReactContext.runOnNativeModulesQueueThread(new Runnable() { // from class: com.facebook.react.uimanager.UIImplementation.1
                @Override // java.lang.Runnable
                public void run() {
                    UIImplementation.this.mShadowNodeRegistry.addRootNode(reactShadowNodeCreateRootShadowNode);
                }
            });
            this.mOperationsQueue.addRootView(i, t);
        }
    }

    public void removeRootView(int i) {
        removeRootShadowNode(i);
        this.mOperationsQueue.enqueueRemoveRootView(i);
    }

    private int getRootViewNum() {
        return this.mOperationsQueue.getNativeViewHierarchyManager().getRootViewNum();
    }

    public void removeRootShadowNode(int i) {
        synchronized (this.uiImplementationThreadLock) {
            this.mShadowNodeRegistry.removeRootNode(i);
        }
    }

    public void updateNodeSize(int i, int i2, int i3) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            FLog.w(ReactConstants.TAG, "Tried to update size of non-existent tag: " + i);
        } else {
            node.setStyleWidth(i2);
            node.setStyleHeight(i3);
            dispatchViewUpdatesIfNeeded();
        }
    }

    public void setViewLocalData(int i, Object obj) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            FLog.w(ReactConstants.TAG, "Attempt to set local data for view with unknown tag: " + i);
        } else {
            node.setLocalData(obj);
            dispatchViewUpdatesIfNeeded();
        }
    }

    public void profileNextBatch() {
        this.mOperationsQueue.profileNextBatch();
    }

    public Map<String, Long> getProfiledBatchPerfCounters() {
        return this.mOperationsQueue.getProfiledBatchPerfCounters();
    }

    public void createView(int i, String str, int i2, ReadableMap readableMap) {
        ReactStylesDiffMap reactStylesDiffMap;
        if (this.mViewOperationsEnabled) {
            synchronized (this.uiImplementationThreadLock) {
                ReactShadowNode reactShadowNodeCreateShadowNode = createShadowNode(str);
                ReactShadowNode node = this.mShadowNodeRegistry.getNode(i2);
                Assertions.assertNotNull(node, "Root node with tag " + i2 + " doesn't exist");
                reactShadowNodeCreateShadowNode.setReactTag(i);
                reactShadowNodeCreateShadowNode.setViewClassName(str);
                reactShadowNodeCreateShadowNode.setRootTag(node.getReactTag());
                reactShadowNodeCreateShadowNode.setThemedContext(node.getThemedContext());
                this.mShadowNodeRegistry.addNode(reactShadowNodeCreateShadowNode);
                if (readableMap != null) {
                    reactStylesDiffMap = new ReactStylesDiffMap(readableMap);
                    reactShadowNodeCreateShadowNode.updateProperties(reactStylesDiffMap);
                } else {
                    reactStylesDiffMap = null;
                }
                handleCreateView(reactShadowNodeCreateShadowNode, i2, reactStylesDiffMap);
            }
        }
    }

    protected void handleCreateView(ReactShadowNode reactShadowNode, int i, ReactStylesDiffMap reactStylesDiffMap) {
        if (reactShadowNode.isVirtual()) {
            return;
        }
        this.mNativeViewHierarchyOptimizer.handleCreateView(reactShadowNode, reactShadowNode.getThemedContext(), reactStylesDiffMap);
    }

    public void updateView(int i, String str, ReadableMap readableMap) {
        if (this.mViewOperationsEnabled) {
            if (this.mViewManagers.get(str) == null) {
                throw new IllegalViewOperationException("Got unknown view type: " + str);
            }
            ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
            if (node == null) {
                throw new IllegalViewOperationException("Trying to update non-existent view with tag " + i);
            }
            if (readableMap != null) {
                ReactStylesDiffMap reactStylesDiffMap = new ReactStylesDiffMap(readableMap);
                node.updateProperties(reactStylesDiffMap);
                handleUpdateView(node, str, reactStylesDiffMap);
            }
        }
    }

    public void synchronouslyUpdateViewOnUIThread(int i, ReactStylesDiffMap reactStylesDiffMap) {
        UiThreadUtil.assertOnUiThread();
        this.mOperationsQueue.getNativeViewHierarchyManager().updateProperties(i, reactStylesDiffMap);
    }

    protected void handleUpdateView(ReactShadowNode reactShadowNode, String str, ReactStylesDiffMap reactStylesDiffMap) {
        if (reactShadowNode.isVirtual()) {
            return;
        }
        this.mNativeViewHierarchyOptimizer.handleUpdateView(reactShadowNode, str, reactStylesDiffMap);
    }

    public void manageChildren(int i, ReadableArray readableArray, ReadableArray readableArray2, ReadableArray readableArray3, ReadableArray readableArray4, ReadableArray readableArray5) throws Throwable {
        ReactShadowNode node;
        int size;
        int size2;
        int size3;
        int i2;
        ViewAtIndex[] viewAtIndexArr;
        int i3;
        int[] iArr;
        ReadableArray readableArray6 = readableArray;
        if (!this.mViewOperationsEnabled) {
            return;
        }
        synchronized (this.uiImplementationThreadLock) {
            try {
                node = this.mShadowNodeRegistry.getNode(i);
                size = readableArray6 == null ? 0 : readableArray.size();
                size2 = readableArray3 == null ? 0 : readableArray3.size();
                size3 = readableArray5 == null ? 0 : readableArray5.size();
                if (size != 0 && (readableArray2 == null || size != readableArray2.size())) {
                    throw new IllegalViewOperationException("Size of moveFrom != size of moveTo!");
                }
                if (size2 != 0 && (readableArray4 == null || size2 != readableArray4.size())) {
                    throw new IllegalViewOperationException("Size of addChildTags != size of addAtIndices!");
                }
                i2 = size + size2;
                viewAtIndexArr = new ViewAtIndex[i2];
                i3 = size + size3;
                iArr = new int[i3];
            } catch (Throwable th) {
                th = th;
            }
            try {
                int[] iArr2 = new int[i3];
                int i4 = i2;
                int[] iArr3 = new int[size3];
                if (size > 0) {
                    Assertions.assertNotNull(readableArray);
                    Assertions.assertNotNull(readableArray2);
                    int i5 = 0;
                    while (i5 < size) {
                        int i6 = i3;
                        int i7 = readableArray6.getInt(i5);
                        int reactTag = node.getChildAt(i7).getReactTag();
                        viewAtIndexArr[i5] = new ViewAtIndex(reactTag, readableArray2.getInt(i5));
                        iArr[i5] = i7;
                        iArr2[i5] = reactTag;
                        i5++;
                        readableArray6 = readableArray;
                        i3 = i6;
                        iArr3 = iArr3;
                        node = node;
                    }
                }
                ReactShadowNode reactShadowNode = node;
                int[] iArr4 = iArr3;
                int i8 = i3;
                if (size2 > 0) {
                    Assertions.assertNotNull(readableArray3);
                    Assertions.assertNotNull(readableArray4);
                    for (int i9 = 0; i9 < size2; i9++) {
                        viewAtIndexArr[size + i9] = new ViewAtIndex(readableArray3.getInt(i9), readableArray4.getInt(i9));
                    }
                }
                if (size3 > 0) {
                    Assertions.assertNotNull(readableArray5);
                    int i10 = 0;
                    while (i10 < size3) {
                        int i11 = readableArray5.getInt(i10);
                        ReactShadowNode reactShadowNode2 = reactShadowNode;
                        int reactTag2 = reactShadowNode2.getChildAt(i11).getReactTag();
                        int i12 = size + i10;
                        iArr[i12] = i11;
                        iArr2[i12] = reactTag2;
                        iArr4[i10] = reactTag2;
                        i10++;
                        reactShadowNode = reactShadowNode2;
                    }
                }
                ReactShadowNode reactShadowNode3 = reactShadowNode;
                Arrays.sort(viewAtIndexArr, ViewAtIndex.COMPARATOR);
                Arrays.sort(iArr);
                int i13 = -1;
                for (int i14 = i8 - 1; i14 >= 0; i14--) {
                    int i15 = iArr[i14];
                    if (i15 == i13) {
                        throw new IllegalViewOperationException("Repeated indices in Removal list for view tag: " + i);
                    }
                    reactShadowNode3.removeChildAt(i15);
                    i13 = iArr[i14];
                }
                int i16 = 0;
                while (true) {
                    int i17 = i4;
                    if (i16 < i17) {
                        ViewAtIndex viewAtIndex = viewAtIndexArr[i16];
                        int[] iArr5 = iArr2;
                        ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(viewAtIndex.mTag);
                        if (node2 == null) {
                            throw new IllegalViewOperationException("Trying to add unknown view tag: " + viewAtIndex.mTag);
                        }
                        reactShadowNode3.addChildAt(node2, viewAtIndex.mIndex);
                        i16++;
                        iArr2 = iArr5;
                        i4 = i17;
                    } else {
                        this.mNativeViewHierarchyOptimizer.handleManageChildren(reactShadowNode3, iArr, iArr2, viewAtIndexArr, iArr4);
                        for (int i18 = 0; i18 < size3; i18++) {
                            removeShadowNode(this.mShadowNodeRegistry.getNode(iArr4[i18]));
                        }
                        return;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void setChildren(int i, ReadableArray readableArray) {
        if (this.mViewOperationsEnabled) {
            synchronized (this.uiImplementationThreadLock) {
                ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
                for (int i2 = 0; i2 < readableArray.size(); i2++) {
                    ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(readableArray.getInt(i2));
                    if (node2 == null) {
                        throw new IllegalViewOperationException("Trying to add unknown view tag: " + readableArray.getInt(i2));
                    }
                    node.addChildAt(node2, i2);
                }
                this.mNativeViewHierarchyOptimizer.handleSetChildren(node, readableArray);
            }
        }
    }

    public void replaceExistingNonRootView(int i, int i2) throws Throwable {
        if (this.mShadowNodeRegistry.isRootNode(i) || this.mShadowNodeRegistry.isRootNode(i2)) {
            throw new IllegalViewOperationException("Trying to add or replace a root tag!");
        }
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            throw new IllegalViewOperationException("Trying to replace unknown view tag: " + i);
        }
        ReactShadowNode parent = node.getParent();
        if (parent == null) {
            throw new IllegalViewOperationException("Node is not attached to a parent: " + i);
        }
        int iIndexOf = parent.indexOf(node);
        if (iIndexOf < 0) {
            throw new IllegalStateException("Didn't find child tag in parent");
        }
        WritableArray writableArrayCreateArray = Arguments.createArray();
        writableArrayCreateArray.pushInt(i2);
        WritableArray writableArrayCreateArray2 = Arguments.createArray();
        writableArrayCreateArray2.pushInt(iIndexOf);
        WritableArray writableArrayCreateArray3 = Arguments.createArray();
        writableArrayCreateArray3.pushInt(iIndexOf);
        manageChildren(parent.getReactTag(), null, null, writableArrayCreateArray, writableArrayCreateArray2, writableArrayCreateArray3);
    }

    public void removeSubviewsFromContainerWithID(int i) throws Throwable {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            throw new IllegalViewOperationException("Trying to remove subviews of an unknown view tag: " + i);
        }
        WritableArray writableArrayCreateArray = Arguments.createArray();
        for (int i2 = 0; i2 < node.getChildCount(); i2++) {
            writableArrayCreateArray.pushInt(i2);
        }
        manageChildren(i, null, null, null, null, writableArrayCreateArray);
    }

    public void findSubviewIn(int i, float f, float f2, Callback callback) {
        this.mOperationsQueue.enqueueFindTargetForTouch(i, f, f2, callback);
    }

    @Deprecated
    public void viewIsDescendantOf(int i, int i2, Callback callback) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(i2);
        if (node == null || node2 == null) {
            callback.invoke(false);
        } else {
            callback.invoke(Boolean.valueOf(node.isDescendantOf(node2)));
        }
    }

    public void measure(int i, Callback callback) {
        if (this.mViewOperationsEnabled) {
            this.mOperationsQueue.enqueueMeasure(i, callback);
        }
    }

    public void measureInWindow(int i, Callback callback) {
        if (this.mViewOperationsEnabled) {
            this.mOperationsQueue.enqueueMeasureInWindow(i, callback);
        }
    }

    public void measureLayout(int i, int i2, Callback callback, Callback callback2) {
        if (this.mViewOperationsEnabled) {
            try {
                measureLayout(i, i2, this.mMeasureBuffer);
                callback2.invoke(Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[0])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[1])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[2])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[3])));
            } catch (IllegalViewOperationException e) {
                callback.invoke(e.getMessage());
            }
        }
    }

    public void measureLayoutRelativeToParent(int i, Callback callback, Callback callback2) {
        if (this.mViewOperationsEnabled) {
            try {
                measureLayoutRelativeToParent(i, this.mMeasureBuffer);
                callback2.invoke(Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[0])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[1])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[2])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[3])));
            } catch (IllegalViewOperationException e) {
                callback.invoke(e.getMessage());
            }
        }
    }

    public void dispatchViewUpdates(int i) {
        if (getRootViewNum() <= 0) {
            return;
        }
        SystraceMessage.beginSection(0L, "UIImplementation.dispatchViewUpdates").arg("batchId", i).flush();
        long jUptimeMillis = SystemClock.uptimeMillis();
        try {
            updateViewHierarchy();
            this.mNativeViewHierarchyOptimizer.onBatchComplete();
            this.mOperationsQueue.dispatchViewUpdates(i, jUptimeMillis, this.mLastCalculateLayoutTime);
        } finally {
            Systrace.endSection(0L);
        }
    }

    private void dispatchViewUpdatesIfNeeded() {
        if (this.mOperationsQueue.isEmpty()) {
            dispatchViewUpdates(-1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0077, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007b, code lost:
    
        throw r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void updateViewHierarchy() {
        /*
            r7 = this;
            java.lang.String r0 = "rootTag"
            java.lang.String r1 = "UIImplementation.updateViewHierarchy"
            r2 = 0
            com.facebook.systrace.Systrace.beginSection(r2, r1)
            r1 = 0
        La:
            com.facebook.react.uimanager.ShadowNodeRegistry r4 = r7.mShadowNodeRegistry     // Catch: java.lang.Throwable -> L77
            int r4 = r4.getRootNodeCount()     // Catch: java.lang.Throwable -> L77
            if (r1 >= r4) goto L73
            com.facebook.react.uimanager.ShadowNodeRegistry r4 = r7.mShadowNodeRegistry     // Catch: java.lang.Throwable -> L77
            int r4 = r4.getRootTag(r1)     // Catch: java.lang.Throwable -> L77
            com.facebook.react.uimanager.ShadowNodeRegistry r5 = r7.mShadowNodeRegistry     // Catch: java.lang.Throwable -> L77
            com.facebook.react.uimanager.ReactShadowNode r4 = r5.getNode(r4)     // Catch: java.lang.Throwable -> L77
            java.lang.Integer r5 = r4.getWidthMeasureSpec()     // Catch: java.lang.Throwable -> L77
            if (r5 == 0) goto L70
            java.lang.Integer r5 = r4.getHeightMeasureSpec()     // Catch: java.lang.Throwable -> L77
            if (r5 == 0) goto L70
            java.lang.String r5 = "UIImplementation.notifyOnBeforeLayoutRecursive"
            com.facebook.systrace.SystraceMessage$Builder r5 = com.facebook.systrace.SystraceMessage.beginSection(r2, r5)     // Catch: java.lang.Throwable -> L77
            int r6 = r4.getReactTag()     // Catch: java.lang.Throwable -> L77
            com.facebook.systrace.SystraceMessage$Builder r5 = r5.arg(r0, r6)     // Catch: java.lang.Throwable -> L77
            r5.flush()     // Catch: java.lang.Throwable -> L77
            r7.notifyOnBeforeLayoutRecursive(r4)     // Catch: java.lang.Throwable -> L6b
            com.facebook.systrace.Systrace.endSection(r2)     // Catch: java.lang.Throwable -> L77
            r7.calculateRootLayout(r4)     // Catch: java.lang.Throwable -> L77
            java.lang.String r5 = "UIImplementation.applyUpdatesRecursive"
            com.facebook.systrace.SystraceMessage$Builder r5 = com.facebook.systrace.SystraceMessage.beginSection(r2, r5)     // Catch: java.lang.Throwable -> L77
            int r6 = r4.getReactTag()     // Catch: java.lang.Throwable -> L77
            com.facebook.systrace.SystraceMessage$Builder r5 = r5.arg(r0, r6)     // Catch: java.lang.Throwable -> L77
            r5.flush()     // Catch: java.lang.Throwable -> L77
            r5 = 0
            r7.applyUpdatesRecursive(r4, r5, r5)     // Catch: java.lang.Throwable -> L66
            com.facebook.systrace.Systrace.endSection(r2)     // Catch: java.lang.Throwable -> L77
            com.facebook.react.uimanager.UIImplementation$LayoutUpdateListener r5 = r7.mLayoutUpdateListener     // Catch: java.lang.Throwable -> L77
            if (r5 == 0) goto L70
            com.facebook.react.uimanager.UIViewOperationQueue r6 = r7.mOperationsQueue     // Catch: java.lang.Throwable -> L77
            r6.enqueueLayoutUpdateFinished(r4, r5)     // Catch: java.lang.Throwable -> L77
            goto L70
        L66:
            r0 = move-exception
            com.facebook.systrace.Systrace.endSection(r2)     // Catch: java.lang.Throwable -> L77
            throw r0     // Catch: java.lang.Throwable -> L77
        L6b:
            r0 = move-exception
            com.facebook.systrace.Systrace.endSection(r2)     // Catch: java.lang.Throwable -> L77
            throw r0     // Catch: java.lang.Throwable -> L77
        L70:
            int r1 = r1 + 1
            goto La
        L73:
            com.facebook.systrace.Systrace.endSection(r2)
            return
        L77:
            r0 = move-exception
            com.facebook.systrace.Systrace.endSection(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.UIImplementation.updateViewHierarchy():void");
    }

    public void setLayoutAnimationEnabledExperimental(boolean z) {
        this.mOperationsQueue.enqueueSetLayoutAnimationEnabled(z);
    }

    public void configureNextLayoutAnimation(ReadableMap readableMap, Callback callback) {
        this.mOperationsQueue.enqueueConfigureLayoutAnimation(readableMap, callback);
    }

    public void setJSResponder(int i, boolean z) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            return;
        }
        while (node.getNativeKind() == NativeKind.NONE) {
            node = node.getParent();
        }
        this.mOperationsQueue.enqueueSetJSResponder(node.getReactTag(), i, z);
    }

    public void clearJSResponder() {
        this.mOperationsQueue.enqueueClearJSResponder();
    }

    @Deprecated
    public void dispatchViewManagerCommand(int i, int i2, ReadableArray readableArray) {
        if (checkOrAssertViewExists(i, "dispatchViewManagerCommand: " + i2)) {
            this.mOperationsQueue.enqueueDispatchCommand(i, i2, readableArray);
        }
    }

    public void dispatchViewManagerCommand(int i, String str, ReadableArray readableArray) {
        if (checkOrAssertViewExists(i, "dispatchViewManagerCommand: " + str)) {
            this.mOperationsQueue.enqueueDispatchCommand(i, str, readableArray);
        }
    }

    public void showPopupMenu(int i, ReadableArray readableArray, Callback callback, Callback callback2) {
        if (checkOrAssertViewExists(i, "showPopupMenu")) {
            this.mOperationsQueue.enqueueShowPopupMenu(i, readableArray, callback, callback2);
        }
    }

    public void dismissPopupMenu() {
        this.mOperationsQueue.enqueueDismissPopupMenu();
    }

    public void sendAccessibilityEvent(int i, int i2) {
        this.mOperationsQueue.enqueueSendAccessibilityEvent(i, i2);
    }

    public void onHostResume() {
        this.mOperationsQueue.resumeFrameCallback();
    }

    public void onHostPause() {
        this.mOperationsQueue.pauseFrameCallback();
    }

    public void onCatalystInstanceDestroyed() {
        this.mViewOperationsEnabled = false;
    }

    public void setViewHierarchyUpdateDebugListener(NotThreadSafeViewHierarchyUpdateDebugListener notThreadSafeViewHierarchyUpdateDebugListener) {
        this.mOperationsQueue.setViewHierarchyUpdateDebugListener(notThreadSafeViewHierarchyUpdateDebugListener);
    }

    protected final void removeShadowNode(ReactShadowNode reactShadowNode) {
        removeShadowNodeRecursive(reactShadowNode);
        reactShadowNode.dispose();
    }

    private void removeShadowNodeRecursive(ReactShadowNode reactShadowNode) {
        NativeViewHierarchyOptimizer.handleRemoveNode(reactShadowNode);
        this.mShadowNodeRegistry.removeNode(reactShadowNode.getReactTag());
        for (int childCount = reactShadowNode.getChildCount() - 1; childCount >= 0; childCount--) {
            removeShadowNodeRecursive(reactShadowNode.getChildAt(childCount));
        }
        reactShadowNode.removeAndDisposeAllChildren();
    }

    private void measureLayout(int i, int i2, int[] iArr) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(i2);
        if (node == null || node2 == null) {
            StringBuilder sb = new StringBuilder("Tag ");
            if (node != null) {
                i = i2;
            }
            sb.append(i);
            sb.append(" does not exist");
            throw new IllegalViewOperationException(sb.toString());
        }
        if (node != node2) {
            for (ReactShadowNode parent = node.getParent(); parent != node2; parent = parent.getParent()) {
                if (parent == null) {
                    throw new IllegalViewOperationException("Tag " + i2 + " is not an ancestor of tag " + i);
                }
            }
        }
        measureLayoutRelativeToVerifiedAncestor(node, node2, iArr);
    }

    private void measureLayoutRelativeToParent(int i, int[] iArr) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            throw new IllegalViewOperationException("No native view for tag " + i + " exists!");
        }
        ReactShadowNode parent = node.getParent();
        if (parent == null) {
            throw new IllegalViewOperationException("View with tag " + i + " doesn't have a parent!");
        }
        measureLayoutRelativeToVerifiedAncestor(node, parent, iArr);
    }

    private void measureLayoutRelativeToVerifiedAncestor(ReactShadowNode reactShadowNode, ReactShadowNode reactShadowNode2, int[] iArr) {
        int iRound;
        int iRound2;
        if (reactShadowNode == reactShadowNode2 || reactShadowNode.isVirtual()) {
            iRound = 0;
            iRound2 = 0;
        } else {
            iRound = Math.round(reactShadowNode.getLayoutX());
            iRound2 = Math.round(reactShadowNode.getLayoutY());
            for (ReactShadowNode parent = reactShadowNode.getParent(); parent != reactShadowNode2; parent = parent.getParent()) {
                Assertions.assertNotNull(parent);
                assertNodeDoesNotNeedCustomLayoutForChildren(parent);
                iRound += Math.round(parent.getLayoutX());
                iRound2 += Math.round(parent.getLayoutY());
            }
            assertNodeDoesNotNeedCustomLayoutForChildren(reactShadowNode2);
        }
        iArr[0] = iRound;
        iArr[1] = iRound2;
        iArr[2] = reactShadowNode.getScreenWidth();
        iArr[3] = reactShadowNode.getScreenHeight();
    }

    private boolean checkOrAssertViewExists(int i, String str) {
        if (this.mShadowNodeRegistry.getNode(i) != null) {
            return true;
        }
        FLog.w(ReactConstants.TAG, "Unable to execute operation " + str + " on view with tag: " + i + ", since the view does not exist");
        return false;
    }

    private void assertNodeDoesNotNeedCustomLayoutForChildren(ReactShadowNode reactShadowNode) {
        NativeModule nativeModule = (ViewManager) Assertions.assertNotNull(this.mViewManagers.get(reactShadowNode.getViewClass()));
        if (nativeModule instanceof IViewManagerWithChildren) {
            IViewManagerWithChildren iViewManagerWithChildren = (IViewManagerWithChildren) nativeModule;
            if (iViewManagerWithChildren == null || !iViewManagerWithChildren.needsCustomLayoutForChildren()) {
                return;
            }
            throw new IllegalViewOperationException("Trying to measure a view using measureLayout/measureLayoutRelativeToParent relative to an ancestor that requires custom layout for it's children (" + reactShadowNode.getViewClass() + "). Use measure instead.");
        }
        throw new IllegalViewOperationException("Trying to use view " + reactShadowNode.getViewClass() + " as a parent, but its Manager doesn't extends ViewGroupManager");
    }

    private void notifyOnBeforeLayoutRecursive(ReactShadowNode reactShadowNode) {
        if (reactShadowNode.hasUpdates()) {
            for (int i = 0; i < reactShadowNode.getChildCount(); i++) {
                notifyOnBeforeLayoutRecursive(reactShadowNode.getChildAt(i));
            }
            reactShadowNode.onBeforeLayout(this.mNativeViewHierarchyOptimizer);
        }
    }

    protected void calculateRootLayout(ReactShadowNode reactShadowNode) {
        SystraceMessage.beginSection(0L, "cssRoot.calculateLayout").arg("rootTag", reactShadowNode.getReactTag()).flush();
        long jUptimeMillis = SystemClock.uptimeMillis();
        try {
            int iIntValue = reactShadowNode.getWidthMeasureSpec().intValue();
            int iIntValue2 = reactShadowNode.getHeightMeasureSpec().intValue();
            float size = Float.NaN;
            float size2 = View.MeasureSpec.getMode(iIntValue) == 0 ? Float.NaN : View.MeasureSpec.getSize(iIntValue);
            if (View.MeasureSpec.getMode(iIntValue2) != 0) {
                size = View.MeasureSpec.getSize(iIntValue2);
            }
            reactShadowNode.calculateLayout(size2, size);
        } finally {
            Systrace.endSection(0L);
            this.mLastCalculateLayoutTime = SystemClock.uptimeMillis() - jUptimeMillis;
        }
    }

    protected void applyUpdatesRecursive(ReactShadowNode reactShadowNode, float f, float f2) {
        if (reactShadowNode.hasUpdates()) {
            Iterable<? extends ReactShadowNode> iterableCalculateLayoutOnChildren = reactShadowNode.calculateLayoutOnChildren();
            if (iterableCalculateLayoutOnChildren != null) {
                Iterator<? extends ReactShadowNode> it = iterableCalculateLayoutOnChildren.iterator();
                while (it.hasNext()) {
                    applyUpdatesRecursive(it.next(), reactShadowNode.getLayoutX() + f, reactShadowNode.getLayoutY() + f2);
                }
            }
            int reactTag = reactShadowNode.getReactTag();
            if (!this.mShadowNodeRegistry.isRootNode(reactTag) && reactShadowNode.dispatchUpdates(f, f2, this.mOperationsQueue, this.mNativeViewHierarchyOptimizer) && reactShadowNode.shouldNotifyOnLayout()) {
                this.mEventDispatcher.dispatchEvent(OnLayoutEvent.obtain(-1, reactTag, reactShadowNode.getScreenX(), reactShadowNode.getScreenY(), reactShadowNode.getScreenWidth(), reactShadowNode.getScreenHeight()));
            }
            reactShadowNode.markUpdateSeen();
            this.mNativeViewHierarchyOptimizer.onViewUpdatesCompleted(reactShadowNode);
        }
    }

    public void addUIBlock(UIBlock uIBlock) {
        this.mOperationsQueue.enqueueUIBlock(uIBlock);
    }

    public void prependUIBlock(UIBlock uIBlock) {
        this.mOperationsQueue.prependUIBlock(uIBlock);
    }

    public int resolveRootTagFromReactTag(int i) {
        if (this.mShadowNodeRegistry.isRootNode(i)) {
            return i;
        }
        ReactShadowNode reactShadowNodeResolveShadowNode = resolveShadowNode(i);
        if (reactShadowNodeResolveShadowNode != null) {
            return reactShadowNodeResolveShadowNode.getRootTag();
        }
        FLog.w(ReactConstants.TAG, "Warning : attempted to resolve a non-existent react shadow node. reactTag=" + i);
        return 0;
    }

    public void setLayoutUpdateListener(LayoutUpdateListener layoutUpdateListener) {
        this.mLayoutUpdateListener = layoutUpdateListener;
    }

    public void removeLayoutUpdateListener() {
        this.mLayoutUpdateListener = null;
    }
}
