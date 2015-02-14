package com.henglianmobile.medical.entity.patient;

import java.io.Serializable;

public class HealthManageMethodListObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dnHid;
	private int dnUserid;
	private String dcContent;
	private String dtAddTime;
	private String dcHeadImg;
	private String dcNickName;
	private String dcRealName;
	private String dcHospital;
	private String dcDepart;
	private String dcActions;
	private String dcIpath;
	public int getDnHid() {
		return dnHid;
	}
	public void setDnHid(int dnHid) {
		this.dnHid = dnHid;
	}
	public int getDnUserid() {
		return dnUserid;
	}
	public void setDnUserid(int dnUserid) {
		this.dnUserid = dnUserid;
	}
	public String getDcContent() {
		return dcContent;
	}
	public void setDcContent(String dcContent) {
		this.dcContent = dcContent;
	}
	public String getDtAddTime() {
		return dtAddTime;
	}
	public void setDtAddTime(String dtAddTime) {
		this.dtAddTime = dtAddTime;
	}
	public String getDcHeadImg() {
		return dcHeadImg;
	}
	public void setDcHeadImg(String dcHeadImg) {
		this.dcHeadImg = dcHeadImg;
	}
	public String getDcNickName() {
		return dcNickName;
	}
	public void setDcNickName(String dcNickName) {
		this.dcNickName = dcNickName;
	}
	public String getDcRealName() {
		return dcRealName;
	}
	public void setDcRealName(String dcRealName) {
		this.dcRealName = dcRealName;
	}
	public String getDcHospital() {
		return dcHospital;
	}
	public void setDcHospital(String dcHospital) {
		this.dcHospital = dcHospital;
	}
	public String getDcDepart() {
		return dcDepart;
	}
	public void setDcDepart(String dcDepart) {
		this.dcDepart = dcDepart;
	}
	public String getDcActions() {
		return dcActions;
	}
	public void setDcActions(String dcActions) {
		this.dcActions = dcActions;
	}
	public String getDcIpath() {
		return dcIpath;
	}
	public void setDcIpath(String dcIpath) {
		this.dcIpath = dcIpath;
	}
	
}
