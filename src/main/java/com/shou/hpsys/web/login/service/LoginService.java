package com.shou.hpsys.web.login.service;

import com.shou.hpsys.web.login.param.LoginAccountPasswordLoginParam;
import com.shou.hpsys.web.login.pojo.SaBaseLoginUser;

/**
 * @description: 登录Service接口
 * @author: Yaohui Hu
 * @date 2024/3/20 15:50
 */
public interface LoginService {

    /**
     * @description: 账号密码登录
     * @param: loginAccountPasswordLoginParam
    type
     * @return: java.lang.String
     * @author Yaohui Hu
     * @date: 2024/3/20 15:55
     */
    String doLogin(LoginAccountPasswordLoginParam loginAccountPasswordLoginParam);

    /**
     * @description: 获取用户信息
     * @param: 
     * @return: com.shou.hpsys.demos.web.User
     * @author Yaohui Hu
     * @date: 2024/3/20 16:09
     */
    SaBaseLoginUser getLoginUser();
}
