package com.ipaas.monitoringplstformsys.common.exception.base;


public class XDapUnauthorizediException extends XDapBaseException {
    public  XDapUnauthorizediException(BaseExceptionEnumInterface exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

}