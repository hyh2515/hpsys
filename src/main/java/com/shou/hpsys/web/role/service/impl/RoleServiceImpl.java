package com.shou.hpsys.web.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.web.relation.entity.Relation;
import com.shou.hpsys.web.relation.enums.RelationCategoryEnum;
import com.shou.hpsys.web.relation.service.RelationService;
import com.shou.hpsys.web.role.entity.Role;
import com.shou.hpsys.web.role.enums.SysBuildRoleEnum;
import com.shou.hpsys.web.role.mapper.RoleMapper;
import com.shou.hpsys.web.role.param.RoleAddParam;
import com.shou.hpsys.web.role.param.RoleEditParam;
import com.shou.hpsys.web.role.param.RoleIdParam;
import com.shou.hpsys.web.role.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 角色Service实现
 * @author: Yaohui Hu
 * @date 2024/3/17 17:12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    RelationService relationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RoleAddParam roleAddParam) {
        Role role = BeanUtil.toBean(roleAddParam, Role.class);
        boolean repeatName = this.count(new LambdaQueryWrapper<Role>()
                .eq(Role::getName, role.getName())) > 0;
        if(repeatName) {
            throw new CommonException("存在重复角色，名称为: {}", role.getName());
        }
        role.setCode(RandomUtil.randomString(10));
        this.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(RoleEditParam roleEditParam) {
        Role role = this.queryEntity(roleEditParam.getId());
        boolean superRole = role.getCode().equals(SysBuildRoleEnum.BUILD_IN_ROLE_CODE.getValue());
        if(superRole) {
            throw new CommonException("不可编辑超管角色");
        }
        boolean repeatName = this.count(new LambdaQueryWrapper<Role>()
                .eq(Role::getName, role.getName())
                .ne(Role::getId, role.getId())) > 0;
        if(repeatName) {
            throw new CommonException("存在重复角色，名称为: {}", role.getName());
        }
        BeanUtil.copyProperties(roleEditParam, role);
        this.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<RoleIdParam> roleIdParamList) {
        List<String> roleIdList = CollStreamUtil.toList(roleIdParamList, RoleIdParam::getId);
        if(ObjectUtil.isNotEmpty(roleIdList)) {
            boolean containsSuperAdminRole = this.listByIds(roleIdList).stream().map(Role::getCode)
                    .collect(Collectors.toList()).contains(SysBuildRoleEnum.BUILD_IN_ROLE_CODE.getValue());
            if(containsSuperAdminRole) {
                throw new CommonException("不可删除系统内置超管角色");
            }
            // 级联删除角色与用户关系
            relationService.remove(new LambdaQueryWrapper<Relation>()
                    .in(Relation::getTargetId, roleIdList));
            // 执行删除
            this.removeByIds(roleIdList);
        }
    }

    @Override
    public Role detail(RoleIdParam roleIdParam) {
        return queryEntity(roleIdParam.getId());
    }

    @Override
    public Role queryEntity(String id) {
        Role role = this.getById(id);
        if(ObjectUtil.isEmpty(role)) {
            throw new CommonException("角色不存在，id值为：{}", id);
        }
        return role;
    }

    @Override
    public List<String> ownUser(RoleIdParam roleIdParam) {
        return relationService.getRelationObjectIdListByTargetId(roleIdParam.getId(), RelationCategoryEnum.USER_ROLE.getValue());
    }

    @Override
    public Role getRoleByCode(String code) {
        SysBuildRoleEnum.validate(code);
        return this.getOne(new LambdaQueryWrapper<Role>().eq(Role::getCode, code));
    }
}
