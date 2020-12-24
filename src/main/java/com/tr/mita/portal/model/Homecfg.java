package com.tr.mita.portal.model;

public class Homecfg {
    private Long id;

    private Long userid;

    private Long menuid;

    private Integer widthcell;

    private Integer heightcell;

    private Integer sortno;

    private String delflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public Integer getWidthcell() {
        return widthcell;
    }

    public void setWidthcell(Integer widthcell) {
        this.widthcell = widthcell;
    }

    public Integer getHeightcell() {
        return heightcell;
    }

    public void setHeightcell(Integer heightcell) {
        this.heightcell = heightcell;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }
}