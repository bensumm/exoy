package com.facebook.react.animated;

import com.facebook.fbreact.specs.NativeAnimatedModuleSpec;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactSoftExceptionLogger;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.bridge.UIManagerListener;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.GuardedFrameCallback;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.common.ViewUtil;
import com.reactcommunity.rndatetimepicker.RNConstants;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.UByte$$ExternalSyntheticBackport0;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = NativeAnimatedModuleSpec.NAME)
public class NativeAnimatedModule extends NativeAnimatedModuleSpec implements LifecycleEventListener, UIManagerListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final boolean ANIMATED_MODULE_DEBUG = false;
    private final GuardedFrameCallback mAnimatedFrameCallback;
    private boolean mBatchingControlledByJS;
    private volatile long mCurrentBatchNumber;
    private volatile long mCurrentFrameNumber;
    private boolean mInitializedForFabric;
    private boolean mInitializedForNonFabric;
    private final AtomicReference<NativeAnimatedNodesManager> mNodesManager;
    private int mNumFabricAnimations;
    private int mNumNonFabricAnimations;
    private final ConcurrentOperationQueue mOperations;
    private final ConcurrentOperationQueue mPreOperations;
    private final ReactChoreographer mReactChoreographer;
    private int mUIManagerType;

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void addListener(String str) {
    }

    @Override // com.facebook.react.bridge.UIManagerListener
    public void didMountItems(UIManager uIManager) {
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void removeListeners(double d) {
    }

    @Override // com.facebook.react.bridge.UIManagerListener
    public void willMountItems(UIManager uIManager) {
    }

    private enum BatchExecutionOpCodes {
        OP_CODE_CREATE_ANIMATED_NODE(1),
        OP_CODE_UPDATE_ANIMATED_NODE_CONFIG(2),
        OP_CODE_GET_VALUE(3),
        OP_START_LISTENING_TO_ANIMATED_NODE_VALUE(4),
        OP_STOP_LISTENING_TO_ANIMATED_NODE_VALUE(5),
        OP_CODE_CONNECT_ANIMATED_NODES(6),
        OP_CODE_DISCONNECT_ANIMATED_NODES(7),
        OP_CODE_START_ANIMATING_NODE(8),
        OP_CODE_STOP_ANIMATION(9),
        OP_CODE_SET_ANIMATED_NODE_VALUE(10),
        OP_CODE_SET_ANIMATED_NODE_OFFSET(11),
        OP_CODE_FLATTEN_ANIMATED_NODE_OFFSET(12),
        OP_CODE_EXTRACT_ANIMATED_NODE_OFFSET(13),
        OP_CODE_CONNECT_ANIMATED_NODE_TO_VIEW(14),
        OP_CODE_DISCONNECT_ANIMATED_NODE_FROM_VIEW(15),
        OP_CODE_RESTORE_DEFAULT_VALUES(16),
        OP_CODE_DROP_ANIMATED_NODE(17),
        OP_CODE_ADD_ANIMATED_EVENT_TO_VIEW(18),
        OP_CODE_REMOVE_ANIMATED_EVENT_FROM_VIEW(19),
        OP_CODE_ADD_LISTENER(20),
        OP_CODE_REMOVE_LISTENERS(21);

        private static BatchExecutionOpCodes[] valueMap = null;
        private final int value;

        BatchExecutionOpCodes(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        public static BatchExecutionOpCodes fromId(int i) {
            if (valueMap == null) {
                valueMap = values();
            }
            return valueMap[i - 1];
        }
    }

    private abstract class UIThreadOperation {
        long mBatchNumber;

        abstract void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager);

        private UIThreadOperation() {
            this.mBatchNumber = -1L;
        }

        public void setBatchNumber(long j) {
            this.mBatchNumber = j;
        }

        public long getBatchNumber() {
            return this.mBatchNumber;
        }
    }

    private class ConcurrentOperationQueue {
        private UIThreadOperation mPeekedOperation;
        private final Queue<UIThreadOperation> mQueue;

        private ConcurrentOperationQueue() {
            this.mQueue = new ConcurrentLinkedQueue();
            this.mPeekedOperation = null;
        }

        boolean isEmpty() {
            return this.mQueue.isEmpty() && this.mPeekedOperation == null;
        }

        void add(UIThreadOperation uIThreadOperation) {
            this.mQueue.add(uIThreadOperation);
        }

        void executeBatch(long j, NativeAnimatedNodesManager nativeAnimatedNodesManager) {
            List<UIThreadOperation> listDrainQueueIntoList = drainQueueIntoList(j);
            if (listDrainQueueIntoList != null) {
                Iterator<UIThreadOperation> it = listDrainQueueIntoList.iterator();
                while (it.hasNext()) {
                    it.next().execute(nativeAnimatedNodesManager);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002c  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0036 A[EDGE_INSN: B:22:0x0036->B:18:0x0036 BREAK  A[LOOP:0: B:6:0x000d->B:19:0x0037], SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.util.List<com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation> drainQueueIntoList(long r6) {
            /*
                r5 = this;
                boolean r0 = r5.isEmpty()
                r1 = 0
                if (r0 == 0) goto L8
                return r1
            L8:
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
            Ld:
                com.facebook.react.animated.NativeAnimatedModule$UIThreadOperation r2 = r5.mPeekedOperation
                if (r2 == 0) goto L21
                long r2 = r2.getBatchNumber()
                int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r2 <= 0) goto L1a
                goto L36
            L1a:
                com.facebook.react.animated.NativeAnimatedModule$UIThreadOperation r2 = r5.mPeekedOperation
                r0.add(r2)
                r5.mPeekedOperation = r1
            L21:
                java.util.Queue<com.facebook.react.animated.NativeAnimatedModule$UIThreadOperation> r2 = r5.mQueue
                java.lang.Object r2 = r2.poll()
                com.facebook.react.animated.NativeAnimatedModule$UIThreadOperation r2 = (com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation) r2
                if (r2 != 0) goto L2c
                goto L36
            L2c:
                long r3 = r2.getBatchNumber()
                int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
                if (r3 <= 0) goto L37
                r5.mPeekedOperation = r2
            L36:
                return r0
            L37:
                r0.add(r2)
                goto Ld
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.animated.NativeAnimatedModule.ConcurrentOperationQueue.drainQueueIntoList(long):java.util.List");
        }
    }

    public NativeAnimatedModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mOperations = new ConcurrentOperationQueue();
        this.mPreOperations = new ConcurrentOperationQueue();
        this.mNodesManager = new AtomicReference<>();
        this.mBatchingControlledByJS = false;
        this.mInitializedForFabric = false;
        this.mInitializedForNonFabric = false;
        this.mUIManagerType = 1;
        this.mNumFabricAnimations = 0;
        this.mNumNonFabricAnimations = 0;
        this.mReactChoreographer = ReactChoreographer.getInstance();
        this.mAnimatedFrameCallback = new GuardedFrameCallback(reactApplicationContext) { // from class: com.facebook.react.animated.NativeAnimatedModule.1
            @Override // com.facebook.react.uimanager.GuardedFrameCallback
            protected void doFrameGuarded(long j) {
                try {
                    NativeAnimatedNodesManager nodesManager = NativeAnimatedModule.this.getNodesManager();
                    if (nodesManager != null && nodesManager.hasActiveAnimations()) {
                        nodesManager.runUpdates(j);
                    }
                    if (nodesManager == null && NativeAnimatedModule.this.mReactChoreographer == null) {
                        return;
                    }
                    ((ReactChoreographer) Assertions.assertNotNull(NativeAnimatedModule.this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, NativeAnimatedModule.this.mAnimatedFrameCallback);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void initialize() {
        super.initialize();
        getReactApplicationContext().addLifecycleEventListener(this);
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        enqueueFrameCallback();
    }

    private void addOperation(UIThreadOperation uIThreadOperation) {
        uIThreadOperation.setBatchNumber(this.mCurrentBatchNumber);
        this.mOperations.add(uIThreadOperation);
    }

    private void addUnbatchedOperation(UIThreadOperation uIThreadOperation) {
        uIThreadOperation.setBatchNumber(-1L);
        this.mOperations.add(uIThreadOperation);
    }

    private void addPreOperation(UIThreadOperation uIThreadOperation) {
        uIThreadOperation.setBatchNumber(this.mCurrentBatchNumber);
        this.mPreOperations.add(uIThreadOperation);
    }

    @Override // com.facebook.react.bridge.UIManagerListener
    public void didScheduleMountItems(UIManager uIManager) {
        this.mCurrentFrameNumber++;
    }

    @Override // com.facebook.react.bridge.UIManagerListener
    public void didDispatchMountItems(UIManager uIManager) {
        if (this.mUIManagerType != 2) {
            return;
        }
        long j = this.mCurrentBatchNumber - 1;
        if (!this.mBatchingControlledByJS) {
            this.mCurrentFrameNumber++;
            if (this.mCurrentFrameNumber - this.mCurrentBatchNumber > 2) {
                this.mCurrentBatchNumber = this.mCurrentFrameNumber;
                j = this.mCurrentBatchNumber;
            }
        }
        this.mPreOperations.executeBatch(j, getNodesManager());
        this.mOperations.executeBatch(j, getNodesManager());
    }

    @Override // com.facebook.react.bridge.UIManagerListener
    public void willDispatchViewUpdates(UIManager uIManager) {
        if ((this.mOperations.isEmpty() && this.mPreOperations.isEmpty()) || this.mUIManagerType == 2) {
            return;
        }
        final long j = this.mCurrentBatchNumber;
        this.mCurrentBatchNumber = 1 + j;
        UIBlock uIBlock = new UIBlock() { // from class: com.facebook.react.animated.NativeAnimatedModule.2
            @Override // com.facebook.react.uimanager.UIBlock
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                NativeAnimatedModule.this.mPreOperations.executeBatch(j, NativeAnimatedModule.this.getNodesManager());
            }
        };
        UIBlock uIBlock2 = new UIBlock() { // from class: com.facebook.react.animated.NativeAnimatedModule.3
            @Override // com.facebook.react.uimanager.UIBlock
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                NativeAnimatedModule.this.mOperations.executeBatch(j, NativeAnimatedModule.this.getNodesManager());
            }
        };
        UIManagerModule uIManagerModule = (UIManagerModule) uIManager;
        uIManagerModule.prependUIBlock(uIBlock);
        uIManagerModule.addUIBlock(uIBlock2);
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        clearFrameCallback();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        clearFrameCallback();
    }

    public NativeAnimatedNodesManager getNodesManager() {
        ReactApplicationContext reactApplicationContextIfActiveOrWarn;
        if (this.mNodesManager.get() == null && (reactApplicationContextIfActiveOrWarn = getReactApplicationContextIfActiveOrWarn()) != null) {
            UByte$$ExternalSyntheticBackport0.m(this.mNodesManager, null, new NativeAnimatedNodesManager(reactApplicationContextIfActiveOrWarn));
        }
        return this.mNodesManager.get();
    }

    private void clearFrameCallback() {
        ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).removeFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
    }

    private void enqueueFrameCallback() {
        ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
    }

    public void setNodesManager(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        this.mNodesManager.set(nativeAnimatedNodesManager);
    }

    private void initializeLifecycleEventListenersForViewTag(int i) {
        UIManager uIManager;
        int uIManagerType = ViewUtil.getUIManagerType(i);
        this.mUIManagerType = uIManagerType;
        if (uIManagerType == 2) {
            this.mNumFabricAnimations++;
        } else {
            this.mNumNonFabricAnimations++;
        }
        NativeAnimatedNodesManager nodesManager = getNodesManager();
        if (nodesManager != null) {
            nodesManager.initializeEventListenerForUIManagerType(this.mUIManagerType);
        } else {
            ReactSoftExceptionLogger.logSoftException(NativeAnimatedModuleSpec.NAME, new RuntimeException("initializeLifecycleEventListenersForViewTag could not get NativeAnimatedNodesManager"));
        }
        if (this.mUIManagerType == 2) {
            if (this.mInitializedForFabric) {
                return;
            }
        } else if (this.mInitializedForNonFabric) {
            return;
        }
        ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        if (reactApplicationContext == null || (uIManager = UIManagerHelper.getUIManager(reactApplicationContext, this.mUIManagerType)) == null) {
            return;
        }
        uIManager.addUIManagerEventListener(this);
        if (this.mUIManagerType == 2) {
            this.mInitializedForFabric = true;
        } else {
            this.mInitializedForNonFabric = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decrementInFlightAnimationsForViewTag(int i) {
        if (ViewUtil.getUIManagerType(i) == 2) {
            this.mNumFabricAnimations--;
        } else {
            this.mNumNonFabricAnimations--;
        }
        int i2 = this.mNumNonFabricAnimations;
        if (i2 == 0 && this.mNumFabricAnimations > 0 && this.mUIManagerType != 2) {
            this.mUIManagerType = 2;
        } else {
            if (this.mNumFabricAnimations != 0 || i2 <= 0 || this.mUIManagerType == 1) {
                return;
            }
            this.mUIManagerType = 1;
        }
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void startOperationBatch() {
        this.mBatchingControlledByJS = true;
        this.mCurrentBatchNumber++;
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void finishOperationBatch() {
        this.mBatchingControlledByJS = true;
        this.mCurrentBatchNumber++;
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void createAnimatedNode(double d, final ReadableMap readableMap) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.createAnimatedNode(i, readableMap);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void updateAnimatedNodeConfig(double d, final ReadableMap readableMap) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.updateAnimatedNodeConfig(i, readableMap);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void startListeningToAnimatedNodeValue(double d) {
        final int i = (int) d;
        final AnimatedNodeValueListener animatedNodeValueListener = new AnimatedNodeValueListener() { // from class: com.facebook.react.animated.NativeAnimatedModule.6
            @Override // com.facebook.react.animated.AnimatedNodeValueListener
            public void onValueUpdate(double d2) {
                WritableMap writableMapCreateMap = Arguments.createMap();
                writableMapCreateMap.putInt("tag", i);
                writableMapCreateMap.putDouble(RNConstants.ARG_VALUE, d2);
                ReactApplicationContext reactApplicationContextIfActiveOrWarn = NativeAnimatedModule.this.getReactApplicationContextIfActiveOrWarn();
                if (reactApplicationContextIfActiveOrWarn != null) {
                    reactApplicationContextIfActiveOrWarn.emitDeviceEvent("onAnimatedValueUpdate", writableMapCreateMap);
                }
            }
        };
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.startListeningToAnimatedNodeValue(i, animatedNodeValueListener);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void stopListeningToAnimatedNodeValue(double d) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.stopListeningToAnimatedNodeValue(i);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void dropAnimatedNode(double d) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.9
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.dropAnimatedNode(i);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void setAnimatedNodeValue(double d, final double d2) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.10
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.setAnimatedNodeValue(i, d2);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void setAnimatedNodeOffset(double d, final double d2) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.11
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.setAnimatedNodeOffset(i, d2);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void flattenAnimatedNodeOffset(double d) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.12
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.flattenAnimatedNodeOffset(i);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void extractAnimatedNodeOffset(double d) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.13
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.extractAnimatedNodeOffset(i);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void startAnimatingNode(double d, double d2, final ReadableMap readableMap, final Callback callback) {
        final int i = (int) d;
        final int i2 = (int) d2;
        addUnbatchedOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.14
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.startAnimatingNode(i, i2, readableMap, callback);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void stopAnimation(double d) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.15
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.stopAnimation(i);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void connectAnimatedNodes(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.16
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.connectAnimatedNodes(i, i2);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void disconnectAnimatedNodes(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.17
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.disconnectAnimatedNodes(i, i2);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void connectAnimatedNodeToView(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        initializeLifecycleEventListenersForViewTag(i2);
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.18
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.connectAnimatedNodeToView(i, i2);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void disconnectAnimatedNodeFromView(double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        decrementInFlightAnimationsForViewTag(i2);
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.19
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.disconnectAnimatedNodeFromView(i, i2);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void restoreDefaultValues(double d) {
        final int i = (int) d;
        addPreOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.20
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.restoreDefaultValues(i);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void addAnimatedEventToView(double d, final String str, final ReadableMap readableMap) {
        final int i = (int) d;
        initializeLifecycleEventListenersForViewTag(i);
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.21
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.addAnimatedEventToView(i, str, readableMap);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void removeAnimatedEventFromView(double d, final String str, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        decrementInFlightAnimationsForViewTag(i);
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.22
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.removeAnimatedEventFromView(i, str, i2);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void getValue(double d, final Callback callback) {
        final int i = (int) d;
        addOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.23
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                nativeAnimatedNodesManager.getValue(i, callback);
            }
        });
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void invalidate() {
        super.invalidate();
        getReactApplicationContext().removeLifecycleEventListener(this);
    }

    @Override // com.facebook.fbreact.specs.NativeAnimatedModuleSpec
    public void queueAndExecuteBatchedOperations(final ReadableArray readableArray) {
        int i;
        final int size = readableArray.size();
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2 + 1;
            switch (AnonymousClass25.$SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.fromId(readableArray.getInt(i2)).ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    i = i3 + 1;
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    i = i3 + 2;
                    break;
                case 18:
                case 19:
                    i = i3 + 3;
                    break;
                case 20:
                    int i4 = i3 + 1;
                    i2 = i4 + 1;
                    initializeLifecycleEventListenersForViewTag(readableArray.getInt(i4));
                    continue;
                case 21:
                    initializeLifecycleEventListenersForViewTag(readableArray.getInt(i3));
                    i2 = i3 + 1 + 1 + 1;
                    continue;
                default:
                    throw new IllegalArgumentException("Batch animation execution op: fetching viewTag: unknown op code");
            }
            i2 = i;
        }
        startOperationBatch();
        addUnbatchedOperation(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.24
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager) {
                int i5;
                int i6;
                int i7;
                NativeAnimatedModule.this.getReactApplicationContextIfActiveOrWarn();
                int i8 = 0;
                while (i8 < size) {
                    int i9 = i8 + 1;
                    switch (AnonymousClass25.$SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.fromId(readableArray.getInt(i8)).ordinal()]) {
                        case 1:
                            i6 = i9 + 1;
                            nativeAnimatedNodesManager.getValue(readableArray.getInt(i9), null);
                            i8 = i6;
                            break;
                        case 2:
                            i7 = i9 + 1;
                            final int i10 = readableArray.getInt(i9);
                            nativeAnimatedNodesManager.startListeningToAnimatedNodeValue(i10, new AnimatedNodeValueListener() { // from class: com.facebook.react.animated.NativeAnimatedModule.24.1
                                @Override // com.facebook.react.animated.AnimatedNodeValueListener
                                public void onValueUpdate(double d) {
                                    WritableMap writableMapCreateMap = Arguments.createMap();
                                    writableMapCreateMap.putInt("tag", i10);
                                    writableMapCreateMap.putDouble(RNConstants.ARG_VALUE, d);
                                    ReactApplicationContext reactApplicationContextIfActiveOrWarn = NativeAnimatedModule.this.getReactApplicationContextIfActiveOrWarn();
                                    if (reactApplicationContextIfActiveOrWarn != null) {
                                        reactApplicationContextIfActiveOrWarn.emitDeviceEvent("onAnimatedValueUpdate", writableMapCreateMap);
                                    }
                                }
                            });
                            i8 = i7;
                            break;
                        case 3:
                            i7 = i9 + 1;
                            nativeAnimatedNodesManager.stopListeningToAnimatedNodeValue(readableArray.getInt(i9));
                            i8 = i7;
                            break;
                        case 4:
                            i7 = i9 + 1;
                            nativeAnimatedNodesManager.stopAnimation(readableArray.getInt(i9));
                            i8 = i7;
                            break;
                        case 5:
                            i7 = i9 + 1;
                            nativeAnimatedNodesManager.flattenAnimatedNodeOffset(readableArray.getInt(i9));
                            i8 = i7;
                            break;
                        case 6:
                            i7 = i9 + 1;
                            nativeAnimatedNodesManager.extractAnimatedNodeOffset(readableArray.getInt(i9));
                            i8 = i7;
                            break;
                        case 7:
                            i7 = i9 + 1;
                            nativeAnimatedNodesManager.restoreDefaultValues(readableArray.getInt(i9));
                            i8 = i7;
                            break;
                        case 8:
                            i7 = i9 + 1;
                            nativeAnimatedNodesManager.dropAnimatedNode(readableArray.getInt(i9));
                            i8 = i7;
                            break;
                        case 9:
                        case 10:
                            i8 = i9 + 1;
                            break;
                        case 11:
                            int i11 = i9 + 1;
                            i6 = i11 + 1;
                            nativeAnimatedNodesManager.createAnimatedNode(readableArray.getInt(i9), readableArray.getMap(i11));
                            i8 = i6;
                            break;
                        case 12:
                            int i12 = i9 + 1;
                            i6 = i12 + 1;
                            nativeAnimatedNodesManager.updateAnimatedNodeConfig(readableArray.getInt(i9), readableArray.getMap(i12));
                            i8 = i6;
                            break;
                        case 13:
                            int i13 = i9 + 1;
                            i6 = i13 + 1;
                            nativeAnimatedNodesManager.connectAnimatedNodes(readableArray.getInt(i9), readableArray.getInt(i13));
                            i8 = i6;
                            break;
                        case 14:
                            int i14 = i9 + 1;
                            i6 = i14 + 1;
                            nativeAnimatedNodesManager.disconnectAnimatedNodes(readableArray.getInt(i9), readableArray.getInt(i14));
                            i8 = i6;
                            break;
                        case 15:
                            int i15 = i9 + 1;
                            i6 = i15 + 1;
                            nativeAnimatedNodesManager.setAnimatedNodeValue(readableArray.getInt(i9), readableArray.getDouble(i15));
                            i8 = i6;
                            break;
                        case 16:
                            int i16 = i9 + 1;
                            i6 = i16 + 1;
                            nativeAnimatedNodesManager.setAnimatedNodeValue(readableArray.getInt(i9), readableArray.getDouble(i16));
                            i8 = i6;
                            break;
                        case 17:
                            int i17 = i9 + 1;
                            int i18 = readableArray.getInt(i9);
                            i6 = i17 + 1;
                            int i19 = readableArray.getInt(i17);
                            NativeAnimatedModule.this.decrementInFlightAnimationsForViewTag(i19);
                            nativeAnimatedNodesManager.disconnectAnimatedNodeFromView(i18, i19);
                            i8 = i6;
                            break;
                        case 18:
                            int i20 = i9 + 1;
                            int i21 = i20 + 1;
                            nativeAnimatedNodesManager.startAnimatingNode(readableArray.getInt(i9), readableArray.getInt(i20), readableArray.getMap(i21), null);
                            i8 = i21 + 1;
                            break;
                        case 19:
                            int i22 = i9 + 1;
                            int i23 = readableArray.getInt(i9);
                            NativeAnimatedModule.this.decrementInFlightAnimationsForViewTag(i23);
                            int i24 = i22 + 1;
                            i5 = i24 + 1;
                            nativeAnimatedNodesManager.removeAnimatedEventFromView(i23, readableArray.getString(i22), readableArray.getInt(i24));
                            i8 = i5;
                            break;
                        case 20:
                            int i25 = i9 + 1;
                            i6 = i25 + 1;
                            nativeAnimatedNodesManager.connectAnimatedNodeToView(readableArray.getInt(i9), readableArray.getInt(i25));
                            i8 = i6;
                            break;
                        case 21:
                            int i26 = i9 + 1;
                            int i27 = i26 + 1;
                            i5 = i27 + 1;
                            nativeAnimatedNodesManager.addAnimatedEventToView(readableArray.getInt(i9), readableArray.getString(i26), readableArray.getMap(i27));
                            i8 = i5;
                            break;
                        default:
                            throw new IllegalArgumentException("Batch animation execution op: unknown op code");
                    }
                }
            }
        });
        finishOperationBatch();
    }

    /* JADX INFO: renamed from: com.facebook.react.animated.NativeAnimatedModule$25, reason: invalid class name */
    static /* synthetic */ class AnonymousClass25 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes;

        static {
            int[] iArr = new int[BatchExecutionOpCodes.values().length];
            $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes = iArr;
            try {
                iArr[BatchExecutionOpCodes.OP_CODE_GET_VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_START_LISTENING_TO_ANIMATED_NODE_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_STOP_LISTENING_TO_ANIMATED_NODE_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_STOP_ANIMATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_FLATTEN_ANIMATED_NODE_OFFSET.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_EXTRACT_ANIMATED_NODE_OFFSET.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_RESTORE_DEFAULT_VALUES.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_DROP_ANIMATED_NODE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_ADD_LISTENER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_REMOVE_LISTENERS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_CREATE_ANIMATED_NODE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_UPDATE_ANIMATED_NODE_CONFIG.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_CONNECT_ANIMATED_NODES.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_DISCONNECT_ANIMATED_NODES.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_SET_ANIMATED_NODE_VALUE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_SET_ANIMATED_NODE_OFFSET.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_DISCONNECT_ANIMATED_NODE_FROM_VIEW.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_START_ANIMATING_NODE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_REMOVE_ANIMATED_EVENT_FROM_VIEW.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_CONNECT_ANIMATED_NODE_TO_VIEW.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$NativeAnimatedModule$BatchExecutionOpCodes[BatchExecutionOpCodes.OP_CODE_ADD_ANIMATED_EVENT_TO_VIEW.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }
}
