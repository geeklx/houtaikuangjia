package com.fosung.system.pbs.api.oldapi;

import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public class AbstractCloudRoleApi extends AppIBaseController implements CloudRoleApi {

	@Override
	public ResponseParam getRole(@PathVariable("source") String source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseParam getRoleByApp(@PathVariable("source") String source,@RequestParam("appId") Long appId){
		return null;
	}

	@Override
	public JSONObject queryRoleMenu(@PathVariable("source") String source,@RequestBody Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
