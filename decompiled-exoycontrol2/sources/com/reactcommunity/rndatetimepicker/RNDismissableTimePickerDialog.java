package com.reactcommunity.rndatetimepicker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TimePicker;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class RNDismissableTimePickerDialog extends MinuteIntervalSnappableTimePickerDialog {
    @Override // com.reactcommunity.rndatetimepicker.MinuteIntervalSnappableTimePickerDialog, android.app.Dialog, android.view.Window.Callback
    public /* bridge */ /* synthetic */ void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // com.reactcommunity.rndatetimepicker.MinuteIntervalSnappableTimePickerDialog, android.app.TimePickerDialog, android.content.DialogInterface.OnClickListener
    public /* bridge */ /* synthetic */ void onClick(DialogInterface dialogInterface, int i) {
        super.onClick(dialogInterface, i);
    }

    @Override // com.reactcommunity.rndatetimepicker.MinuteIntervalSnappableTimePickerDialog, android.app.Dialog, android.view.Window.Callback
    public /* bridge */ /* synthetic */ void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // com.reactcommunity.rndatetimepicker.MinuteIntervalSnappableTimePickerDialog, android.app.TimePickerDialog, android.widget.TimePicker.OnTimeChangedListener
    public /* bridge */ /* synthetic */ void onTimeChanged(TimePicker timePicker, int i, int i2) {
        super.onTimeChanged(timePicker, i, i2);
    }

    @Override // com.reactcommunity.rndatetimepicker.MinuteIntervalSnappableTimePickerDialog, android.app.TimePickerDialog
    public /* bridge */ /* synthetic */ void updateTime(int i, int i2) {
        super.updateTime(i, i2);
    }

    public RNDismissableTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener, int i, int i2, int i3, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        super(context, onTimeSetListener, i, i2, i3, z, rNTimePickerDisplay);
        fixSpinner(context, i, i2, z, rNTimePickerDisplay);
    }

    public RNDismissableTimePickerDialog(Context context, int i, TimePickerDialog.OnTimeSetListener onTimeSetListener, int i2, int i3, int i4, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        super(context, i, onTimeSetListener, i2, i3, i4, z, rNTimePickerDisplay);
        fixSpinner(context, i2, i3, z, rNTimePickerDisplay);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
    }

    private void fixSpinner(Context context, int i, int i2, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        if (Build.VERSION.SDK_INT == 24 && rNTimePickerDisplay == RNTimePickerDisplay.SPINNER) {
            try {
                context.obtainStyledAttributes(null, (int[]) Class.forName("com.android.internal.R$styleable").getField("TimePicker").get(null), android.R.attr.timePickerStyle, 0).recycle();
                TimePicker timePicker = (TimePicker) ReflectionHelper.findField(TimePickerDialog.class, TimePicker.class, "mTimePicker").get(this);
                Field fieldFindField = ReflectionHelper.findField(TimePicker.class, Class.forName("android.widget.TimePicker$TimePickerDelegate"), "mDelegate");
                Object obj = fieldFindField.get(timePicker);
                Class<?> cls = Class.forName("android.widget.TimePickerSpinnerDelegate");
                if (obj.getClass() != cls) {
                    fieldFindField.set(timePicker, null);
                    timePicker.removeAllViews();
                    Constructor<?> constructor = cls.getConstructor(TimePicker.class, Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE);
                    constructor.setAccessible(true);
                    fieldFindField.set(timePicker, constructor.newInstance(timePicker, context, null, Integer.valueOf(android.R.attr.timePickerStyle), 0));
                    timePicker.setIs24HourView(Boolean.valueOf(z));
                    timePicker.setCurrentHour(Integer.valueOf(i));
                    timePicker.setCurrentMinute(Integer.valueOf(i2));
                    timePicker.setOnTimeChangedListener(this);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
