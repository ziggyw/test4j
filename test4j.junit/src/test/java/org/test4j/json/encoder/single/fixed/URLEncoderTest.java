package org.test4j.json.encoder.single.fixed;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import org.test4j.json.encoder.EncoderTest;
import org.test4j.json.encoder.single.fixed.URLEncoder;

public class URLEncoderTest extends EncoderTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testURLEncoder() throws Exception {
		URL url = new File("d:/temp/1.txt").toURL();
		URLEncoder encoder = URLEncoder.instance;
		this.setUnmarkFeature(encoder);

		encoder.encode(url, writer, references);
		String json = writer.toString();
		want.string(json).isEqualTo("'file:/d:/temp/1.txt'");
	}
}
