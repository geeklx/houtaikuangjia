package com.fosung.system.support.system.controller.sys;
import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.system.support.system.anno.SysLog;
import com.fosung.system.support.system.dict.OptLogType;
import com.fosung.system.support.system.dict.PostStatus;
import com.fosung.system.support.system.dto.sys.SysPostDto;
import com.fosung.system.support.system.entity.sys.SysPostEntity;
import com.fosung.system.support.system.entity.sys.SysUserPostEntity;
import com.fosung.system.support.system.service.config.DTOCallbackHandlerWithSysDict;
import com.fosung.system.support.system.service.sys.SysPostService;
import com.fosung.system.support.system.service.sys.SysUserPostService;
import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value=SysPostController.BASE_PATH)
public class SysPostController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/syspost" ;

    /**
     * 岗位编码前缀
     */
    public static final String POST_CODE_PREFIX = "fs-user-post-";

    @Autowired
    private SysPostService sysPostService ;

    @Autowired
    private SysUserPostService sysUserPostService;

    @Autowired
    private DTOCallbackHandlerWithSysDict handlerWithSysDict;

    /**
     * 岗位记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysPostDto sysPostDto){
        if(sysPostDto.getProjectId() == null){
            return ResponseParam.success()
                    .datalist(Lists.newArrayList()) ;
        }
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysPostDto, null);
        //执行分页查询
        Page<SysPostEntity> sysPostPage = sysPostService.queryPage(searchParam , MybatisPageRequest.of(sysPostDto.getPageNum() , sysPostDto.getPageSize())) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysPostList = UtilDTO.toDTO(sysPostPage.getContent(),
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysPostPage )
                .datalist( sysPostList) ;
    }

    /**
     *查询全部岗位信息，不分页
     *
     * @param sysPostDto
     * @author liuke
     * @date 2021/4/16 10:52
     * @return com.fosung.framework.web.http.ResponseParam
     */
    @PostMapping("queryall")
    public ResponseParam queryAll(@RequestBody SysPostDto sysPostDto){
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysPostDto, null);
        searchParam.put("status", PostStatus.normal);
        //执行查询
        List<SysPostEntity> sysPostEntities = sysPostService.queryAll(searchParam,new String[]{"num:asc","createDatetime:asc"});

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysPostList = UtilDTO.toDTO(sysPostEntities,
                null , getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .datalist( sysPostList) ;
    }
    /**
     * 获取岗位详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        //查询岗位表
        SysPostEntity sysPost = sysPostService.get(param.getId()) ;

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysPost ,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }

    /**
     * 保存岗位实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysPost
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @SysLog(optModule = "岗位管理",optName = "保存",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysPostEntity  sysPost) {
        //id不为空，进行更新操作，否则进行添加
        if(sysPost.getId() != null){
			//按照字段更新对象
			sysPostService.update( sysPost ,  Arrays.asList("postName","num","postType","identify","remark"));

			return ResponseParam.updateSuccess() ;
        }else{
            // 校验岗位编码是否存在
            if(StringUtils.isNotBlank(sysPost.getPostCode())){
                // 角色编码唯一性校验
                HashMap<String, Object> checkPostCode = new HashMap<>();
                checkPostCode.put("projectId",sysPost.getProjectId());
                checkPostCode.put("postCode",sysPost.getPostCode());
                checkPostCode.put("del",false);
                if(sysPostService.isExist(checkPostCode)){
                    return ResponseParam.fail().message("岗位编码已存在!");
                }
            }else {
                sysPost.setPostCode( POST_CODE_PREFIX + System.currentTimeMillis());
            }
			sysPostService.save(sysPost);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @SysLog(optModule = "岗位管理",optName = "删除",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
 	    if (UtilCollection.isEmpty(list)) {
 	        return ResponseParam.deleteFail();
 	    }
 	    //执行删除
        List<Long> ids = list.stream().map(map -> map.getId()).collect(Collectors.toList());
        sysPostService.delete(ids);
 	    // 删除用户岗位关联关系
        HashMap<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("postIds",ids);
        List<SysUserPostEntity> sysUserPostEntities = sysUserPostService.queryAll(searchParams);
        if(UtilCollection.isNotEmpty(sysUserPostEntities)){
            sysUserPostService.delete(sysUserPostEntities.stream().map(map -> map.getId()).collect(Collectors.toList()));
        }
        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 描述: 批量开启/停用
     * @param sysPosts
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/26 11:47
     **/
    @PostMapping("update/status")
    @SysLog(optModule = "岗位管理",optName = "批量启用/禁用",optType = OptLogType.BATCH_ENABLED)
    public ResponseParam batchUpdateStatus(@RequestBody List<SysPostEntity>  sysPosts) {
        //id不为空，进行更新操作，否则进行添加
        if(sysPosts!=null){
            sysPosts.forEach(post -> {
                if(post.getId() != null){
                    //按照字段更新对象
                    sysPostService.update( post , Arrays.asList("status")) ;
                }
            });
            return ResponseParam.updateSuccess() ;
        }
        return ResponseParam.updateFail();
    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {


        return getDTOCallbackHandlerProxy(handlerWithSysDict,true);
    }

}