package server.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable{
	boolean stopped;
	private String scheme="http";
	public String getScheme(){
		return scheme;
	}
	public void run() {
		ServerSocket serverSocket=null;
		try {
			serverSocket=new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		while(!stopped){
			Socket socket=null;
			try {
				socket=serverSocket.accept();
			} catch (IOException e) {
				continue;
			}
			//httpprocessor
			HttpProcessor processor=new HttpProcessor();
			processor.process(socket);
		}
	}
	
	public void start(){
		Thread thread=new Thread(this);
		thread.start();
	}
}
