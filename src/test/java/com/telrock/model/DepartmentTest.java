package com.telrock.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.telrock.dao.DepartmentService;
import com.telrock.model.Department;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DepartmentTest {
	private final String DEP_DEV = "Dev";
	private final String DEP_DESIGN = "Design";
	private final String LONDON = "London";

	@Autowired
	private DepartmentService departmentService;

	@Test
	public void testCompareTo() {
		Department department1 = departmentService.getOrCreateDepartment(DEP_DEV, LONDON);
		Department department2 = departmentService.getOrCreateDepartment(DEP_DESIGN, LONDON);

		Assert.assertNotNull("The returned value can't be null", department1.compareTo(department2));
		Assert.assertTrue("Has to be less than 0", department1.compareTo(department2) < 0);
		Assert.assertTrue("Has to be greater than 0", department2.compareTo(department1) > 0);

	}

	@Test
	public void testHasCode() {
		Department department1 = departmentService.getOrCreateDepartment(DEP_DEV, LONDON);
		Department department2 = departmentService.getOrCreateDepartment(DEP_DESIGN, LONDON);

		Assert.assertNotNull("The returned hash can't be null", department1.hashCode());
		Assert.assertNotEquals("Can not be equal", department1.hashCode(), department2.hashCode());
		Assert.assertNotEquals(department1.hashCode(), department2);
		Assert.assertEquals(department1.hashCode(), department1.hashCode());
	}

	@Test
	public void testEquals() {
		Department department1 = departmentService.getOrCreateDepartment(DEP_DEV, LONDON);
		Department department2 = departmentService.getOrCreateDepartment(DEP_DESIGN, LONDON);

		Assert.assertTrue("Has to be equal", department1.equals(department1));
		Assert.assertTrue("Has to be equal", department2.equals(department2));
		Assert.assertFalse(department1.equals(department2));
		Assert.assertFalse(department2.equals(department1));
		Assert.assertFalse(department1.equals(null));
		Assert.assertFalse(department1.equals(new Object()));
	}
}
