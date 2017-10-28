package server;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletProcessor1 {
	public void process(Request request, Response response) {
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf('/') + 1);
		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(Constants.WEB_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
			urls[0] = new URL(null, repository, streamHandler);
			System.out.println(urls[0].toString());
			loader = new URLClassLoader(urls);
		} catch (Exception e) {
			System.out.println(e);
		}
		Class myClass=null;
		try {
			myClass=loader.loadClass(servletName);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		Servlet servlet=null;
		try {
			servlet=(Servlet) myClass.newInstance();
			servlet.service((ServletRequest)request,(ServletResponse) response);
		} catch (Exception e) {
			System.out.println(e);
		} catch (Throwable e) {
			System.out.println(e);
		} 
	}
}
