package com.aerotop.ssoserver.filter;

import com.aerotop.constant.AuthConstant;
import com.aerotop.ssoserver.storage.ClientStorage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName: SessionFilter
 * @Description: TODO
 * @Author: gaosong
 * @Date 2021/2/3 17:15
 */
@WebFilter(filterName = "SessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("进入SessionFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String logoutRequest = "/logout";
        // 注销请求，放行
        if (logoutRequest.equals(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 已经登录，放行
        if (session.getAttribute(AuthConstant.IS_LOGIN) != null) {
            // 如果是客户端发起的登录请求，跳转回客户端，并附带token
            String clientUrl = request.getParameter(AuthConstant.CLIENT_URL);
            String token = (String) session.getAttribute(AuthConstant.TOKEN);
            if (clientUrl != null && !"".equals(clientUrl)) {
                // 存储，用于注销
                ClientStorage.INSTANCE.set(token, clientUrl);
                response.sendRedirect(clientUrl + "?" + AuthConstant.TOKEN + "=" + token);
                return;
            }
            if (!"/success".equals(uri)) {
                response.sendRedirect("/success");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        // 登录请求，放行
        if ("/".equals(uri) || "/login".equals(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 其他请求，拦截
        response.sendRedirect("/");
    }

    @Override
    public void destroy() {

    }
}
