package com.henglianmobile.medical.util;

/**
 * ������������ӿ�
 * 
 * @author Administrator
 * 
 */
public class Const {
	public static final int PAGEROWS = 10;

	public static final String BASEIP = "http://115.28.147.21:2233";

	/** ע�� */
	// public static final String REGISTERURL =
	// "http://192.168.3.8:8084/api/users/register?";
	public static final String REGISTERURL = BASEIP + "/api/users/register?";
	/** ��֤�ֻ����Ƿ��Ѿ�ע�� */
	public static final String PHONEISEXISTURL = BASEIP + "/api/users/isexist?";
	/** ��¼ */
	public static final String LOGONURL = BASEIP + "/api/users/login?";
	/** ��ȡ��֤��ӿ� */
	public static final String GETCODEURL = BASEIP + "/api/users/sendmsg?mobile=";
	/** ҽʦ�ʸ���֤ */
	public static final String DOCTORQUALIFICATIONURL = BASEIP + "/BllHandle/DoctorApp.ashx";
	/** ��ȡҽ��Ȧҽ���б�ӿ� */
	public static final String GETYIYOUQUANDOCTORLISTURL = BASEIP + "/api/answer/friendDoctorList?";
	/** ��ȡҽ��Ȧ�б�ӿ� */
	public static final String GETHUANYOUQUANLISTURL = BASEIP + "/api/answer/questionList?";
	/** ��ȡ��������ӿ� */
	public static final String GETHUANYOUQUANDETAILURL = BASEIP + "/api/question/gettopicinfo?Id=";
	/** ��ȡ�����������۽ӿ� */
	public static final String GETHUANYOUQUANPINGLUNURL = BASEIP + "/api/answer/getanswer?Id=";
	/** ��ӻ����������۽ӿ� */
	public static final String ADDHUANYOUQUANPINGLUNURL = BASEIP + "/api/answer/addanswer?";
	/** ����ҽ��Ȧ�ӿ� */
	public static final String PUBLISHHUANYOUSTATUSURL = BASEIP + "/BllHandle/addTopic.ashx?";
//	public static final String PUBLISHHUANYOUSTATUSURL = BASEIP + "/api/question/addtopic?";
	/** ��ȡҽ����Ѷ�б�ӿ� */
	public static final String GETMEDICALZIXUNLISTURL = BASEIP + "/api/new/getnew?";
	/** ��ȡҽ����Ѷͷ���ӿ� */
	public static final String GETMEDICALZIXUNTITLEURL = BASEIP + "/api/new/getNewsAd";
	/** ��ȡҽ����Ѷ����ӿ� */
	public static final String GETMEDICALZIXUNDETAILURL = BASEIP + "/newsView.aspx?id=";
	/** �ϴ�ͼƬ�ӿ� */
	public static final String UPLOADPICTUREURL = BASEIP + "/BllHandle/upimg.ashx";
	/** ��ȡ������Ϣ�ӿ� */
	public static final String GETUSERINFODETAILURL = BASEIP + "/api/users/getInfo?uid=";
	/** �޸ĸ�����Ϣ�ӿ� */
	public static final String UPDATEUSERINFODETAILURL = BASEIP + "/BllHandle/upMyInfo.ashx?";
//	public static final String UPDATEUSERINFODETAILURL = BASEIP + "/api/users/upmyinfo?";
	/** �޸�����ӿ� */
	public static final String UPDATEPASSWORDURL = BASEIP + "/api/users/uppass?";
	/** �޸��ֻ��Žӿ� */
	public static final String UPDATEMOBILEURL = BASEIP + "/api/users/upMobileNo?";
	/** ��������ӿ� */
	public static final String FORGETPASSWORDURL = BASEIP + "/api/users/findPsw?";
	/** �ϴ�ͷ��ӿ� */
	public static final String UPLOADPHOTOURL = BASEIP + "/BllHandle/upuserimg.ashx?";

	/** ����ҽ��-�ӿ� */
	/** ����ҽ��-���ҽ�����۽ӿ� */
	public static final String ADDDINGJIAURL = BASEIP + "/api/ownprice/addprice?";
	/** ����ҽ��-�޸�ҽ�����۽ӿ� */
	public static final String UPDATEDINGJIAURL = BASEIP + "/api/ownprice/upprice?";
	/** ����ҽ��-��ȡҽ�����۽ӿ� */
	public static final String GETDINGJIAURL = BASEIP + "/api/ownprice/getprice?uid=";
	/** ����ҽ��-��ȡԤԼ���ﻼ���б�ӿ� */
	public static final String DOCTORGETYUYUEJIUZHENLISTURL = BASEIP + "/api/make/getDoctorMake?";
	/** ����ҽ��-����ԤԼ�ӿ� */
	public static final String DOCTORSOLVEYUYUEJIUZHENLISTURL = BASEIP + "/api/make/upmake?";
	/** ����ҽ��-�ҵĻ��߽ӿ� */
	public static final String DOCTORMYPATIENTURL = BASEIP + "/api/make/getSickerList?";
	/** ����ҽ��-˽��ҽ�����ҵ�ԤԼ���ߣ��ӿ� */
	public static final String DOCTORMYPERSONALPATIENTYUYUEURL = BASEIP + "/api/make/getdoctormake?";
	/** ����ҽ��-˽��ҽ�����ҵ�ר�����ߣ��ӿ� */
	public static final String DOCTORMYPERSONALPATIENTURL = BASEIP + "/api/make/getSickerList?";
	/** ����ҽ��-˽��ҽ�����ҵ�ר�����ߣ����������������ӿ� */
	public static final String DOCTORMYPERSONALPATIENTPUBLISHHEALTHMETHODURL = BASEIP + "/BllHandle/addHealthProgram.ashx?";
//	public static final String DOCTORMYPERSONALPATIENTPUBLISHHEALTHMETHODURL = BASEIP + "/api/healthprogram/addhlpro?";
	/** ����ҽ��-����ͳ�� */
	public static final String BAOCHOUTONGJIURL = BASEIP + "/api/make/serviceCount?";

	/** ���ǻ���-�ӿ� */
	/** ���ǻ���-����ҽ���б�ӿ� */
	public static final String SEARCHDOCTORLISTURL = BASEIP + "/api/users/getdoctorlist?";
	/** ���ǻ���-����ӣ�ԤԼ�ӿ� */
	public static final String ADDYUYUEURL = BASEIP + "/BllHandle/addMake.ashx?";
//	public static final String ADDYUYUEURL = BASEIP + "/api/make/addmake?";
	/** ���ǻ���-��ȡԤԼ����ҽ���б�ӿ� */
	public static final String PATIENTTYUYUEJIUZHENLISTURL = BASEIP + "/api/make/getSickerMake?";
//	public static final String PATIENTTYUYUEJIUZHENLISTURL = BASEIP + "/api/make/getmymake?";
	/** ���ǻ���-�ҵ�˽��ҽ��ҽ���б�ӿ� */
	public static final String PATIENTTMYPERSONALDOCTORLISTURL = BASEIP + "/api/make/getmymake?";
	/** ���ǻ���-�ҵ�ҽ���ӿ� */
	public static final String PATIENTMYDOCTORLISTURL = BASEIP + "/api/make/getDoctorList?";
	/** ���ǻ���-���ҽ��Ϊ���ѽӿ� */
	public static final String PATIENTADDDOCTORURL = BASEIP + "/api/make/addFriend?";
	/** ���ǻ���-�ҵ�ҽ��-ɾ�����ѽӿ� */
	public static final String PATIENTDELETEDOCTORURL = BASEIP + "/api/make/delmake?";
	/** ���ǻ���-ҩƷ���� */
	public static final String YAOPINDAOGOULISTURL = BASEIP + "/api/drugPurchase/drugList?";
	/** ���ǻ���-ҩƷ����-����ҩ���ӿ� */
	public static final String UPLOADYAOFANGURL = BASEIP + "/BllHandle/addDrug.ashx?";
//	public static final String UPLOADYAOFANGURL = BASEIP + "/api/drugPurchase/drugAdd?";
	/** ���ǻ���-ҩƷ����-��ȡҩ������ӿ� */
	public static final String GETYAOFANGDETAILURL = BASEIP + "/api/drugPurchase/drugView?id=";

	/** ���ǻ���-��������ģ�� */
	/** ���(�޸�)������� */
	public static final String ADDPATIENTBASEINFORMATIONURL = BASEIP + "/BllHandle/upHealthInfo.ashx?";
//	public static final String ADDPATIENTBASEINFORMATIONURL = BASEIP + "/api/health/upinfo?";
	/** ��ȡ������� */
	public static final String GETPATIENTBASEINFORMATIONURL = BASEIP + "/api/health/getinfo?uid=";
	/** �ϴ���챨��ͻ��鵥�ӿ� */
	public static final String UPLOADHUAYANDANANDTIJIANBAOGAOURL = BASEIP + "/BllHandle/uptupian.ashx";
	/** ��ȡ���鵥�ӿ� */
	public static final String GETHUAYANDANURL = BASEIP + "/api/photograph/getLaboratoryimg?userId=";
	/** ��ȡ��챨��ӿ� */
	public static final String GETTIJIANBAOGAOURL = BASEIP + "/api/photograph/getphysicalimg?uid=";
	/** �����ҩ��ʷ�ӿ� */
	public static final String ADDPATIENTYONGYAOLISHIURL = BASEIP + "/BllHandle/addPast.ashx?";
//	public static final String ADDPATIENTYONGYAOLISHIURL = BASEIP + "/api/past/addpast?";
	/** ��ȡ��ҩ��ʷ�ӿ� */
	public static final String GETYONGYAOLISHIURL = BASEIP + "/api/past/getpast?uid=";
	/** ��ȡ�����������ӿ� */
	public static final String GETHEALTHMANAGEMETHODURL = BASEIP + "/api/healthprogram/getprolist?";

	/** ����ҵ��Ա-�ӿ� */
	/** ����ҵ��Ա-�ҵ�ҽ���ӿ� */
	public static final String GETMYDOCTORURL = BASEIP + "/api/salesMan/doctorList?";
	/** ����ҵ��Ա-������Ŀ�����ӿ� */
	public static final String GETFUWUXIANGMUCOUNTURL = BASEIP + "/api/salesMan/serviceCountList?";
	/** ����ҵ��Ա-�ҵ���ɽӿ� */
	public static final String GETCOMMISSIONURL = BASEIP + "/api/salesMan/serviceCount?";
	public static final String GETBALANCEURL = BASEIP + "/api/users/getBalance?userId=";

	/** �������֧�� */
	public static final String PAYFROMBALANCEURL = BASEIP + "/api/users/addChange?";
	/** ��ȡ��ֵ�������׺Žӿ� */
	public static final String GETRECHARGEORDERNOURL = BASEIP + "/api/users/getOrderNo?";

	/** ��ȡapk�汾XML�ӿ� */
	public static final String APK_VERSION_URL = BASEIP + "/apk/apkversion.xml";

}
