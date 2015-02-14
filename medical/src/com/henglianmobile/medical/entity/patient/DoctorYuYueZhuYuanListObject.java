package com.henglianmobile.medical.entity.patient;
/**
 * 我是患者中的预约住院医生列表对象
 * @author Administrator
 *
 */
public class DoctorYuYueZhuYuanListObject {
	/**预约编号*/
	private int dnMakeid;
	/**预约类型（1挂号、2住院、3、热线、4私人医生）*/
	private int dnMakeType;
	/**用户ID*/
	private int dnUserid;
	/**患者手机号*/
	private String dcCellPhone;
	/**医生ID*/
	private int dnDUserid;
	/**医院*/
	private String dcHospital;
	/**科室*/
	private String dcDepart;
	/**预约价格*/
	private int dnMakePrice;
	/**是否同意 （0申请1同意2拒绝）*/
	private int dnIsAgree;
	/**床位号*/
	private String bedNo;
	/**价格（备用）*/
	private int dnPrice;
	/**预约日期*/
	private String dcMakeTime;
	/**预约开始时间*/
	private String startHour;
	/**预约结束时间*/
	private String endHour;
	/**添加时间*/
	private String dtAddTime;
	/**医生回复-床位号*/
	private String dcOther;
	/**医生昵称*/
	private String dcNickName;
	/**医生真实姓名*/
	private String dcRealName;
	/**医生头像*/
	private String dcHeadImg;
	/**医生职位*/
	private String dcPosition;
	
	
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getDcPosition() {
		return dcPosition;
	}
	public void setDcPosition(String dcPosition) {
		this.dcPosition = dcPosition;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
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
	public String getDcCellPhone() {
		return dcCellPhone;
	}
	public void setDcCellPhone(String dcCellPhone) {
		this.dcCellPhone = dcCellPhone;
	}
	public int getDnDUserid() {
		return dnDUserid;
	}
	public void setDnDUserid(int dnDUserid) {
		this.dnDUserid = dnDUserid;
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
	public int getDnMakePrice() {
		return dnMakePrice;
	}
	public void setDnMakePrice(int dnMakePrice) {
		this.dnMakePrice = dnMakePrice;
	}
	public int getDnIsAgree() {
		return dnIsAgree;
	}
	public void setDnIsAgree(int dnIsAgree) {
		this.dnIsAgree = dnIsAgree;
	}
	public int getDnPrice() {
		return dnPrice;
	}
	public void setDnPrice(int dnPrice) {
		this.dnPrice = dnPrice;
	}
	public String getDcMakeTime() {
		return dcMakeTime;
	}
	public void setDcMakeTime(String dcMakeTime) {
		this.dcMakeTime = dcMakeTime;
	}
	public String getDtAddTime() {
		return dtAddTime;
	}
	public void setDtAddTime(String dtAddTime) {
		this.dtAddTime = dtAddTime;
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
