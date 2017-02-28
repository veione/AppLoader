package com.think.listener;

import org.apache.commons.vfs.FileChangeEvent;
import com.think.app.ApplicationManager;

/**
 * 默认提供的文件监听实现
 * @author veione
 *
 */
public class DefaultFolderListener extends AbstractFolderListener {
	
	/**
	 * 文件改变时触发,选择更新应用程序
	 */
	@Override
	public void fileChanged(FileChangeEvent event) {
		String ext = event.getFile().getName().getExtension();
		if(!"jar".equals(ext)){
			return;
		}
		
		String appName = event.getFile().getName().getParent().getBaseName();
		ApplicationManager.getInstance().reloadApplication(appName);
		System.out.println("application:"+appName+" has reload successfully.");
	}

}
