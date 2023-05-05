package com.fosung.workbench.rest.controller.sys;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.sys.SysDictDataDto;
import com.fosung.workbench.entity.sys.SysDictDataEntity;
import com.fosung.workbench.service.sys.SysDictDataService;
import com.fosung.workbench.service.sys.SysDictTypeService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value=SysDictDataController.BASE_PATH)
public class SysDictDataController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysdictdata" ;


    @Autowired
    private SysDictDataService sysDictDataService ;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 字典数据记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysDictDataDto sysDictDataDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysDictDataDto, null);
        //执行分页查询
        Page<SysDictDataEntity> sysDictDataPage = sysDictDataService.queryByPage(searchParam , sysDictDataDto.getPageNum() , sysDictDataDto.getPageSize(),new String[]{"num:asc","createDatetime:asc"}) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysDictDataList = UtilDTO.toDTO(sysDictDataPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysDictDataPage )
                .datalist( sysDictDataList) ;
    }

    /**
     *不分页查询全部字典数据
     *
     * @param sysDictDataDto
     * @author liuke
     * @date 2021/5/6 9:18
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/queryall")
    public ResponseParam queryByRoleId(@RequestBody SysDictDataDto sysDictDataDto){
        //查询资源
        Map<String,Object> searchMap = UtilDTO.toDTO(sysDictDataDto,null);
        //将实体转换为数据传输对象
        List<SysDictDataEntity> sysDictDataEntityList = sysDictDataService.queryAll(searchMap,new String[]{"num:asc","createDatetime:asc"});
        List<Map<String, Object>> dtoObjects = UtilDTO.toDTO( sysDictDataEntityList ,null , getDtoCallbackHandler() ) ;
        //当传入值为1时将数据处理为树
        return ResponseParam.success()
                .datalist(dtoObjects);
    }
    /**
     * 根据id获取字典详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询字典数据
        SysDictDataEntity sysDictData = sysDictDataService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysDictData ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存字典数据实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysDictData
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule = "字典管理",optName = "保存字典项",optType = OptLogConst.SAVE)
    public ResponseParam save(@RequestBody SysDictDataEntity  sysDictData) {

 	    if(StringUtils.isBlank(sysDictData.getDictType())){
 	        return ResponseParam.fail().message("字典编码不能为空!");
        }
        //id不为空，进行更新操作，否则进行添加
        if(sysDictData.getId() != null){
            SysDictDataEntity sysDictDataEntity = sysDictDataService.get(sysDictData.getId());
            if(null == sysDictDataEntity){
                return ResponseParam.fail().message("未找到该数据!");
            }
            if(!sysDictDataEntity.getDictLabel().equals(sysDictData.getDictLabel())){
                // 在同一租户、同一字典下、字典项要求唯一
                Map<String, Object> existParam1 = Maps.newHashMap();
                existParam1.put("dictLabel",sysDictData.getDictLabel());
                existParam1.put("dictType",sysDictData.getDictType());
                boolean exist1 = sysDictDataService.isExist(existParam1);
                if(exist1){
                    return ResponseParam.fail().message("本类型下字典项名称已经存在！");
                }
            }
            if(!sysDictDataEntity.getDictValue().equals(sysDictData.getDictValue())) {
                Map<String, Object> existParam2 = Maps.newHashMap();
                existParam2.put("dictValue",sysDictData.getDictValue());
                existParam2.put("dictType",sysDictData.getDictType());
                boolean exist2 = sysDictDataService.isExist(existParam2);
                if(exist2){
                    return ResponseParam.fail().message("本类型下字典项的值已经存在！");
                }
            }
            //由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysDictData, Arrays.asList("id")).keySet();
			//按照字段更新对象
			sysDictDataService.update( sysDictData , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
            // 在同一租户、同一字典下、字典项要求唯一
            Map<String, Object> existParam1 = Maps.newHashMap();
            existParam1.put("dictLabel",sysDictData.getDictLabel());
            existParam1.put("dictType",sysDictData.getDictType());
            boolean exist1 = sysDictDataService.isExist(existParam1);
            if(exist1){
                return ResponseParam.fail().message("本类型下字典项名称已经存在！");
            }

            Map<String, Object> existParam2 = Maps.newHashMap();
            existParam2.put("dictValue",sysDictData.getDictValue());
            existParam2.put("dictType",sysDictData.getDictType());
            boolean exist2 = sysDictDataService.isExist(existParam2);
            if(exist2){
                return ResponseParam.fail().message("本类型下字典项的值已经存在！");
            }
            sysDictDataService.save(sysDictData);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除字典信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule = "字典管理",optName = "删除字典项",optType = OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        sysDictDataService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 根据类型获取字典
     * @param dictType
     * @return
     */
    @PostMapping("getdictdata")
    public ResponseParam querySysTemDict(@RequestParam("dictType") String dictType) {
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("dictType",dictType);
        List<SysDictDataEntity> sysDictDataEntities = sysDictDataService.queryAll(searchParam);
        List<Map<String,Object>> results = UtilDTO.toDTO(sysDictDataEntities,null,getDtoCallbackHandler());
        return ResponseParam.success().datalist(results);
    }

    /**
     *查询字典
     *
     * @author liuke
     * @date 2022/5/8 15:18
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @PostMapping("/dict")
    public ResponseParam queryDictByTypes(@RequestBody SysDictDataEntity sysDictDataEntity){
        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("dictType",sysDictDataEntity.getDictType());
        return ResponseParam.success().datalist(sysDictTypeService.queryDictByType(searchParam));
    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        //创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}