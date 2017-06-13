package org.hulan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能描述：
 * 时间：2017/6/11 11:34
 * @author ：zhaokuiqiang
 * CREATE TABLE user
(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name NVARCHAR(20),
    sex INT(2),
    mail VARCHAR(20),
    tel INT(20),
    img VARCHAR(50)
)
 */
@Entity
@Table(name = "user")
public class User {
	Long id;
	String name;
	Integer sex;
	String mail;
	String tell;
	String img;
	
	@Id
	@Column(name = "id",precision = 16)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name",length = 20)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "sex",precision = 2)
	public Integer getSex() {
		return sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Column(name = "mail",length = 20)
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Column(name = "tell",length = 20)
	public String getTell() {
		return tell;
	}
	
	public void setTell(String tell) {
		this.tell = tell;
	}
	
	@Column(name = "img",length = 50)
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
}
