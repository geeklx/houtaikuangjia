package com.fosung.system.pbs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CloudTreeNode implements Serializable {

    @JsonProperty("orgId")
    private String id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("orgName")
    private String name;

    @JsonProperty(value = "parentId")
    private String parentId;

    @JsonProperty(value = "levelType")
    private String levelType;

    @JsonProperty(value = "outId")
    private String outId;

    @JsonProperty(value = "leaf")
    private Boolean leaf;

    @JsonProperty(value = "children")
    private List<CloudTreeNode> children = new ArrayList<>();

    public CloudTreeNode(String id, String code, String name, String parentId, String levelType) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.levelType = levelType;
    }

    public CloudTreeNode(String id, String code, String name, String parentId, String levelType, String outId, Boolean leaf) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.levelType = levelType;
        this.outId = outId;
        this.leaf = leaf;
    }

    public CloudTreeNode() {
    }
}
