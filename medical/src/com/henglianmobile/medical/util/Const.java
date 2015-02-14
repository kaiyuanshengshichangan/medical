package com.henglianmobile.medical.util;

/**
 * 此类用来定义接口
 * 
 * @author Administrator
 * 
 */
public class Const {
	public static final int PAGEROWS = 10;

	public static final String BASEIP = "http://115.28.147.21:2233";

	/** 注册 */
	// public static final String REGISTERURL =
	// "http://192.168.3.8:8084/api/users/register?";
	public static final String REGISTERURL = BASEIP + "/api/users/register?";
	/** 验证手机号是否已经注册 */
	public static final String PHONEISEXISTURL = BASEIP + "/api/users/isexist?";
	/** 登录 */
	public static final String LOGONURL = BASEIP + "/api/users/login?";
	/** 获取验证码接口 */
	public static final String GETCODEURL = BASEIP + "/api/users/sendmsg?mobile=";
	/** 医师资格认证 */
	public static final String DOCTORQUALIFICATIONURL = BASEIP + "/BllHandle/DoctorApp.ashx";
	/** 获取医友圈医生列表接口 */
	public static final String GETYIYOUQUANDOCTORLISTURL = BASEIP + "/api/answer/friendDoctorList?";
	/** 获取医友圈列表接口 */
	public static final String GETHUANYOUQUANLISTURL = BASEIP + "/api/answer/questionList?";
	/** 获取患有详情接口 */
	public static final String GETHUANYOUQUANDETAILURL = BASEIP + "/api/question/gettopicinfo?Id=";
	/** 获取患有详情评论接口 */
	public static final String GETHUANYOUQUANPINGLUNURL = BASEIP + "/api/answer/getanswer?Id=";
	/** 添加患有详情评论接口 */
	public static final String ADDHUANYOUQUANPINGLUNURL = BASEIP + "/api/answer/addanswer?";
	/** 发布医友圈接口 */
	public static final String PUBLISHHUANYOUSTATUSURL = BASEIP + "/BllHandle/addTopic.ashx?";
//	public static final String PUBLISHHUANYOUSTATUSURL = BASEIP + "/api/question/addtopic?";
	/** 获取医疗资讯列表接口 */
	public static final String GETMEDICALZIXUNLISTURL = BASEIP + "/api/new/getnew?";
	/** 获取医疗资讯头部接口 */
	public static final String GETMEDICALZIXUNTITLEURL = BASEIP + "/api/new/getNewsAd";
	/** 获取医疗资讯详情接口 */
	public static final String GETMEDICALZIXUNDETAILURL = BASEIP + "/newsView.aspx?id=";
	/** 上传图片接口 */
	public static final String UPLOADPICTUREURL = BASEIP + "/BllHandle/upimg.ashx";
	/** 获取个人信息接口 */
	public static final String GETUSERINFODETAILURL = BASEIP + "/api/users/getInfo?uid=";
	/** 修改个人信息接口 */
	public static final String UPDATEUSERINFODETAILURL = BASEIP + "/BllHandle/upMyInfo.ashx?";
//	public static final String UPDATEUSERINFODETAILURL = BASEIP + "/api/users/upmyinfo?";
	/** 修改密码接口 */
	public static final String UPDATEPASSWORDURL = BASEIP + "/api/users/uppass?";
	/** 修改手机号接口 */
	public static final String UPDATEMOBILEURL = BASEIP + "/api/users/upMobileNo?";
	/** 忘记密码接口 */
	public static final String FORGETPASSWORDURL = BASEIP + "/api/users/findPsw?";
	/** 上传头像接口 */
	public static final String UPLOADPHOTOURL = BASEIP + "/BllHandle/upuserimg.ashx?";

	/** 我是医生-接口 */
	/** 我是医生-添加医生定价接口 */
	public static final String ADDDINGJIAURL = BASEIP + "/api/ownprice/addprice?";
	/** 我是医生-修改医生定价接口 */
	public static final String UPDATEDINGJIAURL = BASEIP + "/api/ownprice/upprice?";
	/** 我是医生-获取医生定价接口 */
	public static final String GETDINGJIAURL = BASEIP + "/api/ownprice/getprice?uid=";
	/** 我是医生-获取预约就诊患者列表接口 */
	public static final String DOCTORGETYUYUEJIUZHENLISTURL = BASEIP + "/api/make/getDoctorMake?";
	/** 我是医生-处理预约接口 */
	public static final String DOCTORSOLVEYUYUEJIUZHENLISTURL = BASEIP + "/api/make/upmake?";
	/** 我是医生-我的患者接口 */
	public static final String DOCTORMYPATIENTURL = BASEIP + "/api/make/getSickerList?";
	/** 我是医生-私人医生（我的预约患者）接口 */
	public static final String DOCTORMYPERSONALPATIENTYUYUEURL = BASEIP + "/api/make/getdoctormake?";
	/** 我是医生-私人医生（我的专属患者）接口 */
	public static final String DOCTORMYPERSONALPATIENTURL = BASEIP + "/api/make/getSickerList?";
	/** 我是医生-私人医生（我的专属患者）发布健康管理方案接口 */
	public static final String DOCTORMYPERSONALPATIENTPUBLISHHEALTHMETHODURL = BASEIP + "/BllHandle/addHealthProgram.ashx?";
//	public static final String DOCTORMYPERSONALPATIENTPUBLISHHEALTHMETHODURL = BASEIP + "/api/healthprogram/addhlpro?";
	/** 我是医生-报酬统计 */
	public static final String BAOCHOUTONGJIURL = BASEIP + "/api/make/serviceCount?";

	/** 我是患者-接口 */
	/** 我是患者-查找医生列表接口 */
	public static final String SEARCHDOCTORLISTURL = BASEIP + "/api/users/getdoctorlist?";
	/** 我是患者-（添加）预约接口 */
	public static final String ADDYUYUEURL = BASEIP + "/BllHandle/addMake.ashx?";
//	public static final String ADDYUYUEURL = BASEIP + "/api/make/addmake?";
	/** 我是患者-获取预约就诊医生列表接口 */
	public static final String PATIENTTYUYUEJIUZHENLISTURL = BASEIP + "/api/make/getSickerMake?";
//	public static final String PATIENTTYUYUEJIUZHENLISTURL = BASEIP + "/api/make/getmymake?";
	/** 我是患者-我的私人医生医生列表接口 */
	public static final String PATIENTTMYPERSONALDOCTORLISTURL = BASEIP + "/api/make/getmymake?";
	/** 我是患者-我的医生接口 */
	public static final String PATIENTMYDOCTORLISTURL = BASEIP + "/api/make/getDoctorList?";
	/** 我是患者-添加医生为好友接口 */
	public static final String PATIENTADDDOCTORURL = BASEIP + "/api/make/addFriend?";
	/** 我是患者-我的医生-删除好友接口 */
	public static final String PATIENTDELETEDOCTORURL = BASEIP + "/api/make/delmake?";
	/** 我是患者-药品导购 */
	public static final String YAOPINDAOGOULISTURL = BASEIP + "/api/drugPurchase/drugList?";
	/** 我是患者-药品导购-发布药方接口 */
	public static final String UPLOADYAOFANGURL = BASEIP + "/BllHandle/addDrug.ashx?";
//	public static final String UPLOADYAOFANGURL = BASEIP + "/api/drugPurchase/drugAdd?";
	/** 我是患者-药品导购-获取药方详情接口 */
	public static final String GETYAOFANGDETAILURL = BASEIP + "/api/drugPurchase/drugView?id=";

	/** 我是患者-健康管理模块 */
	/** 添加(修改)基本情况 */
	public static final String ADDPATIENTBASEINFORMATIONURL = BASEIP + "/BllHandle/upHealthInfo.ashx?";
//	public static final String ADDPATIENTBASEINFORMATIONURL = BASEIP + "/api/health/upinfo?";
	/** 获取基本情况 */
	public static final String GETPATIENTBASEINFORMATIONURL = BASEIP + "/api/health/getinfo?uid=";
	/** 上传体检报告和化验单接口 */
	public static final String UPLOADHUAYANDANANDTIJIANBAOGAOURL = BASEIP + "/BllHandle/uptupian.ashx";
	/** 获取化验单接口 */
	public static final String GETHUAYANDANURL = BASEIP + "/api/photograph/getLaboratoryimg?userId=";
	/** 获取体检报告接口 */
	public static final String GETTIJIANBAOGAOURL = BASEIP + "/api/photograph/getphysicalimg?uid=";
	/** 添加用药历史接口 */
	public static final String ADDPATIENTYONGYAOLISHIURL = BASEIP + "/BllHandle/addPast.ashx?";
//	public static final String ADDPATIENTYONGYAOLISHIURL = BASEIP + "/api/past/addpast?";
	/** 获取用药历史接口 */
	public static final String GETYONGYAOLISHIURL = BASEIP + "/api/past/getpast?uid=";
	/** 获取健康管理方案接口 */
	public static final String GETHEALTHMANAGEMETHODURL = BASEIP + "/api/healthprogram/getprolist?";

	/** 我是业务员-接口 */
	/** 我是业务员-我的医生接口 */
	public static final String GETMYDOCTORURL = BASEIP + "/api/salesMan/doctorList?";
	/** 我是业务员-服务项目数量接口 */
	public static final String GETFUWUXIANGMUCOUNTURL = BASEIP + "/api/salesMan/serviceCountList?";
	/** 我是业务员-我的提成接口 */
	public static final String GETCOMMISSIONURL = BASEIP + "/api/salesMan/serviceCount?";
	public static final String GETBALANCEURL = BASEIP + "/api/users/getBalance?userId=";

	/** 进行余额支付 */
	public static final String PAYFROMBALANCEURL = BASEIP + "/api/users/addChange?";
	/** 获取充值订单交易号接口 */
	public static final String GETRECHARGEORDERNOURL = BASEIP + "/api/users/getOrderNo?";

	/** 获取apk版本XML接口 */
	public static final String APK_VERSION_URL = BASEIP + "/apk/apkversion.xml";

}
