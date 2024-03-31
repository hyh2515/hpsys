package com.shou.hpsys.web.user.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户修改密码参数
 * @author: Yaohui Hu
 * @date 2024/3/20 20:47
 */
@Getter
@Setter
public class UserUpdatePwdParam {

//    旧密码
    @NotBlank(message = "password不能为空")
    private String password;

//    新密码
    @NotBlank(message = "newPassword不能为空")
    private String newPassword;
}
