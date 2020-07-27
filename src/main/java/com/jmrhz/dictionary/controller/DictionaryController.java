package com.jmrhz.dictionary.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.CommonUtil;
import com.common.utils.IntegerValueFilter;
import com.jmrhz.dictionary.service.IDictionaryService;

@RestController
@RequestMapping(value = "/dictionary")
@CrossOrigin
public class DictionaryController {
	@Autowired
	private IDictionaryService dictionaryService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/list")
	public void listSysdictionary() {

		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dictionaryId", CommonUtil.getRequest().getParameter("dictionaryId"));
			map.put("dictionaryValue", CommonUtil.getRequest().getParameter("dictionaryValue"));
			map.put("dictionaryType", CommonUtil.getRequest().getParameter("dictionaryType"));
			JSONObject obj = new JSONObject();
			obj.put("data", JSONArray
					.parseArray(JSON.toJSONString(dictionaryService.listDictionarys(map), new IntegerValueFilter())));

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
}
