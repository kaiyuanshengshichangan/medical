package com.henglianmobile.medical.entity.doctor;

import java.io.Serializable;

/**
 * 我是医生-我的患者对象
 * @author Administrator
 *
 */
public class MyPatientBaseInfoObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 197730900439126256L;
	private int Row;
	private int dnUserid;
	private String dcNickName;
	private String dcRealName;
	private String dcHeadImg;
	private String dcCellPhone;
	public int getRow() {
		return Row;
	}
	public void setRow(int row) {
		Row = row;
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
	public String getDcRealName() {
		return dcRealName;
	}
	public void setDcRealName(String dcRealName) {
		this.dcRealName = dcRealName;
	}
	public String getDcHeadImg() {
		return dcHeadImg;
	}
	public void setDcHeadImg(String dcHeadImg) {
		this.dcHeadImg = dcHeadImg;
	}
	public String getDcCellPhone() {
		return dcCellPhone;
	}
	public void setDcCellPhone(String dcCellPhone) {
		this.dcCellPhone = dcCellPhone;
	}
	
}
