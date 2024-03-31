package com.shou.hpsys.common.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @description: 通用实体
 * @author: Yaohui Hu
 * @date 2024/3/17 16:15
 */
@Getter
@Setter
public class CommonEntity {
    //    删除标志
    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    //    创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //    创建人
    @TableField(fill = FieldFill.INSERT)
    @Trans(type = TransType.RPC, targetClassName = "com.shou.sys.user.entity", fields = "name", alias = "createUser", ref = "createUserName")
    private String createUser;

    //    创建人名称
    @TableField(exist = false)
    private String creteUserName;

    //    更新时间
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    //    更新人
    @TableField(fill = FieldFill.UPDATE)
    @Trans(type = TransType.RPC, targetClassName = "com.shou.sys.user.entity.SysUser", fields = "name", alias = "updateUser", ref = "updateUserName")
    private String updateUser;

    //    更新人名称
    @TableField(exist = false)
    private String updateUserName;
}
