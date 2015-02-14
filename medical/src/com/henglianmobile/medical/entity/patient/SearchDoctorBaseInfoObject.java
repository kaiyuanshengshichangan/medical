package com.henglianmobile.medical.entity.patient;
/**
 * 医生基本信息对象
 * @author Administrator
 *
 */
public class SearchDoctorBaseInfoObject {
	/**医生ID*/
	private int dnUserid;
	/**医生昵称*/
	private String dcNickName;
	/**医生真实姓名*/
	private String dcRealName;
	/**头像路径*/
	private String dcHeadImg;
	/**医生主治*/
	private String dcActions;
	/**医生所属医院*/
	private String dcHospital;
	/**医生科室*/
	private String dcDepart;
	/**医生简介*/
	private String dcSign;
	/**医生推荐指数*/
	private String dnHot;
	/**热线价格*/
	private float dnHotLinePrice;
	/**私人医生价格*/
	private float dnOwnPrice;
	/**住院价格*/
	private float dnInHosPrice;
	/**挂号价格*/
	private float dnRegPrice;
	
	public String getDcHeadImg() {
		return dcHeadImg;
	}
	public void setDcHeadImg(String dcHeadImg) {
		this.dcHeadImg = dcHeadImg;
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
	public String getDcActions() {
		return dcActions;
	}
	public void setDcActions(String dcActions) {
		this.dcActions = dcActions;
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
	public String getDcSign() {
		return dcSign;
	}
	public void setDcSign(String dcSign) {
		this.dcSign = dcSign;
	}
	public String getDnHot() {
		return dnHot;
	}
	public void setDnHot(String dnHot) {
		this.dnHot = dnHot;
	}
	public float getDnHotLinePrice() {
		return dnHotLinePrice;
	}
	public void setDnHotLinePrice(float dnHotLinePrice) {
		this.dnHotLinePrice = dnHotLinePrice;
	}
	public float getDnOwnPrice() {
		return dnOwnPrice;
	}
	public void setDnOwnPrice(float dnOwnPrice) {
		this.dnOwnPrice = dnOwnPrice;
	}
	public float getDnInHosPrice() {
		return dnInHosPrice;
	}
	public void setDnInHosPrice(float dnInHosPrice) {
		this.dnInHosPrice = dnInHosPrice;
	}
	public float getDnRegPrice() {
		return dnRegPrice;
	}
	public void setDnRegPrice(float dnRegPrice) {
		this.dnRegPrice = dnRegPrice;
	}
	
}
