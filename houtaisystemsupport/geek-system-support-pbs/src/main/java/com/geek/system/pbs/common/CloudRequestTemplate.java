package com.geek.system.pbs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fosung.framework.common.json.JsonMapper;
import com.fosung.framework.common.util.UtilString;
import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.request.AbsHttpRequest;
import com.mzlion.easyokhttp.response.HttpResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@SuppressWarnings("unchecked")
public class CloudRequestTemplate implements InitializingBean {

	@Setter
	@Autowired
	private CloudProperties cloudProperties;

	private HttpClient httpClient;

	@Getter
	private ObjectMapper objectMapper = new ObjectMapper();

	public CloudRequestTemplate() {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 获取泛型的Collection Type
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses 元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * 发起数组请求
	 * @param appDTRequestCallback
	 * @param entityClass
	 * @param <T>
	 * @return
	 */
	public <T> List<T> doRequestForList(CloudRequestCallback appDTRequestCallback, Class<T> entityClass) {
		String resultText = doRequest(appDTRequestCallback);

		if (UtilString.isBlank(resultText)) {
			return null;
		}

		List<T> resultList = null;
		try {
			resultList = (List<T>) objectMapper.readValue(resultText, getCollectionType(ArrayList.class, entityClass));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	/**
	 * 发起实体对象请求
	 * @param appDTRequestCallback
	 * @param entityClass
	 * @param <T>
	 * @return
	 */
	public <T> T doRequestForBean(CloudRequestCallback appDTRequestCallback, Class<T> entityClass) {
		if (appDTRequestCallback == null) {
			return null;
		}

		String resultText = doRequest(appDTRequestCallback);

		if (UtilString.isBlank(resultText)) {
			return null;
		}

		T result = null;
		try {
			result = objectMapper.readValue(resultText, entityClass);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 发起请求
	 * @param appDTRequestCallback
	 * @return
	 */
	public String doRequest(CloudRequestCallback appDTRequestCallback) {
		if (appDTRequestCallback == null) {
			return null;
		}
		// 构建请求
		AbsHttpRequest absHttpRequest = appDTRequestCallback.doInConnection(httpClient);

		// 设置超时间
		absHttpRequest.connectTimeout(cloudProperties.getConnection().getConnectTimeout());
		// 设置读取超时时间
		absHttpRequest.readTimeout(cloudProperties.getConnection().getReadTimeout());
		// 发起请求
		HttpResponse httpResponse = absHttpRequest.execute();

		if (!httpResponse.isSuccess()) {
			log.info("请求失败：{}", JsonMapper.toJSONString(absHttpRequest));
			return null;
		}

		//        log.info("请求响应数据: {}" , httpResponse.asString());

		return httpResponse.asString();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (httpClient == null) {
			httpClient = HttpClient.Instance;
			httpClient.setCookieStore(new PbsHttpNoCookieStore());
			log.info("创建 dt 客户端请求实例");
		}
	}
}
