package org.hulan.service;

import com.alibaba.fastjson.JSONObject;
import org.hulan.repository.OperatorRepository;
import org.hulan.repository.QNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：
 * 时间：2017/6/11 9:57
 * @author ：zhaokuiqiang
 */
@Service
public class QNoteService {
	@Autowired
	QNoteRepository qNoteRepository;
	
	public boolean saveNote(JSONObject json){
		return true;
	}
}
