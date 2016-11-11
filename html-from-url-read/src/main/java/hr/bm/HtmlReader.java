package hr.bm;

import java.io.InputStream;
import java.net.URL;

public class HtmlReader {

	public StringBuffer getHtml(String urlPath) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(urlPath);
			InputStream is = url.openStream();
			int ptr = 0;
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}

//	public String findElementValue(String urlPath) {
//		String 
//	}
}
