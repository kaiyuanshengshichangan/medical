package com.henglianmobile.medical.entity.doctor;
/**
 * 我是医生-私人医生-我的专属患者
 * @author Administrator
 *
 */
public class PersonalPatientListObject {
	/**编号*/
	private int Row;
	/**预约编号*/
	private int dnMakeid;
	/**预约类型*/
	private int dnMakeType;
	/**用户（医生）id*/
	private int dnUserid;
	/**起始日期*/
	private String startDate;
	/**起始时间*/
	private String startHour;
	/**截止日期*/
	private String endDate;
	/**截止时间*/
	private String endHour;
	/**其他*/
	private String dcOther;
	/**患者昵称*/
	private String dcNickName;
	/**患者真实姓名*/
	private String dcRealName;
	/**患者头像*/
	private String dcHeadImg;
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public int getRow() {
		return Row;
	}
	public void setRow(int row) {
		Row = row;
	}
	public int getDnMakeid() {
		return dnMakeid;
	}
	public void setDnMakeid(int dnMakeid) {
		this.dnMakeid = dnMakeid;
	}
	public int getDnMakeType() {
		return dnMakeType;
	}
	public void setDnMakeType(int dnMakeType) {
		this.dnMakeType = dnMakeType;
	}
	public int getDnUserid() {
		return dnUserid;
	}
	public void setDnUserid(int dnUserid) {
		this.dnUserid = dnUserid;
	}
	public String getDcOther() {
		return dcOther;
	}
	public void setDcOther(String dcOther) {
		this.dcOther = dcOther;
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
	
}
