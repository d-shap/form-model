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

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Tests for {@link FormXmlDefinitionBuilderImpl}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionBuilderImplTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionBuilderImplTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isFormDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Assertions.assertThat(createBuilder().isFormDefinition(document1.getDocumentElement())).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Assertions.assertThat(createBuilder().isFormDefinition(document2.getDocumentElement())).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Assertions.assertThat(createBuilder().isFormDefinition(document3.getDocumentElement())).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:FORM>";
        Document document4 = parse(xml4);
        Assertions.assertThat(createBuilder().isFormDefinition(document4.getDocumentElement())).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<form id='id1'>";
        xml5 += "</form>";
        Document document5 = parse(xml5);
        Assertions.assertThat(createBuilder().isFormDefinition(document5.getDocumentElement())).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(document1.getDocumentElement(), "source1");
        Assertions.assertThat(formDefinition1).isNotNull();
        Assertions.assertThat(formDefinition1.getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinition1.getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinition1.getSource()).isEqualTo("source1");
        Assertions.assertThat(formDefinition1.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        FormDefinition formDefinition2 = createBuilder().createFormDefinition(document2.getDocumentElement(), "source2");
        Assertions.assertThat(formDefinition2).isNotNull();
        Assertions.assertThat(formDefinition2.getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinition2.getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinition2.getSource()).isEqualTo("source2");
        Assertions.assertThat(formDefinition2.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formDefinition2.getOtherAttributeNames()).containsExactly();

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            createBuilder().createFormDefinition(document3.getDocumentElement(), "source3");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition is not valid: {http://example.com}form]");
        }

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "</ns1:FORM>";
            Document document4 = parse(xml4);
            createBuilder().createFormDefinition(document4.getDocumentElement(), "source4");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition is not valid: {http://d-shap.ru/schema/form-model/1.0}FORM]");
        }

        try {
            String xml5 = "<?xml version='1.0'?>\n";
            xml5 += "<form id='id1'>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            createBuilder().createFormDefinition(document5.getDocumentElement(), "source5");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition is not valid: form]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isAttributeDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:attribute>";
        Document document1 = parse(xml1);
        Assertions.assertThat(createBuilder().isAttributeDefinition(document1.getDocumentElement())).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<attribute id='id3' lookup='lookup3' type='required' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</attribute>";
        Document document2 = parse(xml2);
        Assertions.assertThat(createBuilder().isAttributeDefinition(document2.getDocumentElement())).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required' xmlns:ns1='http://example.com'>";
        xml3 += "</ns1:attribute>";
        Document document3 = parse(xml3);
        Assertions.assertThat(createBuilder().isAttributeDefinition(document3.getDocumentElement())).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:ATTRIBUTE id='id3' lookup='lookup3' type='required' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:ATTRIBUTE>";
        Document document4 = parse(xml4);
        Assertions.assertThat(createBuilder().isAttributeDefinition(document4.getDocumentElement())).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<attribute id='id3' lookup='lookup3' type='required'>";
        xml5 += "</attribute>";
        Document document5 = parse(xml5);
        Assertions.assertThat(createBuilder().isAttributeDefinition(document5.getDocumentElement())).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isElementDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:element>";
        Document document1 = parse(xml1);
        Assertions.assertThat(createBuilder().isElementDefinition(document1.getDocumentElement())).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<element id='id2' lookup='lookup2' type='required' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</element>";
        Document document2 = parse(xml2);
        Assertions.assertThat(createBuilder().isElementDefinition(document2.getDocumentElement())).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required' xmlns:ns1='http://example.com'>";
        xml3 += "</ns1:element>";
        Document document3 = parse(xml3);
        Assertions.assertThat(createBuilder().isElementDefinition(document3.getDocumentElement())).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:ELEMENT id='id2' lookup='lookup2' type='required' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:ELEMENT>";
        Document document4 = parse(xml4);
        Assertions.assertThat(createBuilder().isElementDefinition(document4.getDocumentElement())).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<element id='id2' lookup='lookup2' type='required'>";
        xml5 += "</element>";
        Document document5 = parse(xml5);
        Assertions.assertThat(createBuilder().isElementDefinition(document5.getDocumentElement())).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isSingleElementDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:singleElement id='id2' type='required' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:singleElement>";
        Document document1 = parse(xml1);
        Assertions.assertThat(createBuilder().isSingleElementDefinition(document1.getDocumentElement())).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<singleElement id='id2' type='required' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</singleElement>";
        Document document2 = parse(xml2);
        Assertions.assertThat(createBuilder().isSingleElementDefinition(document2.getDocumentElement())).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:singleElement id='id2' type='required' xmlns:ns1='http://example.com'>";
        xml3 += "</ns1:singleElement>";
        Document document3 = parse(xml3);
        Assertions.assertThat(createBuilder().isSingleElementDefinition(document3.getDocumentElement())).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:SINGLEELEMENT id='id2' type='required' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:SINGLEELEMENT>";
        Document document4 = parse(xml4);
        Assertions.assertThat(createBuilder().isSingleElementDefinition(document4.getDocumentElement())).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<singleElement id='id2' type='required'>";
        xml5 += "</singleElement>";
        Document document5 = parse(xml5);
        Assertions.assertThat(createBuilder().isSingleElementDefinition(document5.getDocumentElement())).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isFormReferenceDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:formReference id='id2' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:formReference>";
        Document document1 = parse(xml1);
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(document1.getDocumentElement())).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<formReference id='id2' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</formReference>";
        Document document2 = parse(xml2);
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(document2.getDocumentElement())).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:formReference id='id2' xmlns:ns1='http://example.com'>";
        xml3 += "</ns1:formReference>";
        Document document3 = parse(xml3);
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(document3.getDocumentElement())).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORMREFERENCE id='id2' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:FORMREFERENCE>";
        Document document4 = parse(xml4);
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(document4.getDocumentElement())).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<formReference id='id2'>";
        xml5 += "</formReference>";
        Document document5 = parse(xml5);
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(document5.getDocumentElement())).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isOtherNodeDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<otherNode>";
        xml1 += "</otherNode>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Assertions.assertThat(createBuilder().isOtherNodeDefinition((Element) document1.getDocumentElement().getFirstChild())).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://example.com'>";
        xml2 += "<ns2:otherNode>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Assertions.assertThat(createBuilder().isOtherNodeDefinition((Element) document2.getDocumentElement().getFirstChild())).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns2:otherNode xmlns:ns2='http://example.com'>";
        xml3 += "</ns2:otherNode>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Assertions.assertThat(createBuilder().isOtherNodeDefinition((Element) document3.getDocumentElement().getFirstChild())).isTrue();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<otherNode xmlns='http://example.com'>";
        xml4 += "</otherNode>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Assertions.assertThat(createBuilder().isOtherNodeDefinition((Element) document4.getDocumentElement().getFirstChild())).isTrue();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:otherNode>";
        xml5 += "</ns1:otherNode>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Assertions.assertThat(createBuilder().isOtherNodeDefinition((Element) document5.getDocumentElement().getFirstChild())).isFalse();

        String xml6 = "<?xml version='1.0'?>\n";
        xml6 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml6 += "<otherNode>";
        xml6 += "</otherNode>";
        xml6 += "</form>";
        Document document6 = parse(xml6);
        Assertions.assertThat(createBuilder().isOtherNodeDefinition((Element) document6.getDocumentElement().getFirstChild())).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionTest() {

    }

    private FormXmlDefinitionBuilderImpl createBuilder() {
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        return new FormXmlDefinitionBuilderImpl(otherNodeXmlDefinitionBuilders);
    }

}
