package com.henglianmobile.medical.entity;
/**
 * 我是医生中的预约就诊(住院)患者列表对象
 * @author Administrator
 *
 */
public class PatientYuYueListObject {
	private int Row;
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
	/**预约价格*/
	private int dnMakePrice;
	/**是否同意 （0否 1是）*/
	private int dnIsAgree;
	/**是否付款（0未支付，1已支付，2已退款）*/
	private int isPayed;
	/**床位号*/
	private String bedNo;
	/**价格（备用）*/
	private int salesPercent;
	/**预约开始日期*/
	private String startDate;
	/**预约开始时间*/
	private String startHour;
	/**预约截止日期*/
	private String endDate;
	/**预约截止时间*/
	private String endHour;
	/**添加时间*/
	private String dtAddTime;
	/**患者描述*/
	private String dcOther;
	/**患者昵称*/
	private String dcNickName;
	/**患者真实姓名*/
	private String dcRealName;
	/**患者头像*/
	private String dcHeadImg;
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
	public int getIsPayed() {
		return isPayed;
	}
	public void setIsPayed(int isPayed) {
		this.isPayed = isPayed;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public int getSalesPercent() {
		return salesPercent;
	}
	public void setSalesPercent(int salesPercent) {
		this.salesPercent = salesPercent;
	}
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
