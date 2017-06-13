package org.hulan.util.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * 功能描述：Web工具
 */
public class WebUtil {
	
	/**
	 * 功能描述：获取request
	 * @author：zhaokuiqiang
	 * @时间：2017-05-15
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 功能描述：获取session
	 * @author：zhaokuiqiang
	 * @时间：2017-05-15
	 */
	public static HttpSession getSession() {
		return getSession(false);
	}
	
	/**
	 * 功能描述：获取session
	 * @author：zhaokuiqiang
	 * @时间：2017-05-15
	 */
	public static HttpSession getSession(boolean flag) {
		HttpServletRequest request = getRequest();
		if(request == null) {
			return null;
		}
		return request.getSession(flag);
	}
	
	/**
	 * 获取sessionid
	 * @author：zhaokuiqiang
	 * @时间：2017-05-15
	 * @param flag
	 * @return
	 */
	public static String getSessionId(boolean flag) {
		return getSession(false).getId();
	}
	
	/**
	 * 注销session
	 * @author：zhaokuiqiang
	 * @时间：2017-05-15
	 */
	public static void invalidateSession() {
		HttpSession session = getSession(false);
		if(session != null) {
			session.invalidate();
		}
	}
	
	public static void setAuthentication(Authentication authentication){
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public static Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static boolean isAuth(){
		return !"anonymousUser".equals(WebUtil.getAuthentication().getName());
	}
	
	public static String getUserName(){
		Authentication authentication = getAuthentication();
		if(authentication != null){
			return authentication.getName();
		}
		return null;
	}
	
	public static Collection<? extends GrantedAuthority> getRole(){
		Authentication authentication = getAuthentication();
		if(authentication != null){
			return authentication.getAuthorities();
		}
		return null;
	}
	
	/**
	 * 获取客户端ip地址
	 * @author：zhaokuiqiang
	 * @时间：2017-05-15
	 * @return
	 */
	public static String getClientIp() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
