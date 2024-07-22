package com.baimao.bmapicommon.model.vo;

import com.baimao.bmapicommon.model.entity.InterfaceInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口分析
 **/
@Data
public class InterfaceInfoVO extends InterfaceInfo implements Serializable {

    /**
     * 接口被调用的总次数
     */
    private Integer totalNum;

}