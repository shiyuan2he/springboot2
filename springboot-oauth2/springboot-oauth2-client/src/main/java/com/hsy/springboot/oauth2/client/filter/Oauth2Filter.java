//package com.hsy.springboot.oauth2.client.filter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@Slf4j
//@Configuration
//public class Oauth2Filter implements Filter {
//    @Value("${shopFilter.whiteUrl}")
//    private String whiteUrl;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//		log.info("Oauth2Filter.............................");
//
//    	HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request = (HttpServletRequest)servletRequest;
//		if (isContains(request)) {
//	        log.info("白名单过滤认证");
//	        filterChain.doFilter(servletRequest, servletResponse);
//		}else {
//
//			String authorization = request.getHeader("Authorization");
//			/*if(StringUtils.isNotBlank(authorization)){
//				String token = authorization.substring(6);
//				JSONObject tokenInfo = apiServerService.checkToken(token);
//				if(Objects.equals(tokenInfo.get("user_name"), "admin")){
//					filterChain.doFilter(servletRequest, servletResponse);
//				}else{
//					log.error("校验token失败");
//				}
//			}else{
//				response.sendRedirect("http://localhost:9988/login/username?callbackUrl=http://localhost:9678/market/doFilter");
//			}*/
//		}
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//
//	private boolean isContains(HttpServletRequest request) {
//        for (String s : whiteUrl.split(",")) {
//            if (request.getRequestURI().contains(s)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
