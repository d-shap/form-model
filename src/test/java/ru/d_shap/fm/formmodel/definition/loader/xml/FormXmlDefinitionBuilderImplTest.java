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
package ru.d_shap.fm.formmodel.definition.loader.xml;

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.fm.formmodel.BaseFormModelTest;
import ru.d_shap.fm.formmodel.ServiceFinder;
import ru.d_shap.fm.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.fm.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.fm.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.fm.formmodel.definition.model.ElementDefinition;
import ru.d_shap.fm.formmodel.definition.model.FormDefinition;
import ru.d_shap.fm.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.fm.formmodel.definition.model.NodePath;
import ru.d_shap.fm.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.fm.formmodel.definition.model.OtherNodeDefinitionImpl;
import ru.d_shap.fm.formmodel.definition.model.SingleElementDefinition;

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
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' attr='value' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element element2 = document2.getDocumentElement();
        FormDefinition formDefinition2 = createBuilder().createFormDefinition(element2, "source");
        Assertions.assertThat(formDefinition2.getOtherAttributeNames()).containsExactly("attr");
        Assertions.assertThat(formDefinition2.getOtherAttributeValue("attr")).isEqualTo("value");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' ns2:xmlns='value' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://example.com'>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element element3 = document3.getDocumentElement();
        FormDefinition formDefinition3 = createBuilder().createFormDefinition(element3, "source");
        Assertions.assertThat(formDefinition3.getOtherAttributeNames()).containsExactly("{http://example.com}xmlns");
        Assertions.assertThat(formDefinition3.getOtherAttributeValue("{http://example.com}xmlns")).isEqualTo("value");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionChildAttributeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:attribute id='id2' lookup='lookup2' type='required'>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element element1 = document1.getDocumentElement();
            createBuilder().createFormDefinition(element1, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attribute], {source}form[@:id1]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<attribute id='id2' lookup='lookup2' type='required'>";
            xml2 += "</attribute>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element element2 = document2.getDocumentElement();
            createBuilder().createFormDefinition(element2, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: attribute], {source}form[@:id1]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:attributeS id='id2' lookup='lookup2' type='required'>";
            xml3 += "</ns1:attributeS>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element element3 = document3.getDocumentElement();
            createBuilder().createFormDefinition(element3, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attributeS], {source}form[@:id1]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionChildElementTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinition1.getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinition1.getElementDefinitions().get(0).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinition1.getElementDefinitions().get(0).getLookup()).isEqualTo("lookup2");
        Assertions.assertThat(formDefinition1.getElementDefinitions().get(0).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(formDefinition1.getElementDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formDefinition1.getElementDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<element id='id2' lookup='lookup2' type='required'>";
            xml2 += "</element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element element2 = document2.getDocumentElement();
            createBuilder().createFormDefinition(element2, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: element], {source}form[@:id1]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:elementS id='id2' lookup='lookup2' type='required'>";
            xml3 += "</ns1:elementS>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element element3 = document3.getDocumentElement();
            createBuilder().createFormDefinition(element3, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}elementS], {source}form[@:id1]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionChildSingleElementTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:single-element id='id2' type='required'>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinition1.getSingleElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinition1.getSingleElementDefinitions().get(0).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinition1.getSingleElementDefinitions().get(0).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(formDefinition1.getSingleElementDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formDefinition1.getSingleElementDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<single-element id='id2' type='required'>";
            xml2 += "</single-element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element element2 = document2.getDocumentElement();
            createBuilder().createFormDefinition(element2, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: single-element], {source}form[@:id1]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:single-elementS id='id2' type='required'>";
            xml3 += "</ns1:single-elementS>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element element3 = document3.getDocumentElement();
            createBuilder().createFormDefinition(element3, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}single-elementS], {source}form[@:id1]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionChildFormReferenceTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:form-reference id='id2'>";
        xml1 += "</ns1:form-reference>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element element1 = document1.getDocumentElement();
        FormDefinition formDefinition1 = createBuilder().createFormDefinition(element1, "source");
        Assertions.assertThat(formDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinition1.getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinition1.getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinition1.getFormReferenceDefinitions().get(0).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinition1.getFormReferenceDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(formDefinition1.getFormReferenceDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<form-reference id='id2'>";
            xml2 += "</form-reference>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element element2 = document2.getDocumentElement();
            createBuilder().createFormDefinition(element2, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: form-reference], {source}form[@:id1]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:form-referenceS id='id2'>";
            xml3 += "</ns1:form-referenceS>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element element3 = document3.getDocumentElement();
            createBuilder().createFormDefinition(element3, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-referenceS], {source}form[@:id1]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionChildOtherNodeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<otherNode>";
            xml1 += "</otherNode>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element element1 = document1.getDocumentElement();
            createBuilder().createFormDefinition(element1, "source");
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: otherNode], {source}form[@:id1]");
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<!-- COMMENT -->";
        xml2 += "<ns2:childNode>";
        xml2 += "</ns2:childNode>";
        xml2 += "<ns2:otherNode>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element element2 = document2.getDocumentElement();
        FormDefinition formDefinition2 = createBuilder().createFormDefinition(element2, "source");
        Assertions.assertThat(formDefinition2.getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinition2.getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinition2.getOtherNodeDefinitions().get(0)).isInstanceOf(DefaultOtherNodeXmlDefinition.class);
        Assertions.assertThat(formDefinition2.getOtherNodeDefinitions().get(1)).isInstanceOf(OtherNodeDefinitionImpl.class);
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
    public void createAttributeDefinitionAttributeIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<ns1:attribute lookup='lookup3' type='required'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
        Element element1 = (Element) parentElement1.getFirstChild();
        AttributeDefinition attributeDefinition1 = createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition1.getId()).isEqualTo("");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<ns1:attribute id='' lookup='lookup3' type='required'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
        Element element2 = (Element) parentElement2.getFirstChild();
        AttributeDefinition attributeDefinition2 = createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition2.getId()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml3 += "<ns1:attribute id=' ' lookup='lookup3' type='required'>";
        xml3 += "</ns1:attribute>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
        Element element3 = (Element) parentElement3.getFirstChild();
        AttributeDefinition attributeDefinition3 = createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition3.getId()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml4 += "<ns1:attribute id='id' lookup='lookup3' type='required'>";
        xml4 += "</ns1:attribute>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = (Element) document4.getDocumentElement().getFirstChild();
        Element element4 = (Element) parentElement4.getFirstChild();
        AttributeDefinition attributeDefinition4 = createBuilder().createAttributeDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition4.getId()).isEqualTo("id");

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml5 += "<ns1:attribute id='-id' lookup='lookup3' type='required'>";
        xml5 += "</ns1:attribute>";
        xml5 += "</ns1:element>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = (Element) document5.getDocumentElement().getFirstChild();
        Element element5 = (Element) parentElement5.getFirstChild();
        AttributeDefinition attributeDefinition5 = createBuilder().createAttributeDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition5.getId()).isEqualTo("-id");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionAttributeLookupTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<ns1:attribute id='id3' type='required'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
        Element element1 = (Element) parentElement1.getFirstChild();
        AttributeDefinition attributeDefinition1 = createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition1.getLookup()).isNull();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<ns1:attribute id='id3' lookup='' type='required'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
        Element element2 = (Element) parentElement2.getFirstChild();
        AttributeDefinition attributeDefinition2 = createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition2.getLookup()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml3 += "<ns1:attribute id='id3' lookup=' ' type='required'>";
        xml3 += "</ns1:attribute>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
        Element element3 = (Element) parentElement3.getFirstChild();
        AttributeDefinition attributeDefinition3 = createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition3.getLookup()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml4 += "<ns1:attribute id='id3' lookup='lookup' type='required'>";
        xml4 += "</ns1:attribute>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = (Element) document4.getDocumentElement().getFirstChild();
        Element element4 = (Element) parentElement4.getFirstChild();
        AttributeDefinition attributeDefinition4 = createBuilder().createAttributeDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition4.getLookup()).isEqualTo("lookup");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionAttributeTypeTest() {
        String xml11 = "<?xml version='1.0'?>\n";
        xml11 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml11 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml11 += "<ns1:attribute id='id3' lookup='lookup3'>";
        xml11 += "</ns1:attribute>";
        xml11 += "</ns1:element>";
        xml11 += "</ns1:form>";
        Document document11 = parse(xml11);
        Element parentElement11 = (Element) document11.getDocumentElement().getFirstChild();
        Element element11 = (Element) parentElement11.getFirstChild();
        AttributeDefinition attributeDefinition11 = createBuilder().createAttributeDefinition(parentElement11, element11, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition11.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml12 = "<?xml version='1.0'?>\n";
        xml12 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml12 += "<otherNode>";
        xml12 += "<ns1:attribute id='id3' lookup='lookup3'>";
        xml12 += "</ns1:attribute>";
        xml12 += "</otherNode>";
        xml12 += "</ns1:form>";
        Document document12 = parse(xml12);
        Element parentElement12 = (Element) document12.getDocumentElement().getFirstChild();
        Element element12 = (Element) parentElement12.getFirstChild();
        AttributeDefinition attributeDefinition12 = createBuilder().createAttributeDefinition(parentElement12, element12, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition12.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml13 = "<?xml version='1.0'?>\n";
        xml13 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml13 += "<ns2:otherNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml13 += "<ns1:attribute id='id3' lookup='lookup3'>";
        xml13 += "</ns1:attribute>";
        xml13 += "</ns2:otherNode>";
        xml13 += "</ns1:form>";
        Document document13 = parse(xml13);
        Element parentElement13 = (Element) document13.getDocumentElement().getFirstChild();
        Element element13 = (Element) parentElement13.getFirstChild();
        AttributeDefinition attributeDefinition13 = createBuilder().createAttributeDefinition(parentElement13, element13, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition13.getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);

        String xml14 = "<?xml version='1.0'?>\n";
        xml14 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml14 += "<ns2:childNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml14 += "<ns1:attribute id='id3' lookup='lookup3'>";
        xml14 += "</ns1:attribute>";
        xml14 += "</ns2:childNode>";
        xml14 += "</ns1:form>";
        Document document14 = parse(xml14);
        Element parentElement14 = (Element) document14.getDocumentElement().getFirstChild();
        Element element14 = (Element) parentElement14.getFirstChild();
        AttributeDefinition attributeDefinition14 = createBuilder().createAttributeDefinition(parentElement14, element14, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition14.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        try {
            OtherNodeXmlDefinitionBuilderImpl.setReturnNotNullCardinality();
            String xml15 = "<?xml version='1.0'?>\n";
            xml15 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml15 += "<ns2:childNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
            xml15 += "<ns1:attribute id='id3' lookup='lookup3'>";
            xml15 += "</ns1:attribute>";
            xml15 += "</ns2:childNode>";
            xml15 += "</ns1:form>";
            Document document15 = parse(xml15);
            Element parentElement15 = (Element) document15.getDocumentElement().getFirstChild();
            Element element15 = (Element) parentElement15.getFirstChild();
            AttributeDefinition attributeDefinition15 = createBuilder().createAttributeDefinition(parentElement15, element15, new NodePath("parent"));
            Assertions.assertThat(attributeDefinition15.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);
        } finally {
            OtherNodeXmlDefinitionBuilderImpl.clearReturnNotNullCardinality();
        }

        try {
            OtherNodeXmlDefinitionBuilderImpl.setReturnNotNullCardinality();
            String xml16 = "<?xml version='1.0'?>\n";
            xml16 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml16 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml16 += "<ns1:attribute id='id3' lookup='lookup3'>";
            xml16 += "</ns1:attribute>";
            xml16 += "</ns1:element>";
            xml16 += "</ns1:form>";
            Document document16 = parse(xml16);
            Element parentElement16 = (Element) document16.getDocumentElement().getFirstChild();
            Element element16 = (Element) parentElement16.getFirstChild();
            AttributeDefinition attributeDefinition16 = createBuilder().createAttributeDefinition(parentElement16, element16, new NodePath("parent"));
            Assertions.assertThat(attributeDefinition16.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        } finally {
            OtherNodeXmlDefinitionBuilderImpl.clearReturnNotNullCardinality();
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<ns1:attribute id='id3' lookup='lookup3' type=''>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
        Element element2 = (Element) parentElement2.getFirstChild();
        AttributeDefinition attributeDefinition2 = createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition2.getCardinalityDefinition()).isNull();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml3 += "<ns1:attribute id='id3' lookup='lookup3' type=' '>";
        xml3 += "</ns1:attribute>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
        Element element3 = (Element) parentElement3.getFirstChild();
        AttributeDefinition attributeDefinition3 = createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition3.getCardinalityDefinition()).isNull();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml4 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
        xml4 += "</ns1:attribute>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = (Element) document4.getDocumentElement().getFirstChild();
        Element element4 = (Element) parentElement4.getFirstChild();
        AttributeDefinition attributeDefinition4 = createBuilder().createAttributeDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition4.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml5 += "<ns1:attribute id='id3' lookup='lookup3' type='required+'>";
        xml5 += "</ns1:attribute>";
        xml5 += "</ns1:element>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = (Element) document5.getDocumentElement().getFirstChild();
        Element element5 = (Element) parentElement5.getFirstChild();
        AttributeDefinition attributeDefinition5 = createBuilder().createAttributeDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition5.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);

        String xml6 = "<?xml version='1.0'?>\n";
        xml6 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml6 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml6 += "<ns1:attribute id='id3' lookup='lookup3' type='optional'>";
        xml6 += "</ns1:attribute>";
        xml6 += "</ns1:element>";
        xml6 += "</ns1:form>";
        Document document6 = parse(xml6);
        Element parentElement6 = (Element) document6.getDocumentElement().getFirstChild();
        Element element6 = (Element) parentElement6.getFirstChild();
        AttributeDefinition attributeDefinition6 = createBuilder().createAttributeDefinition(parentElement6, element6, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition6.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);

        String xml7 = "<?xml version='1.0'?>\n";
        xml7 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml7 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml7 += "<ns1:attribute id='id3' lookup='lookup3' type='optional+'>";
        xml7 += "</ns1:attribute>";
        xml7 += "</ns1:element>";
        xml7 += "</ns1:form>";
        Document document7 = parse(xml7);
        Element parentElement7 = (Element) document7.getDocumentElement().getFirstChild();
        Element element7 = (Element) parentElement7.getFirstChild();
        AttributeDefinition attributeDefinition7 = createBuilder().createAttributeDefinition(parentElement7, element7, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition7.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL_MULTIPLE);

        String xml8 = "<?xml version='1.0'?>\n";
        xml8 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml8 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml8 += "<ns1:attribute id='id3' lookup='lookup3' type='prohibited'>";
        xml8 += "</ns1:attribute>";
        xml8 += "</ns1:element>";
        xml8 += "</ns1:form>";
        Document document8 = parse(xml8);
        Element parentElement8 = (Element) document8.getDocumentElement().getFirstChild();
        Element element8 = (Element) parentElement8.getFirstChild();
        AttributeDefinition attributeDefinition8 = createBuilder().createAttributeDefinition(parentElement8, element8, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition8.getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionAttributeOtherTest() {
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
        Assertions.assertThat(attributeDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<ns1:attribute id='id3' lookup='lookup3' type='required' attr='value'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
        Element element2 = (Element) parentElement2.getFirstChild();
        AttributeDefinition attributeDefinition2 = createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition2.getOtherAttributeNames()).containsExactly("attr");
        Assertions.assertThat(attributeDefinition2.getOtherAttributeValue("attr")).isEqualTo("value");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://example.com'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required' ns2:xmlns='value'>";
        xml3 += "</ns1:attribute>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
        Element element3 = (Element) parentElement3.getFirstChild();
        AttributeDefinition attributeDefinition3 = createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition3.getOtherAttributeNames()).containsExactly("{http://example.com}xmlns");
        Assertions.assertThat(attributeDefinition3.getOtherAttributeValue("{http://example.com}xmlns")).isEqualTo("value");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionChildAttributeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:attribute id='id4' lookup='lookup4' type='required'>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attribute], parent/attribute[@id3]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<attribute id='id4' lookup='lookup4' type='required'>";
            xml2 += "</attribute>";
            xml2 += "</ns1:attribute>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: attribute], parent/attribute[@id3]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:attributeS id='id4' lookup='lookup4' type='required'>";
            xml3 += "</ns1:attributeS>";
            xml3 += "</ns1:attribute>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attributeS], parent/attribute[@id3]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionChildElementTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:element id='id4' lookup='lookup4' type='required'>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}element], parent/attribute[@id3]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<element id='id4' lookup='lookup4' type='required'>";
            xml2 += "</element>";
            xml2 += "</ns1:attribute>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: element], parent/attribute[@id3]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:elementS id='id4' lookup='lookup4' type='required'>";
            xml3 += "</ns1:elementS>";
            xml3 += "</ns1:attribute>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}elementS], parent/attribute[@id3]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionChildSingleElementTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:single-element id='id4' type='required'>";
            xml1 += "</ns1:single-element>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}single-element], parent/attribute[@id3]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<single-element id='id4' type='required'>";
            xml2 += "</single-element>";
            xml2 += "</ns1:attribute>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: single-element], parent/attribute[@id3]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:single-elementS id='id4' type='required'>";
            xml3 += "</ns1:single-elementS>";
            xml3 += "</ns1:attribute>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}single-elementS], parent/attribute[@id3]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionChildFormReferenceTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:form-reference id='id4'>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-reference], parent/attribute[@id3]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<form-reference id='id4'>";
            xml2 += "</form-reference>";
            xml2 += "</ns1:attribute>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: form-reference], parent/attribute[@id3]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:form-referenceS id='id4'>";
            xml3 += "</ns1:form-referenceS>";
            xml3 += "</ns1:attribute>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = (Element) document3.getDocumentElement().getFirstChild();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-referenceS], parent/attribute[@id3]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionChildOtherNodeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<otherNode>";
            xml1 += "</otherNode>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = (Element) document1.getDocumentElement().getFirstChild();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createAttributeDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: otherNode], parent/attribute[@id3]");
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
        xml2 += "<!-- COMMENT -->";
        xml2 += "<ns2:childNode>";
        xml2 += "</ns2:childNode>";
        xml2 += "<ns2:otherNode>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = (Element) document2.getDocumentElement().getFirstChild();
        Element element2 = (Element) parentElement2.getFirstChild();
        AttributeDefinition attributeDefinition2 = createBuilder().createAttributeDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(attributeDefinition2.getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(attributeDefinition2.getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(attributeDefinition2.getOtherNodeDefinitions().get(0)).isInstanceOf(DefaultOtherNodeXmlDefinition.class);
        Assertions.assertThat(attributeDefinition2.getOtherNodeDefinitions().get(1)).isInstanceOf(OtherNodeDefinitionImpl.class);
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
    public void createElementDefinitionAttributeIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element lookup='lookup2' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1.getId()).isEqualTo("");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='' lookup='lookup2' type='required'>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        ElementDefinition elementDefinition2 = createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(elementDefinition2.getId()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id=' ' lookup='lookup2' type='required'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        ElementDefinition elementDefinition3 = createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(elementDefinition3.getId()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id' lookup='lookup2' type='required'>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        ElementDefinition elementDefinition4 = createBuilder().createElementDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(elementDefinition4.getId()).isEqualTo("id");

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:element id='-id' lookup='lookup2' type='required'>";
        xml5 += "</ns1:element>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        ElementDefinition elementDefinition5 = createBuilder().createElementDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(elementDefinition5.getId()).isEqualTo("-id");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionAttributeLookupTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1.getLookup()).isNull();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='' type='required'>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        ElementDefinition elementDefinition2 = createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(elementDefinition2.getLookup()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id2' lookup=' ' type='required'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        ElementDefinition elementDefinition3 = createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(elementDefinition3.getLookup()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id2' lookup='lookup' type='required'>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        ElementDefinition elementDefinition4 = createBuilder().createElementDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(elementDefinition4.getLookup()).isEqualTo("lookup");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionAttributeTypeTest() {
        String xml11 = "<?xml version='1.0'?>\n";
        xml11 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml11 += "<ns1:element id='id2' lookup='lookup2'>";
        xml11 += "</ns1:element>";
        xml11 += "</ns1:form>";
        Document document11 = parse(xml11);
        Element parentElement11 = document11.getDocumentElement();
        Element element11 = (Element) parentElement11.getFirstChild();
        ElementDefinition elementDefinition11 = createBuilder().createElementDefinition(parentElement11, element11, new NodePath("parent"));
        Assertions.assertThat(elementDefinition11.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml12 = "<?xml version='1.0'?>\n";
        xml12 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml12 += "<ns1:single-element id='id2' type='required'>";
        xml12 += "<ns1:element id='id3' lookup='lookup3'>";
        xml12 += "</ns1:element>";
        xml12 += "</ns1:single-element>";
        xml12 += "</ns1:form>";
        Document document12 = parse(xml12);
        Element parentElement12 = (Element) document12.getDocumentElement().getFirstChild();
        Element element12 = (Element) parentElement12.getFirstChild();
        ElementDefinition elementDefinition12 = createBuilder().createElementDefinition(parentElement12, element12, new NodePath("parent"));
        Assertions.assertThat(elementDefinition12.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);

        String xml13 = "<?xml version='1.0'?>\n";
        xml13 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml13 += "<otherNode>";
        xml13 += "<ns1:element id='id3' lookup='lookup3'>";
        xml13 += "</ns1:element>";
        xml13 += "</otherNode>";
        xml13 += "</ns1:form>";
        Document document13 = parse(xml13);
        Element parentElement13 = (Element) document13.getDocumentElement().getFirstChild();
        Element element13 = (Element) parentElement13.getFirstChild();
        ElementDefinition elementDefinition13 = createBuilder().createElementDefinition(parentElement13, element13, new NodePath("parent"));
        Assertions.assertThat(elementDefinition13.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml14 = "<?xml version='1.0'?>\n";
        xml14 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml14 += "<ns2:otherNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml14 += "<ns1:element id='id3' lookup='lookup3'>";
        xml14 += "</ns1:element>";
        xml14 += "</ns2:otherNode>";
        xml14 += "</ns1:form>";
        Document document14 = parse(xml14);
        Element parentElement14 = (Element) document14.getDocumentElement().getFirstChild();
        Element element14 = (Element) parentElement14.getFirstChild();
        ElementDefinition elementDefinition14 = createBuilder().createElementDefinition(parentElement14, element14, new NodePath("parent"));
        Assertions.assertThat(elementDefinition14.getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);

        String xml15 = "<?xml version='1.0'?>\n";
        xml15 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml15 += "<ns2:childNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml15 += "<ns1:element id='id3' lookup='lookup3'>";
        xml15 += "</ns1:element>";
        xml15 += "</ns2:childNode>";
        xml15 += "</ns1:form>";
        Document document15 = parse(xml15);
        Element parentElement15 = (Element) document15.getDocumentElement().getFirstChild();
        Element element15 = (Element) parentElement15.getFirstChild();
        ElementDefinition elementDefinition15 = createBuilder().createElementDefinition(parentElement15, element15, new NodePath("parent"));
        Assertions.assertThat(elementDefinition15.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        try {
            OtherNodeXmlDefinitionBuilderImpl.setReturnNotNullCardinality();
            String xml16 = "<?xml version='1.0'?>\n";
            xml16 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml16 += "<ns2:childNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
            xml16 += "<ns1:element id='id3' lookup='lookup3'>";
            xml16 += "</ns1:element>";
            xml16 += "</ns2:childNode>";
            xml16 += "</ns1:form>";
            Document document16 = parse(xml16);
            Element parentElement16 = (Element) document16.getDocumentElement().getFirstChild();
            Element element16 = (Element) parentElement16.getFirstChild();
            ElementDefinition elementDefinition16 = createBuilder().createElementDefinition(parentElement16, element16, new NodePath("parent"));
            Assertions.assertThat(elementDefinition16.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);
        } finally {
            OtherNodeXmlDefinitionBuilderImpl.clearReturnNotNullCardinality();
        }

        try {
            OtherNodeXmlDefinitionBuilderImpl.setReturnNotNullCardinality();
            String xml17 = "<?xml version='1.0'?>\n";
            xml17 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml17 += "<ns1:element id='id2' lookup='lookup2'>";
            xml17 += "</ns1:element>";
            xml17 += "</ns1:form>";
            Document document17 = parse(xml17);
            Element parentElement17 = document17.getDocumentElement();
            Element element17 = (Element) parentElement17.getFirstChild();
            ElementDefinition elementDefinition17 = createBuilder().createElementDefinition(parentElement17, element17, new NodePath("parent"));
            Assertions.assertThat(elementDefinition17.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        } finally {
            OtherNodeXmlDefinitionBuilderImpl.clearReturnNotNullCardinality();
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type=''>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        ElementDefinition elementDefinition2 = createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(elementDefinition2.getCardinalityDefinition()).isNull();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type=' '>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        ElementDefinition elementDefinition3 = createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(elementDefinition3.getCardinalityDefinition()).isNull();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        ElementDefinition elementDefinition4 = createBuilder().createElementDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(elementDefinition4.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:element id='id2' lookup='lookup2' type='required+'>";
        xml5 += "</ns1:element>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        ElementDefinition elementDefinition5 = createBuilder().createElementDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(elementDefinition5.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);

        String xml6 = "<?xml version='1.0'?>\n";
        xml6 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml6 += "<ns1:element id='id2' lookup='lookup2' type='optional'>";
        xml6 += "</ns1:element>";
        xml6 += "</ns1:form>";
        Document document6 = parse(xml6);
        Element parentElement6 = document6.getDocumentElement();
        Element element6 = (Element) parentElement6.getFirstChild();
        ElementDefinition elementDefinition6 = createBuilder().createElementDefinition(parentElement6, element6, new NodePath("parent"));
        Assertions.assertThat(elementDefinition6.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);

        String xml7 = "<?xml version='1.0'?>\n";
        xml7 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml7 += "<ns1:element id='id2' lookup='lookup2' type='optional+'>";
        xml7 += "</ns1:element>";
        xml7 += "</ns1:form>";
        Document document7 = parse(xml7);
        Element parentElement7 = document7.getDocumentElement();
        Element element7 = (Element) parentElement7.getFirstChild();
        ElementDefinition elementDefinition7 = createBuilder().createElementDefinition(parentElement7, element7, new NodePath("parent"));
        Assertions.assertThat(elementDefinition7.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL_MULTIPLE);

        String xml8 = "<?xml version='1.0'?>\n";
        xml8 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml8 += "<ns1:element id='id2' lookup='lookup2' type='prohibited'>";
        xml8 += "</ns1:element>";
        xml8 += "</ns1:form>";
        Document document8 = parse(xml8);
        Element parentElement8 = document8.getDocumentElement();
        Element element8 = (Element) parentElement8.getFirstChild();
        ElementDefinition elementDefinition8 = createBuilder().createElementDefinition(parentElement8, element8, new NodePath("parent"));
        Assertions.assertThat(elementDefinition8.getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionAttributeOtherTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type='required' attr='value'>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        ElementDefinition elementDefinition2 = createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(elementDefinition2.getOtherAttributeNames()).containsExactly("attr");
        Assertions.assertThat(elementDefinition2.getOtherAttributeValue("attr")).isEqualTo("value");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://example.com'>";
        xml3 += "<ns1:element id='id2' lookup='lookup2' type='required' ns2:xmlns='value'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        ElementDefinition elementDefinition3 = createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(elementDefinition3.getOtherAttributeNames()).containsExactly("{http://example.com}xmlns");
        Assertions.assertThat(elementDefinition3.getOtherAttributeValue("{http://example.com}xmlns")).isEqualTo("value");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionChildAttributeTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getAttributeDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getAttributeDefinitions().get(0).getId()).isEqualTo("id3");
        Assertions.assertThat(elementDefinition1.getAttributeDefinitions().get(0).getLookup()).isEqualTo("lookup3");
        Assertions.assertThat(elementDefinition1.getAttributeDefinitions().get(0).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(elementDefinition1.getAttributeDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(elementDefinition1.getAttributeDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<attribute id='id3' lookup='lookup3' type='required'>";
            xml2 += "</attribute>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: attribute], parent/element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:attributeS id='id3' lookup='lookup3' type='required'>";
            xml3 += "</ns1:attributeS>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attributeS], parent/element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionChildElementTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:element id='id3' lookup='lookup3' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getElementDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getElementDefinitions().get(0).getId()).isEqualTo("id3");
        Assertions.assertThat(elementDefinition1.getElementDefinitions().get(0).getLookup()).isEqualTo("lookup3");
        Assertions.assertThat(elementDefinition1.getElementDefinitions().get(0).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(elementDefinition1.getElementDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(elementDefinition1.getElementDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<element id='id3' lookup='lookup3' type='required'>";
            xml2 += "</element>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: element], parent/element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:elementS id='id3' lookup='lookup3' type='required'>";
            xml3 += "</ns1:elementS>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}elementS], parent/element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionChildSingleElementTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:single-element id='id3' type='required'>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getSingleElementDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getSingleElementDefinitions().get(0).getId()).isEqualTo("id3");
        Assertions.assertThat(elementDefinition1.getSingleElementDefinitions().get(0).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(elementDefinition1.getSingleElementDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(elementDefinition1.getSingleElementDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<single-element id='id3' type='required'>";
            xml2 += "</single-element>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: single-element], parent/element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:single-elementS id='id3' type='required'>";
            xml3 += "</ns1:single-elementS>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}single-elementS], parent/element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionChildFormReferenceTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:form-reference id='id3'>";
        xml1 += "</ns1:form-reference>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        ElementDefinition elementDefinition1 = createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(elementDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(elementDefinition1.getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(elementDefinition1.getFormReferenceDefinitions().get(0).getId()).isEqualTo("id3");
        Assertions.assertThat(elementDefinition1.getFormReferenceDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(elementDefinition1.getFormReferenceDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<form-reference id='id3'>";
            xml2 += "</form-reference>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: form-reference], parent/element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:form-referenceS id='id3'>";
            xml3 += "</ns1:form-referenceS>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-referenceS], parent/element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionChildOtherNodeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<otherNode>";
            xml1 += "</otherNode>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createElementDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: otherNode], parent/element[@id2]");
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<ns1:element id='id2' lookup='lookup2' type='required'>";
        xml2 += "<!-- COMMENT -->";
        xml2 += "<ns2:childNode>";
        xml2 += "</ns2:childNode>";
        xml2 += "<ns2:otherNode>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        ElementDefinition elementDefinition2 = createBuilder().createElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(elementDefinition2.getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(elementDefinition2.getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(elementDefinition2.getOtherNodeDefinitions().get(0)).isInstanceOf(DefaultOtherNodeXmlDefinition.class);
        Assertions.assertThat(elementDefinition2.getOtherNodeDefinitions().get(1)).isInstanceOf(OtherNodeDefinitionImpl.class);
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isSingleElementDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='id2' type='required'>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        Assertions.assertThat(createBuilder().isSingleElementDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<single-element id='id2' type='required'>";
        xml2 += "</single-element>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        Assertions.assertThat(createBuilder().isSingleElementDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "<ns1:single-element id='id2' type='required'>";
        xml3 += "</ns1:single-element>";
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
        xml5 += "<single-element id='id2' type='required'>";
        xml5 += "</single-element>";
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
        xml1 += "<ns1:single-element id='id2' type='required'>";
        xml1 += "</ns1:single-element>";
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
        xml2 += "<single-element id='id2' type='required'>";
        xml2 += "</single-element>";
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
            xml3 += "<ns1:single-element id='id2' type='required'>";
            xml3 += "</ns1:single-element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Single element definition is not valid: {http://example.com}single-element], parent");
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
            xml5 += "<single-element id='id2' type='required'>";
            xml5 += "</single-element>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            Element parentElement5 = document5.getDocumentElement();
            Element element5 = (Element) parentElement5.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement5, element5, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Single element definition is not valid: single-element], parent");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionAttributeIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element type='required'>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        SingleElementDefinition singleElementDefinition1 = createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition1.getId()).isEqualTo("");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:single-element id='' type='required'>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        SingleElementDefinition singleElementDefinition2 = createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition2.getId()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:single-element id=' ' type='required'>";
        xml3 += "</ns1:single-element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        SingleElementDefinition singleElementDefinition3 = createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition3.getId()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:single-element id='id' type='required'>";
        xml4 += "</ns1:single-element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        SingleElementDefinition singleElementDefinition4 = createBuilder().createSingleElementDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition4.getId()).isEqualTo("id");

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:single-element id='-id' type='required'>";
        xml5 += "</ns1:single-element>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        SingleElementDefinition singleElementDefinition5 = createBuilder().createSingleElementDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition5.getId()).isEqualTo("-id");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionAttributeTypeTest() {
        String xml11 = "<?xml version='1.0'?>\n";
        xml11 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml11 += "<ns1:single-element id='id2'>";
        xml11 += "</ns1:single-element>";
        xml11 += "</ns1:form>";
        Document document11 = parse(xml11);
        Element parentElement11 = document11.getDocumentElement();
        Element element11 = (Element) parentElement11.getFirstChild();
        SingleElementDefinition singleElementDefinition11 = createBuilder().createSingleElementDefinition(parentElement11, element11, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition11.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml12 = "<?xml version='1.0'?>\n";
        xml12 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml12 += "<ns1:single-element id='id2' type='required'>";
        xml12 += "<ns1:single-element id='id3'>";
        xml12 += "</ns1:single-element>";
        xml12 += "</ns1:single-element>";
        xml12 += "</ns1:form>";
        Document document12 = parse(xml12);
        Element parentElement12 = (Element) document12.getDocumentElement().getFirstChild();
        Element element12 = (Element) parentElement12.getFirstChild();
        SingleElementDefinition singleElementDefinition12 = createBuilder().createSingleElementDefinition(parentElement12, element12, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition12.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);

        String xml13 = "<?xml version='1.0'?>\n";
        xml13 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml13 += "<otherNode>";
        xml13 += "<ns1:single-element id='id3'>";
        xml13 += "</ns1:single-element>";
        xml13 += "</otherNode>";
        xml13 += "</ns1:form>";
        Document document13 = parse(xml13);
        Element parentElement13 = (Element) document13.getDocumentElement().getFirstChild();
        Element element13 = (Element) parentElement13.getFirstChild();
        SingleElementDefinition singleElementDefinition13 = createBuilder().createSingleElementDefinition(parentElement13, element13, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition13.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml14 = "<?xml version='1.0'?>\n";
        xml14 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml14 += "<ns2:otherNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml14 += "<ns1:single-element id='id3'>";
        xml14 += "</ns1:single-element>";
        xml14 += "</ns2:otherNode>";
        xml14 += "</ns1:form>";
        Document document14 = parse(xml14);
        Element parentElement14 = (Element) document14.getDocumentElement().getFirstChild();
        Element element14 = (Element) parentElement14.getFirstChild();
        SingleElementDefinition singleElementDefinition14 = createBuilder().createSingleElementDefinition(parentElement14, element14, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition14.getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);

        String xml15 = "<?xml version='1.0'?>\n";
        xml15 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml15 += "<ns2:childNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml15 += "<ns1:single-element id='id3'>";
        xml15 += "</ns1:single-element>";
        xml15 += "</ns2:childNode>";
        xml15 += "</ns1:form>";
        Document document15 = parse(xml15);
        Element parentElement15 = (Element) document15.getDocumentElement().getFirstChild();
        Element element15 = (Element) parentElement15.getFirstChild();
        SingleElementDefinition singleElementDefinition15 = createBuilder().createSingleElementDefinition(parentElement15, element15, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition15.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        try {
            OtherNodeXmlDefinitionBuilderImpl.setReturnNotNullCardinality();
            String xml16 = "<?xml version='1.0'?>\n";
            xml16 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml16 += "<ns2:childNode xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
            xml16 += "<ns1:single-element id='id3'>";
            xml16 += "</ns1:single-element>";
            xml16 += "</ns2:childNode>";
            xml16 += "</ns1:form>";
            Document document16 = parse(xml16);
            Element parentElement16 = (Element) document16.getDocumentElement().getFirstChild();
            Element element16 = (Element) parentElement16.getFirstChild();
            SingleElementDefinition singleElementDefinition16 = createBuilder().createSingleElementDefinition(parentElement16, element16, new NodePath("parent"));
            Assertions.assertThat(singleElementDefinition16.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);
        } finally {
            OtherNodeXmlDefinitionBuilderImpl.clearReturnNotNullCardinality();
        }

        try {
            OtherNodeXmlDefinitionBuilderImpl.setReturnNotNullCardinality();
            String xml17 = "<?xml version='1.0'?>\n";
            xml17 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml17 += "<ns1:single-element id='id2'>";
            xml17 += "</ns1:single-element>";
            xml17 += "</ns1:form>";
            Document document17 = parse(xml17);
            Element parentElement17 = document17.getDocumentElement();
            Element element17 = (Element) parentElement17.getFirstChild();
            SingleElementDefinition singleElementDefinition17 = createBuilder().createSingleElementDefinition(parentElement17, element17, new NodePath("parent"));
            Assertions.assertThat(singleElementDefinition17.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        } finally {
            OtherNodeXmlDefinitionBuilderImpl.clearReturnNotNullCardinality();
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:single-element id='id2' type=''>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        SingleElementDefinition singleElementDefinition2 = createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition2.getCardinalityDefinition()).isNull();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:single-element id='id2' type=' '>";
        xml3 += "</ns1:single-element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        SingleElementDefinition singleElementDefinition3 = createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition3.getCardinalityDefinition()).isNull();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:single-element id='id2' type='required'>";
        xml4 += "</ns1:single-element>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        SingleElementDefinition singleElementDefinition4 = createBuilder().createSingleElementDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition4.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:single-element id='id2' type='required+'>";
        xml5 += "</ns1:single-element>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        SingleElementDefinition singleElementDefinition5 = createBuilder().createSingleElementDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition5.getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);

        String xml6 = "<?xml version='1.0'?>\n";
        xml6 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml6 += "<ns1:single-element id='id2' type='optional'>";
        xml6 += "</ns1:single-element>";
        xml6 += "</ns1:form>";
        Document document6 = parse(xml6);
        Element parentElement6 = document6.getDocumentElement();
        Element element6 = (Element) parentElement6.getFirstChild();
        SingleElementDefinition singleElementDefinition6 = createBuilder().createSingleElementDefinition(parentElement6, element6, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition6.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);

        String xml7 = "<?xml version='1.0'?>\n";
        xml7 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml7 += "<ns1:single-element id='id2' type='optional+'>";
        xml7 += "</ns1:single-element>";
        xml7 += "</ns1:form>";
        Document document7 = parse(xml7);
        Element parentElement7 = document7.getDocumentElement();
        Element element7 = (Element) parentElement7.getFirstChild();
        SingleElementDefinition singleElementDefinition7 = createBuilder().createSingleElementDefinition(parentElement7, element7, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition7.getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL_MULTIPLE);

        String xml8 = "<?xml version='1.0'?>\n";
        xml8 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml8 += "<ns1:single-element id='id2' type='prohibited'>";
        xml8 += "</ns1:single-element>";
        xml8 += "</ns1:form>";
        Document document8 = parse(xml8);
        Element parentElement8 = document8.getDocumentElement();
        Element element8 = (Element) parentElement8.getFirstChild();
        SingleElementDefinition singleElementDefinition8 = createBuilder().createSingleElementDefinition(parentElement8, element8, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition8.getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionAttributeOtherTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='id2' type='required'>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        SingleElementDefinition singleElementDefinition1 = createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:single-element id='id2' type='required' attr='value'>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        SingleElementDefinition singleElementDefinition2 = createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition2.getOtherAttributeNames()).containsExactly("attr");
        Assertions.assertThat(singleElementDefinition2.getOtherAttributeValue("attr")).isEqualTo("value");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://example.com'>";
        xml3 += "<ns1:single-element id='id2' type='required' ns2:xmlns='value'>";
        xml3 += "</ns1:single-element>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        SingleElementDefinition singleElementDefinition3 = createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition3.getOtherAttributeNames()).containsExactly("{http://example.com}xmlns");
        Assertions.assertThat(singleElementDefinition3.getOtherAttributeValue("{http://example.com}xmlns")).isEqualTo("value");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionChildAttributeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:single-element id='id2' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:single-element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attribute], parent/single-element[@id2]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:single-element id='id2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<attribute id='id3' lookup='lookup3' type='required'>";
            xml2 += "</attribute>";
            xml2 += "</ns1:single-element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: attribute], parent/single-element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:single-element id='id2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:attributeS id='id3' lookup='lookup3' type='required'>";
            xml3 += "</ns1:attributeS>";
            xml3 += "</ns1:single-element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attributeS], parent/single-element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionChildElementTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='id2' type='required'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:element id='id3' lookup='lookup3' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        SingleElementDefinition singleElementDefinition1 = createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(singleElementDefinition1.getElementDefinitions()).hasSize(1);
        Assertions.assertThat(singleElementDefinition1.getElementDefinitions().get(0).getId()).isEqualTo("id3");
        Assertions.assertThat(singleElementDefinition1.getElementDefinitions().get(0).getLookup()).isEqualTo("lookup3");
        Assertions.assertThat(singleElementDefinition1.getElementDefinitions().get(0).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(singleElementDefinition1.getElementDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(singleElementDefinition1.getElementDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:single-element id='id2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<element id='id3' lookup='lookup3' type='required'>";
            xml2 += "</element>";
            xml2 += "</ns1:single-element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: element], parent/single-element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:single-element id='id2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:elementS id='id3' lookup='lookup3' type='required'>";
            xml3 += "</ns1:elementS>";
            xml3 += "</ns1:single-element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}elementS], parent/single-element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionChildSingleElementTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='id2' type='required'>";
        xml1 += "<!-- COMMENT -->";
        xml1 += "<ns1:single-element id='id3' type='required'>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        SingleElementDefinition singleElementDefinition1 = createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition1.getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(singleElementDefinition1.getSingleElementDefinitions()).hasSize(1);
        Assertions.assertThat(singleElementDefinition1.getSingleElementDefinitions().get(0).getId()).isEqualTo("id3");
        Assertions.assertThat(singleElementDefinition1.getSingleElementDefinitions().get(0).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(singleElementDefinition1.getSingleElementDefinitions().get(0).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(singleElementDefinition1.getSingleElementDefinitions().get(0).getOtherAttributeNames()).containsExactly();

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:single-element id='id2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<single-element id='id3' type='required'>";
            xml2 += "</single-element>";
            xml2 += "</ns1:single-element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: single-element], parent/single-element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:single-element id='id2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:single-elementS id='id3' type='required'>";
            xml3 += "</ns1:single-elementS>";
            xml3 += "</ns1:single-element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}single-elementS], parent/single-element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionChildFormReferenceTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:single-element id='id2' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:form-reference id='id3'>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:single-element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-reference], parent/single-element[@id2]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:single-element id='id2' type='required'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<form-reference id='id3'>";
            xml2 += "</form-reference>";
            xml2 += "</ns1:single-element>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: form-reference], parent/single-element[@id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:single-element id='id2' type='required'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:form-referenceS id='id3'>";
            xml3 += "</ns1:form-referenceS>";
            xml3 += "</ns1:single-element>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-referenceS], parent/single-element[@id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionChildOtherNodeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:single-element id='id2' type='required'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<otherNode>";
            xml1 += "</otherNode>";
            xml1 += "</ns1:single-element>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createSingleElementDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: otherNode], parent/single-element[@id2]");
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<ns1:single-element id='id2' type='required'>";
        xml2 += "<!-- COMMENT -->";
        xml2 += "<ns2:childNode>";
        xml2 += "</ns2:childNode>";
        xml2 += "<ns2:otherNode>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        SingleElementDefinition singleElementDefinition2 = createBuilder().createSingleElementDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(singleElementDefinition2.getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(singleElementDefinition2.getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(singleElementDefinition2.getOtherNodeDefinitions().get(0)).isInstanceOf(DefaultOtherNodeXmlDefinition.class);
        Assertions.assertThat(singleElementDefinition2.getOtherNodeDefinitions().get(1)).isInstanceOf(OtherNodeDefinitionImpl.class);
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isFormReferenceDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:form-reference id='id2'>";
        xml1 += "</ns1:form-reference>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(element1)).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form id='id1' xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<form-reference id='id2'>";
        xml2 += "</form-reference>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        Assertions.assertThat(createBuilder().isFormReferenceDefinition(element2)).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://example.com'>";
        xml3 += "<ns1:form-reference id='id2'>";
        xml3 += "</ns1:form-reference>";
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
        xml5 += "<form-reference id='id2'>";
        xml5 += "</form-reference>";
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
        xml1 += "<ns1:form-reference id='id2'>";
        xml1 += "</ns1:form-reference>";
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
        xml2 += "<form-reference id='id2'>";
        xml2 += "</form-reference>";
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
            xml3 += "<ns1:form-reference id='id2'>";
            xml3 += "</ns1:form-reference>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference definition is not valid: {http://example.com}form-reference], parent");
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
            xml5 += "<form-reference id='id2'>";
            xml5 += "</form-reference>";
            xml5 += "</form>";
            Document document5 = parse(xml5);
            Element parentElement5 = document5.getDocumentElement();
            Element element5 = (Element) parentElement5.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement5, element5, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference definition is not valid: form-reference], parent");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionAttributeGroupTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:form-reference id='id2'>";
        xml1 += "</ns1:form-reference>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        FormReferenceDefinition formReferenceDefinition1 = createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition1.getGroup()).isEqualTo("");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:form-reference group='' id='id2'>";
        xml2 += "</ns1:form-reference>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        FormReferenceDefinition formReferenceDefinition2 = createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition2.getGroup()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:form-reference group=' ' id='id2'>";
        xml3 += "</ns1:form-reference>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        FormReferenceDefinition formReferenceDefinition3 = createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition3.getGroup()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:form-reference group='group' id='id2'>";
        xml4 += "</ns1:form-reference>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        FormReferenceDefinition formReferenceDefinition4 = createBuilder().createFormReferenceDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition4.getGroup()).isEqualTo("group");

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:form-reference group='-group' id='id2'>";
        xml5 += "</ns1:form-reference>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        FormReferenceDefinition formReferenceDefinition5 = createBuilder().createFormReferenceDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition5.getGroup()).isEqualTo("-group");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionAttributeIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:form-reference>";
        xml1 += "</ns1:form-reference>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        FormReferenceDefinition formReferenceDefinition1 = createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition1.getId()).isEqualTo("");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:form-reference id=''>";
        xml2 += "</ns1:form-reference>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        FormReferenceDefinition formReferenceDefinition2 = createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition2.getId()).isEqualTo("");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:form-reference id=' '>";
        xml3 += "</ns1:form-reference>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        FormReferenceDefinition formReferenceDefinition3 = createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition3.getId()).isEqualTo(" ");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:form-reference id='id'>";
        xml4 += "</ns1:form-reference>";
        xml4 += "</ns1:form>";
        Document document4 = parse(xml4);
        Element parentElement4 = document4.getDocumentElement();
        Element element4 = (Element) parentElement4.getFirstChild();
        FormReferenceDefinition formReferenceDefinition4 = createBuilder().createFormReferenceDefinition(parentElement4, element4, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition4.getId()).isEqualTo("id");

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml5 += "<ns1:form-reference id='-id'>";
        xml5 += "</ns1:form-reference>";
        xml5 += "</ns1:form>";
        Document document5 = parse(xml5);
        Element parentElement5 = document5.getDocumentElement();
        Element element5 = (Element) parentElement5.getFirstChild();
        FormReferenceDefinition formReferenceDefinition5 = createBuilder().createFormReferenceDefinition(parentElement5, element5, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition5.getId()).isEqualTo("-id");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionAttributeOtherTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:form-reference id='id2'>";
        xml1 += "</ns1:form-reference>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Element parentElement1 = document1.getDocumentElement();
        Element element1 = (Element) parentElement1.getFirstChild();
        FormReferenceDefinition formReferenceDefinition1 = createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition1.getOtherAttributeNames()).containsExactly();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:form-reference id='id2' attr='value'>";
        xml2 += "</ns1:form-reference>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        FormReferenceDefinition formReferenceDefinition2 = createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition2.getOtherAttributeNames()).containsExactly("attr");
        Assertions.assertThat(formReferenceDefinition2.getOtherAttributeValue("attr")).isEqualTo("value");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://example.com'>";
        xml3 += "<ns1:form-reference id='id2' ns2:xmlns='value'>";
        xml3 += "</ns1:form-reference>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Element parentElement3 = document3.getDocumentElement();
        Element element3 = (Element) parentElement3.getFirstChild();
        FormReferenceDefinition formReferenceDefinition3 = createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition3.getOtherAttributeNames()).containsExactly("{http://example.com}xmlns");
        Assertions.assertThat(formReferenceDefinition3.getOtherAttributeValue("{http://example.com}xmlns")).isEqualTo("value");
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionChildAttributeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:form-reference id='id2'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:attribute id='id3' lookup='lookup3' type='required'>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attribute], parent/form-reference[@:id2]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:form-reference id='id2'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<attribute id='id3' lookup='lookup3' type='required'>";
            xml2 += "</attribute>";
            xml2 += "</ns1:form-reference>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: attribute], parent/form-reference[@:id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:form-reference id='id2'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:attributeS id='id3' lookup='lookup3' type='required'>";
            xml3 += "</ns1:attributeS>";
            xml3 += "</ns1:form-reference>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}attributeS], parent/form-reference[@:id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionChildElementTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:form-reference id='id2'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:element id='id3' lookup='lookup3' type='required'>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}element], parent/form-reference[@:id2]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:form-reference id='id2'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<element id='id3' lookup='lookup3' type='required'>";
            xml2 += "</element>";
            xml2 += "</ns1:form-reference>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: element], parent/form-reference[@:id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:form-reference id='id2'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:elementS id='id3' lookup='lookup3' type='required'>";
            xml3 += "</ns1:elementS>";
            xml3 += "</ns1:form-reference>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}elementS], parent/form-reference[@:id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionChildSingleElementTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:form-reference id='id2'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:single-element id='id3' type='required'>";
            xml1 += "</ns1:single-element>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}single-element], parent/form-reference[@:id2]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:form-reference id='id2'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<single-element id='id3' type='required'>";
            xml2 += "</single-element>";
            xml2 += "</ns1:form-reference>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: single-element], parent/form-reference[@:id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:form-reference id='id2'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:single-elementS id='id3' type='required'>";
            xml3 += "</ns1:single-elementS>";
            xml3 += "</ns1:form-reference>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}single-elementS], parent/form-reference[@:id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionChildFormReferenceTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:form-reference id='id2'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<ns1:form-reference id='id3'>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-reference], parent/form-reference[@:id2]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:form-reference id='id2'>";
            xml2 += "<!-- COMMENT -->";
            xml2 += "<form-reference id='id3'>";
            xml2 += "</form-reference>";
            xml2 += "</ns1:form-reference>";
            xml2 += "</ns1:form>";
            Document document = parse(xml2);
            Element parentElement = document.getDocumentElement();
            Element element = (Element) parentElement.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement, element, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: form-reference], parent/form-reference[@:id2]");
        }

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:form-reference id='id2'>";
            xml3 += "<!-- COMMENT -->";
            xml3 += "<ns1:form-referenceS id='id3'>";
            xml3 += "</ns1:form-referenceS>";
            xml3 += "</ns1:form-reference>";
            xml3 += "</ns1:form>";
            Document document3 = parse(xml3);
            Element parentElement3 = document3.getDocumentElement();
            Element element3 = (Element) parentElement3.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement3, element3, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: {http://d-shap.ru/schema/form-model/1.0}form-referenceS], parent/form-reference[@:id2]");
        }
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionChildOtherNodeTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:form-reference id='id2'>";
            xml1 += "<!-- COMMENT -->";
            xml1 += "<otherNode>";
            xml1 += "</otherNode>";
            xml1 += "</ns1:form-reference>";
            xml1 += "</ns1:form>";
            Document document1 = parse(xml1);
            Element parentElement1 = document1.getDocumentElement();
            Element element1 = (Element) parentElement1.getFirstChild();
            createBuilder().createFormReferenceDefinition(parentElement1, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Child element is not valid: otherNode], parent/form-reference[@:id2]");
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<ns1:form-reference id='id2'>";
        xml2 += "<!-- COMMENT -->";
        xml2 += "<ns2:childNode>";
        xml2 += "</ns2:childNode>";
        xml2 += "<ns2:otherNode>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:form-reference>";
        xml2 += "</ns1:form>";
        Document document2 = parse(xml2);
        Element parentElement2 = document2.getDocumentElement();
        Element element2 = (Element) parentElement2.getFirstChild();
        FormReferenceDefinition formReferenceDefinition2 = createBuilder().createFormReferenceDefinition(parentElement2, element2, new NodePath("parent"));
        Assertions.assertThat(formReferenceDefinition2.getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formReferenceDefinition2.getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formReferenceDefinition2.getOtherNodeDefinitions().get(0)).isInstanceOf(DefaultOtherNodeXmlDefinition.class);
        Assertions.assertThat(formReferenceDefinition2.getOtherNodeDefinitions().get(1)).isInstanceOf(OtherNodeDefinitionImpl.class);
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
        Assertions.assertThat(createBuilder().isOtherNodeDefinition(element2)).isFalse();

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

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<otherNode>";
            xml2 += "</otherNode>";
            xml2 += "</ns1:form>";
            Document document2 = parse(xml2);
            Element parentElement2 = document2.getDocumentElement();
            Element element2 = (Element) parentElement2.getFirstChild();
            createBuilder().createOtherNodeDefinition(parentElement2, element2, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: otherNode], parent");
        }

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

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionChildAttributeTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml += "<otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:attribute id='id' lookup='lookup' type='required'>";
        xml += "</ns1:attribute>";
        xml += "</otherNode>";
        xml += "<ns2:otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:attribute id='id' lookup='lookup' type='required'>";
        xml += "</ns1:attribute>";
        xml += "</ns2:otherNode>";
        xml += "</ns1:form>";
        Document document = parse(xml);
        Element parentElement = document.getDocumentElement();

        try {
            Element element1 = (Element) parentElement.getChildNodes().item(0);
            createBuilder().createOtherNodeDefinition(parentElement, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: otherNode], parent");
        }

        Element element2 = (Element) parentElement.getChildNodes().item(1);
        OtherNodeDefinition otherNodeDefinition2 = createBuilder().createOtherNodeDefinition(parentElement, element2, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition2).isInstanceOf(OtherNodeDefinitionImpl.class);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getAttributeDefinition()).isNotNull();
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getAttributeDefinition().getId()).isEqualTo("id");
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getAttributeDefinition().getLookup()).isEqualTo("lookup");
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getAttributeDefinition().getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getAttributeDefinition().getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getAttributeDefinition().getOtherAttributeNames()).containsExactly();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionChildElementTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml += "<otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml += "</ns1:element>";
        xml += "</otherNode>";
        xml += "<ns2:otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml += "</ns1:element>";
        xml += "</ns2:otherNode>";
        xml += "</ns1:form>";
        Document document = parse(xml);
        Element parentElement = document.getDocumentElement();

        try {
            Element element1 = (Element) parentElement.getChildNodes().item(0);
            createBuilder().createOtherNodeDefinition(parentElement, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: otherNode], parent");
        }

        Element element2 = (Element) parentElement.getChildNodes().item(1);
        OtherNodeDefinition otherNodeDefinition2 = createBuilder().createOtherNodeDefinition(parentElement, element2, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition2).isInstanceOf(OtherNodeDefinitionImpl.class);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getElementDefinition()).isNotNull();
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getElementDefinition().getId()).isEqualTo("id");
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getElementDefinition().getLookup()).isEqualTo("lookup");
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getElementDefinition().getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getElementDefinition().getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getElementDefinition().getOtherAttributeNames()).containsExactly();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionChildSingleElementTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml += "<otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:single-element id='id' type='required'>";
        xml += "</ns1:single-element>";
        xml += "</otherNode>";
        xml += "<ns2:otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:single-element id='id' type='required'>";
        xml += "</ns1:single-element>";
        xml += "</ns2:otherNode>";
        xml += "</ns1:form>";
        Document document = parse(xml);
        Element parentElement = document.getDocumentElement();

        try {
            Element element1 = (Element) parentElement.getChildNodes().item(0);
            createBuilder().createOtherNodeDefinition(parentElement, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: otherNode], parent");
        }

        Element element2 = (Element) parentElement.getChildNodes().item(1);
        OtherNodeDefinition otherNodeDefinition2 = createBuilder().createOtherNodeDefinition(parentElement, element2, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition2).isInstanceOf(OtherNodeDefinitionImpl.class);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getSingleElementDefinition()).isNotNull();
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getSingleElementDefinition().getId()).isEqualTo("id");
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getSingleElementDefinition().getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getSingleElementDefinition().getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getSingleElementDefinition().getOtherAttributeNames()).containsExactly();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionChildFormReferenceTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml += "<otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:form-reference id='id'>";
        xml += "</ns1:form-reference>";
        xml += "</otherNode>";
        xml += "<ns2:otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns1:form-reference id='id'>";
        xml += "</ns1:form-reference>";
        xml += "</ns2:otherNode>";
        xml += "</ns1:form>";
        Document document = parse(xml);
        Element parentElement = document.getDocumentElement();

        try {
            Element element1 = (Element) parentElement.getChildNodes().item(0);
            createBuilder().createOtherNodeDefinition(parentElement, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: otherNode], parent");
        }

        Element element2 = (Element) parentElement.getChildNodes().item(1);
        OtherNodeDefinition otherNodeDefinition2 = createBuilder().createOtherNodeDefinition(parentElement, element2, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition2).isInstanceOf(OtherNodeDefinitionImpl.class);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getFormReferenceDefinition()).isNotNull();
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getFormReferenceDefinition().getGroup()).isEqualTo("");
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getFormReferenceDefinition().getId()).isEqualTo("id");
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getFormReferenceDefinition().getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getFormReferenceDefinition().getOtherAttributeNames()).containsExactly();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionChildOtherNodeTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml += "<otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<otherNode>";
        xml += "</otherNode>";
        xml += "<ns2:otherNode>";
        xml += "</ns2:otherNode>";
        xml += "</otherNode>";
        xml += "<ns2:otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<otherNode>";
        xml += "</otherNode>";
        xml += "</ns2:otherNode>";
        xml += "<ns2:otherNode>";
        xml += "<!-- COMMENT -->";
        xml += "<ns2:otherNode>";
        xml += "</ns2:otherNode>";
        xml += "</ns2:otherNode>";
        xml += "</ns1:form>";
        Document document = parse(xml);
        Element parentElement = document.getDocumentElement();

        try {
            Element element1 = (Element) parentElement.getChildNodes().item(0);
            createBuilder().createOtherNodeDefinition(parentElement, element1, new NodePath("parent"));
            Assertions.fail("FormXmlDefinitionBuilderImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Other node definition is not valid: otherNode], parent");
        }

        Element element2 = (Element) parentElement.getChildNodes().item(1);
        OtherNodeDefinition otherNodeDefinition2 = createBuilder().createOtherNodeDefinition(parentElement, element2, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition2).isInstanceOf(OtherNodeDefinitionImpl.class);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition2).getOtherNodeDefinition()).isNull();

        Element element3 = (Element) parentElement.getChildNodes().item(2);
        OtherNodeDefinition otherNodeDefinition3 = createBuilder().createOtherNodeDefinition(parentElement, element3, new NodePath("parent"));
        Assertions.assertThat(otherNodeDefinition3).isInstanceOf(OtherNodeDefinitionImpl.class);
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition3).getOtherNodeDefinition()).isNotNull();
        Assertions.assertThat(((OtherNodeDefinitionImpl) otherNodeDefinition3).getOtherNodeDefinition()).isInstanceOf(OtherNodeDefinitionImpl.class);
    }

    private FormXmlDefinitionBuilderImpl createBuilder() {
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        return new FormXmlDefinitionBuilderImpl(otherNodeXmlDefinitionBuilders);
    }

}
