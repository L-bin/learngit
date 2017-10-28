package nioserver;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Handler {
	public void handler(SelectionKey key) throws IOException;
}
