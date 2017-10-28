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
		System.out.println("���ܵ�����"+
				socketChannel.socket().getInetAddress()+":"+
				socketChannel.socket().getPort()+"������");
		ChannelIO cio=new ChannelIO(socketChannel, false);
		
		socketChannel.register(key.selector(),SelectionKey.OP_READ, null);
	}

}
