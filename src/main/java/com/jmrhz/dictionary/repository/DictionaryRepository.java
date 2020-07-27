package com.jmrhz.dictionary.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.common.repository.BaseRepository;
import com.jmrhz.dictionary.dto.DictionaryDTO;

@Mapper
public interface DictionaryRepository extends BaseRepository<DictionaryDTO> {
	void saveBatch(List<DictionaryDTO> dictionarys);
}
