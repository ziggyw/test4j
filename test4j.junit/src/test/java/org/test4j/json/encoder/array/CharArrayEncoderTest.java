package org.test4j.json.encoder.array;

import java.io.StringWriter;
import java.util.ArrayList;

import org.junit.Test;
import org.test4j.json.encoder.JSONEncoder;
import org.test4j.json.helper.JSONFeature;
import org.test4j.junit.Test4J;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CharArrayEncoderTest extends Test4J {
    @Test
    public void testEncode() throws Exception {
        char[] values = new char[] { 'a', '\n', '"' };

        JSONEncoder encoder = JSONEncoder.get(values.getClass());
        encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);

        StringWriter writer = new StringWriter();
        encoder.encode(values, writer, new ArrayList<String>());
        String json = writer.toString();
        want.string(json).eqIgnoreSpace("['a','\\n','\\\"']");
    }

    @Test
    public void testEncode_Character() throws Exception {
        Character[] values = new Character[] { '\'', '\t', null };

        JSONEncoder encoder = JSONEncoder.get(values.getClass());
        want.object(encoder).clazIs(ObjectArrayEncoder.class);

        encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);

        StringWriter writer = new StringWriter();
        encoder.encode(values, writer, new ArrayList<String>());
        String json = writer.toString();
        want.string(json).eqIgnoreSpace("['\\'','\\t',null]");
    }
}
