package com.think.config;

import java.io.Serializable;

/**
 * 应用实体类
 * 
 * @author veione
 * 
 */
public class ApplicationModel implements Serializable {
	private static final long serialVersionUID = -5370218463053585412L;
	private String name;
	private String cls;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	@Override
	public String toString() {
		return "ApplicationModel[name=" + name + ",cls=" + cls + "]";
	}

}
