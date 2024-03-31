package com.shou.hpsys.web.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shou.hpsys.common.enums.CommonSortOrderEnum;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.common.page.CommonPageRequest;
import com.shou.hpsys.common.utils.CommonAvatarUtil;
import com.shou.hpsys.common.utils.CommonCryptogramUtil;
import com.shou.hpsys.common.utils.CommonIpAddressUtil;
import com.shou.hpsys.common.utils.CommonServletUtil;
import com.shou.hpsys.web.login.util.StpLoginUserUtil;
import com.shou.hpsys.web.menu.entity.Menu;
import com.shou.hpsys.web.menu.enums.MenuTypeEnum;
import com.shou.hpsys.web.menu.service.MenuService;
import com.shou.hpsys.web.relation.enums.RelationCategoryEnum;
import com.shou.hpsys.web.relation.service.RelationService;
import com.shou.hpsys.web.role.entity.Role;
import com.shou.hpsys.web.role.enums.SysBuildRoleEnum;
import com.shou.hpsys.web.role.service.RoleService;
import com.shou.hpsys.web.user.entity.User;
import com.shou.hpsys.web.user.enums.SysBuildUserEnum;
import com.shou.hpsys.web.user.mapper.UserMapper;
import com.shou.hpsys.web.user.param.*;
import com.shou.hpsys.web.user.result.LoginUser;
import com.shou.hpsys.web.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description: 用户Service接口实现类
 * @author: Yaohui Hu
 * @date 2024/3/20 20:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private RelationService relationService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Override
    public Page<User> page(UserPageParam userPageParam) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(userPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q -> q.like(User::getAccount, userPageParam.getSearchKey()).or()
                    .like(User::getName, userPageParam.getSearchKey()));
        }
        if (ObjectUtil.isAllNotEmpty(userPageParam.getSortField(), userPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(userPageParam.getSortOrder());
            queryWrapper.orderBy(true, userPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(userPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(User::getSortCode);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public LoginUser getUserById(String id) {
        User user = this.getById(id);
        if(ObjectUtil.isNotEmpty(user)) {
            return BeanUtil.copyProperties(user, LoginUser.class);
        }
        return null;
    }

    @Override
    public LoginUser getUserByAccount(String account) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, account));
        if(ObjectUtil.isNotEmpty(user)) {
            return BeanUtil.copyProperties(user, LoginUser.class);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(UserAddParam userAddParam) {
        checkParam(userAddParam);
        User user = BeanUtil.toBean(userAddParam, User.class);
        if(ObjectUtil.isEmpty(user.getAvatar())) {
            // 设置默认头像
            user.setAvatar(CommonAvatarUtil.generateImg(user.getName()));
        }
        // 设置默认密码
        user.setPassword(CommonCryptogramUtil.doHashValue("123456"));
        // 将用户关系指向为普通用户
        String userId = this.getUserByAccount(userAddParam.getAccount()).getId();
        String userRoleId = roleService.getRoleByCode(SysBuildRoleEnum.BUILD_INT_ROLE_CODE_USER.getValue()).getId();
        relationService.saveRelationWithAppend(userId, userRoleId, RelationCategoryEnum.USER_ROLE.getValue());
        this.save(user);
    }

    private void checkParam(UserAddParam userAddParam) {
        if(this.count(new LambdaQueryWrapper<User>()
                .eq(User::getAccount, userAddParam.getAccount())) > 0) {
            throw new CommonException("存在重复的账号，账号为：{}", userAddParam.getAccount());
        }
        if(ObjectUtil.isNotEmpty(userAddParam.getPhone())) {
            if(!PhoneUtil.isMobile(userAddParam.getPhone())) {
                throw new CommonException("手机号码：{}格式错误", userAddParam.getPhone());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(UserEditParam userEditParam) {
        User user = this.queryEntity(userEditParam.getId());
        checkParam(userEditParam);
        boolean updateAdminAccount = user.getAccount().equals(SysBuildUserEnum.BUILD_IN_USER_ACCOUNT.getValue()) &&
                !userEditParam.getAccount().equals(SysBuildUserEnum.BUILD_IN_USER_ACCOUNT.getValue());
        if(updateAdminAccount) {
            throw new CommonException("不可修改系统内置超管账号");
        }
        BeanUtil.copyProperties(userEditParam, user);
        this.updateById(user);
    }

    private void checkParam(UserEditParam userEditParam) {
        if(this.count(new LambdaQueryWrapper<User>()
                .eq(User::getAccount, userEditParam.getAccount())
                .ne(User::getId, userEditParam.getId())) > 0) {
            throw new CommonException("存在重复账号，账号为: {}", userEditParam.getAccount());
        }
        if(ObjectUtil.isNotEmpty(userEditParam.getPhone())) {
            if (!PhoneUtil.isMobile(userEditParam.getPhone())) {
                throw new CommonException("手机号码：{}格式错误", userEditParam.getPhone());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<UserIdParam> userIdParamList) {
        List<String> userIdList = CollStreamUtil.toList(userIdParamList, UserIdParam::getId);
        if(ObjectUtil.isNotEmpty(userIdList)) {
            boolean containsAdminAccount = this.listByIds(userIdList).stream().map(User::getAccount)
                    .collect(Collectors.toSet()).contains(SysBuildUserEnum.BUILD_IN_USER_ACCOUNT.getValue());
            if(containsAdminAccount) {
                throw new CommonException("不可删除系统内置超管用户");
            }
            this.removeByIds(userIdList);
        }
    }

    @Override
    public User detail(UserIdParam userIdParam) {
        return this.queryEntity(userIdParam.getId());
    }

    @Override
    public User queryEntity(String id) {
        User user = this.getById(id);
        if(ObjectUtil.isEmpty(user)) {
            throw new CommonException("用户不存在，id值为: {}", id);
        }
        return user;
    }

    @Override
    public void updatePassword(UserUpdatePwdParam userUpdatePwdParam) {
        User user = this.queryEntity(StpUtil.getLoginIdAsString());
        String password = userUpdatePwdParam.getPassword();
        String newPassword = userUpdatePwdParam.getNewPassword();
        if(!CommonCryptogramUtil.doHashValue(password).equals(user.getPassword())) {
            throw new CommonException("原密码错误");
        }
        this.update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, user.getId())
                .set(User::getPassword, CommonCryptogramUtil.doHashValue(newPassword)));
    }

    @Override
    public String updateAvatar(MultipartFile file) {
        User user = this.queryEntity(StpUtil.getLoginIdAsString());
        try {
            String suffix = Objects.requireNonNull(FileUtil.getSuffix(file.getOriginalFilename())).toLowerCase();
            BufferedImage image = ImgUtil.toImage(file.getBytes());
            String base64;
            if(image.getWidth() <= 200 && image.getHeight() <= 200) {
                base64 = ImgUtil.toBase64DataUri(image, suffix);
            } else {
                base64 = ImgUtil.toBase64DataUri(ImgUtil.scale(image, 200, 200, null), suffix);
            }
            this.update(new LambdaUpdateWrapper<User>()
                    .eq(User::getId, user.getId())
                    .set(User::getAvatar, base64));
            return base64;
        } catch (IOException e) {
            log.error(">>> 头像修改错误: ", e);
            throw new CommonException("头像修改错误，用户id值为{}", user.getId());
        }
    }

    @Override
    public void updateUserLoginIp(String userId) {
        User user = this.queryEntity(userId);
        String ip = CommonIpAddressUtil.getIp(CommonServletUtil.getRequest());
        user.setLoginIp(ip);
        this.updateById(user);
    }

    @Override
    public List<String> ownRole(UserIdParam userIdParam) {
        return relationService.getRelationTargetIdListByObjectId(userIdParam.getId(), RelationCategoryEnum.USER_ROLE.getValue());
    }

    @Override
    public List<JSONObject> getRoleList(String userId) {
        List<String> roleIdList = relationService.getRelationTargetIdListByObjectId(userId, RelationCategoryEnum.USER_ROLE.getValue());
        if(ObjectUtil.isNotEmpty(roleIdList)) {
            return roleService.listByIds(roleIdList).stream().map(JSONUtil::parseObj)
                    .collect(Collectors.toList());
        }
        return CollectionUtil.newArrayList();
    }

    @Override
    public void updateUserInfo(UserUpdateInfoParam userUpdateInfoParam) {
        String id = StpLoginUserUtil.getLoginUser().getId();
        if(!StrUtil.equals(id, userUpdateInfoParam.getId())) {
            throw new CommonException("禁止修改他人信息");
        }

        User user = this.queryEntity(userUpdateInfoParam.getId());

        if(ObjectUtil.isNotEmpty(userUpdateInfoParam.getPhone())) {
            if (!PhoneUtil.isMobile(userUpdateInfoParam.getPhone())) {
                throw new CommonException("手机号码：{}格式错误", userUpdateInfoParam.getPhone());
            }
            LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<User>().eq(User::getId, user.getId());
            if(ObjectUtil.isNotEmpty(userUpdateInfoParam.getName())) {
                lambdaUpdateWrapper.set(User::getName, userUpdateInfoParam.getName());
            }
            if(ObjectUtil.isNotEmpty(userUpdateInfoParam.getAge())) {
                lambdaUpdateWrapper.set(User::getAge, userUpdateInfoParam.getAge());
            }
            if(ObjectUtil.isNotEmpty(userUpdateInfoParam.getMailingAddress())) {
                lambdaUpdateWrapper.set(User::getMailingAddress, userUpdateInfoParam.getMailingAddress());
            }
            if(ObjectUtil.isNotEmpty(userUpdateInfoParam.getPhone())) {
                lambdaUpdateWrapper.set(User::getPhone, userUpdateInfoParam.getPhone());
            }
            // 更新指定字段
            this.update(lambdaUpdateWrapper);
        }
    }

    @Override
    public List<Tree<String>> ownMenu(UserIdParam userIdParam) {
        // 获取角色id列表
        List<String> roleIdList = this.ownRole(userIdParam);

        // 获取菜单id列表
        List<String> menuIdList = new ArrayList<>();

        if(ObjectUtil.isNotEmpty(roleIdList)) {
            menuIdList.addAll(relationService.getRelationTargetIdListByObjectIdList(roleIdList, RelationCategoryEnum.ROLE_MENU.getValue()));
        }

        // 获取所有的菜单列表，并按排序码排序
        List<Menu> allMenuAndSpaList = menuService.list(new LambdaQueryWrapper<Menu>()
                .orderByAsc(CollectionUtil.newArrayList(Menu::getSortCode)));
        // 全部以菜单承载
        List<Menu> allMenuList = CollectionUtil.newArrayList();
        // 根据类型抽取
        allMenuList.addAll(allMenuAndSpaList);
        // 定义结果
        List<Menu> resultList = CollectionUtil.newArrayList();

        // 获取拥有的菜单列表
        List<Menu> menuList = allMenuList.stream().filter(menu ->
            menuIdList.contains(menu.getId())).collect(Collectors.toList());

        // 对获取到的角色对应的菜单列表进行处理，获取父列表
        menuList.forEach(menu -> execRecursionParent(allMenuList, menu.getId(), resultList));

        // 将拥有的菜单列表添加
        resultList.addAll(menuList);

        // 获取第第一个菜单
        Optional<Menu> menus = menuList.stream()
                .findFirst()
                .filter(menu -> !menu.getMenuType().equals(MenuTypeEnum.CATALOG.getValue()));

        // 构造meta
        List<JSONObject> resultJsonObjectList = resultList.stream().map(menu -> {
            JSONObject menuJsonObject = JSONUtil.parseObj(menu);
            JSONObject metaJsonObject = JSONUtil.createObj();
            metaJsonObject.set("icon", menu.getIcon());
            metaJsonObject.set("title", menu.getTitle());
            metaJsonObject.set("type", menu.getMenuType().toLowerCase());
            if (!menu.getMenuType().equals(MenuTypeEnum.CATALOG.getValue())) {
                metaJsonObject.set("type", menu.getMenuType().toLowerCase());
                // 如果设置了不可见，那么设置为false，为了兼容已有，所以只是false的为不显示
                if (ObjectUtil.isNotEmpty(menu.getVisible()) && menu.getVisible().equals("FALSE")) {
                    metaJsonObject.set("hidden", true);
                }
            }
            if (menu.getId().equals(menus.orElse(null) != null ? menus.orElse(null).getId() : null)) {
                // 如果是首页，则设置affix
                metaJsonObject.set("affix", true);
            }
            menuJsonObject.set("meta", metaJsonObject);
            return menuJsonObject;
        }).collect(Collectors.toList());

        // 执行构造树
        List<TreeNode<String>> treeNodeList = resultJsonObjectList.stream().map(jsonObject ->
                        new TreeNode<>(jsonObject.getStr("id"), jsonObject.getStr("parentId"),
                                jsonObject.getStr("title"), jsonObject.getInt("sortCode")).setExtra(JSONUtil.parseObj(jsonObject)))
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, "0");
    }

    @Override
    public List<Role> getRoleListByIdList(UserIdListParam userIdListParam) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询部分字段
        lambdaQueryWrapper.select(Role::getId, Role::getName, Role::getSortCode)
                .in(Role::getId, userIdListParam.getIdList()).orderByAsc(Role::getSortCode);
        return roleService.list(lambdaQueryWrapper);
    }

    private void execRecursionParent(List<Menu> originDataList, String id, List<Menu> resultList) {
        originDataList.forEach(item -> {
            if(item.getId().equals(id)) {
                int idx = CollStreamUtil.toList(originDataList, Menu::getId).indexOf(id);
                Menu parent = idx == -1 ? null : originDataList.get(idx);
                if(ObjectUtil.isNotEmpty(parent)) {
                    if(!CollectionUtil.contains(resultList, parent)) {
                        resultList.add(parent);
                    }
                }
                execRecursionParent(originDataList, item.getParentId(), resultList);
            }
        });
    }
}
