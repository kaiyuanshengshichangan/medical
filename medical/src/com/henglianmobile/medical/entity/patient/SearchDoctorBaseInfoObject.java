package com.henglianmobile.medical.entity.patient;
/**
 * ҽ��������Ϣ����
 * @author Administrator
 *
 */
public class SearchDoctorBaseInfoObject {
	/**ҽ��ID*/
	private int dnUserid;
	/**ҽ���ǳ�*/
	private String dcNickName;
	/**ҽ����ʵ����*/
	private String dcRealName;
	/**ͷ��·��*/
	private String dcHeadImg;
	/**ҽ������*/
	private String dcActions;
	/**ҽ������ҽԺ*/
	private String dcHospital;
	/**ҽ������*/
	private String dcDepart;
	/**ҽ�����*/
	private String dcSign;
	/**ҽ���Ƽ�ָ��*/
	private String dnHot;
	/**���߼۸�*/
	private float dnHotLinePrice;
	/**˽��ҽ���۸�*/
	private float dnOwnPrice;
	/**סԺ�۸�*/
	private float dnInHosPrice;
	/**�Һż۸�*/
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
