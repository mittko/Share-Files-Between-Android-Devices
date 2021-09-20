package com.sendshare.movecopydata.wififiletransfer.adapters;

public class ListRow {
    private String path;
    private boolean checked;
    private boolean isDirectory;
    private boolean isVisible;

    public boolean isVisible() {
        return isVisible;
    }

    public String getPath() {
        return path;
    }

    boolean isChecked() {
        return checked;
    }

    void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public ListRow(String path, boolean checked, boolean isVisible) {
        this.path = path;
        this.checked = checked;
        this.isVisible = isVisible;
    }

}
