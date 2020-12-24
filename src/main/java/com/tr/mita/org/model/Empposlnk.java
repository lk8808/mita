package com.tr.mita.org.model;

import java.util.Date;

public class Empposlnk {
    private Integer id;

    private Integer departmentid;

    private Integer employeeid;

    private Integer positionid;

    private Date createtime;

    private String creator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getPositionid() {
        return positionid;
    }

    public void setPositionid(Integer positionid) {
        this.positionid = positionid;
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

	@Override
	public String toString() {
		return "Empposlnk [id=" + id + ", departmentid=" + departmentid
				+ ", employeeid=" + employeeid + ", positionid=" + positionid + ", createtime=" + createtime
				+ ", creator=" + creator + "]";
	}
    
}