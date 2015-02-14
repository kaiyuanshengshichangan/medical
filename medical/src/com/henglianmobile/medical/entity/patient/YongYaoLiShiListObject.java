package com.henglianmobile.medical.entity.patient;
/**
 * 患者使用过的药物对象
 * @author Administrator
 *
 */
public class YongYaoLiShiListObject {

	private int dnID;
	private int dnUserid;
	private String dcMakePast;
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
	public String getDcMakePast() {
		return dcMakePast;
	}
	public void setDcMakePast(String dcMakePast) {
		this.dcMakePast = dcMakePast;
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
