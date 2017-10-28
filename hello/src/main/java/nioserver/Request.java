package nioserver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;

public class Request {
	static class Action{
		private String name;
		private Action(String name){
			this.name=name;
		}
		static Action GET=new Action("GET");
		static Action POST=new Action("POST");
		static Action PUT=new Action("PUT");
		static Action HEAD=new Action("HEAD");
		
		public static Action parse(String s){
			if(s.equals("GET"))
				return GET;
			if(s.equals("POST"))
				return POST;
			if(s.equals("PUT"))
				return PUT;
			if(s.equals("HEAD"))
				return HEAD;
			throw new IllegalArgumentException();
		}
		
	}
	private Action action;
	private String version;
	private URI uri;
	
	
	public Action getAction() {
		return action;
	}

	public String getVersion() {
		return version;
	}


	public URI getUri() {
		return uri;
	}

	public Request(Action a,String v,URI u){
		this.action=a;
		this.version=v;
		this.uri=u;
	}

	@Override
	public String toString() {
		return action+" "+version+" "+uri;
	}
	
	private static Charset requestCharset=Charset.forName("GBK");
	
	public static boolean isComplete(ByteBuffer bb){
		ByteBuffer temp=bb.asReadOnlyBuffer();
		temp.flip();
		String data=requestCharset.decode(temp).toString();
		if(data.indexOf("\r\n\r\n")!=-1){
			return true;
		}
		return false;
	}
	private static ByteBuffer deleteContent(ByteBuffer bb){
		ByteBuffer temp=bb.asReadOnlyBuffer();
		temp.flip();
		String data=requestCharset.decode(bb).toString();
		if(data.indexOf("\r\n\r\n")!=-1){
			data=data.substring(0, data.indexOf("\r\n\r\n")+4);
			return requestCharset.encode(data);
		}
		return bb;
	}
	
	private static Pattern requestPattern=null;
	
	private static Request parse(ByteBuffer bb) throws MalformedURLException{
		bb=deleteContent(bb);
		CharBuffer cb=requestCharset.decode(bb);
		Matcher m=requestPattern.matcher(cb);
		if(!m.matches())
			throw new MalformedURLException();
		Action a;
		try{
			a=Action.parse(m.group(1));
		}catch(IllegalArgumentException e){
			throw new MalformedURLException();
		}
		URI u;
		try {
			u=new URI("http://"+m.group(4)+m.group(2));
		} catch (URISyntaxException e) {
			throw new MalformedURLException();
		}
		return new Request(a, m.group(3), u);
	}
}
