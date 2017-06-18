package org.hulan.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hulan.constant.State;
import org.hulan.service.OperatorService;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.hulan.constant.State.SUCCESS;

/**
 * 功能描述：
 * 时间：2017/6/11 14:48
 * @author ：zhaokuiqiang
 */
@Controller
public class OperatorController {
	
	@Autowired
	OperatorService operatorService;
	
	@RequestMapping("/")
	public String welcome(){
		return "redirect:/qnote/list";
	}
	
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
		State.StateWrapper wrapper = operatorService.auth(new JSONObject(params),request,response);
		if(wrapper == State.SUCCESS){
			return new ModelAndView("redirect:/qnote/list");
		}
		return new ModelAndView("login","message",wrapper.getMessage());
	}
}
