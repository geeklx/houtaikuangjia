package com.fosung.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.fosung.workbench.dict.MenuType;
import com.fosung.workbench.dict.StatusType;
import lombok.Data;

/**
 * @version V1.0
 * @Description：顶部导航表Dto
 */
@Data
public class TerminalConfigNavigationTopDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 导航名称
	 */
	private String navigationName ;

   /**
	 * 底部导航id
	 */
	private Long navigationBtmId ;
	/**
	 * 顶部导航id
	 */
	private Long terminalId;
   /**
	 * 菜单类型
	 */
	private MenuType menuType ;

	/**
	 * 描述:  地域
	 */
	private String area ;
	private StatusType status;

}