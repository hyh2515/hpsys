package com.shou.hpsys.web.role.controller;

import com.shou.hpsys.common.pojo.CommonResult;
import com.shou.hpsys.web.role.entity.Role;
import com.shou.hpsys.web.role.param.RoleIdParam;
import com.shou.hpsys.web.role.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description: TODO
 * @author: Yaohui Hu
 * @date 2024/3/17 17:19
 */
@RestController
@Validated
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping("/detail")
    public CommonResult<Role> detail(@Valid RoleIdParam roleIdParam) {
        return CommonResult.data(roleService.detail(roleIdParam));
    }
}
