package com.fosung.system.pbs.api.oldapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.fosung.system.pbs.dto.CloudOrg;
import com.fosung.system.pbs.dto.CloudTreeNode;
import com.fosung.system.pbs.dto.OrgDto;
import com.fosung.system.pbs.dto.support.dt.DTOrg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.ResponseParam;

import javax.validation.Valid;

@FeignClient(name = "CloudOrgApi", url = "${app.pbs.client.url}")
@RequestMapping("/{source}/api/cloud/org")
public interface CloudOrgApi {

	/**
	 * 查询根组织机构
	 *
	 * @param source
	 * @return
	 */
	@PostMapping(value = "/root")
	List<CloudOrg> queryRootOrgs(@PathVariable("source") String source);


	/**
	 * 组织信息保存接口（id为空时新增，id不为空时修改该组织）
	 * @param orgDto
	 * @return
	 */
	@PostMapping(value = "/save")
	ResponseParam save(@PathVariable("source") String source,@RequestBody @Valid OrgDto orgDto);

	/**
	 * 删除组织接口 id 是组织id
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/delete")
	ResponseParam delete(@PathVariable("source") String source,@RequestParam("orgId") String orgId);

	/**
	 * 获取上级组织
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/query/parentOrgs")
	List<CloudOrg> parentOrgs(@PathVariable("source") String source,
							  @RequestParam("orgId") String orgId);

	/**
	 * 开放省内安全中心查询获取组织管理范围
	 *
	 * @param parentId
	 * @return 返回简项库对应结构
	 */
	@RequestMapping(value = "/queryOrgInfo/{parentId}")
	List<DTOrg> querySimpleDataOrgs(@PathVariable("source") String source,
									@PathVariable(value = "parentId") String parentId);


	/**
	 * 查询直接子节点
	 *
	 * @param source
	 * @param parentId
	 * @return
	 */
	@PostMapping(value = "/suborg")
	List<CloudOrg> querySubOrgs(@PathVariable("source") String source, @RequestParam(value = "parentId",name = "parentId") String parentId);

	/**
	 * 批量查询子节点
	 *
	 * @param source
	 * @param parentIds
	 * @return
	 */
	@PostMapping(value = "/querySubOrg")
	List<CloudOrg> batchQuerySubOrg(@PathVariable("source") String source, @RequestParam(value = "parentIds",name = "parentIds") ArrayList<String> parentIds);

	/**
	 * 分页查询下级组织
	 *
	 * @param source
	 * @param parentId
	 * @param orgName
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@PostMapping(value = "/suborg/page")
	ResponseParam subOrgPage(@PathVariable("source") String source,
			@RequestParam(value = "orgLevelType",name = "orgLevelType", required = false) String orgLevelType,
			@RequestParam(value = "parentId",name = "parentId") String parentId,
			@RequestParam(value = "orgName",name = "orgName", required = false) String orgName,
			@RequestParam(value = "pageSize",name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNum",name = "pageNum", defaultValue = "1") Integer pageNum);

	/**
	 * 更新类型
	 *
	 * @param orgId
	 * @param orgLevelType
	 * @return
	 */
	@PostMapping("/update/level")
	ResponseParam updateLevel(@PathVariable("source") String source, @RequestParam("orgId") String orgId,
			@RequestParam("orgLevelType") String orgLevelType);

	/**
	 * 批量更新组织类型
	 *
	 * @param orgIds
	 * @param orgLevelType
	 * @return
	 */
	@PostMapping(value = "/update/levels")
	ResponseParam updateLevels(@PathVariable("source") String source,
							   @RequestParam("orgIds") String[] orgIds,
							   @RequestParam("orgLevelType") String orgLevelType);

	@PostMapping(value = "/subOrgByName")
	List<CloudOrg> querySubOrgByName(@PathVariable("source") String source,
			@RequestParam(value = "parentId",name = "parentId") String parentId,
			@RequestParam(value = "orgName",name = "orgName", required = false) String orgName);

	/**
	 * 根据code查询下级子节点
	 *
	 * @param source
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/suborg/code")
	List<CloudOrg> querySubOrgsByCode(@PathVariable("source") String source,
			@RequestParam(value = "orgCode",name = "orgCode") String orgCode);

	/**
	 * 查询组织机构信息
	 *
	 * @param source
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/info")
	CloudOrg queryOrgInfo(@PathVariable("source") String source, @RequestParam(value = "orgId",name = "orgId") String orgId);

	/**
	 * 批量查询组织机构信息
	 *
	 * @param source
	 * @param orgIds
	 * @return
	 */
	@PostMapping(value = "/queryOrgInfo")
	List<CloudOrg> batchQueryOrgInfo(@PathVariable("source") String source, @RequestParam(value = "orgIds",name = "orgIds") ArrayList<String> orgIds);

	/**
	 * 根据code查询机构信息
	 *
	 * @param source
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/code")
	CloudOrg queryOrgByCode(@PathVariable("source") String source, @RequestParam(value = "orgCode",name = "orgCode") String orgCode);

	/**
	 * 查询用户所属的组织机构
	 *
	 * @param source
	 * @param userId
	 * @return
	 */
	@PostMapping(value = "/user")
	CloudOrg queryUserOrg(@PathVariable("source") String source, @RequestParam(value = "userid",name = "userid") String userId);

	/**
	 * 根据名字模糊匹配下级组织
	 *
	 * @param source
	 * @param orgCode
	 * @param orgName
	 * @return
	 */
	@PostMapping(value = "/search/suborg")
	List<CloudOrg> searchSubOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
			@RequestParam("orgName") String orgName);

	/**
	 * 获取所有上级，返回树形结构数据
	 *
	 * @param source
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/query/allParentOrg")
	List<CloudTreeNode> getAllParentOrg(@PathVariable("source") String source, @RequestParam("orgId") String orgId);

	/**
	 * 批量获取所有上级，返回树形结构数据
	 *
	 * @param source
	 * @param orgIds
	 * @return
	 */
	@PostMapping(value = "/queryAllParentOrgByIds")
	List<CloudTreeNode> queryAllParentOrgByIds(@PathVariable("source") String source, @RequestParam("orgIds") ArrayList<Long> orgIds);

	/**
	 * 根据orgID获取所有最下级组织
	 *
	 * @param source
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/query/leaf/org")
	List<CloudOrg> leafOrgs(@PathVariable("source") String source, @RequestParam("orgId") String orgId);

	/**
	 * 分页查询最下级组织
	 *
	 * @param source
	 * @param orgId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value = "/query/leaf/page")
	ResponseParam queryLeafPage(@PathVariable("source") String source, @RequestParam("orgId") String orgId,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);

	/**
	 * 查询最下级组织根据name和code匹配
	 *
	 * @param source
	 * @param orgCode
	 * @param orgName
	 * @return
	 */
	@PostMapping(value = "/query/leaf")
	ResponseParam queryLeafByNameAndCode(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
			@RequestParam(value = "orgName", required = false) String orgName,
			@RequestParam(value = "pageFlag") boolean flag,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);

	/**
	 * 模糊查询所有村级组织
	 *
	 * @param source
	 * @param orgName
	 * @return
	 */
	@PostMapping(value = "/queryByName")
	List<CloudOrg> queryVillageOrg(@PathVariable("source") String source,
			@RequestParam(value = "orgName", required = false) String orgName,
			@RequestParam(value = "leaf") Boolean leaf);

	/**
	 * 根据组织类型和code查询下级组织
	 * @param orgCode
	 * @param orgLevel
	 * @return
	 */
	@PostMapping(value = "/query")
	ResponseParam queryByCodeAndLevel(@PathVariable("source") String source,
			@RequestParam(value = "orgCode") String orgCode, @RequestParam(value = "orgLevel") String orgLevel);

	/**
	 * 根据组织名称分页查询
	 * @param source 组织名称
	 * @return List<SysOrg>
	 */
	@PostMapping("/name")
	ResponseParam queryByNamePage(@PathVariable("source") String source, @RequestBody Map<String, Object> map);

	/**
	 * 查询上级id
	 *
	 */
	@PostMapping("/parent")
	ResponseParam getParentId(@PathVariable("source") String source, @RequestBody Map<String, Object> param);

	/**
	 * 根据组织code直接查询所有下级组织数量包含本级
	 * @param source
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/countByOrgCode")
	ResponseParam countByOrgCode(@PathVariable("source") String source, @RequestParam(value = "orgCode") String orgCode);

	/**
	 * 根据组织code，级别类型分类查询下级数量
	 * @param source
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/countGroupLevelType")
	ResponseParam countGroupLevelType(@PathVariable("source") String source, @RequestParam(value = "orgCode") String orgCode);

	/**
	 * 根据组织code，级别类型查询下级组织
	 * @param source
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/queryByCodeAndLevelType")
	ResponseParam queryByCodeAndLevelType(@PathVariable("source") String source,
										  @RequestParam(value = "orgCode") String orgCode,
										  @RequestParam(value = "levelType") String levelType);

	/**
	 *  查询下级所有节点
	 * @param source
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/queryAllSubOrg")
	ResponseParam queryAllSubOrg(@PathVariable("source") String source, @RequestParam(value = "orgCode") String orgCode);

	/**
	 *  查询下级所有节点
	 * @param source
	 * @param orgCode
	 * @return
	 */
	@PostMapping(value = "/queryAllSubSysOrg")
	List<CloudOrg> queryAllSubSysOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode);

	/**
	 * 获取所有上级
	 *
	 * @param source
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/queryAllParentOrg")
	List<CloudOrg> queryAllParentOrg(@PathVariable("source") String source, @RequestParam("orgId") String orgId);


	/**
	 * 分页查询 orgCode下带有人员的组织,且统计出组织中的人数
	 * @param source
	 * @param orgCode
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryHaveUserOrg")
	ResponseParam queryHaveUserOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
								   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
								   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);

	/**
	 * 组织树通过组织名称搜索，以树的形式返回
	 * @param rootId 根节点组织id
	 * @param name 组织名称
	 * @return
	 */
	@PostMapping(value = "/queryTreeNodeByName")
	ResponseParam queryTreeNodeByName(@PathVariable("source") String source, @RequestParam("rootId") Long rootId, @RequestParam("name") String name);

	/**
	 * 查询所有上级组织id包含本级，并按组织等级由大到小顺序排序
	 * @param orgId 要查询的组织
	 * @param rootId 根节点组织id
	 * @return
	 */
	@PostMapping(value = "/queryAllParentId")
	ResponseParam queryAllParentId(@PathVariable("source") String source, @RequestParam("orgId") Long orgId, @RequestParam("rootId") Long rootId);

	/**
	 * 根据组织id和级别类型查询上级组织
	 * @param source
	 * @param orgId
	 * @param levelType
	 * @return
	 */
	@PostMapping(value = "/queryByLevelType")
	List<CloudOrg> queryByLevelType(@PathVariable("source") String source,
									@RequestParam("orgId") Long orgId,
									@RequestParam(required = false,value = "levelType") String levelType);

	/**
	 * 批量根据组织id和级别类型查询上级组织
	 * @param source
	 * @param orgIds
	 * @param levelType
	 * @return
	 */
	@PostMapping(value = "/queryAllParentOrgByLevelType")
	List<CloudOrg> queryAllParentOrgByLevelType(@PathVariable("source") String source,
									@RequestParam("orgIds") ArrayList<Long> orgIds,
									@RequestParam(required = false,value = "levelType") String levelType);

	/**
	 * 获取直接上级组织
	 * @param source
	 * @param orgId
	 * @return
	 */
	@PostMapping(value = "/getUpOrg")
	CloudOrg getUpOrg(@PathVariable("source") String source, @RequestParam("orgId") Long orgId);

	/**
	 * 查询组织信息
	 * @param source
	 * @param cloudOrg
	 * @return
	 */
	@PostMapping(value = "/querySysOrg")
	List<CloudOrg> querySysOrg(@PathVariable("source") String source, @RequestBody CloudOrg cloudOrg);


	/**
	 *系统管理-根据节点查询当前组织下所有组织信息
	 *
	 * @param source
	 * @param orgId
	 * @author liuke
	 * @date 2021/4/27 15:54
	 * @return com.fosung.framework.web.http.ResponseParam
	 */
	@PostMapping(value = "/queryallchildorg")
	ResponseParam queryAllChildOrg(@PathVariable("source") String source,@RequestParam("orgId") Long orgId);
}
