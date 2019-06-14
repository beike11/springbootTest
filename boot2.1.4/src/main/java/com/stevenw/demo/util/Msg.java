package com.stevenw.demo.util;

public class Msg {
	/**
	 * 申请书件名
	 */
	private String enventName;
	/**
	 * 申请书种类
	 */
	private String formName;
	/**
	 * 申请书no
	 */
	private Integer formNo;
	/**
	 * 申请者名
	 */
	private String entryUser;

	public String getEnventName() {
		return enventName;
	}

	public void setEnventName(String enventName) {
		this.enventName = enventName;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Integer getFormNo() {
		return formNo;
	}

	public void setFormNo(Integer formNo) {
		this.formNo = formNo;
	}

	public String getEntryUser() {
		return entryUser;
	}

	public void setEntryUser(String entryUser) {
		this.entryUser = entryUser;
	}
}
