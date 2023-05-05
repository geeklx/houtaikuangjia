package com.fosung.workbench.dto.terminal;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description：终端应用身份授权Dto
 */
@Data
public class TerminalApplicationShelvesDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;

	/**
	 * 项目id
	 */
	private Long projectId;
	
     /**
	 * 终端应用配置id
	 */
	private Long appId ;
   /**
	 * 用户授权范围
	 */
	private List<String> userShelvesRange;

	/**
	 * 身份授权
	 */
	private List<String> identityShelvesRange;

	/**
	 * 组织授权
	 */
	private List<String> orgShelvesRange;

	/**
	 * 地区授权
	 */
	private List<String> areaShelvesRange;

	/**
	 * 描述: 用户父节点
	 * @author fuhao
	 * @date 2021/11/10 19:07
	 **/
	private String userShelvesRangesParents;

	/***
	 * 描述: 组织父节点
	 * @author fuhao
	 * @date 2021/11/10 19:07
	 **/
	private String orgShelvesRangeParents;

	/**
	 * 终端id
	 */
	private Long terminalId;

	/**
	 * 数据来源
	 */
	private String dataSource ;

	/**
	 * 终端应用配置id
	 */
	private Long appConfigId;

}