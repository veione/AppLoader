package com.think;

import com.think.app.ApplicationManager;
import com.think.config.Configuration;
import com.think.config.DefaultConfiguration;

/**
 * 主函数
 * @author veione
 *
 */
public class App {

	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
            public void run() {
            	Configuration configuration = new DefaultConfiguration("conf/loader.xml");
        		ApplicationManager manager = ApplicationManager.getInstance();
        		manager.setConfiguration(configuration);
        		manager.init();
            }
        });
		t.setName("AppLoader-Thread");
        t.start();

        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
	}

}
