package org.hulan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 功能描述：
 * 时间：2017/5/27 11:38
 * @author ：zhaokuiqiang
 */
public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {
	
	@Autowired
	FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
	
	@Autowired
	AccessDecisionManager accessDecisionManager;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterInvocation filterInvocation = new FilterInvocation(request,response,chain);
		InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try{
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }finally{
            super.afterInvocation(token, null);
        }
	}
	
	@Override
	public void destroy() {
	
	}
	
	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}
	
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return filterInvocationSecurityMetadataSource;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setAuthenticationManager(authenticationManager);
		setAccessDecisionManager(accessDecisionManager);
		super.afterPropertiesSet();
	}
	
}
