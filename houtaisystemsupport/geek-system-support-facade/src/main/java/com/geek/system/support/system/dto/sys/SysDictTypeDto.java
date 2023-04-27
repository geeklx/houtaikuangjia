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
 * @Description：字典类型Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictTypeDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 字典名
	 */
	private String dictName ;

	/**
	 * 字典名
	 */
	private String dictNameL ;
   /**
	 * 字典类型
	 */
	private String dictType ;
   /**
	 * 备注
	 */
	private String remark ;
   /**
	 * 系统id
	 */
	private Long appId ;
	/**
	 * 项目id
	 */
	private Long projectId ;

	/**
	 * 描述: 目标租户
	 * @author fuhao
	 * @date 2022/1/19 15:56
	 **/
	private Long targetProjectId;

	/**
	 * 描述: 字典类型多个
	 * @author fuhao
	 * @date 2022/1/19 16:01
	 **/
	private List<String> dictTypes;

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

	private String status;
}