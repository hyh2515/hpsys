package com.shou.hpsys.web.user.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 编辑个人信息参数
 * @author: Yaohui Hu
 * @date 2024/3/20 20:53
 */
@Getter
@Setter
public class UserUpdateInfoParam {

    //    id
    @NotBlank(message = "id不能为空")
    private String id;

    //    姓名
    @NotBlank(message = "name不能为空")
    private String name;

    //    年龄
    private String age;

    //    通信地址
    private String mailingAddress;

    //    电话
    private String phone;
}
