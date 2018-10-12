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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.InputSourceException;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Tests for {@link FormXmlDefinitionsInputStreamLoader}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsInputStreamLoaderTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionsInputStreamLoaderTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionsInputStreamLoader} class test.
     */
    @Test
    public void loadTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "</ns1:form>";

        InputStream inputStream1 = new ByteArrayInputStream(xml.getBytes());
        FormXmlDefinitionsInputStreamLoader formXmlDefinitionsInputStreamLoader1 = new FormXmlDefinitionsInputStreamLoader(inputStream1, "source1");
        List<FormDefinition> formDefinitions1 = formXmlDefinitionsInputStreamLoader1.load();
        Assertions.assertThat(formDefinitions1).hasSize(1);
        Assertions.assertThat(formDefinitions1.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions1.get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions1.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions1.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions1.get(0).getSource()).isEqualTo("source1");

        InputStream inputStream2 = new ByteArrayInputStream(xml.getBytes());
        FormXmlDefinitionsInputStreamLoader formXmlDefinitionsInputStreamLoader2 = new FormXmlDefinitionsInputStreamLoader(new XmlDocumentBuilderConfiguratorImpl(), inputStream2, "source2");
        List<FormDefinition> formDefinitions2 = formXmlDefinitionsInputStreamLoader2.load();
        Assertions.assertThat(formDefinitions2).hasSize(1);
        Assertions.assertThat(formDefinitions2.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions2.get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions2.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions2.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions2.get(0).getSource()).isEqualTo("source2");

        InputStream inputStream3 = new ByteArrayInputStream(xml.getBytes());
        FormXmlDefinitionsInputStreamLoader formXmlDefinitionsInputStreamLoader3 = new FormXmlDefinitionsInputStreamLoader(formXmlDefinitionsInputStreamLoader1, inputStream3, "source3");
        List<FormDefinition> formDefinitions3 = formXmlDefinitionsInputStreamLoader3.load();
        Assertions.assertThat(formDefinitions3).hasSize(1);
        Assertions.assertThat(formDefinitions3.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions3.get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions3.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions3.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions3.get(0).getSource()).isEqualTo("source3");
    }

    /**
     * {@link FormXmlDefinitionsInputStreamLoader} class test.
     */
    @Test
    public void loadFailTest() {
        try {
            InputStream inputStream = new ReadErrorInputStream();
            FormXmlDefinitionsInputStreamLoader formXmlDefinitionsInputStreamLoader = new FormXmlDefinitionsInputStreamLoader(inputStream, "source");
            formXmlDefinitionsInputStreamLoader.load();
            Assertions.fail("FormXmlDefinitionsInputStreamLoader test fail");
        } catch (InputSourceException ex) {
            Assertions.assertThat(ex).hasMessage("READ ERROR!");
            Assertions.assertThat(ex).hasCause(IOException.class);
        }
        try {
            InputStream inputStream = new CloseErrorInputStream();
            FormXmlDefinitionsInputStreamLoader formXmlDefinitionsInputStreamLoader = new FormXmlDefinitionsInputStreamLoader(inputStream, "source");
            formXmlDefinitionsInputStreamLoader.load();
            Assertions.fail("FormXmlDefinitionsInputStreamLoader test fail");
        } catch (InputSourceException ex) {
            Assertions.assertThat(ex).hasMessage("CLOSE ERROR!");
            Assertions.assertThat(ex).hasCause(IOException.class);
        }
    }

    /**
     * {@link FormXmlDefinitionsInputStreamLoader} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void closeTest() throws IOException {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml += "</ns1:form>";

        CloseableInputStream inputStream1 = new CloseableInputStream(new ByteArrayInputStream(xml.getBytes()));
        Assertions.assertThat(inputStream1.isClosed()).isFalse();
        FormXmlDefinitionsInputStreamLoader formXmlDefinitionsInputStreamLoader1 = new FormXmlDefinitionsInputStreamLoader(inputStream1, "source1");
        formXmlDefinitionsInputStreamLoader1.close();
        Assertions.assertThat(inputStream1.isClosed()).isTrue();

        CloseableInputStream inputStream2 = new CloseableInputStream(new ByteArrayInputStream(xml.getBytes()));
        Assertions.assertThat(inputStream2.isClosed()).isFalse();
        FormXmlDefinitionsInputStreamLoader formXmlDefinitionsInputStreamLoader2 = new FormXmlDefinitionsInputStreamLoader(inputStream2, "source1");
        List<FormDefinition> formDefinitions2 = formXmlDefinitionsInputStreamLoader2.load();
        Assertions.assertThat(formDefinitions2).hasSize(1);
        Assertions.assertThat(inputStream2.isClosed()).isTrue();
    }

}
