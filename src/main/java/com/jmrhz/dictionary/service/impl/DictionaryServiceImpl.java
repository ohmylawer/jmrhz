package com.jmrhz.dictionary.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmrhz.dictionary.dto.DictionaryDTO;
import com.jmrhz.dictionary.repository.DictionaryRepository;
import com.jmrhz.dictionary.service.IDictionaryService;
@Service
public class DictionaryServiceImpl implements IDictionaryService{
	
	@Autowired
	private DictionaryRepository dictionaryRepository;

	@Override
	public List<DictionaryDTO> listDictionarys(Map<String, Object> map) throws Exception {
		return dictionaryRepository.queryList(map);
	}

}
