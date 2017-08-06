package com.sillyhat.base.core.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Get请求的数据进行编码转换filter
 * OncePerRequestFilter确保每个请求只会被 Filter过滤一次，而不需要重复执行
 *
 * @author 徐士宽
 */
public class EncodingConvertFilter extends OncePerRequestFilter {

    private static final Logger log = LogManager.getLogger(EncodingConvertFilter.class);

    private String fromEncoding = "ISO-8859-1";
    private String toEncoding = "UTF-8";

    @SuppressWarnings("unchecked")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            Iterator iterator = request.getParameterMap().values().iterator();
            while (iterator.hasNext()) {
                String[] parameters =  (String[])iterator.next();
                for (int i = 0; i < parameters.length; i++)
                    try {
                        parameters[i] = new String(parameters[i].getBytes(this.getFromEncoding()), this.getToEncoding());
                    } catch (UnsupportedEncodingException e) {
                        log.error("[Get请求的数据进行编码转换filter]编码转换-->发生异常:{}", e.getMessage());
                    }
            }
        }
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("[Get请求的数据进行编码转换filter]转交下一个处理发生异常-->{}", e.getMessage());
        }
    }

    public void setFromEncoding(String fromEncoding) {
        this.fromEncoding = fromEncoding;
    }

    public void setToEncoding(String toEncoding) {
        this.toEncoding = toEncoding;
    }

    public String getFromEncoding() {
        return fromEncoding;
    }

    public String getToEncoding() {
        return toEncoding;
    }
}
