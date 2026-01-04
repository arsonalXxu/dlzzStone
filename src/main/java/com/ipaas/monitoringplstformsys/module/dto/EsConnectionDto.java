package com.ipaas.monitoringplstformsys.module.dto;

import com.ipaas.monitoringplstformsys.common.constant.DeipaasConstant;
import lombok.Data;

import java.util.List;

@Data
public class EsConnectionDto {
    /**
     * 请求协议（HTTP、HTTPS）
     */
    private String protocol;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 日志存储库连接信息
     */
    private List<EsConnectionConfigDto> connectConfig;

    /**
     * 根据请求协议获取URL前缀
     *
     * @return
     */
    public String buildProtocolPrefix() {
        if (DeipaasConstant.HTTP_UPPER.equals(this.protocol)) {
            return DeipaasConstant.HTTP_PREFIX;
        }
        if (DeipaasConstant.HTTPS_UPPER.equals(this.protocol)) {
            return DeipaasConstant.HTTPS_PREFIX;
        }
        return "";
    }
}
