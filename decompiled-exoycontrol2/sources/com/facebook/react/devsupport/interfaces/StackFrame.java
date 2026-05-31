package com.facebook.react.devsupport.interfaces;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public interface StackFrame {
    int getColumn();

    String getFile();

    String getFileName();

    int getLine();

    String getMethod();

    boolean isCollapsed();

    JSONObject toJSON();
}
