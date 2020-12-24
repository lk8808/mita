package com.tr.mita.org.model;

import java.util.Date;

public class Position {
    private Integer id;

    private String posno;

    private String posname;

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

    public String getPosno() {
        return posno;
    }

    public void setPosno(String posno) {
        this.posno = posno == null ? null : posno.trim();
    }

    public String getPosname() {
        return posname;
    }

    public void setPosname(String posname) {
        this.posname = posname == null ? null : posname.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", posno='" + posno + '\'' +
                ", posname='" + posname + '\'' +
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