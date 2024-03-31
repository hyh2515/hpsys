package com.shou.hpsys.web.relation.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shou.hpsys.web.relation.entity.Relation;
import com.shou.hpsys.web.relation.mapper.RelationMapper;
import org.springframework.stereotype.Service;

import com.shou.hpsys.web.relation.service.RelationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 关系Service实现
 * @author: Yaohui Hu
 * @date 2024/3/21 15:35
 */
@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements RelationService {

    @Transactional(rollbackFor = Exception.class)
    public void saveRelation(String objectId, String targetId, String category, boolean clear) {
        // 是否需要先删除关系
        if(clear) {
            this.remove(new LambdaQueryWrapper<Relation>()
                    .eq(Relation::getObjectId, objectId)
                    .eq(Relation::getCategory, category));
        }
        Relation relation = new Relation();
        relation.setObjectId(objectId);
        relation.setTargetId(targetId);
        relation.setCategory(category);
        this.save(relation);
    }

    @Override
    public void saveRelationWithAppend(String objectId, String targetId, String category) {
        this.saveRelation(objectId, targetId, category, false);
    }

    @Override
    public void saveRelationWithClear(String objectId, String targetId, String category) {
        this.saveRelation(objectId, targetId, category, true);
    }

    @Override
    public List<Relation> getRelationListByObjectId(String objectId, String category) {
        LambdaQueryWrapper<Relation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Relation::getObjectId, objectId);
        if(ObjectUtil.isNotEmpty(category)) {
            lambdaQueryWrapper.eq(Relation::getCategory, category);
        }
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public List<Relation> getRelationListByTargetId(String targetId, String category) {
        LambdaQueryWrapper<Relation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Relation::getTargetId, targetId);
        if(ObjectUtil.isNotEmpty(category)) {
            lambdaQueryWrapper.eq(Relation::getCategory, category);
        }
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public List<String> getRelationTargetIdListByObjectId(String objectId, String category) {
        return this.getRelationListByObjectId(objectId, category).stream()
                .map(Relation::getTargetId).collect(Collectors.toList());
    }

    @Override
    public List<String> getRelationTargetIdListByObjectIdList(List<String> objectIdList, String category) {
        return this.getRelationListByObjectIdList(objectIdList, category)
                .stream()
                .map(Relation::getTargetId).collect(Collectors.toList());
    }

    @Override
    public List<String> getRelationObjectIdListByTargetId(String targetId, String category) {
        return this.getRelationListByTargetId(targetId, category).stream()
                .map(Relation::getObjectId).collect(Collectors.toList());
    }

    @Override
    public List<String> getRelationObjectIdListByTargetIdList(List<String> targetIdList, String category) {
        return getRelationListByTargetIdList(targetIdList, category)
                .stream()
                .map(Relation::getObjectId).collect(Collectors.toList());
    }

    @Override
    public List<Relation> getRelationListByObjectIdList(List<String> objectIdList, String category) {
        LambdaQueryWrapper<Relation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Relation::getObjectId, objectIdList);
        if(ObjectUtil.isNotEmpty(category)) {
            lambdaQueryWrapper.eq(Relation::getCategory, category);
        }
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public List<Relation> getRelationListByTargetIdList(List<String> targetIdList, String category) {
        LambdaQueryWrapper<Relation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Relation::getTargetId, targetIdList);
        if(ObjectUtil.isNotEmpty(category)) {
            lambdaQueryWrapper.eq(Relation::getCategory, category);
        }
        return this.list(lambdaQueryWrapper);
    }
}
