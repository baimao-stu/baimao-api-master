package com.baimao.bmapibackend.service.impl.inner;

import com.baimao.bmapibackend.service.UserService;
import com.baimao.bmapicommon.common.ErrorCode;
import com.baimao.bmapicommon.exception.BusinessException;
import com.baimao.bmapicommon.model.entity.User;
import com.baimao.bmapicommon.service.InnerUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author baimao
 * @title InnerUserServiceImpl
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserService userService;

    /**
     * 根据accessKey从数据库查询用户信息（主要获取对应的accessSecret）
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {
        if(StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccessKey, accessKey);
        return userService.getOne(queryWrapper);
    }
}
