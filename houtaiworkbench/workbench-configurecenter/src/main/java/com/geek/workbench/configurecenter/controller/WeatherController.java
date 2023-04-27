package com.geek.workbench.configurecenter.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.workbench.configurecenter.shorturl.util.UrlUtils;
import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.request.GetRequest;
import com.mzlion.easyokhttp.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description 天气服务
 * @Author gaojian
 * @Date 2022/2/9 14:43
 * @Version V1.0
 */
@RestController
@RequestMapping(value = WeatherController.BASE_PATH)
@Slf4j
public class WeatherController {

    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/workbench/weather" ;

    /**
     * 描述:  天气服务地址
     *
     * @createDate: 2022/2/10 9:28
     * @author: gaojian
     */
    @Value("${app.weather_service_url:http://wthrcdn.etouch.cn/weather_mini}")
    private String serviceUrl;

    /**
     * 描述:  redis 操作
     *
     * @createDate: 2022/2/10 9:28
     * @author: gaojian
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 描述:  redis 天气前缀
     *
     * @createDate: 2022/2/10 9:28
     * @author: gaojian
     */
    private final String REDIS_WTHRCDN_PREFIX = "wthrcdn:";

    /**
     * 描述:  获取天气信息接口
     *
     * @createDate: 2022/2/10 9:28
     * @author: gaojian
     */
    @RequestMapping("/get/info")
    public ResponseParam getInfo(@RequestBody JSONObject jsonObject) throws Exception{

        // 1. 组装请求参数
        String city = jsonObject.getString("city");
        if(StringUtils.isBlank(city)){
            return ResponseParam.fail().message("未获取到地市信息！");
        }

        // 2. 读取缓存信息

        String cityToday = stringRedisTemplate.opsForValue().get(REDIS_WTHRCDN_PREFIX + UrlUtils.getURLEncoderString(city));
        if(StringUtils.isNotBlank(cityToday)){
            return ResponseParam.success().message("获取信息成功").data(JSONObject.parse(cityToday));
        }

        GetRequest getRequest = HttpClient.get(serviceUrl + "?city="+city);
        HttpResponse httpResponse = getRequest.execute();

        // 3. 判断请求是否成功了
        if (!httpResponse.isSuccess()) {
            StringBuilder msg = new StringBuilder("远程执行:"+httpResponse.getErrorMessage() + ":" + httpResponse.getErrorCode());
            log.error(msg.toString());
            return ResponseParam.fail().message("获取信息失败");
        }

        // 4. 获取请求内容
        String content = httpResponse.asString();
        if(StringUtils.isNotBlank(content)){
            JSONObject result = JSONObject.parseObject(content,JSONObject.class);
            if(1000 == result.getInteger("status")){
                JSONObject data = result.getJSONObject("data");
                JSONArray forecast = data.getJSONArray("forecast");
                if(forecast != null && forecast.size() > 0){
                    JSONObject today = forecast.getJSONObject(0);
                    String type = today.getString("type");
                    String high = today.getString("high");
                    if(StringUtils.isNotBlank(high)){
                        high = high.replace("高温","");
                    }
                    String low = today.getString("low");
                    if(StringUtils.isNotBlank(low)){
                        low = low.replace("低温","").replace("℃","");
                    }
                    today.put("jointInfo", type + low + "~" + high);

                    // 设置 key 30 分钟过期
                    stringRedisTemplate.opsForValue().set(REDIS_WTHRCDN_PREFIX + UrlUtils.getURLEncoderString(city),today.toJSONString(),30, TimeUnit.MINUTES);
                    return ResponseParam.success().message("获取信息成功").data(today);
                }
                return ResponseParam.success().message("获取信息成功");

            }else{
                return ResponseParam.fail().message("获取天气信息失败");
            }
        }else {
            return ResponseParam.fail().message("获取天气信息失败");
        }
    }

    public static void main(String[] args) {
        System.out.println(UrlUtils.getURLEncoderString("济南"));
    }
}
