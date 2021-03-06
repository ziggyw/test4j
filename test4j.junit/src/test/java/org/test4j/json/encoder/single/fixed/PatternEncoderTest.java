package org.test4j.json.encoder.single.fixed;

import java.util.regex.Pattern;

import org.junit.Test;
import org.test4j.json.encoder.EncoderTest;
import org.test4j.json.encoder.single.fixed.PatternEncoder;

public class PatternEncoderTest extends EncoderTest {

	@Test
	public void testPatternEncoder() {
		Pattern pattern = Pattern.compile("abc\\d+\\/\\w+$\"\'abc");
		PatternEncoder encoder = PatternEncoder.instance;
		this.setUnmarkFeature(encoder);

		encoder.encode(pattern, writer, references);
		String json = writer.toString();
		want.string(json).isEqualTo("'abc\\\\d+\\\\/\\\\w+$\\\"\\'abc'");
	}
}
