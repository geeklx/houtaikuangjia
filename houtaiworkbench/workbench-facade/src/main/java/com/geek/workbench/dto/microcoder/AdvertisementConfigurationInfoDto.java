package com.geek.workbench.dto.microcoder;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Description：广告配置信息Dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdvertisementConfigurationInfoDto extends AppBasePageParam {
	
	/**
	 * id
	 */
   private  Long id;
	
     /**
	 * 终端id
	 */
	private String terminalId ;
   /**
	 * 广告logo
	 */
	private String advertisementLogo ;

}