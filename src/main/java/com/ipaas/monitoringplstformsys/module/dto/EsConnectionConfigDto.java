package com.ipaas.monitoringplstformsys.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsConnectionConfigDto {
    /**
     * ip/域名
     */
    private String host;
    /**
     * 端口
     */
    private String port;
}
