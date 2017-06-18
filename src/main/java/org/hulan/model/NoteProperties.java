package org.hulan.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 功能描述：
 * 时间：2017/6/11 11:34
 * @author ：zhaokuiqiang
 */
@Entity
@Table(name = "note_properties")
public class NoteProperties {
	Long id;
	Operator operator;
	Note note;
	int status;
	String createtime;
	String modifytime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",precision = 16,unique = true,nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oid")
	public Operator getOperator() {
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nid")
	public Note getNote() {
		return note;
	}
	
	public void setNote(Note note) {
		this.note = note;
	}
	
	@Column(name = "status",precision = 2)
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name = "createtime",length = 20)
	public String getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "modifytime",length = 20)
	public String getModifytime() {
		return modifytime;
	}
	
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
}
