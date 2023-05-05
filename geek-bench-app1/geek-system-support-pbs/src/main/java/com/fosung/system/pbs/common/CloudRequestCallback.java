package com.fosung.system.pbs.common;

import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.request.AbsHttpRequest;

public interface CloudRequestCallback {

    AbsHttpRequest doInConnection(HttpClient httpClient);

}
