package com.fosung.workbench.rest.controller.application;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.common.AppMessageContent;
import com.fosung.workbench.common.MessageContent;
import com.fosung.workbench.config.OptLog;
import com.fosung.workbench.config.OptLogConst;
import com.fosung.workbench.dict.AppType;
import com.fosung.workbench.dict.TerminalType;
import com.fosung.workbench.dto.application.ApplicationConfigAndroidDto;
import com.fosung.workbench.entity.application.ApplicationBasicEntity;
import com.fosung.workbench.entity.application.ApplicationConfigAndroidEntity;
import com.fosung.workbench.entity.application.ApplicationVersionEntity;
import com.fosung.workbench.service.application.ApplicationBasicService;
import com.fosung.workbench.service.application.ApplicationConfigAndroidService;
import com.fosung.workbench.service.application.ApplicationVersionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述:  安卓应用控制层
 * @createDate: 2021/10/23 23:11
 * @author: gaojian
 */
@RestController
@RequestMapping(value=ApplicationConfigAndroidController.BASE_PATH)
public class ApplicationConfigAndroidController extends AbstractApplicationConfig {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/application/config/android" ;

    /**
     * 描述:  注入安卓应用服务
     * @createDate: 2021/10/23 23:12
     * @author: gaojian
     */
    @Autowired
    private ApplicationConfigAndroidService applicationConfigAndroidService ;

    /**
     * 描述:  注入版本服务
     * @createDate: 2021/10/27 16:50
     * @author: gaojian
     */
    @Autowired
    private ApplicationVersionService applicationVersionService;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="应用中心-应用管理",optName="查询安卓高级配置信息-分页",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody ApplicationConfigAndroidDto applicationConfigAndroidDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(applicationConfigAndroidDto, null);
        //执行分页查询
        Page<ApplicationConfigAndroidEntity> applicationConfigAndroidPage = applicationConfigAndroidService.queryByPage(searchParam , applicationConfigAndroidDto.getPageNum() , applicationConfigAndroidDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationConfigAndroidList = UtilDTO.toDTO(applicationConfigAndroidPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( applicationConfigAndroidPage )
                .datalist( applicationConfigAndroidList) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="应用中心-应用管理",optName="获取安卓应用配置信息",optType= OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){

        // 1. 查询安卓高级页面配置
        ApplicationConfigAndroidEntity applicationConfigAndroid = applicationConfigAndroidService.get(param.getId()) ;

        // 2. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( applicationConfigAndroid ,null , getDtoCallbackHandler() ) ;
        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="应用中心-版本升级",optName="升级应用信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@Validated @RequestBody ApplicationConfigAndroidEntity applicationConfigAndroid) {

        // 1. 校验包名
        checkPackageName(applicationConfigAndroid);

        // 2. 判断版本是否大于历史版本
        ApplicationVersionEntity applicationVersionEntity = new ApplicationVersionEntity();
        applicationVersionEntity.setAppId(applicationConfigAndroid.getAppId());
        applicationVersionEntity.setAppType(TerminalType.android);
        Integer maxVersionNum = applicationVersionService.queryMaxVersionNum(applicationVersionEntity);

        // 3. id不为空，进行更新操作，否则进行添加
        if(applicationConfigAndroid.getId() != null){

            if(applicationConfigAndroid.getVersionNum() != null && maxVersionNum > applicationConfigAndroid.getVersionNum()){
                throw new AppException(AppMessageContent.VERSION_NUM_SMALL);
            }

            // 3.1. 按照字段更新对象
            applicationConfigAndroidService.updateApp( applicationConfigAndroid  );
            return ResponseParam.updateSuccess().data(applicationConfigAndroid);
        }else{

            if(applicationConfigAndroid.getVersionNum() != null && maxVersionNum >= applicationConfigAndroid.getVersionNum()){
                throw new AppException(AppMessageContent.VERSION_NUM_SMALL);
            }

            // 3.2 升级应用
			applicationConfigAndroidService.upgradeApp(applicationConfigAndroid);
			return ResponseParam.saveSuccess().data(applicationConfigAndroid);
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="应用中心-应用管理",optName="删除安卓高级配置信息-分页",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        applicationConfigAndroidService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
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

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler,true);
    }

}