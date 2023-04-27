package com.geek.workbench.entity.terminal;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.support.dao.entity.AppJpaBaseEntity;
import com.fosung.framework.common.support.dao.entity.AppJpaSoftDelEntity;
import com.geek.workbench.dict.ImageType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 描述:  引导页配置表实体对象
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Entity
@Table(name="terminal_image_config")
@Setter
@Getter
public class TerminalImageConfigEntity extends AppJpaBaseEntity implements AppJpaSoftDelEntity  {

	/**
     * 是否删除
     */
    @Column
    private Boolean del = Boolean.FALSE ;

	/**
	 * 图片地址
	 */
	@Column(name="img_url")
	private String imgUrl ;


	/**
	 * 链接地址
	 */
	@Column(name="link_url")
	private String linkUrl ;


	/**
	 * 开始时间
	 */
	@Column(name="start_time")
	@DateTimeFormat(pattern= AppConstants.DATE_TIME_PATTERN)
	private Date startTime ;


	/**
	 * 结束时间
	 */
	@Column(name="end_time")
	@DateTimeFormat(pattern= AppConstants.DATE_TIME_PATTERN)
	private Date endTime ;


	/**
	 * 顺序
	 */
	@Column(name="num")
	private Integer num ;


	/**
	 * 类型（advert、guide）
	 */
	@Column(name="image_type")
	@Enumerated(EnumType.STRING)
	private ImageType imageType ;


	/**
	 * 终端id
	 */
	@NotNull( message = "终端主键信息不能为空！" )
	@Column(name="terminal_id")
	private Long terminalId ;

}