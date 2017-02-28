package com.think.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO操作工具类
 * @author veione
 *
 */
public final class IOUtil {
	private static final int EOF = -1;// End of line
	private IOUtil(){
		
	}
	
	/**
	 * 从InputStream转换成字符串
	 * @param in 输入流
	 * @return
	 */
	public static String getContent(InputStream in){
		StringBuilder builder = new StringBuilder();
		BufferedInputStream bis = new BufferedInputStream(in);
		int hasRead = 0;
		try{
			byte[] buffer = new byte[in.available()];
			while((hasRead = bis.read(buffer))!=EOF){
				builder.append(new String(buffer, 0, hasRead));
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
