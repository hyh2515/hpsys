package com.shou.hpsys.web.user.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户查询参数
 * @author: Yaohui Hu
 * @date 2024/3/24 21:11
 */
@Getter
@Setter
public class UserPageParam {

//    当前页
    private Integer current;

//    每页条数
    private Integer size;

//    排序字段
    private String sortField;

//    排序方式
    private String sortOrder;

//    账号、姓名关键词
    private String searchKey;
}
