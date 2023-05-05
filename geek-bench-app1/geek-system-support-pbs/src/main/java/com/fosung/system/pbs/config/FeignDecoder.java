/*
 ***************************************************************************************
 * All rights Reserved, Designed By FOSUNG
 * @Title:  FeignDecoder.java   
 * @Package com.fosung.cloud.pbs.rest.client.config   
 * @Description: feign客户端返回解析   
 * @author: 司福林
 * @date:   2020-9-29 17:42:44   
 * @version V1.0 
 * @Copyright: 2020 FOSUNG. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package com.fosung.system.pbs.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fosung.framework.common.exception.AppException;
import com.fosung.framework.common.util.UtilString;
import com.fosung.framework.web.http.ResponseParam;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 *  
 *
 * @ClassName:  FeignDecoder
 * @author: 67077
 * @date:   2020-9-29 17:42:44
 * @since:  v1.0
 */
//@Component
public class FeignDecoder extends SpringDecoder {

	/**
	 * Instantiates a new feign decoder.
	 *
	 */
	public FeignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
		super(messageConverters);

	}

	@Override
	public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {

		byte[] bodyData = Util.toByteArray(response.body().asInputStream());
		String s = new String(bodyData);
		if (UtilString.isNotBlank(s) && s.startsWith("{") && s.endsWith("}")) {
			JSONObject jsonObject = JSON.parseObject(s);
			if (jsonObject.get("success") != null) {
				Boolean success = MapUtils.getBoolean(jsonObject, "success");
				if (!success) {
					throw new AppException(MapUtils.getString(jsonObject, ResponseParam.MESSAGE_PARAM));
				}
			}
		}
		return super.decode(response.toBuilder().body(bodyData).build(), type);
	}

}
