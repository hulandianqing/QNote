package org.hulan.repository;

import org.hulan.model.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 功能描述：
 * 时间：2017/6/11 10:22
 * @author ：zhaokuiqiang
 */
public interface OperatorRepository extends CrudRepository<Operator,Long>{

	Operator findByUsername(String username);
}
