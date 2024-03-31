package com.shou.hpsys.web.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.web.menu.entity.Menu;
import com.shou.hpsys.web.menu.enums.MenuTypeEnum;
import com.shou.hpsys.web.menu.mapper.MenuMapper;
import com.shou.hpsys.web.menu.param.MenuAddParam;
import com.shou.hpsys.web.menu.param.MenuIdParam;
import com.shou.hpsys.web.menu.param.MenuTreeParam;
import com.shou.hpsys.web.menu.service.MenuService;
import com.shou.hpsys.web.relation.service.RelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @description: 菜单Service接口实现类
 * @author: Yaohui Hu
 * @date 2024/3/22 16:34
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private RelationService relationService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(MenuAddParam menuAddParam) {
        checkParam(menuAddParam);
        Menu menu = BeanUtil.toBean(menuAddParam, Menu.class);
        boolean repeatTitle = this.count(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getParentId, menu.getParentId())
                .eq(Menu::getTitle, menu.getTitle())) > 0;
        if(repeatTitle) {
            throw new CommonException("存在重复菜单，名称为{}", menu.getTitle());
        }
        List<Menu> originDataList = this.list();
        if(!"0".equals(menuAddParam.getParentId())) {
            Menu parentMenu = this.getById(originDataList, menuAddParam.getParentId());
            if(ObjectUtil.isEmpty(parentMenu)) {
                throw new CommonException("上级菜单不存在，id值为：{}", menuAddParam.getParentId());
            }
        }
        this.save(menu);
    }

    public Menu getById(List<Menu> originDataList, String id) {
        int idx = CollStreamUtil.toList(originDataList, Menu::getId).indexOf(id);
        return idx == -1 ? null : originDataList.get(idx);
    }

    private void checkParam(MenuAddParam menuAddParam) {
        MenuTypeEnum.validate(menuAddParam.getMenuType());
        if(MenuTypeEnum.MENU.getValue().equals(menuAddParam.getMenuType())) {
            if(ObjectUtil.isEmpty(menuAddParam.getComponent())) {
                throw new CommonException("component不能为空");
            }
        } else {
            menuAddParam.setComponent(null);
        }
    }

    @Override
    public List<Tree<String>> tree(MenuTreeParam menuTreeParam) {
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Menu::getSortCode);
        if(ObjectUtil.isNotEmpty(menuTreeParam.getSearchKey())) {
            lambdaQueryWrapper.like(Menu::getTitle, menuTreeParam.getSearchKey());
        }
        List<Menu> menuList = this.list(lambdaQueryWrapper);

        // 填充上层的父级菜单
        List<TreeNode<String>> treeNodeList = menuList.stream().map(menu ->
                new TreeNode<>(menu.getId(), menu.getParentId(), menu.getTitle(), menu.getSortCode())
                        .setExtra(JSONUtil.parseObj(menu)))
                .collect(Collectors.toList());

        return TreeUtil.build(treeNodeList, "0");
    }

    private void fillParentSysMenuInfo(List<Menu> menuList) {
        if(CollUtil.isNotEmpty(menuList)) {
            List<Menu> parentRelationMenus = menuList.stream()
                    .filter(distinctByKey(Menu::getParentId))
                    .collect(Collectors.toList());

            List<String> parentIds = null;
            if(CollUtil.isNotEmpty(parentRelationMenus)) {
                parentIds = CollUtil.newArrayList();
                for(Menu parentRelationMenu : parentRelationMenus) {
                    if(!StrUtil.equals(parentRelationMenu.getParentId(), "0")) {
                        parentIds.add(parentRelationMenu.getParentId());
                    }
                }
                if(CollUtil.isNotEmpty(parentIds)) {
                    LambdaQueryWrapper<Menu> parentMeunuLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    parentMeunuLambdaQueryWrapper.in(Menu::getId, parentIds);
                    List<Menu> parentMenus = this.list(parentMeunuLambdaQueryWrapper);
                    if(CollUtil.isNotEmpty(parentMenus)) {
                        this.fillParentSysMenuInfo(parentMenus);
                        menuList.addAll(parentMenus);
                    }
                }
            }
        }
    }

    private static <T>Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public Menu detail(MenuIdParam menuIdParam) {
        return this.queryEntity(menuIdParam.getId());
    }

    @Override
    public Menu queryEntity(String id) {
        Menu menu = this.getById(id);
        if(ObjectUtil.isEmpty(menu)) {
            throw new CommonException("菜单不存在，id值为: {}", id);
        }
        return menu;
    }
}
