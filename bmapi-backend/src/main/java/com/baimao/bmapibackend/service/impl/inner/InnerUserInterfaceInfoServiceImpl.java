package com.baimao.bmapibackend.service.impl.inner;

import com.baimao.bmapibackend.service.UserInterfaceInfoService;
import com.baimao.bmapicommon.common.ErrorCode;
import com.baimao.bmapicommon.exception.BusinessException;
import com.baimao.bmapicommon.model.entity.UserInterfaceInfo;
import com.baimao.bmapicommon.service.InnerUserInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author baimao
 * @title InnerUserInterfaceInfoServiceImpl
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 接口调用次数 + 1
     * @param interfaceInfoId 接口 id
     * @param userId 用户 id
     */
    @Override
    public Boolean invokeCount(Long interfaceInfoId, Long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
    }

    /**
     * 获取用户对接口的调用次数
     * @param interfaceInfoId 接口 id
     * @param userId 用户 id
     * @return
     */
    @Override
    public Integer getInvokeNum(Long interfaceInfoId,Long userId) {
        if(interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId,interfaceInfoId);
        queryWrapper.eq(UserInterfaceInfo::getUserId,userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(queryWrapper);
        if(ObjectUtils.isEmpty(userInterfaceInfo)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return userInterfaceInfo.getLeftNum();
    }
}
