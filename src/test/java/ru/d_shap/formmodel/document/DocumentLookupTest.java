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

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link DocumentLookup}.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentLookupTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public DocumentLookupTest() {
        super();
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void createNewObjectTest() {
        Assertions.assertThat(new DocumentLookup()).isNotNull();
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getDocumentLookupTest() {
        Assertions.assertThat(DocumentLookup.getDocumentLookup()).isNotNull();
        Assertions.assertThat(DocumentLookup.getDocumentLookup()).isNotSameAs(DocumentLookup.getDocumentLookup());
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value1</element>";
        xml += "<element>value2</element>";
        xml += "</document>";
        Document document = parse(xml);
        List<Element> elements1 = DocumentLookup.getDocumentLookup().getElements(document, "/document");
        Assertions.assertThat(elements1).hasSize(1);
        Assertions.assertThat(elements1.get(0).getTextContent()).isEqualTo("value1value2");

        List<Element> elements2 = DocumentLookup.getDocumentLookup().getElements(document, "//element");
        Assertions.assertThat(elements2).hasSize(2);
        Assertions.assertThat(elements2.get(0).getTextContent()).isEqualTo("value1");
        Assertions.assertThat(elements2.get(1).getTextContent()).isEqualTo("value2");
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWrongReturnTypeTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<document>";
        xml1 += "<element attr='val'>value</element>";
        xml1 += "<!--COMMENT TEXT-->";
        xml1 += "</document>";
        Document document1 = parse(xml1);
        List<Element> elements1 = DocumentLookup.getDocumentLookup().getElements(document1, "//element/@attr");
        Assertions.assertThat(elements1).hasSize(0);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document>";
        xml2 += "<element>value</element>";
        xml2 += "<!--COMMENT TEXT-->";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        List<Element> elements2 = DocumentLookup.getDocumentLookup().getElements(document2, "//element/following::comment()[1]");
        Assertions.assertThat(elements2).hasSize(0);
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWrongXPathFailTest() {
        try {
            String xml = "<?xml version='1.0'?>\n";
            xml += "<document>";
            xml += "<element>value</element>";
            xml += "<!--COMMENT TEXT-->";
            xml += "</document>";
            Document document = parse(xml);
            DocumentLookup.getDocumentLookup().getElements(document, "//element/wrongaxisname::comment()[1]");
            Assertions.fail("DocumentLookup test fail");
        } catch (DocumentLookupException ex) {
            Assertions.assertThat(ex).hasCause(XPathExpressionException.class);
        }
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWithIdTest() {

    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWithAttributeTest() {

    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedElementsTest() {

    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedElementsWithClassTest() {

    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedAttributesTest() {

    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedAttributesWithClassTest() {

    }

}
