package org.hulan.rest;

import com.alibaba.fastjson.JSONObject;
import org.hulan.constant.State;
import org.hulan.model.NoteProperties;
import org.hulan.service.QNoteService;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
	public String edit(@RequestParam(value = "pid",required = false) String pid,@RequestParam(value = "content",required = false) String content,Map model){
		NoteProperties properties = null;
		if(StringUtils.hasText(pid)){
			properties = qNoteService.authQnote(pid);
		}
		model.put("properties",properties);
		model.put("content",content);
		model.put("type","edit");
		return "qnote/edit";
	}
	
	@RequestMapping("/saveNote")
	public String saveNote(@RequestParam Map params,Map modal){
		boolean flag = qNoteService.saveNote(new JSONObject(params));
		if(flag){
			return "redirect:/qnote/list";
		}
		return edit(null, (String) params.get("content"),modal);
	}
	
	@RequestMapping("/list")
	public ModelAndView list(){
		Map model = new HashMap();
		model.put("type","list");
		model.put("list",qNoteService.queryUserNotes());
		return new ModelAndView("qnote/list",model);
	}
	
	@GetMapping("/view/{username}/{pid}")
	public ModelAndView viewQnote(@PathVariable("username") String username,@PathVariable("pid") String pid,Map modal){
		NoteProperties properties = qNoteService.existsQnote(username,pid);
		if(properties == null){
			return new ModelAndView("redirect:/qnote/list");
		}
		modal.put("type","view");
		modal.put("properties",properties);
		modal.put("username",username);
		modal.put("path",qNoteService.getNotePath(properties));
		return new ModelAndView("qnote/view",modal);
	}
	
	@RequestMapping("/delete/{pid}")
	@ResponseBody
	public String deleteQnote(@PathVariable("pid") String pid,Map modal){
		State.StateWrapper state = qNoteService.deleteQnote(pid);
		return state.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("hulan"));
	}
}
