package com.geek.system.support.system.service.sys;

import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.geek.system.support.system.dao.sys.AuthClientDao;
import com.geek.system.support.system.entity.sys.AuthClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
/**
 *
 * 认证配置查询类
 *
 * @author liuke
 * @date  2022/3/9 14:41
 * @version
*/
@Service
public class AuthClientServiceImpl extends AppJPABaseDataServiceImpl<AuthClientEntity, AuthClientDao>
        implements AuthClientService{
    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String , String>(){
        {
            put("clientId","clientId:EQ");
            put("projectId","projectId:EQ");
            put("type","type:EQ");
            put("types","type:IN");
            put("appIds","appId:IN");
            put("projectAppIds","projectAppId:IN");
        }
    };


    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }

    /**
     *分页查询认证信息
     * 
     * @param searchParam
     * @param pageable
     * @author liuke
     * @date 2022/3/9 14:40
     * @return org.springframework.data.domain.Page<com.fosung.system.support.system.entity.sys.AuthClientEntity>
     */
    @Override
    public Page<AuthClientEntity> queryPage(Map<String, Object> searchParam, Pageable pageable) {
        return entityDao.queryAuthClientPage(searchParam,pageable);
    }
    /**
     *根据用户id查询应用
     *
     * @param param
     * @author liuke
     * @date 2022/3/9 14:40
     * @return java.util.Set<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public Set<Map<String,Object>> queryAppByUserId(Map<String,Object> param){
        return entityDao.queryAppByUserId(param);
    }
    /**
     *根据应用和用户查询组织
     *
     * @param param
     * @author liuke
     * @date 2022/3/9 14:41
     * @return java.util.Set<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public Set<Map<String,Object>> queryUserOrgsByUserAndClient( Map<String,Object> param){
        return entityDao.queryUserOrgsByUserAndClient(param);
    }

    /**
     * 描述: 查询详情
     * @param searchParam
     * @return com.fosung.system.support.system.entity.sys.AuthClientEntity
     * @author fuhao
     * @date 2022/4/7 9:07
     **/
    @Override
    public AuthClientEntity queryAuthClientById(Long id) {
        return entityDao.queryAuthClientById(id);
    }
}
