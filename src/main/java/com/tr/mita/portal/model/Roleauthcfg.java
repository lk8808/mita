package com.tr.mita.portal.model;

public class Roleauthcfg {

    private String roleauthtype;

    private Integer refid;

    private Integer departmentid;

    private Integer positionid;

    private Integer roleid;

    public String getRoleauthtype() {
        return roleauthtype;
    }

    public void setRoleauthtype(String roleauthtype) {
        this.roleauthtype = roleauthtype;
    }

    public Integer getRefid() {
        return refid;
    }

    public void setRefid(Integer refid) {
        this.refid = refid;
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
        return "Roleauthcfg{" +
                "roleauthtype='" + roleauthtype + '\'' +
                ", refid=" + refid +
                ", departmentid=" + departmentid +
                ", positionid=" + positionid +
                ", roleid=" + roleid +
                '}';
    }
}