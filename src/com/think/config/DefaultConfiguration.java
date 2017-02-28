package com.think.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.think.util.IOUtil;
import com.think.util.XmlParseUtil;

/**
 * 默认配置文件读取类
 * 
 * @author veione
 * 
 */
public class DefaultConfiguration implements Configuration {
	private String configFile; // 配置文件
	private boolean hasConfiguration = false; // 是否已经加载配置

	public DefaultConfiguration() {
	}
	
	public DefaultConfiguration(String configFile) {
		this.configFile = configFile;
		ConfigurationManager.getInstance().setConfigFile(configFile);
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load() {
		try {
			File file = new File(configFile);
			InputStream in = new FileInputStream(file);
			String xml = IOUtil.getContent(in);
			List<ApplicationModel> list = (List<ApplicationModel>) XmlParseUtil.parseXml2List(xml);
			if(list != null && list.size() > 0){
				Map<String, ApplicationModel> applicationModels = new HashMap<String, ApplicationModel>();
				for(ApplicationModel model : list){
					applicationModels.put(model.getName(), model);
				}
				ConfigurationManager.getInstance().setApplicationModels(applicationModels);
			}
			hasConfiguration = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isHasConfiguration() {
		return hasConfiguration;
	}

	public void setHasConfiguration(boolean hasConfiguration) {
		this.hasConfiguration = hasConfiguration;
	}

}
