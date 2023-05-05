package com.fosung.workbench.AppBean;

import lombok.Data;

@Data
public class AppNavicationBtm extends AppInfo{

    private String img_normal;

    private String img_press;

    public AppNavicationBtm(String id, String img, String name, String url, boolean enable, String img_normal, String img_press ) {
        super(id, img, name, url, enable);
        this.img_normal = img_normal;
        this.img_press = img_press;
    }
}
