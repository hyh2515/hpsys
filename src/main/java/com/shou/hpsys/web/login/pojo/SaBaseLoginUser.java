package com.shou.hpsys.web.login.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description: 基础的登录用户对象，可继承此类扩展更多属性
 * @author: Yaohui Hu
 * @date 2024/3/20 16:14
 */
@Getter
@Setter
public abstract class SaBaseLoginUser {
//    id
    private String id;

//    头像
    private String avatar;

//    账号
    private String account;

//    姓名
    private String name;

//    年龄
    private String age;

//    通信地址
    private String mailingAddress;

//    电话
    private String phone;

//    最新登录ip
    private String loginIp;

//    排序码
    private Integer sortCode;

//    角色码集合
    private List<String> roleCodeList;

//    用户密码hash值
    @JsonIgnore
    private String password;
}
