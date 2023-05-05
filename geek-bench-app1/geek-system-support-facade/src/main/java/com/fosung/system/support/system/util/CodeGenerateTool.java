package com.fosung.system.support.system.util;

import com.fosung.framework.common.util.UtilCollection;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 *编码自动生成工具类
 *
 * @author liuke
 * @date  2022/1/26 8:39
 * @version
*/
public class CodeGenerateTool {

    /**
     * 每层组织编码长度
     */
    public final static int CODE_LENGTH = 6;

    private static Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+(\\\\.[0-9]+)?");

    /**
     *生成组织编码，组织编码必须为纯数字类型
     *
     * @param parentCode
     * @param codes
     * @author liuke
     * @date 2022/1/26 11:22
     * @return java.lang.String
     */
    public static String generateOrgCode(String parentCode,List<String> codes) throws Exception{
        //校验编码是否合法
        if(parentCode.length()%CODE_LENGTH!=0||(!NUMBER_PATTERN.matcher(parentCode).matches())){
            throw new Exception("生成code失败，父编码校验失败");
        }
        codes=codes.stream()
                //过滤掉不合法的编码
                .filter(code -> code.startsWith(parentCode)
                        &&(code.length()==parentCode.length()+CODE_LENGTH)
                        &&NUMBER_PATTERN.matcher(code).matches())
                //对编码进行排序
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        if(UtilCollection.sizeIsEmpty(codes)){
            return parentCode+String.format("%0"+CODE_LENGTH+"d",1);
        }else {
            //生成新的下一个编码
            return parentCode+String.format("%0"+CODE_LENGTH+"d",Integer.valueOf(codes.get(0).substring(parentCode.length()+1))+1);
        }
    }

}