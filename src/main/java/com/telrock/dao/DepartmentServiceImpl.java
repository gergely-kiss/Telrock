/*
 * Copyright Telrock Communications Limited 2008 * 
 *
 * $Header:  $
 * $Revision:  $
 * $Date:  $ 
 */
package com.telrock.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.telrock.model.Department;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private EntityManagerFactory emf;

	public Department getOrCreateDepartment(String departmentName, String area) {
		EntityManager em = emf.createEntityManager();
		Department department = null;
		TypedQuery<Department> departmentForName = em
				.createQuery("select d from Department d where d.name = ?1 and d.area = ?2", Department.class);
		departmentForName.setParameter(1, departmentName);
		departmentForName.setParameter(2, area);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			department = departmentForName.getSingleResult();
		} catch (NoResultException e) {
			department = new Department(departmentName, area);
			em.persist(department);
		}
		transaction.commit();
		return department;
	}

	public Department getDefaultDepartment() {
		return getOrCreateDepartment(DEFAULT_NAME, DEFAULT_AREA);
	}

	public List<Department> findDepartmentsInArea(String area) {
		EntityManager em = emf.createEntityManager();
		List<Department> departmentList = null;
		TypedQuery<Department> departmentForArea = em.createQuery("select d from Department d where d.area = ?1",
				Department.class);
		departmentForArea.setParameter(1, area);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		departmentList = departmentForArea.getResultList();

		transaction.commit();
		return departmentList;
	}
}