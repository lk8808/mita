package com.tr.mita.comm.service;

import com.tr.mita.base.entity.RespData;
import com.tr.mita.comm.model.Bizfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IFileService {

    public RespData upload(MultipartFile file);

    public List<Bizfile> queryByEntity(Map<String, Object> params);

    public Integer del(Long fileid);

}
