package com.fosung.workbench.rest.controller.application;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fosung.workbench.dto.application.ApplicationOwnerDto;
import com.fosung.workbench.entity.application.ApplicationOwnerEntity;
import com.fosung.workbench.service.application.ApplicationOwnerService;
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
@RequestMapping(value=ApplicationOwnerController.BASE_PATH)
public class ApplicationOwnerController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/applicationowner" ;

    @Autowired
    private ApplicationOwnerService applicationOwnerService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody ApplicationOwnerDto applicationOwnerDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(applicationOwnerDto, null);
        //执行分页查询
        Page<ApplicationOwnerEntity> applicationOwnerPage = applicationOwnerService.queryByPage(searchParam , applicationOwnerDto.getPageNum() , applicationOwnerDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationOwnerList = UtilDTO.toDTO(applicationOwnerPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( applicationOwnerPage )
                .datalist( applicationOwnerList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询我的应用
        ApplicationOwnerEntity applicationOwner = applicationOwnerService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( applicationOwner ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param applicationOwner
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    public ResponseParam save(@RequestBody ApplicationOwnerEntity  applicationOwner) {
        //id不为空，进行更新操作，否则进行添加
        if(applicationOwner.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(applicationOwner, Arrays.asList("id")).keySet();
            //按照字段更新对象
			applicationOwnerService.update( applicationOwner , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			applicationOwnerService.save(applicationOwner);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        //执行删除
        applicationOwnerService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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