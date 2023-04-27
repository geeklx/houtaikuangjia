package com.geek.system.support.system.entity.sys;

import com.geek.system.support.system.dict.AppType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ReturnTreeData {

    private Long id;

    private String code;

    private String name;

    private String type;

    private String img;

    private Boolean chekced;

    private Boolean enable = true;

    private Long parentId;

    @Enumerated(EnumType.STRING)
    private AppType appType;

    public ReturnTreeData() {
    }

    public ReturnTreeData(Long id, String code, String name, String type, String img, Boolean chekced, Boolean enable, Long parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.img = img;
        this.chekced = chekced;
        this.enable = enable;
        this.parentId = parentId;
    }

    public ReturnTreeData(Long id, String code, String name, String type, String img, Long parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.img = img;
        this.parentId = parentId;
    }
}
