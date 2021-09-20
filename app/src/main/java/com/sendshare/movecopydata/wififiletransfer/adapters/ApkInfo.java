package com.sendshare.movecopydata.wififiletransfer.adapters;

import android.graphics.drawable.Drawable;

public class ApkInfo {
    private String name;
    private String apkFilepath;
    private Drawable drawableIcon;
    private boolean isChecked;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ApkInfo(String name, String apkFilepath, Drawable drawableIcon, boolean isChecked) {
        this.name = name;
        this.apkFilepath = apkFilepath;
        this.drawableIcon = drawableIcon;
        this.isChecked = isChecked;
    }

    public String getApkFilepath() {
        return apkFilepath;
    }

    public void setApkFilepath(String apkFilepath) {
        this.apkFilepath = apkFilepath;
    }

    public Drawable getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(Drawable drawableIcon) {
        this.drawableIcon = drawableIcon;
    }
}
