package com.sina.linford.uiautomationassistant;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.ThemedSpinnerAdapter;

import com.sina.linford.uiautomationassistant.actions.ActionFactory;
import com.sina.linford.uiautomationassistant.actions.BaseAction;
import com.sina.linford.uiautomationassistant.basicInfor.BasicMinitorViewInfo;
import com.sina.linford.uiautomationassistant.basicInfor.BasicMinitorViewInfoFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zongjing on 6/16/2017.
 */

public class AutoInstallService extends AccessibilityService {

    private String tag = "Linford";
    //    private BaseAction action = ActionFactory.getAction(Build.BRAND, this);
    private List<BasicMinitorViewInfo> viewInfos = BasicMinitorViewInfoFactory.createViewInfos(Build.BRAND);
    private Boolean isSupport = BasicMinitorViewInfoFactory.supportBrandList.contains(Build.BRAND);


    @Override
    protected void onServiceConnected() {
        if (!isSupport) return;

        Log.d(tag, "connected");

        List<String> packageNames = new ArrayList<>();
        for (BasicMinitorViewInfo info : viewInfos) {
            if (info != null && info.getPackageName() != null && !info.getPackageName().isEmpty() && !packageNames.contains(info.getPackageName())) {
                packageNames.add(info.getPackageName());
            }
        }

        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.notificationTimeout = 100;
        serviceInfo.flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        String[] packages = new String[packageNames.size()];
        packageNames.toArray(packages);
        serviceInfo.packageNames = packages;

        setServiceInfo(serviceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (!isSupport) return;

        Log.d(tag, event.toString());
        // get the event type
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
//            case AccessibilityEvent.TYPE_VIEW_CLICKED:

                List<AccessibilityWindowInfo> currentWindows = getWindows();

                AccessibilityNodeInfo currentActiveWindows = getRootInActiveWindow();
                if (currentActiveWindows == null) break;

                // event.getClassName().toString()拿到的是发出事件的类，不是view对应的class
                // String viewName = event.getClassName().toString();
                String viewName = currentActiveWindows.getClassName().toString();

                Log.d(tag, viewName);

                for (BasicMinitorViewInfo viewInfo : viewInfos) {
                    if (viewName.equals(viewInfo.getRootViewClass()) && event.getPackageName().equals(viewInfo.getPackageName())) {

                        Log.d(tag, "myNode name" + currentActiveWindows.getClassName().toString());
                        List<AccessibilityNodeInfo> allowBtns = currentActiveWindows.findAccessibilityNodeInfosByViewId(viewInfo.getTargetViewId());
                        if (allowBtns.size() > 0) {
                            Log.d(tag, "click");
                            allowBtns.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            // 如果已经匹配当前Viewinfo，则跳出，不再去尝试其它Viewinfo
                            break;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }


    @Override
    public void onDestroy() {
        Log.d(tag, "destory");
        super.onDestroy();
    }


}
