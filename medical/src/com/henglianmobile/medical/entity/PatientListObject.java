package com.henglianmobile.medical.entity;

import java.io.Serializable;
/**
 * 此类为患友圈患者对象
 * @author Administrator
 *
 */
public class PatientListObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dnTid;
	private int dnUserid;
	private String dcNickName;
	private String dcQuestion;
	private String dtAddTime;
	private int dnIsShow;
	private String dcHeadImg;
	private String dcIpath;
	public int getDnTid() {
		return dnTid;
	}
	public void setDnTid(int dnTid) {
		this.dnTid = dnTid;
	}
	public int getDnUserid() {
		return dnUserid;
	}
	public void setDnUserid(int dnUserid) {
		this.dnUserid = dnUserid;
	}
	public String getDcNickName() {
		return dcNickName;
	}
	public void setDcNickName(String dcNickName) {
		this.dcNickName = dcNickName;
	}
	public String getDcQuestion() {
		return dcQuestion;
	}
	public void setDcQuestion(String dcQuestion) {
		this.dcQuestion = dcQuestion;
	}
	public String getDtAddTime() {
		return dtAddTime;
	}
	public void setDtAddTime(String dtAddTime) {
		this.dtAddTime = dtAddTime;
	}
	public int getDnIsShow() {
		return dnIsShow;
	}
	public void setDnIsShow(int dnIsShow) {
		this.dnIsShow = dnIsShow;
	}
	public String getDcHeadImg() {
		return dcHeadImg;
	}
	public void setDcHeadImg(String dcHeadImg) {
		this.dcHeadImg = dcHeadImg;
	}
	public String getDcIpath() {
		return dcIpath;
	}
	public void setDcIpath(String dcIpath) {
		this.dcIpath = dcIpath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
