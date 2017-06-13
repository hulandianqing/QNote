package org.hulan.service;

import com.alibaba.fastjson.JSONObject;
import org.hulan.model.Note;
import org.hulan.model.NoteProperties;
import org.hulan.repository.NotePropertyRepository;
import org.hulan.repository.NoteRepository;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.Timestamp;

import static org.hulan.constant.SysConstant.NOTE_CONTENT;

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
	 * 保存note
	 * @param json
	 * @return
	 */
	@Transactional
	public boolean saveNote(JSONObject json) {
		String content = json.getString(NOTE_CONTENT);
		if(!StringUtils.hasText(content)){
			return false;
		}
		Note note = new Note();
		if(content.length() <= 5){
			note.setTitle(content);
		}else{
			note.setTitle(content.substring(0,5));
		}
		note.setContent(content);
		note.setType(1);
		qNoteRepository.save(note);
		NoteProperties noteProperties = new NoteProperties();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		noteProperties.setCreatetime(timestamp);
		noteProperties.setModifytime(timestamp);
		noteProperties.setNote(note);
		noteProperties.setStatus(1);
		noteProperties.setOperator(WebUtil.operator());
		qNotePropertyRepository.save(noteProperties);
		try {
			templateService.writeHTML(note);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
}
