package com.fosung.workbench.configurecenter.shorturl.util;

/**
 * @Description 进制转换工具
 * @Author gaojian
 * @Date 2022/1/17 10:10
 * @Version V1.0
 */
public class ScaleConvert {

    /**
     * 描述: 62 位字符
     * @createDate: 2022/1/17 10:14
     * @author: gaojian
     */
    static final char[] DIGITS =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /**
     * 描述:  十进制转 62 进制
     * @createDate: 2022/1/17 10:11
     * @author: gaojian
     * @modify:
     * @param seq
     * @return: java.lang.String
     */
    public static String to62RadixString(long seq) {
        StringBuilder strBuilder = new StringBuilder();
        while (true) {
            int remainder = (int) (seq % 62);
            strBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        return strBuilder.reverse().toString();
    }

}
