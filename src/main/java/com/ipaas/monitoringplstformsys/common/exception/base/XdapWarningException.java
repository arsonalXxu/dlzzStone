package com.ipaas.monitoringplstformsys.common.exception.base;


import com.ipaas.monitoringplstformsys.common.constant.DeipaasExceptionEnum;

public class XdapWarningException extends XDapBaseException {
    private Long interval;

    public XdapWarningException(DeipaasExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public XdapWarningException(BaseExceptionEnumInterface exceptionEnum, Long interval) {
        this.exceptionEnum = exceptionEnum;
        this.interval = interval;
    }

    public XdapWarningException(BaseExceptionEnumInterface exceptionEnum, Object... args) {
        this.exceptionEnum = exceptionEnum;
        this.args = args;
    }

    public XdapWarningException(BaseExceptionEnumInterface exceptionEnum, Long interval, Object... args) {
        this.exceptionEnum = exceptionEnum;
        this.interval = interval;
        this.args = args;
    }
}
