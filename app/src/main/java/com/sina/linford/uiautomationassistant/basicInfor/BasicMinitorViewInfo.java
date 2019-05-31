package com.sina.linford.uiautomationassistant.basicInfor;

public class BasicMinitorViewInfo {
    private String packageName;

    private String targetViewId;

    private String rootViewClass;

    public BasicMinitorViewInfo(String packageName, String rootViewClass, String targetViewId) {
        this.packageName = packageName;
        this.rootViewClass = rootViewClass;
        this.targetViewId = targetViewId;
    }

    public static BasicMinitorViewInfo getSinaNewMinitorViewInfo() {
        return new BasicMinitorViewInfo("com.samsung.android.packageinstaller", "android.widget.FrameLayout", "com.sina.news:id/btn_agree");
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTargetViewId() {
        return targetViewId;
    }

    public void setTargetViewId(String targetViewId) {
        this.targetViewId = targetViewId;
    }

    public String getRootViewClass() {
        return rootViewClass;
    }

    public void setRootViewClass(String rootViewClass) {
        this.rootViewClass = rootViewClass;
    }
}
