package com.hsy.springboot.oauth2.all.multiLogin.filter;
import com.hsy.springboot.oauth2.all.multiLogin.common.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * springboot2/com.hsy.springboot.oauth2.all.filter
 *
 * @author heshiyuan
 */
public class ParamTogetherFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        String username = request.getParameter("username");
        /**
         * loginType :登录类型：0-用户名登录；1-工号登录；2-手机号登录
         */
        String loginType = request.getParameter("loginType");
        StringBuilder newUserName = new StringBuilder();
        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(loginType)){
            newUserName.append(username).append(CommonConstant.USERNAME_LOGINTYPE_SPLIT)
                    .append(loginType);
            CommonConstant.LOGIN_TYPE.set(newUserName.toString());
        }
        filterChain.doFilter(request, response);
    }
}
