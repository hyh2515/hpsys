package com.shou.hpsys.web.predict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shou.hpsys.web.predict.entity.Community;
import com.shou.hpsys.web.predict.param.CommunityAddParam;

import java.util.List;

/**
 * @description: 小区实体Service
 * @author: Yaohui Hu
 * @date 2024/3/28 12:05
 */
public interface CommunityService extends IService<Community> {

    /**
     * @description: 增加小区信息
     * @param: communityAddParam
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/28 12:07
     */
    public void add(CommunityAddParam communityAddParam);

    public Community getCommunityByName(String name);

    public List<Community> getAllCommunityList();
}
