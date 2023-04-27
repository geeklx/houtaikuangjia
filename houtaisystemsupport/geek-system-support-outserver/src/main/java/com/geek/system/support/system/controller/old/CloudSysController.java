package com.geek.system.support.system.controller.old;

import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.entity.sys.SysDictDataEntity;
import com.geek.system.support.system.service.sys.SysDictDataService;
import com.geek.system.support.system.service.sys.SysProjectService;
import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/{source}/api/cloud/app")
public class CloudSysController extends AppIBaseController {

	@Autowired
	private SysDictDataService sysDictDataService;

	@Autowired
	private SysProjectService sysProjectService;

	/**
	 * @param @param  code
	 * @param @return
	 * @return List<Map>
	 * @throws
	 * @Title: queryAppDictionary
	 * @Description: 根据分类code查询下级字典项
	 */
	@PostMapping(value = "/dictionary/runtime")
	List<Map<String, String>> queryAppDictionary(@PathVariable("source") String source,
			@RequestParam(name = "code") String code) {
		// 通过租户编码查询租户id
		Long projectId = sysProjectService.getProjectId(source);
		// 根据分类code查询下级字典项
		HashMap<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("projectId",projectId);
		searchParams.put("dictType",code);
		List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(searchParams);
		List<Map<String, String>> result = Lists.newArrayList();
		sysDictDataEntities.forEach(data -> {
			HashMap<String, String> resultMap = Maps.newHashMap();
			resultMap.put("code",data.getDictValue());
			resultMap.put("name",data.getDictLabel());
			result.add(resultMap);
		});
		return result;
	}

	/**
	 * 灯塔字典项(民族、学历)
	 *
	 * @param dictType
	 * @param code
	 * @return
	 */
	@PostMapping(value = "/dt/dict")
	Map<String, Object> dtDict(@PathVariable("source") String source, @RequestParam("dictType") String dictType,
			@RequestParam("code") String code){
		Long projectId = sysProjectService.getProjectId(source);
		Map<String, Object> result = Maps.newHashMap();
		HashMap<String, Object> searchParams = Maps.newHashMap();
		if("DEGREE".equals(dictType)){
			searchParams.put("dictType","DT_DEGREE");
		}else if("NATION".equals(dictType)){
			searchParams.put("dictType","DT_NATION");
		}
		searchParams.put("projectId",projectId);
		searchParams.put("dictValue",code);
		List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(searchParams);
		if(UtilCollection.isNotEmpty(sysDictDataEntities)){
			SysDictDataEntity sysDictDataEntity = sysDictDataEntities.get(0);
			result.put(sysDictDataEntity.getDictLabel(),sysDictDataEntity.getDictValue());
		}else {
			return null;
		}
		return result;
	}

	/**
	 * 描述: 根据code查询单个字典项
	 * @param source
	 * @param code
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author fuhao
	 * @date 2022/2/21 13:56
	 **/
	@PostMapping(value = "/dictionary")
	Map<String, Object> queryByCode(@PathVariable("source") String source, @RequestParam("code") String code){
		Long projectId = sysProjectService.getProjectId(source);
		HashMap<String, Object> dictDataParams = Maps.newHashMap();
		dictDataParams.put("projectId",projectId);
		dictDataParams.put("dictValue",code);
		List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(dictDataParams);
		if(UtilCollection.isNotEmpty(sysDictDataEntities)){
			SysDictDataEntity sysDictDataEntity = sysDictDataEntities.get(0);
			HashMap<String, Object> result = Maps.newHashMap();
			result.put("code",sysDictDataEntity.getDictValue());
			result.put("name",sysDictDataEntity.getDictLabel());
			result.put("num",sysDictDataEntity.getNum());
			return result;
		}else {
            return null;
        }
	}

	/**
	 * 描述: 通过字典编码查询字典项包括过滤查询和要查询的字段
	 * @param source
	 * @param code
	 * @param excludeCodes
	 * @param includeCodes
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
	 * @author fuhao
	 * @date 2022/2/21 13:59
	 **/
	@PostMapping("/dictionary/condition")
	List<Map<String, String>> queryAppDictionExcludeAndInclude(@PathVariable("source") String source,
			@RequestParam("code") String code, @RequestParam("excludeCodes") String excludeCodes,
			@RequestParam("includeCodes") String includeCodes){
		Long projectId = sysProjectService.getProjectId(source);
		HashMap<String, Object> dictParams = Maps.newHashMap();
		String[] includes = includeCodes.split(",");
		List<String> includesList = Arrays.asList(includes);
		if(UtilCollection.isNotEmpty(includesList)){
			dictParams.put("dictValues",includesList);
		}
		String[] excludes = excludeCodes.split(",");
		List<String> excludesList = Arrays.asList(excludes);
		if(UtilCollection.isNotEmpty(excludesList)){
			dictParams.put("dictValuesNotIn",excludeCodes);
		}
		dictParams.put("projectId",projectId);
		dictParams.put("dictType",code);
		List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(dictParams);
		List<Map<String,String>> result = Lists.newArrayList();
		sysDictDataEntities.forEach(dictData -> {
			HashMap<String, String> resultMap = Maps.newHashMap();
			resultMap.put("name",dictData.getDictLabel());
			resultMap.put("code",dictData.getDictValue());
			result.add(resultMap);
		});
		return result;
	}

	/**
	 * 描述: 通过字典编码查询全部字典项
	 * @param source
	 * @param code
	 * @return com.fosung.framework.web.http.ResponseParam
	 * @author fuhao
	 * @date 2022/2/21 14:00
	 **/
	@PostMapping("/dictionary/path")
	ResponseParam queryLikePath(@PathVariable("source") String source, @RequestParam("code") String code){
		Long projectId = sysProjectService.getProjectId(source);
		HashMap<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("projectId",projectId);
		searchParams.put("dictType",code);
		List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(searchParams);
		List<Map<String,String>> result = Lists.newArrayList();
		sysDictDataEntities.forEach(dictData -> {
			HashMap<String, String> resultMap = Maps.newHashMap();
			resultMap.put("name",dictData.getDictLabel());
			resultMap.put("code",dictData.getDictValue());
			result.add(resultMap);
		});
		return ResponseParam.success().data(result);
	}
	/* @PostMapping("/post")
	ResponseParam queryAllPost();*/

	/**
	 *获取系统管理字典数据
	 *
	 * @param source
	 * @param dictType
	 * @date 2021/4/23 9:47
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping("/querysystemdict")
	ResponseParam querySysTemDict(@PathVariable("source") String source,@RequestParam("dictType") String dictType){

		return null;
	}

	/**
	 * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
	 *
	 * @return
	 */
	public DTOCallbackHandler getDtoCallbackHandler() {

		//创建转换接口类
		DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
			@Override
			public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {

			}
		};

		return getDTOCallbackHandlerProxy(dtoCallbackHandler, true);
	}
}
