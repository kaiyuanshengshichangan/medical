package com.henglianmobile.medical.entity.patient;

import java.io.Serializable;

/**
 * ���ǻ����е�ԤԼҽ���б����
 * @author Administrator
 *
 */
public class DoctorYuYueListObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Row;
	/**ԤԼ���*/
	private int dnMakeid;
	/**ԤԼ���ͣ�1�Һš�2סԺ��3�����ߡ�4˽��ҽ����*/
	private int dnMakeType;
	/**�û�ID*/
	private int dnUserid;
	/**�����ֻ���*/
	private String dcCellPhone;
	/**ҽ��ID*/
	private int dnDUserid;
	/**ԤԼ�۸�*/
	private float dnMakePrice;
	/**�Ƿ�ͬ�� ��0����1ͬ��2�ܾ���*/
	private int dnIsAgree;
	/**�Ƿ񸶿� ��0δ֧����1��֧����2���˿*/
	private int isPayed;
	/**����*/
	private String bedNo;
	/**�۸񣨱��ã�*/
	private int salesPercent;
	/**ԤԼ��ʼ����*/
	private String startDate;
	/**ԤԼ��ʼʱ��*/
	private String startHour;
	/**ԤԼ��������*/
	private String endDate;
	/**ԤԼ����ʱ��*/
	private String endHour;
	/**���ʱ��*/
	private String dtAddTime;
	/**ҽ���ظ�*/
	private String dcOther;
	/**ҽ���ǳ�*/
	private String dcNickName;
	/**ҽ����ʵ����*/
	private String dcRealName;
	/**ҽ��ͷ��*/
	private String dcHeadImg;
	/**ҽԺ*/
	private String dcHospital;
	/**����*/
	private String dcDepart;
	/**ҽ��ְλ*/
	private String dcPosition;
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
	
	public float getDnMakePrice() {
		return dnMakePrice;
	}
	public void setDnMakePrice(float dnMakePrice) {
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
	public String getDcPosition() {
		return dcPosition;
	}
	public void setDcPosition(String dcPosition) {
		this.dcPosition = dcPosition;
	}
	
}
