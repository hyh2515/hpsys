package com.shou.hpsys.web.user.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户添加参数
 * @author: Yaohui Hu
 * @date 2024/3/20 20:36
 */
@Getter
@Setter
public class UserAddParam {

//    账号
    @NotBlank(message = "account不能为空")
    private String account;

//    姓名
    @NotBlank(message = "name不能为空")
    private String name;

//    头像
    private String avatar;

//    年龄
    private String age;

//    通信地址
    private String mailingAddress;

//    电话
    private String phone;

//    排序码
    private Integer sortCode;
}
