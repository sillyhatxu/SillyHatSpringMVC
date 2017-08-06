package com.sillyhat.springmvc.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component("springUtils")
@Lazy(false)
//实现applicationContextAware接口，在bean的实例化时会自动调用setApplicationContext方法
//实现disposable接口在注销的时候会调用destroy();
public final class SpringUtils implements DisposableBean,ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public void destroy() {
        applicationContext = null;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    public static String getMessage(String code, Object[] args) {
        LocaleResolver localeResolver = SpringUtils.getBean("localeResolver", LocaleResolver.class);
        Locale locale = localeResolver.resolveLocale(null);
//        Locale locale = localeResolver.resolveLocale(null);
//        getMessage是通过调用messageSource Bean里面的getMessage()来实现的
        return applicationContext.getMessage(code, args, locale);
    }
}
