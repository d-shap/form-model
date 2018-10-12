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
package ru.d_shap.formmodel;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link XmlDocumentBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentBuilderTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public XmlDocumentBuilderTest() {
        super();
    }

    /**
     * {@link XmlDocumentBuilder} class test.
     */
    @Test
    public void createNewObjectTest() {
        Assertions.assertThat(new XmlDocumentBuilder()).isNotNull();
        Assertions.assertThat(new XmlDocumentBuilder(null)).isNotNull();
        Assertions.assertThat(new XmlDocumentBuilder(new XmlDocumentBuilderConfiguratorImpl())).isNotNull();

        try {
            new XmlDocumentBuilder(new ErrorXmlDocumentBuilderConfiguratorImpl());
            Assertions.fail("XmlDocumentBuilder test fail");
        } catch (XmlDocumentBuilderConfigurationException ex) {
            Assertions.assertThat(ex).hasCause(ParserConfigurationException.class);
        }
    }

    /**
     * {@link XmlDocumentBuilder} class test.
     */
    @Test
    public void getDocumentBuilderTest() {
        Assertions.assertThat(XmlDocumentBuilder.getDocumentBuilder()).isNotNull();
        Assertions.assertThat(XmlDocumentBuilder.getDocumentBuilder()).isNotSameAs(XmlDocumentBuilder.getDocumentBuilder());

        Assertions.assertThat(XmlDocumentBuilder.getDocumentBuilder(null)).isNotNull();
        Assertions.assertThat(XmlDocumentBuilder.getDocumentBuilder(new XmlDocumentBuilderConfiguratorImpl())).isNotNull();

        try {
            XmlDocumentBuilder.getDocumentBuilder(new ErrorXmlDocumentBuilderConfiguratorImpl());
            Assertions.fail("XmlDocumentBuilder test fail");
        } catch (XmlDocumentBuilderConfigurationException ex) {
            Assertions.assertThat(ex).hasCause(ParserConfigurationException.class);
        }
    }

    /**
     * {@link XmlDocumentBuilder} class test.
     */
    @Test
    public void newDocumentTest() {
        Document document = XmlDocumentBuilder.getDocumentBuilder().newDocument();
        Assertions.assertThat(document).isNotNull();
        Assertions.assertThat(XmlDocumentBuilder.getDocumentBuilder().newDocument()).isNotSameAs(document);
    }

    /**
     * {@link XmlDocumentBuilder} class test.
     */
    @Test
    public void parseTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document xmlns:ns1='http://example.com'>";
        xml += "<ns1:element>value</ns1:element>";
        xml += "</document>";
        Document document = XmlDocumentBuilder.getDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        Assertions.assertThat(document).isNotNull();
        Assertions.assertThat(document.getDocumentElement().getTagName()).isEqualTo("document");
        Assertions.assertThat(document.getDocumentElement().getNamespaceURI()).isNull();
        Assertions.assertThat(document.getDocumentElement().getLocalName()).isEqualTo("document");
        Assertions.assertThat(((Element) document.getDocumentElement().getFirstChild()).getTagName()).isEqualTo("ns1:element");
        Assertions.assertThat(document.getDocumentElement().getFirstChild().getNamespaceURI()).isEqualTo("http://example.com");
        Assertions.assertThat(document.getDocumentElement().getFirstChild().getLocalName()).isEqualTo("element");
        Assertions.assertThat(document.getDocumentElement().getTextContent()).isEqualTo("value");
    }

    /**
     * {@link XmlDocumentBuilder} class test.
     */
    @Test
    public void parseFailTest() {
        try {
            XmlDocumentBuilder.getDocumentBuilder().parse(new InputSource(new ReadErrorInputStream()));
            Assertions.fail("XmlDocumentBuilder test fail");
        } catch (InputSourceException ex) {
            Assertions.assertThat(ex).hasMessage("READ ERROR!");
            Assertions.assertThat(ex).hasCause(IOException.class);
        }
    }

}
