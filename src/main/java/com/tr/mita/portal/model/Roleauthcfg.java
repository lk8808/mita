package com.tr.mita.portal.model;

public class Roleauthcfg {
    private String cfgtype;

    private Integer authcfgid;

    private Integer departmentid;

    private Integer positionid;

    private Integer roleid;

    public String getCfgtype() {
        return cfgtype;
    }

    public void setCfgtype(String cfgtype) {
        this.cfgtype = cfgtype == null ? null : cfgtype.trim();
    }

    public Integer getAuthcfgid() {
        return authcfgid;
    }

    public void setAuthcfgid(Integer authcfgid) {
        this.authcfgid = authcfgid;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getPositionid() {
        return positionid;
    }

    public void setPositionid(Integer positionid) {
        this.positionid = positionid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

	@Override
	public String toString() {
		return "Roleauthcfg [cfgtype=" + cfgtype + ", authcfgid=" + authcfgid + ", departmentid=" + departmentid
				+ ", positionid=" + positionid + ", roleid=" + roleid + "]";
	}
     
}