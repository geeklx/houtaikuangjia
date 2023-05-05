package com.fosung.system.support.system.controller.sys;
import java.util.*;
import java.util.stream.Collectors;

import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.common.util.UtilTree;
import com.fosung.framework.dao.config.mybatis.page.MybatisPageRequest;
import com.fosung.system.support.system.anno.SysLog;
import com.fosung.system.support.system.controller.auth.SysIBaseController;
import com.fosung.system.support.system.dict.OptLogType;
import com.fosung.system.support.system.dto.sys.SysOrgDto;
import com.fosung.system.support.system.entity.sys.SysOrgEntity;
import com.fosung.system.support.system.entity.sys.SysUserEntity;
import com.fosung.system.support.system.service.sys.SysOrgService;
import com.fosung.system.support.system.service.sys.SysUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fosung.framework.common.dto.params.AppBaseIdParam;
import org.springframework.web.bind.annotation.*;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;

@RestController
@RequestMapping(value=SysOrgController.BASE_PATH)
public class SysOrgController extends SysIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/sysorg" ;

    private static final Set<String> includeFields = Sets.newHashSet("id","orgName","projectName","orgCode","remark",
            "createDatetime","parentId","parentName","orgType","projectId","num","leaf","levelType","level","dtOrgId","dtOrgName","dtOrgCode");

    @Autowired
    private SysOrgService sysOrgService ;

    @Autowired
    private SysUserService sysUserService;


    /**
     * 顶级组织记录分页查询
     * @return
     * @throws Exception
     */
    @PostMapping("query")
	public ResponseParam query(@RequestBody SysOrgDto sysOrgDto){
        if(sysOrgDto.getProjectId()==null&&getLoginProjectId()!= null){
            sysOrgDto.setProjectId(sysOrgDto.getProjectId());
        }
        if(sysOrgDto.getProjectId()==null){
            return ResponseParam.success().datalist(Lists.newArrayList());
        }
        //获取查询参数
        Map<String, Object> searchParam =  UtilDTO.toDTO(sysOrgDto, null);
        //执行分页查询
        Pageable pageable = MybatisPageRequest.of(sysOrgDto.getPageNum(),sysOrgDto.getPageSize());
        Page<SysOrgEntity> sysOrgPage = sysOrgService.queryPage(searchParam , pageable) ;

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgPage.getContent(),includeFields
                 ,null, getDtoCallbackHandler()) ;

        return ResponseParam.success()
                .pageParam( sysOrgPage )
                .datalist( sysOrgList) ;
    }

    /**
     * 根据父id查询组织树
     *
     * @param sysOrgDto, dataType
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2022/2/7 16:00
     */
    @PostMapping(value = {"/query/tree"})
    public ResponseParam queryTree(@RequestBody SysOrgDto sysOrgDto){
        List<SysOrgEntity> sysOrgEntities = Lists.newArrayList(sysOrgService.getOrgsByParentId(sysOrgDto.getParentId()));
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,includeFields,
                null , getDtoCallbackHandler()) ;
        if(!UtilCollection.sizeIsEmpty(sysOrgList)){
            sysOrgList= UtilTree.getTreeData(sysOrgList,"id","parentId","children",false);
        }
        return ResponseParam.success().datalist(sysOrgList);
    }

    /**
     * 根据组织id查询组织树
     *
     * @param sysOrgDto, dataType
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/7 16:00
     */
    @PostMapping(value = {"/query/treebyrole"})
    public ResponseParam queryTreeByRoleId(@RequestBody SysOrgDto sysOrgDto){
        if(sysOrgDto.getUserId()==null){
            if(getLoginUserId()==null){
                return ResponseParam.fail().message("用户id不能为空").datalist(Lists.newArrayList());
            }else {
                sysOrgDto.setUserId(Long.valueOf(getLoginUserId()));
            }
        }
        if (sysOrgDto.getProjectId()==null){
            if(getLoginProjectId()==null){
                return ResponseParam.fail().message("项目id不能为空").datalist(Lists.newArrayList());
            }else {
                sysOrgDto.setProjectId(getLoginProjectId());
            }
        }
        List<SysOrgEntity> sysOrgEntities = sysOrgService.getOrgByRoleIdAndUserId(sysOrgDto.getUserId(),sysOrgDto.getRoleId(),sysOrgDto.getProjectId(),sysOrgDto.getOrgType());
        sysOrgEntities=sysOrgEntities.stream().
                sorted(Comparator.comparing(SysOrgEntity::getNum,Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(SysOrgEntity::getCreateDatetime,Comparator.nullsLast(Date::compareTo)))
                .collect(Collectors.toList());
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,includeFields,
                null , getDtoCallbackHandler()) ;
        if(!UtilCollection.sizeIsEmpty(sysOrgList)){
            sysOrgList= UtilTree.getTreeData(sysOrgList,"id","parentId","children",false);
        }
        return ResponseParam.success().datalist(sysOrgList);
    }

    /**
     * 根据组织id查询组织树
     *
     * @param sysOrgDto, dataType
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/7 16:00
     */
    @PostMapping(value = {"/queryparty/treebyrole"})
    public ResponseParam queryPartyTreeByRoleId(@RequestBody SysOrgDto sysOrgDto){
        if(sysOrgDto.getRoleId()==null){
            return ResponseParam.fail().message("角色id不能为空").datalist(Lists.newArrayList());
        }
        if(sysOrgDto.getUserId()==null){
            if(getLoginUserId()==null){
                return ResponseParam.fail().message("用户id不能为空").datalist(Lists.newArrayList());
            }else {
                sysOrgDto.setUserId(Long.valueOf(getLoginUserId()));
            }
        }
        if (sysOrgDto.getProjectId()==null){
            if(getLoginProjectId()==null){
                return ResponseParam.fail().message("项目id不能为空").datalist(Lists.newArrayList());
            }else {
                sysOrgDto.setProjectId(getLoginProjectId());
            }
        }
        List<SysOrgEntity> sysOrgEntities = sysOrgService.getPartyOrgByRoleIdAndUserId(sysOrgDto.getUserId(),sysOrgDto.getRoleId(),sysOrgDto.getProjectId());
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,includeFields,
                null , getDtoCallbackHandler()) ;
        if(!UtilCollection.sizeIsEmpty(sysOrgList)){
            sysOrgList= UtilTree.getTreeData(sysOrgList,"id","parentId","children",false);
        }
        return ResponseParam.success().datalist(sysOrgList);
    }

    /**
     * 根据组织id查询组织树
     *
     * @param sysOrgDto, dataType
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/7 16:00
     */
    @PostMapping(value = {"/query/lazytreebyrole"})
    public ResponseParam queryLazyTreeByRoleId(@RequestBody SysOrgDto sysOrgDto){
        List<SysOrgEntity> sysOrgEntities = Lists.newArrayList();
        if(sysOrgDto.getParentId()!=null){
            Map<String,Object> searchParam = Maps.newHashMap();
            searchParam.put("parentId",sysOrgDto.getParentId());
            sysOrgEntities = sysOrgService.queryOrgsByParentId(searchParam);
        }else {
            if(sysOrgDto.getUserId()==null){
                if(getLoginUserId()==null){
                    return ResponseParam.fail().message("用户id不能为空");
                }else {
                    sysOrgDto.setUserId(Long.valueOf(getLoginUserId()));
                }
            }
            if (sysOrgDto.getProjectId()==null){
                if(getLoginProjectId()==null){
                    return ResponseParam.fail().message("项目id不能为空");
                }else {
                    sysOrgDto.setProjectId(getLoginProjectId());
                }
            }
            sysOrgEntities = sysOrgService.getLazyOrgByRoleIdAndUserId(sysOrgDto.getUserId(),sysOrgDto.getRoleId(),sysOrgDto.getProjectId());
        }

        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,includeFields,
                null , getDtoCallbackHandler()) ;
        if(!UtilCollection.sizeIsEmpty(sysOrgList)){
            sysOrgList= UtilTree.getTreeData(sysOrgList,"id","parentId","children",false);

        }
        return ResponseParam.success().datalist(sysOrgList);
    }

    /**
     * 根据组织id查询组织树
     *
     * @param sysOrgDto, dataType
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/7 16:00
     */
    @PostMapping(value = {"/partyquery/lazytreebyrole"})
    public ResponseParam queryPartyLazyTreeByRoleId(@RequestBody SysOrgDto sysOrgDto){
        List<SysOrgEntity> sysOrgEntities = Lists.newArrayList();
        if(sysOrgDto.getParentId()!=null){
            Map<String,Object> searchParam = Maps.newHashMap();
            searchParam.put("parentId",sysOrgDto.getParentId());
            sysOrgEntities = sysOrgService.queryOrgsByParentId(searchParam);
        }else {
            if(sysOrgDto.getRoleId()==null){
                return ResponseParam.fail().message("角色id不能为空");
            }
            if(sysOrgDto.getUserId()==null){
                if(getLoginUserId()==null){
                    return ResponseParam.fail().message("用户id不能为空");
                }else {
                    sysOrgDto.setUserId(Long.valueOf(getLoginUserId()));
                }
            }
            if (sysOrgDto.getProjectId()==null){
                if(getLoginProjectId()==null){
                    return ResponseParam.fail().message("项目id不能为空");
                }else {
                    sysOrgDto.setProjectId(getLoginProjectId());
                }
            }
            sysOrgEntities = sysOrgService.getPartyLazyOrgByRoleIdAndUserId(sysOrgDto.getUserId(),sysOrgDto.getRoleId(),sysOrgDto.getProjectId());
        }

        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,includeFields,
                null , getDtoCallbackHandler()) ;
        if(!UtilCollection.sizeIsEmpty(sysOrgList)){
            sysOrgList= UtilTree.getTreeData(sysOrgList,"id","parentId","children",false);

        }
        return ResponseParam.success().datalist(sysOrgList);
    }

    /**
     * 根据父id查询子集组织
     *
     * @param sysOrgDto, dataType
     * @return com.fosung.framework.web.http.ResponseParam
     * @author liuke
     * @date 2021/4/7 16:00
     */
    @PostMapping(value = {"/query/sonorg"})
    public ResponseParam querySonOrg(@RequestBody SysOrgDto sysOrgDto){
        if(sysOrgDto.getParentId()==null){
            return ResponseParam.fail().message("缺少参数parentId");
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("parentId",sysOrgDto.getParentId());
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryOrgsByParentId(map);
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,includeFields,
                null , getDtoCallbackHandler()) ;
        return ResponseParam.success().datalist(sysOrgList);
    }
    /**
      * 查询全部组织
      *
      * @param sysOrgDto, dataType
      * @return com.fosung.framework.web.http.ResponseParam
      * @author liuke
      * @date 2021/4/7 16:00
      */
    @PostMapping(value = {"/queryall/{dataType}"})
    public ResponseParam queryAll(@RequestBody SysOrgDto sysOrgDto,
                                  @PathVariable(value = "dataType",required = false) String dataType){
        Map<String,Object> searchParam = UtilDTO.toDTO(sysOrgDto,null);

        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(searchParam,new String[]{"num"});
        //当数据类型为treedata时转换为tree
        List<Map<String, Object>> sysOrgList = UtilDTO.toDTO(sysOrgEntities,includeFields,
                null , getDtoCallbackHandler()) ;
        if("treedata".equals(dataType)){
            if(!UtilCollection.sizeIsEmpty(sysOrgList)){
                sysOrgList= UtilTree.getTreeData(sysOrgList,"id","parentId","children",false);
            }
        }
        return ResponseParam.success().datalist(sysOrgList);


    }
    /**
     * 获取组织详情数据。
     */
	@PostMapping("get")
    public ResponseParam detail(@RequestBody AppBaseIdParam param){
        // 查询组织机构
        SysOrgEntity sysOrg = sysOrgService.get(param.getId()) ;
        // 查询是否有父节点
        if(sysOrg.getParentId() != null){
            SysOrgEntity sysOrgEntity = sysOrgService.get(sysOrg.getParentId());
            sysOrg.setParentName(sysOrgEntity.getOrgName());
        }
        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO( sysOrg ,null,null , getDtoCallbackHandler() ) ;

        return ResponseParam.success()
                .data( dtoObject );
    }
    /**
     * 保存组织实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     * @param sysOrg
     * @return
     * @throws Exception
     */
 	@PostMapping("save")
    @SysLog(optModule = "组织机构管理",optName = "保存",optType = OptLogType.SAVE)
    public ResponseParam save(@RequestBody SysOrgEntity  sysOrg) {

         //校验编码长度
        if(sysOrg.getParentId()!=null&&UtilString.isNotBlank(sysOrg.getOrgCode())){
            SysOrgEntity sysOrgEntity = sysOrgService.get(sysOrg.getParentId());
            if(sysOrg.getOrgCode().startsWith(sysOrgEntity.getOrgCode())){
                if((sysOrg.getOrgCode().length()-sysOrgEntity.getOrgCode().length())!=6){
                    return ResponseParam.fail().message("组织编码不合法，没有遵循长度规则");
                }
            }else {
                return ResponseParam.fail().message("组织编码不合法，没有遵循父节点路径规则");
            }
        }else if(UtilString.isNotBlank(sysOrg.getOrgCode())){
            if((sysOrg.getOrgCode().length()%6)!=0){
                return ResponseParam.fail().message("组织编码长度不合法");
            }
        }

        //id不为空，进行更新操作，否则进行添加
        if(sysOrg.getId() != null){
            SysOrgEntity sysOrgEntitys = sysOrgService.get(sysOrg.getId());
//            if(!checkOrgDelOrUpdate(sysOrg.getId())){
//                return ResponseParam.fail().message(sysOrgEntitys.getOrgName() + ":该节点下有子节点不允许修改");
//            }
//            if(!checkUserDelOrUpdate(sysOrg.getId())){
//                return ResponseParam.fail().message(sysOrgEntitys.getOrgName() + ":该节点已关联人员不允许修改");
//            }
            SysOrgEntity sysOrgEntity = sysOrgService.get(sysOrg.getId());
            if(!sysOrg.getOrgName().equals(sysOrgEntity.getOrgName())){
                HashMap<String, Object> existParam = Maps.newHashMap();
                existParam.put("projectId",sysOrg.getProjectId());
                existParam.put("orgName",sysOrg.getOrgName());
                existParam.put("parentId",sysOrg.getParentId());
                existParam.put("del",false);
                if(sysOrgService.isExist(existParam)){
                    return ResponseParam.fail().message("同一节点下组织名称不允许重复！");
                }
            }

            Map<String,Object> map = UtilDTO.toDTO(sysOrg,null,getDtoCallbackHandler());
            List<String> cloums = Lists.newArrayList();
            for (String s : map.keySet()) {
                if(map.get(s)!=null){
                    cloums.add(s);
                }
            }
            //由请求参数中获取需要更新的字段
			//Set<String> updateFields = Sets.newHashSet("orgName","orgType","remark","parentId","createTime","num","leaf","areaType","levelType");
			//按照字段更新对象
			sysOrgService.update( sysOrg , cloums ) ;

			return ResponseParam.updateSuccess() ;
        }else{

            // 在同一父节点下不允许有同名
            HashMap<String, Object> existParam = Maps.newHashMap();
            existParam.put("projectId",sysOrg.getProjectId());
            existParam.put("orgName",sysOrg.getOrgName());
            if(sysOrg.getParentId() != null){
                existParam.put("parentId",sysOrg.getParentId());
            }else {
                existParam.put("parentIdIsNull","123");
            }
            existParam.put("del",false);
            if(sysOrgService.isExist(existParam)){
                return ResponseParam.fail().message("同一节点下组织名称不允许重复！");
            }
            // 组织编码租户下唯一
            if(StringUtils.isNotBlank(sysOrg.getOrgCode())){
                HashMap<String, Object> checkOrgCode = Maps.newHashMap();
                checkOrgCode.put("projectId",sysOrg.getProjectId());
                checkOrgCode.put("orgCodeEq",sysOrg.getOrgCode());
                checkOrgCode.put("del",false);
                if (sysOrgService.isExist(checkOrgCode)){
                    return ResponseParam.fail().message("组织编码已存在！");
                }
            }

            if(sysOrg.getParentId() == null){
                // 同一租户只允许存在一棵相同的树
                HashMap<String, Object> treeExist = Maps.newHashMap();
                treeExist.put("projectId",sysOrg.getProjectId());
                treeExist.put("del",false);
                treeExist.put("orgType",sysOrg.getOrgType());
                treeExist.put("parentIdIsNull","123");
                if(sysOrgService.isExist(treeExist)){
                    return ResponseParam.fail().message("同一租户下只能存在同类型的组织！");
                }
            }

            if(sysOrg.getProjectId()==null&&getLoginProjectId()!=null){
                sysOrg.setProjectId(getLoginProjectId());
            }
            if(sysOrg.getProjectId()==null){
                return ResponseParam.saveFail().message("请登录或选择项目");
            }
            sysOrg.setSource("user");
            sysOrg.setSourceName("用户权限中心");
            sysOrgService.save(sysOrg);
			return ResponseParam.saveSuccess() ;
        }
    }

    /**
     * 删除组织信息
     * @param list
     * @return
     */
 	@PostMapping("delete")
    @SysLog(optModule = "组织机构管理",optName = "删除",optType = OptLogType.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
         if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        List<Long> collect = list.stream().map(map -> map.getId()).collect(Collectors.toList());
        // 该节点下存在子节点与人员的组织不允许删除
        for (Long id:  collect) {
            SysOrgEntity sysOrgEntity = sysOrgService.get(id);
            if(!checkOrgDelOrUpdate(id)){
                return ResponseParam.fail().message(sysOrgEntity.getOrgName() + ":该节点下有子节点不允许删除");
            }
            if(!checkUserDelOrUpdate(id)){
                return ResponseParam.fail().message(sysOrgEntity.getOrgName() + ":该节点已关联人员不允许删除");
            }
        }
        //执行删除
        sysOrgService.deleteInfo(collect);
        return ResponseParam.deleteSuccess() ;
    }

    /**
     * 描述: 校验子节点是否存在
     * @param id
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2022/1/26 11:48
     **/
    public Boolean checkOrgDelOrUpdate(Long id){
 	    // 检查该节点下是否存在子节点
        HashMap<String, Object> checkOrg = Maps.newHashMap();
        checkOrg.put("parentId",id);
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryAll(checkOrg);
        if(UtilCollection.isNotEmpty(sysOrgEntities)){
            return false;
        }
        return true;
    }

    /**
     * 描述: 校验人员是否存在
     * @param id
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2022/1/26 11:48
     **/
    public Boolean checkUserDelOrUpdate(Long id){
        // 检查该节点下是否存在人
        HashMap<String, Object> checkUser = Maps.newHashMap();
        checkUser.put("orgId",id);
        List<SysUserEntity> sysUserEntities = sysUserService.queryAll(checkUser);
        if(UtilCollection.isNotEmpty(sysUserEntities)){
            return false;
        }
        return true;
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