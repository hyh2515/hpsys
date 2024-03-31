package com.shou.hpsys.web.user.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 用户id集合参数
 * @author: Yaohui Hu
 * @date 2024/3/23 16:49
 */
@Getter
@Setter
public class UserIdListParam {

//    id集合
    @NotNull(message = "idList不能为空")
    private List<String> idList;
}
