package com.jmrhz.product.dto;

import java.io.Serializable;
import java.sql.Blob;

/**
 * 产品
 * 
 * @author tjx
 *
 */
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = -1;

	private long id;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 往届参展情况
	 */
	private String previousExhibitions;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 技术成熟度
	 */
	private String technicalMaturity;
	/**
	 * 先进程度
	 */
	private String advancedDegree;
	/**
	 * 技术领域
	 */
	private String exhibitionArea;
	/**
	 * 是否自主知识产权
	 */
	private Boolean selfIntellectual;
	/**
	 * 自主知识产权说明
	 */
	private String selfIntellectualExplain;
	/**
	 * 产品简介
	 */
	private String productInstruction;
	/**
	 * 主要产品指标
	 */
	private String mainProductIndicators;
	/**
	 * 应用情况
	 */
	private String situationOfApplication;
	/**
	 * 投入和收益
	 */
	private String inputsndReturns;
	/**
	 * 专利情况
	 */
	private String patentSituation;
	/**
	 * 获奖情况
	 */
	private String prizeSituation;
	/**
	 * 展品形式
	 */
	private String displayExhibits;
	/**
	 * 展示效果
	 */
	private String displayEffect;
	/**
	 * 展品尺寸
	 */
	private String displayShape;
	/**
	 * 展品重量
	 */
	private String displayWeight;
	/**
	 * 承重要求
	 */
	private String bearingRequirements;
	/**
	 * 场地要求
	 */
	private String siteRequirements;
	/**
	 * 产品图片
	 */
	private String productPicture;
	/**
	 * 附件
	 */
	private String accessory;
	/**
	 * 产品技术编号
	 */
	private String productTechnicalNumber;
	/**
	 * 公司ID
	 */
	private String businessLicense;
	/**
	 * 公司名称
	 */
	private String companyName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPreviousExhibitions() {
		return previousExhibitions;
	}

	public void setPreviousExhibitions(String previousExhibitions) {
		this.previousExhibitions = previousExhibitions;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getTechnicalMaturity() {
		return technicalMaturity;
	}

	public void setTechnicalMaturity(String technicalMaturity) {
		this.technicalMaturity = technicalMaturity;
	}

	public String getAdvancedDegree() {
		return advancedDegree;
	}

	public void setAdvancedDegree(String advancedDegree) {
		this.advancedDegree = advancedDegree;
	}

	public String getExhibitionArea() {
		return exhibitionArea;
	}

	public void setExhibitionArea(String exhibitionArea) {
		this.exhibitionArea = exhibitionArea;
	}

	public Boolean getSelfIntellectual() {
		return selfIntellectual;
	}

	public void setSelfIntellectual(Boolean selfIntellectual) {
		this.selfIntellectual = selfIntellectual;
	}

	public String getSelfIntellectualExplain() {
		return selfIntellectualExplain;
	}

	public void setSelfIntellectualExplain(String selfIntellectualExplain) {
		this.selfIntellectualExplain = selfIntellectualExplain;
	}

	public String getProductInstruction() {
		return productInstruction;
	}

	public void setProductInstruction(String productInstruction) {
		this.productInstruction = productInstruction;
	}

	public String getMainProductIndicators() {
		return mainProductIndicators;
	}

	public void setMainProductIndicators(String mainProductIndicators) {
		this.mainProductIndicators = mainProductIndicators;
	}

	public String getSituationOfApplication() {
		return situationOfApplication;
	}

	public void setSituationOfApplication(String situationOfApplication) {
		this.situationOfApplication = situationOfApplication;
	}

	public String getInputsndReturns() {
		return inputsndReturns;
	}

	public void setInputsndReturns(String inputsndReturns) {
		this.inputsndReturns = inputsndReturns;
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

	public String getDisplayExhibits() {
		return displayExhibits;
	}

	public void setDisplayExhibits(String displayExhibits) {
		this.displayExhibits = displayExhibits;
	}

	public String getDisplayEffect() {
		return displayEffect;
	}

	public void setDisplayEffect(String displayEffect) {
		this.displayEffect = displayEffect;
	}

	public String getDisplayShape() {
		return displayShape;
	}

	public void setDisplayShape(String displayShape) {
		this.displayShape = displayShape;
	}

	public String getDisplayWeight() {
		return displayWeight;
	}

	public void setDisplayWeight(String displayWeight) {
		this.displayWeight = displayWeight;
	}

	public String getBearingRequirements() {
		return bearingRequirements;
	}

	public void setBearingRequirements(String bearingRequirements) {
		this.bearingRequirements = bearingRequirements;
	}

	public String getSiteRequirements() {
		return siteRequirements;
	}

	public void setSiteRequirements(String siteRequirements) {
		this.siteRequirements = siteRequirements;
	}

	public String getProductTechnicalNumber() {
		return productTechnicalNumber;
	}

	public void setProductTechnicalNumber(String productTechnicalNumber) {
		this.productTechnicalNumber = productTechnicalNumber;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
