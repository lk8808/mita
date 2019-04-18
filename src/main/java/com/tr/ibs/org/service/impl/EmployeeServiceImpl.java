package com.tr.ibs.org.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.entity.Rtsts;
import com.tr.ibs.entity.UserObject;
import com.tr.ibs.org.dao.EmployeeDao;
import com.tr.ibs.org.dao.EmpposlnkDao;
import com.tr.ibs.org.dao.UserDao;
import com.tr.ibs.org.model.Employee;
import com.tr.ibs.org.model.User;
import com.tr.ibs.org.service.IEmployeeService;
import com.tr.ibs.utils.DateUtil;
import com.tr.ibs.utils.MD5Util;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
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
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		//设置分页
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		//设置排序
		if (params.get("sort") == null) {
			params.put("sort", "sortno");
		}
		if (params.get("order") == null) {
			params.put("order", "asc");
		}
		int depid = params.get("depid") == null ? 0 : (int)params.get("depid");
		if (depid > 0) {
			params.put("depid", depid);
			respData.setRtdata("rows", employeeDao.queryEmpsByDepidWithPage(params));
			respData.setRtdata("total", employeeDao.countEmpsByDepid(params));
		} else {
			respData.setRtdata("rows", employeeDao.queryListWithPage(params));
			respData.setRtdata("total", employeeDao.count(params));
		}		
		return respData;
	}

	@Override
	public RespData queryAllEmpsByDepid(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		//设置分页
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		//设置排序
		if (params.get("sort") == null) {
			params.put("sort", "sortno");
		}
		if (params.get("order") == null) {
			params.put("order", "asc");
		}
		int depid = params.get("depid") == null ? 0 : (int)params.get("depid");
		if (depid > 0) {
			params.put("depid", depid);
			respData.setRtdata("rows", employeeDao.queryAllEmpsByDepidWithPage(params));
			respData.setRtdata("total", employeeDao.countAllEmpsByDepid(params));
		} else {
			respData.setRtdata("rows", employeeDao.queryListWithPage(params));
			respData.setRtdata("total", employeeDao.count(params));
		}
		System.out.println(respData.getRtdata());
		return respData;
	}

	@Override
	public RespData save(Employee employee, String depposlnkids) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		try {
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
				user.setEmail(employee.getEmail());
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
				user.setEpasswd(MD5Util.md5Encode("000000"));
				user.setEmail(employee.getEmail());
				user.setTelephone(employee.getTelephone());
				user.setCreator(userObject.getUser().getUsername());
				user.setCreatetime(new Date());
				user.setEnableflag("Y");
				user.setEsignflag("N");
				user.setImsicheckflag("N");
				user.setErrornum(0);
				user.setPasswdduedate(DateUtil.addDays(new Date(), -1));
				user.setDelflag("0");
				userDao.insert(user);
			}
			//删除旧的员工岗位
			empposlnkDao.deleteByEmpid(employee.getId());
			//添加员工岗位
			if (depposlnkids != null && !"".equals(depposlnkids)) {
				Map<String, Object> params = new HashMap<String, Object>();
				String[] depposlnkidArr = depposlnkids.split(",");
				params.put("depposlnkids", depposlnkidArr);
				params.put("employeeid", employee.getId());
				params.put("creator", userObject.getUser().getUsername());
				empposlnkDao.insertByDepposlnk(params);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("100001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}

	@Override
	public RespData del(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "删除成功！"));
		try {
			if (ids != null) {
				String[] idArr = ids.split(",");
				employeeDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("100001", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	@Override
	public boolean isUnique(Employee employee) {
		int tmpId = employee.getId();
		employee.setId(null);
		Employee tmp = employeeDao.expand(employee);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}
	
	@Override
	public Employee getMyInfo(){
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		return employeeDao.get(userObject.getUser().getEmployeeid());
	};
}
