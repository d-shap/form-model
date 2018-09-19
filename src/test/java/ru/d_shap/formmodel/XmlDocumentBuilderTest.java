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
    public void constructorTest() {
        Assertions.assertThat(XmlDocumentBuilder.class).hasOnePrivateConstructor();
    }

    /**
     * {@link XmlDocumentBuilder} class test.
     */
    @Test
    public void newDocumentTest() {
        Document document = XmlDocumentBuilder.newDocument();
        Assertions.assertThat(document).isNotNull();
        Assertions.assertThat(XmlDocumentBuilder.newDocument()).isNotSameAs(document);
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
        Document document = XmlDocumentBuilder.parse(new InputSource(new StringReader(xml)));
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
            XmlDocumentBuilder.parse(new InputSource(new ErrorInputStream()));
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
