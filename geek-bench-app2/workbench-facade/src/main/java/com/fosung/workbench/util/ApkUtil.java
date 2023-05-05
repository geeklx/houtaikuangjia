package com.fosung.workbench.util;


import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApkUtil {

    /**
     * 解析Apk文件
     *
     * @param file Apk的文件路径
     * @return
     */
    public static Map<String, Object> parseApk(File file) {
        Map<String, Object> map = new HashMap();
        try {
            // File file = new File(url);
            ApkFile apkFile = new ApkFile(file);
            ApkMeta apkMeta = apkFile.getApkMeta();
            map.put("appName", apkMeta.getLabel());
            map.put("appPackageName", apkMeta.getPackageName());
            map.put("versionCode", apkMeta.getVersionCode());
            map.put("versionName", apkMeta.getVersionName());
            map.put("packetSize", (double) (file.length() * 100 / 1024 / 1024) / 100 + " MB");

            apkFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * String url = "https://img.cheguakao.com/prodimg/20200927/__UNI__99584F9_0106120442.ipa";
     * 网络资源下载到项目
     *
     * @param url
     * @return
     */
    public static File openStream(String url) {
        //对本地文件命名
        String fileName = url.substring(url.lastIndexOf("."));
        File file = null;
        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", fileName);
            //下载
            urlfile = new URL(url);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //可返回File  对象  这里只返回文件的路径
        return file;
    }


}

