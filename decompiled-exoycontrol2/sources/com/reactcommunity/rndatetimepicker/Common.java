package com.reactcommunity.rndatetimepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.util.RNLog;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes.dex */
public class Common {
    public static final String LABEL = "label";
    public static final String NEGATIVE = "negative";
    public static final String NEUTRAL = "neutral";
    public static final String POSITIVE = "positive";
    public static final String TEXT_COLOR = "textColor";

    public static void dismissDialog(FragmentActivity fragmentActivity, String str, Promise promise) {
        if (fragmentActivity == null) {
            promise.reject(RNConstants.ERROR_NO_ACTIVITY, "Tried to close a " + str + " dialog while not attached to an Activity");
            return;
        }
        try {
            DialogFragment dialogFragment = (DialogFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag(str);
            boolean z = dialogFragment != null;
            if (z) {
                dialogFragment.dismiss();
            }
            promise.resolve(Boolean.valueOf(z));
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    public static int getDefaultDialogButtonTextColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        return ContextCompat.getColor(context, typedValue.resourceId != 0 ? typedValue.resourceId : typedValue.data);
    }

    public static DialogInterface.OnShowListener setButtonTextColor(final Context context, final AlertDialog alertDialog, final Bundle bundle, final boolean z) {
        return new DialogInterface.OnShowListener() { // from class: com.reactcommunity.rndatetimepicker.Common$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                Common.lambda$setButtonTextColor$0(alertDialog, context, bundle, z, dialogInterface);
            }
        };
    }

    static /* synthetic */ void lambda$setButtonTextColor$0(AlertDialog alertDialog, Context context, Bundle bundle, boolean z, DialogInterface dialogInterface) {
        Button button = alertDialog.getButton(-1);
        Button button2 = alertDialog.getButton(-2);
        Button button3 = alertDialog.getButton(-3);
        int defaultDialogButtonTextColor = getDefaultDialogButtonTextColor(context);
        setTextColor(button, POSITIVE, bundle, z, defaultDialogButtonTextColor);
        setTextColor(button2, NEGATIVE, bundle, z, defaultDialogButtonTextColor);
        setTextColor(button3, NEUTRAL, bundle, z, defaultDialogButtonTextColor);
    }

    private static void setTextColor(Button button, String str, Bundle bundle, boolean z, int i) {
        if (button == null) {
            return;
        }
        Integer buttonColor = getButtonColor(bundle, str);
        if (z || buttonColor != null) {
            if (buttonColor != null) {
                i = buttonColor.intValue();
            }
            button.setTextColor(i);
        }
    }

    private static Integer getButtonColor(Bundle bundle, String str) {
        Bundle bundle2;
        int i;
        Bundle bundle3 = bundle.getBundle(RNConstants.ARG_DIALOG_BUTTONS);
        if (bundle3 == null || (bundle2 = bundle3.getBundle(str)) == null || (i = (int) bundle2.getDouble(TEXT_COLOR, 0.0d)) == 0) {
            return null;
        }
        return Integer.valueOf(i);
    }

    public static RNTimePickerDisplay getDisplayTime(Bundle bundle) {
        RNTimePickerDisplay rNTimePickerDisplay = RNTimePickerDisplay.DEFAULT;
        return (bundle == null || bundle.getString("display", null) == null) ? rNTimePickerDisplay : RNTimePickerDisplay.valueOf(bundle.getString("display").toUpperCase(Locale.US));
    }

    public static RNDatePickerDisplay getDisplayDate(Bundle bundle) {
        RNDatePickerDisplay rNDatePickerDisplay = RNDatePickerDisplay.DEFAULT;
        return (bundle == null || bundle.getString("display", null) == null) ? rNDatePickerDisplay : RNDatePickerDisplay.valueOf(bundle.getString("display").toUpperCase(Locale.US));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setButtonTitles(Bundle bundle, AlertDialog alertDialog, DialogInterface.OnClickListener onClickListener) {
        Bundle bundle2 = bundle.getBundle(RNConstants.ARG_DIALOG_BUTTONS);
        if (bundle2 == null) {
            return;
        }
        setButtonLabel(bundle2.getBundle(NEUTRAL), alertDialog, -3, onClickListener);
        DialogInterface.OnClickListener onClickListener2 = (DialogInterface.OnClickListener) alertDialog;
        setButtonLabel(bundle2.getBundle(POSITIVE), alertDialog, -1, onClickListener2);
        setButtonLabel(bundle2.getBundle(NEGATIVE), alertDialog, -2, onClickListener2);
    }

    private static void setButtonLabel(Bundle bundle, AlertDialog alertDialog, int i, DialogInterface.OnClickListener onClickListener) {
        if (bundle == null || bundle.getString(LABEL) == null) {
            return;
        }
        alertDialog.setButton(i, bundle.getString(LABEL), onClickListener);
    }

    public static TimeZone getTimeZone(Bundle bundle) {
        if (bundle != null && bundle.containsKey(RNConstants.ARG_TZOFFSET_MINS)) {
            return new SimpleTimeZone(((int) bundle.getLong(RNConstants.ARG_TZOFFSET_MINS)) * 60 * 1000, TimeZones.GMT_ID);
        }
        if (bundle != null && bundle.containsKey(RNConstants.ARG_TZ_NAME)) {
            String string = bundle.getString(RNConstants.ARG_TZ_NAME);
            if (TimeZones.GMT_ID.equals(string)) {
                return TimeZone.getTimeZone(TimeZones.GMT_ID);
            }
            if (!TimeZones.GMT_ID.equals(TimeZone.getTimeZone(string).getID())) {
                return TimeZone.getTimeZone(string);
            }
            RNLog.w(null, "'" + string + "' does not exist in TimeZone.getAvailableIDs(). Falling back to TimeZone.getDefault()=" + TimeZone.getDefault().getID());
        }
        return TimeZone.getDefault();
    }

    public static long maxDateWithTimeZone(Bundle bundle) {
        if (!bundle.containsKey(RNConstants.ARG_MAXDATE)) {
            return Long.MAX_VALUE;
        }
        Calendar calendar = Calendar.getInstance(getTimeZone(bundle));
        calendar.setTimeInMillis(bundle.getLong(RNConstants.ARG_MAXDATE));
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar.getTimeInMillis();
    }

    public static long minDateWithTimeZone(Bundle bundle) {
        if (!bundle.containsKey(RNConstants.ARG_MINDATE)) {
            return 0L;
        }
        Calendar calendar = Calendar.getInstance(getTimeZone(bundle));
        calendar.setTimeInMillis(bundle.getLong(RNConstants.ARG_MINDATE));
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static Bundle createFragmentArguments(ReadableMap readableMap) {
        Bundle bundle = new Bundle();
        if (readableMap.hasKey(RNConstants.ARG_VALUE) && !readableMap.isNull(RNConstants.ARG_VALUE)) {
            bundle.putLong(RNConstants.ARG_VALUE, (long) readableMap.getDouble(RNConstants.ARG_VALUE));
        }
        if (readableMap.hasKey(RNConstants.ARG_TZ_NAME) && !readableMap.isNull(RNConstants.ARG_TZ_NAME)) {
            bundle.putString(RNConstants.ARG_TZ_NAME, readableMap.getString(RNConstants.ARG_TZ_NAME));
        }
        return bundle;
    }
}
