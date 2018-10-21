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

import java.io.StringWriter;

import org.junit.Test;
import org.w3c.dom.Document;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link DocumentWriter}.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentWriterTest extends BaseFormModelTest {

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
        DocumentWriter.withEncoding("UTF-16").writeTo(document, stringWriter);
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
        Assertions.assertThat(stringWriter.getBuffer()).isEqualTo("<document>\r\n<element>value</element>\r\n</document>\r\n");
    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToOutputStreamDefaultTest() {

    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToOutputStreamWithXmlDeclarationTest() {

    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToOutputStreamWithEncodingTest() {

    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToOutputStreamWithStandaloneTest() {

    }

    /**
     * {@link DocumentWriter} class test.
     */
    @Test
    public void writeToOutputStreamWithIndentTest() {

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
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andEncoding("UTF-16").getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andStandalone().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andIndent().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<document>\r\n<element>value</element>\r\n</document>\r\n");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andEncoding("UTF-16").getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andStandalone().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withXmlDeclaration().andIndent().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<document xmlns:ns1=\"http://example.com\">\r\n<ns1:element>value</ns1:element>\r\n</document>\r\n");
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
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").getAsString(document1)).isEqualTo("<document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").andXmlDeclaration().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").andStandalone().getAsString(document1)).isEqualTo("<document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").andIndent().getAsString(document1)).isEqualTo("<document>\r\n<element>value</element>\r\n</document>\r\n");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").andXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-16\" standalone=\"no\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").andStandalone().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withEncoding("UTF-16").andIndent().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">\r\n<ns1:element>value</ns1:element>\r\n</document>\r\n");
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
        Assertions.assertThat(DocumentWriter.withStandalone().andEncoding("UTF-16").getAsString(document1)).isEqualTo("<document><element>value</element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andIndent().getAsString(document1)).isEqualTo("<document>\r\n<element>value</element>\r\n</document>\r\n");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withStandalone().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andEncoding("UTF-16").getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\"><ns1:element>value</ns1:element></document>");
        Assertions.assertThat(DocumentWriter.withStandalone().andIndent().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">\r\n<ns1:element>value</ns1:element>\r\n</document>\r\n");
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
        Assertions.assertThat(DocumentWriter.withIndent().getAsString(document1)).isEqualTo("<document>\r\n<element>value</element>\r\n</document>\r\n");
        Assertions.assertThat(DocumentWriter.withIndent().andXmlDeclaration().getAsString(document1)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<document>\r\n<element>value</element>\r\n</document>\r\n");
        Assertions.assertThat(DocumentWriter.withIndent().andEncoding("UTF-16").getAsString(document1)).isEqualTo("<document>\r\n<element>value</element>\r\n</document>\r\n");
        Assertions.assertThat(DocumentWriter.withIndent().andStandalone().getAsString(document1)).isEqualTo("<document>\r\n<element>value</element>\r\n</document>\r\n");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document xmlns:ns1='http://example.com'>";
        xml2 += "<ns1:element>value</ns1:element>";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        document2.setXmlStandalone(true);
        Assertions.assertThat(DocumentWriter.withIndent().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">\r\n<ns1:element>value</ns1:element>\r\n</document>\r\n");
        Assertions.assertThat(DocumentWriter.withIndent().andXmlDeclaration().getAsString(document2)).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<document xmlns:ns1=\"http://example.com\">\r\n<ns1:element>value</ns1:element>\r\n</document>\r\n");
        Assertions.assertThat(DocumentWriter.withIndent().andEncoding("UTF-16").getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">\r\n<ns1:element>value</ns1:element>\r\n</document>\r\n");
        Assertions.assertThat(DocumentWriter.withIndent().andStandalone().getAsString(document2)).isEqualTo("<document xmlns:ns1=\"http://example.com\">\r\n<ns1:element>value</ns1:element>\r\n</document>\r\n");
    }

}
