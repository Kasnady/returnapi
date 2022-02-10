package com.kasnady.returnapi.testreturnapi.util;

import java.io.InputStream;
import java.net.URISyntaxException;

public class FileResourceUtil {

	/**
	 * Get File from Resources folder as Stream
	 * 
	 * @param classLoader
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 */
	public InputStream getFileUriFromResourceAsStream(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}
}
