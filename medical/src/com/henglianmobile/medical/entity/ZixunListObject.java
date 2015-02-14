package com.henglianmobile.medical.entity;
/**
 * 咨询列表对象
 * @author Administrator
 *
 */
public class ZixunListObject {
	/**资讯ID*/
	private int dnNid;
	/**资讯标题*/
	private String dcNewTitle;
	/**资讯内容*/
	private String dcContent;
	/**资讯封面图片*/
	private String dcImgPath;
	/**资讯类型（备用）*/
	private int dnType;
	/**是否显示(1是 0 否)*/
	private int dnIsShow;
	/**用户ID*/
	private int dnUserid;
	/**添加时间*/
	private String dtAddTime;
	/**修改时间*/
	private String dtEditTime;
	/**发布人昵称*/
	private String dcNickName;
	/**发布人头像*/
	private String dcHeadImg;
	public int getDnNid() {
		return dnNid;
	}
	public void setDnNid(int dnNid) {
		this.dnNid = dnNid;
	}
	public String getDcNewTitle() {
		return dcNewTitle;
	}
	public void setDcNewTitle(String dcNewTitle) {
		this.dcNewTitle = dcNewTitle;
	}
	public String getDcContent() {
		return dcContent;
	}
	public void setDcContent(String dcContent) {
		this.dcContent = dcContent;
	}
	public String getDcImgPath() {
		return dcImgPath;
	}
	public void setDcImgPath(String dcImgPath) {
		this.dcImgPath = dcImgPath;
	}
	public int getDnType() {
		return dnType;
	}
	public void setDnType(int dnType) {
		this.dnType = dnType;
	}
	public int getDnIsShow() {
		return dnIsShow;
	}
	public void setDnIsShow(int dnIsShow) {
		this.dnIsShow = dnIsShow;
	}
	public int getDnUserid() {
		return dnUserid;
	}
	public void setDnUserid(int dnUserid) {
		this.dnUserid = dnUserid;
	}
	public String getDtAddTime() {
		return dtAddTime;
	}
	public void setDtAddTime(String dtAddTime) {
		this.dtAddTime = dtAddTime;
	}
	public String getDtEditTime() {
		return dtEditTime;
	}
	public void setDtEditTime(String dtEditTime) {
		this.dtEditTime = dtEditTime;
	}
	public String getDcNickName() {
		return dcNickName;
	}
	public void setDcNickName(String dcNickName) {
		this.dcNickName = dcNickName;
	}
	public String getDcHeadImg() {
		return dcHeadImg;
	}
	public void setDcHeadImg(String dcHeadImg) {
		this.dcHeadImg = dcHeadImg;
	}
	
}
