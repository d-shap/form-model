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
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link XmlDocumentBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentBuilderTest {

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
        Assertions.assertThat(new XmlDocumentBuilder(new DocumentBuilderFactoryConfiguratorImpl())).isNotNull();

        try {
            new XmlDocumentBuilder(new ErrorDocumentBuilderFactoryConfiguratorImpl());
            Assertions.fail("XmlDocumentBuilder test fail");
        } catch (XmlDocumentBuilderException ex) {
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
        Assertions.assertThat(XmlDocumentBuilder.getDocumentBuilder(new DocumentBuilderFactoryConfiguratorImpl())).isNotNull();

        try {
            XmlDocumentBuilder.getDocumentBuilder(new ErrorDocumentBuilderFactoryConfiguratorImpl());
            Assertions.fail("XmlDocumentBuilder test fail");
        } catch (XmlDocumentBuilderException ex) {
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
        xml += "<document>";
        xml += "value";
        xml += "</document>";
        Document document = XmlDocumentBuilder.getDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        Assertions.assertThat(document).isNotNull();
        Assertions.assertThat(document.getDocumentElement().getTagName()).isEqualTo("document");
        Assertions.assertThat(document.getDocumentElement().getTextContent()).isEqualTo("value");
    }

    /**
     * {@link XmlDocumentBuilder} class test.
     */
    @Test
    public void parseFailTest() {
        try {
            XmlDocumentBuilder.getDocumentBuilder().parse(new InputSource(new ErrorInputStream()));
            Assertions.fail("XmlDocumentBuilder test fail");
        } catch (InputSourceReadException ex) {
            Assertions.assertThat(ex).hasMessage("ERROR!");
            Assertions.assertThat(ex).hasCause(IOException.class);
        }
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class DocumentBuilderFactoryConfiguratorImpl implements XmlDocumentBuilderConfigurator {

        DocumentBuilderFactoryConfiguratorImpl() {
            super();
        }

        @Override
        public void configure(final DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
            documentBuilderFactory.setNamespaceAware(true);
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorDocumentBuilderFactoryConfiguratorImpl implements XmlDocumentBuilderConfigurator {

        ErrorDocumentBuilderFactoryConfiguratorImpl() {
            super();
        }

        @Override
        public void configure(final DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setFeature("some fake feature", true);
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorInputStream extends InputStream {

        ErrorInputStream() {
            super();
        }

        @Override
        public int read() throws IOException {
            throw new IOException("ERROR!");
        }

    }

}
