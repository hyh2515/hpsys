package com.shou.hpsys.web.login.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.shou.hpsys.common.pojo.CommonResult;
import com.shou.hpsys.web.login.param.LoginAccountPasswordLoginParam;
import com.shou.hpsys.web.login.pojo.SaBaseLoginUser;
import com.shou.hpsys.web.login.service.LoginService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description: 登录控制器
 * @author: Yaohui Hu
 * @date 2024/3/21 16:51
 */
@RestController
@Validated
public class LoginController {

    @Resource
    private LoginService loginService;
    
    /**
     * @description: 登录
     * @param: loginAccountPasswordLoginParam
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/21 16:58
     */
    @PostMapping("/auth/doLogin")
    public CommonResult<String> doLogin(@RequestBody @Valid LoginAccountPasswordLoginParam loginAccountPasswordLoginParam) {
        return CommonResult.data(loginService.doLogin(loginAccountPasswordLoginParam));
    }

    /**
     * @description: 退出登录
     * @param:
     * @return: com.shou.hpsys.common.pojo.CommonResult<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/21 16:59
     */
    @SaCheckLogin
    @GetMapping("/auth/doLogout")
    public CommonResult<String> doLogout() {
        StpUtil.logout();
        return CommonResult.ok();
    }

    /**
     * @description: 获取用户信息
     * @param: 
     * @return: com.shou.hpsys.common.pojo.CommonResult<com.shou.hpsys.web.login.pojo.SaBaseLoginUser>
     * @author Yaohui Hu
     * @date: 2024/3/21 17:00
     */
    @SaCheckLogin
    @GetMapping("/auth/getLoginUser")
    public CommonResult<SaBaseLoginUser> getLoginUser() {
        return CommonResult.data(loginService.getLoginUser());
    }
}
