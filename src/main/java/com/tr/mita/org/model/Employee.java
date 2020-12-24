package com.tr.mita.org.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Employee {
    private Integer id;

    private String empno;

    private String empname;

    private String idnum;

    private String gender;

    private Date birthday;

    private String telephone;

    private Integer departmentid;

    private String depname;

    private String empsts;

    private Integer sortno;

    private Date createtime;

    private Date modifytime;

    private String creator;

    private String modifier;

    private String delflag;

    private byte[] photo;
    
    private MultipartFile photo_ext;

    List<Integer> posids;

    public MultipartFile getPhoto_ext() {
		return photo_ext;
	}

	public void setPhoto_ext(MultipartFile photo_ext) {
		this.photo_ext = photo_ext;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno == null ? null : empno.trim();
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum == null ? null : idnum.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getEmpsts() {
        return empsts;
    }

    public void setEmpsts(String empsts) {
        this.empsts = empsts == null ? null : empsts.trim();
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<Integer> getPosids() {
        return posids;
    }

    public void setPosids(List<Integer> posids) {
        this.posids = posids;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empno='" + empno + '\'' +
                ", empname='" + empname + '\'' +
                ", idnum='" + idnum + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", telephone='" + telephone + '\'' +
                ", departmentid=" + departmentid +
                ", depname='" + depname + '\'' +
                ", empsts='" + empsts + '\'' +
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