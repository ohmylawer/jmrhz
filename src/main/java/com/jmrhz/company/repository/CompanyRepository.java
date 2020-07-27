package com.jmrhz.company.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.common.repository.BaseRepository;
import com.jmrhz.company.dto.CompanyDTO;
import com.jmrhz.company.dto.RecommendDTO;

/**
 * 
 * @author tjx
 *
 */
@Mapper
public interface CompanyRepository extends BaseRepository<CompanyDTO> {
	List<CompanyDTO> listCompanies(long[] longIDS);

	CompanyDTO loadByLicese(final String id);

	List<Long> getIdList();

	List<RecommendDTO> getRecommendInfo(String recommendedName);
	
	List<CompanyDTO> loadByCompanyName(String companyName);
}
