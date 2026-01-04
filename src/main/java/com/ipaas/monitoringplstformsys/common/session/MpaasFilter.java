//package com.ipaas.monitoringplstformsys.common.session;
//
//
//import com.ipaas.monitoringplstformsys.common.entity.IMpaasSSOAuthentication;
//import com.ipaas.monitoringplstformsys.common.entity.UserProfile;
//import com.ipaas.monitoringplstformsys.common.exception.base.XDapUnauthorizediException;
//import com.ipaas.monitoringplstformsys.exception.AuthsExceptionEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class MpaasFilter implements Filter {
//    @Autowired(
//            required = false
//    )
//    private IMpaasSSOAuthentication ssoAuthentication;
//
//    public MpaasFilter() {
//    }
//
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        UserProfile userProfile = null;
//        // 校验API访问权限，根据当前用户获取用户的菜单权限，获取对应的API权限
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        try {
//            Cookie[] cookies = request.getCookies();
//            Map<String, String> ck = null;
//            if (cookies != null) {
//                ck = new HashMap<>();
//
//                for (Cookie k : cookies) {
//                    ck.put(k.getName(), k.getValue());
//                }
//            }
//
//            Map<String, String> header = new HashMap<>();
//            Enumeration<String> headers = request.getHeaderNames();
//
//            while(headers.hasMoreElements()) {
//                String label = (String)headers.nextElement();
//                header.put(label, request.getHeader(label));
//            }
//
//            try {
//                userProfile = this.ssoAuthentication.ssoAuth(header, ck);
//            } catch (Exception e) {
//                e.printStackTrace();
//                userProfile = UserProfile.anonymous;
//            }
//        } catch (Exception e) {
//            throw new XDapUnauthorizediException(AuthsExceptionEnum.UNAUTHORIZED_ERROR);
//        }
//        MpaasSession.setUserProfile(userProfile);
//        BodyReaderRequestWrapper wrapper=null;
//        if("application/json".equals(request.getHeader("Content-Type"))) {
//            wrapper = new BodyReaderRequestWrapper(request);
//        }
//
//        if( wrapper == null ){
//            filterChain.doFilter(servletRequest,servletResponse);
//        }else{
//            filterChain.doFilter(wrapper, servletResponse);
//        }
//    }
//
//
//}
