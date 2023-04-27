package com.geek.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：终端导航配置Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalConfigNavigationBtmDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 导航名称
	 */
	private String navigationName ;
   /**
	 * 关联栏目
	 */
	private String menuRelate ;
   /**
	 * 链接地址
	 */
	private String navigationUrl ;
   /**
	 * 导航图标（选中）
	 */
	private String navigationLogoChecked ;
   /**
	 * 导航图标（未选中）
	 */
	private String navigationLogoNoChecked ;
   /**
	 * 强制登录
	 */
	private String forceLogin ;
   /**
	 * 排序
	 */
	private String orderNum ;
   /**
	 * 状态
	 */
	private Boolean status ;
   /**
	 * 终端id
	 */
	private String terminalId ;

	/***
	 * 描述: 地区
	 * @author fuhao
	 * @date 2021/11/9 14:09
	 **/
	private String area;

}