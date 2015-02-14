package com.henglianmobile.medical.entity.patient;
/**
 * 化验单列表对象
 * @author Administrator
 *
 */
public class HuaYanDanListObject {

	private int dnID;
	private int dnUserid;
	private String dcImgPath;
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
	public String getDcImgPath() {
		return dcImgPath;
	}
	public void setDcImgPath(String dcImgPath) {
		this.dcImgPath = dcImgPath;
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
