package com.baimao.bmapibackend.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装只需 id 的请求
 */
@Data
public class IdRequest implements Serializable {


    private Long id;

    private static final long serialVersionUID = 1L;
}