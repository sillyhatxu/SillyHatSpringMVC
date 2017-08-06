package com.sillyhat.springmvc.core.interceptor;

import com.sillyhat.springmvc.utils.BasePathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 徐士宽
 * @date 2017/3/30 15:56
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    /**
     * 在【业务处理器】处理请求之前被调用<br/>
     * <br/>如果返回false<br/>
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链<br/>
     * 如果返回true<br/>
     * 执行下一个拦截器,直到所有的拦截器都执行完毕<br/>
     * 再执行被拦截的Controller<br/>
     * 然后进入拦截器链<br/>
     * 从最后一个拦截器往回执行所有的postHandle()<br/>
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()<br/>
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("【顺序】2【拦截器】TokenInterceptor【操作】preHandle||转交给业务处理器处理请求之前调用此方法。");
//        String jsessionid = CookieUtils.getCookie(request, Constants.DEFAULT_COOKIE_JSESSIONID);
//        if(jsessionid == null || "".equals(jsessionid)){
//            CookieUtils.addCookie(request, response, Constants.DEFAULT_COOKIE_JSESSIONID, SessionIdGenerator.getJSessionId());
//        }
        return true;
    }

    /**
     * 在业务处理器处理请求、执行完成后,生成视图之前执行的动作<br/>
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.info("【顺序】2【拦截器】TokenInterceptor【操作】postHandle||在响应客户端请求,生成试图请求之前调用此方法。");
        //添加basepath
        if(modelAndView!=null){
            String basePath = BasePathUtils.getBasePath(request, response);
            modelAndView.addObject("base", basePath);
        }
    }


    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("【顺序】2【拦截器】TokenInterceptor【操作】afterCompletion||响应完客服端的请求之后调用此方法。");
    }
}
