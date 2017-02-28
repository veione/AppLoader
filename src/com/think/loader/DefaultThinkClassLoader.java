package com.think.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


/**
 * 默认提供的类加载器实现类
 * 
 * @author veione
 * 
 */
public class DefaultThinkClassLoader implements ThinkClassLoader {

	@Override
	public ClassLoader getClassLoader(ClassLoader parentClsLoader,String... folders) {
		List<URL> loadJars = new ArrayList<URL>();
		for (String folder : folders) {
			List<String> jarPaths = getJarFiles(folder);
			for (String jar : jarPaths) {
				try {
					File file = new File(jar);
					loadJars.add(file.toURI().toURL());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		URL[] urls = new URL[loadJars.size()];
		loadJars.toArray(urls);
		return new URLClassLoader(urls, parentClsLoader);
	}

	/**
	 * 根据文件夹加载Jar文件
	 * @param folder
	 * @return
	 */
	private List<String> getJarFiles(String folderPath) {
		List<String> jars = new ArrayList<String>();
		File folder = new File(folderPath);
		if(!folder.isDirectory()){
			throw new IllegalArgumentException("the folder is not a directory. folder:"+folderPath);
		}
		for(File f : folder.listFiles()){
			if(!f.isFile()){
				continue;
			}
			String name = f.getName();
			
			// 检查文件是否是jar包文件
			if(name == null || name.length() == 0){
				continue;
			}
			
			int extIndex = name.lastIndexOf(".");
			if(extIndex < 0){
				continue;
			}
			String ext = name.substring(extIndex);
			if(!ext.equalsIgnoreCase(".jar")){
				continue;
			}
			
			jars.add(folderPath + File.separator + name);
		}
		return jars;
	}

}
