package com.ipaas.monitoringplstformsys.common.exception.base;

public enum CommonBizExceptionEnum implements BaseExceptionEnumInterface {
    XDAP_BIZ_ERROR("XDAP-4000", "XDAP_BIZ_ERROR"),
    XDAP_BIZ_FORMAT_MISMATCH("XDAP-4001", "XDAP_BIZ_FORMAT_MISMATCH"),
    XDAP_BIZ_EMPTY_VALUE("XDAP-4002", "XDAP_BIZ_EMPTY_VALUE"),
    XDAP_BIZ_LESS_MIN_LENGTH("XDAP-4003", "XDAP_BIZ_LESS_MIN_LENGTH"),
    XDAP_BIZ_GREATER_MAX_LENGTH("XDAP-4004", "XDAP_BIZ_GREATER_MAX_LENGTH"),
    XDAP_BIZ_LESS_MIN_RANGE("XDAP-4005", "XDAP_BIZ_LESS_MIN_RANGE"),
    XDAP_BIZ_LESS_MAX_RANGE("XDAP-4006", "XDAP_BIZ_LESS_MAX_RANGE");

    private String code;
    private String message;

    private CommonBizExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
