package com.geek.system.support.system.controller.sys;
import java.util.*;
import java.util.stream.Collectors;

import com.geek.system.support.system.anno.SysLog;
import com.geek.system.support.system.dict.OptLogType;
import com.geek.system.support.system.dto.sys.SysDictTypeDto;
import com.geek.system.support.system.entity.sys.SysDictTypeEntity;
import com.geek.system.support.system.service.sys.SysDictTypeService;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Sets;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import org.springframework.web.bind.annotation.*;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;

@RestController
@RequestMapping(value=SysDictTypeController.BASE_PATH)
public class SysDictTypeController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysdicttype" ;

    @Autowired
    private SysDictTypeService sysDictTypeService ;

    /**
     * 字典分类记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysDictTypeDto sysDictTypeDto){
        //Assert.notNull(sysDictTypeDto.getProjectId(),"请选择租户！");
        if(sysDictTypeDto.getProjectId() == null){
            return ResponseParam.success()
                    .datalist(Lists.newArrayList()) ;
        }
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysDictTypeDto, null);
        //执行分页查询
        Page<SysDictTypeEntity> sysDictTypePage = sysDictTypeService.queryPage(searchParam , sysDictTypeDto.getPageNum() , sysDictTypeDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysDictTypeList = UtilDTO.toDTO(sysDictTypePage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysDictTypePage )
                .datalist( sysDictTypeList) ;
    }

    /**
     *根据appid查询字典类型
     *
     * @param sysDictType
     * @author liuke
     * @date 2021/4/12 9:12
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("/queryall")
    public ResponseParam queryByRoleId(@RequestBody SysDictTypeDto sysDictType){
        //查询资源
        Map<String,Object> searchMap = UtilDTO.toDTO(sysDictType,null);
        //searchMap.put("status", DictStatus.normal);
        List<SysDictTypeEntity> sysDictTypeEntityList = sysDictTypeService.queryAll(searchMap);
        List<Map<String, Object>> dtoObjects = UtilDTO.toDTO( sysDictTypeEntityList ,null , getDtoCallbackHandler() ) ;
        //当传入值为1时将数据处理为树
        return ResponseParam.success()
                .datalist(dtoObjects);
    }
    /**
     * 获取字典分类详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询字典类型
        SysDictTypeEntity sysDictType = sysDictTypeService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysDictType ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存字典分类实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysDictType
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
//    @SysLog(optModule = "字典管理",optName = "保存字典",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysDictTypeEntity  sysDictType) {
        //id不为空，进行更新操作，否则进行添加
        if(sysDictType.getId() != null){
			//由请求参数中获取需要更新的字段
			//Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysDictType, Arrays.asList("id")).keySet();
            SysDictTypeEntity sysDictTypeEntity = sysDictTypeService.get(sysDictType.getId());
            if(!sysDictType.getDictName().equals(sysDictTypeEntity.getDictName())){
                // 校验字典唯一性
                HashMap<String, Object> exsitParam = Maps.newHashMap();
                exsitParam.put("projectId",sysDictType.getProjectId());
                exsitParam.put("dictName",sysDictType.getDictName());
                boolean exist = sysDictTypeService.isExist(exsitParam);
                if(exist){
                    return ResponseParam.fail().message("字典名称已存在！");
                }
            }
            // 修改
            Set<String> updateFields = Sets.newHashSet();
            updateFields.add("dictName");
            updateFields.add("remark");
            //按照字段更新对象
            sysDictTypeService.update( sysDictType , updateFields ) ;
			return ResponseParam.updateSuccess() ;
        }else{
            // 校验字典唯一性
            HashMap<String, Object> exsitParam1 = Maps.newHashMap();
            exsitParam1.put("projectId",sysDictType.getProjectId());
            exsitParam1.put("dictName",sysDictType.getDictName());
            boolean exist1 = sysDictTypeService.isExist(exsitParam1);
            if(exist1){
                return ResponseParam.fail().message("字典名称已存在！");
            }
            HashMap<String, Object> exsitParam2 = Maps.newHashMap();
            exsitParam2.put("projectId",sysDictType.getProjectId());
            exsitParam2.put("dictType",sysDictType.getDictType());
            boolean exist2 = sysDictTypeService.isExist(exsitParam2);
            if(exist2){
                return ResponseParam.fail().message("字典编码已存在！");
            }
            sysDictTypeService.save(sysDictType);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @SysLog(optModule = "字典管理",optName = "删除字典",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        sysDictTypeService.deleteInfo(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 描述: 批量启动/禁用
     * @param sysDictType
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/25 10:22
     **/
    @PostMapping("update/status")
    @SysLog(optModule = "字典管理",optName = "批量启用/禁用",optType = OptLogType.BATCH_ENABLED)
    public ResponseParam batchUpdateStatus(@RequestBody List<SysDictTypeEntity>  sysDictType) {
        //id不为空，进行更新操作，否则进行添加
        if(sysDictType!=null){
            sysDictType.forEach(dict -> {
                if(dict.getId() != null){
                    //按照字段更新对象
                    sysDictTypeService.update( sysDictType , Arrays.asList("status")) ;
                }
            });
            return ResponseParam.updateSuccess() ;
        }
        return ResponseParam.updateFail();
    }

    /**
     * 描述: 复制功能(单一复制、批量复制)
     * @param dto
     * @return ResponseParam
     * @author fuhao
     * @date 2022/1/19 15:57
     **/
    @PostMapping("copy")
    @SysLog(optModule = "字典管理",optName = "复制",optType = OptLogType.SAVE)
    public ResponseParam copy(@RequestBody SysDictTypeDto dto) {
        // 判断目标租户是否存在该字典

        sysDictTypeService.copy(dto);
        return ResponseParam.success().message("复制成功！");
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