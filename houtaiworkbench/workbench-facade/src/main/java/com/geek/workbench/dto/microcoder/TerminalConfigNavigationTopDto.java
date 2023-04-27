package com.geek.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.dict.MenuType;
import com.geek.workbench.dict.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：顶部导航表Dto
 */
@EqualsAndHashCode(callSuper = true)
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