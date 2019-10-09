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
package ru.d_shap.formmodel.definition.loader.xml;

import java.io.StringReader;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;

/**
 * Tests for {@link FormXmlDefinitionsLoader}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsLoaderTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionsLoaderTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionsLoader} class test.
     */
    @Test
    public void parseTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document xmlns:ns1='http://example.com'>";
        xml += "<ns1:element>value</ns1:element>";
        xml += "</document>";

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader1 = new FormXmlDefinitionsLoaderImpl();
        Document document1 = formXmlDefinitionsLoader1.parse(new InputSource(new StringReader(xml)));
        Assertions.assertThat(document1.getDocumentElement().getTagName()).isEqualTo("document");
        Assertions.assertThat(document1.getDocumentElement().getFirstChild().getNamespaceURI()).isEqualTo("http://example.com");
        Assertions.assertThat(document1.getDocumentElement().getFirstChild().getLocalName()).isEqualTo("element");
        Assertions.assertThat(document1.getDocumentElement().getFirstChild().getTextContent()).isEqualTo("value");

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader2 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        Document document2 = formXmlDefinitionsLoader2.parse(new InputSource(new StringReader(xml)));
        Assertions.assertThat(document2.getDocumentElement().getTagName()).isEqualTo("document");
        Assertions.assertThat(document2.getDocumentElement().getFirstChild().getNamespaceURI()).isEqualTo("http://example.com");
        Assertions.assertThat(document2.getDocumentElement().getFirstChild().getLocalName()).isEqualTo("element");
        Assertions.assertThat(document2.getDocumentElement().getFirstChild().getTextContent()).isEqualTo("value");

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader3 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1);
        Document document3 = formXmlDefinitionsLoader3.parse(new InputSource(new StringReader(xml)));
        Assertions.assertThat(document3.getDocumentElement().getTagName()).isEqualTo("document");
        Assertions.assertThat(document3.getDocumentElement().getFirstChild().getNamespaceURI()).isEqualTo("http://example.com");
        Assertions.assertThat(document3.getDocumentElement().getFirstChild().getLocalName()).isEqualTo("element");
        Assertions.assertThat(document3.getDocumentElement().getFirstChild().getTextContent()).isEqualTo("value");
    }

    /**
     * {@link FormXmlDefinitionsLoader} class test.
     */
    @Test
    public void validateTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='id' type='optional'>";
        xml1 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml1 += "</ns1:element>";
        xml1 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader11 = new FormXmlDefinitionsLoaderImpl();
        formXmlDefinitionsLoader11.validate(document1);

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader12 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        formXmlDefinitionsLoader12.validate(document1);

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader13 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader11);
        formXmlDefinitionsLoader13.validate(document1);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:single-element id='id' type='optional'>";
        xml2 += "<ns1:elemenT lookup='lookup' repr='repr1' count='-1'>";
        xml2 += "</ns1:elemenT>";
        xml2 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader21 = new FormXmlDefinitionsLoaderImpl();
        try {
            formXmlDefinitionsLoader21.validate(document2);
            Assertions.fail("FormXmlDefinitionsLoader test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasCause(SAXException.class);
        }

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader22 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        try {
            formXmlDefinitionsLoader22.validate(document2);
            Assertions.fail("FormXmlDefinitionsLoader test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasCause(SAXException.class);
        }

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader23 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader21);
        try {
            formXmlDefinitionsLoader23.validate(document2);
            Assertions.fail("FormXmlDefinitionsLoader test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasCause(SAXException.class);
        }

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:single-element id='id' type='optional'>";
        xml3 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml3 += "</ns1:element>";
        xml3 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml3 += "<!--INVALID!-->";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:single-element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader31 = new FormXmlDefinitionsLoaderImpl();
        try {
            formXmlDefinitionsLoader31.validate(document3);
            Assertions.fail("FormXmlDefinitionsLoader test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Invalid comment found!");
        }

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader32 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        try {
            formXmlDefinitionsLoader32.validate(document3);
            Assertions.fail("FormXmlDefinitionsLoader test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Invalid comment found!");
        }

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader33 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader31);
        try {
            formXmlDefinitionsLoader33.validate(document3);
            Assertions.fail("FormXmlDefinitionsLoader test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Invalid comment found!");
        }
    }

    /**
     * {@link FormXmlDefinitionsLoader} class test.
     */
    @Test
    public void getFormXmlDefinitionBuilderTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "</ns1:form>";
        Document document = parse(xml);

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader1 = new FormXmlDefinitionsLoaderImpl();
        Assertions.assertThat(formXmlDefinitionsLoader1.getFormXmlDefinitionBuilder()).isNotNull();
        Assertions.assertThat(formXmlDefinitionsLoader1.getFormXmlDefinitionBuilder().isFormDefinition(document.getDocumentElement())).isTrue();

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader2 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        Assertions.assertThat(formXmlDefinitionsLoader2.getFormXmlDefinitionBuilder()).isNotNull();
        Assertions.assertThat(formXmlDefinitionsLoader2.getFormXmlDefinitionBuilder().isFormDefinition(document.getDocumentElement())).isTrue();

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader3 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1);
        Assertions.assertThat(formXmlDefinitionsLoader3.getFormXmlDefinitionBuilder()).isNotNull();
        Assertions.assertThat(formXmlDefinitionsLoader3.getFormXmlDefinitionBuilder().isFormDefinition(document.getDocumentElement())).isTrue();
    }

}
