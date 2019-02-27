package com.hsy.springboot.oauth2.all.filter;

import org.springframework.security.web.savedrequest.Enumerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: changjiang
 * Date: 2019/1/28
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
public class AuthJsonServletRequestWrapper extends HttpServletRequestWrapper {

    private final HashMap<String, String[]> params;

    public AuthJsonServletRequestWrapper(HttpServletRequest request, HashMap<String, String[]> params) {
        super(request);
        this.params = params;
    }


    @Override
    public String getParameter(String name) {
        if (this.params.containsKey(name)) {
            return this.params.get(name)[0];
        }
        return "";
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Enumerator<>(params.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }
}
