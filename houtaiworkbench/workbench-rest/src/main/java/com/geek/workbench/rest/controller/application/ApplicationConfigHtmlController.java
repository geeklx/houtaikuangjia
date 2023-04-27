package com.geek.workbench.rest.controller.application;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.common.AppMessageContent;
import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dict.TerminalType;
import com.geek.workbench.dto.application.ApplicationConfigHtmlDto;
import com.geek.workbench.entity.application.ApplicationConfigHtmlEntity;
import com.geek.workbench.entity.application.ApplicationVersionEntity;
import com.geek.workbench.service.application.ApplicationConfigHtmlService;
import com.geek.workbench.service.application.ApplicationVersionService;
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
 * 描述:  H5应用控制层
 * @createDate: 2021/10/23 23:11
 * @author: gaojian
 */
@RestController
@RequestMapping(value=ApplicationConfigHtmlController.BASE_PATH)
public class ApplicationConfigHtmlController extends AbstractApplicationConfig {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/application/config/html" ;

    /**
     * 描述:  注入H5应用配置服务
     * @createDate: 2021/10/23 23:19
     * @author: gaojian
     */
    @Autowired
    private ApplicationConfigHtmlService applicationConfigHtmlService ;

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
    @OptLog(optModule="应用中心-应用管理",optName="查询H5高级配置信息-分页",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody ApplicationConfigHtmlDto applicationConfigHtmlDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(applicationConfigHtmlDto, null);
        //执行分页查询
        Page<ApplicationConfigHtmlEntity> applicationConfigHtmlPage = applicationConfigHtmlService.queryByPage(searchParam , applicationConfigHtmlDto.getPageNum() , applicationConfigHtmlDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationConfigHtmlList = UtilDTO.toDTO(applicationConfigHtmlPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( applicationConfigHtmlPage )
                .datalist( applicationConfigHtmlList) ;
    }

    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="应用中心-应用管理",optName="获取5高级配置信息",optType= OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){

        // 1. 查询h5页面高级配置
        ApplicationConfigHtmlEntity applicationConfigHtml = applicationConfigHtmlService.get(param.getId()) ;

        // 2. 将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( applicationConfigHtml ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param applicationConfigHtml
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="应用中心-版本升级",optName="升级应用信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@Validated @RequestBody ApplicationConfigHtmlEntity  applicationConfigHtml) {

        // 1. 校验包名
        checkPackageName(applicationConfigHtml);

        // 2. 判断版本是否大于历史版本
        ApplicationVersionEntity applicationVersionEntity = new ApplicationVersionEntity();
        applicationVersionEntity.setAppId(applicationConfigHtml.getAppId());
        applicationVersionEntity.setAppType(TerminalType.android);
        Integer maxVersionNum = applicationVersionService.queryMaxVersionNum(applicationVersionEntity);

        // 1. id不为空，进行更新操作，否则进行添加
        if(applicationConfigHtml.getId() != null){

            if(applicationConfigHtml.getVersionNum() != null && maxVersionNum > applicationConfigHtml.getVersionNum()){
                throw new AppException(AppMessageContent.VERSION_NUM_SMALL);
            }

            // 2. 按照字段更新对象
            applicationConfigHtmlService.updateApp( applicationConfigHtml  );
            return ResponseParam.updateSuccess().data(applicationConfigHtml);
        }else{

            if(applicationConfigHtml.getVersionNum() != null && maxVersionNum >= applicationConfigHtml.getVersionNum()){
                throw new AppException(AppMessageContent.VERSION_NUM_SMALL);
            }

            // 3. 升级应用版本
			applicationConfigHtmlService.upgradeApp(applicationConfigHtml);
			return ResponseParam.saveSuccess().data(applicationConfigHtml);
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="应用中心-应用管理",optName="删除H5高级配置信息",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {

        // 1. 非空判断
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }

        // 2. 执行删除
        applicationConfigHtmlService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
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