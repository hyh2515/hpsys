package com.shou.hpsys.web.login.util;

import cn.dev33.satoken.stp.StpUtil;
import com.shou.hpsys.web.login.pojo.SaBaseLoginUser;

/**
 * @description: 获取登录信息
 * @author: Yaohui Hu
 * @date 2024/3/20 17:10
 */
public class StpLoginUserUtil {

    /**
     * @description: 获取当前登录用户
     * @param:
     * @return: com.shou.hpsys.web.login.pojo.SaBaseLoginUser
     * @author Yaohui Hu
     * @date: 2024/3/20 17:12
     */
    public static SaBaseLoginUser getLoginUser() {
        return (SaBaseLoginUser) StpUtil.getTokenSession().get("loginUser");
    }
}
