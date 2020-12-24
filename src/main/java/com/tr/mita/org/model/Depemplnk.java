package com.tr.mita.org.model;

import java.util.Date;

public class Depemplnk {
    private Long id;

    private Long departmentid;

    private Long employeeid;

    private String mainflag;

    private Date createtime;

    private String creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Long departmentid) {
        this.departmentid = departmentid;
    }

    public Long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Long employeeid) {
        this.employeeid = employeeid;
    }

    public String getMainflag() {
        return mainflag;
    }

    public void setMainflag(String mainflag) {
        this.mainflag = mainflag == null ? null : mainflag.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
}