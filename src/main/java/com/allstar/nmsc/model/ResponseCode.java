package com.allstar.nmsc.model;

public enum ResponseCode {

	OK("ok", 1), ERROR("error", 2), NotExist("not exist",3), EXCEPTION("Exception", 4);
	
	private int index; 
	private String name; 
	
	private ResponseCode(String name, int code){}

	//getter setter
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
