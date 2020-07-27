package com.jmrhz.company.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.jmrhz.company.dto.CompanyDTO;

import freemarker.template.TemplateException;

/**
 * 
 * @author tjx
 *
 *
 */
public interface ICompanyService {

	/**
	 * 描述：根据Id获取DTO
	 * 
	 * @param id
	 */
	CompanyDTO loadCompany(final long id) throws Exception;

	CompanyDTO loadByLicese(final String id) throws Exception;

	CompanyDTO saveCompany(final CompanyDTO companyDTO) throws Exception;

	List<CompanyDTO> listCompanys(final Map<String, Object> map) throws Exception;

	List<CompanyDTO> listCompanies(long[] longIDS) throws Exception;

	void saveCompanies(List<CompanyDTO> list);

	void deleteCompany(final long id) throws Exception;

	void deleteCompanies(final long[] longIDS) throws Exception;

	CompanyDTO updateCompany(final CompanyDTO companyDTO) throws Exception;

	List<Long> getIdList();

	String createRecommendDoc(String finalName) throws IOException, TemplateException;

	List<String> createCompanyDoc(String finalName) throws IOException;
}