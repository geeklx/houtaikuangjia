package com.geek.system.pbs.api.oldapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.geek.system.pbs.dto.CloudOrg;
import com.geek.system.pbs.dto.CloudTreeNode;
import com.geek.system.pbs.dto.OrgDto;
import com.geek.system.pbs.dto.support.dt.DTOrg;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;

public abstract class AbstractCloudOrgApi extends AppIBaseController implements CloudOrgApi {

	@Override
	public List<CloudOrg> queryRootOrgs(@PathVariable("source") String source) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 开放省内安全中心获取组织管理范围
	 *
	 * @param parentId
	 * @return 返回简项库对应结构
	 */
	@Override
	public List<DTOrg> querySimpleDataOrgs(@PathVariable("source") String source, @PathVariable(value = "parentId") String parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 组织信息保存接口（id为空时新增，id不为空时修改该组织）
	 * @param orgDto
	 * @return
	 */
	@Override
	public ResponseParam save(@PathVariable("source") String source,@RequestBody OrgDto orgDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam delete(@PathVariable("source") String source,@RequestParam String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudOrg> parentOrgs(@PathVariable("source") String source, @RequestParam("orgId") String orgId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<CloudOrg> querySubOrgs(@PathVariable("source") String source,
			@RequestParam(name = "parentId") String parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 批量查询子节点
	 *
	 * @param source
	 * @param parentIds
	 * @return
	 */
	@Override
	public List<CloudOrg> batchQuerySubOrg(@PathVariable("source") String source,
										   @RequestParam(value = "parentIds",name = "parentIds") ArrayList<String> parentIds) {
		return null;
	}

	@Override
	public ResponseParam subOrgPage(@PathVariable("source") String source,
			@RequestParam(name = "orgLevelType", required = false) String orgLevelType,
			@RequestParam(name = "parentId") String parentId,
			@RequestParam(name = "orgName", required = false) String orgName,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam updateLevel(@PathVariable("source") String source, @RequestParam("orgId") String orgId,
			@RequestParam("orgLevelType") String orgLevelType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam updateLevels(@PathVariable("source") String source,
									  @RequestParam("orgIds") String[] orgIds,
									  @RequestParam("orgLevelType") String orgLevelType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudOrg> querySubOrgByName(@PathVariable("source") String source,
			@RequestParam(name = "parentId") String parentId,
			@RequestParam(name = "orgName", required = false) String orgName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudOrg> querySubOrgsByCode(@PathVariable("source") String source,
			@RequestParam(name = "orgCode") String orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloudOrg queryOrgInfo(@PathVariable("source") String source, @RequestParam(name = "orgId") String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudOrg> batchQueryOrgInfo(@PathVariable("source") String source, @RequestParam(value = "orgIds",name = "orgIds") ArrayList<String> orgIds) {
		return null;
	}

	@Override
	public CloudOrg queryOrgByCode(@PathVariable("source") String source,
			@RequestParam(name = "orgCode") String orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloudOrg queryUserOrg(@PathVariable("source") String source, @RequestParam(name = "userid") String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudOrg> searchSubOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
			@RequestParam("orgName") String orgName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudTreeNode> getAllParentOrg(@PathVariable("source") String source,
											   @RequestParam("orgId") String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudTreeNode> queryAllParentOrgByIds(@PathVariable("source") String source, @RequestParam("orgIds") ArrayList<Long> orgIds) {
		return null;
	}

	@Override
	public List<CloudOrg> leafOrgs(@PathVariable("source") String source, @RequestParam("orgId") String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryLeafPage(@PathVariable("source") String source, @RequestParam("orgId") String orgId,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryLeafByNameAndCode(@PathVariable("source") String source,
			@RequestParam("orgCode") String orgCode, @RequestParam(value = "orgName", required = false) String orgName,
			@RequestParam(value = "pageFlag") boolean flag,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CloudOrg> queryVillageOrg(@PathVariable("source") String source,
			@RequestParam(value = "orgName", required = false) String orgName,
			@RequestParam(value = "leaf") Boolean leaf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryByCodeAndLevel(@PathVariable("source") String source,
			@RequestParam(value = "orgCode") String orgCode, @RequestParam(value = "orgLevel") String orgLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam queryByNamePage(@PathVariable("source") String source, @RequestBody Map<String, Object> map) {
		return null;
	}

	@Override
	public ResponseParam getParentId(@PathVariable("source") String source, @RequestBody Map<String, Object> param) {
		return null;
	}

	@Override
	public ResponseParam countByOrgCode(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode) {
		return null;
	}

	@Override
	public ResponseParam countGroupLevelType(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode) {
		return null;
	}

	@Override
	public ResponseParam queryByCodeAndLevelType(@PathVariable("source") String source,
												 @RequestParam("orgCode") String orgCode,
												 @RequestParam("levelType") String levelType) {
		return null;
	}

	@Override
	public ResponseParam queryAllSubOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode) {
		return null;
	}

	@Override
	public List<CloudOrg> queryAllSubSysOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode) {
		return null;
	}

	@Override
	public List<CloudOrg> queryAllParentOrg(@PathVariable("source") String source, @RequestParam("orgId") String orgId) {
		return null;
	}

	/**
	 * 分页查询 orgCode下带有人员的组织,且统计出组织中的人数
	 *
	 * @param source
	 * @param orgCode
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public ResponseParam queryHaveUserOrg(@PathVariable("source") String source, @RequestParam("orgCode") String orgCode,
										  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
										  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		return null;
	}

	@Override
	public ResponseParam queryTreeNodeByName(@PathVariable("source") String source, @RequestParam("rootId") Long rootId, @RequestParam("name") String name) {
		return null;
	}

	@Override
	public ResponseParam queryAllParentId(@PathVariable("source") String source, @RequestParam("orgId") Long orgId, @RequestParam("rootId") Long rootId) {
		return null;
	}

	@Override
	public List<CloudOrg> queryByLevelType(@PathVariable("source") String source,
										   @RequestParam("orgId") Long orgId,
										   @RequestParam(required = false,value = "levelType") String levelType) {
		return null;
	}

	@Override
	public List<CloudOrg> queryAllParentOrgByLevelType(@PathVariable("source") String source,
												@RequestParam("orgIds") ArrayList<Long> orgIds,
												@RequestParam(required = false,value = "levelType") String levelType) {
		return null;
	}

	@Override
	public CloudOrg getUpOrg(@PathVariable("source") String source, @RequestParam("orgId") Long orgId) {
		return null;
	}

	@Override
	public List<CloudOrg> querySysOrg(@PathVariable("source") String source, @RequestBody CloudOrg cloudOrg) {
		return null;
	}

	@Override
	public ResponseParam queryAllChildOrg(@PathVariable String source,@RequestParam("orgId") Long orgId){
		return null;
	}
}
