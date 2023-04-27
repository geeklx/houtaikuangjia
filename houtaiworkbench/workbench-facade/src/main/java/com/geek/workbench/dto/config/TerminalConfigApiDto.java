package com.geek.workbench.dto.config;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.dict.BindCategoryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

/**
 * 描述:  终端api配置表dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TerminalConfigApiDto extends AppBasePageParam {
	
   @NotEmpty
   private  Long id;
	

	private Long terminalId ;

	@Enumerated(EnumType.STRING)
	private BindCategoryType bindCategory ;


	private String bindType ;


	private Long bindGroupId ;

	private String apiCode;

}