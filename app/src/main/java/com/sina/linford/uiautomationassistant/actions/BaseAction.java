package com.sina.linford.uiautomationassistant.actions;

import android.view.accessibility.AccessibilityEvent;

public interface BaseAction {
    public void onServiceConnected();
    public void onAccessibilityEvent(AccessibilityEvent event);
}
