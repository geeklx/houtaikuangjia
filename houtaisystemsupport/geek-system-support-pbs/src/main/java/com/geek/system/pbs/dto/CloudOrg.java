package com.geek.system.pbs.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class CloudOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("orgId")
    private String id;

    @JsonProperty("orgName")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("level")
    private Integer level;

    @JsonProperty("levelType")
    private String levelType;

    @JsonProperty("isLeaf")
    private Boolean leaf;

    @JsonProperty("parentId")
    private String parentId;

    @JsonProperty("outId")
    private String outId;

    @JsonProperty("outCode")
    private String outCode;

    @JsonProperty("outName")
    private String outName;

    @JsonProperty("num")
    private Integer num=999;

    @JsonProperty(value = "children")
    private List<CloudOrg> children = new ArrayList<>();

    public CloudOrg(String id, String name, String code, String parentId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.parentId = parentId;
    }

    public CloudOrg() {
    }
}
