package com.fosung.workbench.AppBean;

import java.io.Serializable;
import java.util.List;

public class AppList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<AppBasicType> data;

    public AppList() {
    }

    public AppList(List<AppBasicType> data) {
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<AppBasicType> getData() {
        return data;
    }

    public void setData(List<AppBasicType> data) {
        this.data = data;
    }
}
