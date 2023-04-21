package com.axing;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TenantIdInterceptor implements HandlerInterceptor {
    private final WiselyTenantIdResolver tenantIdResolver;

    public TenantIdInterceptor(WiselyTenantIdResolver tenantIdResolver) {
        this.tenantIdResolver = tenantIdResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        tenantIdResolver.setCurrentTenant(request.getHeader("x-tenant-id"));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
