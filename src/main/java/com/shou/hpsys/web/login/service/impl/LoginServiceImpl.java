package com.shou.hpsys.web.login.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.common.utils.CommonCryptogramUtil;
import com.shou.hpsys.web.login.enums.LoginExceptionEnum;
import com.shou.hpsys.web.login.param.LoginAccountPasswordLoginParam;
import com.shou.hpsys.web.login.pojo.SaBaseLoginUser;
import com.shou.hpsys.web.login.service.LoginService;
import com.shou.hpsys.web.login.util.StpLoginUserUtil;
import com.shou.hpsys.web.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 角色Service接口实现类
 * @author: Yaohui Hu
 * @date 2024/3/20 16:10
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserService userService;

    @Override
    public String doLogin(LoginAccountPasswordLoginParam loginAccountPasswordLoginParam) {
        // 获取账号
        String account = loginAccountPasswordLoginParam.getAccount();
        // 获取密码
        String password = loginAccountPasswordLoginParam.getPassword();
        // SM2解密并获得前端传来的密码hash值
        String passwordHash;
        try {
            // 解密，并做hash值
            passwordHash = CommonCryptogramUtil.doHashValue(CommonCryptogramUtil.doSm2Decrypt(password));
        } catch (Exception e) {
            throw new CommonException(LoginExceptionEnum.PWD_DECRYPT_ERROR.getValue());
        }
        // 根据账号获取用户信息
        SaBaseLoginUser saBaseLoginUser = userService.getUserByAccount(account);
        if(ObjectUtil.isEmpty(saBaseLoginUser)) {
            throw new CommonException(LoginExceptionEnum.ACCOUNT_ERROR.getValue());
        }
        if(!saBaseLoginUser.getPassword().equals(passwordHash)) {
            throw new CommonException(LoginExceptionEnum.PWD_ERROR.getValue());
        }
        return execLogin(saBaseLoginUser);
    }

    private String execLogin(SaBaseLoginUser saBaseLoginUser) {
        // 执行登录
        StpUtil.login(saBaseLoginUser.getId(), new SaLoginModel().setExtra("name", saBaseLoginUser.getName()));
        // 角色集合
        List<JSONObject> roleList = userService.getRoleList(saBaseLoginUser.getId());
        // 角色码集合
        List<String> roleCodeList = roleList.stream()
                .map(jsonObject -> jsonObject.getStr("code"))
                .collect(Collectors.toList());
        // 获取角色码
        saBaseLoginUser.setRoleCodeList(roleCodeList);
        // 缓存用户信息，此处使用TokenSession为了指定时间内无操作则自动下线
        StpUtil.getTokenSession().set("loginUser", saBaseLoginUser);
        // 返回token
        return StpUtil.getTokenInfo().tokenValue;
    }

    @Override
    public SaBaseLoginUser getLoginUser() {
        SaBaseLoginUser saBaseLoginUser = StpLoginUserUtil.getLoginUser();
        saBaseLoginUser.setPassword(null);
        return saBaseLoginUser;
    }
}
