package io.github.microvibe.tika;

import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(org.junit.runners.Parameterized.class)
public class TikaReaderTest {
    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "test.doc" },
                { "test.docm" },
                { "test.docx" },
                { "test.htm" },
                { "test.mht" },
                { "test.pdf" },
                { "test.rtf" },
                { "test.xml" },
                { "test.xlsx" },
        });
    }

    @Parameter(0)
    public String filename;

    @Before
    public void before() {
        for (int i : new int[100]) {
            System.out.print("-");
        }
        System.out.println();
    }

    @Test
    public void test001() throws Exception {
        InputStream in = getClass().getResourceAsStream(filename);
        Tika tika = new Tika();
        String filetype = tika.detect(in);
        System.out.println(filetype);
        in.close();
    }

    @Test
    public void test002() throws Exception {
        InputStream in = getClass().getResourceAsStream(filename);
        Tika tika = new Tika();
        Reader reader = tika.parse(in);
        System.out.println(IOUtils.toString(reader));
        in.close();
    }

    @Test
    public void test003() throws Exception {
        Tika tika = new Tika();
        InputStream in = getClass().getResourceAsStream(filename);
        String filetype = tika.detect(in);
        System.out.println(filetype);
        String str = tika.parseToString(in);
        System.out.println(str);
        in.close();
    }

    @Test
    public void test004() throws Exception {
        Tika tika = new Tika();
        InputStream in = getClass().getResourceAsStream(filename);
        String filetype = tika.detect(in);
        System.out.println(filetype);

        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata(); // empty metadata object
        ParseContext context = new ParseContext();
        parser.parse(in, handler, metadata, context);

        System.out.println(Arrays.toString(metadata.names()));
        for (String name : metadata.names()) {
            System.out.printf("%s = %s%n", name, metadata.get(name));
        }
        in.close();
    }

    @Test
    public void test005() throws Exception {
        Tika tika = new Tika();
        InputStream in = getClass().getResourceAsStream(filename);
        String filetype = tika.detect(in);
        System.out.println(filetype);

      //Parser method parameters
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        //Parsing the given document
        parser.parse(in, handler, metadata, new ParseContext());
        
//        LanguageDetector.getDefaultLanguageDetector();
        LanguageIdentifier object = new LanguageIdentifier(handler.toString());
        System.out.println("Language name :" + object.getLanguage());
        in.close();
    }

}
