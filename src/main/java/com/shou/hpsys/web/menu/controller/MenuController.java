package com.shou.hpsys.web.menu.controller;

import cn.hutool.core.lang.tree.Tree;
import com.shou.hpsys.common.pojo.CommonResult;
import com.shou.hpsys.web.menu.entity.Menu;
import com.shou.hpsys.web.menu.param.MenuIdParam;
import com.shou.hpsys.web.menu.param.MenuTreeParam;
import com.shou.hpsys.web.menu.service.MenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @description: 菜单控制器
 * @author: Yaohui Hu
 * @date 2024/3/22 17:14
 */
@RestController
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * @description: 获取菜单树
     * @param: menuTreeParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.util.List < cn.hutool.core.lang.tree.Tree < java.lang.String>>>
     * @author Yaohui Hu
     * @date: 2024/3/22 17:17
     */
    @GetMapping("/menu/tree")
    public CommonResult<List<Tree<String>>> tree(MenuTreeParam menuTreeParam) {
        return CommonResult.data(menuService.tree(menuTreeParam));
    }

    @GetMapping("/menu/detail")
    public CommonResult<Menu> detail(@Valid MenuIdParam menuIdParam) {
        return CommonResult.data(menuService.detail(menuIdParam));
    }
}
