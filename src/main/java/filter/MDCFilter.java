package filter; /**
 * Baifubao.com,Inc.
 * Copyright (c) 2017-2018 All Rights Reserved.
 */

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;

import util.ContextHolder;

/**
 *   过滤器
 *
 *   MDC是用于本线程的跟踪；将业务值传入Request的Header中，是为了不同线程（如不同系统）的跟踪
 *
 *   考虑问题：
 *
 *         怎样保证所生成的traceId是唯一的？ (LogId一般有前端的负载生成，比如Nginx或Lighttpd,这句话含义？)
 *
 *         怎样将业务ID传入Request中的？(HttpServletRequest中没有相关方法添加header,它是先存入上下文ContextHolder中，然后再去取，ThreadLocal实现的)
 *
 *
 * @author duanyuejiao
 */
public class MDCFilter implements Filter {

    private static final String CTX_LOG_ID_MDC        = "ctxLogId";

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest)request;
        try {
            String ctxId = hsr.getHeader(CTX_LOG_ID_MDC);
            if (null == ctxId || ctxId.equals("")) {
                ctxId = genId();
            }
            MDC.put(CTX_LOG_ID_MDC, ctxId);
            ContextHolder.put(CTX_LOG_ID_MDC, ctxId);
        } catch (Exception e) {
            //
        }

        try {
            chain.doFilter(request,response);
        } finally {
            MDC.clear();//must be,threadLocal
        }

    }

    private String genId() {
        return UUID.randomUUID().toString();
    }
}