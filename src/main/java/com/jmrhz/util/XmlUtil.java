package com.jmrhz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("rawtypes")
public class XmlUtil {
	/** entity map对象，key:name ,value:entity的属性map集 **/
	public Map entityMap;

	/** column map 对象，key:entityName_colName , value:column的属性map集 **/
	public Map columnMap;

	/** rule map 对象，key:entityName_colName_ruleName, value: rule 的map集：找到一行rule **/
	public Map ruleMap;

	/**
	 * rules map 对象, key:entityName_colName, value: rules 的map集:找到该column下所有的rule
	 **/
	public Map columnRulesMap;

	/** entity--column map: key:entityName, value: column list:根据实体类名得到所有的列 **/
	public Map columnListMap;

	/** column list **/
	public List columnList;

	/**
	 * 构造方法
	 * <p>
	 * 开始解析xml文件 :
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param xmlFilePath
	 */
	public XmlUtil(File xmlFilePath) {
		FileInputStream in = null;
		try {
			if (xmlFilePath == null) {
				throw new FileNotFoundException();
			}
			SAXReader reader = new SAXReader();
			in = new FileInputStream(xmlFilePath);
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			Iterator itEntity = root.elements("company").iterator();
			while (itEntity.hasNext()) {
				Element entity = (Element) itEntity.next();
				parseEntity(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @Title: parseEntity @Description:开始解析entity @param entity 设定文件 @return void
	 *         返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public void parseEntity(Element entity) {
		if (entity != null) {

			/** 对数据进行初始化设置 **/
			columnListMap = new HashMap();
			columnMap = new HashMap();
			entityMap = new HashMap();
			ruleMap = new HashMap();
			columnRulesMap = new HashMap();
			columnList = new ArrayList();

			setEntityMap(entity);
			String entityName = entity.attributeValue("name");
			Iterator itColumn = entity.elements("column").iterator();
			while (itColumn.hasNext()) {
				Element column = (Element) itColumn.next();
				setColumnMap(entityName, column);
			}
			columnListMap.put(entityName, columnList);
		}
	}

	/**
	 * 
	 * @Title: setEntityMap @Description: 将entity放入entityMap中 @param entity
	 *         设定文件 @return void 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public void setEntityMap(Element entity) {
		Map ent = new HashMap();
		String name = entity.attributeValue("name");
		String code = entity.attributeValue("code");
		ent.put("name", name);
		ent.put("code", code);
		entityMap.put(name, ent);
	}

	/**
	 * 
	 * @Title: setColumnMap @Description:将column放入columnMap中 @param
	 *         entityName @param column 设定文件 @return void 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public void setColumnMap(String entityName, Element column) {
		if (column != null) {
			Map col = new HashMap();
			String name = column.attributeValue("name");
			String code = column.attributeValue("code");
			String type = column.attributeValue("type");
			col.put("name", name);
			col.put("code", code);
			col.put("type", type);
			String columnMapKey = entityName + "_" + name; // eg: 用户表_用户名
			columnMap.put(columnMapKey, col);
			columnList.add(col);
			Iterator ruleIt = column.elements("rules").iterator(); // 获得rules
			while (ruleIt.hasNext()) {
				Element rules = (Element) ruleIt.next();
				Iterator rule = rules.elements("rule").iterator(); // 获得 rule
				while (rule.hasNext()) {
					Element ruleValid = (Element) rule.next(); // 获得每一行rule
					setRuleMap(entityName, name, ruleValid);
				}
			}
		}
	}

	/**
	 * 
	 * @Title: setRuleMap @Description: 将 rule 验证规则放入ruleMap中 @param
	 *         entityName @param columnName @param ruleValid 设定文件 @return void
	 *         返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public void setRuleMap(String entityName, String columnName, Element ruleValid) {
		if (ruleValid != null) {
			String ruleName = ruleValid.attributeValue("name");
			String ruleMsg = ruleValid.attributeValue("message");
			Map ruleValidMap = new HashMap();
			ruleValidMap.put("name", ruleName);
			ruleValidMap.put("message", ruleMsg);
			String ruleStrKey = entityName + "_" + columnName + "_" + ruleName;
			String colStrKey = entityName + "_" + columnName;
			if (this.getColumnRulesMap().containsKey(colStrKey)) {
				List valids = (List) this.getColumnRulesMap().get(colStrKey);
				valids.add(ruleValidMap);
			} else {
				List valids = new ArrayList();
				valids.add(ruleValidMap);
				this.columnRulesMap.put(colStrKey, valids); // 将每个column下的所有rules存入该map中
			}
			ruleMap.put(ruleStrKey, ruleValidMap); // 将每个column下的一条rule存入该map中
		}
	}

	public Map getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map entityMap) {
		this.entityMap = entityMap;
	}

	public Map getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map columnMap) {
		this.columnMap = columnMap;
	}

	public Map getRuleMap() {
		return ruleMap;
	}

	public void setRuleMap(Map ruleMap) {
		this.ruleMap = ruleMap;
	}

	public Map getColumnRulesMap() {
		return columnRulesMap;
	}

	public void setColumnRulesMap(Map columnRulesMap) {
		this.columnRulesMap = columnRulesMap;
	}

	public Map getColumnListMap() {
		return columnListMap;
	}

	public void setColumnListMap(Map columnListMap) {
		this.columnListMap = columnListMap;
	}
}
