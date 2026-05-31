package com.swmansion.reanimated.layoutReanimation;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.views.view.ReactViewGroup;
import com.swmansion.reanimated.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class SharedTransitionManager {
    private final AnimationsManager mAnimationsManager;
    private boolean mIsSharedTransitionActive;
    private NativeMethodsHolder mNativeMethodsHolder;
    private View mTransitionContainer;
    private final List<View> mAddedSharedViews = new ArrayList();
    private final Map<Integer, View> mSharedTransitionParent = new HashMap();
    private final Map<Integer, Integer> mSharedTransitionInParentIndex = new HashMap();
    private final Map<Integer, Snapshot> mSnapshotRegistry = new HashMap();
    private final Map<Integer, View> mCurrentSharedTransitionViews = new HashMap();
    private final List<View> mRemovedSharedViews = new ArrayList();
    private final Set<Integer> mViewTagsToHide = new HashSet();
    private final Map<Integer, Integer> mDisableCleaningForViewTag = new HashMap();
    private List<SharedElement> mSharedElements = new ArrayList();
    private Map<Integer, SharedElement> mSharedElementsLookup = new HashMap();
    private final List<SharedElement> mSharedElementsWithProgress = new ArrayList();
    private final List<SharedElement> mSharedElementsWithAnimation = new ArrayList();
    private final Map<Integer, View> mViewsWithCanceledAnimation = new HashMap();

    interface TreeVisitor {
        void run(View view);
    }

    public SharedTransitionManager(AnimationsManager animationsManager) {
        this.mAnimationsManager = animationsManager;
    }

    protected void notifyAboutNewView(View view) {
        this.mAddedSharedViews.add(view);
    }

    protected void notifyAboutRemovedView(View view) {
        this.mRemovedSharedViews.add(view);
    }

    @Nullable
    protected View getTransitioningView(int i) {
        return this.mCurrentSharedTransitionViews.get(Integer.valueOf(i));
    }

    protected void screenDidLayout() {
        tryStartSharedTransitionForViews(this.mAddedSharedViews, true);
        this.mAddedSharedViews.clear();
    }

    protected void viewDidLayout(View view) {
        maybeRestartAnimationWithNewLayout(view);
    }

    protected void onViewsRemoval(int[] iArr) {
        if (iArr == null) {
            return;
        }
        visitTreeForTags(iArr, new SnapshotTreeVisitor());
        if (this.mRemovedSharedViews.size() > 0) {
            if (!tryStartSharedTransitionForViews(this.mRemovedSharedViews, false)) {
                this.mRemovedSharedViews.clear();
            }
            visitTreeForTags(iArr, new ConfigCleanerTreeVisitor());
            return;
        }
        if (this.mCurrentSharedTransitionViews.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (View view : this.mCurrentSharedTransitionViews.values()) {
                for (int i : iArr) {
                    if (isViewChildParentWithTag(view, i)) {
                        arrayList.add(view);
                    }
                }
            }
            tryStartSharedTransitionForViews(arrayList, false);
            Iterator<View> it = arrayList.iterator();
            while (it.hasNext()) {
                clearAllSharedConfigsForView(it.next());
            }
        }
    }

    private boolean isViewChildParentWithTag(View view, int i) {
        Object parent = this.mSharedTransitionParent.get(Integer.valueOf(view.getId()));
        while (true) {
            View view2 = (View) parent;
            if (view2 != null) {
                if (view2.getId() != i) {
                    if (view2.getClass().getSimpleName().equals("Screen") || !(view2 instanceof View)) {
                        break;
                    }
                    parent = view2.getParent();
                } else {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    protected void doSnapshotForTopScreenViews(ViewGroup viewGroup) {
        if (viewGroup.getChildCount() > 0) {
            View childAt = viewGroup.getChildAt(0);
            if (childAt instanceof ViewGroup) {
                visitNativeTreeAndMakeSnapshot(((ViewGroup) childAt).getChildAt(0));
            } else {
                Log.e("[Reanimated]", "Unable to recognize screen on stack.");
            }
        }
    }

    protected void setNativeMethods(NativeMethodsHolder nativeMethodsHolder) {
        this.mNativeMethodsHolder = nativeMethodsHolder;
    }

    private void maybeRestartAnimationWithNewLayout(View view) {
        View view2 = this.mCurrentSharedTransitionViews.get(Integer.valueOf(view.getId()));
        if (view2 == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (SharedElement sharedElement : this.mSharedElements) {
            if (sharedElement.targetView == view2) {
                arrayList.add(sharedElement);
                View view3 = sharedElement.sourceView;
                View view4 = sharedElement.targetView;
                Snapshot snapshot = new Snapshot(view3);
                Snapshot snapshot2 = this.mSnapshotRegistry.get(Integer.valueOf(view4.getId()));
                Snapshot snapshot3 = new Snapshot(view4);
                int i = (snapshot2.originX - snapshot2.originXByParent) + snapshot3.originX;
                int i2 = (snapshot2.originY - snapshot2.originYByParent) + snapshot3.originY;
                snapshot2.originX = i;
                snapshot2.originY = i2;
                snapshot2.globalOriginX = i;
                snapshot2.globalOriginY = i2;
                snapshot2.originXByParent = snapshot3.originXByParent;
                snapshot2.originYByParent = snapshot3.originYByParent;
                snapshot2.height = snapshot3.height;
                snapshot2.width = snapshot3.width;
                sharedElement.sourceViewSnapshot = snapshot;
                sharedElement.targetViewSnapshot = snapshot2;
                disableCleaningForViewTag(view3.getId());
                disableCleaningForViewTag(view4.getId());
            }
        }
        startSharedTransition(arrayList, 4);
    }

    private boolean tryStartSharedTransitionForViews(List<View> list, boolean z) {
        if (list.isEmpty()) {
            return false;
        }
        sortViewsByTags(list);
        List<SharedElement> sharedElementsForCurrentTransition = getSharedElementsForCurrentTransition(list, z);
        if (sharedElementsForCurrentTransition.isEmpty()) {
            return false;
        }
        setupTransitionContainer();
        reparentSharedViewsForCurrentTransition(sharedElementsForCurrentTransition);
        orderByAnimationTypes(sharedElementsForCurrentTransition);
        startSharedTransition(this.mSharedElementsWithAnimation, 4);
        startSharedTransition(this.mSharedElementsWithProgress, 5);
        return true;
    }

    private void sortViewsByTags(List<View> list) {
        Collections.sort(list, new Comparator() { // from class: com.swmansion.reanimated.layoutReanimation.SharedTransitionManager$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((View) obj2).getId(), ((View) obj).getId());
            }
        });
    }

    private List<SharedElement> getSharedElementsForCurrentTransition(List<View> list, boolean z) {
        View viewResolveView;
        ViewGroup viewGroup;
        ViewGroupManager viewGroupManager;
        int childCount;
        ArrayList<View> arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        if (!z) {
            Iterator<View> it = list.iterator();
            while (it.hasNext()) {
                hashSet.add(Integer.valueOf(it.next().getId()));
            }
        }
        ArrayList<SharedElement> arrayList2 = new ArrayList();
        ReanimatedNativeHierarchyManager reanimatedNativeHierarchyManager = this.mAnimationsManager.getReanimatedNativeHierarchyManager();
        for (View view : list) {
            int iFindPrecedingViewTagForTransition = this.mNativeMethodsHolder.findPrecedingViewTagForTransition(view.getId());
            boolean z2 = !z && hashSet.contains(Integer.valueOf(iFindPrecedingViewTagForTransition));
            if (iFindPrecedingViewTagForTransition >= 0) {
                if (z) {
                    View viewResolveView2 = reanimatedNativeHierarchyManager.resolveView(iFindPrecedingViewTagForTransition);
                    viewResolveView = view;
                    view = viewResolveView2;
                } else {
                    viewResolveView = reanimatedNativeHierarchyManager.resolveView(iFindPrecedingViewTagForTransition);
                }
                if (z2) {
                    clearAllSharedConfigsForView(view);
                    clearAllSharedConfigsForView(viewResolveView);
                } else {
                    boolean zContainsKey = this.mCurrentSharedTransitionViews.containsKey(Integer.valueOf(view.getId()));
                    boolean zContainsKey2 = this.mCurrentSharedTransitionViews.containsKey(Integer.valueOf(viewResolveView.getId()));
                    if (!zContainsKey && !zContainsKey2) {
                        View viewFindScreen = findScreen(view);
                        View viewFindScreen2 = findScreen(viewResolveView);
                        if (viewFindScreen != null && viewFindScreen2 != null && (viewGroup = (ViewGroup) findStack(viewFindScreen)) != null && (childCount = (viewGroupManager = (ViewGroupManager) reanimatedNativeHierarchyManager.resolveViewManager(viewGroup.getId())).getChildCount(viewGroup)) >= 2) {
                            View childAt = viewGroupManager.getChildAt(viewGroup, childCount - 1);
                            View childAt2 = viewGroupManager.getChildAt(viewGroup, childCount - 2);
                            if (!(!z ? !(childAt.getId() == viewFindScreen.getId() && childAt2.getId() == viewFindScreen2.getId()) : !(childAt2.getId() == viewFindScreen.getId() && childAt.getId() == viewFindScreen2.getId()))) {
                            }
                        }
                    }
                    Snapshot snapshot = null;
                    if (z) {
                        this.mViewTagsToHide.add(Integer.valueOf(view.getId()));
                        if (zContainsKey) {
                            snapshot = new Snapshot(view);
                        } else {
                            makeSnapshot(view);
                        }
                        makeSnapshot(viewResolveView);
                    } else if (zContainsKey) {
                        makeSnapshot(view);
                    }
                    if (snapshot == null) {
                        snapshot = this.mSnapshotRegistry.get(Integer.valueOf(view.getId()));
                    }
                    Snapshot snapshot2 = this.mSnapshotRegistry.get(Integer.valueOf(viewResolveView.getId()));
                    arrayList.add(view);
                    arrayList.add(viewResolveView);
                    arrayList2.add(new SharedElement(view, snapshot, viewResolveView, snapshot2));
                }
            }
        }
        if (!arrayList.isEmpty()) {
            for (View view2 : this.mCurrentSharedTransitionViews.values()) {
                if (arrayList.contains(view2)) {
                    disableCleaningForViewTag(view2.getId());
                } else {
                    this.mViewsWithCanceledAnimation.put(Integer.valueOf(view2.getId()), view2);
                }
            }
            this.mCurrentSharedTransitionViews.clear();
            for (View view3 : arrayList) {
                this.mCurrentSharedTransitionViews.put(Integer.valueOf(view3.getId()), view3);
            }
            for (View view4 : new ArrayList(this.mViewsWithCanceledAnimation.values())) {
                cancelAnimation(view4);
                finishSharedAnimation(view4.getId());
            }
        }
        this.mSharedElements = arrayList2;
        for (SharedElement sharedElement : arrayList2) {
            this.mSharedElementsLookup.put(Integer.valueOf(sharedElement.sourceView.getId()), sharedElement);
        }
        return arrayList2;
    }

    private void setupTransitionContainer() {
        if (this.mIsSharedTransitionActive) {
            return;
        }
        this.mIsSharedTransitionActive = true;
        ReactContext context = this.mAnimationsManager.getContext();
        Activity currentActivity = context.getCurrentActivity();
        if (currentActivity == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) currentActivity.getWindow().getDecorView().getRootView();
        if (this.mTransitionContainer == null) {
            this.mTransitionContainer = new ReactViewGroup(context);
        }
        viewGroup.addView(this.mTransitionContainer);
        this.mTransitionContainer.bringToFront();
    }

    private void reparentSharedViewsForCurrentTransition(List<SharedElement> list) {
        Iterator<SharedElement> it = list.iterator();
        while (it.hasNext()) {
            View view = it.next().sourceView;
            if (!this.mSharedTransitionParent.containsKey(Integer.valueOf(view.getId()))) {
                this.mSharedTransitionParent.put(Integer.valueOf(view.getId()), (View) view.getParent());
                this.mSharedTransitionInParentIndex.put(Integer.valueOf(view.getId()), Integer.valueOf(((ViewGroup) view.getParent()).indexOfChild(view)));
                ((ViewGroup) view.getParent()).removeView(view);
                ((ViewGroup) this.mTransitionContainer).addView(view);
            }
        }
    }

    private void startSharedTransition(List<SharedElement> list, int i) {
        for (SharedElement sharedElement : list) {
            startSharedAnimationForView(sharedElement.sourceView, sharedElement.sourceViewSnapshot, sharedElement.targetViewSnapshot, i);
            sharedElement.targetView.setVisibility(4);
        }
    }

    private void startSharedAnimationForView(View view, Snapshot snapshot, Snapshot snapshot2, int i) {
        HashMap<String, Object> targetMap = snapshot2.toTargetMap();
        HashMap<String, Object> mapPrepareDataForAnimationWorklet = this.mAnimationsManager.prepareDataForAnimationWorklet(snapshot.toCurrentMap(), false, true);
        HashMap<String, Object> map = new HashMap<>(this.mAnimationsManager.prepareDataForAnimationWorklet(targetMap, true, true));
        map.putAll(mapPrepareDataForAnimationWorklet);
        this.mNativeMethodsHolder.startAnimation(view.getId(), i, map);
    }

    protected void finishSharedAnimation(int i) {
        final ViewParent parent;
        if (this.mDisableCleaningForViewTag.containsKey(Integer.valueOf(i))) {
            enableCleaningForViewTag(i);
            return;
        }
        View view = this.mCurrentSharedTransitionViews.get(Integer.valueOf(i));
        if (view == null && (view = this.mViewsWithCanceledAnimation.get(Integer.valueOf(i))) != null) {
            this.mViewsWithCanceledAnimation.remove(Integer.valueOf(view.getId()));
        }
        if (view != null) {
            int id = view.getId();
            ((ViewGroup) this.mTransitionContainer).removeView(view);
            View view2 = this.mSharedTransitionParent.get(Integer.valueOf(id));
            int iIntValue = this.mSharedTransitionInParentIndex.get(Integer.valueOf(id)).intValue();
            ViewGroup viewGroup = (ViewGroup) view2;
            if (iIntValue <= viewGroup.getChildCount()) {
                viewGroup.addView(view, iIntValue);
            } else {
                viewGroup.addView(view);
            }
            Snapshot snapshot = this.mSnapshotRegistry.get(Integer.valueOf(id));
            if (snapshot != null) {
                int i2 = snapshot.originY;
                if (findStack(view) == null) {
                    snapshot.originY = snapshot.originYByParent;
                }
                HashMap<String, Object> basicMap = snapshot.toBasicMap();
                HashMap map = new HashMap();
                for (String str : basicMap.keySet()) {
                    Object obj = basicMap.get(str);
                    if (str.equals(Snapshot.TRANSFORM_MATRIX)) {
                        map.put(str, obj);
                    } else {
                        map.put(str, Double.valueOf(PixelUtil.toDIPFromPixel(Utils.convertToFloat(obj))));
                    }
                }
                this.mAnimationsManager.progressLayoutAnimation(id, map, true);
                snapshot.originY = i2;
            }
            if (this.mViewTagsToHide.contains(Integer.valueOf(i))) {
                view.setVisibility(4);
            }
            SharedElement sharedElement = this.mSharedElementsLookup.get(Integer.valueOf(id));
            if (sharedElement != null) {
                View view3 = sharedElement.targetView;
                Integer numValueOf = Integer.valueOf(view3.getId());
                view3.setVisibility(0);
                this.mCurrentSharedTransitionViews.remove(numValueOf);
                this.mViewsWithCanceledAnimation.remove(numValueOf);
            }
            this.mCurrentSharedTransitionViews.remove(Integer.valueOf(id));
            this.mSharedTransitionParent.remove(Integer.valueOf(id));
            this.mSharedTransitionInParentIndex.remove(Integer.valueOf(id));
            if (this.mRemovedSharedViews.contains(view)) {
                this.mNativeMethodsHolder.clearAnimationConfig(view.getId());
                this.mRemovedSharedViews.remove(view);
            }
        }
        if (this.mCurrentSharedTransitionViews.isEmpty()) {
            View view4 = this.mTransitionContainer;
            if (view4 != null && (parent = view4.getParent()) != null) {
                this.mTransitionContainer.setVisibility(4);
                this.mTransitionContainer.post(new Runnable() { // from class: com.swmansion.reanimated.layoutReanimation.SharedTransitionManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m508x6bfb5c4c(parent);
                    }
                });
            }
            this.mSharedElements.clear();
            this.mSharedElementsLookup.clear();
            this.mSharedElementsWithProgress.clear();
            this.mSharedElementsWithAnimation.clear();
            this.mRemovedSharedViews.clear();
            this.mViewTagsToHide.clear();
            this.mIsSharedTransitionActive = false;
        }
    }

    /* JADX INFO: renamed from: lambda$finishSharedAnimation$1$com-swmansion-reanimated-layoutReanimation-SharedTransitionManager, reason: not valid java name */
    /* synthetic */ void m508x6bfb5c4c(ViewParent viewParent) {
        ((ViewGroup) viewParent).removeView(this.mTransitionContainer);
        this.mTransitionContainer.setVisibility(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    private View findScreen(View view) {
        for (ViewParent parent = view.getParent(); parent != 0; parent = parent.getParent()) {
            if (parent.getClass().getSimpleName().equals("Screen")) {
                return (View) parent;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    private View findStack(View view) {
        for (ViewParent parent = view.getParent(); parent != 0; parent = parent.getParent()) {
            if (parent.getClass().getSimpleName().equals("ScreenStack")) {
                return (View) parent;
            }
        }
        return null;
    }

    protected void makeSnapshot(View view) {
        this.mSnapshotRegistry.put(Integer.valueOf(view.getId()), new Snapshot(view));
    }

    class SnapshotTreeVisitor implements TreeVisitor {
        SnapshotTreeVisitor() {
        }

        @Override // com.swmansion.reanimated.layoutReanimation.SharedTransitionManager.TreeVisitor
        public void run(View view) {
            if (SharedTransitionManager.this.mAnimationsManager.hasAnimationForTag(view.getId(), 4)) {
                SharedTransitionManager.this.mRemovedSharedViews.add(view);
                SharedTransitionManager.this.makeSnapshot(view);
            }
        }
    }

    class ConfigCleanerTreeVisitor implements TreeVisitor {
        ConfigCleanerTreeVisitor() {
        }

        @Override // com.swmansion.reanimated.layoutReanimation.SharedTransitionManager.TreeVisitor
        public void run(View view) {
            SharedTransitionManager.this.mNativeMethodsHolder.clearAnimationConfig(view.getId());
        }
    }

    protected void visitTreeForTags(int[] iArr, TreeVisitor treeVisitor) {
        if (iArr == null) {
            return;
        }
        ReanimatedNativeHierarchyManager reanimatedNativeHierarchyManager = this.mAnimationsManager.getReanimatedNativeHierarchyManager();
        for (int i : iArr) {
            visitTree(reanimatedNativeHierarchyManager.resolveView(i), treeVisitor);
        }
    }

    private void visitTree(View view, TreeVisitor treeVisitor) {
        int id = view.getId();
        if (id == -1) {
            return;
        }
        ReanimatedNativeHierarchyManager reanimatedNativeHierarchyManager = this.mAnimationsManager.getReanimatedNativeHierarchyManager();
        try {
            treeVisitor.run(view);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                ViewManager viewManagerResolveViewManager = reanimatedNativeHierarchyManager.resolveViewManager(id);
                ViewGroupManager viewGroupManager = viewManagerResolveViewManager instanceof ViewGroupManager ? (ViewGroupManager) viewManagerResolveViewManager : null;
                if (viewGroupManager == null) {
                    return;
                }
                for (int i = 0; i < viewGroupManager.getChildCount(viewGroup); i++) {
                    visitTree(viewGroupManager.getChildAt(viewGroup, i), treeVisitor);
                }
            }
        } catch (IllegalViewOperationException unused) {
        }
    }

    void visitNativeTreeAndMakeSnapshot(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (this.mAnimationsManager.hasAnimationForTag(view.getId(), 4)) {
                makeSnapshot(view);
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                visitNativeTreeAndMakeSnapshot(viewGroup.getChildAt(i));
            }
        }
    }

    private void clearAllSharedConfigsForView(View view) {
        int id = view.getId();
        this.mSnapshotRegistry.remove(Integer.valueOf(id));
        this.mNativeMethodsHolder.clearAnimationConfig(id);
    }

    private void cancelAnimation(View view) {
        this.mNativeMethodsHolder.cancelAnimation(view.getId());
    }

    private void disableCleaningForViewTag(int i) {
        Integer num = this.mDisableCleaningForViewTag.get(Integer.valueOf(i));
        if (num != null) {
            this.mDisableCleaningForViewTag.put(Integer.valueOf(i), Integer.valueOf(num.intValue() + 1));
        } else {
            this.mDisableCleaningForViewTag.put(Integer.valueOf(i), 1);
        }
    }

    private void enableCleaningForViewTag(int i) {
        Integer num = this.mDisableCleaningForViewTag.get(Integer.valueOf(i));
        if (num == null) {
            return;
        }
        if (num.intValue() == 1) {
            this.mDisableCleaningForViewTag.remove(Integer.valueOf(i));
        } else {
            this.mDisableCleaningForViewTag.put(Integer.valueOf(i), Integer.valueOf(num.intValue() - 1));
        }
    }

    void orderByAnimationTypes(List<SharedElement> list) {
        this.mSharedElementsWithProgress.clear();
        this.mSharedElementsWithAnimation.clear();
        for (SharedElement sharedElement : list) {
            if (this.mAnimationsManager.hasAnimationForTag(sharedElement.sourceView.getId(), 5)) {
                this.mSharedElementsWithProgress.add(sharedElement);
            } else {
                this.mSharedElementsWithAnimation.add(sharedElement);
            }
        }
    }
}
