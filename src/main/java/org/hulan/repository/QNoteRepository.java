package org.hulan.repository;

import org.hulan.model.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 功能描述：
 * 时间：2017/6/11 10:22
 * @author ：zhaokuiqiang
 */
@Repository
public class QNoteRepository {
	
	@Autowired
	EntityManager entityManager;
	
	public void a(){
		String sql = "select * from operator";
		Query query = entityManager.createNativeQuery(sql, Operator.class);
		System.out.println(query.getResultList());
	}
}
