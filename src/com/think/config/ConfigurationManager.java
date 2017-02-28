package com.think.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置管理器
 * 
 * @author veione
 * 
 */
public class ConfigurationManager {
	private static ConfigurationManager instance;	// 应用实例
	private Map<String,ApplicationModel> applicationModels; // 配置文件中的实例
	private String configFile;	// 配置文件名称
	private String appDirectory;	// 应用目录

	/**
	 * 私有化构造函数
	 */
	private ConfigurationManager() {
		applicationModels = new ConcurrentHashMap<String, ApplicationModel>();
	}

	/**
	 * 获取管理类实例
	 * 
	 * @return
	 */
	public static ConfigurationManager getInstance() {
		if (instance == null) {
			synchronized (ConfigurationManager.class) {
				if (instance == null) {
					instance = new ConfigurationManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 获取应用实例
	 * @return
	 */
	public ApplicationModel getApplication(String appName){
		if(this.applicationModels.containsKey(appName)){
			return this.applicationModels.get(appName);
		}
		return null;
	}

	public Map<String, ApplicationModel> getApplicationModels() {
		return applicationModels;
	}

	public void setApplicationModels(Map<String, ApplicationModel> applicationModels) {
		this.applicationModels = applicationModels;
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getAppDirectory() {
		return appDirectory;
	}

	public void setAppDirectory(String appDirectory) {
		this.appDirectory = appDirectory;
	}
	
}
