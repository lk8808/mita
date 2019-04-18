package com.tr.ibs.comm.model;

public class Dict {
	
	private String dicttypeid;

    private String dictid;
	
    private String dicttypename;

    private String dictname;

    private Integer status;

    private Integer sortno;

    private Integer rank;

    private String parentid;

    private String seqno;

    private String filter1;

    private String filter2;
    
    public String getDicttypeid() {
		return dicttypeid;
	}

	public void setDicttypeid(String dicttypeid) {
		this.dicttypeid = dicttypeid;
	}

	public String getDictid() {
		return dictid;
	}

	public void setDictid(String dictid) {
		this.dictid = dictid;
	}

	public String getDicttypename() {
        return dicttypename;
    }

    public void setDicttypename(String dicttypename) {
        this.dicttypename = dicttypename == null ? null : dicttypename.trim();
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname == null ? null : dictname.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno == null ? null : seqno.trim();
    }

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1 == null ? null : filter1.trim();
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2 == null ? null : filter2.trim();
    }
}