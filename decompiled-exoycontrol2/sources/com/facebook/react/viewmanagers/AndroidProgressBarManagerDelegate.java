package com.facebook.react.viewmanagers;

import android.view.View;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class AndroidProgressBarManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidProgressBarManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public AndroidProgressBarManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -1001078227:
                if (str.equals(NotificationCompat.CATEGORY_PROGRESS)) {
                    b = 0;
                }
                break;
            case -877170387:
                if (str.equals("testID")) {
                    b = 1;
                }
                break;
            case -676876213:
                if (str.equals("typeAttr")) {
                    b = 2;
                }
                break;
            case 94842723:
                if (str.equals(ViewProps.COLOR)) {
                    b = 3;
                }
                break;
            case 633138363:
                if (str.equals("indeterminate")) {
                    b = 4;
                }
                break;
            case 1118509918:
                if (str.equals("animating")) {
                    b = 5;
                }
                break;
            case 1804741442:
                if (str.equals("styleAttr")) {
                    b = 6;
                }
                break;
        }
        switch (b) {
            case 0:
                ((AndroidProgressBarManagerInterface) this.mViewManager).setProgress(t, obj == null ? 0.0d : ((Double) obj).doubleValue());
                break;
            case 1:
                ((AndroidProgressBarManagerInterface) this.mViewManager).setTestID(t, obj == null ? "" : (String) obj);
                break;
            case 2:
                ((AndroidProgressBarManagerInterface) this.mViewManager).setTypeAttr(t, obj != null ? (String) obj : null);
                break;
            case 3:
                ((AndroidProgressBarManagerInterface) this.mViewManager).setColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 4:
                ((AndroidProgressBarManagerInterface) this.mViewManager).setIndeterminate(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 5:
                ((AndroidProgressBarManagerInterface) this.mViewManager).setAnimating(t, obj != null ? ((Boolean) obj).booleanValue() : true);
                break;
            case 6:
                ((AndroidProgressBarManagerInterface) this.mViewManager).setStyleAttr(t, obj != null ? (String) obj : null);
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
