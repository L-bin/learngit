package nioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;


public class HttpServer {

	private Selector selector=null;
	private ServerSocketChannel serverSocketChannel=null;
	private int port=80;
	private Charset charset=Charset.forName("GBK");
	public HttpServer() throws IOException{
		selector=Selector.open();
		serverSocketChannel=ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().setReuseAddress(true);
		//serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}
	public void service() throws IOException{
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		for(;;){
			int n=selector.select();
			if(n==0) return ;
			Set keys=selector.selectedKeys();
			Iterator i=keys.iterator();
			while(i.hasNext()){
				SelectionKey key=(SelectionKey)i.next();
				i.remove();
				key.attachment();
			}
		}
	}
	public static void main(String[] args) throws IOException{
		final HttpServer server=new HttpServer();
		server.service();
	}
}
