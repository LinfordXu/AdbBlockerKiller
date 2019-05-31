package com.sina.linford.uiautomationassistant.actions;

import android.accessibilityservice.AccessibilityService;

public class ActionFactory {
    public static BaseAction getAction(String brand, AccessibilityService service) {
        switch (brand.toLowerCase()) {
            case "sansung":
                return new SamsungAction(service);
            default:
                return null;
        }
    }
}
