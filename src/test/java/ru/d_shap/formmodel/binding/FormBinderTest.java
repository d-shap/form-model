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
import org.xml.sax.SAXException;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.binding.model.BindingSourceImpl;
import ru.d_shap.formmodel.definition.FormDefinitionNotFoundException;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.document.DocumentWriter;

/**
 * Tests for {@link FormBinder}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormBinderTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormBinderTest() {
        super();
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void bindIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "id");
        Assertions.assertThat(DocumentWriter.newInstance().getAsString(document1)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            formBinder1.bind(new BindingSourceImpl("source"), "wrong id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @:wrong id]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='el-id' lookup='lookup' type='required' repr='repr' count='3'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
            FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
            formBinder2.bind(new BindingSourceImpl("source"), "id");
            Assertions.fail("FormBinder test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is present more than once: element[@el-id]], {source}form[@:id]");
        }

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml3 += "<ns1:element id='id' lookup='lookup'>";
        xml3 += "</ns1:element>";
        xml3 += "<ns2:otherNode repr='other' valid='true'>";
        xml3 += "</ns2:otherNode>";
        xml3 += "</ns1:form>";
        FormDefinitions formDefinitions3 = createFormDefinitionsFromXml(xml3);
        FormBinder formBinder3 = new FormBinder(formDefinitions3, new FormInstanceBinderImpl());
        Document document3 = formBinder3.bind(new BindingSourceImpl("source"), "id");
        Assertions.assertThat(DocumentWriter.newInstance().getAsString(document3)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"><element id=\"id\"/><otherNode repr=\"other\" xmlns=\"http://d-shap.ru/schema/form-instance-other-node/1.0\"/></form>");

        try {
            String xml4 = "<?xml version='1.0'?>\n";
            xml4 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
            xml4 += "<ns1:element id='id' lookup='lookup'>";
            xml4 += "</ns1:element>";
            xml4 += "<ns2:otherNode repr='insertInvalidElement' valid='true'>";
            xml4 += "</ns2:otherNode>";
            xml4 += "</ns1:form>";
            FormDefinitions formDefinitions4 = createFormDefinitionsFromXml(xml4);
            FormBinder formBinder4 = new FormBinder(formDefinitions4, new FormInstanceBinderImpl());
            formBinder4.bind(new BindingSourceImpl("source"), "id");
            Assertions.fail("FormBinder test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasCause(SAXException.class);
        }
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void bindGroupAndIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "group", "id");
        Assertions.assertThat(DocumentWriter.newInstance().getAsString(document1)).isEqualTo("<form group=\"group\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            formBinder1.bind(new BindingSourceImpl("source"), "group", "wrong id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @group:wrong id]");
        }
        try {
            formBinder1.bind(new BindingSourceImpl("source"), "wrong group", "id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:id]");
        }
        try {
            formBinder1.bind(new BindingSourceImpl("source"), "wrong group", "wrong id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:wrong id]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='el-id' lookup='lookup' type='required' repr='repr1' count='3'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
            FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
            formBinder2.bind(new BindingSourceImpl("source"), "group", "id");
            Assertions.fail("FormBinder test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is present more than once: element[@el-id]], {source}form[@group:id]");
        }
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void bindDocumentProcessorIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='el-id' lookup='lookup' type='required+' repr='repr1' count='3'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        List<String> elements1 = formBinder1.bind(new BindingSourceImpl("source"), "id", new DocumentProcessorImpl("el-id"));
        Assertions.assertThat(elements1).containsExactlyInOrder("Element: repr1[0]", "Element: repr1[1]", "Element: repr1[2]");

        try {
            formBinder1.bind(new BindingSourceImpl("source"), "wrong id", new DocumentProcessorImpl("el-id"));
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @:wrong id]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='el-id' lookup='lookup' type='required' repr='repr1' count='3'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
            FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
            formBinder2.bind(new BindingSourceImpl("source"), "id", new DocumentProcessorImpl("el-id"));
            Assertions.fail("FormBinder test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is present more than once: element[@el-id]], {source}form[@:id]");
        }
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void bindDocumentProcessorGroupAndIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='el-id' lookup='lookup' type='required+' repr='repr1' count='3'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        List<String> elements1 = formBinder1.bind(new BindingSourceImpl("source"), "group", "id", new DocumentProcessorImpl("el-id"));
        Assertions.assertThat(elements1).containsExactlyInOrder("Element: repr1[0]", "Element: repr1[1]", "Element: repr1[2]");

        try {
            formBinder1.bind(new BindingSourceImpl("source"), "group", "wrong id", new DocumentProcessorImpl("el-id"));
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @group:wrong id]");
        }
        try {
            formBinder1.bind(new BindingSourceImpl("source"), "wrong group", "id", new DocumentProcessorImpl("el-id"));
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:id]");
        }
        try {
            formBinder1.bind(new BindingSourceImpl("source"), "wrong group", "wrong id", new DocumentProcessorImpl("el-id"));
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:wrong id]");
        }

        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='el-id' lookup='lookup' type='required' repr='repr1' count='3'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
            FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
            formBinder2.bind(new BindingSourceImpl("source"), "group", "id", new DocumentProcessorImpl("el-id"));
            Assertions.fail("FormBinder test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is present more than once: element[@el-id]], {source}form[@group:id]");
        }
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void formInstanceBuilderCallbackEventsTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:element id='el-id' lookup='lookup' type='required+' repr='repr1' count='3'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormInstanceBinderImpl formInstanceBinder1 = new FormInstanceBinderImpl();
        FormBinder formBinder1 = new FormBinder(formDefinitions1, formInstanceBinder1);
        Assertions.assertThat(formInstanceBinder1.getFormDefinition()).isNull();
        Assertions.assertThat(formInstanceBinder1.getFormDefinition()).isNull();
        Assertions.assertThat(formInstanceBinder1.getDocument()).isNull();
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "group", "id");
        Assertions.assertThat(formInstanceBinder1.getFormDefinition()).isNull();
        Assertions.assertThat(formInstanceBinder1.getFormDefinition()).isNull();
        Assertions.assertThat(formInstanceBinder1.getDocument()).isSameAs(document1);

        FormInstanceBinderImpl formInstanceBinder2 = new FormInstanceBinderImpl();
        try {
            String xml2 = "<?xml version='1.0'?>\n";
            xml2 += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
            xml2 += "<ns1:element id='el-id' lookup='lookup' type='required+' repr='repr1' count='0'>";
            xml2 += "</ns1:element>";
            xml2 += "</ns1:form>";
            FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
            FormBinder formBinder2 = new FormBinder(formDefinitions2, formInstanceBinder2);
            Assertions.assertThat(formInstanceBinder2.getFormDefinition()).isNull();
            Assertions.assertThat(formInstanceBinder2.getFormDefinition()).isNull();
            Assertions.assertThat(formInstanceBinder2.getDocument()).isNull();
            formBinder2.bind(new BindingSourceImpl("source"), "group", "id");
            Assertions.fail("FormBinder test fail");
        } catch (FormBindingException ex) {
            Assertions.assertThat(ex).hasMessage("[Required element is not present: element[@el-id]], {source}form[@group:id]");
        }
        Assertions.assertThat(formInstanceBinder2.getFormDefinition()).isNull();
        Assertions.assertThat(formInstanceBinder2.getFormDefinition()).isNull();
        Assertions.assertThat(formInstanceBinder2.getDocument()).isNull();
    }

}
