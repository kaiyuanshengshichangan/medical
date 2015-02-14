package com.henglianmobile.medical.entity.saleman;

public class CommissionListObject {

	private int dnMakeType;
	private int serviceNum;
	private float sumPrice;
	private int sumMonth;
	
	
	public int getSumMonth() {
		return sumMonth;
	}
	public void setSumMonth(int sumMonth) {
		this.sumMonth = sumMonth;
	}
	public int getDnMakeType() {
		return dnMakeType;
	}
	public void setDnMakeType(int dnMakeType) {
		this.dnMakeType = dnMakeType;
	}
	public int getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(int serviceNum) {
		this.serviceNum = serviceNum;
	}
	public float getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(float sumPrice) {
		this.sumPrice = sumPrice;
	}
	
}
