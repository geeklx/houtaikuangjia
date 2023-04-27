package com.geek.workbench.AppBean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MyAppList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<MyAppBasic> data;


}
