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
    public void buildFormInstanceTest() {
        try {
            String xml11 = "<?xml version='1.0'?>\n";
            xml11 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml11 += "</ns1:form>";
            FormDefinitions formDefinitions11 = createFormDefinitions(xml11);
            FormInstanceBuilderImpl formInstanceBuilder11 = createBinder(formDefinitions11);
            Document document11 = newDocument();
            formInstanceBuilder11.buildFormInstance(new BindingSourceImpl(null), document11, formDefinitions11.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Form is not present: {source}form[@:id]]");
        }

        String xml12 = "<?xml version='1.0'?>\n";
        xml12 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml12 += "</ns1:form>";
        FormDefinitions formDefinitions12 = createFormDefinitions(xml12);
        FormInstanceBuilderImpl formInstanceBuilder12 = createBinder(formDefinitions12);
        Document document12 = newDocument();
        formInstanceBuilder12.buildFormInstance(new BindingSourceImpl("source repr"), document12, formDefinitions12.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document12)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml21 = "<?xml version='1.0'?>\n";
        xml21 += "<ns1:form id='id' repr='repr' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml21 += "</ns1:form>";
        FormDefinitions formDefinitions21 = createFormDefinitions(xml21);
        FormInstanceBuilderImpl formInstanceBuilder21 = createBinder(formDefinitions21);
        Document document21 = newDocument();
        formInstanceBuilder21.buildFormInstance(new BindingSourceImpl(null), document21, formDefinitions21.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document21)).isEqualTo("<form group=\"\" id=\"id\" repr=\"repr\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml22 = "<?xml version='1.0'?>\n";
        xml22 += "<ns1:form id='id' repr='repr' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml22 += "</ns1:form>";
        FormDefinitions formDefinitions22 = createFormDefinitions(xml22);
        FormInstanceBuilderImpl formInstanceBuilder22 = createBinder(formDefinitions22);
        Document document22 = newDocument();
        formInstanceBuilder22.buildFormInstance(new BindingSourceImpl("source repr"), document22, formDefinitions22.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document22)).isEqualTo("<form group=\"\" id=\"id\" repr=\"repr\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildAttributeInstanceTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml1 += "<ns1:attribute id='id' lookup='lookup' type='required'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute id=\"id\"/></element></form>");

        try {
            String xml21 = "<?xml version='1.0'?>\n";
            xml21 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml21 += "<ns1:element id='id' lookup='lookup' type='required'>";
            xml21 += "<ns1:attribute id='id' lookup='lookup' type='required' repr='repr' count='0'>";
            xml21 += "</ns1:attribute>";
            xml21 += "</ns1:element>";
            xml21 += "</ns1:form>";
            FormDefinitions formDefinitions21 = createFormDefinitions(xml21);
            FormInstanceBuilderImpl formInstanceBuilder21 = createBinder(formDefinitions21);
            Document document21 = newDocument();
            formInstanceBuilder21.buildFormInstance(new BindingSourceImpl("repr"), document21, formDefinitions21.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required attribute is not present: attribute[@id]], {source}form[@:id]/element[@id]");
        }

        String xml22 = "<?xml version='1.0'?>\n";
        xml22 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml22 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml22 += "<ns1:attribute id='id' lookup='lookup' type='required' repr='repr' count='1'>";
        xml22 += "</ns1:attribute>";
        xml22 += "</ns1:element>";
        xml22 += "</ns1:form>";
        FormDefinitions formDefinitions22 = createFormDefinitions(xml22);
        FormInstanceBuilderImpl formInstanceBuilder22 = createBinder(formDefinitions22);
        Document document22 = newDocument();
        formInstanceBuilder22.buildFormInstance(new BindingSourceImpl("repr"), document22, formDefinitions22.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document22)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute count=\"1\" id=\"id\" repr=\"repr\"/></element></form>");

        String xml31 = "<?xml version='1.0'?>\n";
        xml31 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml31 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml31 += "<ns1:attribute id='id' lookup='lookup' type='optional' repr='repr' count='0'>";
        xml31 += "</ns1:attribute>";
        xml31 += "</ns1:element>";
        xml31 += "</ns1:form>";
        FormDefinitions formDefinitions31 = createFormDefinitions(xml31);
        FormInstanceBuilderImpl formInstanceBuilder31 = createBinder(formDefinitions31);
        Document document31 = newDocument();
        formInstanceBuilder31.buildFormInstance(new BindingSourceImpl("repr"), document31, formDefinitions31.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document31)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");

        String xml32 = "<?xml version='1.0'?>\n";
        xml32 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml32 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml32 += "<ns1:attribute id='id' lookup='lookup' type='optional' repr='repr' count='1'>";
        xml32 += "</ns1:attribute>";
        xml32 += "</ns1:element>";
        xml32 += "</ns1:form>";
        FormDefinitions formDefinitions32 = createFormDefinitions(xml32);
        FormInstanceBuilderImpl formInstanceBuilder32 = createBinder(formDefinitions32);
        Document document32 = newDocument();
        formInstanceBuilder32.buildFormInstance(new BindingSourceImpl("repr"), document32, formDefinitions32.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document32)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute count=\"1\" id=\"id\" repr=\"repr\"/></element></form>");

        String xml41 = "<?xml version='1.0'?>\n";
        xml41 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml41 += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml41 += "<ns1:attribute id='id' lookup='lookup' type='prohibited' repr='repr' count='0'>";
        xml41 += "</ns1:attribute>";
        xml41 += "</ns1:element>";
        xml41 += "</ns1:form>";
        FormDefinitions formDefinitions41 = createFormDefinitions(xml41);
        FormInstanceBuilderImpl formInstanceBuilder41 = createBinder(formDefinitions41);
        Document document41 = newDocument();
        formInstanceBuilder41.buildFormInstance(new BindingSourceImpl("repr"), document41, formDefinitions41.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document41)).isEqualTo("<form group=\"\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");

        try {
            String xml42 = "<?xml version='1.0'?>\n";
            xml42 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml42 += "<ns1:element id='id' lookup='lookup' type='required'>";
            xml42 += "<ns1:attribute id='id' lookup='lookup' type='prohibited' repr='repr' count='1'>";
            xml42 += "</ns1:attribute>";
            xml42 += "</ns1:element>";
            xml42 += "</ns1:form>";
            FormDefinitions formDefinitions42 = createFormDefinitions(xml42);
            FormInstanceBuilderImpl formInstanceBuilder42 = createBinder(formDefinitions42);
            Document document42 = newDocument();
            formInstanceBuilder42.buildFormInstance(new BindingSourceImpl("repr"), document42, formDefinitions42.getFormDefinition("id"));
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
