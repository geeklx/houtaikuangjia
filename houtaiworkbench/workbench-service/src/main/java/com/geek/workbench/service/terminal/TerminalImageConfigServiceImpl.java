package com.geek.workbench.service.terminal;

import java.util.*;

import com.fosung.framework.common.config.AppConstants;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilDate;
import com.geek.workbench.common.MessageContent;
import com.geek.workbench.dao.terminal.TerminalImageConfigDao;
import com.geek.workbench.dict.ImageType;
import com.geek.workbench.entity.terminal.TerminalImageConfigEntity;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;

/**
 * 描述:  终端图片配置服务实现类
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@Service
public class TerminalImageConfigServiceImpl extends AppJPABaseDataServiceImpl<TerminalImageConfigEntity, TerminalImageConfigDao>
	implements TerminalImageConfigService {
	
	/**
	 * 查询条件表达式
	 */
	private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
			{
				put("terminalId","terminalId:EQ");
				put("imageType","imageType:EQ");
				put("startTime","startTime:LTEDATE");
				put("endTime","endTime:GTEDATE");
				put("imageType","imageType:EQ");
	       }
	};
	
	@Override
	public Map<String, String> getQueryExpressions() {
		return expressionMap ;
	}



	/**
	 *
	 * 根据终端获取应用
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalImageConfigEntity> getCacheTerminalImgByTerminal(Long terminalId){
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("terminalId",terminalId);
		searchParam.put("startTime", UtilDate.getCurrentDateFormatStr(AppConstants.DATE_TIME_PATTERN));
		searchParam.put("endTime",UtilDate.getCurrentDateFormatStr(AppConstants.DATE_TIME_PATTERN));
		searchParam.put("imageType", ImageType.advert);
		return queryAll(searchParam,new String[]{"num"});
	}
	/**
	 *
	 * 根据终端获取应用
	 * @param terminalId
	 * @author liuke
	 * @date 2021/10/18 10:05
	 * @return java.util.List<com.fosung.workbench.entity.terminal.TerminalApplicationConfigEntity>
	 */
	@Override
	public List<TerminalImageConfigEntity> getCacheTerminalGuideImgByTerminal(Long terminalId){
		Map<String,Object> searchParam = Maps.newHashMap();
		searchParam.put("terminalId",terminalId);
		searchParam.put("imageType", ImageType.guide);
		return queryAll(searchParam,new String[]{"num"});
	}
	/**
	 * 描述:  保存信息
	 *
	 * @param list
	 * @createDate: 2021/10/25 18:37
	 * @author: gaojian
	 * @modify:
	 * @return: void
	 */
	@Override
	public void saveInfo(List<TerminalImageConfigEntity> list) {

		// 1. 非空判断
		if(list == null || list.isEmpty()){
			throw new AppException(MessageContent.PARAMS_IS_NULL);
		}

		// 2. 删除以前的配置
		entityDao.deleteAllByTerminalIdAndImageType(list.get(0).getTerminalId(),list.get(0).getImageType());

		// 3. 循环插入数据
		saveBatch(list);

	}

}
