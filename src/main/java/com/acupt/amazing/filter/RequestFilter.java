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

/**
 * Created by liujie on 2017/8/15.
 */
@WebFilter(filterName = "requestFilter", urlPatterns = "/*")
public class RequestFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long t0 = System.currentTimeMillis();
        String uri = request.getRequestURI();
        filterChain.doFilter(request, response);
        if (!isStatic(uri)) {
            logger.info("uri:{},ip:{},cost:{}ms,agent:{}", uri, ContextUtil.getRemoteIp(request),
                    System.currentTimeMillis() - t0, ContextUtil.getAgent(request));
        }
    }

    private boolean isStatic(String uri) {
        if (uri.startsWith("/js/")) {
            return true;
        }
        if (uri.startsWith("/img/")) {
            return true;
        }
        if (uri.equals("/favicon.ico")) {
            return true;
        }
        return false;
    }
}
