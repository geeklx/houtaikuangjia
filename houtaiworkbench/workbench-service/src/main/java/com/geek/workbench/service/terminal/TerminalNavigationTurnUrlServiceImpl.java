package com.geek.workbench.service.terminal;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dao.terminal.TerminalNavigationTurnUrlDao;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;
import com.geek.workbench.entity.terminal.TerminalNavigationTurnUrl;
import com.geek.workbench.service.microcoder.TerminalConfigNavigationBtmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述:  终端导航跳转路径服务实现层
 * @createDate: 2021/11/05 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class TerminalNavigationTurnUrlServiceImpl extends AppJPABaseDataServiceImpl<TerminalNavigationTurnUrl, TerminalNavigationTurnUrlDao>
	implements TerminalNavigationTurnUrlService {

	/**
	 * 描述:  底部导航信息
	 * @createDate: 2021/11/20 16:33
	 * @author: gaojian
	 */
	@Autowired
	private TerminalConfigNavigationBtmService terminalConfigNavigationBtmService;

	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("associationType","associationType:EQ");
				put("navigationTurnUrl","navigationTurnUrl:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}


	/**
	 * 描述:  保存导航跳转路径
	 *
	 * @param terminalNavigationTurnUrl
	 * @createDate: 2021/11/20 9:52
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveInfo(TerminalNavigationTurnUrl terminalNavigationTurnUrl) {

		// 1. 查询是否已有此配置
		checkConfigIsExist(terminalNavigationTurnUrl);

		// 2. 保存配置信息
		save(terminalNavigationTurnUrl);
	}

	/**
	 * 描述:  修改导航跳转路径
	 *
	 * @param terminalNavigationTurnUrl
	 * @createDate: 2021/11/20 9:53
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void updateInfo(TerminalNavigationTurnUrl terminalNavigationTurnUrl) {

		// 1. 由请求参数中获取需要更新的字段
		Set<String> updateFields = UtilDTO.toDTOExcludeFields(terminalNavigationTurnUrl, Arrays.asList("id","terminalId")).keySet();
		TerminalNavigationTurnUrl result = get(terminalNavigationTurnUrl.getId());
		if (!StringUtils.equals(result.getNavigationTurnUrl(),terminalNavigationTurnUrl.getNavigationTurnUrl())
			|| result.getAssociationType().equals(terminalNavigationTurnUrl.getAssociationType())){

			// 2. 查询是否已有此配置
			checkConfigIsExist(terminalNavigationTurnUrl);
		}

		update(terminalNavigationTurnUrl,updateFields);

		// 3. 修改绑定的此配置终端的配置信息
		if (!StringUtils.equals(result.getNavigationTurnUrl(),terminalNavigationTurnUrl.getNavigationTurnUrl())){
			TerminalConfigNavigationBtmEntity terminalConfigNavigationBtmEntity = new TerminalConfigNavigationBtmEntity();
			terminalConfigNavigationBtmEntity.setTerminalId(terminalNavigationTurnUrl.getTerminalId());
			terminalConfigNavigationBtmEntity.setAssociatedObject(terminalNavigationTurnUrl.getAssociationType());
			terminalConfigNavigationBtmEntity.setNavigationUrl(terminalNavigationTurnUrl.getNavigationTurnUrl());
			terminalConfigNavigationBtmEntity.setOldAssociatedObject(result.getAssociationType());
			terminalConfigNavigationBtmEntity.setOldNavigationUrl(result.getNavigationTurnUrl());
			entityDao.updateNavigationBtmUrl(terminalConfigNavigationBtmEntity);
		}
	}

	/**
	 * 描述:  检验配置信息是否已存在
	 * @createDate: 2021/11/20 10:25
	 * @author: gaojian
	 * @modify:
	 * @param terminalNavigationTurnUrl
	 * @return: void
	 */
	private void checkConfigIsExist(TerminalNavigationTurnUrl terminalNavigationTurnUrl){

		Map<String,Object> searchParams = new HashMap<>(8);
		searchParams.put("associationType",terminalNavigationTurnUrl.getAssociationType());
		searchParams.put("terminalId",terminalNavigationTurnUrl.getTerminalId());
		searchParams.put("navigationTurnUrl",terminalNavigationTurnUrl.getNavigationTurnUrl());
		List<TerminalNavigationTurnUrl> list = queryAll(searchParams);
		if( list != null && list.size() > 0 ){
			throw new AppException(MessageContent.CONFIG_INFO_IS_EXIST);
		}
	}

	/**
	 * 描述:  删除信息
	 *
	 * @param list
	 * @createDate: 2021/11/20 9:53
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void deleteInfo(List<AppBaseIdParam> list) {

		// 1. 判断删除url是否已绑定菜单
		list.forEach(appBaseIdParam -> {

			TerminalNavigationTurnUrl terminalNavigationTurnUrl = get(appBaseIdParam.getId());
			Map<String,Object> searchParams = new HashMap<>(8);
			searchParams.put("terminalId",terminalNavigationTurnUrl.getTerminalId());
			searchParams.put("navigationUrl",terminalNavigationTurnUrl.getNavigationTurnUrl());
			searchParams.put("associatedObject",terminalNavigationTurnUrl.getAssociationType());
			List<TerminalConfigNavigationBtmEntity> navigationBtmEntities = terminalConfigNavigationBtmService.queryAll(searchParams);
			if( navigationBtmEntities != null && navigationBtmEntities.size() > 0){
				throw new AppException("此跳转地址【"+terminalNavigationTurnUrl.getTurnName()+"】绑定了有效的导航信息，不能删除！");
			}
		});

		// 2. 执行删除
		delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	}
}
