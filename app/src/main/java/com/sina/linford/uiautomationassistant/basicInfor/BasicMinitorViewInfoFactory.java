package com.sina.linford.uiautomationassistant.basicInfor;

import java.util.ArrayList;
import java.util.List;

// Adb block infor
// permission infor
// sinanews infor
public class BasicMinitorViewInfoFactory {


    public static List<BasicMinitorViewInfo> createViewInfos(String brand) {
        switch (brand.toLowerCase()) {
            case "samsung":
                return generateSumsungInfo();
            case "xiaomi":
                return generateXiaomiInfo();
            case "huawei":
                return generateHuaweiInfo();
            default:
                return null;
        }
    }


    public static BasicMinitorViewInfo generateSinaNewsInfo() {
        return new BasicMinitorViewInfo("com.sina.news", "android.widget.FrameLayout", "com.sina.news:id/btn_agree");
    }

    public static List<BasicMinitorViewInfo> generateSumsungInfo() {
        List<BasicMinitorViewInfo> infos = new ArrayList<>();

        // 权限弹窗
        infos.add(new BasicMinitorViewInfo("com.samsung.android.packageinstaller", "android.widget.FrameLayout", "com.android.packageinstaller:id/permission_allow_button"));

        // 新闻弹窗
        infos.add(generateSinaNewsInfo());
        return infos;
    }

    public static List<BasicMinitorViewInfo> generateXiaomiInfo() {
        List<BasicMinitorViewInfo> infos = new ArrayList<>();
        infos.add(new BasicMinitorViewInfo("com.miui.securitycenter", "android.widget.FrameLayout", "android:id/button2"));
        infos.add(new BasicMinitorViewInfo("com.lbe.security.miui", "android.widget.FrameLayout", "android:id/button1"));
        infos.add(generateSinaNewsInfo());
        return infos;
    }

    // 华为通过adb安装阻拦更多，所以adb block killer 有两层
    public static List<BasicMinitorViewInfo> generateHuaweiInfo() {
        List<BasicMinitorViewInfo> infos = new ArrayList<>();
        // adb blocker 弹窗
        infos.add(new BasicMinitorViewInfo("com.android.packageinstaller", "android.widget.FrameLayout", "android:id/button1"));
        infos.add(new BasicMinitorViewInfo("com.android.packageinstaller", "android.widget.FrameLayout", "com.android.packageinstaller:id/ok_button"));

        // 权限弹窗
        infos.add(new BasicMinitorViewInfo("com.android.packageinstaller", "android.widget.FrameLayout", "com.android.packageinstaller:id/permission_allow_button"));

        // 新闻弹窗
        infos.add(generateSinaNewsInfo());
        return infos;
    }
}
