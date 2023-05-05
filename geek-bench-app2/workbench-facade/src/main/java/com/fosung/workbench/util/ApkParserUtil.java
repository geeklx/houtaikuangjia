package com.fosung.workbench.util;

import com.fosung.workbench.AppBean.ApkBasic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * apk工具类。封装了获取Apk信息的方法。
 * 包名/版本号/版本名/应用程序名/支持的android最低版本号/支持的SDK版本/建议的SDK版本/所需设备特性等
 */
public   class ApkParserUtil {
    public static final String VERSION_CODE = "versionCode";
    public static final String VERSION_NAME = "versionName";
    public static final String SDK_VERSION = "sdkVersion";
    public static final String PACKAGE = "package";
    public static final String APPLICATION_LABEL = "application-label";
    private ProcessBuilder mBuilder;
    private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";
    private static final String FEATURE_SPLIT_REGEX = "(:')|(',')|'";
    static String softName = "";
    static String[] shellCommand;
    /**
     * aapt所在的目录。
     */
    //windows环境下直接指向appt.exe
    //比如你可以放在src下
    private String mAaptPath = "src/main/java/aapt/";
    static  String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"aapt/aapt.exe";
    //linux下
    //private String mAaptPath = "/usr/local/apktool/aapt";

    public ApkParserUtil() {
        mBuilder = new ProcessBuilder();
        mBuilder.redirectErrorStream(true);
    }


    /**
     * 返回一个apk程序的信息。
     *
     * @param apkPath apk的路径。
     * @return apkInfo 一个Apk的信息。
     */
    public static ApkBasic getApkInfo(String apkPath) throws Exception {
        System.out.println("================================开始执行命令=========================");

        ProcessBuilder mBuilder = new ProcessBuilder();
        mBuilder.redirectErrorStream(true);
        //通过命令调用aapt工具解析apk文件
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"aapt/aapt.exe";

        Process process = mBuilder.command(path, "d", "badging", apkPath)
                .start();
        InputStream is = null;
        is = process.getInputStream();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8));
        String tmp = br.readLine();
        try {
            if (tmp == null || !tmp.startsWith("package")) {
                throw new Exception("参数不正确，无法正常解析APK包。输出结果为:\n" + tmp + "...");
            }
            ApkBasic apkInfo = new ApkBasic();
            do {
                setApkInfoProperty(apkInfo, tmp);
            } while ((tmp = br.readLine()) != null);
            return apkInfo;
        } catch (Exception e) {
            throw e;
        } finally {
            process.destroy();
            closeIO(is);
            closeIO(br);
        }
    }

    /**
     * 设置APK的属性信息。
     *
     * @param apkInfo
     * @param source
     */
    private static void setApkInfoProperty(ApkBasic apkInfo, String source) {
        if (source.startsWith(PACKAGE)) {
            splitPackageInfo(apkInfo, source);
        }else if (source.startsWith(SDK_VERSION)) {
            apkInfo.setServerVersionCode(getPropertyInQuote(source));
        }else if (source.startsWith(APPLICATION_LABEL)) {
            //windows下获取应用名称
            apkInfo.setApplicationLable(getPropertyInQuote(source));
        }  else {
//       System.out.println(source);
        }
    }

    /**
     * 返回出格式为name: 'value'中的value内容。
     *
     * @param source
     * @return
     */
    private static String getPropertyInQuote(String source) {
        int index = source.indexOf("'") + 1;
        return source.substring(index, source.indexOf('\'', index));
    }

    /**
     * 返回冒号前的属性名称
     *
     * @param source
     * @return
     */
    private String getKeyBeforeColon(String source) {
        return source.substring(0, source.indexOf(':'));
    }

    /**
     * 分离出包名、版本等信息。
     *
     * @param apkInfo
     * @param packageSource
     */
    private static void splitPackageInfo(ApkBasic apkInfo, String packageSource) {
        String[] packageInfo = packageSource.split(SPLIT_REGEX);
        apkInfo.setAppPackageName(packageInfo[2]);
        apkInfo.setServerVersionCode(packageInfo[4]);
        apkInfo.setServerVersionName(packageInfo[6]);
    }

    /**
     * 释放资源。
     *
     * @param c 将关闭的资源
     */
    private static final void closeIO(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*   public static void main(String[] args) {
        try {
            String demo = "http://119.188.115.252:8090/resource-handle/uploads/gongzuotai/app_3.0.2.2.apk";
            //demo ="C:\\Users\\fosung\\Downloads\\app_3.0.2.2.apk";
            ApkInfo apkInfo =  getApkInfo(demo);
            System.out.println(apkInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static Map  apkParser(String url) {
        // 获取文件名
        String fileName = url.substring(url.lastIndexOf("."));
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File file = ApkUtil.openStream(url);
        Map<String, Object> map = null;
        if (prefix.equals(".apk")) {
            map = ApkUtil.parseApk(file);
        }
        // 判断是否存在，存在删除
        if (file.exists()) {
            file.delete();
        }
        return map;
    }

}
