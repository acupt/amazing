package com.acupt.amazing.filter;

import com.acupt.amazing.util.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liujie on 2017/8/15.
 */
@WebFilter(filterName = "requestFilter", urlPatterns = "/*")
public class RequestFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static List<String> staticUri = Arrays.asList("/js/", "/img/", "/css/", "/fonts/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long t0 = System.currentTimeMillis();
        String uri = request.getRequestURI();
//        if (uri.startsWith("/qq/") && !LoginContext.get(request).isLogined()) {
//            response.sendRedirect("/");
//            return;
//        }
        filterChain.doFilter(request, response);
        if (!isStatic(uri)) {
            logger.info("{}:{},ip:{},cost:{}ms,agent:{}", request.getMethod(), uri, ContextUtil.getRemoteIp(request),
                    System.currentTimeMillis() - t0, ContextUtil.getAgent(request));
        }
    }

    private boolean isStatic(String uri) {
        for (String head : staticUri) {
            if (uri.startsWith(head)) {
                return true;
            }
        }
        if (uri.equals("/favicon.ico")) {
            return true;
        }
        return false;
    }
}
