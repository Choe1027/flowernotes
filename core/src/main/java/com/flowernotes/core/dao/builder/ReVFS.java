package com.flowernotes.core.dao.builder;

import org.apache.ibatis.io.VFS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** 重写扫描（为解决扫描jar文件，同时也解决SpringBootVFS未重写list(String)方法） */
public class ReVFS extends VFS {
	private final ResourcePatternResolver resourceResolver;

	public ReVFS() {
		this.resourceResolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	protected List<String> list(URL url, String path) throws IOException {
		Resource[] resources = resourceResolver.getResources("classpath*:" + path + "/**/*.class");
		List<String> resourcePaths = new ArrayList<String>();
		for (Resource resource : resources) {
			resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
		}
		return resourcePaths;
	}

	@Override
	public List<String> list(String path) throws IOException {
		Resource[] resources = resourceResolver.getResources("classpath*:" + path + "/**/*.class");
		List<String> resourcePaths = new ArrayList<String>();
		for (Resource resource : resources) {
			resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
		}
		return resourcePaths;
	}

	private static String preserveSubpackageName(final URI uri, final String rootPath) {
		final String uriStr = uri.toString();
		final int start = uriStr.indexOf(rootPath);
		if(start == -1) {
			String temp = rootPath.substring(0, rootPath.indexOf("*"));
			return uriStr.substring(uriStr.indexOf(temp));
		}
		return uriStr.substring(start);
	}
}