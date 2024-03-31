package com.shou.hpsys.web.menu.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 菜单添加参数
 * @author: Yaohui Hu
 * @date 2024/3/23 14:30
 */
@Getter
@Setter
public class MenuAddParam {
//    父id
    @NotBlank(message = "parentId不能为空")
    private String parentId;

//    标题
    @NotBlank(message = "title不能为空")
    private String title;

//    菜单类型
    @NotBlank(message = "menuType不能为空")
    private String menuType;

//    路径
    @NotBlank(message = "path不能为空")
    private String path;

//    排序码
    @NotNull(message = "sortCode不能为空")
    private Integer sortCode;

//    组件
    private String component;

//    图标
    private String icon;

//    是否可见
    private String visible;
}
