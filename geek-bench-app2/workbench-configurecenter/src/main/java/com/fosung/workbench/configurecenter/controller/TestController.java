package com.fosung.workbench.configurecenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.workbench.util.AppHeaderResolution;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/workbench/test")
public class TestController{

    @RequestMapping("/login")
    public ResponseParam login(@RequestBody JSONObject jsonObject) throws Exception{
        System.out.println(jsonObject.toJSONString());
        Map<String,Object> map = Maps.newHashMap();
        String userName ="";
        if(null !=jsonObject && null != jsonObject.get("username") ){
              userName = jsonObject.get("username").toString();
            if(userName.equals("15810900543")){
                map.put("token","abcdefg");
            }else if(userName.equals("15137615080")){
                map.put("token","abcdefgh");
            }else if(userName.equals("15810900544")){
                map.put("token","abcdefghj");
            }else if(userName.equals("15810900545")){
                map.put("token","abcdefghjk");
            }else if(userName.equals("15810900546")){
                map.put("token","abcdefghjki");
            }else if(userName.equals("15810900547")){
                map.put("token","abcdefghjkij");
            }else if(userName.equals("13912345671")){
                map.put("token","aa13912345671");
            }else if(userName.equals("13912345672")){
                map.put("token","aa13912345672");
            }else if(userName.equals("13912345673")){
                map.put("token","aa13912345672");
            }else if(userName.equals("13912345674")){
                map.put("token","aa13912345674");
            }else if(userName.equals("13912345675")){
                map.put("token","aa13912345675");
            }else if(userName.equals("13912345676")){
                map.put("token","aa13912345676");
            }else{
                return ResponseParam.fail().message("请先注册账号");
            }
        }else{
            return ResponseParam.fail().message("请先注册账号");
        }
       // map.put("token","sdlafhjaopdvgjpf[wagutpofjo;lsgupourfpogjudfjdasjdfpj;dfjasfsf"+ran1);
        //map.put("token","sdlafhjaopdvgjpf[wagutpofjo;lsgupourfpogjudfjdasjdfpj;dfjasfsf");
        return ResponseParam.success().message("获取信息成功").data(map);
    }
    @RequestMapping("/registered")
    public ResponseParam registered(@RequestBody JSONObject jsonObject) throws Exception{
        Map<String,Object> map = Maps.newHashMap();
        return ResponseParam.success().message("注册成功");
    }
    @RequestMapping("/forgotPwd")
    public ResponseParam forgotPwd(@RequestBody JSONObject jsonObject) throws Exception{
        Map<String,Object> map = Maps.newHashMap();
        return ResponseParam.success().message("设置成功");
    }
    @RequestMapping("/refreshToken")
    public ResponseParam refreshToken(@RequestBody JSONObject jsonObject) throws Exception{
        Map<String,Object> map = Maps.newHashMap();
        map.put("token","sdlafhjaopdvgjpf[wagutpofjo;lsgupourfpogjudfjdasjdfpj;dfjasfsf");
        return ResponseParam.success().message("获取信息成功").data(map);
    }
    @RequestMapping("/exit")
    public ResponseParam exit(@RequestBody JSONObject jsonObject) throws Exception{
        Map<String,Object> map = Maps.newHashMap();
        return ResponseParam.success().message("登出成功");
    }
    @RequestMapping("/verificationCode")
    public ResponseParam verificationCode(@RequestBody JSONObject jsonObject) throws Exception{
        Map<String,Object> map = Maps.newHashMap();
        map.put("code","123456");
        return ResponseParam.success().message("获取信息成功").data(map);
    }
    @RequestMapping("/verificationImg")
    public ResponseParam verificationImg(@RequestBody JSONObject jsonObject) throws Exception{
        Map<String,Object> map = Maps.newHashMap();
        map.put("code","123456");
        return ResponseParam.success().message("获取信息成功").data(map);
    }
    @RequestMapping("/userInfo")
    public ResponseParam userinfo( HttpServletRequest servletRequest ) throws Exception{
         AppHeaderResolution.HeaderMessage headerMessage = AppHeaderResolution.resolutionHeader(servletRequest);
         String token = headerMessage.getToken();
         Map<String,Object> map = Maps.newHashMap();
         if(null == token || "".equals(token)){
             return ResponseParam.fail().message("获取信息失败").data(map);
         }else{
             if(token.equals("abcdefg")){
                 map.put("id","12345");
                 map.put("userId","12345");
                 map.put("username","lhm");
                 map.put("code","12345");
                 map.put("name","韩梅梅");
                 map.put("phonenum","15810900543");
                 map.put("cityName","青岛");
             }else if(token.equals("abcdefgh")){
                 map.put("id","1123456");
                 map.put("userId","1123456");
                 map.put("username","hdj");
                 map.put("code","1123456");
                 map.put("name","文文");
                 map.put("phonenum","15137615080");
                 map.put("cityName","济南");
             }else if(token.equals("abcdefghj")){
                 map.put("id","11234567");
                 map.put("userId","11234567");
                 map.put("username","hdj2");
                 map.put("code","11234567");
                 map.put("name","测试2");
                 map.put("phonenum","15810900544");
                 map.put("cityName","泰安");
             }else if(token.equals("abcdefghjk")){
                 map.put("id","112345678");
                 map.put("userId","112345678");
                 map.put("username","hdj3");
                 map.put("code","112345678");
                 map.put("name","测试3");
                 map.put("phonenum","15810900545");
                 map.put("cityName","济南");
             }else if(token.equals("abcdefghjki")){
                 map.put("id","123456789");
                 map.put("userId","123456789");
                 map.put("username","hdj3");
                 map.put("code","123456789");
                 map.put("name","测试3");
                 map.put("phonenum","15810900546");
                 map.put("cityName","济南");
             }else if(token.equals("abcdefghjkij")){
                 map.put("id","11234567890");
                 map.put("userId","11234567890");
                 map.put("username","hdj4");
                 map.put("code","11234567890");
                 map.put("name","测试4");
                 map.put("phonenum","15810900547");
                 map.put("cityName","济南");
             }else if(token.equals("aa13912345671")){
                 map.put("id","13912345671");
                 map.put("userId","13912345671");
                 map.put("username","csxz1");
                 map.put("code","13912345671");
                 map.put("name","测试乡镇1");
                 map.put("phonenum","13912345671");
                 map.put("cityName","济南");
             }else if(token.equals("aa13912345672")){
                 map.put("id","13912345672");
                 map.put("userId","13912345672");
                 map.put("username","csxz2");
                 map.put("code","13912345672");
                 map.put("name","测试乡镇2");
                 map.put("phonenum","13912345672");
                 map.put("cityName","济南");
             }else if(token.equals("aa13912345673")){
                 map.put("id","13912345673");
                 map.put("userId","13912345673");
                 map.put("username","csxz3");
                 map.put("code","13912345673");
                 map.put("name","测试乡镇3");
                 map.put("phonenum","13912345673");
                 map.put("cityName","济南");
             }else if(token.equals("aa13912345674")){
                 map.put("id","13912345674");
                 map.put("userId","13912345674");
                 map.put("username","csxz4");
                 map.put("code","13912345674");
                 map.put("name","测试乡镇4");
                 map.put("phonenum","13912345674");
                 map.put("cityName","济南");
             }else if(token.equals("aa13912345675")){
                 map.put("id","13912345675");
                 map.put("userId","13912345675");
                 map.put("username","csxz5");
                 map.put("code","13912345675");
                 map.put("name","测试乡镇5");
                 map.put("phonenum","13912345675");
                 map.put("cityName","济南");
             }else if(token.equals("aa13912345676")){
                 map.put("id","13912345676");
                 map.put("userId","13912345676");
                 map.put("username","csxz6");
                 map.put("code","13912345676");
                 map.put("name","测试乡镇6");
                 map.put("phonenum","13912345676");
                 map.put("cityName","济南");
             }else{
                 map.put("id","11234567891");
                 map.put("userId","11234567891");
                 map.put("username","hdj5");
                 map.put("code","11234567891");
                 map.put("name","测试5");
                 map.put("phonenum","15810900548");
                 map.put("cityName","济南");
             }
             map.put("signature","签名");
             map.put("orgCode","qfwyh");
             map.put("orgName","中国共产党曲阜市委员会");
             map.put("takeOrgName","中国共产党曲阜市委员会");
             map.put("position","职位");

             map.put("landline","座机");
             map.put("hostmail","邮箱");
             map.put("sex",0);
             map.put("photo","http://119.188.115.252:8090/resource-handle/uploads/gongzuotai/aa.jpg");
             //后台另写接口获取
             map.put("organization","http://www.baidu.com/?condition=login");
             map.put("myCollection","dataability://com.github.geekcodesteam.app.hs.act.slbapp.BjyyAct{act}?query1={s}aaaaa&query2={s}2a&query3={s}3a");
             map.put("myMsg","http://www.baidu.com/");
             map.put("ordId","123456");
             map.put("identityId","123456");
             map.put("fingerprint",false);
         }
        // List l = [{"roleCode":"YWGLY","roleName":"运维管理员","orgId":"3496254971291118781","orgCode":"11","orgName":null,"orgSource":null,"orgLevel":1,"ext":"3496254971291118781"}];
        return ResponseParam.success().message("获取信息成功").data(map);
    }

    @RequestMapping("/update/img")
    public ResponseParam update(@RequestBody JSONObject jsonObject ) throws Exception{
        Map map= new HashMap();
        return ResponseParam.success().message("上传成功");
    }



}
