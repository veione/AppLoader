package com.think.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.think.config.ApplicationModel;
import com.think.config.ConfigurationManager;

/**
 * xml工具类
 * 
 * @author veione
 * 
 */
public class XmlParseUtil {
	/**
	 * 反射设置实体不同类型字段的值 <暂时只支持 日期 字符串 boolean Integer值设置 待扩建>
	 * 
	 * @param field
	 * @param obj
	 * @param value
	 * @throws Exception
	 */
	public static void convertValue(Field field, Object obj, String value)
			throws Exception {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (field.getGenericType().toString().equals("class java.lang.Integer")) {
			field.set(obj, Integer.parseInt(value));
		} else if (field.getGenericType().toString().equals("boolean")) {
			field.set(obj, Boolean.parseBoolean(value));
		} else if (field.getGenericType().toString()
				.equals("class java.util.Date")) {
			field.set(obj, sim.parse(value));
		} else {
			field.set(obj, value);
		}

	}

	/**
	 * 解析xml文件返回保存List集合
	 * 
	 * @param xml
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<?> parseXml2List(String xml) throws Exception {
		List<Object> lists = null;
		Document doc = DocumentHelper.parseText(xml);
		Element et = doc.getRootElement();
		String appDirectory = et.attribute("directory").getText();
		ConfigurationManager.getInstance().setAppDirectory(appDirectory);

		List<Element> appsList = et.elements();
		if (!appsList.isEmpty() && appsList.size() > 0) {
			// 判断对象父节点是否有包含数据
			lists = new ArrayList<Object>();
			for (Element e : appsList) {
				List<Element> li = e.elements();
				ApplicationModel model = new ApplicationModel();
				for (Element element2 : li) {
					String name = element2.getName();
					String value = element2.getText();
					Field field = model.getClass().getDeclaredField(name);
					field.setAccessible(true);
					convertValue(field, model, value);
				}
				lists.add(model);
			}
		}
		return lists;
	}
}