package com.shou.hpsys.web.user.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shou.hpsys.common.handler.CommonSm4CbcTypeHandler;
import com.shou.hpsys.common.pojo.CommonEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 用户实体
 * @author: Yaohui Hu
 * @date 2024/3/20 20:10
 */
@Getter
@Setter
@TableName(value = "USER", autoResultMap = true)
public class User extends CommonEntity {

//    id
    @TableId
    private String id;

//    头像
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String avatar;

//    账号
    private String account;

//    密码
    @JsonIgnore
    private String password;

//    姓名
    private String name;

//    年龄
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String age;

//    通信地址
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String mailingAddress;

//    电话
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, typeHandler = CommonSm4CbcTypeHandler.class)
    private String phone;

//    最新登录ip
    private String loginIp;

//    排序码
    private String sortCode;
}
