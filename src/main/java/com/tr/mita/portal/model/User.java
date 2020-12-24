package com.tr.mita.portal.model;

import java.util.Arrays;
import java.util.Date;

public class User {
    private Integer id;

    private Integer employeeid;

    private String username;

    private String passwd;

    private String telephone;

    private String desktopstyle;

    private String enableflag;

    private Date passwdduedate;

    private Integer errornum;

    private Date logintime;

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

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getDesktopstyle() {
        return desktopstyle;
    }

    public void setDesktopstyle(String desktopstyle) {
        this.desktopstyle = desktopstyle == null ? null : desktopstyle.trim();
    }

    public String getEnableflag() {
        return enableflag;
    }

    public void setEnableflag(String enableflag) {
        this.enableflag = enableflag == null ? null : enableflag.trim();
    }

    public Date getPasswdduedate() {
        return passwdduedate;
    }

    public void setPasswdduedate(Date passwdduedate) {
        this.passwdduedate = passwdduedate;
    }

    public Integer getErrornum() {
        return errornum;
    }

    public void setErrornum(Integer errornum) {
        this.errornum = errornum;
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
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
		return "User [id=" + id + ", employeeid=" + employeeid + ", username=" + username + ", passwd=" + passwd
				+ ", telephone=" + telephone + ", desktopstyle=" + desktopstyle
				+ ", enableflag=" + enableflag + ", passwdduedate=" + passwdduedate + ", errornum=" + errornum
				+ ", logintime=" + logintime + ", createtime=" + createtime + ", modifytime=" + modifytime
				+ ", creator=" + creator + ", modifier=" + modifier + ", delflag=" + delflag + "]";
	}
	
}