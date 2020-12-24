package com.tr.mita.org.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tr.mita.org.dao.DepartmentDao;
import com.tr.mita.org.dao.DepposlnkDao;
import com.tr.mita.org.model.Department;
import com.tr.mita.org.model.Employee;
import com.tr.mita.org.service.IDepartmentService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
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
	public RespData getDepartmentTree() {
		RespData respData = new RespData();
		List<Map<String, Object>> nodes = getDepartmentTreeByParentid(0);
		respData.setRtdata("bizdatas", nodes);
		return respData;
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
	public RespData queryAllDepsByParentid(Map<String, Object> params) {
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
		int parentid = params.get("parentid") == null ? 0 : (int)params.get("parentid");
		if (parentid > 0) {
			params.put("parentid", parentid);
			respData.setRtdata("rows", departmentDao.queryAllDepsByParentidWithPage(params));
			respData.setRtdata("total", departmentDao.countAllDepsByParentid(params));
		} else {
			respData.setRtdata("rows", departmentDao.queryListWithPage(params));
			respData.setRtdata("total", departmentDao.count(params));
		}
		return respData;
	}

	@Override
	public RespData save(Department department) {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		try {
			Department tmp = new Department();
			tmp.setId(department.getId());
			tmp.setDepno(department.getDepno());
			if (!isUnique(tmp)) {
				respData.setRtsts(new Rtsts("200001", "部门编号不唯一！"));
				return respData;
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
				depposlnkDao.insertByDepidPosids(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
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
				departmentDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200002", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
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
