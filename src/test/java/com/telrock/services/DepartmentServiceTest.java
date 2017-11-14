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
import com.telrock.model.Department;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DepartmentServiceTest {
	private final String DEP_DEV = "Dev";
	private final String DEP_DESIGN = "Design";
	private final String LONDON = "London";
	private final String BRIGHTON = "Brighton";
	private final String LONDON_2 = "London_2";
	private final String BRIGHTON_2 = "Brighton_2";
	@Autowired
	private DepartmentService departmentService;

	@Test
	public void testGetOrCreateDepartment() {
		Department dp1 = departmentService.getOrCreateDepartment(DEP_DEV, LONDON);
		Department dp2 = departmentService.getOrCreateDepartment(DEP_DEV, LONDON);
		Department dp3 = departmentService.getOrCreateDepartment(DEP_DESIGN, BRIGHTON);

		Assert.assertNotNull("The returned object can't be null", dp1);
		Assert.assertNotNull("The id can't be null", dp1.getId());
		Assert.assertEquals(DEP_DEV, dp1.getName());
		Assert.assertEquals(LONDON, dp1.getArea());
		Assert.assertEquals(dp1.getId(), dp2.getId());
		Assert.assertNotEquals(dp1.getId(), dp3.getId());
	}

	@Test
	public void testGetDefaultDepartment() {
		Department defaultDepartment = departmentService.getDefaultDepartment();
		Department department = departmentService.getOrCreateDepartment(DepartmentService.DEFAULT_NAME,
				DepartmentService.DEFAULT_AREA);
		Assert.assertNotNull("The returned object can't be null", defaultDepartment);
		Assert.assertNotNull("The id can't be null", defaultDepartment.getId());
		Assert.assertEquals(defaultDepartment, department);
		Assert.assertEquals(DepartmentService.DEFAULT_NAME, defaultDepartment.getName());
		Assert.assertEquals(DepartmentService.DEFAULT_AREA, defaultDepartment.getArea());
	}

	@Test
	public void testIndDepartmentsInArea() {
		createTestDepartments();

		Assert.assertTrue(!departmentService.findDepartmentsInArea(LONDON_2).isEmpty());
		Assert.assertTrue(!departmentService.findDepartmentsInArea(BRIGHTON_2).isEmpty());

		Assert.assertEquals(4, departmentService.findDepartmentsInArea(LONDON_2).size());
		Assert.assertEquals(5, departmentService.findDepartmentsInArea(BRIGHTON_2).size());
	}

	private void createTestDepartments() {
		for (int i = 1; i < 10; i++) {
			if (i % 2 == 0) {
				departmentService.getOrCreateDepartment(DEP_DEV + i, LONDON_2);
			} else {
				departmentService.getOrCreateDepartment(DEP_DEV + i, BRIGHTON_2);
			}
		}
	}
}
