package com.baimao.bmapibackend.controller;

import com.baimao.bmapibackend.annotation.AuthCheck;
import com.baimao.bmapibackend.model.dto.interfaceinfo.*;
import com.baimao.bmapibackend.service.InterfaceInfoService;
import com.baimao.bmapibackend.service.UserInterfaceInfoService;
import com.baimao.bmapibackend.service.UserService;
import com.baimao.bmapicommon.common.BaseResponse;
import com.baimao.bmapicommon.common.DeleteRequest;
import com.baimao.bmapicommon.common.ErrorCode;
import com.baimao.bmapicommon.common.ResultUtils;
import com.baimao.bmapicommon.constant.UserConstant;
import com.baimao.bmapicommon.exception.BusinessException;
import com.baimao.bmapicommon.exception.ThrowUtils;
import com.baimao.bmapicommon.model.entity.InterfaceInfo;
import com.baimao.bmapicommon.model.entity.User;
import com.baimao.bmapicommon.model.entity.UserInterfaceInfo;
import com.baimao.bmapicommon.model.enums.InterfaceInfoStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * api 接口
 *
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

//    @Resource
//    private ApiClient apiClient;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);

        interfaceInfoService.validPost(interfaceInfo, true);
        //获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newPostId = interfaceInfo.getId();
        return ResultUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validPost(interfaceInfo, false);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @PostMapping("/list/page")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * 上线接口（仅管理员）
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        /** 1. 判断接口是否存在 */
        long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        /** 2. 判断接口是否可调用（调用一遍） */
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String accessSecret = loginUser.getAccessSecret();
        // todo 换成该接口本身的信息
        Object result = invokeInterfaceInfo(interfaceInfo.getSdkClient(),
                interfaceInfo.getMethod(), interfaceInfo.getRequestParams(),
                accessKey, accessSecret);
        if(result == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        /** 3. 修改接口状态 */
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean res = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(res);
    }

    /**
     * 下线接口（仅管理员）
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        /** 1. 判断接口是否存在 */
        long id = idRequest.getId();
        InterfaceInfo InterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(InterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        /** 2. 修改接口状态 */
        InterfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean res = interfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(res);
    }

    /**
     * （用户）调用接口
     *
     * @param interfaceInfoInvokeRequest
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request) {
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        /** 1. 判断接口是否存在 */
        long id = interfaceInfoInvokeRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        if(!InterfaceInfoStatusEnum.ONLINE.getValue().equals(interfaceInfo.getStatus())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口未开放");
        }
        /** 2. 调用接口（带上用户注册时颁发的 ak、sk） */
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String accessSecret = loginUser.getAccessSecret();

        /** 3.用户调用次数校验 */
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId,interfaceInfo.getId());
        queryWrapper.eq(UserInterfaceInfo::getUserId,loginUser.getId());
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(queryWrapper);
        if(userInterfaceInfo == null || userInterfaceInfo.getLeftNum() <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "调用次数不足！");
        }

        /** 4.动态调用接口 */
        String requestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        Object result = invokeInterfaceInfo(interfaceInfo.getSdkClient(),
                interfaceInfo.getName(), requestParams,
                accessKey, accessSecret);
        if(result == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(result);
    }

    /**
     * 利用反射根据 sdkClient和接口名动态调用对应的方法
     * @param sdkClient 根据业务划分的 sdk 客户端
     * @param methodName sdk 方法名
     * @param requestParams json格式统一封装用户的请求参数
     * @param accessKey
     * @param accessSecret
     * @return
     */
    public Object invokeInterfaceInfo(String sdkClient, String methodName, String requestParams,String accessKey, String accessSecret) {
        if(StringUtils.isAnyBlank(sdkClient,methodName,accessKey,accessSecret)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        try {
            Class<?> clazz = Class.forName(sdkClient);
            //1.获取对应 sdkClient 的构造器，参数为ak、sk
            Constructor<?> apiClientConstructor = clazz.getConstructor(String.class, String.class);
            //2.初始化对应 sdkClient 对象
            Object apiClient = apiClientConstructor.newInstance(accessKey, accessSecret);
            //3.找到该类对应的方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if(methodName.equals(method.getName())) {
                    //3.1 获取方法的参数类型列表
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    // 不需要参数，直接调用
                    if(parameterTypes.length == 0) {
                        return method.invoke(apiClient);
                    }
                    Gson gson = new Gson();
                    Object parameter = gson.fromJson(requestParams, parameterTypes[0]);
                    return method.invoke(apiClient,parameter);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "找不到调用的方法!! 请检查你的请求参数是否正确!");
        }
        return null;
    }

    //todo 用户查询接口列表，再写个接口显示已开放的接口列表
//    /**
//     * 分页获取列表（封装类）
//     *
//     * @param interfaceInfoQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<PostVO>> listPostVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
//            HttpServletRequest request) {
//        long current = interfaceInfoQueryRequest.getCurrent();
//        long size = interfaceInfoQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
//                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
//        return ResultUtils.success(interfaceInfoService.getPostVOPage(interfaceInfoPage, request));
//    }
//
//    /**
//     * 分页获取当前用户创建的资源列表
//     *
//     * @param interfaceInfoQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page/vo")
//    public BaseResponse<Page<PostVO>> listMyPostVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
//            HttpServletRequest request) {
//        if (interfaceInfoQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User loginUser = userService.getLoginUser(request);
//        interfaceInfoQueryRequest.setUserId(loginUser.getId());
//        long current = interfaceInfoQueryRequest.getCurrent();
//        long size = interfaceInfoQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
//                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
//        return ResultUtils.success(interfaceInfoService.getPostVOPage(interfaceInfoPage, request));
//    }

}
