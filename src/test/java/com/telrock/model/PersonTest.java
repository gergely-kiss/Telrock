package com.telrock.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.telrock.dao.PersonService;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PersonTest {
	private final String NAME = "the name";
	private final String LAST_NAME = "last name";
	@Autowired
	private PersonService personService;

	@Test
	public void testCompareTo() {
		Person person = new Person(NAME, LAST_NAME);
		Person savedPerson = personService.save(person);
		Person savedPerson2 = personService.save(new Person(NAME, LAST_NAME));

		Assert.assertNotNull("The returned value can't be null", savedPerson.compareTo(savedPerson2));
		Assert.assertTrue("Has to be greater less than 0", savedPerson.compareTo(savedPerson2) < 0);
		Assert.assertNotEquals(savedPerson.compareTo(savedPerson2), savedPerson2.compareTo(savedPerson));
		Assert.assertEquals(0, savedPerson.compareTo(savedPerson2) + Math.abs(savedPerson.compareTo(savedPerson2)));
	}

	@Test
	public void testHasCode() {
		Person person = new Person(NAME, LAST_NAME);
		Person savedPerson = personService.save(person);
		Person savedPerson2 = personService.save(new Person(NAME, LAST_NAME));

		Assert.assertNotNull("The returned hash can't be null", savedPerson.hashCode());
		Assert.assertNotEquals(savedPerson.hashCode(), savedPerson2.hashCode());
		Assert.assertNotEquals(savedPerson.hashCode(), person);
		Assert.assertEquals(savedPerson.hashCode(), savedPerson.hashCode());
	}

	@Test
	public void testEquals() {
		Person person = new Person(NAME, LAST_NAME);
		Person savedPerson = personService.save(person);
		Person savedPerson2 = personService.save(new Person(NAME, LAST_NAME));

		Assert.assertTrue("Has to be equal", savedPerson.equals(savedPerson));
		Assert.assertTrue("Has to be equal", savedPerson2.equals(savedPerson2));
		Assert.assertFalse(savedPerson.equals(savedPerson2));
		Assert.assertFalse(savedPerson2.equals(savedPerson));
		Assert.assertFalse(savedPerson.equals(null));
		Assert.assertFalse(savedPerson.equals(new Object()));
	}
}
