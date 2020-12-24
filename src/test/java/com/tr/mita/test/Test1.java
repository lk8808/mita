package com.tr.mita.test;

import com.tr.mita.org.dao.EmployeeDao;
import com.tr.mita.org.model.Employee;
import com.tr.mita.portal.dao.UserDao;
import com.tr.mita.portal.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {
	
	@Autowired
	private EmployeeDao employeeDao;

	@Test
	public void test1() {
		Employee emp = employeeDao.get(1);
		System.out.println(emp);
	}
}
