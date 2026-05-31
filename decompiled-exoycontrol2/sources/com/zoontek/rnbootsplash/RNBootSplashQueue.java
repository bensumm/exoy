package com.zoontek.rnbootsplash;

import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class RNBootSplashQueue<E> extends Vector<E> {
    public synchronized E shift() {
        if (size() == 0) {
            return null;
        }
        E eElementAt = elementAt(0);
        removeElementAt(0);
        return eElementAt;
    }

    public void push(E e) {
        addElement(e);
    }
}
