package com.baimao.bmapibackend.service;

import com.baimao.bmapibackend.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.baimao.bmapicommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 校验参数
     * @param userInterfaceInfo 用户接口信息
     * @param add 是否为增加，false为修改
     */
    void validPost(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param userInterfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

    /**
     * todo 新增免费的接口时，给所有用户一些调用次数
     * 接口调用次数 + 1
     * @param interfaceInfoId 接口 id
     * @param userId 用户 id
     */
    Boolean invokeCount(Long interfaceInfoId, Long userId);
}
