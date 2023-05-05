package com.fosung.workbench.service.busi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.config.AppProperties;
import com.fosung.framework.dao.support.service.jpa.AppJPABaseDataServiceImpl;
import com.fosung.workbench.dao.busi.BusiOrgAdmDao;
import com.fosung.workbench.dict.CityLevel;
import com.fosung.workbench.dto.busi.BusiOrgAdmDto;
import com.fosung.workbench.entity.busi.BusiOrgAdmEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BusiOrgAdmServiceImpl extends AppJPABaseDataServiceImpl<BusiOrgAdmEntity, BusiOrgAdmDao>
        implements BusiOrgAdmService {
    @Autowired
    protected AppProperties appProperties;
    //获取行政树列表
    private String treeSuffix = "/gwapi/workbenchserver/api/org/queryAdmTree";
    /**
     * 查询条件表达式
     */
    private Map<String, String> expressionMap = new LinkedHashMap<String, String>() {
        {
            put("admId", "admId:EQ");
            put("orgId", "orgId:EQ");
            put("userId", "userId:EQ");
            put("enable", "enable:EQ");
            put("admParentId", "admParentId:EQ");
            put("level", "level:EQ");
        }
    };

    @Override
    public Map<String, String> getQueryExpressions() {
        return expressionMap;
    }

    /**
     * 保存区域组织
     *
     * @param busiOrgAdmDto
     * @return
     */
    @Override
    public Map saveSelectedAdm(BusiOrgAdmDto busiOrgAdmDto) {
        Map map = new HashMap();
        //id不为空，进行更新操作，否则进行添加
       /* if(busiOrgAdmDto.getCity().getId() != null){
            //由请求参数中获取需要更新的字段
            Set<String> updateFields = UtilDTO.toDTOExcludeFields(busiOrgAdmDto, Arrays.asList("id")).keySet();
            //按照字段更新对象
            busiOrgAdmService.update( busiOrgAdmDto.getCity() , updateFields ) ;
            return ResponseParam.updateSuccess() ;
        }else{*/
        BusiOrgAdmEntity province = getProvince(busiOrgAdmDto);
        map.put("province", province);
        if (null != busiOrgAdmDto.getCity()) {
            BusiOrgAdmEntity city = getCity(busiOrgAdmDto);
            map.put("city", city);
        }
        if (null != busiOrgAdmDto.getRegion()) {
            BusiOrgAdmEntity region = getRegion(busiOrgAdmDto);
            map.put("region", region);
        }
        if (null != busiOrgAdmDto.getStreet()) {
            BusiOrgAdmEntity street = getStreet(busiOrgAdmDto);
            map.put("street", street);
        }
        return map;
    }

    /**
     * 获取城市信息
     *
     * @param busiOrgAdmDto
     * @return
     */
    public BusiOrgAdmEntity getProvince(BusiOrgAdmDto busiOrgAdmDto) {
        BusiOrgAdmEntity province = busiOrgAdmDto.getProvince();
        Map searchMap = new HashMap();
        //判断是否已存在城市
        searchMap.put("admId", busiOrgAdmDto.getProvince().getAdmId());
        searchMap.put("orgId", busiOrgAdmDto.getProvince().getOrgId());
        searchMap.put("userId", busiOrgAdmDto.getUserId());
        //searchMap.put("enable", true);
        searchMap.put("level", CityLevel.province.getValue());
        List<BusiOrgAdmEntity> busiOrgAdmList = this.queryAll(searchMap);
        if (busiOrgAdmList.isEmpty()) {
            searchMap = new HashMap();
            //判断是否已有其他选中省  如有 改为false
            searchMap.put("userId", busiOrgAdmDto.getUserId());
            searchMap.put("enable", true);
            searchMap.put("level", CityLevel.province.getValue());
            List<BusiOrgAdmEntity> oldBusiOrgAdmList = this.queryAll(searchMap);
            for (BusiOrgAdmEntity entity : oldBusiOrgAdmList) {
                entity.setEnable(false);
                this.update(entity, Arrays.asList("enable"));
            }
            //保存省
            province.setId(null);
            province.setAdmParentId("0");
            province.setEnable(true);
            province.setUserId(busiOrgAdmDto.getUserId());
            province = this.save(province);
        } else {
            //已有该省
            province = busiOrgAdmList.get(0);
            province.setEnable(true);
            this.update(province, Arrays.asList("enable"));
        }
        return province;
    }

    /**
     * 获取城市
     *
     * @param busiOrgAdmDto
     * @return
     */
    public BusiOrgAdmEntity getCity(BusiOrgAdmDto busiOrgAdmDto) {
        BusiOrgAdmEntity city = busiOrgAdmDto.getCity();
        Map searchMap = new HashMap();
        //判断是否已有其他选中城市  如有 改为false
        searchMap.put("userId", busiOrgAdmDto.getUserId());
        searchMap.put("enable", true);
        searchMap.put("level", CityLevel.city.getValue());
        List<BusiOrgAdmEntity> oldBusiOrgAdmList = this.queryAll(searchMap);
        for (BusiOrgAdmEntity entity : oldBusiOrgAdmList) {
            if(!entity.getAdmId().equals(busiOrgAdmDto.getCity().getAdmId())){
                entity.setEnable(false);
                this.update(entity, Arrays.asList("enable"));
            }
        }
        searchMap = new HashMap();
        //判断是否已存在城市
        searchMap.put("admId", busiOrgAdmDto.getCity().getAdmId());
        searchMap.put("orgId", busiOrgAdmDto.getCity().getOrgId());
        searchMap.put("userId", busiOrgAdmDto.getUserId());
        searchMap.put("level", CityLevel.city.getValue());
        List<BusiOrgAdmEntity> busiOrgAdmList = this.queryAll(searchMap);
        if (busiOrgAdmList.isEmpty()) {

            //保存城市
            city.setId(null);
            city.setEnable(true);
            city.setUserId(busiOrgAdmDto.getUserId());
            city = this.save(city);
        } else {
            //已有该城市
            city = busiOrgAdmList.get(0);
            city.setEnable(true);
            this.update(city, Arrays.asList("enable"));
        }
        return city;
    }
    /**
     * 获取地区
     *
     * @param busiOrgAdmDto
     * @return
     */
    public BusiOrgAdmEntity getRegion(BusiOrgAdmDto busiOrgAdmDto) {
        BusiOrgAdmEntity region = busiOrgAdmDto.getRegion();
        Map searchMap = new HashMap();
        //判断是否已有其他选中地区  如有 改为false
        searchMap.put("userId", busiOrgAdmDto.getUserId());
        searchMap.put("enable", true);
        searchMap.put("level", CityLevel.regional.getValue());
        //保存地区
        List<BusiOrgAdmEntity> oldBusiOrgAdmList = this.queryAll(searchMap);
        for (BusiOrgAdmEntity entity : oldBusiOrgAdmList) {
            if(!entity.getAdmId().equals(busiOrgAdmDto.getRegion().getAdmId())){
                entity.setEnable(false);
                this.update(entity, Arrays.asList("enable"));
            }
        }
        searchMap = new HashMap();
        //判断是否已存在该地区
        searchMap.put("admId", busiOrgAdmDto.getRegion().getAdmId());
        searchMap.put("orgId", busiOrgAdmDto.getRegion().getOrgId());
        searchMap.put("userId", busiOrgAdmDto.getUserId());
        searchMap.put("level", CityLevel.regional.getValue());
        List<BusiOrgAdmEntity> regionList = this.queryAll(searchMap);
        if (regionList.isEmpty()) {
            region.setId(null);
            region.setEnable(true);
            region.setUserId(busiOrgAdmDto.getUserId());
            region = this.save(region);
        } else {
            //已有该地区
            region = regionList.get(0);
            region.setEnable(true);
            this.update(region, Arrays.asList("enable"));
        }

        return region;
    }
    /**
     * 获取街道
     *
     * @param busiOrgAdmDto
     * @return
     */

    public BusiOrgAdmEntity getStreet(BusiOrgAdmDto busiOrgAdmDto) {
        {
            BusiOrgAdmEntity street = busiOrgAdmDto.getStreet();
            Map searchMap = new HashMap();
            //判断是否已有其他选中街道  如有 改为false
            searchMap.put("userId", busiOrgAdmDto.getUserId());
            searchMap.put("enable", true);
            searchMap.put("level", CityLevel.street.getValue());
            List<BusiOrgAdmEntity> oldBusiOrgAdmList = this.queryAll(searchMap);
            for (BusiOrgAdmEntity entity : oldBusiOrgAdmList) {
                if(!entity.getAdmId().equals(busiOrgAdmDto.getStreet().getAdmId())) {
                    entity.setEnable(false);
                    this.update(entity, Arrays.asList("enable"));
                }
            }
             searchMap = new HashMap();
            //判断是否已存在该街道
            searchMap.put("admId", busiOrgAdmDto.getStreet().getAdmId());
            searchMap.put("orgId", busiOrgAdmDto.getStreet().getOrgId());
            searchMap.put("userId", busiOrgAdmDto.getUserId());
            searchMap.put("level", CityLevel.street.getValue());
            List<BusiOrgAdmEntity> regionList = this.queryAll(searchMap);
            if (regionList.isEmpty()) {

                //保存街道
                street.setId(null);
                street.setEnable(true);
                street.setUserId(busiOrgAdmDto.getUserId());
                street.setLevel(CityLevel.street.getValue());
                street = this.save(street);
            } else {
                //已有该街道
                street = regionList.get(0);
                street.setEnable(true);
                this.update(street, Arrays.asList("enable"));
            }
            return street;
        }
    }

    /**
     * 　　* @description: 获取行政树
     * 　　* @param userUrl  token
     * 　　* @return JSONObject
     * 　　* @author lihuiming
     * 　　* @date 2021/11/24 19:13
     *
     */
    @Override
    public ConcurrentHashMap queryAdmTree(BusiOrgAdmDto busiOrgAdmDto) {
        ConcurrentHashMap treeMap = new ConcurrentHashMap();
        //获取前缀路径
        Map<String, Object> globalParams = this.appProperties.getGlobalParams();
        String commonUrl = globalParams.get("umsUrl").toString();

        //获取用户信息地址 传递header及参数
        String treeUrl = commonUrl  + treeSuffix;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("treeClientId", busiOrgAdmDto.getTreeClientId()));
        nvps.add(new BasicNameValuePair("amdId", busiOrgAdmDto.getAmdId()));
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(treeUrl);
        httpPost.addHeader("ContentType", "application/json;charset=UTF-8");
        JSONObject treeResult = new JSONObject();
        try {
            //获取返回数据
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse httpResponse = httpclient.execute(httpPost);


            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "utf-8");
                      treeMap = JSON.parseObject(result, ConcurrentHashMap.class);
                    //treeResult = JSONObject.parseObject(result);
                }
            }
            //treeMap.put("treeObject", treeResult);
        } catch (Exception e) {
            treeMap.put("msg", "接口调用异常！");
            return treeMap;
        }
        return treeMap;
    }

    /**
     * 查询区域组织
     *
     * @param busiOrgAdmDto
     * @return
     */
    @Override
    public ConcurrentHashMap querySelectedAdm(BusiOrgAdmDto busiOrgAdmDto) {
        //获取查询参数
        // Map<String, Object> searchParam =  UtilDTO.toDTO(busiOrgAdmDto, null);
        ConcurrentHashMap map = new ConcurrentHashMap();
        ConcurrentHashMap<String, Object>  treeMap = this.queryAdmTree(busiOrgAdmDto);
        Map<String, Object> searchParam = new HashMap<>();
        Map root = new HashMap();
        List<Map<String,Object>> treeList = new ArrayList<Map<String,Object>>();
        if (null == busiOrgAdmDto.getUserId()) {
            for(Map.Entry<String, Object> entry: treeMap.entrySet()) {
                if(entry.getKey().equals("data")){
                    JSONObject  json = (JSONObject )entry.getValue();
                    Map jsonMap = toHashMap(json);
                    root = toHashMap((JSONObject )jsonMap.get("root"));
                    root.put("enable",false);
                    treeList =  ((List<Map<String,Object>> )jsonMap.get("tree"));
                    if(!treeList.isEmpty()){
                        for(Map<String,Object> tree :treeList ){
                                tree.put("enable",false);
                        }
                    }
                }
            }
            map.put("root",root);
            map.put("tree",treeList);
            return  map;
        }else{
            searchParam.put("userId", busiOrgAdmDto.getUserId());
            searchParam.put("admParentId", busiOrgAdmDto.getAmdId());
            //searchParam.put("level", busiOrgAdmDto.getLevel());
            searchParam.put("enable",true);
            //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
            List<BusiOrgAdmEntity> List = this.queryAll(searchParam);
            if(!List.isEmpty()){
                for(BusiOrgAdmEntity entity : List){
                    for(Map.Entry<String, Object> entry: treeMap.entrySet()) {
                        if(entry.getKey().equals("data")){
                            //ConcurrentHashMap value =  (ConcurrentHashMap)entry.getValue();
                            JSONObject  json = (JSONObject )entry.getValue();
                            Map jsonMap = toHashMap(json);
                            root = toHashMap((JSONObject )jsonMap.get("root"));
                            root.put("enable","true");
                            treeList =  ((List<Map<String,Object>> )jsonMap.get("tree"));
                            if(!treeList.isEmpty()){
                                for(Map<String,Object> tree :treeList ){
                                    if(entity.getLevel().equals(tree.get("level").toString()) && entity.getAdmName().equals(tree.get("admName").toString())){
                                        tree.put("enable",true);
                                    }else{
                                        tree.put("enable",false);
                                    }
                                }
                            }
                        }
                    }
                }
                map.put("root",root);
                map.put("tree",treeList);
            }else{
                for(Map.Entry<String, Object> entry: treeMap.entrySet()) {
                    if(entry.getKey().equals("data")){
                        JSONObject  json = (JSONObject )entry.getValue();
                        Map jsonMap = toHashMap(json);
                        root = toHashMap((JSONObject )jsonMap.get("root"));
                        root.put("enable",false);
                        treeList =  ((List<Map<String,Object>> )jsonMap.get("tree"));
                        if(!treeList.isEmpty()){
                            for(Map<String,Object> tree :treeList ){
                                tree.put("enable",false);
                            }
                        }
                    }
                }
                map.put("root",root);
                map.put("tree",treeList);
                return  map;
            }
            return map;
        }
    }
    public static Map<String, Object> toHashMap(JSONObject jsonObject) {
        // 用于存储接收到的key:value
        Map<String, Object> data = new HashMap<String, Object>();
        // 获取json对象中的键
        @SuppressWarnings("unchecked")
        Set<String> keySet = jsonObject.keySet();
        String key = "";
        Object value = null;
        // 遍历jsonObject数据，添加到Map对象
        for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
            key = iterator.next();
            value = jsonObject.get(key);
            data.put(key, value);
        }

        return data;
    }
    /**
     * 获取选中城市的所有区域
     *
     * @param cityList
     * @param busiOrgAdmDto
     * @return
     */
    public List<BusiOrgAdmEntity> getRegions(List<BusiOrgAdmEntity> cityList, BusiOrgAdmDto busiOrgAdmDto) {
        List<BusiOrgAdmEntity> regionalLists = new ArrayList<>();
        Map<String, Object> searchParam = new HashMap<>();
        for (BusiOrgAdmEntity cityEntity : cityList) {
            searchParam = new HashMap<>();
            searchParam.put("userId", busiOrgAdmDto.getUserId());
            searchParam.put("admParentId", cityEntity.getAdmId());
            searchParam.put("level", CityLevel.regional.getValue());
            List<BusiOrgAdmEntity> regionalList = this.queryAll(searchParam);
            if (!regionalList.isEmpty()) {
                regionalLists.addAll(regionalList);
            }
        }
        return regionalLists;
    }

    /**
     * 获取选中区域的所有街道
     *
     * @param regionalList
     * @param busiOrgAdmDto
     * @return
     */
    public List getStreets(List<BusiOrgAdmEntity> regionalList, BusiOrgAdmDto busiOrgAdmDto) {
        List<BusiOrgAdmEntity> streetLists = new ArrayList<>();
        Map<String, Object> searchParam = new HashMap<>();
        for (BusiOrgAdmEntity regionalEntity : regionalList) {
            searchParam = new HashMap<>();
            searchParam.put("userId", busiOrgAdmDto.getUserId());
            searchParam.put("admParentId", regionalEntity.getAdmId());
            searchParam.put("level", CityLevel.street.getValue());
            List<BusiOrgAdmEntity> streetList = this.queryAll(searchParam);
            if (!streetList.isEmpty()) {
                streetLists.addAll(streetList);
            }
        }
        return streetLists;
    }
}
