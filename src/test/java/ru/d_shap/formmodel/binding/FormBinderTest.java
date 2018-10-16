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
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.binding.model.BindedElementImpl;
import ru.d_shap.formmodel.binding.model.BindingSourceImpl;
import ru.d_shap.formmodel.definition.FormDefinitionNotFoundException;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.document.DocumentLookup;
import ru.d_shap.formmodel.document.DocumentProcessor;
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
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "</ns1:form>";
        FormDefinitions formDefinitions = createFormDefinitionsFromXml(xml);
        FormBinder formBinder = new FormBinder(formDefinitions, new FormInstanceBinderImpl());
        Document document = formBinder.bind(new BindingSourceImpl("source"), "id");
        Assertions.assertThat(DocumentWriter.getAsString(document)).isEqualTo("<form id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            formBinder.bind(new BindingSourceImpl("source"), "wrong id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @:wrong id]");
        }
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void bindGroupAndIdTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "</ns1:form>";
        FormDefinitions formDefinitions = createFormDefinitionsFromXml(xml);
        FormBinder formBinder = new FormBinder(formDefinitions, new FormInstanceBinderImpl());
        Document document = formBinder.bind(new BindingSourceImpl("source"), "group", "id");
        Assertions.assertThat(DocumentWriter.getAsString(document)).isEqualTo("<form group=\"group\" id=\"id\" xmlns=\"http://d-shap.ru/schema/form-instance/1.0\"/>");

        try {
            formBinder.bind(new BindingSourceImpl("source"), "group", "wrong id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @group:wrong id]");
        }
        try {
            formBinder.bind(new BindingSourceImpl("source"), "wrong group", "id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:id]");
        }
        try {
            formBinder.bind(new BindingSourceImpl("source"), "wrong group", "wrong id");
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:wrong id]");
        }
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void bindDocumentProcessorIdTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "<ns1:element id='el-id' lookup='lookup' type='required+' repr='repr1' count='3'>";
        xml += "</ns1:element>";
        xml += "</ns1:form>";
        FormDefinitions formDefinitions = createFormDefinitionsFromXml(xml);
        FormBinder formBinder = new FormBinder(formDefinitions, new FormInstanceBinderImpl());
        List<String> elements = formBinder.bind(new BindingSourceImpl("source"), "id", new DocumentProcessorImpl());
        Assertions.assertThat(elements).containsExactlyInOrder("Element: repr1[0]", "Element: repr1[1]", "Element: repr1[2]");

        try {
            formBinder.bind(new BindingSourceImpl("source"), "wrong id", new DocumentProcessorImpl());
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @:wrong id]");
        }
    }

    /**
     * {@link FormBinder} class test.
     */
    @Test
    public void bindDocumentProcessorGroupAndIdTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form group='group' id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "<ns1:element id='el-id' lookup='lookup' type='required+' repr='repr1' count='3'>";
        xml += "</ns1:element>";
        xml += "</ns1:form>";
        FormDefinitions formDefinitions = createFormDefinitionsFromXml(xml);
        FormBinder formBinder = new FormBinder(formDefinitions, new FormInstanceBinderImpl());
        List<String> elements = formBinder.bind(new BindingSourceImpl("source"), "group", "id", new DocumentProcessorImpl());
        Assertions.assertThat(elements).containsExactlyInOrder("Element: repr1[0]", "Element: repr1[1]", "Element: repr1[2]");

        try {
            formBinder.bind(new BindingSourceImpl("source"), "group", "wrong id", new DocumentProcessorImpl());
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @group:wrong id]");
        }
        try {
            formBinder.bind(new BindingSourceImpl("source"), "wrong group", "id", new DocumentProcessorImpl());
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:id]");
        }
        try {
            formBinder.bind(new BindingSourceImpl("source"), "wrong group", "wrong id", new DocumentProcessorImpl());
            Assertions.fail("FormBinder test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:wrong id]");
        }
    }

    private FormInstanceBuilderImpl createBinder(final FormDefinitions formDefinitions) {
        List<OtherNodeInstanceBuilder> otherNodeInstanceBuilders = ServiceFinder.find(OtherNodeInstanceBuilder.class);
        return new FormInstanceBuilderImpl(formDefinitions, new FormInstanceBinderImpl(), otherNodeInstanceBuilders);
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class DocumentProcessorImpl implements DocumentProcessor<List<String>> {

        DocumentProcessorImpl() {
            super();
        }

        @Override
        public List<String> process(final Document document) {
            List<Element> elements = DocumentLookup.getElementsWithId(document, "el-id");
            List<BindedElementImpl> bindedElements = DocumentLookup.getBindedElements(elements, BindedElementImpl.class);
            List<String> result = new ArrayList<>();
            for (BindedElementImpl bindedElement : bindedElements) {
                result.add(bindedElement.toString());
            }
            return result;
        }

    }

}
