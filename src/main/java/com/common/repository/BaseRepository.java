package com.common.repository;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
* 描述：数据访问公共接口，定义数据的CRUD行为
* @author Team
* @date Sat Apr 06 02:28:32 CST 2019
*/
@Mapper
public interface BaseRepository<T> {

	/**
	 * 加载信息
	 * id 待加载信息的ID
	 * 返回信息
	 */
	public T load( final long id);
	
	
	/**
	 * 
	    * @Title: 条件查询
	    * @Description: TODO(按条件查询信息)
	    * @param map 查询条件
	    * @return List<T>    返回信息
	 */
	public List<T> queryList(Map<String, Object> map);
	
	
	/**
	 * 查询符合条件的记录数量
	 * @param map 查询条件
	 * @return 符合条件的记录数。
	 */
	public Integer queryCount( Map<String, Object> map );
	
	
	/**
	 * 统计安排经费（万元）和计划条数
	 * @param map 统计条件
	 * @return	安排经费（万元）
	 */
	public Map<String,Object> queryApplyMoneyAndNumber( Map<String, Object> map  );
	
	
	/**
	 * 
	    * @Title: 保存信息
	    * @Description: TODO(保存信息)
	    * @param  t    	待保存信息
	    * @return void    返回类型 空
	 */
	public void save(T t);

	/**
	 * 
	    * @Title: 保存信息
	    * @Description: TODO(保存信息)
	    * @param  map    待保存信息集合
	    * @return void    返回类型 空
	 */
	public void save(Map<String, Object> map);
	
	
	/**
	 * 批量保存数据
	 * @param t	数据集合，采用List封装数据。
	 */
	public void saveBatch(final List<T> t);

	/**
	 * 
	    * @Title: 更新信息
	    * @Description: TODO(更新信息)
	    * @param  t    	待更新信息
	    * @return int      更新数量
	 */
	int update(T t);

	/**
	 * 
	    * @Title: 更新信息
	    * @Description: TODO(更新信息)
	    * @param  map  待更新信息
	    * @return int      更新数量
	 */
	int update(Map<String, Object> map);

	/**
	 * 
	    * @Title: 删除信息
	    * @Description: TODO(删除信息)
	    * @param  id    	待删除信息
	    * @return int      删除数量
	 */
	int delete(Object id);

	
	/**
	 * 
	    * @Title: 删除多条信息
	    * @Description: TODO(删除多条信息)
	    * @param  array    	待删除信息ID数组
	    * @return int      删除数量
	 */
	int deleteBatch(long[] array);
	
	/**
	 * 数据库清理使用的方法
	 * @return 已删除记录数
	 */
	int deleteAll();
	
	/**
	 * 按条件删除待导入计划数据。
	 * @param importConditionMap	删除条件
	 */
	void deletePlanForImport( final Map<String,Object> importConditionMap);
	
}
