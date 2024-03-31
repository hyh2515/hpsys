package com.shou.hpsys.web.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shou.hpsys.common.pojo.CommonEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 角色实体
 * @author: Yaohui Hu
 * @date 2024/3/17 17:10
 */
@Getter
@Setter
@TableName("ROLE")
public class Role extends CommonEntity {

    //    id
    private String id;

    //    名称
    private String name;

    //    编码
    private String code;

    //    排序码
    private Integer sortCode;
}
