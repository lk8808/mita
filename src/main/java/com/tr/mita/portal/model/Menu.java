package com.tr.mita.portal.model;

import java.util.Date;

public class Menu {
    private Integer id;

    private String menuno;

    private String menuname;

    private Integer parentid;

    private Integer menulevel;

    private String menuurl;

    private Long appid;

    private Integer sortno;

    private String menutype;

    private Date createtime;

    private Date modifytime;

    private String creator;

    private String modifier;

    private String delflag;

    private String remark;

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuno() {
        return menuno;
    }

    public void setMenuno(String menuno) {
        this.menuno = menuno == null ? null : menuno.trim();
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getMenulevel() {
        return menulevel;
    }

    public void setMenulevel(Integer menulevel) {
        this.menulevel = menulevel;
    }

    public String getMenuurl() {
        return menuurl;
    }

    public void setMenuurl(String menuurl) {
        this.menuurl = menuurl == null ? null : menuurl.trim();
    }

    public Long getAppid() {
        return appid;
    }

    public void setAppid(Long appid) {
        this.appid = appid;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getMenutype() {
        return menutype;
    }

    public void setMenutype(String menutype) {
        this.menutype = menutype == null ? null : menutype.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuno='" + menuno + '\'' +
                ", menuname='" + menuname + '\'' +
                ", parentid=" + parentid +
                ", menulevel=" + menulevel +
                ", menuurl='" + menuurl + '\'' +
                ", appid=" + appid +
                ", sortno=" + sortno +
                ", menutype='" + menutype + '\'' +
                ", createtime=" + createtime +
                ", modifytime=" + modifytime +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", delflag='" + delflag + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}