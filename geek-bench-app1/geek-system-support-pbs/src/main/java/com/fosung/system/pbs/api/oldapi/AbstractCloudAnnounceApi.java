package com.fosung.system.pbs.api.oldapi;

import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Desc
 * @Author zyy
 * @Date 2021/5/10 20:04
 */
public abstract class AbstractCloudAnnounceApi extends AppIBaseController implements CloudAnnounceApi {

    @Override
    public ResponseParam add(@PathVariable("source") String source, @RequestBody Map<String, Object> param) {
        return null;
    }

    @Override
    public ResponseParam delete(@PathVariable("source") String source, @RequestParam("announceId") Long announceId) {
        return null;
    }
}
