package com.baimao.bmapibackend.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用请求
 *

 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 接口 id
     */
    private Long id;

    /**
     * 请求参数
     */
    private String userRequestParams;


    private static final long serialVersionUID = 1L;
}