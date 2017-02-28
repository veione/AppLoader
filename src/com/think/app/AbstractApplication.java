package com.think.app;

/**
 * 抽象应用类
 * @author 凌星
 *
 */
public abstract class AbstractApplication implements Application {
	private boolean appRun;
	
	@Override
	public void init() {
		throw new IllegalArgumentException("invalid init");
	}
	
	@Override
	public void destory() {
		throw new IllegalArgumentException("invalid destory");
	}

	public boolean isAppRun() {
		return appRun;
	}

	public void setAppRun(boolean appRun) {
		this.appRun = appRun;
	}
	
}
