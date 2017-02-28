package com.think.listener;

import org.apache.commons.vfs.FileChangeEvent;
import org.apache.commons.vfs.FileListener;

/**
 * 文件监听抽象类
 * 
 * @author veione
 * 
 */
public abstract class AbstractFolderListener implements FileListener {

	@Override
	public abstract void fileChanged(FileChangeEvent event);

	@Override
	public void fileCreated(FileChangeEvent event) throws Exception {
		System.out.println("fileCreated");
	}

	@Override
	public void fileDeleted(FileChangeEvent event) throws Exception {
		System.out.println("fileDeleted");
	}

}
