package com.reactcommunity.rndatetimepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

/* JADX INFO: loaded from: classes.dex */
public class RNTimePickerDialogFragment extends DialogFragment {
    private TimePickerDialog instance;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnClickListener mOnNeutralButtonActionListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        TimePickerDialog timePickerDialogCreateDialog = createDialog(getArguments());
        this.instance = timePickerDialogCreateDialog;
        return timePickerDialogCreateDialog;
    }

    public void update(Bundle bundle) {
        RNDate rNDate = new RNDate(bundle);
        this.instance.updateTime(rNDate.hour(), rNDate.minute());
    }

    static TimePickerDialog getDialog(Bundle bundle, Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        RNDate rNDate = new RNDate(bundle);
        int iHour = rNDate.hour();
        int iMinute = rNDate.minute();
        boolean zIs24HourFormat = DateFormat.is24HourFormat(context);
        if (bundle != null) {
            zIs24HourFormat = bundle.getBoolean(RNConstants.ARG_IS24HOUR, DateFormat.is24HourFormat(context));
        }
        boolean z = zIs24HourFormat;
        int i = (bundle == null || !MinuteIntervalSnappableTimePickerDialog.isValidMinuteInterval(bundle.getInt(RNConstants.ARG_INTERVAL))) ? 1 : bundle.getInt(RNConstants.ARG_INTERVAL);
        RNTimePickerDisplay displayTime = Common.getDisplayTime(bundle);
        if (displayTime == RNTimePickerDisplay.SPINNER) {
            return new RNDismissableTimePickerDialog(context, R.style.SpinnerTimePickerDialog, onTimeSetListener, iHour, iMinute, i, z, displayTime);
        }
        return new RNDismissableTimePickerDialog(context, onTimeSetListener, iHour, iMinute, i, z, displayTime);
    }

    private TimePickerDialog createDialog(Bundle bundle) {
        FragmentActivity activity = getActivity();
        TimePickerDialog dialog = getDialog(bundle, activity, this.mOnTimeSetListener);
        if (bundle != null) {
            Common.setButtonTitles(bundle, dialog, this.mOnNeutralButtonActionListener);
            if (activity != null) {
                dialog.setOnShowListener(Common.setButtonTextColor(activity, dialog, bundle, Common.getDisplayTime(bundle) == RNTimePickerDisplay.SPINNER));
            }
        }
        return dialog;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.mOnTimeSetListener = onTimeSetListener;
    }

    void setOnNeutralButtonActionListener(DialogInterface.OnClickListener onClickListener) {
        this.mOnNeutralButtonActionListener = onClickListener;
    }
}
