package org.example.filter;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * @author yangshunxin
 * @create 2021-07-07-14:42
 */
public class CharacterEncodingFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {
//        Filter.super.init(filterConfig);
    }

    public void destroy() {
//        Filter.super.destroy();
    }
}
