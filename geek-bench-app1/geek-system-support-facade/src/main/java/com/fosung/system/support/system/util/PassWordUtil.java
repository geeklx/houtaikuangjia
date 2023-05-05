package com.fosung.system.support.system.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.fosung.framework.common.util.UtilString;
import org.apache.commons.codec.binary.Base64;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密身份证，可逆
 */
public class PassWordUtil {

    private static final String AES_KEY = "0CoJUm6Qyw8W8jud";

    private static final String IV = "0102030405060708";


    public static String desEncrypt(String idCrd) {
        return getAes().encryptHex(idCrd);
    }

    public static String desDecrypt(String encryptIdCard) {
        return getAes().decryptStr(encryptIdCard);
    }

    public static AES getAes() {
        return new AES(Mode.CTS, Padding.PKCS5Padding, AES_KEY.getBytes(), IV.getBytes());
    }


    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    private static final String KEY = "UeXsBA5AMY870G7ljFjv/Zg+T52bbrqR";


    /**
     * @param source 待加密的原字符串
     * @return 返回经过base64编码的字符串
     * @throws Exception
     * @Description: DES进行加密
     */
    public static String encrypt(String source) throws Exception {
        //如果字符串为空，返回原来字符串
        if (UtilString.isBlank(source)){
            return source;
        }
        return encrypt(source, KEY);
    }


    /**
     * @param source 待加密的原字符串
     * @param key    加密时使用的 密钥
     * @return 返回经过base64编码的字符串
     * @throws Exception
     * @Description: DES进行加密
     */
    public static String encrypt(String source, String key) throws Exception {
        byte[] sourceBytes = source.getBytes("UTF-8");
        byte[] keyBytes = Base64.decodeBase64(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decrypted = cipher.doFinal(sourceBytes);
        return Base64.encodeBase64String(decrypted);
    }

    /**
     * @param encryptStr DES加密后的再经过base64编码的密文
     * @return 返回 utf-8 编码的明文
     * @throws Exception
     * @Description: DES解密
     */
    public static String decrypt(String encryptStr) throws Exception {
        //如果原来字符串为空，返回原来字符串
        if (UtilString.isBlank(encryptStr)){
            return encryptStr;
        }
        return decrypt(encryptStr, KEY);
    }

    /**
     * @param encryptStr DES加密后的再经过base64编码的密文
     * @param key        加密使用的密钥
     * @return 返回 utf-8 编码的明文
     * @throws Exception
     * @Description: DES解密
     */
    public static String decrypt(String encryptStr, String key) throws Exception {
        byte[] sourceBytes = Base64.decodeBase64(encryptStr);
        byte[] keyBytes = Base64.decodeBase64(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, "UTF-8");
    }

    /**
     * @return
     * @throws Exception
     * @Description: key生成密钥, 返回168位的密钥
     */
    public static String generateKey() throws Exception {
        //实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //DESede 要求密钥长度为 112位或168位
        kg.init(168);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获得密钥的字符串形式
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    // test
    public static void main(String[] args) {

        try {
            System.out.println(desEncrypt("371524199011144119"));
            System.out.println(decrypt("51sr5F2Yr12G8JmHIkdBC7oN7fq5Mik+"));
            // 生成秘钥

            System.out.println("秘钥：" + KEY);

            // 加密
            String encryptStr = encrypt("371524199011144119");
            System.out.println("密文：" + encryptStr);
            // 解密
            String resource = decrypt(encryptStr);
            System.out.println("明文：" + resource);

//            String resource1 = decrypt("UkCYAH3r05KPLwTp3FCciUCiPFC9+G5Y");
//            System.out.println("明文："+ resource1);

            System.out.println("校验：" + "371524199011144119".equals(resource));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
