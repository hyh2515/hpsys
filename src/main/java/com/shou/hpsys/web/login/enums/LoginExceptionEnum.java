package com.shou.hpsys.web.login.enums;

import lombok.Getter;

/**
 * @description: 登录错误枚举
 * @author: Yaohui Hu
 * @date 2024/3/20 15:45
 */
@Getter
public enum LoginExceptionEnum {
//    账号错误
    ACCOUNT_ERROR("账号错误"),
//    账号已停用
    ACCOUNT_DISABLED("账号已停用"),
//    密码错误
    PWD_ERROR("密码错误"),
//    密码解密失败，请检查前端公钥
    PWD_DECRYPT_ERROR("密码解密失败，请检查前端公钥");

    private final String value;
    LoginExceptionEnum(String value) {
        this.value = value;
    }
}
