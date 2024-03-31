package com.shou.hpsys.common.utils;

import com.antherd.smcrypto.sm2.Sm2;
import com.antherd.smcrypto.sm3.Sm3;
import com.antherd.smcrypto.sm4.Sm4;
import com.antherd.smcrypto.sm4.Sm4Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/17 16:00
 */
public class CommonCryptogramUtil {
    /* 日志 */
    private static  final Logger LOGGER = LoggerFactory.getLogger(CommonCryptogramUtil.class);

    /* 公钥 */
    private static final String PUBLIC_KEY = "045274c63e9feb5db589a141b78c16bf122172932b71cf45f8c467113ca9e87f9aa66522e539bc832e867303b818fd3435973613067f403c0fde6d44a27797f790";

    /* 私钥 */
    private static final String PRIVATE_KEY = "463b8ca338901f6a6ff1ed60c6eaeb5046dc8eb72990aceaf060d6f817db9fe1";

    /* Sm4对称密钥（生产环境需要改成自己使用的） */
    private static final String KEY = "0123456789abcdeffedcba9876543210";

    /**
     * @description: Sm2加密方法
     * @param: str
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/17 16:09
     */
    public static String doSm2Encrypt(String str) {
        return Sm2.doEncrypt(str, PUBLIC_KEY);
    }

    /**
     * @description: Sm2解密方法
     * @param: str
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/17 16:10
     */
    public static String doSm2Decrypt(String str) {
        return Sm2.doDecrypt(str, PRIVATE_KEY);
    }

    /**
     * @description: SM4加密 cbc模式
     * @param: str
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/17 16:10
     */
    public static String doSm4CbcEncrypt(String str) {
        Sm4Options sm4Options = new Sm4Options();
        sm4Options.setMode("cbc");
        sm4Options.setIv("fedcba98765432100123456789abcdef");
        return Sm4.encrypt(str, KEY, sm4Options);
    }

    /**
     * @description: 解密，cbc模式，输出utf8字符串（采用加密机的方法，用try catch 捕捉异常，返回原文值）
     * @param: str
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/17 16:11
     */
    public static String doSm4CbcDecrypt(String str) {
        Sm4Options sm4Options = new Sm4Options();
        sm4Options.setMode("cbc");
        sm4Options.setIv("fedcba98765432100123456789abcdef");
        String docString = Sm4.decrypt(str, KEY, sm4Options);
        if("".equals(docString)) {
            LOGGER.warn(">>> 字段解密失败，原文值为{}", str);
            return str;
        } else {
            return docString;
        }
    }

    /**
     * @description: 使用私钥签名
     * @param: str
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/17 16:12
     */
    public static String doSignature(String str) {
        return Sm2.doSignature(str, PRIVATE_KEY);
    }

    /**
     * @description: 验证签名结果
     * @param: originalStr
    str
     * @return: boolean
     * @author Yaohui Hu
     * @date: 2024/3/17 16:12
     */
    public static boolean doVerifySignature(String originalStr, String str) {
        return Sm2.doVerifySignature(originalStr, str, PUBLIC_KEY);
    }

    /**
     * @description: 通过杂凑算法获取hash值，用于数据完整性保护
     * @param: str
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/17 16:13
     */
    public static String doHashValue(String str) {
        return Sm3.sm3(str);
    }
}
