package com.geek.workbench.rest.controller.microcoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.dto.microcoder.TerminalConfigAgreementDto;
import com.geek.workbench.entity.microcoder.TerminalConfigAgreementEntity;
import com.geek.workbench.service.microcoder.TerminalConfigAgreementService;
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
@RequestMapping(value=TerminalConfigAgreementController.BASE_PATH)
public class TerminalConfigAgreementController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/agreement/config" ;

    @Autowired
    private TerminalConfigAgreementService agreementConfigurationInfoService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="终端管理-基础配置",optName="查询协议配置信息",optType= OptLogConst.QUERY)
	public ResponseParam query(@RequestBody TerminalConfigAgreementDto agreementConfigurationInfoDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(agreementConfigurationInfoDto, null);
        //执行分页查询
        Page<TerminalConfigAgreementEntity> agreementConfigurationInfoPage = agreementConfigurationInfoService.queryByPage(searchParam , agreementConfigurationInfoDto.getPageNum() , agreementConfigurationInfoDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> agreementConfigurationInfoList = UtilDTO.toDTO(agreementConfigurationInfoPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( agreementConfigurationInfoPage )
                .datalist( agreementConfigurationInfoList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    @OptLog(optModule="终端管理-基础配置",optName="获取协议配置信息",optType= OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询协议配置信息
        TerminalConfigAgreementEntity agreementConfigurationInfo = agreementConfigurationInfoService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( agreementConfigurationInfo ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param agreementConfigurationInfo
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @OptLog(optModule="终端管理-基础配置",optName="保存协议配置信息",optType= OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalConfigAgreementEntity  agreementConfigurationInfo) {
        //id不为空，进行更新操作，否则进行添加
        if(agreementConfigurationInfo.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(agreementConfigurationInfo, Arrays.asList("id")).keySet();
            //按照字段更新对象
			agreementConfigurationInfoService.update( agreementConfigurationInfo , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			agreementConfigurationInfoService.save(agreementConfigurationInfo);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @OptLog(optModule="终端管理-基础配置",optName="删除协议配置信息",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        agreementConfigurationInfoService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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