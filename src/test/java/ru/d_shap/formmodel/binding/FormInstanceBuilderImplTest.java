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
package ru.d_shap.formmodel.binding;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.binding.model.BindingSourceImpl;
import ru.d_shap.formmodel.definition.loader.xml.FormXmlDefinitionsElementLoader;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.document.DocumentWriter;

/**
 * Tests for {@link FormInstanceBuilderImpl}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormInstanceBuilderImplTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormInstanceBuilderImplTest() {
        super();
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildFormInstanceDefaultTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "</ns1:form>";
            FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
            FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
            Document document1 = newDocument();
            formInstanceBuilder1.buildFormInstance(new BindingSourceImpl(null), document1, formDefinitions1.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Form is not present: {source}form[@:id]]");
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("source repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildFormInstanceReprTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' repr='repr' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl(null), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form group=\"\" id=\"id\" repr=\"repr\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' repr='repr' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("source repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form group=\"\" id=\"id\" repr=\"repr\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildAttributeInstanceDefaultTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml += "<ns1:attribute id='id' lookup='lookup' type='required'>";
        xml += "</ns1:attribute>";
        xml += "</ns1:element>";
        xml += "</ns1:form>";
        FormDefinitions formDefinitions = createFormDefinitions(xml);
        FormInstanceBuilderImpl formInstanceBuilder = createBinder(formDefinitions);
        Document document = newDocument();
        formInstanceBuilder.buildFormInstance(new BindingSourceImpl("repr"), document, formDefinitions.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute id=\"id\"/></element></form>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildAttributeInstanceReprRequiredTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id' lookup='lookup' type='required'>";
            xml1 += "<ns1:attribute id='id' lookup='lookup' type='required' repr='repr' count='0'>";
            xml1 += "</ns1:attribute>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
            FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
            Document document1 = newDocument();
            formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required attribute is not present: attribute[@id]], {source}form[@:id]/element[@id]");
        }

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml2 += "<ns1:attribute id='id' lookup='lookup' type='required' repr='repr' count='1'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute count=\"1\" id=\"id\" repr=\"repr\"/></element></form>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildAttributeInstanceReprOptionalTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml1 += "<ns1:attribute id='id' lookup='lookup' type='optional' repr='repr' count='0'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml2 += "<ns1:attribute id='id' lookup='lookup' type='optional' repr='repr' count='1'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute count=\"1\" id=\"id\" repr=\"repr\"/></element></form>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildAttributeInstanceReprProhibitedTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml1 += "<ns1:attribute id='id' lookup='lookup' type='prohibited' repr='repr' count='0'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id' lookup='lookup' type='required'>";
            xml2 += "<ns1:attribute id='id' lookup='lookup' type='prohibited' repr='repr' count='1'>";
            xml2 += "</ns1:attribute>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
            FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
            Document document2 = newDocument();
            formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited attribute is present: attribute[@id]], {source}form[@:id]/element[@id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildElementInstanceTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");

        try {
            String xml21 = "<?xml version='1.0'?>\n";
            xml21 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml21 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='-1'>";
            xml21 += "</ns1:element>";
            xml21 += "</ns1:form>";
            FormDefinitions formDefinitions21 = createFormDefinitions(xml21);
            FormInstanceBuilderImpl formInstanceBuilder21 = createBinder(formDefinitions21);
            Document document21 = newDocument();
            formInstanceBuilder21.buildFormInstance(new BindingSourceImpl("repr"), document21, formDefinitions21.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        try {
            String xml22 = "<?xml version='1.0'?>\n";
            xml22 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml22 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='0'>";
            xml22 += "</ns1:element>";
            xml22 += "</ns1:form>";
            FormDefinitions formDefinitions22 = createFormDefinitions(xml22);
            FormInstanceBuilderImpl formInstanceBuilder22 = createBinder(formDefinitions22);
            Document document22 = newDocument();
            formInstanceBuilder22.buildFormInstance(new BindingSourceImpl("repr"), document22, formDefinitions22.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        String xml23 = "<?xml version='1.0'?>\n";
        xml23 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml23 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='1'>";
        xml23 += "</ns1:element>";
        xml23 += "</ns1:form>";
        FormDefinitions formDefinitions23 = createFormDefinitions(xml23);
        FormInstanceBuilderImpl formInstanceBuilder23 = createBinder(formDefinitions23);
        Document document23 = newDocument();
        formInstanceBuilder23.buildFormInstance(new BindingSourceImpl("repr"), document23, formDefinitions23.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document23)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        try {
            String xml24 = "<?xml version='1.0'?>\n";
            xml24 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml24 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='2'>";
            xml24 += "</ns1:element>";
            xml24 += "</ns1:form>";
            FormDefinitions formDefinitions24 = createFormDefinitions(xml24);
            FormInstanceBuilderImpl formInstanceBuilder24 = createBinder(formDefinitions24);
            Document document24 = newDocument();
            formInstanceBuilder24.buildFormInstance(new BindingSourceImpl("repr"), document24, formDefinitions24.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is present more than once: element[@id]], {source}form[@:id]");
        }

        try {
            String xml31 = "<?xml version='1.0'?>\n";
            xml31 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml31 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='-1'>";
            xml31 += "</ns1:element>";
            xml31 += "</ns1:form>";
            FormDefinitions formDefinitions31 = createFormDefinitions(xml31);
            FormInstanceBuilderImpl formInstanceBuilder31 = createBinder(formDefinitions31);
            Document document31 = newDocument();
            formInstanceBuilder31.buildFormInstance(new BindingSourceImpl("repr"), document31, formDefinitions31.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        try {
            String xml32 = "<?xml version='1.0'?>\n";
            xml32 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml32 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='0'>";
            xml32 += "</ns1:element>";
            xml32 += "</ns1:form>";
            FormDefinitions formDefinitions32 = createFormDefinitions(xml32);
            FormInstanceBuilderImpl formInstanceBuilder32 = createBinder(formDefinitions32);
            Document document32 = newDocument();
            formInstanceBuilder32.buildFormInstance(new BindingSourceImpl("repr"), document32, formDefinitions32.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        String xml33 = "<?xml version='1.0'?>\n";
        xml33 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml33 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='1'>";
        xml33 += "</ns1:element>";
        xml33 += "</ns1:form>";
        FormDefinitions formDefinitions33 = createFormDefinitions(xml33);
        FormInstanceBuilderImpl formInstanceBuilder33 = createBinder(formDefinitions33);
        Document document33 = newDocument();
        formInstanceBuilder33.buildFormInstance(new BindingSourceImpl("repr"), document33, formDefinitions33.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document33)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        String xml34 = "<?xml version='1.0'?>\n";
        xml34 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml34 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='2'>";
        xml34 += "</ns1:element>";
        xml34 += "</ns1:form>";
        FormDefinitions formDefinitions34 = createFormDefinitions(xml34);
        FormInstanceBuilderImpl formInstanceBuilder34 = createBinder(formDefinitions34);
        Document document34 = newDocument();
        formInstanceBuilder34.buildFormInstance(new BindingSourceImpl("repr"), document34, formDefinitions34.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document34)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"2\" id=\"id\" repr=\"repr\"/><element count=\"2\" id=\"id\" repr=\"repr\"/></form>");

        String xml41 = "<?xml version='1.0'?>\n";
        xml41 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml41 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='-1'>";
        xml41 += "</ns1:element>";
        xml41 += "</ns1:form>";
        FormDefinitions formDefinitions41 = createFormDefinitions(xml41);
        FormInstanceBuilderImpl formInstanceBuilder41 = createBinder(formDefinitions41);
        Document document41 = newDocument();
        formInstanceBuilder41.buildFormInstance(new BindingSourceImpl("repr"), document41, formDefinitions41.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document41)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml42 = "<?xml version='1.0'?>\n";
        xml42 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml42 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='0'>";
        xml42 += "</ns1:element>";
        xml42 += "</ns1:form>";
        FormDefinitions formDefinitions42 = createFormDefinitions(xml42);
        FormInstanceBuilderImpl formInstanceBuilder42 = createBinder(formDefinitions42);
        Document document42 = newDocument();
        formInstanceBuilder42.buildFormInstance(new BindingSourceImpl("repr"), document42, formDefinitions42.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document42)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml43 = "<?xml version='1.0'?>\n";
        xml43 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml43 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='1'>";
        xml43 += "</ns1:element>";
        xml43 += "</ns1:form>";
        FormDefinitions formDefinitions43 = createFormDefinitions(xml43);
        FormInstanceBuilderImpl formInstanceBuilder43 = createBinder(formDefinitions43);
        Document document43 = newDocument();
        formInstanceBuilder43.buildFormInstance(new BindingSourceImpl("repr"), document43, formDefinitions43.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document43)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        try {
            String xml44 = "<?xml version='1.0'?>\n";
            xml44 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml44 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='2'>";
            xml44 += "</ns1:element>";
            xml44 += "</ns1:form>";
            FormDefinitions formDefinitions44 = createFormDefinitions(xml44);
            FormInstanceBuilderImpl formInstanceBuilder44 = createBinder(formDefinitions44);
            Document document44 = newDocument();
            formInstanceBuilder44.buildFormInstance(new BindingSourceImpl("repr"), document44, formDefinitions44.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id]], {source}form[@:id]");
        }

        String xml51 = "<?xml version='1.0'?>\n";
        xml51 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml51 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='-1'>";
        xml51 += "</ns1:element>";
        xml51 += "</ns1:form>";
        FormDefinitions formDefinitions51 = createFormDefinitions(xml51);
        FormInstanceBuilderImpl formInstanceBuilder51 = createBinder(formDefinitions51);
        Document document51 = newDocument();
        formInstanceBuilder51.buildFormInstance(new BindingSourceImpl("repr"), document51, formDefinitions51.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document51)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml52 = "<?xml version='1.0'?>\n";
        xml52 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml52 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='0'>";
        xml52 += "</ns1:element>";
        xml52 += "</ns1:form>";
        FormDefinitions formDefinitions52 = createFormDefinitions(xml52);
        FormInstanceBuilderImpl formInstanceBuilder52 = createBinder(formDefinitions52);
        Document document52 = newDocument();
        formInstanceBuilder52.buildFormInstance(new BindingSourceImpl("repr"), document52, formDefinitions52.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document52)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml53 = "<?xml version='1.0'?>\n";
        xml53 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml53 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='1'>";
        xml53 += "</ns1:element>";
        xml53 += "</ns1:form>";
        FormDefinitions formDefinitions53 = createFormDefinitions(xml53);
        FormInstanceBuilderImpl formInstanceBuilder53 = createBinder(formDefinitions53);
        Document document53 = newDocument();
        formInstanceBuilder53.buildFormInstance(new BindingSourceImpl("repr"), document53, formDefinitions53.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document53)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        String xml54 = "<?xml version='1.0'?>\n";
        xml54 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml54 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='2'>";
        xml54 += "</ns1:element>";
        xml54 += "</ns1:form>";
        FormDefinitions formDefinitions54 = createFormDefinitions(xml54);
        FormInstanceBuilderImpl formInstanceBuilder54 = createBinder(formDefinitions54);
        Document document54 = newDocument();
        formInstanceBuilder54.buildFormInstance(new BindingSourceImpl("repr"), document54, formDefinitions54.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document54)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"2\" id=\"id\" repr=\"repr\"/><element count=\"2\" id=\"id\" repr=\"repr\"/></form>");

        String xml61 = "<?xml version='1.0'?>\n";
        xml61 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml61 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='-1'>";
        xml61 += "</ns1:element>";
        xml61 += "</ns1:form>";
        FormDefinitions formDefinitions61 = createFormDefinitions(xml61);
        FormInstanceBuilderImpl formInstanceBuilder61 = createBinder(formDefinitions61);
        Document document61 = newDocument();
        formInstanceBuilder61.buildFormInstance(new BindingSourceImpl("repr"), document61, formDefinitions61.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document61)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml62 = "<?xml version='1.0'?>\n";
        xml62 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml62 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='0'>";
        xml62 += "</ns1:element>";
        xml62 += "</ns1:form>";
        FormDefinitions formDefinitions62 = createFormDefinitions(xml62);
        FormInstanceBuilderImpl formInstanceBuilder62 = createBinder(formDefinitions62);
        Document document62 = newDocument();
        formInstanceBuilder62.buildFormInstance(new BindingSourceImpl("repr"), document62, formDefinitions62.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document62)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            String xml63 = "<?xml version='1.0'?>\n";
            xml63 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml63 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='1'>";
            xml63 += "</ns1:element>";
            xml63 += "</ns1:form>";
            FormDefinitions formDefinitions63 = createFormDefinitions(xml63);
            FormInstanceBuilderImpl formInstanceBuilder63 = createBinder(formDefinitions63);
            Document document63 = newDocument();
            formInstanceBuilder63.buildFormInstance(new BindingSourceImpl("repr"), document63, formDefinitions63.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited element is present: element[@id]], {source}form[@:id]");
        }

        try {
            String xml64 = "<?xml version='1.0'?>\n";
            xml64 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml64 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='2'>";
            xml64 += "</ns1:element>";
            xml64 += "</ns1:form>";
            FormDefinitions formDefinitions64 = createFormDefinitions(xml64);
            FormInstanceBuilderImpl formInstanceBuilder64 = createBinder(formDefinitions64);
            Document document64 = newDocument();
            formInstanceBuilder64.buildFormInstance(new BindingSourceImpl("repr"), document64, formDefinitions64.getFormDefinition("id"));
            Assertions.assertThat(DocumentWriter.getAsString(document64)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"2\" id=\"id\" repr=\"repr\"/><element count=\"2\" id=\"id\" repr=\"repr\"/></form>");
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited element is present: element[@id]], {source}form[@:id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildSingleElementInstanceTest() {
        String xml11 = "<?xml version='1.0'?>\n";
        xml11 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml11 += "<ns1:single-element id='id' type='required'>";
        xml11 += "<ns1:element lookup='lookup'>";
        xml11 += "</ns1:element>";
        xml11 += "</ns1:single-element>";
        xml11 += "</ns1:form>";
        FormDefinitions formDefinitions11 = createFormDefinitions(xml11);
        FormInstanceBuilderImpl formInstanceBuilder11 = createBinder(formDefinitions11);
        Document document11 = newDocument();
        formInstanceBuilder11.buildFormInstance(new BindingSourceImpl("repr"), document11, formDefinitions11.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document11)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element id=\"\"/></single-element></form>");

        try {
            String xml12 = "<?xml version='1.0'?>\n";
            xml12 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml12 += "<ns1:single-element id='id' type='required'>";
            xml12 += "<ns1:element lookup='lookup'>";
            xml12 += "</ns1:element>";
            xml12 += "<ns1:element lookup='lookup'>";
            xml12 += "</ns1:element>";
            xml12 += "</ns1:single-element>";
            xml12 += "</ns1:form>";
            FormDefinitions formDefinitions12 = createFormDefinitions(xml12);
            FormInstanceBuilderImpl formInstanceBuilder12 = createBinder(formDefinitions12);
            Document document12 = newDocument();
            formInstanceBuilder12.buildFormInstance(new BindingSourceImpl("repr"), document12, formDefinitions12.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml211 = "<?xml version='1.0'?>\n";
            xml211 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml211 += "<ns1:single-element id='id' type='required'>";
            xml211 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
            xml211 += "</ns1:element>";
            xml211 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
            xml211 += "</ns1:element>";
            xml211 += "</ns1:single-element>";
            xml211 += "</ns1:form>";
            FormDefinitions formDefinitions211 = createFormDefinitions(xml211);
            FormInstanceBuilderImpl formInstanceBuilder211 = createBinder(formDefinitions211);
            Document document211 = newDocument();
            formInstanceBuilder211.buildFormInstance(new BindingSourceImpl("repr"), document211, formDefinitions211.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml212 = "<?xml version='1.0'?>\n";
            xml212 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml212 += "<ns1:single-element id='id' type='required'>";
            xml212 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
            xml212 += "</ns1:element>";
            xml212 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
            xml212 += "</ns1:element>";
            xml212 += "</ns1:single-element>";
            xml212 += "</ns1:form>";
            FormDefinitions formDefinitions212 = createFormDefinitions(xml212);
            FormInstanceBuilderImpl formInstanceBuilder212 = createBinder(formDefinitions212);
            Document document212 = newDocument();
            formInstanceBuilder212.buildFormInstance(new BindingSourceImpl("repr"), document212, formDefinitions212.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        String xml213 = "<?xml version='1.0'?>\n";
        xml213 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml213 += "<ns1:single-element id='id' type='required'>";
        xml213 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
        xml213 += "</ns1:element>";
        xml213 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml213 += "</ns1:element>";
        xml213 += "</ns1:single-element>";
        xml213 += "</ns1:form>";
        FormDefinitions formDefinitions213 = createFormDefinitions(xml213);
        FormInstanceBuilderImpl formInstanceBuilder213 = createBinder(formDefinitions213);
        Document document213 = newDocument();
        formInstanceBuilder213.buildFormInstance(new BindingSourceImpl("repr"), document213, formDefinitions213.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document213)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml214 = "<?xml version='1.0'?>\n";
            xml214 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml214 += "<ns1:single-element id='id' type='required'>";
            xml214 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml214 += "</ns1:element>";
            xml214 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
            xml214 += "</ns1:element>";
            xml214 += "</ns1:single-element>";
            xml214 += "</ns1:form>";
            FormDefinitions formDefinitions214 = createFormDefinitions(xml214);
            FormInstanceBuilderImpl formInstanceBuilder214 = createBinder(formDefinitions214);
            Document document214 = newDocument();
            formInstanceBuilder214.buildFormInstance(new BindingSourceImpl("repr"), document214, formDefinitions214.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml221 = "<?xml version='1.0'?>\n";
            xml221 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml221 += "<ns1:single-element id='id' type='required'>";
            xml221 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
            xml221 += "</ns1:element>";
            xml221 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
            xml221 += "</ns1:element>";
            xml221 += "</ns1:single-element>";
            xml221 += "</ns1:form>";
            FormDefinitions formDefinitions221 = createFormDefinitions(xml221);
            FormInstanceBuilderImpl formInstanceBuilder221 = createBinder(formDefinitions221);
            Document document221 = newDocument();
            formInstanceBuilder221.buildFormInstance(new BindingSourceImpl("repr"), document221, formDefinitions221.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml222 = "<?xml version='1.0'?>\n";
            xml222 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml222 += "<ns1:single-element id='id' type='required'>";
            xml222 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
            xml222 += "</ns1:element>";
            xml222 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
            xml222 += "</ns1:element>";
            xml222 += "</ns1:single-element>";
            xml222 += "</ns1:form>";
            FormDefinitions formDefinitions222 = createFormDefinitions(xml222);
            FormInstanceBuilderImpl formInstanceBuilder222 = createBinder(formDefinitions222);
            Document document222 = newDocument();
            formInstanceBuilder222.buildFormInstance(new BindingSourceImpl("repr"), document222, formDefinitions222.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        String xml223 = "<?xml version='1.0'?>\n";
        xml223 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml223 += "<ns1:single-element id='id' type='required'>";
        xml223 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
        xml223 += "</ns1:element>";
        xml223 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
        xml223 += "</ns1:element>";
        xml223 += "</ns1:single-element>";
        xml223 += "</ns1:form>";
        FormDefinitions formDefinitions223 = createFormDefinitions(xml223);
        FormInstanceBuilderImpl formInstanceBuilder223 = createBinder(formDefinitions223);
        Document document223 = newDocument();
        formInstanceBuilder223.buildFormInstance(new BindingSourceImpl("repr"), document223, formDefinitions223.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document223)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml224 = "<?xml version='1.0'?>\n";
            xml224 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml224 += "<ns1:single-element id='id' type='required'>";
            xml224 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml224 += "</ns1:element>";
            xml224 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
            xml224 += "</ns1:element>";
            xml224 += "</ns1:single-element>";
            xml224 += "</ns1:form>";
            FormDefinitions formDefinitions224 = createFormDefinitions(xml224);
            FormInstanceBuilderImpl formInstanceBuilder224 = createBinder(formDefinitions224);
            Document document224 = newDocument();
            formInstanceBuilder224.buildFormInstance(new BindingSourceImpl("repr"), document224, formDefinitions224.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        String xml231 = "<?xml version='1.0'?>\n";
        xml231 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml231 += "<ns1:single-element id='id' type='required'>";
        xml231 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml231 += "</ns1:element>";
        xml231 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
        xml231 += "</ns1:element>";
        xml231 += "</ns1:single-element>";
        xml231 += "</ns1:form>";
        FormDefinitions formDefinitions231 = createFormDefinitions(xml231);
        FormInstanceBuilderImpl formInstanceBuilder231 = createBinder(formDefinitions231);
        Document document231 = newDocument();
        formInstanceBuilder231.buildFormInstance(new BindingSourceImpl("repr"), document231, formDefinitions231.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document231)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr2\"/></single-element></form>");

        String xml232 = "<?xml version='1.0'?>\n";
        xml232 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml232 += "<ns1:single-element id='id' type='required'>";
        xml232 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
        xml232 += "</ns1:element>";
        xml232 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
        xml232 += "</ns1:element>";
        xml232 += "</ns1:single-element>";
        xml232 += "</ns1:form>";
        FormDefinitions formDefinitions232 = createFormDefinitions(xml232);
        FormInstanceBuilderImpl formInstanceBuilder232 = createBinder(formDefinitions232);
        Document document232 = newDocument();
        formInstanceBuilder232.buildFormInstance(new BindingSourceImpl("repr"), document232, formDefinitions232.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document232)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr2\"/></single-element></form>");

        try {
            String xml233 = "<?xml version='1.0'?>\n";
            xml233 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml233 += "<ns1:single-element id='id' type='required'>";
            xml233 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml233 += "</ns1:element>";
            xml233 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml233 += "</ns1:element>";
            xml233 += "</ns1:single-element>";
            xml233 += "</ns1:form>";
            FormDefinitions formDefinitions233 = createFormDefinitions(xml233);
            FormInstanceBuilderImpl formInstanceBuilder233 = createBinder(formDefinitions233);
            Document document233 = newDocument();
            formInstanceBuilder233.buildFormInstance(new BindingSourceImpl("repr"), document233, formDefinitions233.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml234 = "<?xml version='1.0'?>\n";
            xml234 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml234 += "<ns1:single-element id='id' type='required'>";
            xml234 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml234 += "</ns1:element>";
            xml234 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml234 += "</ns1:element>";
            xml234 += "</ns1:single-element>";
            xml234 += "</ns1:form>";
            FormDefinitions formDefinitions234 = createFormDefinitions(xml234);
            FormInstanceBuilderImpl formInstanceBuilder234 = createBinder(formDefinitions234);
            Document document234 = newDocument();
            formInstanceBuilder234.buildFormInstance(new BindingSourceImpl("repr"), document234, formDefinitions234.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml241 = "<?xml version='1.0'?>\n";
            xml241 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml241 += "<ns1:single-element id='id' type='required'>";
            xml241 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
            xml241 += "</ns1:element>";
            xml241 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml241 += "</ns1:element>";
            xml241 += "</ns1:single-element>";
            xml241 += "</ns1:form>";
            FormDefinitions formDefinitions241 = createFormDefinitions(xml241);
            FormInstanceBuilderImpl formInstanceBuilder241 = createBinder(formDefinitions241);
            Document document241 = newDocument();
            formInstanceBuilder241.buildFormInstance(new BindingSourceImpl("repr"), document241, formDefinitions241.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml242 = "<?xml version='1.0'?>\n";
            xml242 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml242 += "<ns1:single-element id='id' type='required'>";
            xml242 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
            xml242 += "</ns1:element>";
            xml242 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml242 += "</ns1:element>";
            xml242 += "</ns1:single-element>";
            xml242 += "</ns1:form>";
            FormDefinitions formDefinitions242 = createFormDefinitions(xml242);
            FormInstanceBuilderImpl formInstanceBuilder242 = createBinder(formDefinitions242);
            Document document242 = newDocument();
            formInstanceBuilder242.buildFormInstance(new BindingSourceImpl("repr"), document242, formDefinitions242.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml243 = "<?xml version='1.0'?>\n";
            xml243 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml243 += "<ns1:single-element id='id' type='required'>";
            xml243 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml243 += "</ns1:element>";
            xml243 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml243 += "</ns1:element>";
            xml243 += "</ns1:single-element>";
            xml243 += "</ns1:form>";
            FormDefinitions formDefinitions243 = createFormDefinitions(xml243);
            FormInstanceBuilderImpl formInstanceBuilder243 = createBinder(formDefinitions243);
            Document document243 = newDocument();
            formInstanceBuilder243.buildFormInstance(new BindingSourceImpl("repr"), document243, formDefinitions243.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml244 = "<?xml version='1.0'?>\n";
            xml244 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml244 += "<ns1:single-element id='id' type='required'>";
            xml244 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml244 += "</ns1:element>";
            xml244 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml244 += "</ns1:element>";
            xml244 += "</ns1:single-element>";
            xml244 += "</ns1:form>";
            FormDefinitions formDefinitions244 = createFormDefinitions(xml244);
            FormInstanceBuilderImpl formInstanceBuilder244 = createBinder(formDefinitions244);
            Document document244 = newDocument();
            formInstanceBuilder244.buildFormInstance(new BindingSourceImpl("repr"), document244, formDefinitions244.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        String xml311 = "<?xml version='1.0'?>\n";
        xml311 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml311 += "<ns1:single-element id='id' type='optional'>";
        xml311 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml311 += "</ns1:element>";
        xml311 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml311 += "</ns1:element>";
        xml311 += "</ns1:single-element>";
        xml311 += "</ns1:form>";
        FormDefinitions formDefinitions311 = createFormDefinitions(xml311);
        FormInstanceBuilderImpl formInstanceBuilder311 = createBinder(formDefinitions311);
        Document document311 = newDocument();
        formInstanceBuilder311.buildFormInstance(new BindingSourceImpl("repr"), document311, formDefinitions311.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document311)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml312 = "<?xml version='1.0'?>\n";
        xml312 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml312 += "<ns1:single-element id='id' type='optional'>";
        xml312 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
        xml312 += "</ns1:element>";
        xml312 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml312 += "</ns1:element>";
        xml312 += "</ns1:single-element>";
        xml312 += "</ns1:form>";
        FormDefinitions formDefinitions312 = createFormDefinitions(xml312);
        FormInstanceBuilderImpl formInstanceBuilder312 = createBinder(formDefinitions312);
        Document document312 = newDocument();
        formInstanceBuilder312.buildFormInstance(new BindingSourceImpl("repr"), document312, formDefinitions312.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document312)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml313 = "<?xml version='1.0'?>\n";
        xml313 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml313 += "<ns1:single-element id='id' type='optional'>";
        xml313 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
        xml313 += "</ns1:element>";
        xml313 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml313 += "</ns1:element>";
        xml313 += "</ns1:single-element>";
        xml313 += "</ns1:form>";
        FormDefinitions formDefinitions313 = createFormDefinitions(xml313);
        FormInstanceBuilderImpl formInstanceBuilder313 = createBinder(formDefinitions313);
        Document document313 = newDocument();
        formInstanceBuilder313.buildFormInstance(new BindingSourceImpl("repr"), document313, formDefinitions313.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document313)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml314 = "<?xml version='1.0'?>\n";
            xml314 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml314 += "<ns1:single-element id='id' type='optional'>";
            xml314 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml314 += "</ns1:element>";
            xml314 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
            xml314 += "</ns1:element>";
            xml314 += "</ns1:single-element>";
            xml314 += "</ns1:form>";
            FormDefinitions formDefinitions314 = createFormDefinitions(xml314);
            FormInstanceBuilderImpl formInstanceBuilder314 = createBinder(formDefinitions314);
            Document document314 = newDocument();
            formInstanceBuilder314.buildFormInstance(new BindingSourceImpl("repr"), document314, formDefinitions314.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        String xml321 = "<?xml version='1.0'?>\n";
        xml321 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml321 += "<ns1:single-element id='id' type='optional'>";
        xml321 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml321 += "</ns1:element>";
        xml321 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
        xml321 += "</ns1:element>";
        xml321 += "</ns1:single-element>";
        xml321 += "</ns1:form>";
        FormDefinitions formDefinitions321 = createFormDefinitions(xml321);
        FormInstanceBuilderImpl formInstanceBuilder321 = createBinder(formDefinitions321);
        Document document321 = newDocument();
        formInstanceBuilder321.buildFormInstance(new BindingSourceImpl("repr"), document321, formDefinitions321.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document321)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml322 = "<?xml version='1.0'?>\n";
        xml322 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml322 += "<ns1:single-element id='id' type='optional'>";
        xml322 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
        xml322 += "</ns1:element>";
        xml322 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
        xml322 += "</ns1:element>";
        xml322 += "</ns1:single-element>";
        xml322 += "</ns1:form>";
        FormDefinitions formDefinitions322 = createFormDefinitions(xml322);
        FormInstanceBuilderImpl formInstanceBuilder322 = createBinder(formDefinitions322);
        Document document322 = newDocument();
        formInstanceBuilder322.buildFormInstance(new BindingSourceImpl("repr"), document322, formDefinitions322.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document322)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml323 = "<?xml version='1.0'?>\n";
        xml323 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml323 += "<ns1:single-element id='id' type='optional'>";
        xml323 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
        xml323 += "</ns1:element>";
        xml323 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
        xml323 += "</ns1:element>";
        xml323 += "</ns1:single-element>";
        xml323 += "</ns1:form>";
        FormDefinitions formDefinitions323 = createFormDefinitions(xml323);
        FormInstanceBuilderImpl formInstanceBuilder323 = createBinder(formDefinitions323);
        Document document323 = newDocument();
        formInstanceBuilder323.buildFormInstance(new BindingSourceImpl("repr"), document323, formDefinitions323.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document323)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml324 = "<?xml version='1.0'?>\n";
            xml324 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml324 += "<ns1:single-element id='id' type='optional'>";
            xml324 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml324 += "</ns1:element>";
            xml324 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
            xml324 += "</ns1:element>";
            xml324 += "</ns1:single-element>";
            xml324 += "</ns1:form>";
            FormDefinitions formDefinitions324 = createFormDefinitions(xml324);
            FormInstanceBuilderImpl formInstanceBuilder324 = createBinder(formDefinitions324);
            Document document324 = newDocument();
            formInstanceBuilder324.buildFormInstance(new BindingSourceImpl("repr"), document324, formDefinitions324.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        String xml331 = "<?xml version='1.0'?>\n";
        xml331 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml331 += "<ns1:single-element id='id' type='optional'>";
        xml331 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml331 += "</ns1:element>";
        xml331 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
        xml331 += "</ns1:element>";
        xml331 += "</ns1:single-element>";
        xml331 += "</ns1:form>";
        FormDefinitions formDefinitions331 = createFormDefinitions(xml331);
        FormInstanceBuilderImpl formInstanceBuilder331 = createBinder(formDefinitions331);
        Document document331 = newDocument();
        formInstanceBuilder331.buildFormInstance(new BindingSourceImpl("repr"), document331, formDefinitions331.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document331)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr2\"/></single-element></form>");

        String xml332 = "<?xml version='1.0'?>\n";
        xml332 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml332 += "<ns1:single-element id='id' type='optional'>";
        xml332 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
        xml332 += "</ns1:element>";
        xml332 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
        xml332 += "</ns1:element>";
        xml332 += "</ns1:single-element>";
        xml332 += "</ns1:form>";
        FormDefinitions formDefinitions332 = createFormDefinitions(xml332);
        FormInstanceBuilderImpl formInstanceBuilder332 = createBinder(formDefinitions332);
        Document document332 = newDocument();
        formInstanceBuilder332.buildFormInstance(new BindingSourceImpl("repr"), document332, formDefinitions332.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document332)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"\" repr=\"repr2\"/></single-element></form>");

        try {
            String xml333 = "<?xml version='1.0'?>\n";
            xml333 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml333 += "<ns1:single-element id='id' type='optional'>";
            xml333 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml333 += "</ns1:element>";
            xml333 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml333 += "</ns1:element>";
            xml333 += "</ns1:single-element>";
            xml333 += "</ns1:form>";
            FormDefinitions formDefinitions333 = createFormDefinitions(xml333);
            FormInstanceBuilderImpl formInstanceBuilder333 = createBinder(formDefinitions333);
            Document document333 = newDocument();
            formInstanceBuilder333.buildFormInstance(new BindingSourceImpl("repr"), document333, formDefinitions333.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml334 = "<?xml version='1.0'?>\n";
            xml334 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml334 += "<ns1:single-element id='id' type='optional'>";
            xml334 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml334 += "</ns1:element>";
            xml334 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml334 += "</ns1:element>";
            xml334 += "</ns1:single-element>";
            xml334 += "</ns1:form>";
            FormDefinitions formDefinitions334 = createFormDefinitions(xml334);
            FormInstanceBuilderImpl formInstanceBuilder334 = createBinder(formDefinitions334);
            Document document334 = newDocument();
            formInstanceBuilder334.buildFormInstance(new BindingSourceImpl("repr"), document334, formDefinitions334.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml341 = "<?xml version='1.0'?>\n";
            xml341 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml341 += "<ns1:single-element id='id' type='optional'>";
            xml341 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
            xml341 += "</ns1:element>";
            xml341 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml341 += "</ns1:element>";
            xml341 += "</ns1:single-element>";
            xml341 += "</ns1:form>";
            FormDefinitions formDefinitions341 = createFormDefinitions(xml341);
            FormInstanceBuilderImpl formInstanceBuilder341 = createBinder(formDefinitions341);
            Document document341 = newDocument();
            formInstanceBuilder341.buildFormInstance(new BindingSourceImpl("repr"), document341, formDefinitions341.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml342 = "<?xml version='1.0'?>\n";
            xml342 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml342 += "<ns1:single-element id='id' type='optional'>";
            xml342 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
            xml342 += "</ns1:element>";
            xml342 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml342 += "</ns1:element>";
            xml342 += "</ns1:single-element>";
            xml342 += "</ns1:form>";
            FormDefinitions formDefinitions342 = createFormDefinitions(xml342);
            FormInstanceBuilderImpl formInstanceBuilder342 = createBinder(formDefinitions342);
            Document document342 = newDocument();
            formInstanceBuilder342.buildFormInstance(new BindingSourceImpl("repr"), document342, formDefinitions342.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml343 = "<?xml version='1.0'?>\n";
            xml343 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml343 += "<ns1:single-element id='id' type='optional'>";
            xml343 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml343 += "</ns1:element>";
            xml343 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml343 += "</ns1:element>";
            xml343 += "</ns1:single-element>";
            xml343 += "</ns1:form>";
            FormDefinitions formDefinitions343 = createFormDefinitions(xml343);
            FormInstanceBuilderImpl formInstanceBuilder343 = createBinder(formDefinitions343);
            Document document343 = newDocument();
            formInstanceBuilder343.buildFormInstance(new BindingSourceImpl("repr"), document343, formDefinitions343.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml344 = "<?xml version='1.0'?>\n";
            xml344 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml344 += "<ns1:single-element id='id' type='optional'>";
            xml344 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml344 += "</ns1:element>";
            xml344 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml344 += "</ns1:element>";
            xml344 += "</ns1:single-element>";
            xml344 += "</ns1:form>";
            FormDefinitions formDefinitions344 = createFormDefinitions(xml344);
            FormInstanceBuilderImpl formInstanceBuilder344 = createBinder(formDefinitions344);
            Document document344 = newDocument();
            formInstanceBuilder344.buildFormInstance(new BindingSourceImpl("repr"), document344, formDefinitions344.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        String xml411 = "<?xml version='1.0'?>\n";
        xml411 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml411 += "<ns1:single-element id='id' type='prohibited'>";
        xml411 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml411 += "</ns1:element>";
        xml411 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml411 += "</ns1:element>";
        xml411 += "</ns1:single-element>";
        xml411 += "</ns1:form>";
        FormDefinitions formDefinitions411 = createFormDefinitions(xml411);
        FormInstanceBuilderImpl formInstanceBuilder411 = createBinder(formDefinitions411);
        Document document411 = newDocument();
        formInstanceBuilder411.buildFormInstance(new BindingSourceImpl("repr"), document411, formDefinitions411.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document411)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml412 = "<?xml version='1.0'?>\n";
        xml412 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml412 += "<ns1:single-element id='id' type='prohibited'>";
        xml412 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
        xml412 += "</ns1:element>";
        xml412 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
        xml412 += "</ns1:element>";
        xml412 += "</ns1:single-element>";
        xml412 += "</ns1:form>";
        FormDefinitions formDefinitions412 = createFormDefinitions(xml412);
        FormInstanceBuilderImpl formInstanceBuilder412 = createBinder(formDefinitions412);
        Document document412 = newDocument();
        formInstanceBuilder412.buildFormInstance(new BindingSourceImpl("repr"), document412, formDefinitions412.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document412)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            String xml413 = "<?xml version='1.0'?>\n";
            xml413 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml413 += "<ns1:single-element id='id' type='prohibited'>";
            xml413 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml413 += "</ns1:element>";
            xml413 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
            xml413 += "</ns1:element>";
            xml413 += "</ns1:single-element>";
            xml413 += "</ns1:form>";
            FormDefinitions formDefinitions413 = createFormDefinitions(xml413);
            FormInstanceBuilderImpl formInstanceBuilder413 = createBinder(formDefinitions413);
            Document document413 = newDocument();
            formInstanceBuilder413.buildFormInstance(new BindingSourceImpl("repr"), document413, formDefinitions413.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml414 = "<?xml version='1.0'?>\n";
            xml414 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml414 += "<ns1:single-element id='id' type='prohibited'>";
            xml414 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml414 += "</ns1:element>";
            xml414 += "<ns1:element lookup='lookup' repr='repr2' count='-1'>";
            xml414 += "</ns1:element>";
            xml414 += "</ns1:single-element>";
            xml414 += "</ns1:form>";
            FormDefinitions formDefinitions414 = createFormDefinitions(xml414);
            FormInstanceBuilderImpl formInstanceBuilder414 = createBinder(formDefinitions414);
            Document document414 = newDocument();
            formInstanceBuilder414.buildFormInstance(new BindingSourceImpl("repr"), document414, formDefinitions414.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        String xml421 = "<?xml version='1.0'?>\n";
        xml421 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml421 += "<ns1:single-element id='id' type='prohibited'>";
        xml421 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
        xml421 += "</ns1:element>";
        xml421 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
        xml421 += "</ns1:element>";
        xml421 += "</ns1:single-element>";
        xml421 += "</ns1:form>";
        FormDefinitions formDefinitions421 = createFormDefinitions(xml421);
        FormInstanceBuilderImpl formInstanceBuilder421 = createBinder(formDefinitions421);
        Document document421 = newDocument();
        formInstanceBuilder421.buildFormInstance(new BindingSourceImpl("repr"), document421, formDefinitions421.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document421)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml422 = "<?xml version='1.0'?>\n";
        xml422 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml422 += "<ns1:single-element id='id' type='prohibited'>";
        xml422 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
        xml422 += "</ns1:element>";
        xml422 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
        xml422 += "</ns1:element>";
        xml422 += "</ns1:single-element>";
        xml422 += "</ns1:form>";
        FormDefinitions formDefinitions422 = createFormDefinitions(xml422);
        FormInstanceBuilderImpl formInstanceBuilder422 = createBinder(formDefinitions422);
        Document document422 = newDocument();
        formInstanceBuilder422.buildFormInstance(new BindingSourceImpl("repr"), document422, formDefinitions422.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document422)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            String xml423 = "<?xml version='1.0'?>\n";
            xml423 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml423 += "<ns1:single-element id='id' type='prohibited'>";
            xml423 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml423 += "</ns1:element>";
            xml423 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
            xml423 += "</ns1:element>";
            xml423 += "</ns1:single-element>";
            xml423 += "</ns1:form>";
            FormDefinitions formDefinitions423 = createFormDefinitions(xml423);
            FormInstanceBuilderImpl formInstanceBuilder423 = createBinder(formDefinitions423);
            Document document423 = newDocument();
            formInstanceBuilder423.buildFormInstance(new BindingSourceImpl("repr"), document423, formDefinitions423.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml424 = "<?xml version='1.0'?>\n";
            xml424 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml424 += "<ns1:single-element id='id' type='prohibited'>";
            xml424 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml424 += "</ns1:element>";
            xml424 += "<ns1:element lookup='lookup' repr='repr2' count='0'>";
            xml424 += "</ns1:element>";
            xml424 += "</ns1:single-element>";
            xml424 += "</ns1:form>";
            FormDefinitions formDefinitions424 = createFormDefinitions(xml424);
            FormInstanceBuilderImpl formInstanceBuilder424 = createBinder(formDefinitions424);
            Document document424 = newDocument();
            formInstanceBuilder424.buildFormInstance(new BindingSourceImpl("repr"), document424, formDefinitions424.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml431 = "<?xml version='1.0'?>\n";
            xml431 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml431 += "<ns1:single-element id='id' type='prohibited'>";
            xml431 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
            xml431 += "</ns1:element>";
            xml431 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml431 += "</ns1:element>";
            xml431 += "</ns1:single-element>";
            xml431 += "</ns1:form>";
            FormDefinitions formDefinitions431 = createFormDefinitions(xml431);
            FormInstanceBuilderImpl formInstanceBuilder431 = createBinder(formDefinitions431);
            Document document431 = newDocument();
            formInstanceBuilder431.buildFormInstance(new BindingSourceImpl("repr"), document431, formDefinitions431.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml432 = "<?xml version='1.0'?>\n";
            xml432 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml432 += "<ns1:single-element id='id' type='prohibited'>";
            xml432 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
            xml432 += "</ns1:element>";
            xml432 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml432 += "</ns1:element>";
            xml432 += "</ns1:single-element>";
            xml432 += "</ns1:form>";
            FormDefinitions formDefinitions432 = createFormDefinitions(xml432);
            FormInstanceBuilderImpl formInstanceBuilder432 = createBinder(formDefinitions432);
            Document document432 = newDocument();
            formInstanceBuilder432.buildFormInstance(new BindingSourceImpl("repr"), document432, formDefinitions432.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml433 = "<?xml version='1.0'?>\n";
            xml433 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml433 += "<ns1:single-element id='id' type='prohibited'>";
            xml433 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml433 += "</ns1:element>";
            xml433 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml433 += "</ns1:element>";
            xml433 += "</ns1:single-element>";
            xml433 += "</ns1:form>";
            FormDefinitions formDefinitions433 = createFormDefinitions(xml433);
            FormInstanceBuilderImpl formInstanceBuilder433 = createBinder(formDefinitions433);
            Document document433 = newDocument();
            formInstanceBuilder433.buildFormInstance(new BindingSourceImpl("repr"), document433, formDefinitions433.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml434 = "<?xml version='1.0'?>\n";
            xml434 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml434 += "<ns1:single-element id='id' type='prohibited'>";
            xml434 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml434 += "</ns1:element>";
            xml434 += "<ns1:element lookup='lookup' repr='repr2' count='1'>";
            xml434 += "</ns1:element>";
            xml434 += "</ns1:single-element>";
            xml434 += "</ns1:form>";
            FormDefinitions formDefinitions434 = createFormDefinitions(xml434);
            FormInstanceBuilderImpl formInstanceBuilder434 = createBinder(formDefinitions434);
            Document document434 = newDocument();
            formInstanceBuilder434.buildFormInstance(new BindingSourceImpl("repr"), document434, formDefinitions434.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml441 = "<?xml version='1.0'?>\n";
            xml441 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml441 += "<ns1:single-element id='id' type='prohibited'>";
            xml441 += "<ns1:element lookup='lookup' repr='repr1' count='-1'>";
            xml441 += "</ns1:element>";
            xml441 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml441 += "</ns1:element>";
            xml441 += "</ns1:single-element>";
            xml441 += "</ns1:form>";
            FormDefinitions formDefinitions441 = createFormDefinitions(xml441);
            FormInstanceBuilderImpl formInstanceBuilder441 = createBinder(formDefinitions441);
            Document document441 = newDocument();
            formInstanceBuilder441.buildFormInstance(new BindingSourceImpl("repr"), document441, formDefinitions441.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml442 = "<?xml version='1.0'?>\n";
            xml442 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml442 += "<ns1:single-element id='id' type='prohibited'>";
            xml442 += "<ns1:element lookup='lookup' repr='repr1' count='0'>";
            xml442 += "</ns1:element>";
            xml442 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml442 += "</ns1:element>";
            xml442 += "</ns1:single-element>";
            xml442 += "</ns1:form>";
            FormDefinitions formDefinitions442 = createFormDefinitions(xml442);
            FormInstanceBuilderImpl formInstanceBuilder442 = createBinder(formDefinitions442);
            Document document442 = newDocument();
            formInstanceBuilder442.buildFormInstance(new BindingSourceImpl("repr"), document442, formDefinitions442.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml443 = "<?xml version='1.0'?>\n";
            xml443 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml443 += "<ns1:single-element id='id' type='prohibited'>";
            xml443 += "<ns1:element lookup='lookup' repr='repr1' count='1'>";
            xml443 += "</ns1:element>";
            xml443 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml443 += "</ns1:element>";
            xml443 += "</ns1:single-element>";
            xml443 += "</ns1:form>";
            FormDefinitions formDefinitions443 = createFormDefinitions(xml443);
            FormInstanceBuilderImpl formInstanceBuilder443 = createBinder(formDefinitions443);
            Document document443 = newDocument();
            formInstanceBuilder443.buildFormInstance(new BindingSourceImpl("repr"), document443, formDefinitions443.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml444 = "<?xml version='1.0'?>\n";
            xml444 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml444 += "<ns1:single-element id='id' type='prohibited'>";
            xml444 += "<ns1:element lookup='lookup' repr='repr1' count='2'>";
            xml444 += "</ns1:element>";
            xml444 += "<ns1:element lookup='lookup' repr='repr2' count='2'>";
            xml444 += "</ns1:element>";
            xml444 += "</ns1:single-element>";
            xml444 += "</ns1:form>";
            FormDefinitions formDefinitions444 = createFormDefinitions(xml444);
            FormInstanceBuilderImpl formInstanceBuilder444 = createBinder(formDefinitions444);
            Document document444 = newDocument();
            formInstanceBuilder444.buildFormInstance(new BindingSourceImpl("repr"), document444, formDefinitions444.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@]], {source}form[@:id]/single-element[@id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildFormReferenceInstanceTest() {

    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildOtherNodeInstanceTest() {

    }

    private FormDefinitions createFormDefinitions(final String... xmls) {
        List<FormDefinition> formDefinitions1 = new ArrayList<>();
        for (String xml : xmls) {
            Document document = parse(xml);
            FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader = new FormXmlDefinitionsElementLoader(document.getDocumentElement(), "source");
            List<FormDefinition> formDefinitions2 = formXmlDefinitionsElementLoader.load();
            formDefinitions1.addAll(formDefinitions2);
        }
        FormDefinitions formDefinitions = new FormDefinitions();
        formDefinitions.addFormDefinitions(formDefinitions1);
        return formDefinitions;
    }

    private FormInstanceBuilderImpl createBinder(final FormDefinitions formDefinitions) {
        List<OtherNodeInstanceBuilder> otherNodeInstanceBuilders = ServiceFinder.find(OtherNodeInstanceBuilder.class);
        return new FormInstanceBuilderImpl(formDefinitions, new FormInstanceBinderImpl(), otherNodeInstanceBuilders);
    }

    private Document newDocument() {
        return XmlDocumentBuilder.getDocumentBuilder().newDocument();
    }

}
