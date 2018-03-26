package org.hulan.security;

import org.hulan.constant.SysConstant;
import org.hulan.model.CurrentOperator;
import org.hulan.model.Operator;
import org.hulan.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hulan.constant.SysConstant.SYS_OPERATOR;

/**
 * 功能描述：
 * 时间：2017/6/11 12:25
 * @author ：zhaokuiqiang
 */
@SuppressWarnings("ALL")
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
//	@Autowired
//	SecurityFilter securityFilter;
	@Autowired
	UserDetailsService myUserDetailsService;
	@Autowired
	OperatorRepository operatorRepository;
	String remembermekey = "QNOTEKEY";
	String remembermeUID = "QNOTEUID";
	
	@Override
	protected UserDetailsService userDetailsService() {
		return myUserDetailsService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/favicon.ico","/resources/**","/login").permitAll()
				.anyRequest().authenticated().and()
				.formLogin()
				.loginPage("/login")
				.failureUrl("/login?error")
				.and().logout()
				.logoutUrl("/logout")
				.deleteCookies(remembermeUID)
				.permitAll()
				.and()
				.rememberMe()
				.rememberMeCookieName(remembermeUID)
				.key(remembermekey);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/","/favicon.ico","/resources/**","/login");
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
	
	@Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationManagerBean();
    }
	
	@Bean
	public UserDetailsService myUserDetailsService() {
		return username -> {
			Operator operator = operatorRepository.findByUsername(username);
			if(operator == null){
				operator = operator.EMPTY;
			}
			return new CurrentOperator(operator);
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	RememberMeServices rememberMeServices(){
		TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(remembermekey,userDetailsService);
		tokenBasedRememberMeServices.setAlwaysRemember(true);
		tokenBasedRememberMeServices.setCookieName(remembermeUID);
		return tokenBasedRememberMeServices;
	}
	
	@Bean
	public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
		return new FilterInvocationSecurityMetadataSource() {
			@Override
			public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
				ConfigAttribute configAttribute = new org.springframework.security.access.SecurityConfig("RUN_AS_1");
				return Collections.singletonList(configAttribute);
			}
			
			@Override
			public Collection<ConfigAttribute> getAllConfigAttributes() {
				ConfigAttribute configAttribute = new org.springframework.security.access.SecurityConfig("RUN_AS_1");
				return Collections.singletonList(configAttribute);
			}
			
			@Override
			public boolean supports(Class<?> clazz) {
				return true;
			}
		};
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		return new AccessDecisionManager() {
			@Override
			public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
				if(configAttributes == null) {
					return;
				}
				/*Iterator<ConfigAttribute> ite = configAttributes.iterator();
				while(ite.hasNext()) {
					ConfigAttribute ca = ite.next();
					String needRole = ((SecurityConfig) ca).getAttribute();
					for(GrantedAuthority ga : authentication.getAuthorities()) {
						if(needRole.trim().equals(ga.getAuthority().trim())) {
							return;
						}
					}
				}
				throw new AccessDeniedException("权限不足");*/
			}
			
			@Override
			public boolean supports(ConfigAttribute attribute) {
				return true;
			}
			
			@Override
			public boolean supports(Class<?> clazz) {
				return true;
			}
		};
	}
}
