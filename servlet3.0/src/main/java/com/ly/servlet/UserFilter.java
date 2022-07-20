package com.ly.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * FileName:UserFilter.class
 * Author:ly
 * Date:2022/7/20
 * Description:
 */
public class UserFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("UserFilter 过虑器执行success");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
