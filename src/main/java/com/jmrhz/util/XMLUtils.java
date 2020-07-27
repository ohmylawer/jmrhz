package com.jmrhz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.CommonUtil;
import com.jmrhz.company.dto.CompanyDTO;
import com.jmrhz.product.dto.ProductDTO;

public class XMLUtils {

	static SAXReader reader = new SAXReader();

	public static String WriteToXML(Map<CompanyDTO, List<ProductDTO>> saveMap, File zipPathFile, String zipDirName)
			throws Exception {
		File file = null;
		Document document = DocumentHelper.createDocument();
		Element firstElement = document.addElement("jmrhz");
		Element element;
		Element productElement;
		for (Map.Entry<CompanyDTO, List<ProductDTO>> entry : saveMap.entrySet()) {
			element = firstElement.addElement("company");
			CompanyDTO company = entry.getKey();
			file = new File(zipPathFile.getPath() + File.separator + zipDirName + ".xml");
			List<ProductDTO> productList = entry.getValue();
			element.addAttribute("companyId", String.valueOf(company.getId()));
			element.addAttribute("companyName", company.getCompanyName());
			element.addAttribute("businessLicense", company.getBusinessLicense());
			element.addAttribute("legalPerson", company.getLegalPerson());
			element.addAttribute("companyRegistrationTime", company.getCompanyRegistrationTime());
			element.addAttribute("joined", company.getJoined());
			element.addAttribute("continueJoin", company.getContinueJoin().toString());
			element.addAttribute("principal", company.getPrincipal());
			element.addAttribute("telephone", company.getTelephone());
			element.addAttribute("fixedlineTelephone", company.getFixedlineTelephone());
			element.addAttribute("address", company.getAddress());
			element.addAttribute("postcode", company.getPostcode());
			element.addAttribute("productType", company.getProductType());
			element.addAttribute("technologyField", company.getTechnologyField());
			element.addAttribute("unitNature", company.getUnitNature());
			element.addAttribute("sacle", company.getSacle());
			element.addAttribute("techCompany", company.getTechCompany().toString());
			element.addAttribute("techCompanyLevel", company.getTechCompanyLevel());
			element.addAttribute("appearOnMarket", company.getAppearOnMarket().toString());
			element.addAttribute("appearOnMarketTime", company.getAppearOnMarketTime());
			element.addAttribute("registeredCaptital", company.getRegisteredCaptital().toString());
			element.addAttribute("totalEnterpriseAssets", company.getTotalEnterpriseAssets().toString());
			element.addAttribute("militaryTurnover", company.getMilitaryTurnover().toString());
			element.addAttribute("avgMilitaryTurnover", company.getAvgMilitaryTurnover().toString());
			element.addAttribute("merchandiseTurnover", company.getMerchandiseTurnover().toString());
			element.addAttribute("avgMerchandiseTurnover", company.getAvgMerchandiseTurnover().toString());
			element.addAttribute("enterpriseProfile", company.getEnterpriseProfile());
			element.addAttribute("personnelStructure", company.getPersonnelStructure());
			element.addAttribute("sharesConstitute", company.getSharesConstitute());
			element.addAttribute("patentSituation", company.getPatentSituation());
			element.addAttribute("prizeSituation", company.getPrizeSituation());
			element.addAttribute("paticipationInLargeProject", company.getPaticipationInLargeProject());
			element.addAttribute("technologyFinancialServicesDemand",
					company.getTechnologyFinancialServicesDemand().toString());
			element.addAttribute("technologyFinancialServicesDemandType",
					company.getTechnologyFinancialServicesDemandType());
			element.addAttribute("technologyFinancialServicesDemandDescription",
					company.getTechnologyFinancialServicesDemandDescription());
			element.addAttribute("enterpriseImage", company.getEnterpriseImage());
			element.addAttribute("accessory", company.getAccessory());
			element.addAttribute("saveTime", company.getSaveTime());
			element.addAttribute("recommendedUnitName", company.getRecommendedUnitName());
			element.addAttribute("recommendedUnitContant", company.getRecommendedUnitContant());
			element.addAttribute("recommendedUnitTel", company.getRecommendedUnitTel());
			element.addAttribute("joinActivity", String.valueOf(company.getJoinActivity()));
			for (ProductDTO product : productList) {
				productElement = element.addElement("product");
				productElement.addAttribute("productId", String.valueOf(product.getId()));
				productElement.addAttribute("productName", product.getProductName());
				productElement.addAttribute("previousExhibitions", product.getPreviousExhibitions());
				productElement.addAttribute("productType", product.getProductType());
				productElement.addAttribute("technicalMaturity", product.getTechnicalMaturity());
				productElement.addAttribute("advancedDegree", product.getAdvancedDegree());
				productElement.addAttribute("exhibitionArea", product.getExhibitionArea());
				productElement.addAttribute("selfIntellectual", product.getSelfIntellectual().toString());
				productElement.addAttribute("selfIntellectualExplain", product.getSelfIntellectualExplain());
				productElement.addAttribute("productInstruction", product.getProductInstruction());
				productElement.addAttribute("mainProductIndicators", product.getMainProductIndicators());
				productElement.addAttribute("situationOfApplication", product.getSituationOfApplication());
				productElement.addAttribute("inputsndReturns", product.getInputsndReturns());
				productElement.addAttribute("patentSituation", product.getPatentSituation());
				productElement.addAttribute("prizeSituation", product.getPrizeSituation());
				productElement.addAttribute("displayExhibits", product.getDisplayExhibits());
				productElement.addAttribute("displayEffect", product.getDisplayEffect());
				productElement.addAttribute("displayShape", product.getDisplayShape());
				productElement.addAttribute("displayWeight", product.getDisplayWeight());
				productElement.addAttribute("bearingRequirements", product.getBearingRequirements());
				productElement.addAttribute("siteRequirements", product.getSiteRequirements());
				productElement.addAttribute("productPicture", product.getProductPicture());
				productElement.addAttribute("accessory", product.getAccessory());
				productElement.addAttribute("productTechnicalNumber", product.getProductTechnicalNumber());
				productElement.addAttribute("companyBusinessLinces", product.getBusinessLicense());
				productElement.addAttribute("companyName", product.getCompanyName());
			}

		}
		Writer out = null;
		XMLWriter writer = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()), "UTF-8");
			OutputFormat format = new OutputFormat("\t", true);
			format.setTrimText(true);
			format.setEncoding("UTF-8");
			writer = new XMLWriter(out, format);
			writer.write(document);
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();
			writer.close();
		}
		return file.getPath();
	}

	public static JSONObject ReaderFromXML(String path) throws Exception {
		reader.setEncoding("utf-8");
		// Document docunment = reader
		// .read(new BufferedReader(new InputStreamReader(new FileInputStream(new
		// File(path)), "UTF-8")));
		Document docunment = reader.read(new File(path));
		JSONObject jsonObject = new JSONObject();
		String oldCompanyBusinessLicense = null;
		String newCompanyBusinessLicense = null;
		String oldProductBusinessLicense = null;
		String newProductBusinessLicense = null;
		Element node = docunment.getRootElement();
		List<CompanyDTO> companyList = new ArrayList<>();
		List<ProductDTO> productList = new ArrayList<>();
		listNodes(node, oldCompanyBusinessLicense, newCompanyBusinessLicense, oldProductBusinessLicense,
				newProductBusinessLicense, companyList, productList);
		jsonObject.put("company", companyList);
		jsonObject.put("product", productList);
		return jsonObject;
	}

	public static void listNodes(Element node, String oldCompanyBusinessLicense, String newCompanyBusinessLicense,
			String oldProductBusinessLicense, String newProductBusinessLicense, List<CompanyDTO> companyList,
			List<ProductDTO> productList) throws Exception {

		CompanyDTO companyDTO = new CompanyDTO();
		ProductDTO productDTO = new ProductDTO();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		List<Attribute> list = node.attributes();
		if ("company".equals(node.getName())) {
			for (Attribute attr : list) {
				switch (attr.getName()) {
				case "companyId":
					companyDTO.setId(Long.parseLong(attr.getValue()));
					break;
				case "companyName":
					companyDTO.setCompanyName(attr.getValue());
					break;
				case "businessLicense":
					oldCompanyBusinessLicense = attr.getValue();
					companyDTO.setBusinessLicense(attr.getValue());
					break;
				case "legalPerson":
					companyDTO.setLegalPerson(attr.getValue());
					break;
				case "companyRegistrationTime":
					companyDTO.setCompanyRegistrationTime(attr.getValue());
					break;
				case "joined":
					companyDTO.setJoined(attr.getValue());
					break;
				case "continueJoin":
					if ("false".equals(attr.getValue())) {
						companyDTO.setContinueJoin(false);
					} else {
						companyDTO.setContinueJoin(true);
					}
					break;
				case "principal":
					companyDTO.setPrincipal(attr.getValue());
					break;
				case "telephone":
					companyDTO.setTelephone(attr.getValue());
					break;
				case "fixedlineTelephone":
					companyDTO.setFixedlineTelephone(attr.getValue());
					break;
				case "address":
					companyDTO.setAddress(attr.getValue());
					break;
				case "postcode":
					companyDTO.setPostcode(attr.getValue());
					break;
				case "productType":
					companyDTO.setProductType(attr.getValue());
					break;
				case "technologyField":
					companyDTO.setTechnologyField(attr.getValue());
					break;
				case "unitNature":
					companyDTO.setUnitNature(attr.getValue());
					break;
				case "sacle":
					companyDTO.setSacle(attr.getValue());
					break;
				case "techCompany":
					if ("false".equals(attr.getValue())) {
						companyDTO.setTechCompany(false);
					} else {
						companyDTO.setTechCompany(true);
					}
					break;
				case "techCompanyLevel":
					companyDTO.setTechCompanyLevel(attr.getValue());
					break;
				case "appearOnMarket":
					if ("false".equals(attr.getValue())) {
						companyDTO.setAppearOnMarket(false);
					} else {
						companyDTO.setAppearOnMarket(true);
					}
					break;
				case "appearOnMarketTime":
					companyDTO.setAppearOnMarketTime(attr.getValue());
					break;
				case "registeredCaptital":
					companyDTO.setRegisteredCaptital(new BigDecimal(attr.getValue()));
					break;
				case "totalEnterpriseAssets":
					companyDTO.setTotalEnterpriseAssets(new BigDecimal(attr.getValue()));
					break;
				case "militaryTurnover":
					companyDTO.setMilitaryTurnover(new BigDecimal(attr.getValue()));
					break;
				case "avgMilitaryTurnover":
					companyDTO.setAvgMilitaryTurnover(new BigDecimal(attr.getValue()));
					break;
				case "merchandiseTurnover":
					companyDTO.setMerchandiseTurnover(new BigDecimal(attr.getValue()));
					break;
				case "avgMerchandiseTurnover":
					companyDTO.setAvgMerchandiseTurnover(new BigDecimal(attr.getValue()));
					break;
				case "enterpriseProfile":
					companyDTO.setEnterpriseProfile(attr.getValue());
					break;
				case "personnelStructure":
					companyDTO.setPersonnelStructure(attr.getValue());
					break;
				case "sharesConstitute":
					companyDTO.setSharesConstitute(attr.getValue());
					break;
				case "patentSituation":
					companyDTO.setPatentSituation(attr.getValue());
					break;
				case "prizeSituation":
					companyDTO.setPrizeSituation(attr.getValue());
					break;
				case "paticipationInLargeProject":
					companyDTO.setPaticipationInLargeProject(attr.getValue());
					break;
				case "technologyFinancialServicesDemand":
					if ("false".equals(attr.getValue())) {
						companyDTO.setTechnologyFinancialServicesDemand(false);
					} else {
						companyDTO.setTechnologyFinancialServicesDemand(true);
					}
					break;
				case "technologyFinancialServicesDemandType":
					companyDTO.setTechnologyFinancialServicesDemandType(attr.getValue());
					break;
				case "technologyFinancialServicesDemandDescription":
					companyDTO.setTechnologyFinancialServicesDemandDescription(attr.getValue());
					break;
				case "enterpriseImage":
					companyDTO.setEnterpriseImage(attr.getValue());
					break;
				case "accessory":
					companyDTO.setAccessory(attr.getValue());
					break;
				case "saveTime":
					companyDTO.setSaveTime(simpleDateFormat.format(new Date()));
					break;
				case "recommendedUnitName":
					companyDTO.setRecommendedUnitName(attr.getValue());
					break;
				case "recommendedUnitContant":
					companyDTO.setRecommendedUnitContant(attr.getValue());
					break;
				case "recommendedUnitTel":
					companyDTO.setRecommendedUnitTel(attr.getValue());
					break;
				case "joinActivity":
					if ("false".equals(attr.getValue())) {
						companyDTO.setJoinActivity(false);
					} else {
						companyDTO.setJoinActivity(true);
					}
					break;
				}
			}
			companyDTO.setId(CommonUtil.genarateID());
			companyList.add(companyDTO);
		} else if ("product".equals(node.getName())) {
			for (Attribute attr : list) {
				switch (attr.getName()) {
				case "productId":
					companyDTO.setId(Long.parseLong(attr.getValue()));
					break;
				case "productName":
					productDTO.setProductName(attr.getValue());
					break;
				case "previousExhibitions":
					productDTO.setPreviousExhibitions(attr.getValue());
					break;
				case "productType":
					productDTO.setProductType(attr.getValue());
					break;
				case "technicalMaturity":
					productDTO.setTechnicalMaturity(attr.getValue());
					break;
				case "advancedDegree":
					productDTO.setAdvancedDegree(attr.getValue());
					break;
				case "exhibitionArea":
					productDTO.setExhibitionArea(attr.getValue());
					break;
				case "selfIntellectual":
					if ("false".equals(attr.getValue())) {
						productDTO.setSelfIntellectual(false);
					} else {
						productDTO.setSelfIntellectual(true);
					}
					break;
				case "selfIntellectualExplain":
					productDTO.setSelfIntellectualExplain(attr.getValue());
					break;
				case "productInstruction":
					productDTO.setProductInstruction(attr.getValue());
					break;
				case "mainProductIndicators":
					productDTO.setMainProductIndicators(attr.getValue());
					break;
				case "situationOfApplication":
					productDTO.setSituationOfApplication(attr.getValue());
					break;
				case "inputsndReturns":
					productDTO.setInputsndReturns(attr.getValue());
					break;
				case "patentSituation":
					productDTO.setPatentSituation(attr.getValue());
					break;
				case "prizeSituation":
					productDTO.setPrizeSituation(attr.getValue());
					break;
				case "displayExhibits":
					productDTO.setDisplayExhibits(attr.getValue());
					break;
				case "displayEffect":
					productDTO.setDisplayEffect(attr.getValue());
					break;
				case "displayShape":
					productDTO.setDisplayShape(attr.getValue());
					break;
				case "displayWeight":
					productDTO.setDisplayWeight(attr.getValue());
					break;
				case "bearingRequirements":
					productDTO.setBearingRequirements(attr.getValue());
					break;
				case "siteRequirements":
					productDTO.setSiteRequirements(attr.getValue());
					break;
				case "productPicture":
					productDTO.setProductPicture(attr.getValue());
					break;
				case "accessory":
					productDTO.setAccessory(attr.getValue());
					break;
				case "productTechnicalNumber":
					productDTO.setProductTechnicalNumber(attr.getValue());
					break;
				case "companyBusinessLinces":
					productDTO.setBusinessLicense(attr.getValue());
					break;
				case "companyName":
					productDTO.setCompanyName(attr.getValue());
					break;
				}
			}
			productList.add(productDTO);
		}

		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			Element e = it.next();
			listNodes(e, oldCompanyBusinessLicense, newCompanyBusinessLicense, oldProductBusinessLicense,
					newProductBusinessLicense, companyList, productList);
		}
	}
}
