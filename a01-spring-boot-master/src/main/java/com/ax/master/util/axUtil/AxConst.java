package com.ax.master.util.axUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author axing
 */
public class AxConst {
    /**
     *
     */
    public static final String ADMIN_NAME = "admin";

    /**
     *
     */
    public static final String ADMIN_PASSWORD = "123456";

    /**
     *
     */
    public static final String USERINFO_IN_SESSION = "USERINFO_IN_SESSION";

    public static final String USERINFO_IN_READIS = "USERINFO_IN_READIS";

    /**
     * @return
     */
    public static HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
