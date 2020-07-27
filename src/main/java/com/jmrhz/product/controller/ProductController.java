package com.jmrhz.product.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.CommonUtil;
import com.common.utils.IntegerValueFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jmrhz.product.dto.ProductDTO;
import com.jmrhz.product.service.IProductService;

/**
 * 描述：请修改类、方法注释控制层
 * 
 * @author Team
 * @date Thu Apr 11 21:12:10 CST 2019
 */
@RestController
@RequestMapping(value = "/product")
@CrossOrigin
public class ProductController {
	@Autowired
	private IProductService productService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/list")
	public void listProduct(final int pageNum, final int pageSize) {

		try {

			PageHelper.startPage(pageNum, pageSize);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productName", CommonUtil.getRequest().getParameter("productName"));
			map.put("productType", CommonUtil.getRequest().getParameter("productType"));
			map.put("technicalMaturity", CommonUtil.getRequest().getParameter("technicalMaturity"));
			map.put("advancedDegree", CommonUtil.getRequest().getParameter("advancedDegree"));
			map.put("exhibitionArea", CommonUtil.getRequest().getParameter("exhibitionArea"));
			map.put("selfIntellectual", CommonUtil.getRequest().getParameter("selfIntellectual"));
			map.put("selfIntellectualExplain", CommonUtil.getRequest().getParameter("selfIntellectualExplain"));
			map.put("recommendedUnitName", CommonUtil.getRequest().getParameter("recommendedUnitName"));
			PageInfo<ProductDTO> pageInfo = new PageInfo<ProductDTO>(productService.listProducts(map));

			JSONObject obj = new JSONObject();
			obj.put("total", pageInfo.getTotal());
			obj.put("data", JSONArray.parseArray(JSON.toJSONString(pageInfo.getList(), new IntegerValueFilter())));

			CommonUtil.sendJsonData(CommonUtil.getResponse(), obj.toJSONString());

		} catch (Exception e) {
			logger.error(e.toString());
			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.toString());
				e1.printStackTrace();
			}
			logger.error(e.toString());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/load")
	public JSONObject loadCompany(final String id) {
		JSONObject obj = new JSONObject();
		try {
			ProductDTO productDTO = productService.loadProduct(Long.parseLong(id));
			obj.put("data", productDTO);
		} catch (Exception e) {

			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.toString());
				e1.printStackTrace();
			}
			logger.error(e.toString());
			e.printStackTrace();
			obj.put("data", "ERROR");
		}
		return obj;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void save(@RequestBody ProductDTO productDTO) {
		try {
			if (CommonUtil.isEmpty(productDTO)) {
				CommonUtil.error(CommonUtil.getResponse(), "缺少要保存的数据！");
				return;
			}
			ProductDTO productDTO1 = productService.saveProduct(productDTO);
			CommonUtil.success(CommonUtil.getResponse(), JSON.toJSONString(productDTO1, new IntegerValueFilter()));
		} catch (Exception e) {
			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.toString());
				e1.printStackTrace();
			}
			logger.error(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 描述：删除请修改类、方法注释
	 * 
	 * @param id
	 *            请修改类、方法注释id
	 */
	@RequestMapping(value = "/deleteById")
	public void deleteById(@RequestParam long id) {

		try {

			productService.deleteProduct(id);

			CommonUtil.success(CommonUtil.getResponse(), "" + id);

		} catch (Exception e) {

			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.toString());
				e1.printStackTrace();
			}
			logger.error(e.toString());
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

			productService.deleteProducts(longIDS);

			CommonUtil.success(CommonUtil.getResponse(), "记录删除成功！");

		} catch (Exception e) {

			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.toString());
				e1.printStackTrace();
			}
			logger.error(e.toString());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void updatePlanerror(@RequestBody ProductDTO productDTO) {

		try {

			productDTO.setId(productDTO.getId());

			productService.updateProduct(productDTO);

			CommonUtil.success(CommonUtil.getResponse(), JSON.toJSONString(productDTO));

		} catch (Exception e) {

			try {
				CommonUtil.error(CommonUtil.getResponse(), e.toString());
			} catch (Exception e1) {
				logger.error(e1.toString());
				e1.printStackTrace();
			}
			logger.error(e.toString());
			e.printStackTrace();
		}
	}
}
