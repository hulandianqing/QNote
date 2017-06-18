package org.hulan.repository;

import org.hulan.model.NoteProperties;
import org.hulan.model.Operator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 功能描述：
 * 时间：2017/6/11 10:22
 * @author ：zhaokuiqiang
 */
public interface NotePropertyRepository extends CrudRepository<NoteProperties,Long>{
	
	@Query("select property from NoteProperties property where property.operator=? and status='1' order by createtime desc")
	Iterable<NoteProperties> queryByOperator(Operator operator);
}
