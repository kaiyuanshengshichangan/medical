package com.henglianmobile.medical.entity.patient;
/**
 * �ҵ�ҽ��-ҽ��������Ϣ����
 * @author Administrator
 *
 */
public class MyDoctorBaseInfoObject {
	/**ID*/
	private int Row;
	/**ԤԼ��*/
	private int dnMakeid;
	/**ҽ��ID*/
	private int dnUserid;
	/**ҽ��ID*/
	private int dnDUserid;
	/**�ǳ�*/
	private String dcNickName;
	/**��ʵ����*/
	private String dcRealName;
	/**ͷ��·��*/
	private String dcHeadImg;
	/**ҽԺ*/
	private String dcHospital;
	/**�ڿ�*/
	private String dcDepart;
	/**����*/
	private String dcActions;
	/**ְλ*/
	private String dcPosition;
	/**�Ƽ�ָ��*/
	private String dnHot;
	/**�Ƿ�ͨ����Ӻ���*/
	private int isFriend;
	
	public int getDnDUserid() {
		return dnDUserid;
	}
	public void setDnDUserid(int dnDUserid) {
		this.dnDUserid = dnDUserid;
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
	public String getDcActions() {
		return dcActions;
	}
	public void setDcActions(String dcActions) {
		this.dcActions = dcActions;
	}
	public String getDcPosition() {
		return dcPosition;
	}
	public void setDcPosition(String dcPosition) {
		this.dcPosition = dcPosition;
	}
	public String getDnHot() {
		return dnHot;
	}
	public void setDnHot(String dnHot) {
		this.dnHot = dnHot;
	}
	public int getIsFriend() {
		return isFriend;
	}
	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}
}
