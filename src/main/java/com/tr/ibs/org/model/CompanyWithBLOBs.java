package com.tr.ibs.org.model;

public class CompanyWithBLOBs extends Company {
    private byte[] complogo;

    private String remark;

    public byte[] getComplogo() {
        return complogo;
    }

    public void setComplogo(byte[] complogo) {
        this.complogo = complogo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}