package com.jmrhz.company.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.CommonUtil;
import com.common.utils.IntegerValueFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.io.Files;
import com.jmrhz.company.dto.CompanyDTO;
import com.jmrhz.company.service.ICompanyService;
import com.jmrhz.product.dto.ProductDTO;
import com.jmrhz.product.service.IProductService;
import com.jmrhz.util.ExcelUtil;
import com.jmrhz.util.GetPathUtil;
import com.jmrhz.util.PicUtil;
import com.jmrhz.util.StringUtils;
import com.jmrhz.util.XMLUtils;
import com.jmrhz.util.ZipUtils;

/**
 * 描述：请修改类、方法注释控制层
 * 
 * @author Team
 * @date Thu Apr 11 21:12:10 CST 2019
 */
@RestController
@RequestMapping(value = "/company")
@CrossOrigin
public class CompanyController {
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IProductService productService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/list")
	public void listCompany(final int pageNum, final int pageSize) {

		try {
			PageHelper.startPage(pageNum, pageSize);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyName", CommonUtil.getRequest().getParameter("companyName"));
			map.put("businessLicense", CommonUtil.getRequest().getParameter("businessLicense"));
			map.put("companyRegistrationTime", CommonUtil.getRequest().getParameter("companyRegistrationTime"));
			map.put("unitNature", CommonUtil.getRequest().getParameter("unitNature"));
			map.put("joined", CommonUtil.getRequest().getParameter("joined"));
			map.put("continueJoin", CommonUtil.getRequest().getParameter("continueJoin"));
			map.put("principal", CommonUtil.getRequest().getParameter("principal"));
			map.put("telephone", CommonUtil.getRequest().getParameter("telephone"));
			map.put("productType", CommonUtil.getRequest().getParameter("productType"));
			map.put("appearOnMarket", CommonUtil.getRequest().getParameter("appearOnMarket"));
			map.put("techCompany", CommonUtil.getRequest().getParameter("techCompany"));
			map.put("technologyFinancialServicesDemand",
					CommonUtil.getRequest().getParameter("technologyFinancialServicesDemand"));
			map.put("recommendedUnitName", CommonUtil.getRequest().getParameter("recommendedUnitName"));

			PageInfo<CompanyDTO> pageInfo = new PageInfo<CompanyDTO>(companyService.listCompanys(map));

			JSONObject obj = new JSONObject();
			obj.put("total", pageInfo.getTotal());
			obj.put("data", JSONArray.parseArray(JSON.toJSONString(pageInfo.getList(), new IntegerValueFilter())));

			CommonUtil.sendJsonData(CommonUtil.getResponse(), obj.toJSONString());

		} catch (Exception e) {

			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/load")
	public JSONObject loadCompany(final String id) {
		JSONObject obj = new JSONObject();
		try {
			CompanyDTO companyDTO = companyService.loadCompany(Long.parseLong(id));
			obj.put("data", companyDTO);
		} catch (Exception e) {

			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			obj.put("data", "ERROR");
		}
		return obj;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void save(@RequestBody CompanyDTO companyDTO) {
		try {
			if (CommonUtil.isEmpty(companyDTO)) {
				CommonUtil.error(CommonUtil.getResponse(), "缺少要保存的数据！");
				return;
			}
			CompanyDTO companyDTO1 = companyService.saveCompany(companyDTO);
			CommonUtil.success(CommonUtil.getResponse(), JSON.toJSONString(companyDTO1, new IntegerValueFilter()));

		} catch (Exception e) {
			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void updatePlanerror(@RequestBody CompanyDTO companyDTO) {

		try {

			companyDTO.setId(companyDTO.getId());

			companyService.updateCompany(companyDTO);

			CommonUtil.success(CommonUtil.getResponse(), JSON.toJSONString(companyDTO));

		} catch (Exception e) {

			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	public JSONObject save(@RequestParam("file") MultipartFile file) {
		JSONObject json = new JSONObject();
		try {
			if (CommonUtil.isEmpty(file)) {
				json.put("message", "缺少要保存的数据！");
				return json;
			}
			byte[] bytes = file.getBytes();
			String filename = file.getOriginalFilename();
			String name = filename.substring(0, filename.lastIndexOf('.'));
			String saveName = name + "-" + new Date().getTime() + filename.split(name)[1];
			String path = GetPathUtil.getPath() + File.separator + saveName;
			Files.write(bytes, new File(path));
			json.put("path", saveName);
			json.put("name", saveName);
			json.put("message", "上传成功");

		} catch (Exception e) {
			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				json.put("message", "上传失败");
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			json.put("message", "上传失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "/getPic")
	@ResponseBody
	public String getPic(@RequestParam("path") String path) throws Exception {
		File file = new File(GetPathUtil.getPath() + File.separator + path);
		int len = 0;
		byte[] buffer = new byte[1024];
		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes, 0, inputStream.available());
		String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
		outputStream.close();
		inputStream.close();
		return base64;
	}

	/**
	 * 描述：删除请修改类、方法注释
	 * 
	 * @param id
	 *            请修改类、方法注释id
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void deleteById(@RequestBody long id) {
		try {
			companyService.deleteCompany(id);
			CommonUtil.success(CommonUtil.getResponse(), "" + id);
		} catch (Exception e) {
			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 描述：删除请修改类、方法注释
	 * 
	 * @param id
	 *            请修改类、方法注释id
	 */
	@RequestMapping(value = "/delete")
	public void deleteByIds(@RequestParam String ids) {
		try {
			if (CommonUtil.isEmpty(ids)) {
				CommonUtil.error(CommonUtil.getResponse(), "请选择要删除的记录！");
				return;
			}
			String[] id = ids.split(",");
			long[] longIDS = new long[id.length];
			for (int i = 0; i < id.length; i++) {
				longIDS[i] = Long.valueOf(id[i]);
			}
			if (CommonUtil.isEmpty(longIDS)) {
				CommonUtil.error(CommonUtil.getResponse(), "请选择要删除的记录！");
				return;
			}
			companyService.deleteCompanies(longIDS);
			CommonUtil.success(CommonUtil.getResponse(), "记录删除成功！");
		} catch (Exception e) {
			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String ids, Boolean flag)
			throws Exception {
		long[] longIDS;
		if (StringUtil.isEmpty(ids)) {
			longIDS = companyService.getIdList().stream().mapToLong(l -> l.longValue()).toArray();
		} else {
			String[] id = ids.split(",");
			longIDS = new long[id.length];
			for (int i = 0; i < id.length; i++) {
				longIDS[i] = Long.valueOf(id[i]);
			}
		}
		String modulePath;
		List<CompanyDTO> companyList = companyService.listCompanies(longIDS);
		Map<CompanyDTO, List<ProductDTO>> saveMap = new HashMap<>();
		if (flag) {
			companyList.stream().forEach(companyDTO -> {
				try {
					List<ProductDTO> productList = productService
							.loadByBusinessLicense(companyDTO.getBusinessLicense());
					saveMap.put(companyDTO, productList);
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			});
			modulePath = GetPathUtil.getModulesPath() + File.separator + "信息表.xls";
		} else {
			companyList.stream().forEach(companyDTO -> saveMap.put(companyDTO, null));
			modulePath = GetPathUtil.getModulesPath() + File.separator + "企业信息表.xls";

		}
		ExcelUtil.createExcel(request, response, saveMap, modulePath);
	}

	@RequestMapping(value = "/exportAll")
	public void exportAll(HttpServletRequest request, HttpServletResponse response, String ids) throws Exception {
		long[] longIDS;
		if (StringUtil.isEmpty(ids)) {
			longIDS = this.companyService.getIdList().stream().mapToLong((l) -> {
				return l;
			}).toArray();
		} else {
			String[] id = ids.split(",");
			longIDS = new long[id.length];

			for (int i = 0; i < id.length; ++i) {
				longIDS[i] = Long.valueOf(id[i]);
			}
		}

		List<CompanyDTO> companyList = this.companyService.listCompanies(longIDS);
		Map<CompanyDTO, List<ProductDTO>> saveMap = new HashMap();
		JSONArray hasRecommededCompanyArr = new JSONArray();
		JSONArray withoutRecommededCompanyArr = new JSONArray();
		long nowDate = (new Date()).getTime();

		File zipPathFile;
		String zipDirName;
		JSONObject companyJson;
		String dirPath;
		String finalName;
		for (int i = 0; i < companyList.size(); ++i) {
			companyJson = new JSONObject();
			CompanyDTO companyDTO = (CompanyDTO) companyList.get(i);
			if (StringUtil.isNotEmpty(companyDTO.getRecommendedUnitName())) {
				File RUDir = new File(
						GetPathUtil.getPath() + File.separator + companyDTO.getRecommendedUnitName() + "-" + nowDate);
				if (!RUDir.exists()) {
					RUDir.mkdir();
				}

				companyJson.put("zipDirPath", RUDir.getPath());
				companyJson.put("zipDirName", RUDir.getName());
				zipPathFile = new File(
						RUDir.getPath() + File.separator + companyDTO.getCompanyName() + "-" + (new Date()).getTime());
				zipDirName = RUDir.getName() + File.separator + zipPathFile.getName();
				companyJson.put("finalName", zipDirName);
				hasRecommededCompanyArr.add(companyJson);
			} else {
				zipPathFile = new File(GetPathUtil.getPath() + File.separator + companyDTO.getCompanyName() + "-"
						+ (new Date()).getTime());
				companyJson.put("zipDirPath", zipPathFile.getPath());
				companyJson.put("zipDirName", zipPathFile.getName());
				zipDirName = zipPathFile.getName();
				companyJson.put("finalName", zipDirName);
				withoutRecommededCompanyArr.add(companyJson);
			}

			try {
				zipPathFile.mkdir();
				String[] companyImgPath = companyDTO.getEnterpriseImage().split(",");
				String[] company = companyImgPath;
				int a = companyImgPath.length;

				File companyRemoveFile;
				File companyReciveFile;
				for (int j = 0; j < companyImgPath.length; j++) {
					dirPath = company[j];
					companyRemoveFile = new File(GetPathUtil.getPath() + File.separator + dirPath);
					companyReciveFile = new File(zipDirName + File.separator + companyRemoveFile.getName());
					PicUtil.copPic(companyRemoveFile.getAbsolutePath(),
							(new File(GetPathUtil.getPath() + File.separator + companyReciveFile.getPath()))
									.getAbsolutePath());
				}

				finalName = companyDTO.getAccessory();
				if (finalName != null && !"".equals(finalName)) {
					companyRemoveFile = new File(GetPathUtil.getPath() + File.separator + finalName);
					companyReciveFile = new File(zipDirName + File.separator + companyRemoveFile.getName());
					PicUtil.copPic(companyRemoveFile.getAbsolutePath(),
							(new File(GetPathUtil.getPath() + File.separator + companyReciveFile.getPath()))
									.getAbsolutePath());
				}

				this.companyService.updateCompany(companyDTO);
				List<ProductDTO> productList = this.productService
						.loadByBusinessLicense(companyDTO.getBusinessLicense());
				for (int j = 0; j < productList.size(); j++) {
					File productDir = new File(
							zipPathFile.getPath() + File.separator + productList.get(j).getProductName());
					productDir.mkdir();
					String[] productImgPath = productList.get(j).getProductPicture().split(",");

					File productRemoveFile;
					for (String path : productImgPath) {
						productRemoveFile = new File(GetPathUtil.getPath() + File.separator + path);
						new File(zipDirName + File.separator + productList.get(j).getProductName() + File.separator
								+ productRemoveFile.getName());
						PicUtil.copPic(productRemoveFile.getAbsolutePath(),
								(new File(GetPathUtil.getPath() + File.separator + zipDirName + File.separator
										+ productList.get(j).getProductName() + File.separator
										+ productRemoveFile.getName())).getAbsolutePath());
					}

					String productAccessoryPath = productList.get(j).getAccessory();
					if (productAccessoryPath != null && !"".equals(productAccessoryPath)) {
						productRemoveFile = new File(GetPathUtil.getPath() + File.separator + productAccessoryPath);
						new File(zipDirName + File.separator + productList.get(j).getProductName() + File.separator
								+ productRemoveFile.getName());
						PicUtil.copPic(productRemoveFile.getAbsolutePath(),
								(new File(GetPathUtil.getPath() + File.separator + zipDirName + File.separator
										+ productList.get(j).getProductName() + File.separator
										+ productRemoveFile.getName())).getAbsolutePath());
					}

					try {
						this.productService.updateProduct(productList.get(j));
					} catch (Exception e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}

				}
				saveMap.put(companyDTO, productList);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		JSONArray allCompanyArr = new JSONArray();
		allCompanyArr.addAll(hasRecommededCompanyArr);
		allCompanyArr.addAll(withoutRecommededCompanyArr);
		boolean isAllBelongSamePath = true;

		JSONObject anyCompany;
		String zipDirPath;
		String XMLpath;
		for (int i = 0; i < allCompanyArr.size() - 1; ++i) {
			anyCompany = (JSONObject) allCompanyArr.get(i);
			JSONObject nextCompanyJson = (JSONObject) allCompanyArr.get(i + 1);
			zipDirPath = (String) anyCompany.get("zipDirName");
			XMLpath = (String) nextCompanyJson.get("zipDirName");
			if (!zipDirPath.equals(XMLpath)) {
				isAllBelongSamePath = false;
				break;
			}
		}

		String finalZipPath = "";
		if (isAllBelongSamePath) {
			if (hasRecommededCompanyArr.size() == 0) {
				anyCompany = (JSONObject) ((JSONObject) withoutRecommededCompanyArr.get(0));
				finalZipPath = (String) anyCompany.get("zipDirPath");
			} else {
				anyCompany = (JSONObject) ((JSONObject) hasRecommededCompanyArr.get(0));
				finalZipPath = (String) anyCompany.get("zipDirPath");
			}
		} else {
			finalZipPath = GetPathUtil.getPath() + File.separator + "all-" + (new Date()).getTime();
			zipPathFile = new File(finalZipPath);
			if (!zipPathFile.exists()) {
				zipPathFile.mkdir();
			}
		}

		Iterator a = allCompanyArr.iterator();

		String reportPath;

		String dirName;
		File oldReportFile;
		while (a.hasNext()) {
			Object company = a.next();
			companyJson = (JSONObject) company;
			XMLpath = (String) companyJson.get("zipDirName");
			dirPath = (String) companyJson.get("zipDirPath");
			finalName = (String) companyJson.get("finalName");
			String finalPath = GetPathUtil.getPath() + File.separator + finalName;
			dirName = (new File(finalPath)).getName().split("-")[0];
			String recommededName = XMLpath.split("-")[0];
			reportPath = this.companyService.createRecommendDoc(recommededName);

			if (reportPath != "") {
				oldReportFile = new File(reportPath);
				File newReportFile = new File(dirPath + File.separator + oldReportFile.getName());
				oldReportFile.renameTo(newReportFile);
			}

			List<String> companyPathArr = this.companyService.createCompanyDoc(dirName);
			Iterator x = companyPathArr.iterator();

			while (x.hasNext()) {
				String companyPath = (String) x.next();
				File oldCompanyFile = new File(companyPath);
				File newCompanyFile = new File(finalPath + File.separator + oldCompanyFile.getName());
				oldCompanyFile.renameTo(newCompanyFile);
			}

		}

		zipPathFile = new File(finalZipPath);
		zipDirName = zipPathFile.getName();
		zipDirPath = zipPathFile.getAbsolutePath();
		XMLpath = XMLUtils.WriteToXML(saveMap, zipPathFile, zipDirName);
		Iterator it = allCompanyArr.iterator();

		while (it.hasNext()) {
			Object company = it.next();
			companyJson = (JSONObject) company;
			dirName = (String) companyJson.get("zipDirName");
			dirPath = (String) companyJson.get("zipDirPath");
			reportPath = (String) companyJson.get("finalName");
			File oldFile = new File(dirPath);
			oldReportFile = new File(finalZipPath + File.separator + dirName);
			oldFile.renameTo(oldReportFile);
		}
		OutputStream outputStream = null;
		InputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		File zipFile = null;
		try {
			dirPath = ZipUtils.zip(zipDirPath);
			zipFile = new File(dirPath);
			outputStream = response.getOutputStream();
			inputStream = new FileInputStream(zipFile);
			bufferedInputStream = new BufferedInputStream(inputStream);
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			response.reset();
			response.setContentType("application/x-download;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(zipFile.getName().getBytes("gb2312"), "ISO8859-1"));
			boolean var57 = false;

			int byteRead;
			while ((byteRead = bufferedInputStream.read()) != -1) {
				bufferedOutputStream.write(byteRead);
			}
		} catch (Exception e) {

		} finally {
			bufferedOutputStream.flush();
			inputStream.close();
			bufferedInputStream.close();
			outputStream.close();
			bufferedOutputStream.close();
		}
		(new File(XMLpath)).delete();
		zipFile.delete();
		zipPathFile.delete();
		(new File(GetPathUtil.getPath() + File.separator + zipDirName)).delete();
	}

	@RequestMapping(value = "/import")
	@ResponseBody
	public JSONObject importZip(@RequestParam("file") MultipartFile file, String recommendedUnitName,
			String recommendedUnitContant, String recommendedUnitTel) throws Exception {
		JSONObject json = new JSONObject();
		File f = new File(GetPathUtil.getPath() + File.separator + file.getOriginalFilename());
		FileUtils.copyInputStreamToFile(file.getInputStream(), f);
		File[] subFiles = ZipUtils.unzip(f, GetPathUtil.getPath() + File.separator);
		File xmlFile = null;
		if (null != subFiles) {
			for (File subFile : subFiles) {
				if (subFile.getName().endsWith(".xml")) {
					xmlFile = subFile;
				} else {
					FileUtils.copyFile(subFile, new File(GetPathUtil.getPath() + File.separator + subFile.getName()));
				}
			}
		}
		json = XMLUtils.ReaderFromXML(xmlFile.getPath());
		List<CompanyDTO> companyList = (List<CompanyDTO>) json.get("company");
		List<CompanyDTO> newCompanyList = new ArrayList<>();
		List<ProductDTO> newProductList = new ArrayList<>();
		if (null != companyList) {
			for (int i = 0; i < companyList.size(); i++) {
				try {
					CompanyDTO companyDTO = companyService.loadByLicese(companyList.get(i).getBusinessLicense());
					if (null != companyDTO) {
						companyList.get(i).setId(companyDTO.getId());
						if (StringUtils.isEmpty(companyList.get(i).getRecommendedUnitContant())) {
							companyList.get(i).setRecommendedUnitContant(recommendedUnitContant);
						}
						if (StringUtils.isEmpty(companyList.get(i).getRecommendedUnitName())) {
							companyList.get(i).setRecommendedUnitName(recommendedUnitName);
						}
						if (StringUtils.isEmpty(companyList.get(i).getRecommendedUnitTel())) {
							companyList.get(i).setRecommendedUnitTel(recommendedUnitTel);
						}
						companyService.updateCompany(companyList.get(i));
					} else {
						if (StringUtils.isEmpty(companyList.get(i).getRecommendedUnitContant())) {
							companyList.get(i).setRecommendedUnitContant(recommendedUnitContant);
						}
						if (StringUtils.isEmpty(companyList.get(i).getRecommendedUnitName())) {
							companyList.get(i).setRecommendedUnitName(recommendedUnitName);
						}
						if (StringUtils.isEmpty(companyList.get(i).getRecommendedUnitTel())) {
							companyList.get(i).setRecommendedUnitTel(recommendedUnitTel);
						}
						newCompanyList.add(companyList.get(i));
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		if (newCompanyList.size() != 0) {
			companyService.saveCompanies(newCompanyList);
		}
		List<ProductDTO> productList = (List<ProductDTO>) json.get("product");
		productList.stream().forEach(product -> {
			try {
				List<ProductDTO> productDTOList = productService.loadByBusinessLicense(product.getBusinessLicense());
				if (null != productDTOList && productDTOList.size() != 0) {
					productDTOList.stream().forEach(pro -> {
						if (pro.getId() == product.getId()) {
							try {
								productService.updateProduct(pro);
							} catch (Exception e) {
								logger.error(e.getMessage());
								e.printStackTrace();
							}
						}
					});
				} else {
					product.setId(CommonUtil.genarateID());
					newProductList.add(product);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		});
		if (newProductList.size() != 0) {
			productService.saveProducts(newProductList);
		}
		f.delete();
		return json;
	}
}