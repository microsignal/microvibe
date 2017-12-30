package io.githut.microvibe.tika;

import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;

public class TikaParserTest {

    @Test
    public void test001() throws Exception {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        InputStream inputstream = getClass().getResourceAsStream("test.pdf");
        ParseContext pcontext = new ParseContext();

        // parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata, pcontext);

        // getting the content of the document
        System.out.println("Contents of the PDF :" + handler.toString());

        // getting metadata of the document
        System.out.println("Metadata of the PDF:");
        String[] metadataNames = metadata.names();

        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
        }
    }

    @Test
    public void test002() throws Exception {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        InputStream inputstream = getClass().getResourceAsStream("test.htm");
        ParseContext pcontext = new ParseContext();
        Parser parser = new HtmlParser();
        parser.parse(inputstream, handler, metadata, pcontext);
        System.out.println("Contents :" + handler.toString());
        // getting metadata of the document
        System.out.println("Metadata :");
        String[] metadataNames = metadata.names();
        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
        }
    }

}
