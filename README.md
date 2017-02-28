# AppLoader
一个用于Java热更新项目的工具

项目架构：
![github](https://github.com/veione/AppLoader/blob/master/structure.png "structure")  

项目使用规则：

1、将项目的入口类实现自com.think.Application接口
<pre>
package com.think.test;

import com.think.app.Application;

public class TestLoader2 implements Application{
    public void init() {
        System.out.println("Test2----------->init");
        System.out.println("11111111111111111");
    }

    public void destory() {
        System.out.println("Test2----------->destory");
    }
}
</pre>


2、配置文件loader.xml(可参考代码中的loader.xml配置文件)
<?xml version="1.0" encoding="UTF-8"?>
<apps directory="apps/">
    <app>
        <name>Test2</name>
        <cls>com.think.test.TestLoader2</cls>
    </app>
</apps>

directory属性是设置你项目部署的文件夹,所以这里不能出错，因为监听器会时刻检查这个目录的改变，
一旦目录改变或者项目jar包变动就会进行自动热替换。


3、开启服务
<pre>
package com.think;

import com.think.app.ApplicationManager;
import com.think.config.Configuration;
import com.think.config.DefaultConfiguration;

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
</pre>

最后

项目必须要以jar包的形式发布，监听器会监听jar包目录是否发生改变，如果发生改变就会自动进行热替换功能。

