package org.hulan.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 功能描述：
 * 时间：2017/6/11 11:34
 * @author ：zhaokuiqiang
 * CREATE TABLE operator
(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    username VARCHAR(50),
    password VARCHAR(200),
    role INT,
    createtime DATETIME,
    uid INTEGER,
    CONSTRAINT operator_user_id_fk FOREIGN KEY (uid) REFERENCES user
)
 */
@Entity
@Table(name = "operator")
public class Operator {
	Long id;
	User user;
	String username;
	String password;
	int role;
	Timestamp createtime;
	Integer status;
	
	@Id
	@Column(name = "id",precision = 16)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "username",length = 50)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "password",length = 200)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "role",precision = 2)
	public int getRole() {
		return role;
	}
	
	public void setRole(int role) {
		this.role = role;
	}
	
	@Column(name = "createtime",length = 20)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "status",precision = 2)
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
}
