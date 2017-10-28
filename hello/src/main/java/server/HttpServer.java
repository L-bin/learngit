package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {
	public static final String WEB_ROOT="D:/mytomcat"+File.separator+"webroot";  //todo
	
	public static final String SHUTDOWN_COMMAND="/SHUTDOWN";  
	
	private boolean shutdown=false;
	
	public static void main(String[] args){
		HttpServer server=new HttpServer();
		server.await();
	}
	
	public void await(){
		ServerSocket serverSocket=null;
		int port=8080;
		try {
			serverSocket=new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		}  catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while(!shutdown){
			Socket socket=null;
			InputStream input=null;
			OutputStream output=null;
			try {
				socket=serverSocket.accept();
				input=socket.getInputStream();
				output=socket.getOutputStream();
				//构造request对象
				Request request=new Request(input);
				request.parse();
				//构造response对象
				Response response=new Response(output);
				response.setRequest(request);
				
				//判断是否请求servlet
				if(request.getUri().startsWith("/servlet/")){
					ServletProcessor1 processor=new ServletProcessor1();
					processor.process(request, response);
				}else{
					StaticResourceProcessor processor=new StaticResourceProcessor();
					processor.process(request, response);
				}
				//关闭SOCKET
				socket.close();
				//shutdown=request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
