package com.think.app;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.vfs.FileListener;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileMonitor;

import com.think.config.ApplicationModel;
import com.think.config.Configuration;
import com.think.config.ConfigurationManager;
import com.think.config.DefaultConfiguration;
import com.think.listener.DefaultFolderListener;
import com.think.loader.DefaultThinkClassLoader;
import com.think.loader.ThinkClassLoader;

/**
 * 应用管理类
 * 
 * @author 凌星
 * 
 */
public final class ApplicationManager {
	private static ApplicationManager instance;
	private Map<String, Application> apps; // 存储应用的实例对象
	private ThinkClassLoader loader; // 加载器
	private FileSystemManager fileSystemManager; // 文件管理对象
	private DefaultFileMonitor fileMonitor; // 文化监视器
	private DefaultConfiguration configuration; // 文件配置
	
	/**
	 * 私有化构造
	 */
	private ApplicationManager() {
	}

	/**
	 * 获取管理类实例对象
	 * 
	 * @return
	 */
	public static ApplicationManager getInstance() {
		if (instance == null) {
			synchronized (ApplicationManager.class) {
				if (instance == null) {
					instance = new ApplicationManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 设置配置类
	 * @param configuration
	 */
	public void setConfiguration(Configuration configuration){
		this.configuration = (DefaultConfiguration) configuration;
		configuration.load();
	}
	
	public void setConfiguration(String configurationFile){
		this.configuration = new DefaultConfiguration(configurationFile);
		setConfiguration(configuration);
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		this.loader = new DefaultThinkClassLoader();
		this.apps = new ConcurrentHashMap<String, Application>();
		if(!configuration.isHasConfiguration()){
			throw new IllegalArgumentException("No configuration file specified");
		}
		initApps();
		initFolderListener();
	}
	
	/**
	 * 初始化应用对象
	 */
	private void initApps() {
		Map<String,ApplicationModel> applicationModels = ConfigurationManager.getInstance().getApplicationModels();
		Set<String> appSets = applicationModels.keySet();
		Iterator<String> iterator = appSets.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			ApplicationModel model = applicationModels.get(key);
			this.loadApplication(model);
		}		
	}

	/**
	 * 初始化文件监听
	 */
	private void initFolderListener(){
		try {
			fileSystemManager = VFS.getManager();
			File file = new File(getBasePath() + ConfigurationManager.getInstance().getAppDirectory());
			FileObject monitoredDir = this.fileSystemManager.resolveFile(file.getAbsolutePath());
			FileListener fileMonitorListener = new DefaultFolderListener();
			this.fileMonitor = new DefaultFileMonitor(fileMonitorListener);
			this.fileMonitor.setRecursive(true);
			this.fileMonitor.addFile(monitoredDir);
			this.fileMonitor.start();
			System.out.println("Now listen to "+ monitoredDir.getName().getPath());
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获取应用实例根据应用名称
	 * 
	 * @param appName
	 * @return
	 */
	public Application getApplication(String appName) {
		if (apps.containsKey(appName)) {
			return apps.get(appName);
		}
		return null;
	}

	/**
	 * 获取已经部署的项目集合
	 * 
	 * @return
	 */
	public Map<String, Application> getApps() {
		return apps;
	}

	/**
	 * 重新加载应用程序
	 * @param appName
	 */
	public void reloadApplication(String appName) {
		Application app = this.apps.remove(appName);
		
		if(app == null){
			return;
		}
		// 先销毁之前的应用
		app.destory();
		
		ApplicationModel model = ConfigurationManager.getInstance().getApplication(appName);
		if(model == null){
			return;
		}
		// 加载新的应用
		loadApplication(model);
	}
	
	/**
	 * 加载应用
	 * @param basePath 路径
	 * @param object 
	 */
	private void loadApplication(ApplicationModel model) {
		String folderPath = getBasePath() + ConfigurationManager.getInstance().getAppDirectory() + model.getName();
		ClassLoader loader = this.loader.getClassLoader(ApplicationManager.class.getClassLoader(),folderPath);
		
		// 使用loader加载对应的application
		try {
			Class<?> cls = loader.loadClass(model.getCls());
			try {
				Application app = (Application)cls.newInstance();
				// 初始化操作
				app.init();
				// 放入集合中
				apps.put(model.getName(), app);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前路径
	 * @return
	 */
	private String getBasePath() {
		return this.getClass().getClassLoader().getResource("").getPath();
	}
}
