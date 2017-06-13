package org.hulan.rest;

import org.hulan.service.QNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 功能描述：
 * 时间：2017/6/10 18:03
 * @author ：zhaokuiqiang
 */
@Controller
public class QNoteController {
	
	@Autowired
	QNoteService qNoteService;
	
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/saveNote")
	public String saveNote(@RequestParam Map params){
		System.out.println(params);
		return "index";
	}
}
