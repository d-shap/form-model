///////////////////////////////////////////////////////////////////////////////////////////////////
// Form model library is a form definition API and a form binding API.
// Copyright (C) 2018 Dmitry Shapovalov.
//
// This file is part of form model library.
//
// Form model library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Form model library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.formmodel.document;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.w3c.dom.Document;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.OutputResultException;

/**
 * Tests for {@link DocumentWriter}.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentWriterTest extends BaseFormModelTest {

    private static final String SEPARATOR = System.getProperty("line.separator");

    /**
     * Test class constructor.
     */
    public DocumentWriterTest() {
        super();
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(DocumentWriter.class).hasOnePrivateConstructor();
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToWriterDefaultTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);
        StringWriter stringWriter = new StringWriter();
        DocumentWriter.writeTo(document, stringWriter);
        Assertions.assertThat(stringWriter.getBuffer()).isEqualTo("<document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToWriterDefaultFailTest() {
        try {
            String xml = "<?xml version='1.0'?>\n";
            xml += "<document>";
            xml += "<element>value</element>";
            xml += "</document>";
            Document document = parse(xml);
            document.setXmlStandalone(true);
            DocumentWriter.writeTo(document, new WriteErrorWriter());
            Assertions.fail("DocumentWriterTest test fail");
        } catch (OutputResultException ex) {
            Assertions.assertThat(ex).hasCause(TransformerException.class);
        }
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToWriterWithXmlDeclarationTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);
        StringWriter stringWriter = new StringWriter();
        DocumentWriter.withXmlDeclaration().writeTo(document, stringWriter);
        Assertions.assertThat(stringWriter.getBuffer()).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToWriterWithEncodingTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);
        StringWriter stringWriter = new StringWriter();
        DocumentWriter.withEncoding(ENCODING_UTF_16).writeTo(document, stringWriter);
        Assertions.assertThat(stringWriter.getBuffer()).isEqualTo("<document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToWriterWithStandaloneTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);
        StringWriter stringWriter = new StringWriter();
        DocumentWriter.withStandalone().writeTo(document, stringWriter);
        Assertions.assertThat(stringWriter.getBuffer()).isEqualTo("<document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToWriterWithIndentTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);
        StringWriter stringWriter = new StringWriter();
        DocumentWriter.withIndent().writeTo(document, stringWriter);
        Assertions.assertThat(stringWriter.getBuffer()).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void closeTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);
        StringWriter stringWriter = new StringWriter();
        CloseableWriter closeableWriter = new CloseableWriter(stringWriter);
        Assertions.assertThat(closeableWriter.isClosed()).isFalse();
        DocumentWriter.writeTo(document, closeableWriter);
        Assertions.assertThat(closeableWriter.isClosed()).isFalse();
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void stderrTest() {
        PrintStream stderr = System.err;
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream, true, ENCODING_UTF_8);
                System.setErr(printStream);
                try {
                    String xml = "<?xml version='1.0'?>\n";
                    xml += "<document>";
                    xml += "<element>value</element>";
                    xml += "</document>";
                    Document document = parse(xml);
                    document.setXmlStandalone(true);
                    DocumentWriter.writeTo(document, new WriteErrorWriter());
                } catch (OutputResultException ex) {
                    Assertions.assertThat(ex).hasCause(TransformerException.class);
                }
                String message = new String(byteArrayOutputStream.toByteArray(), ENCODING_UTF_8);
                Assertions.assertThat(message).isBlank();
            } catch (UnsupportedEncodingException ex) {
                Assertions.fail(ex.getMessage());
            }
        } finally {
            System.setErr(stderr);
        }
    }

    /**
     * {@link DocumentWriter} class test.
     *
     * @throws UnsupportedEncodingException unsupported encoding exception.
     */
    @Test
    public void writeToOutputStreamDefaultTest() throws UnsupportedEncodingException {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        DocumentWriter.writeTo(document, byteArrayOutputStream1, ENCODING_UTF_8);
        byte[] bytes1 = byteArrayOutputStream1.toByteArray();
        Assertions.assertThat(bytes1).hasLength(45);
        Assertions.assertThat(new String(bytes1, ENCODING_UTF_8)).isEqualTo("<document><element>value</element></document>");

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DocumentWriter.writeTo(document, byteArrayOutputStream2, ENCODING_UTF_16);
        byte[] bytes2 = byteArrayOutputStream2.toByteArray();
        Assertions.assertThat(bytes2).hasLength(92);
        Assertions.assertThat(new String(bytes2, ENCODING_UTF_16)).isEqualTo("<document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToOutputStreamDefaultFailTest() {
        try {
            String xml = "<?xml version='1.0'?>\n";
            xml += "<document>";
            xml += "<element>value</element>";
            xml += "</document>";
            Document document = parse(xml);
            document.setXmlStandalone(true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DocumentWriter.writeTo(document, byteArrayOutputStream, "wrong encoding");
            Assertions.fail("DocumentWriterTest test fail");
        } catch (OutputResultException ex) {
            Assertions.assertThat(ex).hasCause(UnsupportedEncodingException.class);
        }
    }

    /**
     * {@link DocumentWriter} class test.
     *
     * @throws UnsupportedEncodingException unsupported encoding exception.
     */
    @Test
    public void writeToOutputStreamWithXmlDeclarationTest() throws UnsupportedEncodingException {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        DocumentWriter.withXmlDeclaration().writeTo(document, byteArrayOutputStream1, ENCODING_UTF_8);
        byte[] bytes1 = byteArrayOutputStream1.toByteArray();
        Assertions.assertThat(bytes1).hasLength(99);
        Assertions.assertThat(new String(bytes1, ENCODING_UTF_8)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><document><element>value</element></document>");

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DocumentWriter.withXmlDeclaration().writeTo(document, byteArrayOutputStream2, ENCODING_UTF_16);
        byte[] bytes2 = byteArrayOutputStream2.toByteArray();
        Assertions.assertThat(bytes2).hasLength(202);
        Assertions.assertThat(new String(bytes2, ENCODING_UTF_16)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     *
     * @throws UnsupportedEncodingException unsupported encoding exception.
     */
    @Test
    public void writeToOutputStreamWithEncodingTest() throws UnsupportedEncodingException {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        DocumentWriter.withEncoding(ENCODING_UTF_16).writeTo(document, byteArrayOutputStream1, ENCODING_UTF_8);
        byte[] bytes1 = byteArrayOutputStream1.toByteArray();
        Assertions.assertThat(bytes1).hasLength(45);
        Assertions.assertThat(new String(bytes1, ENCODING_UTF_8)).isEqualTo("<document><element>value</element></document>");

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DocumentWriter.withEncoding(ENCODING_UTF_16).writeTo(document, byteArrayOutputStream2, ENCODING_UTF_16);
        byte[] bytes2 = byteArrayOutputStream2.toByteArray();
        Assertions.assertThat(bytes2).hasLength(92);
        Assertions.assertThat(new String(bytes2, ENCODING_UTF_16)).isEqualTo("<document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     *
     * @throws UnsupportedEncodingException unsupported encoding exception.
     */
    @Test
    public void writeToOutputStreamWithStandaloneTest() throws UnsupportedEncodingException {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        DocumentWriter.withStandalone().writeTo(document, byteArrayOutputStream1, ENCODING_UTF_8);
        byte[] bytes1 = byteArrayOutputStream1.toByteArray();
        Assertions.assertThat(bytes1).hasLength(45);
        Assertions.assertThat(new String(bytes1, ENCODING_UTF_8)).isEqualTo("<document><element>value</element></document>");

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DocumentWriter.withStandalone().writeTo(document, byteArrayOutputStream2, ENCODING_UTF_16);
        byte[] bytes2 = byteArrayOutputStream2.toByteArray();
        Assertions.assertThat(bytes2).hasLength(92);
        Assertions.assertThat(new String(bytes2, ENCODING_UTF_16)).isEqualTo("<document><element>value</element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     *
     * @throws UnsupportedEncodingException unsupported encoding exception.
     */
    @Test
    public void writeToOutputStreamWithIndentTest() throws UnsupportedEncodingException {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value</element>";
        xml += "</document>";
        Document document = parse(xml);
        document.setXmlStandalone(true);

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        DocumentWriter.withIndent().writeTo(document, byteArrayOutputStream1, ENCODING_UTF_8);
        byte[] bytes1 = byteArrayOutputStream1.toByteArray();
        Assertions.assertThat(bytes1).hasLength(45 + SEPARATOR.length() * 3);
        Assertions.assertThat(new String(bytes1, ENCODING_UTF_8)).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DocumentWriter.withIndent().writeTo(document, byteArrayOutputStream2, ENCODING_UTF_16);
        byte[] bytes2 = byteArrayOutputStream2.toByteArray();
        Assertions.assertThat(bytes2).hasLength(92 + SEPARATOR.length() * 6);
        Assertions.assertThat(new String(bytes2, ENCODING_UTF_16)).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void getAsStringDefaultTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<document>";
        xml1 += "<element>value</element>";
        xml1 += "</document>";
        Document document1 = parse(xml1);
        document1.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<document><element>value</element></document>");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void getAsStringWithXmlDeclarationTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<document>";
        xml1 += "<element>value</element>";
        xml1 += "</document>";
        Document document1 = parse(xml1);
        document1.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andEncoding(ENCODING_UTF_16).getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andStandalone().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andIndent().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + SEPARATOR + "<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andEncoding(ENCODING_UTF_16).getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andStandalone().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andIndent().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + SEPARATOR + "<document xmlns:ns1=\"http://example.com\">" + SEPARATOR + "<ns1:element>value</ns1:element>" + SEPARATOR + "</document>" + SEPARATOR);
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void getAsStringWithEncodingTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<document>";
        xml1 += "<element>value</element>";
        xml1 += "</document>";
        Document document1 = parse(xml1);
        document1.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).getAsString(document1)).isEqualTo("<document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).andXmlDeclaration().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).andStandalone().getAsString(document1)).isEqualTo("<document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).andIndent().getAsString(document1)).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).andXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).andStandalone().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding(ENCODING_UTF_16).andIndent().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">" + SEPARATOR + "<ns1:element>value</ns1:element>" + SEPARATOR + "</document>" + SEPARATOR);
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void getAsStringWithStandaloneTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<document>";
        xml1 += "<element>value</element>";
        xml1 += "</document>";
        Document document1 = parse(xml1);
        document1.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withStandalone().getAsString(document1)).isEqualTo("<document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andXmlDeclaration().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andEncoding(ENCODING_UTF_16).getAsString(document1)).isEqualTo("<document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andIndent().getAsString(document1)).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withStandalone().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andEncoding(ENCODING_UTF_16).getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andIndent().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">" + SEPARATOR + "<ns1:element>value</ns1:element>" + SEPARATOR + "</document>" + SEPARATOR);
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void getAsStringWithIndentTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<document>";
        xml1 += "<element>value</element>";
        xml1 += "</document>";
        Document document1 = parse(xml1);
        document1.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withIndent().getAsString(document1)).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);
        Assertions.assertThat(DocumentWriter.withIndent().andXmlDeclaration().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + SEPARATOR + "<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);
        Assertions.assertThat(DocumentWriter.withIndent().andEncoding(ENCODING_UTF_16).getAsString(document1)).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);
        Assertions.assertThat(DocumentWriter.withIndent().andStandalone().getAsString(document1)).isEqualTo("<document>" + SEPARATOR + "<element>value</element>" + SEPARATOR + "</document>" + SEPARATOR);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withIndent().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">" + SEPARATOR + "<ns1:element>value</ns1:element>" + SEPARATOR + "</document>" + SEPARATOR);
        Assertions.assertThat(DocumentWriter.withIndent().andXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + SEPARATOR + "<document xmlns:ns1=\"http://example.com\">" + SEPARATOR + "<ns1:element>value</ns1:element>" + SEPARATOR + "</document>" + SEPARATOR);
        Assertions.assertThat(DocumentWriter.withIndent().andEncoding(ENCODING_UTF_16).getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">" + SEPARATOR + "<ns1:element>value</ns1:element>" + SEPARATOR + "</document>" + SEPARATOR);
        Assertions.assertThat(DocumentWriter.withIndent().andStandalone().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">" + SEPARATOR + "<ns1:element>value</ns1:element>" + SEPARATOR + "</document>" + SEPARATOR);
    }

    /**
     * {@link DocumentWriter} class test.
     *
     * @throws TransformerException transformer exception.
     */
    @Test
    public void skipErrorListenerMethodsTest() throws TransformerException {
        DocumentWriter.SkipErrorListener skipErrorListener = new DocumentWriter.SkipErrorListener();

        skipErrorListener.warning(new TransformerException("Message"));
        skipErrorListener.warning(new TransformerException(""));
        skipErrorListener.warning(new TransformerException((String) null));
        skipErrorListener.warning(null);

        skipErrorListener.error(new TransformerException("Message"));
        skipErrorListener.error(new TransformerException(""));
        skipErrorListener.error(new TransformerException((String) null));
        skipErrorListener.error(null);

        skipErrorListener.fatalError(new TransformerException("Message"));
        skipErrorListener.fatalError(new TransformerException(""));
        skipErrorListener.fatalError(new TransformerException((String) null));
        skipErrorListener.fatalError(null);
    }

}
