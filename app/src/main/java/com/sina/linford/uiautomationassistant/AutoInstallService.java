package com.sina.linford.uiautomationassistant;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zongjing on 6/16/2017.
 */

public class AutoInstallService extends AccessibilityService {
    private String packageName;
    private String viewtype;
    private String buttonId;
    public static HashMap<String, List<String>> modelPackageDic = new HashMap<String, List<String>>();
    public static HashMap<String, String> modelViewtypeDic = new HashMap<>();

    private String tag = "Linford";

    @Override
    protected void onServiceConnected() {

        getModelPackageModel();

        // set the package which the service shold be monitored
        this.setPackageandViewtypebyPhoneModel();

        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{packageName};
        serviceInfo.notificationTimeout = 100;
        setServiceInfo(serviceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
// get the event type
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.d(tag, event.getClassName().toString());
                if (event.getClassName().toString().equals(viewtype)) {
                    AccessibilityNodeInfo myNode = getRootInActiveWindow();
                    Log.d(tag,"myNode name"+myNode.getClassName().toString());
                    AccessibilityNodeInfo continue_install_btn = myNode.findAccessibilityNodeInfosByViewId(buttonId).get(0);
                    continue_install_btn.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }

    private void setPackageandViewtypebyPhoneModel() {
        String phoneModel = Build.MODEL;
        this.packageName = modelPackageDic.get(phoneModel).get(0);
        this.viewtype = modelPackageDic.get(phoneModel).get(1);
        this.buttonId = modelPackageDic.get(phoneModel).get(2);
    }

    public void getModelPackageModel() {
        List<String> packageViewtypeButton= new ArrayList<>(Arrays.asList("com.miui.securitycenter","android.widget.FrameLayout","android:id/button2"));
        modelPackageDic.put("MIX", packageViewtypeButton);
        packageViewtypeButton.clear();
        packageViewtypeButton= new ArrayList<>(Arrays.asList("android","android.widget.FrameLayout","vivo:id/vivo_adb_install_ok_button"));
        modelPackageDic.put("vivo Xplay6", packageViewtypeButton);
        packageViewtypeButton=null;
    }
//
//    public void getModelViewType() {
//        modelViewtypeDic.put("MIX",);
//        modelViewtypeDic.put("vivo Xplay6", "android.widget.FrameLayout");
//    }
}
