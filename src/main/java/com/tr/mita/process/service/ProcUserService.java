package com.tr.mita.process.service;

import com.tr.mita.org.dao.EmployeeDao;
import com.tr.mita.org.model.Employee;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcUserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IdentityService identityService;

    @Autowired
    private EmployeeDao employeeDao;

    public void syncUser() {
        List<Employee> employeeList = employeeDao.queryAllList();
        for (Employee employee : employeeList) {
            User user = new UserEntityImpl();
            user.setId("E" + employee.getId());
            user.setDisplayName(employee.getEmpname());
            identityService.saveUser(user);
        }
    }
}
