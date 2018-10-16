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

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.binding.model.BindingSourceImpl;
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
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");
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
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form id=\"id\" repr=\"repr\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' repr='repr' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("source repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form id=\"id\" repr=\"repr\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");
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
        Assertions.assertThat(DocumentWriter.getAsString(document)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute id=\"id\"/></element></form>");
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
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute count=\"1\" id=\"id\" repr=\"repr\"/></element></form>");
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
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");

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
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"><attribute count=\"1\" id=\"id\" repr=\"repr\"/></element></form>");
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
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");

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
    public void buildElementInstanceDefaultTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "<ns1:element id='id' lookup='lookup' type='required'>";
        xml += "</ns1:element>";
        xml += "</ns1:form>";
        FormDefinitions formDefinitions = createFormDefinitions(xml);
        FormInstanceBuilderImpl formInstanceBuilder = createBinder(formDefinitions);
        Document document = newDocument();
        formInstanceBuilder.buildFormInstance(new BindingSourceImpl("repr"), document, formDefinitions.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/></form>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildElementInstanceReprRequiredTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='-1'>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
            FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
            Document document1 = newDocument();
            formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='0'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
            FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
            Document document2 = newDocument();
            formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='1'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        FormDefinitions formDefinitions3 = createFormDefinitions(xml3);
        FormInstanceBuilderImpl formInstanceBuilder3 = createBinder(formDefinitions3);
        Document document3 = newDocument();
        formInstanceBuilder3.buildFormInstance(new BindingSourceImpl("repr"), document3, formDefinitions3.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document3)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "<ns1:element id='id' lookup='lookup' type='required' repr='repr' count='2'>";
            xml4 += "</ns1:element>";
            xml4 += "</ns1:form>";
            FormDefinitions formDefinitions4 = createFormDefinitions(xml4);
            FormInstanceBuilderImpl formInstanceBuilder4 = createBinder(formDefinitions4);
            Document document4 = newDocument();
            formInstanceBuilder4.buildFormInstance(new BindingSourceImpl("repr"), document4, formDefinitions4.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is present more than once: element[@id]], {source}form[@:id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildElementInstanceReprRequiredMultipleTest() {
        try {
            String xml1 = "<?xml version='1.0'?>\n";
            xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml1 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='-1'>";
            xml1 += "</ns1:element>";
            xml1 += "</ns1:form>";
            FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
            FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
            Document document1 = newDocument();
            formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='0'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
            FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
            Document document2 = newDocument();
            formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@id]], {source}form[@:id]");
        }

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='1'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        FormDefinitions formDefinitions3 = createFormDefinitions(xml3);
        FormInstanceBuilderImpl formInstanceBuilder3 = createBinder(formDefinitions3);
        Document document3 = newDocument();
        formInstanceBuilder3.buildFormInstance(new BindingSourceImpl("repr"), document3, formDefinitions3.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document3)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id' lookup='lookup' type='required+' repr='repr' count='2'>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        FormDefinitions formDefinitions4 = createFormDefinitions(xml4);
        FormInstanceBuilderImpl formInstanceBuilder4 = createBinder(formDefinitions4);
        Document document4 = newDocument();
        formInstanceBuilder4.buildFormInstance(new BindingSourceImpl("repr"), document4, formDefinitions4.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document4)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"2\" id=\"id\" repr=\"repr\"/><element count=\"2\" id=\"id\" repr=\"repr\"/></form>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildElementInstanceReprOptionalTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='-1'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='0'>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='1'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        FormDefinitions formDefinitions3 = createFormDefinitions(xml3);
        FormInstanceBuilderImpl formInstanceBuilder3 = createBinder(formDefinitions3);
        Document document3 = newDocument();
        formInstanceBuilder3.buildFormInstance(new BindingSourceImpl("repr"), document3, formDefinitions3.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document3)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "<ns1:element id='id' lookup='lookup' type='optional' repr='repr' count='2'>";
            xml4 += "</ns1:element>";
            xml4 += "</ns1:form>";
            FormDefinitions formDefinitions4 = createFormDefinitions(xml4);
            FormInstanceBuilderImpl formInstanceBuilder4 = createBinder(formDefinitions4);
            Document document4 = newDocument();
            formInstanceBuilder4.buildFormInstance(new BindingSourceImpl("repr"), document4, formDefinitions4.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id]], {source}form[@:id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildElementInstanceReprOptionalMultipleTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='-1'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='0'>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml3 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='1'>";
        xml3 += "</ns1:element>";
        xml3 += "</ns1:form>";
        FormDefinitions formDefinitions3 = createFormDefinitions(xml3);
        FormInstanceBuilderImpl formInstanceBuilder3 = createBinder(formDefinitions3);
        Document document3 = newDocument();
        formInstanceBuilder3.buildFormInstance(new BindingSourceImpl("repr"), document3, formDefinitions3.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document3)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"1\" id=\"id\" repr=\"repr\"/></form>");

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr' count='2'>";
        xml4 += "</ns1:element>";
        xml4 += "</ns1:form>";
        FormDefinitions formDefinitions4 = createFormDefinitions(xml4);
        FormInstanceBuilderImpl formInstanceBuilder4 = createBinder(formDefinitions4);
        Document document4 = newDocument();
        formInstanceBuilder4.buildFormInstance(new BindingSourceImpl("repr"), document4, formDefinitions4.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document4)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"2\" id=\"id\" repr=\"repr\"/><element count=\"2\" id=\"id\" repr=\"repr\"/></form>");
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildElementInstanceReprProhibitedTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='-1'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='0'>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
        FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
        Document document2 = newDocument();
        formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document2)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            String xml3 = "<?xml version='1.0'?>\n";
            xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml3 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='1'>";
            xml3 += "</ns1:element>";
            xml3 += "</ns1:form>";
            FormDefinitions formDefinitions3 = createFormDefinitions(xml3);
            FormInstanceBuilderImpl formInstanceBuilder3 = createBinder(formDefinitions3);
            Document document3 = newDocument();
            formInstanceBuilder3.buildFormInstance(new BindingSourceImpl("repr"), document3, formDefinitions3.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited element is present: element[@id]], {source}form[@:id]");
        }

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml4 += "<ns1:element id='id' lookup='lookup' type='prohibited' repr='repr' count='2'>";
            xml4 += "</ns1:element>";
            xml4 += "</ns1:form>";
            FormDefinitions formDefinitions4 = createFormDefinitions(xml4);
            FormInstanceBuilderImpl formInstanceBuilder4 = createBinder(formDefinitions4);
            Document document4 = newDocument();
            formInstanceBuilder4.buildFormInstance(new BindingSourceImpl("repr"), document4, formDefinitions4.getFormDefinition("id"));
            Assertions.assertThat(DocumentWriter.getAsString(document4)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element count=\"2\" id=\"id\" repr=\"repr\"/><element count=\"2\" id=\"id\" repr=\"repr\"/></form>");
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited element is present: element[@id]], {source}form[@:id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildSingleElementInstanceDefaultTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='id' type='required'>";
        xml1 += "<ns1:element id='id' lookup='lookup'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitions(xml1);
        FormInstanceBuilderImpl formInstanceBuilder1 = createBinder(formDefinitions1);
        Document document1 = newDocument();
        formInstanceBuilder1.buildFormInstance(new BindingSourceImpl("repr"), document1, formDefinitions1.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document1)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element id=\"id\"/></single-element></form>");

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:single-element id='id' type='required'>";
            xml2 += "<ns1:element id='id1' lookup='lookup'>";
            xml2 += "</ns1:element>";
            xml2 += "<ns1:element id='id2' lookup='lookup'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:single-element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitions(xml2);
            FormInstanceBuilderImpl formInstanceBuilder2 = createBinder(formDefinitions2);
            Document document2 = newDocument();
            formInstanceBuilder2.buildFormInstance(new BindingSourceImpl("repr"), document2, formDefinitions2.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildSingleElementInstanceReprRequiredTest() {
        try {
            String xml11 = "<?xml version='1.0'?>\n";
            xml11 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml11 += "<ns1:single-element id='id' type='required'>";
            xml11 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
            xml11 += "</ns1:element>";
            xml11 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
            xml11 += "</ns1:element>";
            xml11 += "</ns1:single-element>";
            xml11 += "</ns1:form>";
            FormDefinitions formDefinitions11 = createFormDefinitions(xml11);
            FormInstanceBuilderImpl formInstanceBuilder11 = createBinder(formDefinitions11);
            Document document11 = newDocument();
            formInstanceBuilder11.buildFormInstance(new BindingSourceImpl("repr"), document11, formDefinitions11.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml12 = "<?xml version='1.0'?>\n";
            xml12 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml12 += "<ns1:single-element id='id' type='required'>";
            xml12 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
            xml12 += "</ns1:element>";
            xml12 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
            xml12 += "</ns1:element>";
            xml12 += "</ns1:single-element>";
            xml12 += "</ns1:form>";
            FormDefinitions formDefinitions12 = createFormDefinitions(xml12);
            FormInstanceBuilderImpl formInstanceBuilder12 = createBinder(formDefinitions12);
            Document document12 = newDocument();
            formInstanceBuilder12.buildFormInstance(new BindingSourceImpl("repr"), document12, formDefinitions12.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        String xml13 = "<?xml version='1.0'?>\n";
        xml13 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml13 += "<ns1:single-element id='id' type='required'>";
        xml13 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
        xml13 += "</ns1:element>";
        xml13 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml13 += "</ns1:element>";
        xml13 += "</ns1:single-element>";
        xml13 += "</ns1:form>";
        FormDefinitions formDefinitions13 = createFormDefinitions(xml13);
        FormInstanceBuilderImpl formInstanceBuilder13 = createBinder(formDefinitions13);
        Document document13 = newDocument();
        formInstanceBuilder13.buildFormInstance(new BindingSourceImpl("repr"), document13, formDefinitions13.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document13)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml14 = "<?xml version='1.0'?>\n";
            xml14 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml14 += "<ns1:single-element id='id' type='required'>";
            xml14 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml14 += "</ns1:element>";
            xml14 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
            xml14 += "</ns1:element>";
            xml14 += "</ns1:single-element>";
            xml14 += "</ns1:form>";
            FormDefinitions formDefinitions14 = createFormDefinitions(xml14);
            FormInstanceBuilderImpl formInstanceBuilder14 = createBinder(formDefinitions14);
            Document document14 = newDocument();
            formInstanceBuilder14.buildFormInstance(new BindingSourceImpl("repr"), document14, formDefinitions14.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        String xml15 = "<?xml version='1.0'?>\n";
        xml15 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml15 += "<ns1:single-element id='id' type='required'>";
        xml15 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
        xml15 += "</ns1:element>";
        xml15 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml15 += "</ns1:element>";
        xml15 += "</ns1:single-element>";
        xml15 += "</ns1:form>";
        FormDefinitions formDefinitions15 = createFormDefinitions(xml15);
        FormInstanceBuilderImpl formInstanceBuilder15 = createBinder(formDefinitions15);
        Document document15 = newDocument();
        formInstanceBuilder15.buildFormInstance(new BindingSourceImpl("repr"), document15, formDefinitions15.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document15)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"2\" id=\"id1\" repr=\"repr1\"/><element count=\"2\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml21 = "<?xml version='1.0'?>\n";
            xml21 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml21 += "<ns1:single-element id='id' type='required'>";
            xml21 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
            xml21 += "</ns1:element>";
            xml21 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
            xml21 += "</ns1:element>";
            xml21 += "</ns1:single-element>";
            xml21 += "</ns1:form>";
            FormDefinitions formDefinitions21 = createFormDefinitions(xml21);
            FormInstanceBuilderImpl formInstanceBuilder21 = createBinder(formDefinitions21);
            Document document21 = newDocument();
            formInstanceBuilder21.buildFormInstance(new BindingSourceImpl("repr"), document21, formDefinitions21.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml22 = "<?xml version='1.0'?>\n";
            xml22 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml22 += "<ns1:single-element id='id' type='required'>";
            xml22 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
            xml22 += "</ns1:element>";
            xml22 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
            xml22 += "</ns1:element>";
            xml22 += "</ns1:single-element>";
            xml22 += "</ns1:form>";
            FormDefinitions formDefinitions22 = createFormDefinitions(xml22);
            FormInstanceBuilderImpl formInstanceBuilder22 = createBinder(formDefinitions22);
            Document document22 = newDocument();
            formInstanceBuilder22.buildFormInstance(new BindingSourceImpl("repr"), document22, formDefinitions22.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required single element is not present: single-element[@id]], {source}form[@:id]");
        }

        String xml23 = "<?xml version='1.0'?>\n";
        xml23 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml23 += "<ns1:single-element id='id' type='required'>";
        xml23 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
        xml23 += "</ns1:element>";
        xml23 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml23 += "</ns1:element>";
        xml23 += "</ns1:single-element>";
        xml23 += "</ns1:form>";
        FormDefinitions formDefinitions23 = createFormDefinitions(xml23);
        FormInstanceBuilderImpl formInstanceBuilder23 = createBinder(formDefinitions23);
        Document document23 = newDocument();
        formInstanceBuilder23.buildFormInstance(new BindingSourceImpl("repr"), document23, formDefinitions23.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document23)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml24 = "<?xml version='1.0'?>\n";
            xml24 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml24 += "<ns1:single-element id='id' type='required'>";
            xml24 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml24 += "</ns1:element>";
            xml24 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
            xml24 += "</ns1:element>";
            xml24 += "</ns1:single-element>";
            xml24 += "</ns1:form>";
            FormDefinitions formDefinitions24 = createFormDefinitions(xml24);
            FormInstanceBuilderImpl formInstanceBuilder24 = createBinder(formDefinitions24);
            Document document24 = newDocument();
            formInstanceBuilder24.buildFormInstance(new BindingSourceImpl("repr"), document24, formDefinitions24.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        String xml25 = "<?xml version='1.0'?>\n";
        xml25 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml25 += "<ns1:single-element id='id' type='required'>";
        xml25 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
        xml25 += "</ns1:element>";
        xml25 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml25 += "</ns1:element>";
        xml25 += "</ns1:single-element>";
        xml25 += "</ns1:form>";
        FormDefinitions formDefinitions25 = createFormDefinitions(xml25);
        FormInstanceBuilderImpl formInstanceBuilder25 = createBinder(formDefinitions25);
        Document document25 = newDocument();
        formInstanceBuilder25.buildFormInstance(new BindingSourceImpl("repr"), document25, formDefinitions25.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document25)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"2\" id=\"id1\" repr=\"repr1\"/><element count=\"2\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        String xml31 = "<?xml version='1.0'?>\n";
        xml31 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml31 += "<ns1:single-element id='id' type='required'>";
        xml31 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
        xml31 += "</ns1:element>";
        xml31 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
        xml31 += "</ns1:element>";
        xml31 += "</ns1:single-element>";
        xml31 += "</ns1:form>";
        FormDefinitions formDefinitions31 = createFormDefinitions(xml31);
        FormInstanceBuilderImpl formInstanceBuilder31 = createBinder(formDefinitions31);
        Document document31 = newDocument();
        formInstanceBuilder31.buildFormInstance(new BindingSourceImpl("repr"), document31, formDefinitions31.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document31)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id2\" repr=\"repr2\"/></single-element></form>");

        String xml32 = "<?xml version='1.0'?>\n";
        xml32 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml32 += "<ns1:single-element id='id' type='required'>";
        xml32 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
        xml32 += "</ns1:element>";
        xml32 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
        xml32 += "</ns1:element>";
        xml32 += "</ns1:single-element>";
        xml32 += "</ns1:form>";
        FormDefinitions formDefinitions32 = createFormDefinitions(xml32);
        FormInstanceBuilderImpl formInstanceBuilder32 = createBinder(formDefinitions32);
        Document document32 = newDocument();
        formInstanceBuilder32.buildFormInstance(new BindingSourceImpl("repr"), document32, formDefinitions32.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document32)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id2\" repr=\"repr2\"/></single-element></form>");

        try {
            String xml33 = "<?xml version='1.0'?>\n";
            xml33 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml33 += "<ns1:single-element id='id' type='required'>";
            xml33 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml33 += "</ns1:element>";
            xml33 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml33 += "</ns1:element>";
            xml33 += "</ns1:single-element>";
            xml33 += "</ns1:form>";
            FormDefinitions formDefinitions33 = createFormDefinitions(xml33);
            FormInstanceBuilderImpl formInstanceBuilder33 = createBinder(formDefinitions33);
            Document document33 = newDocument();
            formInstanceBuilder33.buildFormInstance(new BindingSourceImpl("repr"), document33, formDefinitions33.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml34 = "<?xml version='1.0'?>\n";
            xml34 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml34 += "<ns1:single-element id='id' type='required'>";
            xml34 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml34 += "</ns1:element>";
            xml34 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml34 += "</ns1:element>";
            xml34 += "</ns1:single-element>";
            xml34 += "</ns1:form>";
            FormDefinitions formDefinitions34 = createFormDefinitions(xml34);
            FormInstanceBuilderImpl formInstanceBuilder34 = createBinder(formDefinitions34);
            Document document34 = newDocument();
            formInstanceBuilder34.buildFormInstance(new BindingSourceImpl("repr"), document34, formDefinitions34.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml35 = "<?xml version='1.0'?>\n";
            xml35 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml35 += "<ns1:single-element id='id' type='required'>";
            xml35 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml35 += "</ns1:element>";
            xml35 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml35 += "</ns1:element>";
            xml35 += "</ns1:single-element>";
            xml35 += "</ns1:form>";
            FormDefinitions formDefinitions35 = createFormDefinitions(xml35);
            FormInstanceBuilderImpl formInstanceBuilder35 = createBinder(formDefinitions35);
            Document document35 = newDocument();
            formInstanceBuilder35.buildFormInstance(new BindingSourceImpl("repr"), document35, formDefinitions35.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml41 = "<?xml version='1.0'?>\n";
            xml41 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml41 += "<ns1:single-element id='id' type='required'>";
            xml41 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
            xml41 += "</ns1:element>";
            xml41 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml41 += "</ns1:element>";
            xml41 += "</ns1:single-element>";
            xml41 += "</ns1:form>";
            FormDefinitions formDefinitions41 = createFormDefinitions(xml41);
            FormInstanceBuilderImpl formInstanceBuilder41 = createBinder(formDefinitions41);
            Document document41 = newDocument();
            formInstanceBuilder41.buildFormInstance(new BindingSourceImpl("repr"), document41, formDefinitions41.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml42 = "<?xml version='1.0'?>\n";
            xml42 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml42 += "<ns1:single-element id='id' type='required'>";
            xml42 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
            xml42 += "</ns1:element>";
            xml42 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml42 += "</ns1:element>";
            xml42 += "</ns1:single-element>";
            xml42 += "</ns1:form>";
            FormDefinitions formDefinitions42 = createFormDefinitions(xml42);
            FormInstanceBuilderImpl formInstanceBuilder42 = createBinder(formDefinitions42);
            Document document42 = newDocument();
            formInstanceBuilder42.buildFormInstance(new BindingSourceImpl("repr"), document42, formDefinitions42.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml43 = "<?xml version='1.0'?>\n";
            xml43 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml43 += "<ns1:single-element id='id' type='required'>";
            xml43 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml43 += "</ns1:element>";
            xml43 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml43 += "</ns1:element>";
            xml43 += "</ns1:single-element>";
            xml43 += "</ns1:form>";
            FormDefinitions formDefinitions43 = createFormDefinitions(xml43);
            FormInstanceBuilderImpl formInstanceBuilder43 = createBinder(formDefinitions43);
            Document document43 = newDocument();
            formInstanceBuilder43.buildFormInstance(new BindingSourceImpl("repr"), document43, formDefinitions43.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml44 = "<?xml version='1.0'?>\n";
            xml44 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml44 += "<ns1:single-element id='id' type='required'>";
            xml44 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml44 += "</ns1:element>";
            xml44 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml44 += "</ns1:element>";
            xml44 += "</ns1:single-element>";
            xml44 += "</ns1:form>";
            FormDefinitions formDefinitions44 = createFormDefinitions(xml44);
            FormInstanceBuilderImpl formInstanceBuilder44 = createBinder(formDefinitions44);
            Document document44 = newDocument();
            formInstanceBuilder44.buildFormInstance(new BindingSourceImpl("repr"), document44, formDefinitions44.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml45 = "<?xml version='1.0'?>\n";
            xml45 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml45 += "<ns1:single-element id='id' type='required'>";
            xml45 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml45 += "</ns1:element>";
            xml45 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml45 += "</ns1:element>";
            xml45 += "</ns1:single-element>";
            xml45 += "</ns1:form>";
            FormDefinitions formDefinitions45 = createFormDefinitions(xml45);
            FormInstanceBuilderImpl formInstanceBuilder45 = createBinder(formDefinitions45);
            Document document45 = newDocument();
            formInstanceBuilder45.buildFormInstance(new BindingSourceImpl("repr"), document45, formDefinitions45.getFormDefinition("id"));
            Assertions.assertThat(DocumentWriter.getAsString(document45)).isEqualTo("");
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildSingleElementInstanceReprOptionalTest() {
        String xml11 = "<?xml version='1.0'?>\n";
        xml11 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml11 += "<ns1:single-element id='id' type='optional'>";
        xml11 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
        xml11 += "</ns1:element>";
        xml11 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml11 += "</ns1:element>";
        xml11 += "</ns1:single-element>";
        xml11 += "</ns1:form>";
        FormDefinitions formDefinitions11 = createFormDefinitions(xml11);
        FormInstanceBuilderImpl formInstanceBuilder11 = createBinder(formDefinitions11);
        Document document11 = newDocument();
        formInstanceBuilder11.buildFormInstance(new BindingSourceImpl("repr"), document11, formDefinitions11.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document11)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml12 = "<?xml version='1.0'?>\n";
        xml12 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml12 += "<ns1:single-element id='id' type='optional'>";
        xml12 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
        xml12 += "</ns1:element>";
        xml12 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml12 += "</ns1:element>";
        xml12 += "</ns1:single-element>";
        xml12 += "</ns1:form>";
        FormDefinitions formDefinitions12 = createFormDefinitions(xml12);
        FormInstanceBuilderImpl formInstanceBuilder12 = createBinder(formDefinitions12);
        Document document12 = newDocument();
        formInstanceBuilder12.buildFormInstance(new BindingSourceImpl("repr"), document12, formDefinitions12.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document12)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml13 = "<?xml version='1.0'?>\n";
        xml13 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml13 += "<ns1:single-element id='id' type='optional'>";
        xml13 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
        xml13 += "</ns1:element>";
        xml13 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml13 += "</ns1:element>";
        xml13 += "</ns1:single-element>";
        xml13 += "</ns1:form>";
        FormDefinitions formDefinitions13 = createFormDefinitions(xml13);
        FormInstanceBuilderImpl formInstanceBuilder13 = createBinder(formDefinitions13);
        Document document13 = newDocument();
        formInstanceBuilder13.buildFormInstance(new BindingSourceImpl("repr"), document13, formDefinitions13.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document13)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml14 = "<?xml version='1.0'?>\n";
            xml14 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml14 += "<ns1:single-element id='id' type='optional'>";
            xml14 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml14 += "</ns1:element>";
            xml14 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
            xml14 += "</ns1:element>";
            xml14 += "</ns1:single-element>";
            xml14 += "</ns1:form>";
            FormDefinitions formDefinitions14 = createFormDefinitions(xml14);
            FormInstanceBuilderImpl formInstanceBuilder14 = createBinder(formDefinitions14);
            Document document14 = newDocument();
            formInstanceBuilder14.buildFormInstance(new BindingSourceImpl("repr"), document14, formDefinitions14.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        String xml15 = "<?xml version='1.0'?>\n";
        xml15 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml15 += "<ns1:single-element id='id' type='optional'>";
        xml15 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
        xml15 += "</ns1:element>";
        xml15 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml15 += "</ns1:element>";
        xml15 += "</ns1:single-element>";
        xml15 += "</ns1:form>";
        FormDefinitions formDefinitions15 = createFormDefinitions(xml15);
        FormInstanceBuilderImpl formInstanceBuilder15 = createBinder(formDefinitions15);
        Document document15 = newDocument();
        formInstanceBuilder15.buildFormInstance(new BindingSourceImpl("repr"), document15, formDefinitions15.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document15)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"2\" id=\"id1\" repr=\"repr1\"/><element count=\"2\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        String xml21 = "<?xml version='1.0'?>\n";
        xml21 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml21 += "<ns1:single-element id='id' type='optional'>";
        xml21 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
        xml21 += "</ns1:element>";
        xml21 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml21 += "</ns1:element>";
        xml21 += "</ns1:single-element>";
        xml21 += "</ns1:form>";
        FormDefinitions formDefinitions21 = createFormDefinitions(xml21);
        FormInstanceBuilderImpl formInstanceBuilder21 = createBinder(formDefinitions21);
        Document document21 = newDocument();
        formInstanceBuilder21.buildFormInstance(new BindingSourceImpl("repr"), document21, formDefinitions21.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document21)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml22 = "<?xml version='1.0'?>\n";
        xml22 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml22 += "<ns1:single-element id='id' type='optional'>";
        xml22 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
        xml22 += "</ns1:element>";
        xml22 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml22 += "</ns1:element>";
        xml22 += "</ns1:single-element>";
        xml22 += "</ns1:form>";
        FormDefinitions formDefinitions22 = createFormDefinitions(xml22);
        FormInstanceBuilderImpl formInstanceBuilder22 = createBinder(formDefinitions22);
        Document document22 = newDocument();
        formInstanceBuilder22.buildFormInstance(new BindingSourceImpl("repr"), document22, formDefinitions22.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document22)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml23 = "<?xml version='1.0'?>\n";
        xml23 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml23 += "<ns1:single-element id='id' type='optional'>";
        xml23 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
        xml23 += "</ns1:element>";
        xml23 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml23 += "</ns1:element>";
        xml23 += "</ns1:single-element>";
        xml23 += "</ns1:form>";
        FormDefinitions formDefinitions23 = createFormDefinitions(xml23);
        FormInstanceBuilderImpl formInstanceBuilder23 = createBinder(formDefinitions23);
        Document document23 = newDocument();
        formInstanceBuilder23.buildFormInstance(new BindingSourceImpl("repr"), document23, formDefinitions23.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document23)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        try {
            String xml24 = "<?xml version='1.0'?>\n";
            xml24 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml24 += "<ns1:single-element id='id' type='optional'>";
            xml24 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml24 += "</ns1:element>";
            xml24 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
            xml24 += "</ns1:element>";
            xml24 += "</ns1:single-element>";
            xml24 += "</ns1:form>";
            FormDefinitions formDefinitions24 = createFormDefinitions(xml24);
            FormInstanceBuilderImpl formInstanceBuilder24 = createBinder(formDefinitions24);
            Document document24 = newDocument();
            formInstanceBuilder24.buildFormInstance(new BindingSourceImpl("repr"), document24, formDefinitions24.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        String xml25 = "<?xml version='1.0'?>\n";
        xml25 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml25 += "<ns1:single-element id='id' type='optional'>";
        xml25 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
        xml25 += "</ns1:element>";
        xml25 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml25 += "</ns1:element>";
        xml25 += "</ns1:single-element>";
        xml25 += "</ns1:form>";
        FormDefinitions formDefinitions25 = createFormDefinitions(xml25);
        FormInstanceBuilderImpl formInstanceBuilder25 = createBinder(formDefinitions25);
        Document document25 = newDocument();
        formInstanceBuilder25.buildFormInstance(new BindingSourceImpl("repr"), document25, formDefinitions25.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document25)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"2\" id=\"id1\" repr=\"repr1\"/><element count=\"2\" id=\"id1\" repr=\"repr1\"/></single-element></form>");

        String xml31 = "<?xml version='1.0'?>\n";
        xml31 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml31 += "<ns1:single-element id='id' type='optional'>";
        xml31 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
        xml31 += "</ns1:element>";
        xml31 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
        xml31 += "</ns1:element>";
        xml31 += "</ns1:single-element>";
        xml31 += "</ns1:form>";
        FormDefinitions formDefinitions31 = createFormDefinitions(xml31);
        FormInstanceBuilderImpl formInstanceBuilder31 = createBinder(formDefinitions31);
        Document document31 = newDocument();
        formInstanceBuilder31.buildFormInstance(new BindingSourceImpl("repr"), document31, formDefinitions31.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document31)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id2\" repr=\"repr2\"/></single-element></form>");

        String xml32 = "<?xml version='1.0'?>\n";
        xml32 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml32 += "<ns1:single-element id='id' type='optional'>";
        xml32 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
        xml32 += "</ns1:element>";
        xml32 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
        xml32 += "</ns1:element>";
        xml32 += "</ns1:single-element>";
        xml32 += "</ns1:form>";
        FormDefinitions formDefinitions32 = createFormDefinitions(xml32);
        FormInstanceBuilderImpl formInstanceBuilder32 = createBinder(formDefinitions32);
        Document document32 = newDocument();
        formInstanceBuilder32.buildFormInstance(new BindingSourceImpl("repr"), document32, formDefinitions32.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document32)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><single-element id=\"id\"><element count=\"1\" id=\"id2\" repr=\"repr2\"/></single-element></form>");

        try {
            String xml33 = "<?xml version='1.0'?>\n";
            xml33 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml33 += "<ns1:single-element id='id' type='optional'>";
            xml33 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml33 += "</ns1:element>";
            xml33 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml33 += "</ns1:element>";
            xml33 += "</ns1:single-element>";
            xml33 += "</ns1:form>";
            FormDefinitions formDefinitions33 = createFormDefinitions(xml33);
            FormInstanceBuilderImpl formInstanceBuilder33 = createBinder(formDefinitions33);
            Document document33 = newDocument();
            formInstanceBuilder33.buildFormInstance(new BindingSourceImpl("repr"), document33, formDefinitions33.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml34 = "<?xml version='1.0'?>\n";
            xml34 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml34 += "<ns1:single-element id='id' type='optional'>";
            xml34 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml34 += "</ns1:element>";
            xml34 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml34 += "</ns1:element>";
            xml34 += "</ns1:single-element>";
            xml34 += "</ns1:form>";
            FormDefinitions formDefinitions34 = createFormDefinitions(xml34);
            FormInstanceBuilderImpl formInstanceBuilder34 = createBinder(formDefinitions34);
            Document document34 = newDocument();
            formInstanceBuilder34.buildFormInstance(new BindingSourceImpl("repr"), document34, formDefinitions34.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml35 = "<?xml version='1.0'?>\n";
            xml35 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml35 += "<ns1:single-element id='id' type='optional'>";
            xml35 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml35 += "</ns1:element>";
            xml35 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml35 += "</ns1:element>";
            xml35 += "</ns1:single-element>";
            xml35 += "</ns1:form>";
            FormDefinitions formDefinitions35 = createFormDefinitions(xml35);
            FormInstanceBuilderImpl formInstanceBuilder35 = createBinder(formDefinitions35);
            Document document35 = newDocument();
            formInstanceBuilder35.buildFormInstance(new BindingSourceImpl("repr"), document35, formDefinitions35.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml41 = "<?xml version='1.0'?>\n";
            xml41 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml41 += "<ns1:single-element id='id' type='optional'>";
            xml41 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
            xml41 += "</ns1:element>";
            xml41 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml41 += "</ns1:element>";
            xml41 += "</ns1:single-element>";
            xml41 += "</ns1:form>";
            FormDefinitions formDefinitions41 = createFormDefinitions(xml41);
            FormInstanceBuilderImpl formInstanceBuilder41 = createBinder(formDefinitions41);
            Document document41 = newDocument();
            formInstanceBuilder41.buildFormInstance(new BindingSourceImpl("repr"), document41, formDefinitions41.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml42 = "<?xml version='1.0'?>\n";
            xml42 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml42 += "<ns1:single-element id='id' type='optional'>";
            xml42 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
            xml42 += "</ns1:element>";
            xml42 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml42 += "</ns1:element>";
            xml42 += "</ns1:single-element>";
            xml42 += "</ns1:form>";
            FormDefinitions formDefinitions42 = createFormDefinitions(xml42);
            FormInstanceBuilderImpl formInstanceBuilder42 = createBinder(formDefinitions42);
            Document document42 = newDocument();
            formInstanceBuilder42.buildFormInstance(new BindingSourceImpl("repr"), document42, formDefinitions42.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml43 = "<?xml version='1.0'?>\n";
            xml43 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml43 += "<ns1:single-element id='id' type='optional'>";
            xml43 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml43 += "</ns1:element>";
            xml43 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml43 += "</ns1:element>";
            xml43 += "</ns1:single-element>";
            xml43 += "</ns1:form>";
            FormDefinitions formDefinitions43 = createFormDefinitions(xml43);
            FormInstanceBuilderImpl formInstanceBuilder43 = createBinder(formDefinitions43);
            Document document43 = newDocument();
            formInstanceBuilder43.buildFormInstance(new BindingSourceImpl("repr"), document43, formDefinitions43.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml44 = "<?xml version='1.0'?>\n";
            xml44 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml44 += "<ns1:single-element id='id' type='optional'>";
            xml44 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml44 += "</ns1:element>";
            xml44 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml44 += "</ns1:element>";
            xml44 += "</ns1:single-element>";
            xml44 += "</ns1:form>";
            FormDefinitions formDefinitions44 = createFormDefinitions(xml44);
            FormInstanceBuilderImpl formInstanceBuilder44 = createBinder(formDefinitions44);
            Document document44 = newDocument();
            formInstanceBuilder44.buildFormInstance(new BindingSourceImpl("repr"), document44, formDefinitions44.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml45 = "<?xml version='1.0'?>\n";
            xml45 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml45 += "<ns1:single-element id='id' type='optional'>";
            xml45 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml45 += "</ns1:element>";
            xml45 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml45 += "</ns1:element>";
            xml45 += "</ns1:single-element>";
            xml45 += "</ns1:form>";
            FormDefinitions formDefinitions45 = createFormDefinitions(xml45);
            FormInstanceBuilderImpl formInstanceBuilder45 = createBinder(formDefinitions45);
            Document document45 = newDocument();
            formInstanceBuilder45.buildFormInstance(new BindingSourceImpl("repr"), document45, formDefinitions45.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }
    }

    /**
     * {@link FormInstanceBuilderImpl} class test.
     */
    @Test
    public void buildSingleElementInstanceReprProhibitedTest() {
        String xml11 = "<?xml version='1.0'?>\n";
        xml11 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml11 += "<ns1:single-element id='id' type='prohibited'>";
        xml11 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
        xml11 += "</ns1:element>";
        xml11 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml11 += "</ns1:element>";
        xml11 += "</ns1:single-element>";
        xml11 += "</ns1:form>";
        FormDefinitions formDefinitions11 = createFormDefinitions(xml11);
        FormInstanceBuilderImpl formInstanceBuilder11 = createBinder(formDefinitions11);
        Document document11 = newDocument();
        formInstanceBuilder11.buildFormInstance(new BindingSourceImpl("repr"), document11, formDefinitions11.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document11)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml12 = "<?xml version='1.0'?>\n";
        xml12 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml12 += "<ns1:single-element id='id' type='prohibited'>";
        xml12 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
        xml12 += "</ns1:element>";
        xml12 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
        xml12 += "</ns1:element>";
        xml12 += "</ns1:single-element>";
        xml12 += "</ns1:form>";
        FormDefinitions formDefinitions12 = createFormDefinitions(xml12);
        FormInstanceBuilderImpl formInstanceBuilder12 = createBinder(formDefinitions12);
        Document document12 = newDocument();
        formInstanceBuilder12.buildFormInstance(new BindingSourceImpl("repr"), document12, formDefinitions12.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document12)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            String xml13 = "<?xml version='1.0'?>\n";
            xml13 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml13 += "<ns1:single-element id='id' type='prohibited'>";
            xml13 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml13 += "</ns1:element>";
            xml13 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
            xml13 += "</ns1:element>";
            xml13 += "</ns1:single-element>";
            xml13 += "</ns1:form>";
            FormDefinitions formDefinitions13 = createFormDefinitions(xml13);
            FormInstanceBuilderImpl formInstanceBuilder13 = createBinder(formDefinitions13);
            Document document13 = newDocument();
            formInstanceBuilder13.buildFormInstance(new BindingSourceImpl("repr"), document13, formDefinitions13.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml14 = "<?xml version='1.0'?>\n";
            xml14 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml14 += "<ns1:single-element id='id' type='prohibited'>";
            xml14 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml14 += "</ns1:element>";
            xml14 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
            xml14 += "</ns1:element>";
            xml14 += "</ns1:single-element>";
            xml14 += "</ns1:form>";
            FormDefinitions formDefinitions14 = createFormDefinitions(xml14);
            FormInstanceBuilderImpl formInstanceBuilder14 = createBinder(formDefinitions14);
            Document document14 = newDocument();
            formInstanceBuilder14.buildFormInstance(new BindingSourceImpl("repr"), document14, formDefinitions14.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml15 = "<?xml version='1.0'?>\n";
            xml15 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml15 += "<ns1:single-element id='id' type='prohibited'>";
            xml15 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml15 += "</ns1:element>";
            xml15 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='-1'>";
            xml15 += "</ns1:element>";
            xml15 += "</ns1:single-element>";
            xml15 += "</ns1:form>";
            FormDefinitions formDefinitions15 = createFormDefinitions(xml15);
            FormInstanceBuilderImpl formInstanceBuilder15 = createBinder(formDefinitions15);
            Document document15 = newDocument();
            formInstanceBuilder15.buildFormInstance(new BindingSourceImpl("repr"), document15, formDefinitions15.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        String xml21 = "<?xml version='1.0'?>\n";
        xml21 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml21 += "<ns1:single-element id='id' type='prohibited'>";
        xml21 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
        xml21 += "</ns1:element>";
        xml21 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml21 += "</ns1:element>";
        xml21 += "</ns1:single-element>";
        xml21 += "</ns1:form>";
        FormDefinitions formDefinitions21 = createFormDefinitions(xml21);
        FormInstanceBuilderImpl formInstanceBuilder21 = createBinder(formDefinitions21);
        Document document21 = newDocument();
        formInstanceBuilder21.buildFormInstance(new BindingSourceImpl("repr"), document21, formDefinitions21.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document21)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        String xml22 = "<?xml version='1.0'?>\n";
        xml22 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml22 += "<ns1:single-element id='id' type='prohibited'>";
        xml22 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
        xml22 += "</ns1:element>";
        xml22 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
        xml22 += "</ns1:element>";
        xml22 += "</ns1:single-element>";
        xml22 += "</ns1:form>";
        FormDefinitions formDefinitions22 = createFormDefinitions(xml22);
        FormInstanceBuilderImpl formInstanceBuilder22 = createBinder(formDefinitions22);
        Document document22 = newDocument();
        formInstanceBuilder22.buildFormInstance(new BindingSourceImpl("repr"), document22, formDefinitions22.getFormDefinition("id"));
        Assertions.assertThat(DocumentWriter.getAsString(document22)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            String xml23 = "<?xml version='1.0'?>\n";
            xml23 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml23 += "<ns1:single-element id='id' type='prohibited'>";
            xml23 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml23 += "</ns1:element>";
            xml23 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
            xml23 += "</ns1:element>";
            xml23 += "</ns1:single-element>";
            xml23 += "</ns1:form>";
            FormDefinitions formDefinitions23 = createFormDefinitions(xml23);
            FormInstanceBuilderImpl formInstanceBuilder23 = createBinder(formDefinitions23);
            Document document23 = newDocument();
            formInstanceBuilder23.buildFormInstance(new BindingSourceImpl("repr"), document23, formDefinitions23.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml24 = "<?xml version='1.0'?>\n";
            xml24 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml24 += "<ns1:single-element id='id' type='prohibited'>";
            xml24 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml24 += "</ns1:element>";
            xml24 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
            xml24 += "</ns1:element>";
            xml24 += "</ns1:single-element>";
            xml24 += "</ns1:form>";
            FormDefinitions formDefinitions24 = createFormDefinitions(xml24);
            FormInstanceBuilderImpl formInstanceBuilder24 = createBinder(formDefinitions24);
            Document document24 = newDocument();
            formInstanceBuilder24.buildFormInstance(new BindingSourceImpl("repr"), document24, formDefinitions24.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml25 = "<?xml version='1.0'?>\n";
            xml25 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml25 += "<ns1:single-element id='id' type='prohibited'>";
            xml25 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml25 += "</ns1:element>";
            xml25 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='0'>";
            xml25 += "</ns1:element>";
            xml25 += "</ns1:single-element>";
            xml25 += "</ns1:form>";
            FormDefinitions formDefinitions25 = createFormDefinitions(xml25);
            FormInstanceBuilderImpl formInstanceBuilder25 = createBinder(formDefinitions25);
            Document document25 = newDocument();
            formInstanceBuilder25.buildFormInstance(new BindingSourceImpl("repr"), document25, formDefinitions25.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml31 = "<?xml version='1.0'?>\n";
            xml31 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml31 += "<ns1:single-element id='id' type='prohibited'>";
            xml31 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
            xml31 += "</ns1:element>";
            xml31 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml31 += "</ns1:element>";
            xml31 += "</ns1:single-element>";
            xml31 += "</ns1:form>";
            FormDefinitions formDefinitions31 = createFormDefinitions(xml31);
            FormInstanceBuilderImpl formInstanceBuilder31 = createBinder(formDefinitions31);
            Document document31 = newDocument();
            formInstanceBuilder31.buildFormInstance(new BindingSourceImpl("repr"), document31, formDefinitions31.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml32 = "<?xml version='1.0'?>\n";
            xml32 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml32 += "<ns1:single-element id='id' type='prohibited'>";
            xml32 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
            xml32 += "</ns1:element>";
            xml32 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml32 += "</ns1:element>";
            xml32 += "</ns1:single-element>";
            xml32 += "</ns1:form>";
            FormDefinitions formDefinitions32 = createFormDefinitions(xml32);
            FormInstanceBuilderImpl formInstanceBuilder32 = createBinder(formDefinitions32);
            Document document32 = newDocument();
            formInstanceBuilder32.buildFormInstance(new BindingSourceImpl("repr"), document32, formDefinitions32.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Prohibited single element is present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml33 = "<?xml version='1.0'?>\n";
            xml33 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml33 += "<ns1:single-element id='id' type='prohibited'>";
            xml33 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml33 += "</ns1:element>";
            xml33 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml33 += "</ns1:element>";
            xml33 += "</ns1:single-element>";
            xml33 += "</ns1:form>";
            FormDefinitions formDefinitions33 = createFormDefinitions(xml33);
            FormInstanceBuilderImpl formInstanceBuilder33 = createBinder(formDefinitions33);
            Document document33 = newDocument();
            formInstanceBuilder33.buildFormInstance(new BindingSourceImpl("repr"), document33, formDefinitions33.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml34 = "<?xml version='1.0'?>\n";
            xml34 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml34 += "<ns1:single-element id='id' type='prohibited'>";
            xml34 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml34 += "</ns1:element>";
            xml34 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml34 += "</ns1:element>";
            xml34 += "</ns1:single-element>";
            xml34 += "</ns1:form>";
            FormDefinitions formDefinitions34 = createFormDefinitions(xml34);
            FormInstanceBuilderImpl formInstanceBuilder34 = createBinder(formDefinitions34);
            Document document34 = newDocument();
            formInstanceBuilder34.buildFormInstance(new BindingSourceImpl("repr"), document34, formDefinitions34.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml35 = "<?xml version='1.0'?>\n";
            xml35 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml35 += "<ns1:single-element id='id' type='prohibited'>";
            xml35 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml35 += "</ns1:element>";
            xml35 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='1'>";
            xml35 += "</ns1:element>";
            xml35 += "</ns1:single-element>";
            xml35 += "</ns1:form>";
            FormDefinitions formDefinitions35 = createFormDefinitions(xml35);
            FormInstanceBuilderImpl formInstanceBuilder35 = createBinder(formDefinitions35);
            Document document35 = newDocument();
            formInstanceBuilder35.buildFormInstance(new BindingSourceImpl("repr"), document35, formDefinitions35.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Multiple single elements are present: single-element[@id]], {source}form[@:id]");
        }

        try {
            String xml41 = "<?xml version='1.0'?>\n";
            xml41 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml41 += "<ns1:single-element id='id' type='prohibited'>";
            xml41 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='-1'>";
            xml41 += "</ns1:element>";
            xml41 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml41 += "</ns1:element>";
            xml41 += "</ns1:single-element>";
            xml41 += "</ns1:form>";
            FormDefinitions formDefinitions41 = createFormDefinitions(xml41);
            FormInstanceBuilderImpl formInstanceBuilder41 = createBinder(formDefinitions41);
            Document document41 = newDocument();
            formInstanceBuilder41.buildFormInstance(new BindingSourceImpl("repr"), document41, formDefinitions41.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml42 = "<?xml version='1.0'?>\n";
            xml42 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml42 += "<ns1:single-element id='id' type='prohibited'>";
            xml42 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='0'>";
            xml42 += "</ns1:element>";
            xml42 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml42 += "</ns1:element>";
            xml42 += "</ns1:single-element>";
            xml42 += "</ns1:form>";
            FormDefinitions formDefinitions42 = createFormDefinitions(xml42);
            FormInstanceBuilderImpl formInstanceBuilder42 = createBinder(formDefinitions42);
            Document document42 = newDocument();
            formInstanceBuilder42.buildFormInstance(new BindingSourceImpl("repr"), document42, formDefinitions42.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml43 = "<?xml version='1.0'?>\n";
            xml43 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml43 += "<ns1:single-element id='id' type='prohibited'>";
            xml43 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
            xml43 += "</ns1:element>";
            xml43 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml43 += "</ns1:element>";
            xml43 += "</ns1:single-element>";
            xml43 += "</ns1:form>";
            FormDefinitions formDefinitions43 = createFormDefinitions(xml43);
            FormInstanceBuilderImpl formInstanceBuilder43 = createBinder(formDefinitions43);
            Document document43 = newDocument();
            formInstanceBuilder43.buildFormInstance(new BindingSourceImpl("repr"), document43, formDefinitions43.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml44 = "<?xml version='1.0'?>\n";
            xml44 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml44 += "<ns1:single-element id='id' type='prohibited'>";
            xml44 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='2'>";
            xml44 += "</ns1:element>";
            xml44 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml44 += "</ns1:element>";
            xml44 += "</ns1:single-element>";
            xml44 += "</ns1:form>";
            FormDefinitions formDefinitions44 = createFormDefinitions(xml44);
            FormInstanceBuilderImpl formInstanceBuilder44 = createBinder(formDefinitions44);
            Document document44 = newDocument();
            formInstanceBuilder44.buildFormInstance(new BindingSourceImpl("repr"), document44, formDefinitions44.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id1]], {source}form[@:id]/single-element[@id]");
        }

        try {
            String xml45 = "<?xml version='1.0'?>\n";
            xml45 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml45 += "<ns1:single-element id='id' type='prohibited'>";
            xml45 += "<ns1:element id='id1' lookup='lookup' type='optional+' repr='repr1' count='2'>";
            xml45 += "</ns1:element>";
            xml45 += "<ns1:element id='id2' lookup='lookup' repr='repr2' count='2'>";
            xml45 += "</ns1:element>";
            xml45 += "</ns1:single-element>";
            xml45 += "</ns1:form>";
            FormDefinitions formDefinitions45 = createFormDefinitions(xml45);
            FormInstanceBuilderImpl formInstanceBuilder45 = createBinder(formDefinitions45);
            Document document45 = newDocument();
            formInstanceBuilder45.buildFormInstance(new BindingSourceImpl("repr"), document45, formDefinitions45.getFormDefinition("id"));
            Assertions.fail("FormInstanceBuilderImpl test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Optional element is present more than once: element[@id2]], {source}form[@:id]/single-element[@id]");
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

    private FormInstanceBuilderImpl createBinder(final FormDefinitions formDefinitions) {
        List<OtherNodeInstanceBuilder> otherNodeInstanceBuilders = ServiceFinder.find(OtherNodeInstanceBuilder.class);
        return new FormInstanceBuilderImpl(formDefinitions, new FormInstanceBinderImpl(), otherNodeInstanceBuilders);
    }

    private Document newDocument() {
        return XmlDocumentBuilder.getDocumentBuilder().newDocument();
    }

}
