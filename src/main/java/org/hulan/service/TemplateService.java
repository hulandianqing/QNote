package org.hulan.service;

import com.google.common.io.Files;
import org.hulan.model.NoteProperties;
import org.hulan.util.common.Generate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * 功能描述：
 * 时间：2017/6/13 18:19
 * @author ：zhaokuiqiang
 */
@Service
public class TemplateService {
	
	public static String templatePath = null;
	static {
		templatePath = TemplateService.class.getResource("/").getPath() + File.separator + "templates"
		+ File.separator + "views" + File.separator;
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
	public String writeHTML(NoteProperties properties) throws IOException {
		Assert.notNull(properties,null);
		Assert.notNull(properties.getNote(),null);
		Assert.hasText(properties.getNote().getContent(),null);
		String name = Generate.create() + System.currentTimeMillis() + ".html";
		String filePath = properties.getOperator().getUsername() + getFilePath();
		File newFile = new File(templatePath + filePath);
		if(!newFile.exists()){
			newFile.mkdirs();
		}
		newFile = new File(newFile.getPath()+File.separator+name);
		StringBuilder fixHTML = new StringBuilder();
		fixHTML.append("<div>").append(properties.getNote().getContent()).append("</div>");
		Files.write(fixHTML.toString().getBytes(),newFile);
		return name;
	}
	
	/**
	 * 返回文件目录
	 * 20170613/
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
		String todayFilePath = calendar.get(Calendar.YEAR) + "-"
				+ mondayStr + "-" +calendar.get(Calendar.DATE)
				+ File.separator;
		return todayFilePath;
	}
	
	/**
	 * 检索html文件是否存在
	 * @return
	 */
	public boolean existsFile(String username,NoteProperties properties){
		Assert.hasText(username,null);
		Assert.notNull(properties,null);
		Assert.hasText(properties.getCreatetime(),null);
		String time = properties.getCreatetime().split(" ")[0];
		String path = templatePath + username + File.separator + time + File.separator + properties.getNote().getContent();
		File htmlFile = new File(path);
		return htmlFile.exists();
	}
}
