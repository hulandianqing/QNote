package org.hulan.rest;

import com.alibaba.fastjson.JSONObject;
import org.hulan.model.NoteProperties;
import org.hulan.service.QNoteService;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 * 时间：2017/6/10 18:03
 * @author ：zhaokuiqiang
 */
@Controller
@RequestMapping("/qnote")
public class QNoteController {
	
	@Autowired
	QNoteService qNoteService;
	
	@RequestMapping("/edit")
	public String index(Map model){
		model.put("type","edit");
		return "qnote/edit";
	}
	
	@RequestMapping("/saveNote")
	public String saveNote(@RequestParam Map params){
		boolean flag = qNoteService.saveNote(new JSONObject(params));
		if(flag){
			return "redirect:/qnote/list";
		}
		return "qnote/edit";
	}
	
	@RequestMapping("/list")
	public ModelAndView list(){
		Map model = new HashMap();
		model.put("type","list");
		model.put("list",qNoteService.queryUserNotes());
		return new ModelAndView("qnote/list",model);
	}
	
	@GetMapping("{username}/{pid}")
	public ModelAndView viewQnote(@PathVariable("username") String username,@PathVariable("pid") String pid,Map modal){
		NoteProperties properties = qNoteService.existsQnote(username,pid);
		if(properties == null){
			return new ModelAndView("redirect:/qnote/list");
		}
		modal.put("type","home");
		modal.put("properties",properties);
		modal.put("username",username);
		modal.put("path",properties.getCreatetime().split(" ")[0]);
		return new ModelAndView("qnote/view",modal);
	}
}
