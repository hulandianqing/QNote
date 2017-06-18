package org.hulan.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hulan.constant.State;
import org.hulan.model.CurrentOperator;
import org.hulan.model.Operator;
import org.hulan.repository.OperatorRepository;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static org.hulan.constant.State.*;
import static org.hulan.constant.SysConstant.*;

/**
 * 功能描述：
 * 时间：2017/6/11 9:57
 * @author ：zhaokuiqiang
 */
@SuppressWarnings("ALL")
@Service
public class OperatorService {
	@Autowired
	OperatorRepository operatorRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	RememberMeServices rememberMeServices;
	@Autowired
	UserDetailsService userDetailsService;
	
	/**
	 * 操作员认证
	 * @param json
	 * @return
	 */
	public StateWrapper auth(JSONObject json, HttpServletRequest request, HttpServletResponse response){
		/*Authentication au = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(json.getString(USERNAME)
						,json.getString(PASSWORD)));
		WebUtil.setAuthentication(au);
		Operator operator = operatorRepository.findByUsername(json.getString(USERNAME));
		WebUtil.getSession(true).setAttribute(SYS_OPERATOR,operator);*/
		if(json == null){
			return State.JUMP;
		}
		if(!StringUtils.hasText(json.getString(USERNAME)) || !StringUtils.hasText(PASSWORD)){
			return State.JUMP;
		}
		UserDetails operator = userDetailsService.loadUserByUsername(json.getString(USERNAME));
		if(operator == null){
			return State.OPERATOR_NOT_FOUNT;
		}
		if(passwordEncoder.matches(json.getString(PASSWORD),operator.getPassword())){
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(operator,operator.getPassword(),operator.getAuthorities());
			rememberMeServices.loginSuccess(request,response,authenticationToken);
			return State.SUCCESS;
		}
		return State.OPERATOR_PASSWORD_ERROR;
	}
}
