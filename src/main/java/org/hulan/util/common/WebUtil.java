package org.hulan.util.common;

import org.hulan.model.CurrentOperator;
import org.hulan.model.Operator;
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

import static org.hulan.constant.SysConstant.SYS_OPERATOR;

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
	
	public static Operator operator(){
		CurrentOperator operator = (CurrentOperator) getAuthentication().getPrincipal();
		if(operator == null){
			return null;
		}
		return operator.getOperator();
	}
	
	public static void setAuthentication(Authentication authentication){
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public static Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static boolean isAuth(){
		Authentication authentication = WebUtil.getAuthentication();
		if(authentication == null){
			return false;
		}
		return !"anonymousUser".equals(authentication.getName());
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
