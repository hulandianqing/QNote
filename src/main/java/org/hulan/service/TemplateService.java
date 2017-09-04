package org.hulan.service;

import com.google.common.io.Files;
import org.hulan.model.NoteProperties;
import org.hulan.util.common.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.hulan.constant.SysConstant.VIEWS;

/**
 * 功能描述：
 * 时间：2017/6/13 18:19
 * @author ：zhaokuiqiang
 */
@Service
public class TemplateService {
	
	public static String templatePath = null;
	public static final String FIX = ".html";
	public static final String VIEW_SEPARATOR = "/";
	@Autowired
	QNoteService qNoteService;
	
	static {
		templatePath = TemplateService.class.getResource("/").getPath() + File.separator + "templates"
		+ File.separator + VIEWS;
		File file = new File(templatePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 写入文件
	 * username/20170617/*.html
	 * @param properties
	 * @return
	 * @throws IOException
	 */
	public String writeHTML(NoteProperties properties,String content,String filePath) throws IOException {
		Assert.notNull(properties,null);
		Assert.notNull(properties.getNote(),null);
		String name;
		if(StringUtils.hasText(properties.getNote().getContent())){
			name = properties.getNote().getContent();
		}else{
			name = Generate.create() + System.currentTimeMillis() + FIX;
		}
		if(filePath == null) {
			filePath = properties.getOperator().getUsername() + getFilePath();
		}
		File newFile = new File(templatePath + File.separator + filePath);
		if(!newFile.exists()){
			newFile.mkdirs();
		}
		newFile = new File(newFile.getPath()+File.separator+name);
		Files.write(content.getBytes(),newFile);
		return name;
	}
	
	/**
	 * 返回文件目录
	 * 20170613/
	 * @return
	 */
	public String getFilePath(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return VIEW_SEPARATOR + sdf.format(System.currentTimeMillis());
	}
	
	/**
	 * 检索html文件是否存在
	 * @return
	 */
	public boolean existsFile(String username,NoteProperties properties){
		Assert.hasText(username,null);
		Assert.notNull(properties,null);
		Assert.hasText(properties.getCreatetime(),null);
		String time = properties.getCreatetime();
		String path = templatePath + File.separator + username + File.separator + time + File.separator + properties.getNote().getContent();
		File htmlFile = new File(path);
		return htmlFile.exists();
	}
}
