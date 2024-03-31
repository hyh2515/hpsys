package com.shou.hpsys.web.predict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shou.hpsys.common.exception.CommonException;
import com.shou.hpsys.web.predict.entity.Community;
import com.shou.hpsys.web.predict.mapper.CommunityMapper;
import com.shou.hpsys.web.predict.param.CommunityAddParam;
import com.shou.hpsys.web.predict.service.CommunityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 小区实体Service实现
 * @author: Yaohui Hu
 * @date 2024/3/28 12:15
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Override
    public void add(CommunityAddParam communityAddParam) {
        checkParam(communityAddParam);
        Community community = BeanUtil.toBean(communityAddParam, Community.class);
        this.save(community);
    }

    private void checkParam(CommunityAddParam communityAddParam) {
        if(ObjectUtil.isEmpty(communityAddParam)) {
            throw new CommonException("增加小区参数为空");
        }
        if(this.count(new LambdaQueryWrapper<Community>()
                .eq(Community::getName, communityAddParam.getName())) > 0) {
            throw new CommonException("存在重复小区名：{}", communityAddParam.getName());
        }
    }

    @Override
    public Community getCommunityByName(String name) {
        Community community = this.getOne(new LambdaQueryWrapper<Community>().eq(Community::getName, name));
        if(ObjectUtil.isNotEmpty(community)) {
            return community;
        }
        return null;
    }

    @Override
    public List<Community> getAllCommunityList() {
        return this.list();
    }
}
