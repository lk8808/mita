package com.tr.mita.entity;

import com.tr.mita.org.model.Employee;
import com.tr.mita.portal.model.User;

public class UserObject {

	private User user;
	private Employee employee ;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserObject [user=" + user + ", employee=" + employee + "]";
	}
	
}
