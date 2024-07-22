package com.baimao.bmapicommon.service;

/**
 *
 */
public interface InnerUserInterfaceInfoService{

    /**
     * 接口调用次数 + 1
     * @param interfaceInfoId 接口 id
     * @param userId 用户 id
     */
    Boolean invokeCount(Long interfaceInfoId, Long userId);

    /**
     * 获取用户对接口的调用次数
     * @param interfaceInfoId 接口 id
     * @param userId 用户 id
     * @return
     */
    Integer getInvokeNum(Long interfaceInfoId,Long userId);
}
