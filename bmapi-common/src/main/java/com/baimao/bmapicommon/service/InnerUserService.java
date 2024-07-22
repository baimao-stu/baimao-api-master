package com.baimao.bmapicommon.service;

import com.baimao.bmapicommon.model.entity.User;

/**
 * 用户服务
 *
 */
public interface InnerUserService{

    /**
     * 根据accessKey从数据库查询用户信息（主要获取对应的accessSecret）
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
