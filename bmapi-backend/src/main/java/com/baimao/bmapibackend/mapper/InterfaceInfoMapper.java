package com.baimao.bmapibackend.mapper;

import com.baimao.bmapicommon.model.entity.InterfaceInfo;
import com.baimao.bmapicommon.model.vo.InterfaceInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity com.baimao.bmapi.model.entity.InterfaceInfo
 */
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {
    /**
     * 接口分析，统计接口调用次数
     * @param limit 查询前n条
     * @return
     */
    List<InterfaceInfoVO> listInterfaceInfoAnalysis(Integer limit);
}




