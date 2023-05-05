package com.fosung.workbench.AppBean;

import lombok.Data;

import java.io.Serializable;
@Data
public class AppInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id ="";
    private String img ="";
    private String name = "";
    private String url ="";
    private Boolean enable;

    public AppInfo() {
    }

    public AppInfo(String id, String img, String name, String url, boolean enable) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.url = url;
        this.enable = enable;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



}
