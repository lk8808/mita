package com.tr.mita.org.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tr.mita.comm.exception.RespException;
import com.tr.mita.org.dao.DepartmentDao;
import com.tr.mita.org.dao.DepposlnkDao;
import com.tr.mita.org.model.Department;
import com.tr.mita.org.service.IDepartmentService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.comm.entity.Rtsts;
import com.tr.mita.comm.entity.UserObject;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private DepposlnkDao depposlnkDao;
	
	@Autowired
	private HttpSession session;

	@Override
	public List<Map<String, Object>> getDepTree() {
		return getDepartmentTreeByParentid(0);
	}

	private List<Department> queryDepartmentsLevel1() {
		return departmentDao.queryDepartmentsLevel1();
	}

	private List<Map<String, Object>> getDepartmentTreeByParentid(Integer parentid) {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		List<Department> departments = null;
		if (parentid > 0) {
			departments = departmentDao.queryDepartmentsByParentid(parentid);
		} else {
			departments = departmentDao.queryDepartmentsLevel1();
		}
		for (Department department : departments) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", department.getId());
			node.put("depno", department.getDepno());
			node.put("depname", department.getDepname());
			node.put("sortno", department.getSortno());
			node.put("parentid", department.getParentid());
			node.put("bizdata", department);
			List<Map<String, Object>> children = getDepartmentTreeByParentid(department.getId());
			node.put("children", children);
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	public Map<String, Object> queryAllDepsByParentid(Map<String, Object> params) {
		Map<String, Object> retMap = new HashMap<String, Object>();
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
		int parentid = params.get("parentid") == null ? 0 : (int)params.get("parentid");
		if (parentid > 0) {
			params.put("parentid", parentid);
			retMap.put("rows", departmentDao.queryAllDepsByParentidWithPage(params));
			retMap.put("total", departmentDao.countAllDepsByParentid(params));
		} else {
			retMap.put("rows", departmentDao.queryListWithPage(params));
			retMap.put("total", departmentDao.count(params));
		}
		return retMap;
	}

	@Override
	public Integer save(Department department) throws Exception{
		String token = request.getHeader("Token");
		Department tmp = new Department();
		tmp.setId(department.getId());
		tmp.setDepno(department.getDepno());
		if (!isUnique(tmp)) {
			throw new RespException("200001", "部门编号不唯一！");
		}
		UserObject userObject = (UserObject)redisUtil.get(token);
		if (department.getId() != null && department.getId() > 0) {
			department.setModifier(userObject.getUser().getUsername());
			department.setModifytime(new Date());
			departmentDao.update(department);
		} else {
			department.setDelflag("0");
			department.setCreator(userObject.getUser().getUsername());
			department.setCreatetime(new Date());
			departmentDao.insert(department);
		}
		//更新部门等级和部门路径
		if (department.getParentid() != null && department.getParentid() > 0) {
			Department parent = departmentDao.get(department.getParentid());
			if (parent != null) {
				int level = parent.getDeplevel();
				String deppath = parent.getDeppath();
				deppath = deppath + department.getId() + ".";
				department.setDeplevel(level + 1);
				department.setDeppath(deppath);
			}
		} else {
			String deppath = "." + department.getId() + ".";
			department.setDeplevel(1);
			department.setDeppath(deppath);
		}
		departmentDao.update(department);
		//删除旧的部门岗位
		depposlnkDao.deleteByDepid(department.getId());
		//添加部门岗位
		if (department.getPosids() != null && department.getPosids().size()>0) {
			Map<String, Object> params = new HashMap<String, Object>();
			List posidArr = department.getPosids();
			params.put("posids", posidArr);
			params.put("depid", department.getId());
			params.put("creator", userObject.getUser().getUsername());
		}
		return 0;
	}

	@Override
	public Integer del(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "删除成功！"));
		if (ids != null) {
			String[] idArr = ids.split(",");
			return departmentDao.deleteBatch(idArr);
		}
		return 0;
	}

	private boolean isUnique(Department department) {
		Integer tmpId = department.getId();
		department.setId(null);
		Department tmp = departmentDao.expand(department);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}

}
