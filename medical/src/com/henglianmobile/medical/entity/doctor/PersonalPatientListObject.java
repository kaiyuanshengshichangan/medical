package com.henglianmobile.medical.entity.doctor;
/**
 * ����ҽ��-˽��ҽ��-�ҵ�ר������
 * @author Administrator
 *
 */
public class PersonalPatientListObject {
	/**���*/
	private int Row;
	/**ԤԼ���*/
	private int dnMakeid;
	/**ԤԼ����*/
	private int dnMakeType;
	/**�û���ҽ����id*/
	private int dnUserid;
	/**��ʼ����*/
	private String startDate;
	/**��ʼʱ��*/
	private String startHour;
	/**��ֹ����*/
	private String endDate;
	/**��ֹʱ��*/
	private String endHour;
	/**����*/
	private String dcOther;
	/**�����ǳ�*/
	private String dcNickName;
	/**������ʵ����*/
	private String dcRealName;
	/**����ͷ��*/
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
