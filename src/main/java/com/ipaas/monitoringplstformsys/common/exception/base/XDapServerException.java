package com.ipaas.monitoringplstformsys.common.exception.base;

public class XDapServerException extends XDapBaseException {
    public XDapServerException(BaseExceptionEnumInterface exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public XDapServerException(BaseExceptionEnumInterface exceptionEnum, Object... args) {
        this.exceptionEnum = exceptionEnum;
        this.args = args;
    }
}
