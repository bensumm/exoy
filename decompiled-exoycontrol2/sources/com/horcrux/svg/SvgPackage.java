package com.horcrux.svg;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.ViewManagerOnDemandReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import com.facebook.react.uimanager.ViewManager;
import com.horcrux.svg.RenderableViewManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes.dex */
public class SvgPackage extends TurboReactPackage implements ViewManagerOnDemandReactPackage {
    private Map<String, ModuleSpec> mViewManagers;

    private Map<String, ModuleSpec> getViewManagersMap(ReactApplicationContext reactApplicationContext) {
        if (this.mViewManagers == null) {
            HashMap mapNewHashMap = MapBuilder.newHashMap();
            mapNewHashMap.put(RenderableViewManager.GroupViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.GroupViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.PathViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.2
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.PathViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.CircleViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.3
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.CircleViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.EllipseViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.4
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.EllipseViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.LineViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.5
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.LineViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.RectViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.6
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.RectViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.TextViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.7
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.TextViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.TSpanViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.8
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.TSpanViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.TextPathViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.9
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.TextPathViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.ImageViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.10
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.ImageViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.ClipPathViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.11
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.ClipPathViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.DefsViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.12
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.DefsViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.UseViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.13
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.UseViewManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.SymbolManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.14
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.SymbolManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.LinearGradientManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.15
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.LinearGradientManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.RadialGradientManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.16
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.RadialGradientManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.PatternManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.17
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.PatternManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.MaskManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.18
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.MaskManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.ForeignObjectManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.19
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.ForeignObjectManager();
                }
            }));
            mapNewHashMap.put(RenderableViewManager.MarkerManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.20
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new RenderableViewManager.MarkerManager();
                }
            }));
            mapNewHashMap.put(SvgViewManager.REACT_CLASS, ModuleSpec.viewManagerSpec(new Provider<NativeModule>() { // from class: com.horcrux.svg.SvgPackage.21
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // javax.inject.Provider
                public NativeModule get() {
                    return new SvgViewManager();
                }
            }));
            this.mViewManagers = mapNewHashMap;
        }
        return this.mViewManagers;
    }

    @Override // com.facebook.react.ViewManagerOnDemandReactPackage
    public List<String> getViewManagerNames(ReactApplicationContext reactApplicationContext) {
        return (List) getViewManagersMap(reactApplicationContext).keySet();
    }

    @Override // com.facebook.react.TurboReactPackage
    protected List<ModuleSpec> getViewManagers(ReactApplicationContext reactApplicationContext) {
        return new ArrayList(getViewManagersMap(reactApplicationContext).values());
    }

    @Override // com.facebook.react.ViewManagerOnDemandReactPackage
    public ViewManager createViewManager(ReactApplicationContext reactApplicationContext, String str) {
        ModuleSpec moduleSpec = getViewManagersMap(reactApplicationContext).get(str);
        if (moduleSpec != null) {
            return (ViewManager) moduleSpec.getProvider().get();
        }
        return null;
    }

    @Override // com.facebook.react.TurboReactPackage
    public NativeModule getModule(String str, @Nonnull ReactApplicationContext reactApplicationContext) {
        str.hashCode();
        if (str.equals(RNSVGRenderableManager.NAME)) {
            return new RNSVGRenderableManager(reactApplicationContext);
        }
        if (str.equals(SvgViewModule.NAME)) {
            return new SvgViewModule(reactApplicationContext);
        }
        return null;
    }

    @Override // com.facebook.react.TurboReactPackage
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        try {
            return (ReactModuleInfoProvider) Class.forName("com.horcrux.svg.SvgPackage$$ReactModuleInfoProvider").newInstance();
        } catch (ClassNotFoundException unused) {
            return new ReactModuleInfoProvider() { // from class: com.horcrux.svg.SvgPackage.22
                @Override // com.facebook.react.module.model.ReactModuleInfoProvider
                public Map<String, ReactModuleInfo> getReactModuleInfos() {
                    HashMap map = new HashMap();
                    Class[] clsArr = {SvgViewModule.class, RNSVGRenderableManager.class};
                    for (int i = 0; i < 2; i++) {
                        Class cls = clsArr[i];
                        ReactModule reactModule = (ReactModule) cls.getAnnotation(ReactModule.class);
                        map.put(reactModule.name(), new ReactModuleInfo(reactModule.name(), cls.getName(), reactModule.canOverrideExistingModule(), reactModule.needsEagerInit(), reactModule.hasConstants(), reactModule.isCxxModule(), TurboModule.class.isAssignableFrom(cls)));
                    }
                    return map;
                }
            };
        } catch (IllegalAccessException e) {
            e = e;
            throw new RuntimeException("No ReactModuleInfoProvider for MyPackage$$ReactModuleInfoProvider", e);
        } catch (InstantiationException e2) {
            e = e2;
            throw new RuntimeException("No ReactModuleInfoProvider for MyPackage$$ReactModuleInfoProvider", e);
        }
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }
}
