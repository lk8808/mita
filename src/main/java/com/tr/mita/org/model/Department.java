package com.tr.mita.org.model;

import java.util.Date;
import java.util.List;

public class Department {
    private Integer id;

    private String depno;

    private String depname;

    private String remark;

    private String address;

    private String workphone;

    private Integer parentid;

    private String deppath;

    private Integer deplevel;

    private Integer managerid;

    private String managername;

    private Integer principalid;

    private String principal;

    private String deptype;

    private String virtualflag;

    private Integer sortno;

    private Date createtime;

    private Date modifytime;

    private String creator;

    private String modifier;

    private String delflag;

    List<Integer> posids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepno() {
        return depno;
    }

    public void setDepno(String depno) {
        this.depno = depno == null ? null : depno.trim();
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname == null ? null : depname.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getWorkphone() {
        return workphone;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone == null ? null : workphone.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getDeppath() {
        return deppath;
    }

    public void setDeppath(String deppath) {
        this.deppath = deppath == null ? null : deppath.trim();
    }

    public Integer getDeplevel() {
        return deplevel;
    }

    public void setDeplevel(Integer deplevel) {
        this.deplevel = deplevel;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername == null ? null : managername.trim();
    }

    public Integer getPrincipalid() {
        return principalid;
    }

    public void setPrincipalid(Integer principalid) {
        this.principalid = principalid;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal == null ? null : principal.trim();
    }

    public String getDeptype() {
        return deptype;
    }

    public void setDeptype(String deptype) {
        this.deptype = deptype == null ? null : deptype.trim();
    }

    public String getVirtualflag() {
        return virtualflag;
    }

    public void setVirtualflag(String virtualflag) {
        this.virtualflag = virtualflag == null ? null : virtualflag.trim();
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public List<Integer> getPosids() {
        return posids;
    }

    public void setPosids(List<Integer> posids) {
        this.posids = posids;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", depno='" + depno + '\'' +
                ", depname='" + depname + '\'' +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                ", workphone='" + workphone + '\'' +
                ", parentid=" + parentid +
                ", deppath='" + deppath + '\'' +
                ", deplevel=" + deplevel +
                ", managerid=" + managerid +
                ", managername='" + managername + '\'' +
                ", principalid=" + principalid +
                ", principal='" + principal + '\'' +
                ", deptype='" + deptype + '\'' +
                ", virtualflag='" + virtualflag + '\'' +
                ", sortno=" + sortno +
                ", createtime=" + createtime +
                ", modifytime=" + modifytime +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", delflag='" + delflag + '\'' +
                ", posids=" + posids +
                '}';
    }
}