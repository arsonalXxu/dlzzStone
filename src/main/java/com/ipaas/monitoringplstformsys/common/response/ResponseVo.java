package com.ipaas.monitoringplstformsys.common.response;


import com.ipaas.monitoringplstformsys.common.i18n.I18nService;
import com.ipaas.monitoringplstformsys.common.utils.ContextUtil;

import java.util.HashMap;
import java.util.Map;

public class ResponseVo<T> {
    public static final String CODE_OK = "ok";
    public static final String CODE_ERR = "error";
    public static final String CODE_WARNING = "warning";
    public static final String MSG_OK = "message_ok";
    public static final String MSG_ERR = "message_err";
    private String code;
    private String bizCode;
    private String message;
    private Long interval;
    private T data;

    public ResponseVo() {
        this("ok", "message_ok");
    }

    public ResponseVo(String code, String message) {
        this.code = "ok";
        this.code = code;
        this.message = this.getI18nMessage(message);
    }

    public ResponseVo(String code, String message, Object... args) {
        this.code = "ok";
        this.code = code;
        this.message = this.getI18nMessage(message, args);
    }

    public static ResponseVo error(String message) {
        return new ResponseVo("error", message);
    }

    public static ResponseVo error(String message, Object... args) {
        return new ResponseVo("error", message, args);
    }

    public static ResponseVo warning(String message) {
        return new ResponseVo("warning", message);
    }

    public static ResponseVo warning(String message, Object... args) {
        return new ResponseVo("warning", message, args);
    }

    public Long getInterval() {
        return this.interval;
    }

    public ResponseVo setInterval(Long interval) {
        this.interval = interval;
        return this;
    }

    public ResponseVo setData(T data) {
        this.data = data;
        return this;
    }

    public ResponseVo data(T data) {
        return this.setData(data);
    }

    public static ResponseVo ok() {
        return new ResponseVo();
    }

    public ResponseVo set(String field, String value) {
        if (this.data == null || !(this.data instanceof Map)) {
            this.data = (T) new HashMap();
        }

        ((Map)this.data).put(field, value);
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public ResponseVo setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ResponseVo setMessage(String message) {
        this.message = this.getI18nMessage(message);
        return this;
    }

    public ResponseVo setMessage(String message, Object... args) {
        this.message = this.getI18nMessage(message, args);
        return this;
    }

    public Object getData() {
        return this.data;
    }

    private String getI18nMessage(String message) {
        I18nService i18nService = (I18nService) ContextUtil.getBean(I18nService.class);
        return i18nService.getMessage(message);
    }

    private String getI18nMessage(String message, Object... args) {
        I18nService i18nService = (I18nService)ContextUtil.getBean(I18nService.class);
        return i18nService.getMessage(message, args);
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public ResponseVo<T> setBizCode(String bizCode) {
        this.bizCode = bizCode;
        return this;
    }
}
