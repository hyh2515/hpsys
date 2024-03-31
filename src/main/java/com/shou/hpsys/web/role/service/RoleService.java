package com.shou.hpsys.web.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shou.hpsys.web.role.entity.Role;
import com.shou.hpsys.web.role.param.RoleAddParam;
import com.shou.hpsys.web.role.param.RoleEditParam;
import com.shou.hpsys.web.role.param.RoleIdParam;

import java.util.List;

/**
 * @description: 角色Service
 * @author: Yaohui Hu
 * @date 2024/3/17 17:12
 */
public interface RoleService extends IService<Role> {

    /**
     * @description: 添加角色
     * @param: roleAddParam
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/21 19:16
     */
    void add(RoleAddParam roleAddParam);

    /**
     * @description: 编辑角色
     * @param: roleEditParam
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/21 19:20
     */
    void edit(RoleEditParam roleEditParam);

    /**
     * @description: 删除角色
     * @param: roleIdParamList
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/21 19:20
     */
    void delete(List<RoleIdParam> roleIdParamList);
    
    /**
     * @description: 获取角色详情
     * @param: sysRoleIdParam
     * @return: com.shou.hpsys.web.role.entity.Role
     * @author Yaohui Hu
     * @date: 2024/3/17 17:14
     */
    Role detail(RoleIdParam roleIdParam);

    /**
     * @description: 获取用户详情
     * @param: id
     * @return: com.shou.hpsys.web.role.entity.Role
     * @author Yaohui Hu
     * @date: 2024/3/17 17:15
     */
    Role queryEntity(String id);

    /**
     * @description: 获取角色下的用户
     * @param: roleIdParam
     * @return: java.util.List<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/21 19:21
     */
    List<String> ownUser(RoleIdParam roleIdParam);

    /**
     * @description: 根据编码获取用户
     * @param: code
     * @return: com.shou.hpsys.web.role.entity.Role
     * @author Yaohui Hu
     * @date: 2024/3/30 18:31
     */
    Role getRoleByCode(String code);
}
