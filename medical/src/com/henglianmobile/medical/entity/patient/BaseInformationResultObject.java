package com.henglianmobile.medical.entity.patient;

public class BaseInformationResultObject {
	private int dnID;
	private int dnUserid;
	private String dcInfo;
	private String dtAddTime;
	private String dtEditTime;
	public int getDnID() {
		return dnID;
	}
	public void setDnID(int dnID) {
		this.dnID = dnID;
	}
	public int getDnUserid() {
		return dnUserid;
	}
	public void setDnUserid(int dnUserid) {
		this.dnUserid = dnUserid;
	}
	public String getDcInfo() {
		return dcInfo;
	}
	public void setDcInfo(String dcInfo) {
		this.dcInfo = dcInfo;
	}
	public String getDtAddTime() {
		return dtAddTime;
	}
	public void setDtAddTime(String dtAddTime) {
		this.dtAddTime = dtAddTime;
	}
	public String getDtEditTime() {
		return dtEditTime;
	}
	public void setDtEditTime(String dtEditTime) {
		this.dtEditTime = dtEditTime;
	}
}
