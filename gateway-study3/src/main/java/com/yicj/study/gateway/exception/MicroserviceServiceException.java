package com.yicj.study.gateway.exception;

import lombok.Data;

@Data
public class MicroserviceServiceException extends RuntimeException {

    private String code ;
}
