package com.geek.workbench.rest.controller.application;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.geek.workbench.dto.application.ApplicationCategoryDto;
import com.geek.workbench.entity.application.ApplicationCategoryEntity;
import com.geek.workbench.service.application.ApplicationCategoryService;
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
@RequestMapping(value=ApplicationCategoryController.BASE_PATH)
public class ApplicationCategoryController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/applicationcategory" ;

    @Autowired
    private ApplicationCategoryService applicationCategoryService ;

    /**
     * 记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody ApplicationCategoryDto applicationCategoryDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(applicationCategoryDto, null);
        //执行分页查询
        Page<ApplicationCategoryEntity> applicationCategoryPage = applicationCategoryService.queryByPage(searchParam , applicationCategoryDto.getPageNum() , applicationCategoryDto.getPageSize()) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> applicationCategoryList = UtilDTO.toDTO(applicationCategoryPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( applicationCategoryPage )
                .datalist( applicationCategoryList) ;
    }
    /**
     * 获取详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询应用系统分类标签
        ApplicationCategoryEntity applicationCategory = applicationCategoryService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( applicationCategory ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param applicationCategory
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    public ResponseParam save(@RequestBody ApplicationCategoryEntity  applicationCategory) {
        //id不为空，进行更新操作，否则进行添加
        if(applicationCategory.getId() != null){
			//由请求参数中获取需要更新的字段
			Set<String> updateFields = UtilDTO.toDTOExcludeFields(applicationCategory, Arrays.asList("id")).keySet();
            //按照字段更新对象
			applicationCategoryService.update( applicationCategory , updateFields ) ;

			return ResponseParam.updateSuccess() ;
        }else{
			applicationCategoryService.save(applicationCategory);
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
        applicationCategoryService.delete(list.stream().map(map -> map.getId()).collect(Collectors.toList()));
	

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