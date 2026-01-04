package com.ipaas.monitoringplstformsys.common.handler;



import com.ipaas.monitoringplstformsys.common.exception.base.XDapBizException;
import com.ipaas.monitoringplstformsys.common.exception.base.XdapWarningException;
import com.ipaas.monitoringplstformsys.common.response.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({XdapWarningException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseVo handleXdapWarningException(XdapWarningException e) {
        logger.warn(e.getMessage(), e);
        return ResponseVo.warning(e.getMessage(), e.getArgs()).setBizCode(e.getCode());
    }
    @ExceptionHandler({XDapBizException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseVo handleBizException(XDapBizException e) {
        logger.warn(e.getMessage(), e);
        return ResponseVo.error(e.getMessage(), e.getArgs()).setBizCode(e.getCode());
    }
}

