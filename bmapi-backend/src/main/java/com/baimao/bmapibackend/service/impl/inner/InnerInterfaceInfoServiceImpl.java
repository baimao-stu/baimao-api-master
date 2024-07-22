package com.baimao.bmapibackend.service.impl.inner;

import com.baimao.bmapibackend.service.InterfaceInfoService;
import com.baimao.bmapicommon.common.ErrorCode;
import com.baimao.bmapicommon.exception.BusinessException;
import com.baimao.bmapicommon.model.entity.InterfaceInfo;
import com.baimao.bmapicommon.service.InnerInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author baimao
 * @title InnerInterfaceInfoServiceImpl
 */

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 判断用户请求是否存在
     * @param url 请求路径
     * @param method 请求方法
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if(StringUtils.isAnyBlank(url,method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterfaceInfo::getUrl, url);
        queryWrapper.eq(InterfaceInfo::getMethod, method);
        return interfaceInfoService.getOne(queryWrapper);
    }
}
