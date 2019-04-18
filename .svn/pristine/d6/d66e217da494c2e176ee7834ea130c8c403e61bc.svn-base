package com.tr.ibs.org.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.tr.ibs.org.dao.DepartmentDao;
import com.tr.ibs.org.dao.DepposlnkDao;
import com.tr.ibs.org.model.Department;
import com.tr.ibs.org.service.IDepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private DepposlnkDao depposlnkDao;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public Department get(int id) {
		return departmentDao.get(id);
	}

	@Override
	public List<Department> queryDepartmentsLevel1() {
		return departmentDao.queryDepartmentsLevel1();
	}

	@Override
	public List<Department> queryDepartmentsByParentid(int parentid) {
		return departmentDao.queryDepartmentsByParentid(parentid);
	}

	@Override
	public List<Map<String, Object>> getDepartmentTree() {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", 0);
		root.put("text", "组织机构");
		root.put("children", getDepartmentTreeByParentid(0));
		nodes.add(root);
		return nodes;
	}

	@Override
	public List<Map<String, Object>> getDepartmentTreeByParentid(int parentid) {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		List<Department> departments = null;
		if (parentid > 0) {
			departments = departmentDao.queryDepartmentsByParentid(parentid);
		} else {
			departments = departmentDao.queryDepartmentsLevel1();
		}
		for (Department department : departments) {
			Map<String, Object> node = new HashMap<String, Object>();
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("id", department.getId());
			node.put("id", department.getId());
			node.put("text", department.getDepname());
			node.put("attributes", attributes);
			List<Map<String, Object>> children = getDepartmentTreeByParentid(department.getId());
			if (children != null && children.size() > 0) {
				if (department.getParentid() != null && department.getParentid() > 0) {
					node.put("state", "closed");
				}
				node.put("children", children);
			}
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
	public RespData save(Department department, String posids) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		try {
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
				Department parent = get(department.getParentid());
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
			if (posids != null && !"".equals(posids)) {
				Map<String, Object> params = new HashMap<String, Object>();
				String[] posidArr = posids.split(",");
				params.put("posids", posidArr);
				params.put("depid", department.getId());
				params.put("creator", userObject.getUser().getUsername());
				depposlnkDao.insertByDepidPosids(params);
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
				departmentDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("100001", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	@Override
	public boolean isUnique(Department department) {
		int tmpId = department.getId();
		department.setId(null);
		Department tmp = departmentDao.expand(department);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}

}
