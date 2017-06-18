package org.hulan.service;

import com.alibaba.fastjson.JSONObject;
import org.hulan.constant.SysConstant;
import org.hulan.model.Note;
import org.hulan.model.NoteProperties;
import org.hulan.model.Operator;
import org.hulan.repository.NotePropertyRepository;
import org.hulan.repository.NoteRepository;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.sqlite.date.DateFormatUtils;
import org.thymeleaf.util.DateUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static org.hulan.constant.SysConstant.NOTE_CONTENT;
import static org.hulan.constant.SysConstant.NOTE_TITLE;

/**
 * 功能描述：
 * 时间：2017/6/11 9:57
 * @author ：zhaokuiqiang
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class QNoteService {
	@Autowired
	NoteRepository qNoteRepository;
	@Autowired
	NotePropertyRepository qNotePropertyRepository;
	@Autowired
	TemplateService templateService;
	
	/**
	 * 查询note列表
	 * @return
	 */
	public List<NoteProperties> queryUserNotes(){
		Operator operator = WebUtil.operator();
		List<NoteProperties> noteProperties = (List<NoteProperties>) qNotePropertyRepository.queryByOperator(operator);
		return noteProperties;
	}
	
	/**
	 * 保存note
	 * @param json
	 * @return
	 */
	@Transactional
	public boolean saveNote(JSONObject json) {
		String content = json.getString(NOTE_CONTENT);
		String title = json.getString(NOTE_TITLE);
		if(!StringUtils.hasText(content)){
			return false;
		}
		if(!StringUtils.hasText(title)){
			return false;
		}
		if(title.length() > 30){
			return false;
		}
		Note note = new Note();
		note.setTitle(title);
		note.setType(1);
		NoteProperties noteProperties = new NoteProperties();
		String time = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd hh:mm:ss");
		noteProperties.setCreatetime(time);
		noteProperties.setModifytime(time);
		noteProperties.setNote(note);
		noteProperties.setStatus(1);
		noteProperties.setOperator(WebUtil.operator());
		try {
			note.setContent(content);
			String file = templateService.writeHTML(noteProperties);
			note.setContent(file);
			qNoteRepository.save(note);
			qNotePropertyRepository.save(noteProperties);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
	
	/**
	 * 检索html是否存在
	 * @param username
	 * @param pid
	 * @return
	 */
	public NoteProperties existsQnote(String username,String pid){
		if(StringUtils.hasText(username) && StringUtils.hasText(pid)){
			NoteProperties noteProperties = qNotePropertyRepository.findOne(Long.parseLong(pid));
			if(noteProperties != null){
				if(SysConstant.NOMAL == noteProperties.getStatus()){
					if(templateService.existsFile(username,noteProperties)){
						return noteProperties;
					}
				}
			}
		}
		return null;
	}
}
