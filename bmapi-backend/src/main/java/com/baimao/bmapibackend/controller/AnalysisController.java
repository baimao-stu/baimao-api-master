package com.baimao.bmapibackend.controller;

import com.baimao.bmapibackend.annotation.AuthCheck;
import com.baimao.bmapibackend.mapper.InterfaceInfoMapper;
import com.baimao.bmapicommon.common.BaseResponse;
import com.baimao.bmapicommon.common.ErrorCode;
import com.baimao.bmapicommon.common.ResultUtils;
import com.baimao.bmapicommon.exception.BusinessException;
import com.baimao.bmapicommon.model.vo.InterfaceInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author baimao
 * @title AnalysisController
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 统计前n条接口的调用次数
     * @param limit 前n条记录
     * @return
     */
    @GetMapping("/listInterfaceInfoInvokeNum")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listInterfaceInfoInvokeNum(String limit) {
        if(StringUtils.isBlank(limit)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<InterfaceInfoVO> interfaceInfoVOS =
                interfaceInfoMapper.listInterfaceInfoAnalysis(Integer.valueOf(limit));
        return ResultUtils.success(interfaceInfoVOS);
    }

}
