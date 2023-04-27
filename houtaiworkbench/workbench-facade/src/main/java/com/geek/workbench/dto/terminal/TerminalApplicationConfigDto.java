package com.geek.workbench.dto.terminal;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.dict.AppType;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @version V1.0
 * @Description：终端应用配置信息Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalApplicationConfigDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 应用id
	 */
	private Long appId ;
   /**
	 * 应用名称
	 */
	private String appName ;
   /**
	 * 应用描述
	 */
	private String remark ;
   /**
	 * 应用图标
	 */
	private String appIcon ;
   /**
	 * 应用上下架
	 */
	private String appShelves ;
   /**
	 * 是否登录
	 */
	private String signIn ;
   /**
	 * 终端id
	 */
	private Long terminalId ;
   /**
	 * 升级方式
	 */
	private String upgradeMode ;
   /**
	 * 升级时间
	 */
	private Date upgradeTime ;

	/**
	 * 项目id
	 */
	private Long projectId;

	/**
	 * 类型参数集合
	 */
	private List<AppType> typeParams;

	/**
	 * 分类id
	 */
	private String categoryCode;

	/**
	 * 应用版本id
	 */
	private Long appVersionId;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = AppConstants.DATE_TIME_PATTERN)
	private Date startTime;

	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = AppConstants.DATE_TIME_PATTERN)
	private Date endTime;

	/**
	 * 启动参数
	 */
	private String startParam;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 版本名称
	 */
	private String versionName;

	/**
	 * 描述:  数据来源
	 * @createDate: 2021/10/22 19:12
	 * @author: gaojian
	 * @modify:
	 * @param null
	 * @return:
	 */
	private String dataSource;

	//终端编码
	private String terminalCode;

	/**************************************权限相关*********************************************/

	/**
	 * 用户id
	 */
	private String userId;
	//用户名
	private String u;
	//密码
	private String p;

	/**
	 * 身份
	 */
	private Set<String> identityIds = Sets.newHashSet();

	/**
	 * 组织id
	 */
	private Set<String> ordIds = Sets.newHashSet();
}