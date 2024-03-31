package com.shou.hpsys.web.user.service;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shou.hpsys.web.role.entity.Role;
import com.shou.hpsys.web.user.entity.User;
import com.shou.hpsys.web.user.param.*;
import com.shou.hpsys.web.user.result.LoginUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: 用户Service接口
 * @author: Yaohui Hu
 * @date 2024/3/20 20:20
 */
public interface UserService extends IService<User> {

//    获取用户分页
    Page<User> page(UserPageParam userPageParam);

//    根据id获取用户信息，查不到则返回null
    LoginUser getUserById(String id);

//    根据账户获取用户信息，查不到则返回null
    LoginUser getUserByAccount(String account);

//    添加用户
    void add(UserAddParam userAddParam);

//    编辑用户
    void edit(UserEditParam userEditParam);

//    删除用户
    void delete(List<UserIdParam> userIdParamList);

//    获取用户详情
    User detail(UserIdParam userIdParam);

//    获取用户详情
    User queryEntity(String id);

//    修改用户密码
    void updatePassword(UserUpdatePwdParam userUpdatePwdParam);

//    修改用户头像返回base64
    String updateAvatar(MultipartFile file);

//    更新用户登录ip信息
    void updateUserLoginIp(String userId);

//    获取用户拥有角色
    List<String> ownRole(UserIdParam userIdParam);

//    获取角色集合
    List<JSONObject> getRoleList(String userId);

//    编辑个人信息
    void updateUserInfo(UserUpdateInfoParam userUpdateInfoParam);

//    获取用户拥有菜单
    List<Tree<String>> ownMenu(UserIdParam userIdParam);

//    根据id集合获取角色集合
    List<Role> getRoleListByIdList(UserIdListParam userIdListParam);
}
