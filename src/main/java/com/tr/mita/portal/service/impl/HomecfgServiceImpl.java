package com.tr.mita.portal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;
import com.tr.mita.portal.dao.HomecfgDao;
import com.tr.mita.portal.model.Homecfg;
import com.tr.mita.portal.service.IHomecfgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HomecfgServiceImpl implements IHomecfgService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HomecfgDao homecfgDao;
	
	@Autowired
	private HttpSession session;
	
	public List<Map<String, Object>> queryMyhomecfg(){
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		return homecfgDao.queryMyhomecfg(userObject.getUser().getId().toString());
	};
	
	@Override
	public RespData saveHomecfg(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		try {
			List<Map<String, Object>> list = (ArrayList<Map<String, Object>>)params.get("homesetcfgs");
			UserObject userObject = (UserObject)session.getAttribute("userObject");
			List<Homecfg> homesetcfgs = new ArrayList<Homecfg>();
			for (Map<String, Object> map : list) {
				Homecfg homecfg = new Homecfg();
				homecfg.setMenuid(map.get("menuid") == null || "".equals((String)map.get("menuid"))? null : Long.valueOf((String)map.get("menuid")));
				homecfg.setUserid(userObject.getUser().getId().longValue());
				homecfg.setWidthcell(map.get("widthcell") == null || "".equals((String)map.get("widthcell"))? null : Integer.valueOf((String)map.get("widthcell")));
				homecfg.setHeightcell(map.get("heightcell") == null || "".equals((String)map.get("heightcell"))? null : Integer.valueOf((String)map.get("heightcell")));
				homecfg.setDelflag("0");
				homecfg.setSortno(map.get("sortno") == null || "".equals((String)map.get("sortno"))? null : Integer.valueOf((String)map.get("sortno")) );
				homesetcfgs.add(homecfg);
			}
			//删除旧关联
			homecfgDao.delHomecfgByUserid(userObject.getUser().getId().toString());
			//新增
			homecfgDao.insertBatch(homesetcfgs);
			
		} catch (Exception e) {
			e.printStackTrace();
			respData.setRtsts(new Rtsts("100001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}
	
}
