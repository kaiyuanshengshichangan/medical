package com.henglianmobile.medical.util;

import java.util.ArrayList;
import java.util.Map;
/**
 * ����������Ÿ��ֳ���
 * @author Administrator
 *
 */
public class Constant {
	/**����ҵ��Ա-������Ŀ*/
	/**����ҵ��Ա-ҽ��������Ŀ����-0-����*/
	public static final String SERVICEALL = "0";
	/**����ҵ��Ա-ҽ��������Ŀ����-1-�Һ�*/
	public static final String SERVICEGUAHAO = "1";
	/**����ҵ��Ա-ҽ��������Ŀ����-2-סԺ*/
	public static final String SERVICEZHUYUAN = "2";
	/**����ҵ��Ա-ҽ��������Ŀ����-3-����*/
	public static final String SERVICEREXIAN = "3";
	/**����ҵ��Ա-ҽ��������Ŀ����-4-˽��ҽ��*/
	public static final String SERVICEPERSOANLDOCTOR = "4";
	
	/**�����������ͣ�0-����ҽ��-���ߵĽ�������
	 *          1-���ǻ���-�Ľ�������*/
	public static final String HEALTH_MANAGE_KEY = "HEALTH_MANAGE";
	public static final String DOCTOR_HEALTH_MANAGE_VALUE = "0";
	public static final String PATIENT_HEALTH_MANAGE_VALUE = "1";
	
	/**ѡ����Ƭ���*/
	public static final String PHOTOTAG = "photo";
	/**ѡ����Ƭ���--5 ����ҩ��ѡ����Ƭ*/
	public static final String PIC_YAOFANG = "5";
	/**ѡ����Ƭ���--1 ��������Ȧѡ����Ƭ*/
	public static final String PIC_HUANYOUQUAN = "1";
	/**ѡ����Ƭ���--2 �������鵥ѡ����Ƭ*/
	public static final String PIC_HUAYANDAN = "2";
	/**ѡ����Ƭ���--3 ������챨��ѡ����Ƭ*/
	public static final String PIC_TIJIANBAOGAO = "3";
	/**ѡ����Ƭ���--4����ҽ���Ի��ߵĽ���������*/
	public static final String PIC_DOCTOR_HEALTH_MANAGE = "4";
	
	/**Ӧ���Ƿ񷢲�*/
	public static final boolean ISRELEASE = false;
	/**ʹ����-ҽ��*/
	public static final String DOCTOR = "2";
	/**ʹ����-ҵ��Ա*/
	public static final String SALEMAN = "4";
	/**ʹ����-����*/
	public static final String PATIENT = "3";
	
	/**�ϴ�ͼƬ����*/
	/**����*/
	public static final String FANGAN = "1";
	/**��Ѷ*/
	public static final String ZIXUN = "2";
	/**����Ȧ*/
	public static final String HUANYOUQUAN = "3";
	/**ԤԼ*/
	public static final String YUYUE = "4";
	/**ҩ��*/
	public static final String YAOFANG = "5";
	
	/**���������ϴ�ͼƬ����*/
	/**���鵥*/
	public static final String HUAYANDAN = "1";
	/**��챨��*/
	public static final String TIJIANBAOGAO = "2";
	
	
	/**���ԤԼ����*/
	/**�Һ�-1*/
	public static final String GUAHAO = "1";
	/**סԺ-2*/
	public static final String ZHUYUAN = "2";
	/**����-3*/
	public static final String REXIAN = "3";
	/**˽��ҽ��-4*/
	public static final String PERSONALDOCTOR = "4";
	
	/**ԤԼ����*/
	/**ר��*/
	public static final String ZHUANSHU = "1";
	/**����ԤԼ*/
	public static final String APPLYYUYUE = "0";
	
	public static final int SELECTCARBRAND = 0;
	public static final int REQUEST_CAMERA = 1;
	public static final int REQUEST_CHOOSE = 2;
	/**
	 * ���ѡ����Ƭ�ĵ�ַ
	 */
	public static ArrayList<Map<String, String>> selectPhotos;
	
	/**���͹㲥*/
	/**����ҩ��ͼƬ���չ㲥*/
	public static final String ACTIONYAOFANGSELECTPHOTO = "com.henglianmobile.medical.ui.activity.patient.UploadYaoFang";
	/**��������ȦͼƬ���չ㲥*/
	public static final String ACTIONHUANYOUQUANSELECTPHOTO = "com.henglianmobile.medical.ui.activity.PublishPatientStatusActivity";
	/**�������鵥ͼƬ���չ㲥*/
	public static final String ACTIONHUAYANDANSELECTPHOTO = "com.henglianmobile.medical.ui.activity.PublishHuaYanDanActivity";
	/**������챨��ͼƬ���չ㲥*/
	public static final String ACTIONTIJIANBAOGAOSELECTPHOTO = "com.henglianmobile.medical.ui.activity.PublishTiJianBaoGaoActivity";
	/**����ҽ���Ի��߽���������ͼƬ���չ㲥*/
	public static final String ACTIONDOCTORHEALTHMANAGEMETHODSELECTPHOTO = "com.henglianmobile.medical.ui.activity.doctor.PublishHealthManageMethodActivity";
	
	
	/** ֧���ɹ����͵Ĺ㲥 */
	/** ֧��ԤԼ����ɹ����͵Ĺ㲥 */
	public static final String ACTIONYUYUEJIUZHENSUCCESS = "com.henglianmobile.medical.ui.activity.patient.GuahaoActivity.pay";
	/** ֧��ԤԼסԺ�ɹ����͵Ĺ㲥 */
	public static final String ACTIONYUYUEZHUYUANNSUCCESS = "com.henglianmobile.medical.ui.activity.patient.ZhuyuanActivity.pay";
	/** ֧��ԤԼ���߳ɹ����͵Ĺ㲥 */
	public static final String ACTIONYUYUEREXIANSUCCESS = "com.henglianmobile.medical.ui.activity.patient.RexianActivity.pay";
	/** ֧��ԤԼ˽��ҽ���ɹ����͵Ĺ㲥 */
	public static final String ACTIONYUYUEPERSONALDOCTORSUCCESS = "com.henglianmobile.medical.ui.activity.patient.PersonalDoctorActivity.pay";

	/** ҽ���ʸ���֤�ɹ����͵Ĺ㲥 */
	public static final String ACTIONDOCTORQUALIFICATIONSUCCESS = "com.henglianmobile.medical.activity.DoctorQualificationActivity";
	/** ��������Ȧ�ɹ����͵Ĺ㲥 */
	public static final String ACTIONPUBLISHHUANYOUQUANSUCCESS = "com.henglianmobile.medical.ui.activity.PublishPatientStatusActivity.MedicalZiXunFragment";
	
	
	/** �޸ĸ������ϳɹ����͵Ĺ㲥 */
	public static final String ACTIONUPDATEUSERINFOSUCCESS = "com.henglianmobile.medical.ui.activity.patient.UPDADEUSERINFOSUCCESS";
}
