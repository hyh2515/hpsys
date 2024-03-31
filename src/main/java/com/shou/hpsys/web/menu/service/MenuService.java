package com.shou.hpsys.web.menu.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shou.hpsys.web.menu.entity.Menu;
import com.shou.hpsys.web.menu.param.MenuAddParam;
import com.shou.hpsys.web.menu.param.MenuIdParam;
import com.shou.hpsys.web.menu.param.MenuTreeParam;

import java.util.List;

/**
 * @description: 菜单Service接口
 * @author: Yaohui Hu
 * @date 2024/3/22 16:33
 */
public interface MenuService extends IService<Menu> {

    void add(MenuAddParam menuAddParam);

    /**
     * @description: 获取菜单树
     * @param: menuTreeParam
     * @return: List<Tree < String>>
     * @author Yaohui Hu
     * @date: 2024/3/22 16:37
     */
    List<Tree<String>> tree(MenuTreeParam menuTreeParam);

    /**
     * @description: 获取菜单详情
     * @param: menuIdParam
     * @return: com.shou.hpsys.web.menu.entity.Menu
     * @author Yaohui Hu
     * @date: 2024/3/22 16:44
     */
    Menu detail(MenuIdParam menuIdParam);

    /**
     * @description: 获取菜单详情
     * @param: id
     * @return: com.shou.hpsys.web.menu.entity.Menu
     * @author Yaohui Hu
     * @date: 2024/3/22 16:45
     */
    Menu queryEntity(String id);
}
