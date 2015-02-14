package com.henglianmobile.medical.util;

import java.util.ArrayList;
import java.util.Map;
/**
 * 此类用来存放各种常量
 * @author Administrator
 *
 */
public class Constant {
	/**我是业务员-服务项目*/
	/**我是业务员-医生服务项目类型-0-所有*/
	public static final String SERVICEALL = "0";
	/**我是业务员-医生服务项目类型-1-挂号*/
	public static final String SERVICEGUAHAO = "1";
	/**我是业务员-医生服务项目类型-2-住院*/
	public static final String SERVICEZHUYUAN = "2";
	/**我是业务员-医生服务项目类型-3-热线*/
	public static final String SERVICEREXIAN = "3";
	/**我是业务员-医生服务项目类型-4-私人医生*/
	public static final String SERVICEPERSOANLDOCTOR = "4";
	
	/**健康管理类型，0-我是医生-患者的健康管理
	 *          1-我是患者-的健康管理*/
	public static final String HEALTH_MANAGE_KEY = "HEALTH_MANAGE";
	public static final String DOCTOR_HEALTH_MANAGE_VALUE = "0";
	public static final String PATIENT_HEALTH_MANAGE_VALUE = "1";
	
	/**选择照片标记*/
	public static final String PHOTOTAG = "photo";
	/**选择照片标记--5 发布药方选择照片*/
	public static final String PIC_YAOFANG = "5";
	/**选择照片标记--1 发布患有圈选择照片*/
	public static final String PIC_HUANYOUQUAN = "1";
	/**选择照片标记--2 发布化验单选择照片*/
	public static final String PIC_HUAYANDAN = "2";
	/**选择照片标记--3 发布体检报告选择照片*/
	public static final String PIC_TIJIANBAOGAO = "3";
	/**选择照片标记--4发布医生对患者的健康管理方案*/
	public static final String PIC_DOCTOR_HEALTH_MANAGE = "4";
	
	/**应用是否发布*/
	public static final boolean ISRELEASE = false;
	/**使用者-医生*/
	public static final String DOCTOR = "2";
	/**使用者-业务员*/
	public static final String SALEMAN = "4";
	/**使用者-患者*/
	public static final String PATIENT = "3";
	
	/**上传图片类型*/
	/**方案*/
	public static final String FANGAN = "1";
	/**资讯*/
	public static final String ZIXUN = "2";
	/**患友圈*/
	public static final String HUANYOUQUAN = "3";
	/**预约*/
	public static final String YUYUE = "4";
	/**药方*/
	public static final String YAOFANG = "5";
	
	/**健康管理上传图片类型*/
	/**化验单*/
	public static final String HUAYANDAN = "1";
	/**体检报告*/
	public static final String TIJIANBAOGAO = "2";
	
	
	/**添加预约类型*/
	/**挂号-1*/
	public static final String GUAHAO = "1";
	/**住院-2*/
	public static final String ZHUYUAN = "2";
	/**热线-3*/
	public static final String REXIAN = "3";
	/**私人医生-4*/
	public static final String PERSONALDOCTOR = "4";
	
	/**预约类型*/
	/**专属*/
	public static final String ZHUANSHU = "1";
	/**申请预约*/
	public static final String APPLYYUYUE = "0";
	
	public static final int SELECTCARBRAND = 0;
	public static final int REQUEST_CAMERA = 1;
	public static final int REQUEST_CHOOSE = 2;
	/**
	 * 存放选择照片的地址
	 */
	public static ArrayList<Map<String, String>> selectPhotos;
	
	/**发送广播*/
	/**发布药方图片接收广播*/
	public static final String ACTIONYAOFANGSELECTPHOTO = "com.henglianmobile.medical.ui.activity.patient.UploadYaoFang";
	/**发布患有圈图片接收广播*/
	public static final String ACTIONHUANYOUQUANSELECTPHOTO = "com.henglianmobile.medical.ui.activity.PublishPatientStatusActivity";
	/**发布化验单图片接收广播*/
	public static final String ACTIONHUAYANDANSELECTPHOTO = "com.henglianmobile.medical.ui.activity.PublishHuaYanDanActivity";
	/**发布体检报告图片接收广播*/
	public static final String ACTIONTIJIANBAOGAOSELECTPHOTO = "com.henglianmobile.medical.ui.activity.PublishTiJianBaoGaoActivity";
	/**发布医生对患者健康管理方案图片接收广播*/
	public static final String ACTIONDOCTORHEALTHMANAGEMETHODSELECTPHOTO = "com.henglianmobile.medical.ui.activity.doctor.PublishHealthManageMethodActivity";
	
	
	/** 支付成功发送的广播 */
	/** 支付预约就诊成功发送的广播 */
	public static final String ACTIONYUYUEJIUZHENSUCCESS = "com.henglianmobile.medical.ui.activity.patient.GuahaoActivity.pay";
	/** 支付预约住院成功发送的广播 */
	public static final String ACTIONYUYUEZHUYUANNSUCCESS = "com.henglianmobile.medical.ui.activity.patient.ZhuyuanActivity.pay";
	/** 支付预约热线成功发送的广播 */
	public static final String ACTIONYUYUEREXIANSUCCESS = "com.henglianmobile.medical.ui.activity.patient.RexianActivity.pay";
	/** 支付预约私人医生成功发送的广播 */
	public static final String ACTIONYUYUEPERSONALDOCTORSUCCESS = "com.henglianmobile.medical.ui.activity.patient.PersonalDoctorActivity.pay";

	/** 医生资格认证成功后发送的广播 */
	public static final String ACTIONDOCTORQUALIFICATIONSUCCESS = "com.henglianmobile.medical.activity.DoctorQualificationActivity";
	/** 发布患友圈成功发送的广播 */
	public static final String ACTIONPUBLISHHUANYOUQUANSUCCESS = "com.henglianmobile.medical.ui.activity.PublishPatientStatusActivity.MedicalZiXunFragment";
	
	
	/** 修改个人资料成功发送的广播 */
	public static final String ACTIONUPDATEUSERINFOSUCCESS = "com.henglianmobile.medical.ui.activity.patient.UPDADEUSERINFOSUCCESS";
}
