package com.fosung.workbench.AppBean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

@Data
public class MyAppBasic implements Serializable {
    private static final long serialVersionUID = 1L;

    private AppInfo mbean;

}
