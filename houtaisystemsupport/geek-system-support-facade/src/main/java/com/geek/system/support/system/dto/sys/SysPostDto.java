package com.geek.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.system.support.system.dict.PostStatus;
import com.geek.system.support.system.util.ProjectConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @version V1.0
 * @Description：岗位表Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPostDto extends AppBasePageParam {

	/**
	 * 岗位名称
	 */
	private String postName ;


	/**
	 * 岗位编码
	 */
	private String postCode ;


	/**
	 * 排序
	 */
	private Integer num ;


	/**
	 * 状态（0正常 1停用）
	 */

	private PostStatus status ;


	/**
	 * 备注
	 */
	private String remark ;


	/**
	 * 系统id
	 */
	private Long projectId ;

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


}