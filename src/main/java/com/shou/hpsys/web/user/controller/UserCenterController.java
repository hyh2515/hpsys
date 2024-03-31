package com.shou.hpsys.web.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.tree.Tree;
import com.shou.hpsys.common.annotation.CommonLog;
import com.shou.hpsys.common.pojo.CommonResult;
import com.shou.hpsys.web.role.entity.Role;
import com.shou.hpsys.web.user.param.*;
import com.shou.hpsys.web.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @description: 用户中心控制器
 * @author: Yaohui Hu
 * @date 2024/3/22 19:46
 */
@RestController
@Validated
public class UserCenterController {

    @Resource
    private UserService userService;

    /**
     * @description: 修改用户密码
     * @param: userUpdatePwdParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/22 20:11
     */
    @CommonLog("修改用户密码")
    @PostMapping("/userCenter/updatePassword")
    public CommonResult<String> updatePassword(@RequestBody @Valid UserUpdatePwdParam userUpdatePwdParam) {
        userService.updatePassword(userUpdatePwdParam);
        return CommonResult.ok();
    }

    /**
     * @description: 修改用户头像
     * @param: file
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/22 20:11
     */
    @CommonLog("修改用户头像")
    @PostMapping("/userCenter/updateAvatar")
    public CommonResult<String> updateAvatar(@RequestPart("file") MultipartFile file) {
        return CommonResult.data(userService.updateAvatar(file));
    }

    /**
     * @description: 获取登录用户菜单
     * @param:
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.util.List < cn.hutool.core.lang.tree.Tree < java.lang.String>>>
     * @author Yaohui Hu
     * @date: 2024/3/23 16:43
     */
    @GetMapping("/userCenter/loginMenu")
    public CommonResult<List<Tree<String>>> loginMenu() {
        UserIdParam userIdParam = new UserIdParam();
        userIdParam.setId(StpUtil.getLoginIdAsString());
        return CommonResult.data(userService.ownMenu(userIdParam));
    }

    /**
     * @description: 编辑个人信息
     * @param: userUpdateInfoParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/23 16:55
     */
    @CommonLog("编辑个人信息")
    @PostMapping("/userCenter/updateUserInfo")
    public CommonResult<String> updateUserInfo(@RequestBody @Valid UserUpdateInfoParam userUpdateInfoParam) {
        userService.updateUserInfo(userUpdateInfoParam);
        return CommonResult.ok();
    }

    /**
     * @description: 根据id集合获取角色集合
     * @param: userIdListParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.util.List < com.shou.hpsys.web.role.entity.Role>>
     * @author Yaohui Hu
     * @date: 2024/3/23 16:55
     */
    @PostMapping("/userCenter/getRoleListByIdList")
    public CommonResult<List<Role>> getRoleListByIdList(@RequestBody @Valid UserIdListParam userIdListParam) {
        return CommonResult.data(userService.getRoleListByIdList(userIdListParam));
    }
}
