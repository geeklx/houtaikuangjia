package com.geek.workbench.AppBean;

import lombok.Data;

/**
 * @author lihuiming
 * @className AppParam
 * @description: apk信息
 * @date 2021/10/1811:18
 */
@Data
public class ApkBasic {
    private String apkPath ="";// 下载地址
    private String serverVersionCode ="";// 文件版本号
    private String serverVersionName ="";// 文件版本名字
    private String appPackageName ="";// 文件包名
    private String updateInfoTitle ;// 更新标题
    private String updateInfo;// 更新内容 支持换行"版本：1.01" + "    " + "大小：10.41M\n" + "1.修改已知问题\n2.加入动态绘本\n3.加入小游戏等你来学习升级"
    private String md5 ="";// md5校验
    private Boolean isForce=false;
    private String upgradeBackImg;//更新背景图
    /**
     * 应用程序名
     */
    private String applicationLable;
}
