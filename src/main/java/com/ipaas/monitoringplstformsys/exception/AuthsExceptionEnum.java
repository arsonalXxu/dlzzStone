package com.ipaas.monitoringplstformsys.exception;


import com.ipaas.monitoringplstformsys.common.exception.base.BaseExceptionEnumInterface;
import com.ipaas.monitoringplstformsys.config.InternationalizationConfig;
public enum AuthsExceptionEnum implements BaseExceptionEnumInterface {
    INVALID_USER("60000", "用户不存在"),

    TOKEN_IS_NULL("60002", "token不存在"),

    TOKEN_IS_ERROR("60006", "登录信息已失效"),
    NOT_MARKET_MANAGER("NOT_MARKET_MANAGER", "当前用户不是API公开市场管理员"),

    UNAUTHORIZED_ERROR("401", "无权限访问"),

    AUTHORITY_MENU_ERROR("60001", "菜单编码错误"),
    /**
     * 授权信息已过期，请检查
     */
    AUTHORIZATION_EXPIRED("60003", "授权信息已过期，请检查"),

    INVALID_REQUEST_PATH("500", "无效的请求路径"),

    INVALID_REQUEST("403", "非法的访问"),
    ;

    private final String code;

    private final String message;

    AuthsExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return InternationalizationConfig.getMessage(this.getClass().getName() + "." + this, this.message);
    }
}
