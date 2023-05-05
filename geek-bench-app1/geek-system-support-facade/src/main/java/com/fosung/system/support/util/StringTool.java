package com.fosung.system.support.util;

import com.nimbusds.oauth2.sdk.util.StringUtils;

/**
 *
 * 特殊字符串转译处理
 *
 * @author liuke
 * @date  2022/4/27 10:43
 * @version
*/
public class StringTool {
    /**
     * pg数据库检索时，转换特殊字符
     *
     * @param s 需要转义的字符串
     * @return 返回转义后的字符串
     */
    public static String escapeQueryChars(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        //查询字符串一般不会太长，挨个遍历也花费不了多少时间
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 需要处理的字符串
            if (c == '\\' || c == '%' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')'
                    || c == '_' || c == ':' || c == '^'	|| c == '[' || c == ']' || c == '\"'
                    || c == '{' || c == '}' || c == '~' || c == '*' || c == '?'
                    || c == '|' || c == '&' || c == ';' || c == '/' || c == '.'
                    || c == '$' || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
