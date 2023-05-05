package com.fosung.workbench.dto.application;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.dto.terminal.TerminalApplicationShelvesDto;
import com.fosung.workbench.entity.terminal.TerminalApplicationShelvesEntity;
import lombok.Data;

import java.util.List;


/**
 * @version V1.0
 * @Description：应用版本信息Dto
 */
@Data
public class ApplicationQueryDto extends AppBasePageParam {
	
	/**
	 * 描述:  项目id
	 * @createDate: 2021/10/23 21:44
	 * @author: gaojian
	 */
	private Long projectId;

	/**
	 * 描述:  终端id
	 * @createDate: 2021/10/23 21:45
	 * @author: gaojian
	 */
	private Long terminalId;

	/**
	 * 描述:  应用名称
	 * @createDate: 2021/10/23 21:45
	 * @author: gaojian
	 */
	private String appName ;

	/**
	 * 描述:  地市编码
	 * @createDate: 2021/11/1 9:07
	 * @author: gaojian
	 * @modify:
	 * @param null
	 * @return: 
	 */
	private String cityCode;

	private String parentId;

	private String requestType;

	/**
	 * 描述:  授权范围
	 * @createDate: 2021/10/23 21:45
	 * @author: gaojian
	 */
	private List<TerminalApplicationShelvesEntity> shelvesList;
}