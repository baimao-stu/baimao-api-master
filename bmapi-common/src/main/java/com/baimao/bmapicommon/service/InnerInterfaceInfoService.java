package com.baimao.bmapicommon.service;


import com.baimao.bmapicommon.model.entity.InterfaceInfo;

/**
 *
 */
public interface InnerInterfaceInfoService{

    /**
     * 判断用户请求是否存在
     * @param path 请求路径
     * @param method 请求方法
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
