package com.jmrhz.product.service;

import java.util.List;
import java.util.Map;

import com.jmrhz.product.dto.ProductDTO;

/**
 * 
 * @author tjx
 *
 */
public interface IProductService {

	/**
	 * 描述：根据Id获取DTO
	 * 
	 * @param id
	 */
	ProductDTO loadProduct(final long id) throws Exception;

	ProductDTO saveProduct(final ProductDTO productDTO) throws Exception;

	List<ProductDTO> listProducts(final Map<String, Object> map) throws Exception;

	List<ProductDTO> loadByBusinessLicense(final String businessLicense) throws Exception;

	void saveProducts(List<ProductDTO> products);

	void deleteProduct(final long id) throws Exception;

	void deleteProducts(final long[] longIDS) throws Exception;

	ProductDTO updateProduct(final ProductDTO productDTO) throws Exception;

}