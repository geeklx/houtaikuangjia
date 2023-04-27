package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.system.support.system.util.ProjectConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Description：岗位表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysProjectDto extends AppBasePageParam {

	/*
	 * 描述: 物理主键
	 * @author fuhao
	 * @date 2021/11/25 19:47
	 **/
	private Long id;

	/**
	 * 删除标志
	 */
	private Boolean del;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 描述: 租户名称模糊查询
	 * @author fuhao
	 * @date 2021/12/29 9:06
	 **/
	private String projectNameL;

	/**
	 * 项目描述
	 */
	private String remark;

	/**
	 * 项目责任人
	 */
	private String projectManager;

	/**
	 * 项目编码
	 */
	private String projectCode;

	/*
	 * 描述: 开始时间
	 * @author fuhao
	 * @date 2021/11/25 10:44
	 **/
	@DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
	private Date startTime;

	/*
	 * 描述: 结束时间
	 * @author fuhao
	 * @date 2021/11/25 10:44
	 **/
	@DateTimeFormat(pattern= ProjectConstant.FORMAT_SECOND)
	private Date endTime;

	/**
	 * 描述: 应用id集合
	 * @author fuhao
	 * @date 2021/11/25 18:38
	 **/
	private List<Long> appIds;

	/**
	 * 描述: 应用名称
	 * @author fuhao
	 * @date 2021/11/25 19:55
	 **/
	private String appName;

}