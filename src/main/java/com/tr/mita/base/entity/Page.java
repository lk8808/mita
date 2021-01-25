package com.tr.mita.base.entity;

public class Page {

	//当前页数
	private int page;
	
	//每页行数
	private int rows;
	
	//总记录数
	private int total;
	
	public Page(){}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
