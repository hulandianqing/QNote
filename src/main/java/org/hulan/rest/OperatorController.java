package org.hulan.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hulan.service.OperatorService;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

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
		if(WebUtil.isAuth()){
			return "redirect:index";
		}
		return "login";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(@RequestParam Map<String,Object> params){
		operatorService.auth(new JSONObject(params));
		return "";
	}
}
