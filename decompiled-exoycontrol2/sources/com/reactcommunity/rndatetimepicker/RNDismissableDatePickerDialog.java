package com.reactcommunity.rndatetimepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.DatePicker;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class RNDismissableDatePickerDialog extends DatePickerDialog {
    public RNDismissableDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener, int i, int i2, int i3, RNDatePickerDisplay rNDatePickerDisplay) {
        super(context, onDateSetListener, i, i2, i3);
        fixSpinner(context, i, i2, i3, rNDatePickerDisplay);
    }

    public RNDismissableDatePickerDialog(Context context, int i, DatePickerDialog.OnDateSetListener onDateSetListener, int i2, int i3, int i4, RNDatePickerDisplay rNDatePickerDisplay) {
        super(context, i, onDateSetListener, i2, i3, i4);
        fixSpinner(context, i2, i3, i4, rNDatePickerDisplay);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
    }

    private void fixSpinner(Context context, int i, int i2, int i3, RNDatePickerDisplay rNDatePickerDisplay) {
        if (Build.VERSION.SDK_INT == 24 && rNDatePickerDisplay == RNDatePickerDisplay.SPINNER) {
            try {
                context.obtainStyledAttributes(null, (int[]) Class.forName("com.android.internal.R$styleable").getField("DatePicker").get(null), android.R.attr.datePickerStyle, 0).recycle();
                DatePicker datePicker = (DatePicker) ReflectionHelper.findField(DatePickerDialog.class, DatePicker.class, "mDatePicker").get(this);
                Field fieldFindField = ReflectionHelper.findField(DatePicker.class, Class.forName("android.widget.DatePickerSpinnerDelegate"), "mDelegate");
                if (fieldFindField.get(datePicker).getClass() != Class.forName("android.widget.DatePickerSpinnerDelegate")) {
                    fieldFindField.set(datePicker, null);
                    datePicker.removeAllViews();
                    Method declaredMethod = DatePicker.class.getDeclaredMethod("createSpinnerUIDelegate", Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE);
                    declaredMethod.setAccessible(true);
                    fieldFindField.set(datePicker, declaredMethod.invoke(datePicker, context, null, Integer.valueOf(android.R.attr.datePickerStyle), 0));
                    datePicker.setCalendarViewShown(false);
                    datePicker.init(i, i2, i3, this);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (rNDatePickerDisplay != RNDatePickerDisplay.SPINNER || getDatePicker() == null) {
            return;
        }
        getDatePicker().setCalendarViewShown(false);
    }
}
