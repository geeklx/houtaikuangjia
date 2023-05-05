package com.fosung.workbench.configurecenter.bean;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 与App对接类如需修改联系app统一修改
 *
 * @author liuke
 * @date  2021/11/12 8:46
 * @version
*/
@Data
public class InitMessageBean implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title_title="";
    /**
     * 用户协议
     */
    private String user="";
    /**
     * 隐私协议
     */
    private String privacy="";
    /**
     * 广告页图片
     */
    private String advertimage="";
    /**
     * 广告页url
     */
    private String advertlinkurl="";
    /**
     * 语言
     */
    private String language_language="";
    /**
     * 电话
     */
    private String customer_phone="";
    /**
     *引导页
     */
    private List<String> guideimage= Lists.newArrayList();
    /**
     * 客户端主题
     */
    private String theme_theme="";
    /**
     * 水印姓名
     */
    private String watermark_contacts_name="";
    /**
     * 水印手机号后4位
     */
    private String watermark_contacts_phone4="";
    /**
     * AccessKey
     */
    private String authorization_ak="";
    /**
     * AccessScrect
     */
    private String authorization_sk="";
    /**
     * 认证版本
     */
    private String authorization_version="";
    /**
     * 是否启用指纹
     */
    private String fingerprint_fingerprint="";
    /**
     * 是否强制登录
     */
    private String login_login="";
    /**
     * 置灰结束时间
     */
    private String short_style_end_time="";
    /**
     * 置灰
     */
    private String short_style_style="";
    /**
     * 置灰开始时间
     */
    private String short_style_start_time="";

    /**
     *项目名称
     */
    private String project_name ="";
    /**
     *项目编号
     */
    private String project_code ="";
    /**
     * 终端图片
     */
    private String terminal_logo="";
}
