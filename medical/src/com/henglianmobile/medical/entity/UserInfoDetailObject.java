package com.henglianmobile.medical.entity;

import java.io.Serializable;
/**
 * 用户信息详情对象
 * @author Administrator
 *
 */
public class UserInfoDetailObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7338003500034623846L;
	/**用户ID*/
	private int dnUserid;
	/**用户名*/
	private String dcUserName;
	/**用户昵称*/
	private String dcNickName;
	/**用户类型 1 医院 2医生 3普通会员 4业务员*/
	private int dnType;
	/**真实姓名*/
	private String dcRealName;
	/**手机号*/
	private String dcCellPhone;
	/**联系电话*/
	private String dcPhone;
	/**QQ号*/
	private String dcTencentQQ;
	/**微信号*/
	private String dcWeChat;
	/**邮箱*/
	private String dcEmail;
	/**头像路径*/
	private String dcHeadImg;
	/**性别 0女 1男*/
	private int dnSex;
	/**年龄*/
	private int dnAge;
	/**签名*/
	private String dcSign;
	/**身份证号码*/
	private String dcIDCard;
	/**主治(医生)*/
	private String dcActions;
	/**医院*/
	private String dcHospital;
	/**医院等级*/
	private String hospitalGrade;
	/**科室*/
	private String dcDepart;
	/**从医资格（职位）*/
	private String dcPosition;
	/**行医证(医生)*/
	private String dcDoctorCart;
	/**备注*/
	private String dcContent;
	/**剩余金额*/
	private int dfMoney;
	/**是否认证职业资格证书 1是 0否*/
	private int dnIsValiJob;
	/**是否绑定手机号 0否 1是*/
	private int dnIsValiPhone;
	/**第三方登录类型（QQ、新浪、微信）*/
	private String dcThirdpartyType;
	/**第三方登录id*/
	private int dnThirdpartyID;
	/**注册时间*/
	private String dtAddTime;
	/**登录时间*/
	private String dtLoginTime;
	/**地址*/
	private String dcAddress;
	/**背景图片*/
	private String dcBackImg;
	/**推荐指数*/
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
