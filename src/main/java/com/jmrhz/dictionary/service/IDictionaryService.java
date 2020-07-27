package com.jmrhz.dictionary.service;

import java.util.List;
import java.util.Map;

import com.jmrhz.dictionary.dto.DictionaryDTO;

public interface IDictionaryService {
	List<DictionaryDTO> listDictionarys(final Map<String, Object> map) throws Exception;
}
