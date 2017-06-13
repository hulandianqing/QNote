package org.hulan.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 功能描述：
 * 时间：2017/6/11 11:34
 * @author ：zhaokuiqiang
 */
@Entity
@Table(name = "note")
public class Note {
	Long id;
	String content;
	String title;
	int type;
	
	@Id
	@Column(name = "id",precision = 16)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "content",length = 500)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "title",length = 20)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "type",precision = 2)
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}
