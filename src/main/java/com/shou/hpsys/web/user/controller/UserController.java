package com.shou.hpsys.web.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shou.hpsys.common.annotation.CommonLog;
import com.shou.hpsys.common.pojo.CommonResult;
import com.shou.hpsys.common.pojo.CommonValidList;
import com.shou.hpsys.web.user.entity.User;
import com.shou.hpsys.web.user.param.UserAddParam;
import com.shou.hpsys.web.user.param.UserEditParam;
import com.shou.hpsys.web.user.param.UserIdParam;
import com.shou.hpsys.web.user.param.UserPageParam;
import com.shou.hpsys.web.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @description: 用户个人控制器
 * @author: Yaohui Hu
 * @date 2024/3/22 19:46
 */
@RestController
@Validated
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/user/page")
    public CommonResult<Page<User>> page(UserPageParam userPageParam) {
        return CommonResult.data(userService.page(userPageParam));
    }

    /**
     * @description: 添加用户
     * @param: userAddParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/22 19:51
     */
    @CommonLog("添加用户")
    @PostMapping("/user/add")
    public CommonResult<String> add(@RequestBody @Valid UserAddParam userAddParam) {
        userService.add(userAddParam);
        return CommonResult.ok();
    }

    @CommonLog("编辑用户")
    @PostMapping("/user/edit")
    public CommonResult<String> edit(@RequestBody @Valid UserEditParam userEditParam) {
        userService.edit(userEditParam);
        return CommonResult.ok();
    }

    /**
     * @description: 删除用户
     * @param: userIdParamList
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/22 20:02
     */
    @CommonLog("删除用户")
    @PostMapping("/user/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") CommonValidList<UserIdParam> userIdParamList) {
        userService.delete(userIdParamList);
        return CommonResult.ok();
    }

    /**
     * @description: 获取用户详情
     * @param: userIdParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<com.shou.hpsys.web.user.entity.User>
     * @author Yaohui Hu
     * @date: 2024/3/22 20:03
     */
    @GetMapping("/user/detail")
    public CommonResult<User> detail(@Valid UserIdParam userIdParam) {
        return CommonResult.data(userService.detail(userIdParam));
    }

    /**
     * @description: 用户拥有角色
     * @param: userIdParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.util.List < java.lang.String>>
     * @author Yaohui Hu
     * @date: 2024/3/23 16:56
     */
    @GetMapping("/user/ownRole")
    public CommonResult<List<String>> ownRole(@Valid UserIdParam userIdParam) {
        return CommonResult.data(userService.ownRole(userIdParam));
    }
}
