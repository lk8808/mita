package com.tr.mita.portal.model;

import java.util.Date;

public class Application {
    private Integer id;

    private String appcode;

    private String appname;

    private String apptype;

    private String url;

    private String icon;

    private String remark;

    private Integer sortno;

    private Date createtime;

    private Date modifytime;

    private String creator;

    private String modifier;

    private String delflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode == null ? null : appcode.trim();
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype == null ? null : apptype.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", appcode='" + appcode + '\'' +
                ", appname='" + appname + '\'' +
                ", apptype='" + apptype + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", remark='" + remark + '\'' +
                ", sortno=" + sortno +
                ", createtime=" + createtime +
                ", modifytime=" + modifytime +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", delflag='" + delflag + '\'' +
                '}';
    }
}