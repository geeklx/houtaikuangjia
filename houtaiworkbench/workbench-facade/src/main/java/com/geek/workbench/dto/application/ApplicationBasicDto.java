package com.geek.workbench.dto.application;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.dict.AppType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @version V1.0
 * @Description：应用基本信息表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationBasicDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 应用名称
	 */
	private String appName ;
   /**
	 * 应用编码
	 */
	private String appCode ;
   /**
	 * 图标
	 */
	private String iconUrl ;
   /**
	 * 排序
	 */
	private Integer num ;
   /**
	 * 备注简介
	 */
	private String remark ;
	/**
	 * 应用类型（app原生，h5）
	 */
	@Column(name="app_type")
	@Enumerated(EnumType.STRING)
	private AppType appType ;
   /**
	 * 应用提供方
	 */
	private String appSupport ;
   /**
	 * 所属项目id
	 */
	private Long projectId ;
   /**
	 * 应用所属分类
	 */
	private String categoryCode ;
   /**
	 * 审核状态
	 */
	private String examineFlag ;
   /**
	 * 负责人
	 */
	private String person ;
   /**
	 * 数据来源
	 */
	private String dataSource ;

   /**
	 * 外部应用唯一id
	 */
	private Long externalId ;
	/**
	 * 内部应用 / 外部应用
	 */
	private Boolean inOrOut;
   /**
	 * 是否维护
	 */
	private Boolean maintain ;
   /**
	 * 维护内容
	 */
	private String maintainMessage ;

	/**
	 * 维护内容URL
	 */
	private String maintainUrl;
   /**
	 * 创建人名称
	 */
	private String createrName ;
   /**
	 * 是否删除
	 */
	private Boolean del ;

	/**
	 * 包名
	 */
	private String packageName;

	/**
	 * 开始时间
	 */
	private String startTime ;
	/**
	 * 结束时间
	 */
	private String endTime ;

}