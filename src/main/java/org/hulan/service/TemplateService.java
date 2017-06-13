package org.hulan.service;

import com.google.common.io.Files;
import org.hulan.model.Note;
import org.hulan.util.common.WebUtil;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * 功能描述：
 * 时间：2017/6/13 18:19
 * @author ：zhaokuiqiang
 */
@Service
public class TemplateService {
	
	static String templatePath = null;
	static {
		templatePath = TemplateService.class.getResource("/").getPath() + File.separator + "templates"
		+ File.separator + "html" + File.separator;
		File file = new File(templatePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 写入文件
	 * @param note
	 * @return
	 * @throws IOException
	 */
	public File writeHTML(Note note) throws IOException {
		String name = UUID.randomUUID().toString() + System.currentTimeMillis() + ".html";
		File newFile = new File(getFilePath()+name);
		Files.write(note.getContent().getBytes(),newFile);
		return newFile;
	}
	
	/**
	 * 返回文件目录
	 * html/20170613/
	 * @return
	 */
	public String getFilePath(){
		Calendar calendar = Calendar.getInstance();
		int monday = calendar.get(Calendar.MONDAY);
		String mondayStr = null;
		if(monday++ < 10){
			mondayStr = "0" + monday;
		}else{
			mondayStr = String.valueOf(monday);
		}
		String todayFilePath = templatePath + calendar.get(Calendar.YEAR)
				+ mondayStr +calendar.get(Calendar.DATE)
				+ File.separator;
		File newFile = new File(todayFilePath);
		if(!newFile.exists()){
			newFile.mkdirs();
		}
		return todayFilePath;
	}
}
