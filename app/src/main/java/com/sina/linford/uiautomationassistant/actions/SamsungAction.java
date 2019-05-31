package com.sina.linford.uiautomationassistant.actions;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.sina.linford.uiautomationassistant.basicInfor.BasicMinitorViewInfo;
import com.sina.linford.uiautomationassistant.basicInfor.BasicMinitorViewInfoFactory;

import java.util.ArrayList;
import java.util.List;

public class SamsungAction implements BaseAction {

    AccessibilityService accessibilityService;
    List<BasicMinitorViewInfo> viewInfos;
    String[] packages;
    private String tag = "Linford";

    public SamsungAction(AccessibilityService service) {
        this.accessibilityService = service;
        viewInfos = BasicMinitorViewInfoFactory.generateSumsungInfo();

    }

    @Override
    public void onServiceConnected() {

        List<String> packageNames = new ArrayList<>();
        for (BasicMinitorViewInfo info : viewInfos) {
            if (info != null && info.getPackageName() != null && info.getPackageName().isEmpty() && packageNames.contains(info.getPackageName())) {
                packageNames.add(info.getPackageName());
            }
        }

        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.notificationTimeout = 100;
        this.packages = new String[packageNames.size()];
        packageNames.toArray(this.packages);
        serviceInfo.packageNames = this.packages;

        this.accessibilityService.setServiceInfo(serviceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.d(tag, event.toString());


        // get the event type
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:

                String viewName = event.getClassName().toString();
                Log.d(tag, viewName);

                // 申请权限的弹窗
                if (viewName.equals(viewInfos.get(1).getRootViewClass()) && event.getPackageName().equals(viewInfos.get(0).getPackageName())) {
                    AccessibilityNodeInfo myNode = this.accessibilityService.getRootInActiveWindow();
                    Log.d(tag, "myNode name" + myNode.getClassName().toString());
                    List<AccessibilityNodeInfo> allowBtns = myNode.findAccessibilityNodeInfosByViewId(viewInfos.get(1).getTargetViewId());
                    if (allowBtns.size() > 0) {
                        allowBtns.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }

                }
                // 新浪新闻的协议弹窗
                else if (viewName.equals(viewInfos.get(2).getRootViewClass()) && event.getPackageName().equals(viewInfos.get(2).getPackageName())) {
                    AccessibilityNodeInfo myNode = this.accessibilityService.getRootInActiveWindow();
                    Log.d(tag, "myNode name" + myNode.getClassName().toString());
                    List<AccessibilityNodeInfo> agreeBtns = myNode.findAccessibilityNodeInfosByViewId(viewInfos.get(2).getTargetViewId());
                    if (agreeBtns.size() > 0) {
                        agreeBtns.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
                break;
            default:
                break;
        }
    }
}
