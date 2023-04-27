package com.geek.system.support.system.util;

import com.fosung.framework.common.exception.AppException;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 加密算法，不可逆
 */
public interface UtilDigest {

    /**
     * 加密身份证
     * @param identifyId
     * @return
     */
    static String digestIdentifyId(String identifyId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");


            byte[] result = digest.digest(identifyId.getBytes());

            return Base64.getEncoder().encodeToString(result);
        } catch (GeneralSecurityException e) {
            throw new AppException("加密身份证信息错误") ;
        }
    }

    public static void main(String[] args) {

        System.out.println( digestIdentifyId("371202197603060089") );

    }

}