package com.common.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReportRepository<T> {

	  List<T>  loadData(Map<String,Object> map);
	  
	  List<T>  loadDataById(Map<String,Object> map);
	  
	  List<T> loadChildren(long parentID);
	  
	  List<T> loadTempChildren(long parentID);
	  
	  T loadSub(long id);
	  
	  String getPlanLevelcode(HashMap<String,Object> map);
	  
	  void deleteTemp();
	  
	  void updateDcbz(long id);
	  
	  void deleteError(long id);
	  
	  Integer getArrangeMoney();
	  
	  Integer getArrangeMoneyNull();
	  
	  T loadByLeveCode(String planLevelcode);
	  
	  void saveTemp(T reportnormalplanDTO);
	  
	  List<T>  loadTemp(Map<String,Object> map);
	  
	  List<T>  loadTree(Map<String,Object> map);
	  
	  List<T>  sumHj(Map<String,Object> map);
	  
	  List<T> loadPData(Map<String, Object> map);
	  
	  List<T> loadCompare(Map<String, Object> map);
	  
	  void coverData1();
	  
	  void coverData2();
	  
	  void coverData3();
	  
	  void coverData4();
	  
      void updateData(long[] id);
      
      void recoverData(long[] id);
      
      void updateDataSub(long[] id);
      
      void recoverDataSub(long[] id);
      
      T loadTempById(long id);
      
      void updateTemp(T reportnormalplanDTO);
      
      void updateDate(Map<String,Object> map);
      
	  void updateSubDate(Map<String,Object> map);
	  
	  /**
		 * 将Excel导入的计划同步到计划表中。
		 */
		void  tableDataSubmit();
		
		/**
		 * 删除待同步计划表中数据。
		 */
		 int deleteData();
}
