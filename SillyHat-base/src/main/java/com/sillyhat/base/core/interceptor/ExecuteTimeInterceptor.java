package com.sillyhat.base.core.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 徐士宽
 * @date 2017/3/30 15:56
 */
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LogManager.getLogger(ExecuteTimeInterceptor.class);
    private static final String START_TIME = ExecuteTimeInterceptor.class.getName() + ".START_TIME";
    private static final String EXECUTE_TIME_ATTRIBUTE_NAME = ExecuteTimeInterceptor.class.getName() + ".EXECUTE_TIME";

    /**
     * 在业务处理器处理请求之前被调用<br/>
     * <br/>如果返回false<br/>
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链<br/>
     * 如果返回true<br/>
     * 执行下一个拦截器,直到所有的拦截器都执行完毕<br/>
     * 再执行被拦截的Controller<br/>
     * 然后进入拦截器链<br/>
     * 从最后一个拦截器往回执行所有的postHandle()<br/>
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()<br/>
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("【顺序】3【拦截器】ExecuteTimeInterceptor【操作】preHandle||转交给业务处理器处理请求之前调用此方法。");
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime == null) {
            startTime = System.currentTimeMillis();
            request.setAttribute(START_TIME, startTime);
        }
        return true;
    }

    /**
     * 在业务处理器处理请求、执行完成后,生成视图之前执行的动作<br/>
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("【顺序】3【拦截器】ExecuteTimeInterceptor【操作】postHandle||在响应客户端请求,生成试图请求之前调用此方法。");
        Long executeTime = (Long) request.getAttribute(EXECUTE_TIME_ATTRIBUTE_NAME);
        String viewName = null;
        if (executeTime == null) {
            Long startTime = (Long) request.getAttribute(START_TIME);
            Long currentTime = System.currentTimeMillis();
            executeTime = currentTime - startTime;
            request.setAttribute(START_TIME, startTime);
        }
        if (modelAndView != null) {
            viewName = modelAndView.getViewName();
            if (!StringUtils.startsWith(viewName, "redirect:")) {
                modelAndView.addObject(EXECUTE_TIME_ATTRIBUTE_NAME, executeTime);
            }
        }
        log.info("Operaction:[" + viewName + "]   [" + handler + "] executeTime: " + executeTime + "ms");
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("【顺序】3【拦截器】ExecuteTimeInterceptor【操作】afterCompletion||响应完客服端的请求之后调用此方法。");
    }
}
