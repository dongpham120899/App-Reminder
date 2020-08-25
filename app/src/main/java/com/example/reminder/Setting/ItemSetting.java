package com.example.reminder.Setting;

import android.graphics.drawable.Icon;

public class ItemSetting {
    private int icon;
    private String name;
    private String detail;

    public ItemSetting(int icon, String name, String detail) {
        this.icon = icon;
        this.name = name;
        this.detail = detail;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
