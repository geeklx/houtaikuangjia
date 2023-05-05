package com.fosung.workbench.rest.controller.config;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.entity.config.ConfigApiParamsEntity;
import com.fosung.workbench.service.config.ConfigApiParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
@RestController
@RequestMapping(value=ConfigApiParamsController.BASE_PATH)
public class ConfigApiParamsController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/config/api/params" ;

    @Autowired
    private ConfigApiParamsService configApiParamsService ;

    /**
     * 记录分页查询
     * @param pageNum 分页号，由0开始
     * @param pageSize 每页记录数，默认为10
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(
@RequestParam(required=false, value="pagenum", defaultValue=""+DEFAULT_PAGE_START_NUM)int pageNum,
@RequestParam(required=false, value="pagesize", defaultValue=""+DEFAULT_PAGE_SIZE_NUM)int pageSize){
        //获取查询参数
        Map<String, Object> searchParam = getParametersStartingWith( getHttpServletRequest(),
                DEFAULT_SEARCH_PREFIX ) ;
        //执行分页查询
        Page<ConfigApiParamsEntity> configApiParamsPage = configApiParamsService.queryByPage(searchParam , pageNum , pageSize) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> configApiParamsList = UtilDTO.toDTO(configApiParamsPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( configApiParamsPage )
                .datalist( configApiParamsList) ;
    }
    /**
     * 获取详情数据。在url中隐藏id值 ， 查询数据对象的id以post参数方式提交。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestParam Long id){
        //查询接口参数配置表
        ConfigApiParamsEntity configApiParams = configApiParamsService.get(id) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( configApiParams ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param configApiParams
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    public ResponseParam save(@Valid ConfigApiParamsEntity configApiParams) {
        //id不为空，进行更新操作，否则进行添加
        if(configApiParams.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = getHttpServletRequest().getParameterMap().keySet() ;
			//按照字段更新对象
			configApiParamsService.update( configApiParams , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			configApiParamsService.save(configApiParams);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param ids
     * @return
     */
 	@PostMapping("delete")
    public ResponseParam delete(@RequestParam(required=true , value="ids") String ids) {
        if( UtilString.isBlank(ids) ){
			return ResponseParam.deleteFail() ;
        }
        //执行删除
        configApiParamsService.delete( toLongIds( ids ) ) ;

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