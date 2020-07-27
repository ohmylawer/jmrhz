//package com.common.utils;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.nio.charset.Charset;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.base.baseequipmentcatalog.dto.BaseequipmentcatalogDTO;
//import com.base.basemeasureunit.dto.BasemeasureunitDTO;
//import com.base.baseplancatalog.dto.BaseplancatalogDTO;
//import com.common.xlsimport.controller.Zip;
//import com.csvreader.CsvReader;
//import com.sys.sysorg.dto.SysorgDTO;
//
//import nci.dsp.ncitag.util.StringHelper;
//
//
//public class CsvUtil{
//	
//	private static final String TMP=System.getProperty("java.io.tmpdir");
//	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//	
//	/*public static String exportMoreCSV(Map<String, Object> map,String bbdwmc) throws Exception{
//		//String jhpc = (String) SpringContextUtil.getSession().getAttribute(WXJHConstants.SESSION_PC);
//		Map<String, JSONArray> map1 = new HashMap<String,JSONArray>();
//		map1.put("计划科目", "[{},{}]");
//		map1.put("ZB科目", "[{},{}]");
//		
//		checkFile.json={
//			"fromUnit":'',
//			"toUnit":'',
//			"planYear":'',
//			"planType":'正常计划',
//			"是否加密":1,
//			"ZB类别":'分类1,分类2...',
//			"dataFile":"计划科目,ZB科目"
//		}
//		String csvFile=null;
//		String txtFile = null;
//		CsvWriter cw=null;
//		String ClassName="";
//		long start = System.currentTimeMillis();
//		csvFile=TMP+File.separator+"exportCSV"+File.separator+bbdwmc+"Csv";
//		File file=new File(csvFile);
//		if(!file.exists()){
//			file.mkdir();
//		}
//		Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
//        String time = sdf.format(date);
//		txtFile = csvFile+File.separator+"explain.txt";
//		String fileName = TMP+File.separator+"exportCSV"+File.separator+time+"下发"+bbdwmc+"基础数据包";
//		csvFile=csvFile+File.separator+time+"下发"+bbdwmc+"基础数据包"+".csv";
//		File file3 = new File(txtFile);
//		file3.delete();
//		FileOutputStream fos = new FileOutputStream(file3);
//		byte[] bytes = (bbdwmc).getBytes(Charset.forName("UTF-8"));
//		fos.write(bytes);
//		fos.close();
//		File file2=new File(csvFile);
//		file2.delete();
//		cw=new CsvWriter(csvFile, ' ', Charset.forName("UTF-8"));
//		//String[] item = null;
//		try {
//			List<String[]> allinfo=new ArrayList<String[]>();
//			Set<String> keys=map.keySet();
//			for (String key : keys) {
//				List<?> iteams=(List<?>) map.get(key);
//				if( !CommonUtil.isEmpty(iteams)){
//					if(iteams.size()>0){
//						Class clazz=iteams.get(0).getClass();
//						Field[] fields=clazz.getDeclaredFields();
//						String[] item  = new String[fields.length];
//						StringBuffer sbf=new StringBuffer();
//						for (int i = 0; i < fields.length; i++) {
//							String value=fields[i].getName().toString();
//							if(value.indexOf("ialVersion")==-1){
//								item[i]="{#|#}"+value;
//							}
//						}
////						ClassName=getListName(clazz.getName());
////						int index=ClassName.indexOf("Item")!=-1?ClassName.indexOf("Item"):ClassName.indexOf("Model");
////						ClassName=ClassName.substring(0,index);
//						String[] bz={"@@@|||"};
//						allinfo.add(bz);
//						if(item[0]==null){
//							item[0]=key;
//						}
//						allinfo.add(item);
//						
//						if(allinfo.size()==0){
//							cw.write("");
//						}else{
//							StringBuffer sbf1=new StringBuffer();
//							for (String[] s : allinfo) {
//								cw.writeRecord(s);
//							}
//						}
//						allinfo.clear();
//						allinfo = new ArrayList<String[]>();
//						System.out.println("开始循环 "+key+" ,有 "+iteams.size()+" 条");
//						//----------
//						
//						String value = "";
//						int emptyIndex = 0;
//						Object result=null;
//						String[] newitem = null;
//						
//						int init = 10000;//分批start
//						int total = iteams.size();
//						int cycelTotal = total/init;
//						if(total%init!=0){
//							cycelTotal +=1;
//						}
//						if(total<init){
//							init = iteams.size();
//						}
//						System.out.println("循环次数"+cycelTotal);
//						
//						List<String[]> allinfo2 = null;
//						for(int i=0;i<cycelTotal;i++){
//							allinfo2 = new ArrayList<String[]>();
//							for (int j = 0; j < init; j++) {
//								//System.out.println(i+" "+j);
//								if(i*10000+j<total){
//									newitem=new String[fields.length];
//									for (int k = 0; k < fields.length; k++) {
//									value=fields[k].getName().toString();
//									if(value.indexOf("ialVersion")==-1){
//										  System.out.println(fields[k].getName());
//										   System.out.println(iteams.get(i*10000+j).getClass());
//										   System.out.println((new PropertyDescriptor(fields[k].getName(), iteams.get(i*10000+j).getClass())));
//										   System.out.println((new PropertyDescriptor(fields[k].getName(), iteams.get(i*10000+j).getClass())).getReadMethod());
//										   System.out.println(iteams.get(i*10000+j));
//
//											result = ((new PropertyDescriptor(fields[k].getName(), iteams.get(i*10000+j).getClass()))).getReadMethod().invoke(iteams.get(i*10000+j));
//											//getmMethod=pd.getReadMethod();
//											//result=getmMethod.invoke(iteams.get(j));
//											if(result==null){
//												result="|||";
//											}else if(result.toString().equals("")){
//												result="||";
//											}
//											//字符串中存在空格
//											emptyIndex=result.toString().indexOf(" ");
//											if(emptyIndex!=-1){
//												result=result.toString().replace(" ","^^^");
//											}
//											newitem[k]="{#|#}"+result.toString();
//											if(JM){
//											sbf.delete(0, sbf.length());
//											for (int l = 0; l < newitem[k].getBytes("UTF-8").length; l++) {
//												sbf.append(newitem[k].getBytes("UTF-8")[l]+",");
//											}
//											newitem[k]=JiamiUtil.base64(sbf.toString());
//											}
//										}
//									}
//									allinfo2.add(newitem);
//									newitem = null;	
//								}
//							}
//							//System.out.println("开始处理");
//							if(allinfo2.size()==0){
//								cw.write("");
//							}else{
//								for (String[] s : allinfo2) {
//									cw.writeRecord(s);
//								}
//							}
//							allinfo2.clear();
//							allinfo2 = null;
//						}
//					}
//				}else{
//					continue;
//				}
//			}
//			long end = System.currentTimeMillis();
//			System.out.println("生成文件共计耗时：" + ((end - start )/1000) + "秒" );
//			cw.close();
//			filebyZip(TMP+File.separator+"exportCSV"+File.separator+bbdwmc+"Csv",fileName);
//			File file4=new File(TMP+File.separator+"exportCSV"+File.separator+bbdwmc+"Csv");
//			deleteDir(file4);
//		} catch (Exception e) {
//			e.printStackTrace();
//			ScriptHelper.WriteToClientUtf8("failure");
//		}finally{
//			if(cw!=null){
//				cw.close();
//			}
//		}
//		ScriptHelper.WriteToClientUtf8(csvFileName);
//		return fileName+".uzip";
//	}*/
//	
//	
//	public static String exportMoreCSV(Map<String, Object> map,String bbdwmc,String  newPath) throws Exception{
//		File file=new File(newPath);
//		
//		
//		
//		
//		return null;
//	}
//	
//	private static void filebyZip(String file_temp,String fileName) {
//		Zip zipCom = new Zip(fileName+".uzip",file_temp);
//        try
//        {
//            zipCom.zip();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//	}
//	
//	
//	/**
//	 * 删除目录
//	 */
//	public static boolean deleteDir(File dir) {
//		if(dir.isDirectory()) {
//			String[] children = dir.list();
//			for(int i = 0;i <children.length; i++) {
//				boolean success = deleteDir(new File(dir,children[i]));
//				if(!success) {
//					return false;
//				}
//			}
//		}
//		return dir.delete();
//	}
//	
//	
//	
//	/**
//	 * 多表导入
//	 * @param clazz
//	 * @param csvPath
//	 * @return
//	 * @throws Exception
//	 */
//	public static Map<String, List<?>> importCSVS(String csvPath) throws Exception{
//		Map<String, List<?>> mmpp=new HashMap<String, List<?>>();
//		//String csvFile=TMP+File.separator+"testcsv"+File.separator+"test.csv";
//		CsvReader reader=new CsvReader(csvPath, ' ', Charset.forName("UTF-8"));
//		List<String> allinfo=new ArrayList<String>();
//		int dsb = 0;
//		while(reader.readRecord()){
//			String info=reader.getRawRecord();
//			/*if(){
//				
//			}*/
//			allinfo.add(info);
//			System.out.println(dsb++);
//			//一次全部读完
//			//info=reader.getValues();
//			//一次读取一行数据
//			//String info=reader.getRawRecord();
//			//解密操作
////			if(JM){
////				StringBuffer sbf=new StringBuffer();
////				String results[]=info.split(" ");
////				for (int i = 0; i < results.length; i++) {
////					results[i]=Base64.decodeToString(results[i]);
////					String val=null;
////					if(!"".equals(results[i])){
////						String[] bys=results[i].split(",");
////						byte[] bytes=new byte[bys.length];
////						for (int j = 0; j < bys.length; j++) {
////							int num=Integer.valueOf(bys[j]);
////							bytes[j]=(byte) num;
////						}
////						val=new String(bytes,"utf8");
////					}
////					sbf.append(val+" ");
////				}
////				allinfo.add(sbf.toString());
////			}else{
////				allinfo.add(info);
////			}
//		}
//		System.out.println("读完了");
//		//List<Class> classs=new ArrayList<Class>();
//		Map<String, Class> mdoelMap=getModelMap();
//		Map<String, Class> mdoelMap2=new HashMap<String, Class>();
//		Map<String, String> snMap=new HashMap<String, String>();
//		Set<String> keys=mdoelMap.keySet();
//		//获取所有集合的开始结束位置
//		List<Integer> position=getPosition(allinfo);
//		for (int i = 0; i < position.size()-1; i++) {
//			//获得本次集合第一条数据
//			int index=position.get(i)+1;
//			String[] result=allinfo.get(index).split(" ");
//			//通过循环获得 集合要转换的类型、开始位置和结束位置
//			for (String key : keys) {
//				if(result[0].equals(key)){
//					//保存本次传过来的集合名 和 需要转换的类型
//					mdoelMap2.put(key,mdoelMap.get(key));
//					//保存本次传过来的集合名 和 开始位置结束位置
//					snMap.put(key, index+"|"+position.get(i+1));
//				}
//			}
//		}
//		keys.clear();
//		keys=mdoelMap2.keySet();
//		if(keys.size()>0){
//			for (String key :keys) {
//				List<Object> models=new ArrayList<Object>();
//				Class clazz=mdoelMap2.get(key);
//				Field[] fields=clazz.getDeclaredFields();
//				String[] values=new String[fields.length];
//				List<String> info=new ArrayList<String>();
//				String allposition=snMap.get(key);
//				//获得开始位置
//				int index=Integer.valueOf(allposition.substring(0,allposition.indexOf("|")));
//				//获得结束位置
//				int end=Integer.valueOf(allposition.substring(allposition.indexOf("|")+1));
//				//清空数据
//				info.clear();
//				//重新计数
//				int count=0;
//				//获得集合第一条数据
//				String result=allinfo.get(index);
//				values=result.split(" ");
//				//比较相同次数
//				count=getCount(fields,values);
//				//除了第一条数据其他都能匹配到
//				if(count>(fields.length/2)){
//					for (int k = index; k < end; k++) {
//						info.add(allinfo.get(k));
//					}
//					
//					//获得分割后字符次数组和属性数组的相对位置（每个集合对应一个map）
//					Map<Integer, Integer> map=getMap(clazz,info.get(0).split("\\{#\\|#\\}"));
//					//移除第一个假数据
//					info.remove(0);
//					//循环赋值
//					int xsb = 0;
//					Object model = null;
//					String[] str= null;
//					for (String ss : info) {
//						if(StringHelper.isNotEmpty(ss)){
//							//获取分割后的数组
//							str = ss.split("\\{#\\|#\\}");
//							//赋值
//							model=setValue(map,str,clazz,clazz.newInstance(), clazz.getDeclaredFields());
//							//JSONArray.fromObject(model);
//							//添加到集合中
//							System.out.println(xsb++);
//							models.add(model);
//						}
//					}
//					
//				}
//				if(models.size()>0){
//					mmpp.put(key, models);	
//				}
//			}
//		}
//		return mmpp;
//	}
//	
//	
//	/**
//	 * 保存的list 要转换为别的类型在此配置
//	 * @return
//	 */
//	public static Map<String,Class> getModelMap(){
//		Map<String,Class> map=new HashMap<String, Class>();
//		//业务数据接口
//		
////		map.put("NDJHSH", NdjhshModel.class);
////		map.put("NDJHSH_SHB", NdjhshSHBModel.class);
////		map.put("NDJHSH_JHMX", NdjhshJhmxModel.class);
////		map.put("NDJHSH_JHMX_SHB", NdjhshJhmxSHBModel.class);
////		map.put("XJSB", XjsbModel.class);
////		map.put("XJSB_SHB", XjsbSHBModel.class);
////		map.put("XJSB_JHMX", XjsbJhmxModel.class);
////		map.put("XJSB_JHMX_SHB", XjsbJhmxSHBModel.class);
////		map.put("YWJSB", YwjsbModel.class);
////		map.put("YWJSB_SHB", YwjsbSHBModel.class);
////		map.put("YWJSB_JHMX", YwjsbJhmxModel.class);
////		map.put("YWJSB_JHMX_SHB", YwjsbJhmxSHBModel.class);
//		
////		map.put("listcsv1", BDSBNdbjjhModel.class);
////		map.put("listcsv2", NdjhshModel.class);
////		map.put("listcsv3", NdjhshSHBModel.class);
////		map.put("listcsv4", JhmxModel.class);
////		map.put("listcsv5", YWJSHNdbjjhModel.class);
////		map.put("listcsv6", SJXDNdbjjhModel.class);
////		map.put("listcsv7", CFSJXDNdbjjhModel.class);
////		
////		//基础数据接口
//		map.put("listcsv11", BaseequipmentcatalogDTO.class);
//		//map.put("listcsv12", ZbslModel.class);
//		//map.put("listcsv13", XmmlModel.class);
//		//map.put("listcsv14", YsdwModel.class);
//		//map.put("listcsv15", SxdwModel.class);
//		//map.put("listcsv16", CxdwModel.class);
//		//map.put("listcsv17", CbdwModel.class);
//		//map.put("listcsv18", ZgbmModel.class);
//		/*map.put("listcsv19", SxdwYsdwRelModel.class);
//		map.put("listcsv20", ZblbZgbmRelModel.class);
//		map.put("listcsv21", ZbxlZgbmRelModel.class);*/
//		map.put("listcsv22", BaseplancatalogDTO.class);
//		//map.put("listcsv23", ZblbsModel.class);
//		//9月11日新增
//		/*map.put("listcsv24", BbdwModel.class);
//		map.put("listcsv25", BbdwYsdwGxModel.class);
//		map.put("listcsv26", ZbslBbdwGXModel.class);
//		map.put("listcsv27", PcglModel.class);
//		map.put("listcsv28", SxdwYsdwRelModel.class);
//		map.put("listcsv29", BbdwZbmlGxModel.class);
//		map.put("listcsv30", YsdwSxModel.class);
//		map.put("listcsv31", ZzjgZgbmGxModel.class);*/
//		
//		//9月11日新增end
//		
//		
//		//20180823 
//		/*map.put("listcsv32", TzjhNdjhxdModel.class);
//		map.put("listcsv33", TzjhNdjhxdZfshModel.class);
//		map.put("listcsv34", TzjhNdjhxdJhmxModel.class);
//		
//		map.put("listcsv35", NdjhxdComModel.class);
//		map.put("listcsv36", NdjhxdSHBModel.class);
//		map.put("listcsv37", NdjhxdComJhmxModel.class);
//		map.put("listcsv38", NdjhxdJhmxSHBModel.class);*/
//		//20190121新增
//		//map.put("listcsv39", XtzdTypeModel.class);
//		map.put("listcsv40", BasemeasureunitDTO.class);
//		map.put("listcsv41", SysorgDTO.class);
//		return map;
//	}
//	
//	
//	
//	/**
//	 * 获得@@@|||在数据中的位置
//	 * @param allinfo
//	 * @return
//	 */
//	public static List<Integer> getPosition(List<String> allinfo){
//		List<Integer> position=new ArrayList<Integer>();
//		for (int i = 0; i < allinfo.size(); i++) {
//			String value=allinfo.get(i);
//			if("@@@|||".equals(value.trim())){
//				position.add(i);
//			}
//		}
//		position.add(allinfo.size());
//		return position;
//	}
//	
//	public static int getCount(Field[] fields,String[] values){
//		for (int i=0;i<values.length;i++) {
//			if(values[i].indexOf("{#|#}")!=-1){
//				values[i]=values[i].substring(5,values[i].length());
//			}
//		}
//		//获得传过来的类名
//		int count=0;
//		for (int f = 0; f < values.length; f++) {
//			for (int k = 0; k < fields.length; k++) {
//				if(fields[k].getName().toString().toUpperCase().equals(values[f].toUpperCase())){
//					count++;
//				}
//			}
//		}
//		return count;
//	}
//	public static Map<Integer, Integer> getMap(Class<?> clazz,String[] result){
//		boolean flag=false;
//		Field[] fields=clazz.getDeclaredFields();
//		for (Field field : fields) {
//			if(field.getName().toString().indexOf("ialVersion")!=-1){
//				flag=true;
//				break;
//			}
//		}
//		Map<Integer, Integer> map=new HashMap<Integer, Integer>();
//		if(flag==true){
//			for (int i = 1; i < result.length; i++) {
//				String str=result[i].toUpperCase().trim();
//				for (int j = 1; j < fields.length; j++) {
//					if(fields[j].getName().toString().indexOf("ialVersion")==-1){
//						String val=fields[j].getName().toString().toUpperCase();
//						if(str.equals(val)){
//							map.put(j,i);
//							break;
//						}
//					}
//				}
//			}
//		}
//		return map;
//	}
//
//	public static Object setValue(Map map,String[] info,Class<?> clazz,Object obj,Field[] fields) throws Exception{
//		//对象初始化
//		//Object obj=clazz.newInstance();
//		//获取类的所有字段
//		//Field[] fields=clazz.getDeclaredFields();
//		//Set<Integer> keys=map.keySet();
//		//通过循环给对应的数组赋值
//		for (Integer i : (Set<Integer>)map.keySet()) {
//			//Integer index=(Integer) map.get(i);
//			String result=info[(Integer) map.get(i)].toString();
//			result=result.replace("^^^", " ");
//			///如果|||是null
//			if(result.equals("||| ")){
//				result=null;
//				//如果||是“”
//			}else if(result.toString().equals("|| ")){
//				result ="";
//			}else{
//				result=result.trim();
//			}
////			if(result!=null&&result.indexOf(" \"")!=-1){
////				if(result.substring(result.indexOf(result.length()-2)).equals(" \"")){
////					result=result.substring(0,result.length()-2);
////				}
////			}
////			if(result.indexOf("\"")!=-1){
////				
////			}
//			//打开强行访问
//			fields[i].setAccessible(true);
//			//String type=fields[i].getType().toString();
//			if(fields[i].getType().toString().equals("double")){
//				fields[i].set(obj, Double.valueOf(result));
//			}else if(fields[i].getType().toString().equals("long")){
//				fields[i].set(obj, Long.valueOf(result));
//			}else if(fields[i].getType().toString().equals("class java.util.Date")){
//				//System.out.println(result);
//				if("".equals(result)||null==result){
//					fields[i].set(obj,null);
//				}else{
//					
//					//System.out.println(sdf.parse(result));
//					fields[i].set(obj,sdf.parse(result));
//				}
//			}else if(fields[i].getType().toString().equals("int")){
//				fields[i].set(obj, Integer.valueOf(result));
//			}else{
//				fields[i].set(obj, result);
//			}
//			
//			result = null;
//		}
//		
//		return obj;
//	}
//}
