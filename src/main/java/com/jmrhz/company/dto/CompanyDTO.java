package com.jmrhz.company.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司
 * 
 * @author tjx
 *
 */
public class CompanyDTO implements Serializable {

	private static final long serialVersionUID = -1;

	private long id;
	/**
	 * 单位名称
	 */
	private String companyName;
	/**
	 * 营业执照
	 */
	private String businessLicense;
	/**
	 * 法人代表
	 */
	private String legalPerson;
	/**
	 * 公司工商注册时间
	 */
	private String companyRegistrationTime;
	/**
	 * 是否参加往届
	 */
	private String joined;
	/**
	 * 是否继续参加
	 */
	private Boolean continueJoin;
	/**
	 * 负责人
	 */
	private String principal;
	/**
	 * 手机
	 */
	private String telephone;
	/**
	 * 固定电话
	 */
	private String fixedlineTelephone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String postcode;
	/**
	 * 产品技术类型
	 */
	private String productType;
	/**
	 * 产品技术领域
	 */
	private String technologyField;
	/**
	 * 单位性质
	 */
	private String unitNature;
	/**
	 * 企业规模
	 */
	private String sacle;
	/**
	 * 是否高新技术企业
	 */
	private Boolean techCompany;
	/**
	 * 高新技术等级
	 */
	private String techCompanyLevel;
	/**
	 * 是否上市
	 */
	private Boolean appearOnMarket;
	/**
	 * 上市时间
	 */
	private String appearOnMarketTime;
	/**
	 * 注册资金
	 */
	private BigDecimal registeredCaptital;
	/**
	 * 企业总资产
	 */
	private BigDecimal totalEnterpriseAssets;
	/**
	 * 军品营业额
	 */
	private BigDecimal militaryTurnover;
	/**
	 * 平均军品营业额
	 */
	private BigDecimal avgMilitaryTurnover;
	/**
	 * 民品营业额
	 */
	private BigDecimal merchandiseTurnover;
	/**
	 * 平均民品营业额
	 */
	private BigDecimal avgMerchandiseTurnover;
	/**
	 * 企业简介
	 */
	private String enterpriseProfile;
	/**
	 * 人员结构
	 */
	private String personnelStructure;
	/**
	 * 股权构成
	 */
	private String sharesConstitute;
	/**
	 * 专利情况
	 */
	private String patentSituation;
	/**
	 * 获奖情况
	 */
	private String prizeSituation;
	/**
	 * 参与大型项目情况
	 */
	private String paticipationInLargeProject;
	/**
	 * 科技金融服务需求
	 */
	private Boolean technologyFinancialServicesDemand;
	/**
	 * 科技金融服务需求类型
	 */
	private String technologyFinancialServicesDemandType;
	/**
	 * 科技金融服务需求说明
	 */
	private String technologyFinancialServicesDemandDescription;
	/**
	 * 企业图片
	 */
	private String enterpriseImage;
	/**
	 * 附件
	 */
	private String accessory;
	/**
	 * 存储时间
	 */
	private String saveTime;

	private String recommendedUnitName;

	private String recommendedUnitContant;

	private String recommendedUnitTel;

	private Boolean joinActivity;

	private String equipment;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getJoined() {
		return joined;
	}

	public void setJoined(String joined) {
		this.joined = joined;
	}

	public Boolean getContinueJoin() {
		return continueJoin;
	}

	public void setContinueJoin(Boolean continueJoin) {
		this.continueJoin = continueJoin;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFixedlineTelephone() {
		return fixedlineTelephone;
	}

	public void setFixedlineTelephone(String fixedlineTelephone) {
		this.fixedlineTelephone = fixedlineTelephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getTechnologyField() {
		return technologyField;
	}

	public void setTechnologyField(String technologyField) {
		this.technologyField = technologyField;
	}

	public String getUnitNature() {
		return unitNature;
	}

	public void setUnitNature(String unitNature) {
		this.unitNature = unitNature;
	}

	public String getSacle() {
		return sacle;
	}

	public void setSacle(String sacle) {
		this.sacle = sacle;
	}

	public Boolean getTechCompany() {
		return techCompany;
	}

	public void setTechCompany(Boolean techCompany) {
		this.techCompany = techCompany;
	}

	public String getTechCompanyLevel() {
		return techCompanyLevel;
	}

	public void setTechCompanyLevel(String techCompanyLevel) {
		this.techCompanyLevel = techCompanyLevel;
	}

	public Boolean getAppearOnMarket() {
		return appearOnMarket;
	}

	public void setAppearOnMarket(Boolean appearOnMarket) {
		this.appearOnMarket = appearOnMarket;
	}

	public BigDecimal getTotalEnterpriseAssets() {
		return totalEnterpriseAssets;
	}

	public void setTotalEnterpriseAssets(BigDecimal totalEnterpriseAssets) {
		this.totalEnterpriseAssets = totalEnterpriseAssets;
	}

	public BigDecimal getMilitaryTurnover() {
		return militaryTurnover;
	}

	public void setMilitaryTurnover(BigDecimal militaryTurnover) {
		this.militaryTurnover = militaryTurnover;
	}

	public BigDecimal getAvgMilitaryTurnover() {
		return avgMilitaryTurnover;
	}

	public void setAvgMilitaryTurnover(BigDecimal avgMilitaryTurnover) {
		this.avgMilitaryTurnover = avgMilitaryTurnover;
	}

	public BigDecimal getMerchandiseTurnover() {
		return merchandiseTurnover;
	}

	public void setMerchandiseTurnover(BigDecimal merchandiseTurnover) {
		this.merchandiseTurnover = merchandiseTurnover;
	}

	public BigDecimal getAvgMerchandiseTurnover() {
		return avgMerchandiseTurnover;
	}

	public void setAvgMerchandiseTurnover(BigDecimal avgMerchandiseTurnover) {
		this.avgMerchandiseTurnover = avgMerchandiseTurnover;
	}

	public BigDecimal getRegisteredCaptital() {
		return registeredCaptital;
	}

	public void setRegisteredCaptital(BigDecimal registeredCaptital) {
		this.registeredCaptital = registeredCaptital;
	}

	public String getEnterpriseProfile() {
		return enterpriseProfile;
	}

	public void setEnterpriseProfile(String enterpriseProfile) {
		this.enterpriseProfile = enterpriseProfile;
	}

	public String getPersonnelStructure() {
		return personnelStructure;
	}

	public void setPersonnelStructure(String personnelStructure) {
		this.personnelStructure = personnelStructure;
	}

	public String getSharesConstitute() {
		return sharesConstitute;
	}

	public void setSharesConstitute(String sharesConstitute) {
		this.sharesConstitute = sharesConstitute;
	}

	public String getPatentSituation() {
		return patentSituation;
	}

	public void setPatentSituation(String patentSituation) {
		this.patentSituation = patentSituation;
	}

	public String getPrizeSituation() {
		return prizeSituation;
	}

	public void setPrizeSituation(String prizeSituation) {
		this.prizeSituation = prizeSituation;
	}

	public String getPaticipationInLargeProject() {
		return paticipationInLargeProject;
	}

	public void setPaticipationInLargeProject(String paticipationInLargeProject) {
		this.paticipationInLargeProject = paticipationInLargeProject;
	}

	public Boolean getTechnologyFinancialServicesDemand() {
		return technologyFinancialServicesDemand;
	}

	public void setTechnologyFinancialServicesDemand(Boolean technologyFinancialServicesDemand) {
		this.technologyFinancialServicesDemand = technologyFinancialServicesDemand;
	}

	public String getTechnologyFinancialServicesDemandType() {
		return technologyFinancialServicesDemandType;
	}

	public void setTechnologyFinancialServicesDemandType(String technologyFinancialServicesDemandType) {
		this.technologyFinancialServicesDemandType = technologyFinancialServicesDemandType;
	}

	public String getTechnologyFinancialServicesDemandDescription() {
		return technologyFinancialServicesDemandDescription;
	}

	public void setTechnologyFinancialServicesDemandDescription(String technologyFinancialServicesDemandDescription) {
		this.technologyFinancialServicesDemandDescription = technologyFinancialServicesDemandDescription;
	}

	public String getEnterpriseImage() {
		return enterpriseImage;
	}

	public void setEnterpriseImage(String enterpriseImage) {
		this.enterpriseImage = enterpriseImage;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public String getRecommendedUnitName() {
		return recommendedUnitName;
	}

	public void setRecommendedUnitName(String recommendedUnitName) {
		this.recommendedUnitName = recommendedUnitName;
	}

	public String getRecommendedUnitContant() {
		return recommendedUnitContant;
	}

	public void setRecommendedUnitContant(String recommendedUnitContant) {
		this.recommendedUnitContant = recommendedUnitContant;
	}

	public String getRecommendedUnitTel() {
		return recommendedUnitTel;
	}

	public void setRecommendedUnitTel(String recommendedUnitTel) {
		this.recommendedUnitTel = recommendedUnitTel;
	}

	public String getCompanyRegistrationTime() {
		return companyRegistrationTime;
	}

	public void setCompanyRegistrationTime(String companyRegistrationTime) {
		this.companyRegistrationTime = companyRegistrationTime;
	}

	public String getAppearOnMarketTime() {
		return appearOnMarketTime;
	}

	public void setAppearOnMarketTime(String appearOnMarketTime) {
		this.appearOnMarketTime = appearOnMarketTime;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public Boolean getJoinActivity() {
		return joinActivity;
	}

	public void setJoinActivity(Boolean joinActivity) {
		this.joinActivity = joinActivity;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

}
