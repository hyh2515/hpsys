package com.shou.hpsys.web.relation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shou.hpsys.web.relation.entity.Relation;

import java.util.List;

/**
 * @description: 关系Service接口
 * @author: Yaohui Hu
 * @date 2024/3/21 15:12
 */
public interface RelationService extends IService<Relation> {

    /**
     * @description: 追加关系
     * @param: objectId
    targetId
    category
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/23 13:53
     */
    void saveRelationWithAppend(String objectId, String targetId, String category);

    /**
     * @description: 清空原关系并保存关系
     * @param: objectId
    targetId
    category
     * @return: void
     * @author Yaohui Hu
     * @date: 2024/3/23 13:52
     */
    void saveRelationWithClear(String objectId, String targetId, String category);

    /**
     * @description: 根据对象id获取关系列表
     * @param: objectId
    category
     * @return: java.util.List<com.shou.hpsys.web.relation.entity.Relation>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:52
     */
    List<Relation> getRelationListByObjectId(String objectId, String category);

    /**
     * @description: 根据对象id集合获取关系列表
     * @param: objectIdList
    category
     * @return: java.util.List<com.shou.hpsys.web.relation.entity.Relation>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:54
     */
    List<Relation> getRelationListByObjectIdList(List<String> objectIdList, String category);


    /**
     * @description: 根据目标id获取关系列表
     * @param: targetId
    category
     * @return: java.util.List<com.shou.hpsys.web.relation.entity.Relation>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:54
     */
    List<Relation> getRelationListByTargetId(String targetId, String category);

    /**
     * @description: 根据目标id集合获取关系列表
     * @param: targetIdList
    category
     * @return: java.util.List<com.shou.hpsys.web.relation.entity.Relation>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:54
     */
    List<Relation> getRelationListByTargetIdList(List<String> targetIdList, String category);

    /**
     * @description: 根据对象id获取目标id列表
     * @param: objectId
    category
     * @return: java.util.List<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:55
     */
    List<String> getRelationTargetIdListByObjectId(String objectId, String category);

    /**
     * @description: 根据对象id获取目标id列表
     * @param: objectIdList
    category
     * @return: java.util.List<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:55
     */
    List<String> getRelationTargetIdListByObjectIdList(List<String> objectIdList, String category);

    /**
     * @description: 根据目标id获取对象id列表
     * @param: targetId
    category
     * @return: java.util.List<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:55
     */
    List<String> getRelationObjectIdListByTargetId(String targetId, String category);

    /**
     * @description: 根据目标id列表获取对象id列表
     * @param: targetIdList
    category
     * @return: java.util.List<java.lang.String>
     * @author Yaohui Hu
     * @date: 2024/3/23 13:56
     */
    List<String> getRelationObjectIdListByTargetIdList(List<String> targetIdList, String category);
}
