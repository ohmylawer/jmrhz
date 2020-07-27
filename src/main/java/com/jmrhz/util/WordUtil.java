package com.jmrhz.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jmrhz.company.dto.RecommendDTO;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class WordUtil {
	private Configuration configure = null;

	public WordUtil() {
		configure = new Configuration();
		configure.setDefaultEncoding("utf-8");
	}

	/**
	 * 根据DOC模板生成word文件
	 * 
	 * @param dataList
	 *            word中需要放的数据
	 * @param tempFileName
	 *            模板文件名
	 * @param filePath
	 *            生成文件存放的路径
	 * @throws IOException
	 * @throws TemplateException
	 */
	public boolean createDoc(Map<String, Object> dataList, String tempFileName, String filePath)
			throws IOException, TemplateException {
		Writer out = null;
		try {
			// 装载模板
			Template temp = null;
			// 加载模板文件
			configure.setDirectoryForTemplateLoading(new File(GetPathUtil.getModulesPath()));
			// 设置对象包装器
			configure.setObjectWrapper(new DefaultObjectWrapper());
			// 设置异常处理器
			configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			// 定义Template对象，注意模板类型名字与fileName要一致
			temp = configure.getTemplate(tempFileName);
			// 输出文档
			File outFile = new File(filePath);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
			temp.process(dataList, out);
			// window下，执行delete方法没有问题，linux下执行，会把文件删除
			// outFile.delete();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (TemplateException e) {
			e.printStackTrace();
			try {
				throw e;
			} catch (TemplateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw e1;
			}
		} finally {
			out.flush();
			out.close();
		}
	}

	public static void main(String[] args) throws IOException, TemplateException {

		List<RecommendDTO> relationList = new ArrayList<RecommendDTO>();
		for (int i = 0; i < 10; i++) {
			RecommendDTO relation = new RecommendDTO();
			relation.setIndex(i + 1);
			relation.setCompanyName("公司" + i);
			relation.setProductName("产品" + i);
			relation.setRelationPerson("联系人 " + i);
			relation.setPhone("电话" + i);
			relation.setRemark("备注 " + i);
			relationList.add(relation);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relationList", relationList);
		String tempFileName = "report";

		String filePath = GetPathUtil.getPath() + File.separator + "ccc.doc";

		new WordUtil().createDoc(map, tempFileName, filePath);
	}
}
