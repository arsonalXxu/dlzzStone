package com.ipaas.monitoringplstformsys.common.entity;

import com.ipaas.monitoringplstformsys.common.exception.base.MpaasBusinessException;

import java.util.Map;

public interface IMpaasSSOAuthentication {
    UserProfile ssoAuth(Map<String, String> header, Map<String, String> cookies) throws MpaasBusinessException;
}
