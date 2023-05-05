package com.fosung.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

/**
 * @version V1.0
 * @Description：顶部导航栏表Dto
 */
@Data
public class TerminalConfigMenuTopDto   extends AppBasePageParam{
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 图标路径
	 */
	private String img ;
   /**
	 * 名称
	 */
	private String name ;
   /**
	 * 地址
	 */
	private String url ;
   /**
	 * 是否启用
	 */
	private Boolean enable ;
   /**
	 * 终端id
	 */
	private Long terminalId ;
   /**
	 * 父id
	 */
	private Long parentId ;
   /**
	 * 地域
	 */
	private String area ;
   /**
	 * 是否删除
	 */
	private String del ;
   /**
	 * 排序
	 */
	private Integer num ;
   /**
	 * 底部导航栏配置ID
	 */
	private Long navigationBtmId ;

	/**
	 * 导航id
	 */
	private Long navigationId ;

	/**
	 * 菜单类型
	 */
	private String menuType ;

	/**
	 * 描述: 是否末级菜单
	 */
	private Boolean isEnd;


	private String cityName;
	/**
	 * 底部导航id
	 */
	private String categoryCode;
}