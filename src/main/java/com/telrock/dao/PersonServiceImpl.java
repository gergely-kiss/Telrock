package com.telrock.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.telrock.model.Person;

/**
 * DAO implementation for Person entity
 * 
 * @author telrock.com
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private DepartmentService departmentService;

	public Person save(Person person) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		Person newPerson = em.merge(person);
		transaction.commit();
		return newPerson;
	}

	public void setDefaultDepartment(Person person) {
		person.setDepartment(departmentService.getDefaultDepartment());
	}
}