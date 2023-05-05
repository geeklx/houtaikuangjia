package com.fosung.workbench.rest.controller.sys;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.sys.SysDictDto;
import com.fosung.workbench.entity.sys.SysDictEntity;
import com.fosung.workbench.service.sys.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lihuiming
 * @className DictController
 * @description: 字典管理
 * @date 2022/5/611:15
 */
@RestController
@RequestMapping(value= SysDictController.BASE_PATH)
public class SysDictController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/dict" ;
    /**
     * 描述:  注入项目基础信息服务
     * @createDate: 2022/5/611:15
     * @author: lihuiming
     */
    @Autowired
    private SysDictService sysDictService ;
    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端管理",optName = "分页查询",optType = OptLogConst.QUERY)
    public ResponseParam query(@RequestBody SysDictDto sysDictDto) {
        // 1. 获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysDictDto, null);
        searchParam.put("parentId",0);
        //执行分页查询
        Page<SysDictEntity> sysDictPage = sysDictService.queryByPage(searchParam,sysDictDto.getPageNum(),sysDictDto.getPageSize());

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> terminalBasicInfoList = UtilDTO.toDTO(sysDictPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysDictPage )
                .datalist( terminalBasicInfoList) ;
    }

    /**
     * 新增
     * @param sysDictEntity
     * @return
     */
    @PostMapping("save")
    @OptLog(optModule="字典管理",optName="保存字典信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@Validated @RequestBody SysDictEntity sysDictEntity) {

        // 1. id 不为空进行修改操作 为空进行保存操作
        if(sysDictEntity.getId() != null){
            // 2. 执行修改操作
            sysDictService. update(sysDictEntity, Arrays.asList("dictCode"));
            return ResponseParam.updateSuccess() ;
        }else{

            // 3. 执行新增操作
            sysDictService.saveInfo(sysDictEntity);
            return ResponseParam.saveSuccess() ;
        }
    }
    /**
     * 获取详情数据。
     */
    @PostMapping("get")
    @OptLog(optModule="项目管理",optName="获取项目详情",optType = OptLogConst.GET)
    public ResponseParam get(@RequestBody AppBaseIdParam param){

        // 1. 主键信息非空判断
        Assert.notNull(param.getId(), MessageContent.ID_IS_NULL);

        // 2. 查询项目基本信息
        SysDictEntity sysNoticeEntity = sysDictService.get(param.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysNoticeEntity ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 删除信息
     * @param param
     * @return
     */
    @PostMapping("delete/id")
    @OptLog(optModule="项目管理",optName="删除项目信息",optType= OptLogConst.DELETE )
    public ResponseParam delete(@RequestBody AppBaseIdParam  param) {

        // 1. 删除请求结果集为空校验
        if (null == param || null == param.getId()) {
            return ResponseParam.deleteFail();
        }
        // 2. 执行删除
        sysDictService.delete(param.getId());
        return ResponseParam.deleteSuccess() ;
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
                dtoMap.remove("del");
            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }
}
