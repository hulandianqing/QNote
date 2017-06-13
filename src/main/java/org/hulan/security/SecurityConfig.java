package org.hulan.security;

import org.hulan.model.Operator;
import org.hulan.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 功能描述：
 * 时间：2017/6/11 12:25
 * @author ：zhaokuiqiang
 */
@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	QNoteAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	SecurityFilter securityFilter;
	@Autowired
	UserDetailsService myUserDetailsService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	OperatorRepository operatorRepository;
	
	@Override
	protected UserDetailsService userDetailsService() {
		return myUserDetailsService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.authorizeRequests()
				.antMatchers("/favicon.ico","/resources/**","/login").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/").permitAll().successHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
				System.out.println("??????");
			}
		}).and()
				.logout().permitAll().invalidateHttpSession(true);
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
		auth.eraseCredentials(true);
	}
	
	@Bean
	public UserDetailsService myUserDetailsService() {
		return username -> {
			Operator operator = operatorRepository.findByUsername(username);
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE"+operator.getRole()));
			User user = new User(operator.getUsername(), operator.getPassword(), authorities);
			return user;
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				if(rawPassword == null){
					return null;
				}
				return String.valueOf(rawPassword);
			}
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return true;
			}
		};
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
