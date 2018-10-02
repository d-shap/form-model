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
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinitionImpl;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;

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
        Element element1 = document1.getDocumentElement();
        Assertions.assertThat(createBuilder().isFormDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element element2 = document2.getDocumentElement();
        Assertions.assertThat(createBuilder().isFormDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element element3 = document3.getDocumentElement();
        Assertions.assertThat(createBuilder().isFormDefinition(element3)).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:FORM>";
        Document document4 = parse(xml4);
        Element element4 = document4.getDocumentElement();
        Assertions.assertThat(createBuilder().isFormDefinition(element4)).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<form id='id1'>";
        xml5 += "</form>";
        Document document5 = parse(xml5);
        Element element5 = document5.getDocumentElement();
        Assertions.assertThat(createBuilder().isFormDefinition(element5)).isFalse();
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
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1).isNotNull();
        Assertions.assertThat(formDefinition1.getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinition1.getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinition1.getSource()).isEqualTo("source");
        Assertions.assertThat(formDefinition1.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element element2 = document2.getDocumentElement();
        FormDefinition formDefinition2 = createBuilder().createFormDefinition(element2, "source");
        Assertions.assertThat(formDefinition2).isNotNull();
        Assertions.assertThat(formDefinition2.getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinition2.getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinition2.getSource()).isEqualTo("source");
        Assertions.assertThat(formDefinition2.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formDefinition2.getOtherAttributeNames()).containsExactly();

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element element3 = document3.getDocumentElement();
            createBuilder().createFormDefinition(element3, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition is not valid: {http://example.com}form]");
        }

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "</ns1:FORM>";
            Document document4 = parse(xml4);
            Element element4 = document4.getDocumentElement();
            createBuilder().createFormDefinition(element4, "source4");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition is not valid: {http://d-shap.ru/schema/form-model/1.0}FORM]");
        }

        try {
            String xml5 = "<?xml version='1.0'?>\n";
            xml5 += "<form id='id1'>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            Element element5 = document5.getDocumentElement();
            createBuilder().createFormDefinition(element5, "source5");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition is not valid: form]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionAttributeGroupTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1.getGroup()).isEqualTo("");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form group='' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element element2 = document2.getDocumentElement();
        FormDefinition formDefinition2 = createBuilder().createFormDefinition(element2, "source");
        Assertions.assertThat(formDefinition2.getGroup()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form group=' ' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element element3 = document3.getDocumentElement();
        FormDefinition formDefinition3 = createBuilder().createFormDefinition(element3, "source");
        Assertions.assertThat(formDefinition3.getGroup()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element element4 = document4.getDocumentElement();
        FormDefinition formDefinition4 = createBuilder().createFormDefinition(element4, "source");
        Assertions.assertThat(formDefinition4.getGroup()).isEqualTo("group");

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form group='-group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element element5 = document5.getDocumentElement();
        FormDefinition formDefinition5 = createBuilder().createFormDefinition(element5, "source");
        Assertions.assertThat(formDefinition5.getGroup()).isEqualTo("-group");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionAttributeIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1.getId()).isEqualTo("");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element element2 = document2.getDocumentElement();
        FormDefinition formDefinition2 = createBuilder().createFormDefinition(element2, "source");
        Assertions.assertThat(formDefinition2.getId()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id=' ' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element element3 = document3.getDocumentElement();
        FormDefinition formDefinition3 = createBuilder().createFormDefinition(element3, "source");
        Assertions.assertThat(formDefinition3.getId()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element element4 = document4.getDocumentElement();
        FormDefinition formDefinition4 = createBuilder().createFormDefinition(element4, "source");
        Assertions.assertThat(formDefinition4.getId()).isEqualTo("id");

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='-id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element element5 = document5.getDocumentElement();
        FormDefinition formDefinition5 = createBuilder().createFormDefinition(element5, "source");
        Assertions.assertThat(formDefinition5.getId()).isEqualTo("-id");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionAttributeOtherTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form attr='value' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element element2 = document2.getDocumentElement();
        FormDefinition formDefinition2 = createBuilder().createFormDefinition(element2, "source");
        Assertions.assertThat(formDefinition2.getOtherAttributeNames()).containsExactly("attr");
        Assertions.assertThat(formDefinition2.getOtherAttributeValue("attr")).isEqualTo("value");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form ns2:xmlns='value' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://example.com'>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element element3 = document3.getDocumentElement();
        FormDefinition formDefinition3 = createBuilder().createFormDefinition(element3, "source");
        Assertions.assertThat(formDefinition3.getOtherAttributeNames()).containsExactly("xmlns");
        Assertions.assertThat(formDefinition3.getOtherAttributeValue("xmlns")).isEqualTo("value");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isAttributeDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
        Element element1 = (Element) parentElement1.getFirstChild();
        Assertions.assertThat(createBuilder().isAttributeDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<attribute id='id3' lookup='lookup3' type='required'>";
        xml2 += "</attribute>";
        xml2 += "</element>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
        Element element2 = (Element) parentElement2.getFirstChild();
        Assertions.assertThat(createBuilder().isAttributeDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
        xml3 += "</ns1:attribute>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
        Element element3 = (Element) parentElement3.getFirstChild();
        Assertions.assertThat(createBuilder().isAttributeDefinition(element3)).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:ELEMENT id='id2' lookup='lookup2' type='required'>";
        xml4 += "<ns1:ATTRIBUTE id='id3' lookup='lookup3' type='required'>";
        xml4 += "</ns1:ATTRIBUTE>";
        xml4 += "</ns1:ELEMENT>";
        xml4 += "</ns1:FORM>";
        Document document4 = parse(xml4);
        Element parentElement4 = (Element) document4.getDocumentElement().getFirstChild();
        Element element4 = (Element) parentElement4.getFirstChild();
        Assertions.assertThat(createBuilder().isAttributeDefinition(element4)).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<form id='id1'>";
        xml5 += "<element id='id2' lookup='lookup2' type='required'>";
        xml5 += "<attribute id='id3' lookup='lookup3' type='required'>";
        xml5 += "</attribute>";
        xml5 += "</element>";
        xml5 += "</form>";
        Document document5 = parse(xml5);
        Element parentElement5 = (Element) document5.getDocumentElement().getFirstChild();
        Element element5 = (Element) parentElement5.getFirstChild();
        Assertions.assertThat(createBuilder().isAttributeDefinition(element5)).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
        Element element1 = (Element) parentElement1.getFirstChild();
        AttributeDefinition attributeDefinition1 = createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition1).isNotNull();
        Assertions.assertThat(attributeDefinition1.getId()).isEqualTo("id3");
        Assertions.assertThat(attributeDefinition1.getLookup()).isEqualTo("lookup3");
        Assertions.assertThat(attributeDefinition1.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(attributeDefinition1.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(attributeDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<attribute id='id3' lookup='lookup3' type='required'>";
        xml2 += "</attribute>";
        xml2 += "</element>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
        Element element2 = (Element) parentElement2.getFirstChild();
        AttributeDefinition attributeDefinition2 = createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition2).isNotNull();
        Assertions.assertThat(attributeDefinition2.getId()).isEqualTo("id3");
        Assertions.assertThat(attributeDefinition2.getLookup()).isEqualTo("lookup3");
        Assertions.assertThat(attributeDefinition2.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(attributeDefinition2.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(attributeDefinition2.getOtherAttributeNames()).containsExactly();

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml3 += "</ns1:attribute>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Attribute definition is not valid: {http://example.com}attribute], parent");
        }

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "<ns1:ELEMENT id='id2' lookup='lookup2' type='required'>";
            xml4 += "<ns1:ATTRIBUTE id='id3' lookup='lookup3' type='required'>";
            xml4 += "</ns1:ATTRIBUTE>";
            xml4 += "</ns1:ELEMENT>";
            xml4 += "</ns1:FORM>";
            Document document4 = parse(xml4);
            Element parentElement4 = (Element) document4.getDocumentElement().getFirstChild();
            Element element4 = (Element) parentElement4.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement4, element4, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Attribute definition is not valid: {http://d-shap.ru/schema/form-model/1.0}ATTRIBUTE], parent");
        }

        try {
            String xml5 = "<?xml version='1.0'?>\n";
            xml5 += "<form id='id1'>";
            xml5 += "<element id='id2' lookup='lookup2' type='required'>";
            xml5 += "<attribute id='id3' lookup='lookup3' type='required'>";
            xml5 += "</attribute>";
            xml5 += "</element>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            Element parentElement5 = (Element) document5.getDocumentElement().getFirstChild();
            Element element5 = (Element) parentElement5.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement5, element5, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Attribute definition is not valid: attribute], parent");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isElementDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        Assertions.assertThat(createBuilder().isElementDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<element id='id2' lookup='lookup2' type='required'>";
        xml2 += "</element>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        Assertions.assertThat(createBuilder().isElementDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        Assertions.assertThat(createBuilder().isElementDefinition(element3)).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:ELEMENT id='id2' lookup='lookup2' type='required'>";
        xml4 += "</ns1:ELEMENT>";
        xml4 += "</ns1:FORM>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        Assertions.assertThat(createBuilder().isElementDefinition(element4)).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<form id='id1'>";
        xml5 += "<element id='id2' lookup='lookup2' type='required'>";
        xml5 += "</element>";
        xml5 += "</form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        Assertions.assertThat(createBuilder().isElementDefinition(element5)).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1).isNotNull();
        Assertions.assertThat(elementDefinition1.getId()).isEqualTo("id2");
        Assertions.assertThat(elementDefinition1.getLookup()).isEqualTo("lookup2");
        Assertions.assertThat(elementDefinition1.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(elementDefinition1.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(elementDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<element id='id2' lookup='lookup2' type='required'>";
        xml2 += "</element>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        ElementDefinition elementDefinition2 = createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(elementDefinition2).isNotNull();
        Assertions.assertThat(elementDefinition2.getId()).isEqualTo("id2");
        Assertions.assertThat(elementDefinition2.getLookup()).isEqualTo("lookup2");
        Assertions.assertThat(elementDefinition2.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(elementDefinition2.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(elementDefinition2.getOtherAttributeNames()).containsExactly();

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Element definition is not valid: {http://example.com}element], parent");
        }

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "<ns1:ELEMENT id='id2' lookup='lookup2' type='required'>";
            xml4 += "</ns1:ELEMENT>";
            xml4 += "</ns1:FORM>";
            Document document4 = parse(xml4);
            Element parentElement4 = document4.getDocumentElement();
            Element element4 = (Element) parentElement4.getFirstChild();
            createBuilder().createElementDefinition(parentElement4, element4, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Element definition is not valid: {http://d-shap.ru/schema/form-model/1.0}ELEMENT], parent");
        }

        try {
            String xml5 = "<?xml version='1.0'?>\n";
            xml5 += "<form id='id1'>";
            xml5 += "<element id='id2' lookup='lookup2' type='required'>";
            xml5 += "</element>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            Element parentElement5 = document5.getDocumentElement();
            Element element5 = (Element) parentElement5.getFirstChild();
            createBuilder().createElementDefinition(parentElement5, element5, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Element definition is not valid: element], parent");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isSingleElementDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:singleElement id='id2' type='required'>";
        xml1 += "</ns1:singleElement>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        Assertions.assertThat(createBuilder().isSingleElementDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<singleElement id='id2' type='required'>";
        xml2 += "</singleElement>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        Assertions.assertThat(createBuilder().isSingleElementDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "<ns1:singleElement id='id2' type='required'>";
        xml3 += "</ns1:singleElement>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        Assertions.assertThat(createBuilder().isSingleElementDefinition(element3)).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:SINGLEELEMENT id='id2' type='required'>";
        xml4 += "</ns1:SINGLEELEMENT>";
        xml4 += "</ns1:FORM>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        Assertions.assertThat(createBuilder().isSingleElementDefinition(element4)).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<form id='id1'>";
        xml5 += "<singleElement id='id2' type='required'>";
        xml5 += "</singleElement>";
        xml5 += "</form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        Assertions.assertThat(createBuilder().isSingleElementDefinition(element5)).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:singleElement id='id2' type='required'>";
        xml1 += "</ns1:singleElement>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        SingleElementDefinition singleElementDefinition1 = createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition1).isNotNull();
        Assertions.assertThat(singleElementDefinition1.getId()).isEqualTo("id2");
        Assertions.assertThat(singleElementDefinition1.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(singleElementDefinition1.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(singleElementDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<singleElement id='id2' type='required'>";
        xml2 += "</singleElement>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        SingleElementDefinition singleElementDefinition2 = createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition2).isNotNull();
        Assertions.assertThat(singleElementDefinition2.getId()).isEqualTo("id2");
        Assertions.assertThat(singleElementDefinition2.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(singleElementDefinition2.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(singleElementDefinition2.getOtherAttributeNames()).containsExactly();

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
            xml3 += "<ns1:singleElement id='id2' type='required'>";
            xml3 += "</ns1:singleElement>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Single element definition is not valid: {http://example.com}singleElement], parent");
        }

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "<ns1:SINGLEELEMENT id='id2' type='required'>";
            xml4 += "</ns1:SINGLEELEMENT>";
            xml4 += "</ns1:FORM>";
            Document document4 = parse(xml4);
            Element parentElement4 = document4.getDocumentElement();
            Element element4 = (Element) parentElement4.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement4, element4, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Single element definition is not valid: {http://d-shap.ru/schema/form-model/1.0}SINGLEELEMENT], parent");
        }

        try {
            String xml5 = "<?xml version='1.0'?>\n";
            xml5 += "<form id='id1'>";
            xml5 += "<singleElement id='id2' type='required'>";
            xml5 += "</singleElement>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            Element parentElement5 = document5.getDocumentElement();
            Element element5 = (Element) parentElement5.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement5, element5, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Single element definition is not valid: singleElement], parent");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isFormReferenceDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:formReference id='id2'>";
        xml1 += "</ns1:formReference>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<formReference id='id2'>";
        xml2 += "</formReference>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "<ns1:formReference id='id2'>";
        xml3 += "</ns1:formReference>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(element3)).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:FORMREFERENCE id='id2'>";
        xml4 += "</ns1:FORMREFERENCE>";
        xml4 += "</ns1:FORM>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(element4)).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<form id='id1'>";
        xml5 += "<formReference id='id2'>";
        xml5 += "</formReference>";
        xml5 += "</form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(element5)).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:formReference id='id2'>";
        xml1 += "</ns1:formReference>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        FormReferenceDefinition formReferenceDefinition1 = createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition1).isNotNull();
        Assertions.assertThat(formReferenceDefinition1.getGroup()).isEqualTo("");
        Assertions.assertThat(formReferenceDefinition1.getId()).isEqualTo("id2");
        Assertions.assertThat(formReferenceDefinition1.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formReferenceDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<formReference id='id2'>";
        xml2 += "</formReference>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        FormReferenceDefinition formReferenceDefinition2 = createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition2).isNotNull();
        Assertions.assertThat(formReferenceDefinition2.getGroup()).isEqualTo("");
        Assertions.assertThat(formReferenceDefinition2.getId()).isEqualTo("id2");
        Assertions.assertThat(formReferenceDefinition2.getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formReferenceDefinition2.getOtherAttributeNames()).containsExactly();

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
            xml3 += "<ns1:formReference id='id2'>";
            xml3 += "</ns1:formReference>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference definition is not valid: {http://example.com}formReference], parent");
        }

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:FORM id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "<ns1:FORMREFERENCE id='id2'>";
            xml4 += "</ns1:FORMREFERENCE>";
            xml4 += "</ns1:FORM>";
            Document document4 = parse(xml4);
            Element parentElement4 = document4.getDocumentElement();
            Element element4 = (Element) parentElement4.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement4, element4, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference definition is not valid: {http://d-shap.ru/schema/form-model/1.0}FORMREFERENCE], parent");
        }

        try {
            String xml5 = "<?xml version='1.0'?>\n";
            xml5 += "<form id='id1'>";
            xml5 += "<formReference id='id2'>";
            xml5 += "</formReference>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            Element parentElement5 = document5.getDocumentElement();
            Element element5 = (Element) parentElement5.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement5, element5, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference definition is not valid: formReference], parent");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isOtherNodeDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml1 += "<ns2:otherNode>";
        xml1 += "</ns2:otherNode>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<otherNode>";
        xml2 += "</otherNode>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml3 += "<ns2:someNode>";
        xml3 += "</ns2:someNode>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element3)).isTrue();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns2:otherNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml4 += "</ns2:otherNode>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element4)).isTrue();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<otherNode xmlns='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml5 += "</otherNode>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element5)).isTrue();

        String xml6 = "<?xml version='1.0'?>\n";
        xml6 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml6 += "<ns1:otherNode>";
        xml6 += "</ns1:otherNode>";
        xml6 += "</ns1:form>";
        Document document6 = parse(xml6);
        Element parentElement6 = document6.getDocumentElement();
        Element element6 = (Element) parentElement6.getFirstChild();
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element6)).isFalse();

        String xml7 = "<?xml version='1.0'?>\n";
        xml7 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml7 += "<otherNode>";
        xml7 += "</otherNode>";
        xml7 += "</form>";
        Document document7 = parse(xml7);
        Element parentElement7 = document7.getDocumentElement();
        Element element7 = (Element) parentElement7.getFirstChild();
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element7)).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml1 += "<ns2:otherNode>";
        xml1 += "</ns2:otherNode>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        OtherNodeDefinition otherNodeDefinition1 = createBuilder().createOtherNodeDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition1).isNotNull();
        Assertions.assertThat(otherNodeDefinition1).isInstanceOf(OtherNodeDefinitionImpl.class);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<otherNode>";
        xml2 += "</otherNode>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        OtherNodeDefinition otherNodeDefinition2 = createBuilder().createOtherNodeDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition2).isNotNull();
        Assertions.assertThat(otherNodeDefinition2).isInstanceOf(DefaultOtherNodeXmlDefinition.class);

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml3 += "<ns2:someNode>";
        xml3 += "</ns2:someNode>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        OtherNodeDefinition otherNodeDefinition3 = createBuilder().createOtherNodeDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition3).isNotNull();
        Assertions.assertThat(otherNodeDefinition3).isInstanceOf(DefaultOtherNodeXmlDefinition.class);

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns2:otherNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml4 += "</ns2:otherNode>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        OtherNodeDefinition otherNodeDefinition4 = createBuilder().createOtherNodeDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition4).isNotNull();
        Assertions.assertThat(otherNodeDefinition4).isInstanceOf(OtherNodeDefinitionImpl.class);

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<otherNode xmlns='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml5 += "</otherNode>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        OtherNodeDefinition otherNodeDefinition5 = createBuilder().createOtherNodeDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition5).isNotNull();
        Assertions.assertThat(otherNodeDefinition5).isInstanceOf(OtherNodeDefinitionImpl.class);

        try {
            String xml6 = "<?xml version='1.0'?>\n";
            xml6 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml6 += "<ns1:otherNode>";
            xml6 += "</ns1:otherNode>";
            xml6 += "</ns1:form>";
            Document document6 = parse(xml6);
            Element parentElement6 = document6.getDocumentElement();
            Element element6 = (Element) parentElement6.getFirstChild();
            createBuilder().createOtherNodeDefinition(parentElement6, element6, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: {http://d-shap.ru/schema/form-model/1.0}otherNode], parent");
        }

        try {
            String xml7 = "<?xml version='1.0'?>\n";
            xml7 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
            xml7 += "<otherNode>";
            xml7 += "</otherNode>";
            xml7 += "</form>";
            Document document7 = parse(xml7);
            Element parentElement7 = document7.getDocumentElement();
            Element element7 = (Element) parentElement7.getFirstChild();
            createBuilder().createOtherNodeDefinition(parentElement7, element7, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: {http://d-shap.ru/schema/form-model/1.0}otherNode], parent");
        }
    }

    private FormXmlDefinitionBuilderImpl createBuilder() {
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        return new FormXmlDefinitionBuilderImpl(otherNodeXmlDefinitionBuilders);
    }

}
