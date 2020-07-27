package com.jmrhz.util;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmrhz.company.dto.CompanyDTO;
import com.jmrhz.product.dto.ProductDTO;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {

	public static void createExcel(HttpServletRequest request, HttpServletResponse response,
			Map<CompanyDTO, List<ProductDTO>> map, String modulePath) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Workbook workbook;
		WritableWorkbook writableWorkbook = null;
		WritableSheet sheet;
		ProductDTO product;
		int row;
		int trueRow = 0;
		StringBuilder name = new StringBuilder();
		map.keySet().stream().forEach(companyDTO -> {
			name.append(companyDTO.getCompanyName());
		});
		File xlsFile = new File(name.append(simpleDateFormat.format(new Date())).toString());
		OutputStream outputStream = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		workbook = Workbook.getWorkbook(new File(modulePath));
		writableWorkbook = Workbook.createWorkbook(outputStream, workbook);
		sheet = writableWorkbook.getSheet(0);
		WritableFont writableFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, true);
		WritableCellFormat writableCellFormat = new WritableCellFormat(writableFont);
		writableCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
		for (Map.Entry<CompanyDTO, List<ProductDTO>> entry : map.entrySet()) {
			CompanyDTO company = entry.getKey();
			List<ProductDTO> productList = entry.getValue();
			if (productList != null && productList.size() != 0) {
				for (row = 0; row < productList.size(); row++) {
					trueRow++;
					product = productList.get(row);
					sheet.addCell(new jxl.write.Label(0, trueRow, String.valueOf(trueRow), writableCellFormat));
					sheet.addCell(new jxl.write.Label(1, trueRow, company.getCompanyName(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(2, trueRow, company.getRecommendedUnitName(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(3, trueRow, company.getBusinessLicense(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(4, trueRow, company.getLegalPerson(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(5, trueRow, company.getCompanyRegistrationTime(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(6, trueRow, company.getJoined(), writableCellFormat));
					if (company.getContinueJoin()) {
						sheet.addCell(new jxl.write.Label(7, trueRow, "是", writableCellFormat));
					} else {
						sheet.addCell(new jxl.write.Label(7, trueRow, "否", writableCellFormat));
					}
					sheet.addCell(new jxl.write.Label(8, trueRow, company.getPrincipal(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(9, trueRow, company.getTelephone(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(10, trueRow, company.getFixedlineTelephone(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(11, trueRow, company.getAddress(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(12, trueRow, company.getPostcode(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(13, trueRow, company.getProductType(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(14, trueRow, company.getTechnologyField(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(15, trueRow, company.getUnitNature(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(16, trueRow, company.getSacle(), writableCellFormat));
					if (company.getTechCompany()) {
						sheet.addCell(new jxl.write.Label(17, trueRow, "是", writableCellFormat));
					} else {
						sheet.addCell(new jxl.write.Label(17, trueRow, "否", writableCellFormat));
					}
					sheet.addCell(new jxl.write.Label(18, trueRow, company.getTechCompanyLevel(), writableCellFormat));
					if (company.getAppearOnMarket()) {
						sheet.addCell(new jxl.write.Label(19, trueRow, "是", writableCellFormat));
					} else {
						sheet.addCell(new jxl.write.Label(19, trueRow, "否", writableCellFormat));
					}
					sheet.addCell(
							new jxl.write.Label(20, trueRow, company.getAppearOnMarketTime(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(21, trueRow, company.getRegisteredCaptital().toString(),
							writableCellFormat));
					sheet.addCell(new jxl.write.Label(22, trueRow, company.getTotalEnterpriseAssets().toString(),
							writableCellFormat));
					sheet.addCell(new jxl.write.Label(23, trueRow, company.getMilitaryTurnover().toString(),
							writableCellFormat));
					sheet.addCell(new jxl.write.Label(24, trueRow, company.getAvgMilitaryTurnover().toString(),
							writableCellFormat));
					sheet.addCell(new jxl.write.Label(25, trueRow, company.getMerchandiseTurnover().toString(),
							writableCellFormat));
					sheet.addCell(new jxl.write.Label(26, trueRow, company.getAvgMerchandiseTurnover().toString(),
							writableCellFormat));
					sheet.addCell(new jxl.write.Label(27, trueRow, company.getPatentSituation(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(28, trueRow, company.getPrizeSituation(), writableCellFormat));
					if (company.getJoinActivity()) {
						sheet.addCell(new jxl.write.Label(29, trueRow, "是", writableCellFormat));
					} else {
						sheet.addCell(new jxl.write.Label(29, trueRow, "否", writableCellFormat));
					}
					sheet.addCell(new jxl.write.Label(30, trueRow, company.getTechnologyFinancialServicesDemandType(),
							writableCellFormat));
					sheet.addCell(new jxl.write.Label(31, trueRow,
							company.getTechnologyFinancialServicesDemandDescription(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(32, trueRow, product.getProductName(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(33, trueRow, product.getProductTechnicalNumber(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(34, trueRow, product.getPreviousExhibitions(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(35, trueRow, product.getProductType(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(36, trueRow, product.getTechnicalMaturity(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(37, trueRow, product.getAdvancedDegree(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(38, trueRow, product.getExhibitionArea(), writableCellFormat));
					if (product.getSelfIntellectual()) {
						sheet.addCell(new jxl.write.Label(39, trueRow, "是", writableCellFormat));
					} else {
						sheet.addCell(new jxl.write.Label(39, trueRow, "否", writableCellFormat));
					}
					sheet.addCell(
							new jxl.write.Label(40, trueRow, product.getSelfIntellectualExplain(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(41, trueRow, product.getProductInstruction(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(42, trueRow, product.getMainProductIndicators(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(43, trueRow, product.getSituationOfApplication(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(44, trueRow, product.getInputsndReturns(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(45, trueRow, product.getPatentSituation(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(46, trueRow, product.getPrizeSituation(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(47, trueRow, product.getDisplayExhibits(), writableCellFormat));
					if (StringUtils.isEmpty(product.getDisplayShape())) {
						sheet.addCell(new jxl.write.Label(48, trueRow, null, writableCellFormat));
					} else {
						sheet.addCell(new jxl.write.Label(48, trueRow,
								product.getDisplayShape().replace(",", "cm") + "cm" + product.getDisplayWeight() + "kg",
								writableCellFormat));
					}
					sheet.addCell(new jxl.write.Label(49, trueRow, product.getDisplayEffect(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(50, trueRow, product.getSiteRequirements(), writableCellFormat));
					sheet.addCell(
							new jxl.write.Label(51, trueRow, product.getBearingRequirements(), writableCellFormat));
					sheet.addCell(new jxl.write.Label(52, trueRow, null, writableCellFormat));
				}
			} else {
				trueRow++;
				sheet.addCell(new jxl.write.Label(0, trueRow, String.valueOf(trueRow), writableCellFormat));
				sheet.addCell(new jxl.write.Label(1, trueRow, company.getCompanyName(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(2, trueRow, company.getRecommendedUnitName(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(3, trueRow, company.getBusinessLicense(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(4, trueRow, company.getLegalPerson(), writableCellFormat));
				sheet.addCell(
						new jxl.write.Label(5, trueRow, company.getCompanyRegistrationTime(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(6, trueRow, company.getJoined(), writableCellFormat));
				if (company.getContinueJoin()) {
					sheet.addCell(new jxl.write.Label(7, trueRow, "是", writableCellFormat));
				} else {
					sheet.addCell(new jxl.write.Label(7, trueRow, "否", writableCellFormat));
				}
				sheet.addCell(new jxl.write.Label(8, trueRow, company.getPrincipal(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(9, trueRow, company.getTelephone(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(10, trueRow, company.getFixedlineTelephone(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(11, trueRow, company.getAddress(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(12, trueRow, company.getPostcode(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(13, trueRow, company.getProductType(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(14, trueRow, company.getTechnologyField(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(15, trueRow, company.getUnitNature(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(16, trueRow, company.getSacle(), writableCellFormat));
				if (company.getTechCompany()) {
					sheet.addCell(new jxl.write.Label(17, trueRow, "是", writableCellFormat));
				} else {
					sheet.addCell(new jxl.write.Label(17, trueRow, "否", writableCellFormat));
				}
				sheet.addCell(new jxl.write.Label(18, trueRow, company.getTechCompanyLevel(), writableCellFormat));
				if (company.getAppearOnMarket()) {
					sheet.addCell(new jxl.write.Label(19, trueRow, "是", writableCellFormat));
				} else {
					sheet.addCell(new jxl.write.Label(19, trueRow, "否", writableCellFormat));
				}
				sheet.addCell(new jxl.write.Label(20, trueRow, company.getAppearOnMarketTime(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(21, trueRow, company.getRegisteredCaptital().toString(),
						writableCellFormat));
				sheet.addCell(new jxl.write.Label(22, trueRow, company.getTotalEnterpriseAssets().toString(),
						writableCellFormat));
				sheet.addCell(
						new jxl.write.Label(23, trueRow, company.getMilitaryTurnover().toString(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(24, trueRow, company.getAvgMilitaryTurnover().toString(),
						writableCellFormat));
				sheet.addCell(new jxl.write.Label(25, trueRow, company.getMerchandiseTurnover().toString(),
						writableCellFormat));
				sheet.addCell(new jxl.write.Label(26, trueRow, company.getAvgMerchandiseTurnover().toString(),
						writableCellFormat));
				sheet.addCell(new jxl.write.Label(27, trueRow, company.getPatentSituation(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(28, trueRow, company.getPrizeSituation(), writableCellFormat));
				if (company.getJoinActivity()) {
					sheet.addCell(new jxl.write.Label(29, trueRow, "是", writableCellFormat));
				} else {
					sheet.addCell(new jxl.write.Label(29, trueRow, "否", writableCellFormat));
				}
				sheet.addCell(new jxl.write.Label(30, trueRow, company.getTechnologyFinancialServicesDemandType(),
						writableCellFormat));
				sheet.addCell(new jxl.write.Label(31, trueRow,
						company.getTechnologyFinancialServicesDemandDescription(), writableCellFormat));
				sheet.addCell(new jxl.write.Label(0, 32, null));
				sheet.addCell(new jxl.write.Label(0, 33, null));
				sheet.addCell(new jxl.write.Label(0, 34, null));
				sheet.addCell(new jxl.write.Label(0, 35, null));
				sheet.addCell(new jxl.write.Label(0, 36, null));
				sheet.addCell(new jxl.write.Label(0, 37, null));
				sheet.addCell(new jxl.write.Label(0, 38, null));
				sheet.addCell(new jxl.write.Label(0, 39, null));
				sheet.addCell(new jxl.write.Label(0, 40, null));
				sheet.addCell(new jxl.write.Label(0, 41, null));
				sheet.addCell(new jxl.write.Label(0, 42, null));
				sheet.addCell(new jxl.write.Label(0, 43, null));
				sheet.addCell(new jxl.write.Label(0, 44, null));
				sheet.addCell(new jxl.write.Label(0, 45, null));
				sheet.addCell(new jxl.write.Label(0, 46, null));
				sheet.addCell(new jxl.write.Label(0, 47, null));
				sheet.addCell(new jxl.write.Label(0, 48, null));
				sheet.addCell(new jxl.write.Label(0, 49, null));
				sheet.addCell(new jxl.write.Label(0, 50, null));
				sheet.addCell(new jxl.write.Label(0, 51, null));
				sheet.addCell(new jxl.write.Label(0, 52, null));
			}
		}
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String(xlsFile.getName().getBytes("gb2312"), "ISO8859-1") + ".xls");
		writableWorkbook.write();
		writableWorkbook.close();
		outputStream.close();

	}
}
