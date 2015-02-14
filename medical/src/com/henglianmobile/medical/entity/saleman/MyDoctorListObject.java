package com.henglianmobile.medical.entity.saleman;
/**
 * 我是业务员-我的医生列表对象
 * @author Administrator
 *
 */
public class MyDoctorListObject {

	private int Row;
	private int id;
	private int userId;
	private int doctorId;
	private String addTime;
	private String dcRealName;
	private String dcHeadImg;
	private String dcPosition;
	private String dcHospital;
	private String dcDepart;
	public int getRow() {
		return Row;
	}
	public void setRow(int row) {
		Row = row;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
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
	public String getDcPosition() {
		return dcPosition;
	}
	public void setDcPosition(String dcPosition) {
		this.dcPosition = dcPosition;
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
	
}
