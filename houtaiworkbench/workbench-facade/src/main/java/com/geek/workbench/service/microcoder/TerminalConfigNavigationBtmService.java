package com.geek.workbench.service.microcoder;

import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.support.service.AppBaseDataService;
import com.geek.workbench.AppBean.AppNavicationBtm;
import com.geek.workbench.dto.config.AppSearchParamDto;
import com.geek.workbench.dto.microcoder.TerminalConfigNavigationBtmDto;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;

import java.util.List;

public interface TerminalConfigNavigationBtmService extends AppBaseDataService<TerminalConfigNavigationBtmEntity, Long> {
	

	/**
	 * 导航编辑功能
	 * @param navigationConfigurationInfo
	 * @return
	 * @author fuhao
	 * @date 2021/9/30 10:02
	 */
	int updateById(TerminalConfigNavigationBtmEntity navigationConfigurationInfo);

	List<AppNavicationBtm> getTerminalConfigNavigationBtms(AppSearchParamDto appSearchParamDto);

	/**
	 * 描述: 查询终端下最大的排序
	 * @param terminalConfigNavigationBtmEntity
	 * @return java.lang.Integer
	 * @author fuhao
	 * @date 2021/11/4 14:22
	 **/
	Integer queryMaxNum(TerminalConfigNavigationBtmEntity terminalConfigNavigationBtmEntity);

	/***
	 * 描述: 获取底部导航下拉框
	 * @param dto
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalConfigNavigationBtmEntity>
	 * @author fuhao
	 * @date 2021/11/9 14:15
	 **/
	List<TerminalConfigNavigationBtmEntity> btmNavOption(TerminalConfigNavigationBtmDto dto);

	/**
	 * 描述: 级联删除所有关于底部导航信息
	 * @param list
	 * @return void
	 * @author fuhao
	 * @date 2021/11/9 16:00
	 **/
	void deleteByBtmNav(List<AppBaseIdParam> list);
}

