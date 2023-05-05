package com.fosung.workbench.dto.other;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class UrlAndParam {


    private String identity="";


    public UrlAndParam(String identity){

        this.identity = identity;
    }
}
