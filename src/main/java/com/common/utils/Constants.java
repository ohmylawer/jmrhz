package com.common.utils;

public interface Constants {

	//组织机构根节点编码
	public String ORG_ROOT_CODE="01";
	
	public long ORG_ROOT_ID = 1l;
	
	public long ORG_ROOT_PARENTID = -1l;
	
	//权限根节点编码
	public String PERMISSION_ROOT_CODE="010000000000";//00000000000000000000
	
	public long PERMISSION_ROOT_ID = 0l;
	
	public long PERMISSION_ROOT_PARENTID = -1l;
	
	public short PERMISSION_TYPE_MENU = 1;
	
	public short PERMISSION_TYPE_BUTTON = 2;
	
	public short PERMISSION_TYPE_OTHER = 3;
	
	public short PERMISSION_STATUS_OK = 1;
	
	/**
	 * *********文件导入**************************
	 */
	
	String IMPORT_FILE_TYPE_EXCEL2003 = "xls";
	
	String IMPORT_FILE_TYPE_EXCEL2007 = "xlsx";
	
	String IMPORT_FILE_TYPE_ZIP = "zip";
	
	String[] IMPORT_FILE_TYPES =  {IMPORT_FILE_TYPE_EXCEL2003,IMPORT_FILE_TYPE_EXCEL2007,IMPORT_FILE_TYPE_ZIP};

	String IMPORT_FILEINFO_FROMUNIT = "IMPORT_FILEINFO_FROMUNIT";
	
	String IMPORT_FILEINFO_TOUNIT = "IMPORT_FILEINFO_TOUNIT";
	
	String IMPORT_FILEINFO_EQU = "IMPORT_FILEINFO_EQU";
	
	String IMPORT_FILEINFO_ENCRYPTION = "IMPORT_FILEINFO_ENCRYPTION";
	
	String IMPORT_FILEINFO_CHECKFILE_NAME = "IMPORT_FILEINFO_CHECKFILE_NAME.json";
	
	String IMPORT_FILEINFO_NAME = "IMPORT_FILEINFO_NAME.json";
	
	
	/**
	 * 基础数据
	 */
	String EXPORT_MODULE_NAME = "单位目录,系统字典,计划年度,计划科目,装备目录,编报单位,预算单位,分配（送修）单位,供货（承修）单位,装备物资价格,计量单位字典";
	String[] EXPORT_MODULE_FILENAMES = {"Sysorg.json","Sysdictionary.json","Sysplan.json","Baseplancatalog.json","Baseequipmentcatalog.json","SysSupply.json","Basemeasureunit.json","Baseequsuppliesprice.json"};
	
	/**
	 * 计划收发
	 */
	String PLAN_SENDRECEIVE_NAME="本级计划,对下计划";
	String[] PLAN_SENDRECEIVE_VALUE= {"ReportRepository.json","ReportSubRepository.json","IMPORT_FILEINFO_CHECKFILE_NAME.json"};
	
	/**
	 * 系统数据备份
	 */
	String[] SYS_BACKUP_REPORT_NAME =  { "reply_normal_plan",  "report_normal_plan","report_normal_plan_business", "report_adjustment_plan", "report_adjustment_plan_business",  "reply_adjustment_plan" };
	
	String[] SYS_BACKUP_REPORT_NAME_SUB =  {  "reply_normal_plan_sub", "report_normal_plan_sub", "report_normal_plan_sub_business" , "report_adjustment_plan_sub","report_adjustment_plan_sub_business",  "reply_adjustment_plan_sub" };
	
	
	/**
	 * **************计划年度信息******************
	 */
	String PLAN_YEAR_ID = "PLAN_YEAR_ID";
	
	String PLAN_YEAR = "PLAN_YEAR";
	
	String PLAN_TYPE = "PLAN_TYPE";
	
	/**
	 * *************登录用户和机构信息*************
	 */
	
	String LOGIN_USER = "COM_SYS_SYSUSER_LOGIN_USER";
	
	String LOGIN_ORG = "COM_SYS_SYSORG";
	
	String LOGIN_DEPT= "COM_SYS_SYSDEPT";
}
