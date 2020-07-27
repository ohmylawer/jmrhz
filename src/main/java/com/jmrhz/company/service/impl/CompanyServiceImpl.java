package com.jmrhz.company.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.CommonUtil;
import com.jmrhz.company.dto.CompanyDTO;
import com.jmrhz.company.dto.RecommendDTO;
import com.jmrhz.company.repository.CompanyRepository;
import com.jmrhz.company.service.ICompanyService;
import com.jmrhz.product.dto.ProductDTO;
import com.jmrhz.product.dto.ProductImgDTO;
import com.jmrhz.product.repository.ProductRepository;
import com.jmrhz.util.GetPathUtil;
import com.jmrhz.util.StringUtils;
import com.jmrhz.util.WordUtil;
import com.jmrhz.util.XMLUtils;
import com.jmrhz.util.ZipUtils;

import freemarker.template.TemplateException;

/**
 * 
 * @author tjx
 *
 */
@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ProductRepository productRepository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public CompanyDTO loadCompany(long id) throws Exception {
		return companyRepository.load(id);
	}

	@Override
	public CompanyDTO saveCompany(CompanyDTO companyDTO) throws Exception {
		if (null == companyDTO) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CompanyDTO company = new CompanyDTO();
		BeanUtils.copyProperties(companyDTO, company);
		// company.setId(new SnowflakeIdWorker(0, 0).nextId());
		company.setId(CommonUtil.genarateID());
		company.setSaveTime(simpleDateFormat.format(new Date()));
		companyRepository.save(company);
		return loadCompany(company.getId());
	}

	@Override
	public List<CompanyDTO> listCompanys(Map<String, Object> map) throws Exception {
		return companyRepository.queryList(map);
	}

	@Override
	public List<CompanyDTO> listCompanies(long[] longIDS) {
		return companyRepository.listCompanies(longIDS);
	}

	@Override
	public void saveCompanies(List<CompanyDTO> list) {
		companyRepository.saveBatch(list);
	}

	@Override
	public void deleteCompany(long id) throws Exception {
		CompanyDTO companyDTO = companyRepository.load(id);
		productRepository.deleteByBusinessLincese(companyDTO.getBusinessLicense());
		companyRepository.delete(id);
	}

	@Override
	public void deleteCompanies(long[] longIDS) throws Exception {
		String[] businessLicense = new String[longIDS.length];
		for (int i = 0; i < longIDS.length; i++) {
			businessLicense[i] = companyRepository.load(longIDS[i]).getBusinessLicense();
			if (null != businessLicense[i]) {
				List<ProductDTO> list = productRepository.loadByBusinessLincese(businessLicense[i]);
				long arr[] = new long[list.size()];
				for (int j = 0; j < list.size(); j++) {
					if (null != list.get(j)) {
						arr[j] = list.get(j).getId();
					}
				}
				if (arr.length != 0) {
					productRepository.deleteBatch(arr);
				}
			}
		}
		companyRepository.deleteBatch(longIDS);
	}

	@Override
	public CompanyDTO updateCompany(CompanyDTO companyDTO) throws Exception {
		if ((null == companyDTO) || (0 == companyDTO.getId())) {

			return null;
		}

		CompanyDTO companny = loadCompany(companyDTO.getId());

		BeanUtils.copyProperties(companyDTO, companny);

		companyRepository.update(companny);

		return companny;
	}

	@Override
	public List<Long> getIdList() {
		return companyRepository.getIdList();
	}

	public CompanyDTO loadByLicese(String id) throws Exception {
		return companyRepository.loadByLicese(id);
	}

	@Override
	public String createRecommendDoc(String recommendedName) throws IOException, TemplateException {
		List<RecommendDTO> recommendList = companyRepository.getRecommendInfo(recommendedName);

		String tempFileName = "report_2003.xml";
		Map<String, Object> map = new HashMap<String, Object>();
		String filePath = "";
		if (recommendList == null || recommendList.size() == 0) { // 企业用户下载时 没有推荐单位 文件名用公司名称
			map.put("relationList", new ArrayList<RecommendDTO>());
			map.put("recommendContant", "");
			map.put("recommendTel", "");
			map.put("year", "");
			map.put("month", "");
			map.put("day", "");
			List<CompanyDTO> companyList = companyRepository.queryList(null);
			filePath = GetPathUtil.getPath() + File.separator + recommendedName + ".doc";
			new WordUtil().createDoc(map, tempFileName, filePath);
			return filePath;
		}
		String recommendedUnitName = recommendList.get(0).getRecommendedUnitName() == null ? ""
				: recommendList.get(0).getRecommendedUnitName();
		int i = 0;
		for (RecommendDTO recommend : recommendList) {
			recommend.setIndex(i + 1);
			i++;
		}

		map.put("relationList", recommendList);
		map.put("recommendContant", recommendList.get(0).getRecommendContant());
		map.put("recommendTel", recommendList.get(0).getRecommendTel());
		Date writeDate = new Date();
		String writeDateStr = new SimpleDateFormat("yyyy-MM-dd").format(writeDate);
		String[] writeDateStrs = writeDateStr.split("-");
		String year = writeDateStrs[0];
		String month = writeDateStrs[1];
		String day = writeDateStrs[2];
		map.put("year", year);
		map.put("month", month);
		map.put("day", day);
		filePath = GetPathUtil.getPath() + File.separator + recommendedUnitName + recommendList.size() + ".doc";

		new WordUtil().createDoc(map, tempFileName, filePath);
		return filePath;
	}

	@Override
	public List<String> createCompanyDoc(String companyName) throws IOException {

		// Map<String, Object> queryMap = new HashMap<String, Object>();
		// queryMap.put("companyName", companyName);
		List<CompanyDTO> companyList = companyRepository.loadByCompanyName(companyName);
		List<String> filePaths = new ArrayList<String>();
		String tempFileName = "company_2003.xml";
		for (CompanyDTO company : companyList) {
			List<ProductDTO> productList = productRepository.loadByBusinessLincese(company.getBusinessLicense());
			List<ProductImgDTO> productImgList = new ArrayList<ProductImgDTO>();
			for (ProductDTO product : productList) {
				String paths = product.getProductPicture();
				String[] pathArr = paths.split(",");
				for (String path : pathArr) {
					path = GetPathUtil.getPath() + File.separator + path;
					String base64 = getImgBase64ByPath(path);
					ProductImgDTO productImg = new ProductImgDTO();
					productImg.setId(product.getId());
					productImg.setBase64Str(base64);
					productImgList.add(productImg);
				}

			}
			boolean continueJoin = company.getContinueJoin();
			boolean techCompany = company.getTechCompany();
			boolean appearOnMarket = company.getAppearOnMarket();
			boolean technologyFinancialServicesDemand = company.getTechnologyFinancialServicesDemand();

			String[] companyPaths = company.getEnterpriseImage().split(",");

			List<String> companyImgList = new ArrayList<String>();
			for (String companyPath : companyPaths) {
				String enterpriseImagePath = GetPathUtil.getPath() + File.separator + companyPath;
				String base64Str = getImgBase64ByPath(enterpriseImagePath);
				companyImgList.add(base64Str);
			}

			String accessoryName = "";
			String accessoryPath = company.getAccessory();
			if (accessoryPath != null && !"".equals(accessoryPath)) {
				company.setAccessory(GetPathUtil.getPath() + File.separator + company.getAccessory());
			} else {
				company.setAccessory("");
			}
			if (accessoryPath.contains("\\") && accessoryPath.lastIndexOf(".") >= 0) {
				accessoryName = accessoryPath.substring(accessoryPath.lastIndexOf("\\") + 1,
						accessoryPath.lastIndexOf("."));
			} else if (accessoryPath.lastIndexOf(".") < 0 && accessoryPath.contains("\\")) {
				accessoryName = accessoryPath.substring(accessoryPath.lastIndexOf("\\") + 1, accessoryPath.length());

			} else {
				accessoryName = accessoryPath;
			}

			try {
				String filePath = GetPathUtil.getPath() + File.separator + companyName + productList.size() + ".doc";
				Map map = org.apache.commons.beanutils.BeanUtils.describe(company);
				map.put("continueJoinStr", formatBooleanStr(continueJoin));
				map.put("techCompanyStr", formatBooleanStr(techCompany));
				map.put("appearOnMarketStr", formatBooleanStr(appearOnMarket));
				map.put("technologyFinancialServicesDemandStr", formatBooleanStr(technologyFinancialServicesDemand));
				map.put("companyImgList", companyImgList);
				map.put("accessoryName", accessoryName);
				map.put("productList", productList);
				map.put("productImgList", productImgList);
				new WordUtil().createDoc(map, tempFileName, filePath);
				filePaths.add(filePath);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		return filePaths;
	}

	private String formatBooleanStr(boolean flag) {

		if (flag) {
			return "是";
		}

		return "否";
	}

	private String getImgBase64ByPath(String path) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		FileInputStream inputStream = null;
		FileInputStream fis = null;
		File file = new File(path);
		String base64Str = "";
		try {
			fis = new FileInputStream(file);
			byte[] data = new byte[fis.available()];
			fis.read(data);
			int len = 0;
			byte[] buffer = new byte[1024];
			inputStream = new FileInputStream(file);
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			base64Str = Base64.getEncoder().encodeToString(outputStream.toByteArray());
			return base64Str;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return "";
		} finally {
			fis.close();
			inputStream.close();
			outputStream.close();
		}

	}
}
