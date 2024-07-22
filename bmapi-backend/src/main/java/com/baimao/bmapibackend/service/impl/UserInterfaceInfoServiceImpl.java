package com.baimao.bmapibackend.service.impl;

import com.baimao.bmapibackend.mapper.UserInterfaceInfoMapper;
import com.baimao.bmapibackend.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.baimao.bmapibackend.service.UserInterfaceInfoService;
import com.baimao.bmapicommon.common.ErrorCode;
import com.baimao.bmapicommon.constant.CommonConstant;
import com.baimao.bmapicommon.exception.BusinessException;
import com.baimao.bmapicommon.exception.ThrowUtils;
import com.baimao.bmapicommon.model.entity.UserInterfaceInfo;
import com.baimao.bmapicommon.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validPost(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();

        if (userId <= 0 || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户或接口不存在");
        }
        if (add) {
            ThrowUtils.throwIf(totalNum <= 0 || leftNum <= 0, ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userinterfaceInfoQueryRequest) {
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (userinterfaceInfoQueryRequest == null) {
            return queryWrapper;
        }

        Long id = userinterfaceInfoQueryRequest.getId();
        Long userId = userinterfaceInfoQueryRequest.getUserId();
        Long interfaceInfoId = userinterfaceInfoQueryRequest.getInterfaceInfoId();
        Integer status = userinterfaceInfoQueryRequest.getStatus();
        String sortField = userinterfaceInfoQueryRequest.getSortField();
        String sortOrder = userinterfaceInfoQueryRequest.getSortOrder();

        //todo 可根据总数和剩余次数构造查询
        Integer totalNum = userinterfaceInfoQueryRequest.getTotalNum();
        Integer leftNum = userinterfaceInfoQueryRequest.getLeftNum();

        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(interfaceInfoId), "interfaceInfoId", interfaceInfoId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;
    }

    @Override
    public Boolean invokeCount(Long interfaceInfoId, Long userId) {
        if(interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getInterfaceInfoId,interfaceInfoId);
        updateWrapper.eq(UserInterfaceInfo::getUserId,userId);
        updateWrapper.gt(UserInterfaceInfo::getLeftNum,0);
        updateWrapper.setSql("leftNum = leftNum - 1,totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }
}




