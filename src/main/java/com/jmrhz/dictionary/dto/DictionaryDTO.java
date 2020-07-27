package com.jmrhz.dictionary.dto;

import java.io.Serializable;

public class DictionaryDTO implements Serializable{
	private static final long serialVersionUID = -1;
	private long id;
	private String dictionaryId;
	private String dictionaryValue;
	private String dictionaryType;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDictionaryId() {
		return dictionaryId;
	}
	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public String getDictionaryValue() {
		return dictionaryValue;
	}
	public void setDictionaryValue(String dictionaryValue) {
		this.dictionaryValue = dictionaryValue;
	}
	public String getDictionaryType() {
		return dictionaryType;
	}
	public void setDictionaryType(String dictionaryType) {
		this.dictionaryType = dictionaryType;
	}
	
}
