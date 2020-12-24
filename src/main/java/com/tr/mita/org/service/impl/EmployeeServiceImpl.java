package com.tr.mita.org.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tr.mita.org.dao.EmployeeDao;
import com.tr.mita.org.dao.EmpposlnkDao;
import com.tr.mita.portal.dao.UserDao;
import com.tr.mita.org.model.Employee;
import com.tr.mita.portal.model.User;
import com.tr.mita.org.service.IEmployeeService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;
import com.tr.mita.utils.DateUtil;
import com.tr.mita.utils.MD5Util;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmpposlnkDao empposlnkDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HttpSession session;

	@Override
	public Employee get(int id) {
		return employeeDao.get(id);
	}
	
	@Override
	public RespData queryEmpsByDepid(Map<String, Object> params) {
		RespData respData = new RespData();
		int depid = params.get("depid") == null ? 0 : (int)params.get("depid");
		List<Employee> bizdatas = null;
		int total = 0;
		if (depid > 0) {
			params.put("depid", depid);
			bizdatas = employeeDao.queryEmpsByDepidWithPage(params);
			total = employeeDao.countEmpsByDepid(params);
		} else {
			bizdatas = employeeDao.queryListWithPage(params);
			total = employeeDao.count(params);
		}
		for (Employee employee : bizdatas) {
			employee.setPosids(empposlnkDao.queryPosidsByEmpid(employee.getId()));
		}
		respData.setRtdata("bizdatas", bizdatas);
		respData.setRtdata("total", total);
		return respData;
	}

	@Override
	public RespData queryAllEmpsByDepid(Map<String, Object> params) {
		RespData respData = new RespData();
		int depid = params.get("depid") == null ? 0 : (int)params.get("depid");
		List<Employee> bizdatas = null;
		int total = 0;
		if (depid > 0) {
			params.put("depid", depid);
			bizdatas = employeeDao.queryAllEmpsByDepidWithPage(params);
			total = employeeDao.countAllEmpsByDepid(params);
		} else {
			bizdatas = employeeDao.queryListWithPage(params);
			total = employeeDao.count(params);
		}
		for (Employee employee : bizdatas) {
			employee.setPosids(empposlnkDao.queryPosidsByEmpid(employee.getId()));
		}
		respData.setRtdata("bizdatas", bizdatas);
		respData.setRtdata("total", total);
		return respData;
	}

	@Override
	@Transactional
	public RespData save(Employee employee) {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		try {
			Employee tmp = new Employee();
			tmp.setId(employee.getId());
			tmp.setEmpno(employee.getEmpno());
			if (!isUnique(tmp)) {
				respData.setRtsts(new Rtsts("200001", "员工号不唯一！"));
				return respData;
			}
			UserObject userObject = (UserObject)redisUtil.get(token);
			if (employee.getPhoto_ext() != null) {
				employee.setPhoto(employee.getPhoto_ext().getBytes());
			}
			if (employee.getId() != null && employee.getId() > 0) {
				employee.setModifier(userObject.getUser().getUsername());
				employee.setModifytime(new Date());
				employeeDao.update(employee);
				//更新用户
				User user = new User();
				user.setUsername(employee.getEmpno());
				user.setEmployeeid(employee.getId());
				user.setTelephone(employee.getTelephone());
				user.setModifier(userObject.getUser().getUsername());
				user.setModifytime(new Date());
				userDao.updateByEmployeeid(user);
			} else {
				employee.setDelflag("0");
				employee.setCreator(userObject.getUser().getUsername());
				employee.setCreatetime(new Date());
				employeeDao.insert(employee);
				//新增用户
				User user = new User();
				user.setEmployeeid(employee.getId());
				user.setUsername(employee.getEmpno());
				//密码默认000000
				user.setPasswd(MD5Util.md5Encode("000000"));
				user.setTelephone(employee.getTelephone());
				user.setCreator(userObject.getUser().getUsername());
				user.setCreatetime(new Date());
				user.setEnableflag("Y");
				user.setErrornum(0);
				user.setPasswdduedate(DateUtil.addDays(new Date(), -1));
				user.setDelflag("0");
				userDao.insert(user);
			}
			//删除旧的员工岗位
			empposlnkDao.deleteByEmpid(employee.getId());
			//添加员工岗位
			if (employee.getPosids() != null && employee.getPosids().size() > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				List posidArr = employee.getPosids();
				params.put("posids", posidArr);
				params.put("empid", employee.getId());
				params.put("depid", employee.getDepartmentid());
				params.put("creator", userObject.getUser().getUsername());
				empposlnkDao.insertByDepidPosids(params);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}

	@Override
	@Transactional
	public RespData del(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "删除成功！"));
		try {
			if (ids != null) {
				String[] idArr = ids.split(",");
				employeeDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200002", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	private boolean isUnique(Employee employee) {
		RespData respData = new RespData();
		Integer tmpId = employee.getId();
		employee.setId(null);
		Employee tmp = employeeDao.expand(employee);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}

}
