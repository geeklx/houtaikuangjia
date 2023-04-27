package com.geek.system.support.system.controller.sys;

import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.dto.sys.SysProjectConfigDto;
import com.geek.system.support.system.entity.sys.SysProjectConfigEntity;
import com.geek.system.support.system.service.sys.SysProjectConfigService;
import com.geek.system.support.system.service.sys.SysProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value= SysProjectConfigController.BASE_PATH)
public class SysProjectConfigController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysprojectconfig" ;


    @Autowired
    private SysProjectConfigService sysProjectConfigService;

    @Autowired
    private SysProjectService sysProjectService;


    /**
     *查询所有配置信息
     *
     * @param sysProjectConfigDto
     * @author liuke
     * @date 2022/5/5 15:23
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("queryall")
    public ResponseParam queryAll(@RequestBody SysProjectConfigDto sysProjectConfigDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysProjectConfigDto, null);
        //执行查询
        List<SysProjectConfigEntity> sysProjectConfigEntities = sysProjectConfigService.queryAll(searchParam);

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysPostList = UtilDTO.toDTO(sysProjectConfigEntities,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( sysPostList) ;
    }
    /**
     * 获取配置详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询岗位表
        SysProjectConfigEntity sysProjectConfigEntity = sysProjectConfigService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysProjectConfigEntity ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 获取配置详情数据。
     */
    @PostMapping("getbyproject")
    public ResponseParam queryByProject(@RequestBody SysProjectConfigEntity param){
        //查询字典表
        SysProjectConfigEntity sysProjectConfigEntity = sysProjectService.getProjectConfig(param.getProjectId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysProjectConfigEntity ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存岗位实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysProjectConfigEntity
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    public ResponseParam save(@RequestBody SysProjectConfigEntity  sysProjectConfigEntity) {
         if(sysProjectConfigEntity.getProjectId()==null){
             return ResponseParam.fail().message("租户id不能为空");
         }
        //id不为空，进行更新操作，否则进行添加
        if(sysProjectConfigEntity.getId() != null){
			//按照字段更新对象
			sysProjectConfigService.update( sysProjectConfigEntity ,null);
            sysProjectService.removeConfigRedis(sysProjectConfigEntity.getProjectId());
			return ResponseParam.updateSuccess() ;
        }else{

			sysProjectConfigService.save(sysProjectConfigEntity);
			return ResponseParam.saveSuccess() ;
        }
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