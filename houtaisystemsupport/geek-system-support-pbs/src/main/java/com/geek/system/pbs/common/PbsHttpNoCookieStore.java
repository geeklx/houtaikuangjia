package com.geek.system.pbs.common;

import java.util.List;

import com.google.common.collect.Lists;
import com.mzlion.easyokhttp.cookie.CookieStore;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 不对cookie进行任何操作
 * @Author : liupeng
 * @Date : 2020-06-20
 * @Modified By
 */
public class PbsHttpNoCookieStore implements CookieStore {

	@Override
	public void add(HttpUrl uri, List<Cookie> cookies) {
	}

	@Override
	public List<Cookie> get(HttpUrl uri) {
		return Lists.newArrayListWithCapacity(1);
	}

	@Override
	public List<Cookie> getCookies() {
		return Lists.newArrayListWithCapacity(1);
	}

	@Override
	public boolean remove(HttpUrl uri, Cookie cookie) {
		return true;
	}

	@Override
	public boolean removeAll() {
		return true;
	}
}
