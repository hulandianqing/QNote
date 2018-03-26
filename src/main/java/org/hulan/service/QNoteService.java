package org.hulan.service;

import com.alibaba.fastjson.JSONObject;
import org.hulan.constant.State;
import org.hulan.constant.SysConstant;
import org.hulan.model.Note;
import org.hulan.model.NoteProperties;
import org.hulan.model.Operator;
import org.hulan.repository.NotePropertyRepository;
import org.hulan.repository.NoteRepository;
import org.hulan.util.common.DateUtil;
import org.hulan.util.common.Generate;
import org.hulan.util.common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.sqlite.date.DateFormatUtils;

import java.io.IOException;
import java.util.List;

import static org.hulan.constant.SysConstant.*;

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
	@Autowired
	TransactionTemplate transactionTemplate;
	public static final String FIX = ".html";
	
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
	 * 如果传入pid修改note
	 * @param json
	 * @return
	 */
	public boolean saveNote(JSONObject json) {
		String content = json.getString(NOTE_CONTENT);
		String title = json.getString(NOTE_TITLE);
		if(!StringUtils.hasText(content)){
			return false;
		}
		if(title == null){
			return false;
		}
		if(title.length() > 30){
			return false;
		}
		String pid = json.getString(NOTE_PID);
		NoteProperties noteProperties = null;
		Note note = null;
		String time = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd hh:mm:ss");
		if(StringUtils.hasText(pid)){
			noteProperties = qNotePropertyRepository.findOne(Long.valueOf(pid));
			if(!noteProperties.getOperator().getUsername().equals(WebUtil.operator().getUsername())
						|| SysConstant.NOMAL != noteProperties.getStatus()){
				return false;
			}
		}
		String filePath;
		Operator operator = WebUtil.operator();
		if(noteProperties == null){
			//新增
			noteProperties = new NoteProperties();
			note = new Note();
			note.setType(1);
			noteProperties.setCreatetime(time.split(" ")[0]);
			noteProperties.setNote(note);
			filePath = operator.getUsername() + templateService.getFilePath();
		}else{
			//修改
			note = noteProperties.getNote();
			filePath = getNotePath(noteProperties);
		}
		note.setTitle(title);
		noteProperties.setModifytime(time);
		noteProperties.setStatus(1);
		noteProperties.setOperator(operator);
		String name;
		if(StringUtils.hasText(noteProperties.getNote().getContent())){
			name = noteProperties.getNote().getContent();
		}else{
			name = Generate.create() + DateUtil.formateNowTime() + FIX;
		}
		try {
			String file = templateService.writeHTML(content,name,filePath);
			Note finalNote = note;
			NoteProperties finalNoteProperties = noteProperties;
			transactionTemplate.execute(status -> {
				finalNote.setContent(file);
				qNoteRepository.save(finalNote);
				qNotePropertyRepository.save(finalNoteProperties);
				return true;
			});
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
	
	/**
	 * qnote权限认证
	 * @param pid
	 * @return
	 */
	public NoteProperties authQnote(String pid){
		if(StringUtils.hasText(pid)){
			NoteProperties noteProperties = qNotePropertyRepository.findOne(Long.parseLong(pid));
			if(noteProperties != null){
				if(noteProperties.getOperator().getUsername().equals(WebUtil.operator().getUsername())
						&& SysConstant.NOMAL == noteProperties.getStatus()){
					return noteProperties;
				}
			}
		}
		return null;
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
	
	/**
	 * 删除note
	 * @param pid
	 * @return
	 */
	@Transactional
	public State.StateWrapper deleteQnote(String pid){
		if(StringUtils.hasText(pid)){
			NoteProperties noteProperties = qNotePropertyRepository.findOne(Long.parseLong(pid));
			if(noteProperties != null){
				if(noteProperties.getOperator().getUsername().equals(WebUtil.operator().getUsername())
						&& SysConstant.NOMAL == noteProperties.getStatus()){
					noteProperties.setStatus(SysConstant.ERROR);
					noteProperties.setModifytime(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
					qNotePropertyRepository.save(noteProperties);
					return State.SUCCESS;
				}
			}
		}
		return State.ERROR;
	}
	/**
	 * 获取指定note路径
	 * @param properties
	 * @return
	 */
	public String getNotePath(NoteProperties properties){
		Operator operator;
		Assert.notNull(properties,"");
		try {
			operator = properties.getOperator();
		} catch(Exception e) {
			operator = WebUtil.operator();
		}
		return operator.getUsername() + TemplateService.VIEW_SEPARATOR + properties.getCreatetime();
	}
}
