package com.geek.workbench.entity.shorturl;

import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description 短 URL 映射实体类
 * @Author gaojian
 * @Date 2022年1月17日11:11:07
 * @Version V1.0
 */
@Entity
@Table(name="short_url_mapping")
@Setter
@Getter
public class ShortUrlMappingEntity extends AppJpaBaseEntity {

    @NotBlank(message = "短网址URL不能为空")
    @Column(name="short_url" , nullable = false)
    private String shortUrl;

    @NotBlank(message = "长网址URL不能为空")
    @Column(name="long_url" , nullable = false)
    private String longUrl;

    @NotNull(message = "起始时间不能为空")
    @Column(name="start_time" , nullable = false)
    private Date startTime;

    @NotNull(message = "有效时长不能为空")
    @Column(name="valid_time" , nullable = false)
    private Integer validTime;

}
