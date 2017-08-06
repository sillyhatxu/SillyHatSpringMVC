package com.sillyhat.base.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BasePathUtils
 *
 * @author 徐士宽
 * @date 2017/3/30 16:46
 */
public final class BasePathUtils {

    private BasePathUtils() {

    }

    public static String getBasePath(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        return basePath;
    }
}