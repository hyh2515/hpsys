package com.shou.hpsys.web.predict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shou.hpsys.common.pojo.CommonEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 小区实体
 * @author: Yaohui Hu
 * @date 2024/3/28 11:57
 */
@Getter
@Setter
@TableName("COMMUNITY")
public class Community extends CommonEntity {

//    id
    private String id;

//    name
    private String name;

//    X坐标
    private double CoordX;

//    Y坐标
    private double CoordY;
}
