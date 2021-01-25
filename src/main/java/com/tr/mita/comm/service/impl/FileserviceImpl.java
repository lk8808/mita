package com.tr.mita.comm.service.impl;

import com.tr.mita.base.entity.RespData;
import com.tr.mita.base.entity.Rtsts;
import com.tr.mita.base.entity.UserObject;
import com.tr.mita.comm.dao.BizfileDao;
import com.tr.mita.comm.dao.FileclassDao;
import com.tr.mita.comm.model.Bizfile;
import com.tr.mita.comm.model.Fileclass;
import com.tr.mita.comm.service.IFileService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileserviceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FileclassDao fileclassDao;

    @Autowired
    private BizfileDao bizfileDao;

    @Value("${storagePath}")
    private String storagePath;
    @Value("${storagePeriod}")
    private String storagePeriod;

    @Override
    public RespData upload(MultipartFile file) {
        RespData respData = new RespData();

        String token = request.getHeader("Token");
        String entityname = request.getParameter("entityname");
        String entityid = request.getParameter("entityid");
        String fileuuid = request.getParameter("fileuuid");
        String filter = request.getParameter("filter");
        String filename = file.getOriginalFilename();
        String filetype = filename.substring(filename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + filetype;
        //文件存储目标路径
        String deskPath = getDeskpathByEntityname(entityname);
        logger.info("deskPath:" + deskPath);
        deskPath = deskPath + File.separator + newFilename;
        logger.info("tarPath:" + deskPath);
        File desk = new File(deskPath);
        try {
            file.transferTo(desk);
            UserObject userObject = (UserObject)redisUtil.get(token);
            Bizfile bizfile = new Bizfile();
            // 获取文件分类
            if (entityname != null && !"".equals(entityname)) {
                Fileclass fileclass = new Fileclass();
                fileclass.setEntityname(entityname);
                fileclass = fileclassDao.expand(fileclass);
                bizfile.setFileclassid(fileclass.getId());
            }
            bizfile.setFilename(filename);
            bizfile.setFilepath(desk.getAbsolutePath());
            bizfile.setFilesize(file.getSize());
            bizfile.setFiletype(filetype);
            bizfile.setFileuuid(fileuuid);
            bizfile.setEntityname(entityname);
            bizfile.setEntityid(entityid);
            bizfile.setFilter(filter);
            bizfile.setCreatetime(new Date());
            bizfile.setCreator(userObject.getEmployee().getEmpname());
            bizfile.setDelflag("0");
            bizfileDao.insert(bizfile);
        } catch (IOException e) {
            e.printStackTrace();
            respData.setRtdata(new Rtsts("200001", "文件上传失败"));
        }
        return respData;
    }

    @Override
    public List<Bizfile> queryByEntity(Map<String, Object> params) {
        return bizfileDao.queryByEntity(params);
    }

    @Override
    public Integer del(Long fileid) {
        return bizfileDao.deleteById(fileid);
    }

    /**
     *  通过业务实体名获取文件存储目录
     * @param entityname
     * @return
     */
    private String getDeskpathByEntityname(String entityname) {
        String deskPath = storagePath != null ? storagePath : "../../../";
        String periodPath = "";
        if ("M".equals(storagePeriod)) {    // 月
            periodPath = new SimpleDateFormat("yyyyMM").format(new Date());
        } else if ("S".equals(storagePeriod)) { // 季
            periodPath = new SimpleDateFormat("yyyy").format(new Date()) + "S" + (Calendar.getInstance().get(Calendar.MONTH)/3 + 1);
        } else {    // 年
            periodPath = new SimpleDateFormat("yyyy").format(new Date());
        }
        deskPath = deskPath + File.separator + periodPath;
        if (entityname != null && !"".equals(entityname)) {
            deskPath = deskPath + File.separator + entityname;
        }
        File file = new File(deskPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return deskPath;
    }

}
