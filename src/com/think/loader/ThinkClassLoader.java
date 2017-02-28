package com.think.loader;

/**
 * 自定义加载器接口
 * 
 * @author veione
 * 
 */
public interface ThinkClassLoader {
	/**
	 * 获取类加载器
	 * 
	 * @param parentClsLoader
	 *            父级加载器
	 * @param paths
	 *            路径
	 * @return
	 */
	ClassLoader getClassLoader(ClassLoader parentClsLoader, String... paths);
}
