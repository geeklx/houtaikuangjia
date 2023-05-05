package com.fosung.workbench.rest.controller.sys;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dto.sys.SysNoticeDto;
import com.fosung.workbench.entity.sys.SysNoticeEntity;
import com.fosung.workbench.service.sys.SysNoticeService;
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
import java.util.Set;

/**
 * @author lihuiming
 * @className NoticeController
 * @description: 消息推送-公告
 * @date 2022/4/2510:03
 */
@RestController
@RequestMapping(value= SysNoticeController.BASE_PATH)
public class SysNoticeController extends AppIBaseController {
    @Autowired
    private SysNoticeService sysNoticeService;
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/notice" ;
    /**
     * 新增
     * @param sysNoticeEntity
     * @return
     */
    @PostMapping("save")
    @OptLog(optModule="公告管理",optName="保存公告信息",optType = OptLogConst.SAVE)
    public ResponseParam save(@Validated @RequestBody SysNoticeEntity sysNoticeEntity) {

        // 1. id 不为空进行修改操作 为空进行保存操作
        if(sysNoticeEntity.getId() != null){
            // 2. 执行修改操作
            sysNoticeService.update(sysNoticeEntity, Arrays.asList("projectId","noticeTitle"));
            return ResponseParam.updateSuccess() ;
        }else{
            // 3. 执行新增操作
            sysNoticeService.saveInfo(sysNoticeEntity);
            return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule = "终端应用版本",optName = "分页查询",optType = OptLogConst.QUERY)
    @Deprecated
    public ResponseParam query(@RequestBody SysNoticeDto sysNoticeDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysNoticeDto, null);
        //执行分页查询
        Page<SysNoticeEntity> sysNoticeEntitydPage = sysNoticeService.queryByPage(searchParam
                , sysNoticeDto.getPageNum() , sysNoticeDto.getPageSize(),new String[]{"createDatetime_desc"}) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysNoticeList = UtilDTO.toDTO(sysNoticeEntitydPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysNoticeEntitydPage )
                .datalist( sysNoticeList) ;
    }
    /**
     * 修改
     * @param sysNoticeEntity
     * @return
     */
    @PostMapping("updateStatus")
    @OptLog(optModule="公告管理",optName="保存公告信息",optType = OptLogConst.UPDATE)
    public ResponseParam updateStatus(@Validated @RequestBody SysNoticeEntity sysNoticeEntity) {
           sysNoticeEntity = sysNoticeService.get(sysNoticeEntity.getId());
           Set<String> updateFields = UtilDTO.toDTOExcludeFields(sysNoticeEntity, Arrays.asList("id")).keySet();
        // 修改信息
            sysNoticeService.update(sysNoticeEntity,Arrays.asList("id","noticeStatus"));
            return ResponseParam.updateSuccess();
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
        SysNoticeEntity sysNoticeEntity = sysNoticeService.get(param.getId()) ;

        // 3. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysNoticeEntity ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 发送公告消息
     * @return
     */
    @PostMapping("sendNoticeMessage")
    public ResponseParam sendNoticeMessage(@Validated @RequestBody SysNoticeEntity sysNoticeEntity){
        sysNoticeService.sendNoticeMessage(sysNoticeEntity);
        return ResponseParam.success().data(sysNoticeEntity);
    }
    /**
     * 发送公告消息
     * @return
     */
    @PostMapping("getNoticeDetailMessage")
    public ResponseParam getNoticeDetail(@Validated @RequestBody(required = false) SysNoticeEntity sysNoticeEntity){
        sysNoticeService.getNoticeDetailMessage(sysNoticeEntity);
        return ResponseParam.success();
    }
    /**
     * 获取详情数据。
     */
    @PostMapping("delete")
    @OptLog(optModule="项目管理",optName="获取项目详情",optType = OptLogConst.GET)
    public ResponseParam delete(@RequestBody AppBaseIdParam param){


        // 1. 非空判断
        if (null == param.getId()) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        sysNoticeService.delete(param.getId());
        return ResponseParam.deleteSuccess() ;
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
