package nioserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelIO {
	private SocketChannel socketChannel = null;
	private ByteBuffer requestBuffer = null;
	private int requestBufferSize = 4096;// 4M

	public ChannelIO(SocketChannel socketChannel, boolean blocking) throws IOException {
		socketChannel.configureBlocking(blocking);
		this.socketChannel = socketChannel;
		requestBuffer = ByteBuffer.allocate(requestBufferSize);
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	protected void resizeRequestBuffer(int remain) {
		if (requestBuffer.remaining() < remain) {
			requestBufferSize = requestBuffer.capacity() * 2;
			ByteBuffer buffer = ByteBuffer.allocate(requestBuffer.capacity() * 2);
			requestBuffer.flip();
			buffer.put(requestBuffer);
			requestBuffer = buffer;
		}
	}

	public int read() throws IOException {
		resizeRequestBuffer(requestBufferSize / 20);
		return socketChannel.read(requestBuffer);
	}

	public ByteBuffer getReadBuf() {
		return requestBuffer;
	}

	public int write(ByteBuffer src) throws IOException {
		return socketChannel.write(src);
	}

	// public long
	
	
	public void close() throws IOException {
		socketChannel.close();
	}
}
