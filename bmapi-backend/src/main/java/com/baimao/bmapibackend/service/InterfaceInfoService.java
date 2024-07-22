package com.baimao.bmapibackend.service;

import com.baimao.bmapibackend.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.baimao.bmapicommon.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验参数
     * @param interfaceInfo 接口信息
     * @param add 是否为增加，false为修改
     */
    void validPost(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

}
