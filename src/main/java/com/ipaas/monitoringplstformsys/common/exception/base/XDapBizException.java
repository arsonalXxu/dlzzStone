package com.ipaas.monitoringplstformsys.common.exception.base;

public class XDapBizException extends XDapBaseException {
    public XDapBizException(BaseExceptionEnumInterface exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public XDapBizException(BaseExceptionEnumInterface exceptionEnum, Object... args) {
        this.exceptionEnum = exceptionEnum;
        this.args = args;
    }
}
