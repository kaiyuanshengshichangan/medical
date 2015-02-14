package com.henglianmobile.medical.entity;

import java.io.Serializable;
/**
 * �û���Ϣ�������
 * @author Administrator
 *
 */
public class UserInfoDetailObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7338003500034623846L;
	/**�û�ID*/
	private int dnUserid;
	/**�û���*/
	private String dcUserName;
	/**�û��ǳ�*/
	private String dcNickName;
	/**�û����� 1 ҽԺ 2ҽ�� 3��ͨ��Ա 4ҵ��Ա*/
	private int dnType;
	/**��ʵ����*/
	private String dcRealName;
	/**�ֻ���*/
	private String dcCellPhone;
	/**��ϵ�绰*/
	private String dcPhone;
	/**QQ��*/
	private String dcTencentQQ;
	/**΢�ź�*/
	private String dcWeChat;
	/**����*/
	private String dcEmail;
	/**ͷ��·��*/
	private String dcHeadImg;
	/**�Ա� 0Ů 1��*/
	private int dnSex;
	/**����*/
	private int dnAge;
	/**ǩ��*/
	private String dcSign;
	/**���֤����*/
	private String dcIDCard;
	/**����(ҽ��)*/
	private String dcActions;
	/**ҽԺ*/
	private String dcHospital;
	/**ҽԺ�ȼ�*/
	private String hospitalGrade;
	/**����*/
	private String dcDepart;
	/**��ҽ�ʸ�ְλ��*/
	private String dcPosition;
	/**��ҽ֤(ҽ��)*/
	private String dcDoctorCart;
	/**��ע*/
	private String dcContent;
	/**ʣ����*/
	private int dfMoney;
	/**�Ƿ���ְ֤ҵ�ʸ�֤�� 1�� 0��*/
	private int dnIsValiJob;
	/**�Ƿ���ֻ��� 0�� 1��*/
	private int dnIsValiPhone;
	/**��������¼���ͣ�QQ�����ˡ�΢�ţ�*/
	private String dcThirdpartyType;
	/**��������¼id*/
	private int dnThirdpartyID;
	/**ע��ʱ��*/
	private String dtAddTime;
	/**��¼ʱ��*/
	private String dtLoginTime;
	/**��ַ*/
	private String dcAddress;
	/**����ͼƬ*/
	private String dcBackImg;
	/**�Ƽ�ָ��*/
	private String dnHot;
	
	
	public String getHospitalGrade() {
		return hospitalGrade;
	}
	public void setHospitalGrade(String hospitalGrade) {
		this.hospitalGrade = hospitalGrade;
	}
	public String getDcBackImg() {
		return dcBackImg;
	}
	public void setDcBackImg(String dcBackImg) {
		this.dcBackImg = dcBackImg;
	}
	public String getDnHot() {
		return dnHot;
	}
	public void setDnHot(String dnHot) {
		this.dnHot = dnHot;
	}
	public String getDcAddress() {
		return dcAddress;
	}
	public void setDcAddress(String dcAddress) {
		this.dcAddress = dcAddress;
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
	public int getDnUserid() {
		return dnUserid;
	}
	public void setDnUserid(int dnUserid) {
		this.dnUserid = dnUserid;
	}
	public String getDcUserName() {
		return dcUserName;
	}
	public void setDcUserName(String dcUserName) {
		this.dcUserName = dcUserName;
	}
	public String getDcNickName() {
		return dcNickName;
	}
	public void setDcNickName(String dcNickName) {
		this.dcNickName = dcNickName;
	}
	public int getDnType() {
		return dnType;
	}
	public void setDnType(int dnType) {
		this.dnType = dnType;
	}
	public String getDcRealName() {
		return dcRealName;
	}
	public void setDcRealName(String dcRealName) {
		this.dcRealName = dcRealName;
	}
	public String getDcCellPhone() {
		return dcCellPhone;
	}
	public void setDcCellPhone(String dcCellPhone) {
		this.dcCellPhone = dcCellPhone;
	}
	public String getDcPhone() {
		return dcPhone;
	}
	public void setDcPhone(String dcPhone) {
		this.dcPhone = dcPhone;
	}
	public String getDcTencentQQ() {
		return dcTencentQQ;
	}
	public void setDcTencentQQ(String dcTencentQQ) {
		this.dcTencentQQ = dcTencentQQ;
	}
	public String getDcWeChat() {
		return dcWeChat;
	}
	public void setDcWeChat(String dcWeChat) {
		this.dcWeChat = dcWeChat;
	}
	public String getDcEmail() {
		return dcEmail;
	}
	public void setDcEmail(String dcEmail) {
		this.dcEmail = dcEmail;
	}
	public String getDcHeadImg() {
		return dcHeadImg;
	}
	public void setDcHeadImg(String dcHeadImg) {
		this.dcHeadImg = dcHeadImg;
	}
	public int getDnSex() {
		return dnSex;
	}
	public void setDnSex(int dnSex) {
		this.dnSex = dnSex;
	}
	public int getDnAge() {
		return dnAge;
	}
	public void setDnAge(int dnAge) {
		this.dnAge = dnAge;
	}
	public String getDcSign() {
		return dcSign;
	}
	public void setDcSign(String dcSign) {
		this.dcSign = dcSign;
	}
	public String getDcIDCard() {
		return dcIDCard;
	}
	public void setDcIDCard(String dcIDCard) {
		this.dcIDCard = dcIDCard;
	}
	public String getDcActions() {
		return dcActions;
	}
	public void setDcActions(String dcActions) {
		this.dcActions = dcActions;
	}
	public String getDcDoctorCart() {
		return dcDoctorCart;
	}
	public void setDcDoctorCart(String dcDoctorCart) {
		this.dcDoctorCart = dcDoctorCart;
	}
	public String getDcContent() {
		return dcContent;
	}
	public void setDcContent(String dcContent) {
		this.dcContent = dcContent;
	}
	public int getDfMoney() {
		return dfMoney;
	}
	public void setDfMoney(int dfMoney) {
		this.dfMoney = dfMoney;
	}
	public int getDnIsValiJob() {
		return dnIsValiJob;
	}
	public void setDnIsValiJob(int dnIsValiJob) {
		this.dnIsValiJob = dnIsValiJob;
	}
	public int getDnIsValiPhone() {
		return dnIsValiPhone;
	}
	public void setDnIsValiPhone(int dnIsValiPhone) {
		this.dnIsValiPhone = dnIsValiPhone;
	}
	public String getDcThirdpartyType() {
		return dcThirdpartyType;
	}
	public void setDcThirdpartyType(String dcThirdpartyType) {
		this.dcThirdpartyType = dcThirdpartyType;
	}
	public int getDnThirdpartyID() {
		return dnThirdpartyID;
	}
	public void setDnThirdpartyID(int dnThirdpartyID) {
		this.dnThirdpartyID = dnThirdpartyID;
	}
	public String getDtAddTime() {
		return dtAddTime;
	}
	public void setDtAddTime(String dtAddTime) {
		this.dtAddTime = dtAddTime;
	}
	public String getDtLoginTime() {
		return dtLoginTime;
	}
	public void setDtLoginTime(String dtLoginTime) {
		this.dtLoginTime = dtLoginTime;
	}
	
}
