package org.hulan.service;

import com.alibaba.fastjson.JSONObject;
import org.hulan.constant.State;
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
import org.springframework.stereotype.Service;

import static org.hulan.constant.State.*;
import static org.hulan.constant.SysConstant.NOMAL;
import static org.hulan.constant.SysConstant.PASSWORD;
import static org.hulan.constant.SysConstant.USERNAME;

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
	UserDetailsService userDetailsService;
	
	/**
	 * 操作员认证
	 * @param json
	 * @return
	 */
	public void auth(JSONObject json){
		Authentication au = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(json.getString(USERNAME)
						,json.getString(PASSWORD)));
		WebUtil.setAuthentication(au);
	}
}
