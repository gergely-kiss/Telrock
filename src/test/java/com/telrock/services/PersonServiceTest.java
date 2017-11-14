/*
 * Copyright Telrock Communications Limited 2008 * 
 *
 * $Header:  $
 * $Revision:  $
 * $Date:  $ 
 */
package com.telrock.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.telrock.dao.DepartmentService;
import com.telrock.dao.PersonService;
import com.telrock.model.Department;
import com.telrock.model.Person;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PersonServiceTest {
	private final String NAME = "the name";
	private final String LAST_NAME = "last name";
	private final String SECOND_NAME = "last name ";

	@Autowired
	private PersonService personService;

	@Autowired
	private DepartmentService departmentService;

	@Test
	public void testSave() {
		Person p1 = new Person();
		p1.setName(NAME);
		p1.setSurname(LAST_NAME);

		Person p2 = personService.save(p1);
		p1.setName(SECOND_NAME);
		personService.save(p1);

		Assert.assertNotNull("The returned object can't be null", p2);
		Assert.assertNotNull("The id can't be null", p2.getId());

		Assert.assertTrue("The id has to be greater than 0", p2.getId() > 0);
		Assert.assertNull("The id has to be null ", p1.getId());

		Assert.assertNotEquals(p1.getName(), p2.getName());
		Assert.assertNotEquals(p1, p2);

		Assert.assertEquals(NAME, p2.getName());
		Assert.assertEquals(p1.getSurname(), p2.getSurname());
	}

	@Test
	public void testSetDefaultDepartment() {
		Person person = new Person(NAME, LAST_NAME);
		personService.setDefaultDepartment(person);
		person = personService.save(person);

		Department defaultDepartment = departmentService.getDefaultDepartment();

		Assert.assertNotNull("The department can't be null", person.getDepartment());

		Assert.assertEquals("Has to have the default name", DepartmentService.DEFAULT_NAME,
				person.getDepartment().getName());

		Assert.assertEquals(DepartmentService.DEFAULT_AREA, person.getDepartment().getArea());

		Assert.assertEquals(defaultDepartment.getId(), person.getDepartment().getId());
	}
}
