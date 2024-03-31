package com.shou.hpsys.web.relation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 关系实体
 * @author: Yaohui Hu
 * @date 2024/3/21 15:09
 */
@Getter
@Setter
@TableName("RELATION")
public class Relation {

//    id
    private String id;

//    对象id
    private String objectId;

//    目标id
    private String targetId;

//    类别
    private String category;
}
