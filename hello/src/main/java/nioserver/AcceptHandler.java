package nioserver;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptHandler implements Handler{

	public void handler(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel=(ServerSocketChannel) key.channel();
		SocketChannel socketChannel=serverSocketChannel.accept();
		if(socketChannel==null) return;
		System.out.println("接受到来自"+
				socketChannel.socket().getInetAddress()+":"+
				socketChannel.socket().getPort()+"的连接");
		ChannelIO cio=new ChannelIO(socketChannel, false);
		
		socketChannel.register(key.selector(),SelectionKey.OP_READ, null);
	}

}
