package com.tr.ibs.org.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.entity.Rtsts;
import com.tr.ibs.org.dao.DepartmentDao;
import com.tr.ibs.org.dao.DepposlnkDao;
import com.tr.ibs.org.model.Department;
import com.tr.ibs.org.model.Depposlnk;
import com.tr.ibs.org.service.IDepposlnkService;

@Service
@Transactional
public class DepposlnkServiceImpl implements IDepposlnkService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DepposlnkDao depposlnkDao;
	
	@Autowired
	private DepartmentDao departmentDao;

	@Override
	public List<Depposlnk> queryByDepid(Integer depid) {	
		System.out.println(depid);
		return depposlnkDao.queryByDepid(depid);
	}

	@Override
	public RespData queryByDepid_ext(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		//设置分页
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		respData.setRtdata("rows", depposlnkDao.queryByDepidWithPage_ext(params));
		respData.setRtdata("total", depposlnkDao.countByDepid(params));
		return respData;
	}
	
	public List<Map<String, Object>> getDepposTree() {
		return getDepposTreeByDepid(0);
	}

	@Override
	public List<Map<String, Object>> getDepposTreeByDepid(Integer depid) {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		List<Department> departments = null;
		if (depid > 0) {
			departments = departmentDao.queryDepartmentsByParentid(depid);
		} else {
			departments = departmentDao.queryDepartmentsLevel1();
		}
		//添加部门节点
		for (Department department : departments) {
			Map<String, Object> node = new HashMap<String, Object>();
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("id", department.getId());
			attributes.put("type", "DEP");
			node.put("id", department.getId());
			node.put("text", department.getDepname());
			node.put("attributes", attributes);
			List<Map<String, Object>> children = getDepposTreeByDepid(department.getId());
			if (children != null && children.size() > 0) {
				if (department.getParentid() != null && department.getParentid() > 0) {
					node.put("state", "closed");
				}
				node.put("children", children);
			}
			nodes.add(node);
		}
		//添加岗位节点
		if(depid > 0) {
			List<Map<String, Object>> depposlnks = depposlnkDao.queryByDepid_ext(depid);
			for (Map<String, Object> depposlnk : depposlnks) {
				Map<String, Object> node = new HashMap<String, Object>();
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("id", depposlnk.get("id"));
				attributes.put("departmentid", depposlnk.get("departmentid"));
				attributes.put("positionid", depposlnk.get("positionid"));
				attributes.put("type", "DEPPOS");
				node.put("id", depposlnk.get("id"));
				node.put("text", depposlnk.get("depname") + "-" + depposlnk.get("posname"));
				node.put("attributes", attributes);
				nodes.add(node);
			}
		}
		return nodes;
	}
}
