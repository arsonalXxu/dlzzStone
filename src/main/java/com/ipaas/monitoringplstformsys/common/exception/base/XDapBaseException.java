package com.ipaas.monitoringplstformsys.common.exception.base;

public abstract class XDapBaseException extends RuntimeException {
    BaseExceptionEnumInterface exceptionEnum;
    Object[] args;

    public XDapBaseException() {
    }

    public String getMessage() {
        return this.exceptionEnum.getMessage();
    }

    public String getCode() {
        return this.exceptionEnum.getCode();
    }

    public Object[] getArgs() {
        return this.args;
    }
}
