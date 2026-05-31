package com.reactcommunity.rndatetimepicker;

import android.os.Bundle;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class RNDate {
    private Calendar now = Calendar.getInstance();

    public RNDate(Bundle bundle) {
        if (bundle != null && bundle.containsKey(RNConstants.ARG_VALUE)) {
            this.now.setTimeInMillis(bundle.getLong(RNConstants.ARG_VALUE));
        }
        this.now.setTimeZone(Common.getTimeZone(bundle));
    }

    public int year() {
        return this.now.get(1);
    }

    public int month() {
        return this.now.get(2);
    }

    public int day() {
        return this.now.get(5);
    }

    public int hour() {
        return this.now.get(11);
    }

    public int minute() {
        return this.now.get(12);
    }
}
