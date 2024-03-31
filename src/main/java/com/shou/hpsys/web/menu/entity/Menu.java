package com.shou.hpsys.web.menu.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shou.hpsys.common.pojo.CommonEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 菜单实体
 * @author: Yaohui Hu
 * @date 2024/3/22 16:27
 */
@Getter
@Setter
@TableName("MENU")
public class Menu extends CommonEntity {

//    id
    private String id;

//    父id
    private String parentId;

//    标题
    private String title;

//    编码
    private String code;

//    菜单类型
    private String menuType;

//    路径
    private String path;

//    组件
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String component;

//    图标
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String icon;

//    是否可见
    private String visible;

//    排序码
    private Integer sortCode;
}
