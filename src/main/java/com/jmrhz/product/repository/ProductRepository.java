package com.jmrhz.product.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.common.repository.BaseRepository;
import com.jmrhz.product.dto.ProductDTO;

/**
 * 描述：请修改类、方法注释 Repository接口
 * 
 * @author Team
 * @date Thu Apr 11 21:12:10 CST 2019
 */
@Mapper
public interface ProductRepository extends BaseRepository<ProductDTO> {
	void deleteByBusinessLincese(String id);
	void deleteBatchBybusinessLincese(String[] longIDS);
	public List<ProductDTO> loadByBusinessLincese(String companyId);
}
