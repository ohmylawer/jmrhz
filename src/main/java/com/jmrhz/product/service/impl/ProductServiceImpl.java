package com.jmrhz.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.utils.CommonUtil;
import com.common.utils.SnowflakeIdWorker;
import com.jmrhz.company.dto.CompanyDTO;
import com.jmrhz.company.repository.CompanyRepository;
import com.github.pagehelper.Page;
import com.jmrhz.product.dto.ProductDTO;
import com.jmrhz.product.repository.ProductRepository;
import com.jmrhz.product.service.IProductService;

/**
 * 
 * @author tjx
 *
 */
@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public ProductDTO loadProduct(long id) throws Exception {
		return productRepository.load(id);
	}

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) throws Exception {
		if (null == productDTO) {
			return null;
		}
		ProductDTO product = new ProductDTO();
		BeanUtils.copyProperties(productDTO, product);
		product.setId(CommonUtil.genarateID());
		CompanyDTO companyDTO = companyRepository.loadByLicese(productDTO.getBusinessLicense());
		product.setProductTechnicalNumber(companyDTO.getBusinessLicense() + "/" + product.getId());
		productRepository.save(product);
		return loadProduct(product.getId());
	}

	@Override
	public List<ProductDTO> listProducts(Map<String, Object> map) throws Exception {

		String recommendedUnit = (String) map.get("recommendedUnitName");
		Page<CompanyDTO> companyList = null;
		List<String> ids = new ArrayList<String>();
		if (recommendedUnit != null && !"".equals(recommendedUnit)) {
			Map<String, String> queryCompanyMap = new HashMap<String, String>();
			queryCompanyMap.put("recommendedUnitName", recommendedUnit);
			companyList = (Page<CompanyDTO>) companyRepository.queryList(map);
			for (CompanyDTO companyDTO : companyList) {
				ids.add(companyDTO.getBusinessLicense());
			}
		}
		map.put("ids", ids);
		return productRepository.queryList(map);
	}

	public void saveProducts(List<ProductDTO> list) {
		productRepository.saveBatch(list);

	}

	@Override
	public void deleteProduct(long id) throws Exception {
		productRepository.delete(id);

	}

	@Override
	public void deleteProducts(long[] longIDS) throws Exception {
		productRepository.deleteBatch(longIDS);

	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO) throws Exception {

		if ((null == productDTO) || (0 == productDTO.getId())) {

			return null;
		}

		ProductDTO product = loadProduct(productDTO.getId());

		BeanUtils.copyProperties(productDTO, product);

		productRepository.update(product);

		return product;
	}

	@Override
	public List<ProductDTO> loadByBusinessLicense(String businessLicense) throws Exception {
		return productRepository.loadByBusinessLincese(businessLicense);
	}

}
