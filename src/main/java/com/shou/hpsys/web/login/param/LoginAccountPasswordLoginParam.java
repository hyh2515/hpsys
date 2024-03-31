package com.shou.hpsys.web.login.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/20 15:50
 */
@Getter
@Setter
public class LoginAccountPasswordLoginParam {

//    账号
    @NotBlank(message = "账号不能为空")
    private String account;

//    密码
    @NotBlank(message = "密码不能为空")
    private String password;
}
