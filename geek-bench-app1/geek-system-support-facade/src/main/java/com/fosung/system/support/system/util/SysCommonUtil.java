package com.fosung.system.support.system.util;


import com.fosung.framework.common.util.UtilString;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * 通用工具类
 *
 * @author liuke
 * @date  2021/11/29 11:25
 * @version
*/
public class SysCommonUtil {

    /**
     *数据脱敏
     *
     * @param str
     * @param startNum
     * @param endNum
     * @author liuke
     * @date 2021/11/29 11:31
     * @return java.lang.String
     */
    public static String  showStartAndEnd(String str,int startNum,int endNum) {
        String s1 = StringUtils.right(str, endNum);
        String s2 = StringUtils.leftPad(s1, StringUtils.length(str), "*");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<startNum;i++) {
            sb.append("*");
        }
        String s3 = StringUtils.removeStart(s2, sb.toString());
        String s4 = StringUtils.left(str, startNum).concat(s3);
        return s4;
    }

    /**
     *手机号脱敏处理
     *
     * @param telephone
     * @author liuke
     * @date 2021/11/29 11:33
     * @return java.lang.String
     */
    public static String formatTelephone(String telephone){
        return showStartAndEnd(telephone,3,2);
    }

    /**
     *身份证号脱敏处理
     *
     * @param idCard
     * @author liuke
     * @date 2021/11/29 11:33
     * @return java.lang.String
     */
    public static String formatIdcard(String idCard){
        if(UtilString.isNotBlank(idCard)){
            return showStartAndEnd(idCard,4,2);
        }else {
            return idCard;
        }
    }
}
