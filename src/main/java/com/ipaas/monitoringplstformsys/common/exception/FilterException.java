package com.ipaas.monitoringplstformsys.common.exception;

import com.ipaas.monitoringplstformsys.exception.AuthsExceptionEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzp
 * @date 2022/8/12 18:26
 */
@Data
@NoArgsConstructor
public class FilterException extends RuntimeException {
    /**
     * code
     */
    private String code;
    /**
     * message
     */
    private String message;

    public FilterException(AuthsExceptionEnum authsExceptionEnum) {
        this.code = authsExceptionEnum.getCode();
        this.message = authsExceptionEnum.getMessage();
    }
}
