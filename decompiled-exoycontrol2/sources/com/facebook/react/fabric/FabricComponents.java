package com.facebook.react.fabric;

import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.modal.ReactModalHostManager;
import com.facebook.react.views.progressbar.ReactProgressBarViewManager;
import com.facebook.react.views.scroll.ReactScrollViewManager;
import com.facebook.react.views.text.ReactRawTextManager;
import com.facebook.react.views.text.ReactTextViewManager;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class FabricComponents {
    private static final Map<String, String> sComponentNames;

    static {
        HashMap map = new HashMap();
        sComponentNames = map;
        map.put("View", "RCTView");
        map.put("Image", ReactImageManager.REACT_CLASS);
        map.put("ScrollView", ReactScrollViewManager.REACT_CLASS);
        map.put("Slider", "RCTSlider");
        map.put("ModalHostView", ReactModalHostManager.REACT_CLASS);
        map.put("Paragraph", ReactTextViewManager.REACT_CLASS);
        map.put("Text", "RCText");
        map.put("RawText", ReactRawTextManager.REACT_CLASS);
        map.put("ActivityIndicatorView", ReactProgressBarViewManager.REACT_CLASS);
        map.put("ShimmeringView", "RKShimmeringView");
        map.put("TemplateView", "RCTTemplateView");
        map.put("AxialGradientView", "RCTAxialGradientView");
        map.put("Video", "RCTVideo");
        map.put("Map", "RCTMap");
        map.put("WebView", "RCTWebView");
        map.put("Keyframes", "RCTKeyframes");
        map.put("ImpressionTrackingView", "RCTImpressionTrackingView");
    }

    public static String getFabricComponentName(String str) {
        String str2 = sComponentNames.get(str);
        return str2 != null ? str2 : str;
    }
}
