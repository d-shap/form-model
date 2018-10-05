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

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Tests for {@link FormXmlDefinitionsFileLoader}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsFileLoaderTest extends BaseFormModelTest {

    public static final String ROOT_FOLDER = FormXmlDefinitionsFileLoaderTest.class.getPackage().getName().replaceAll("\\.", "/");

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionsFileLoaderTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionsFileLoader} class test.
     */
    @Test
    public void loadTest() {
        URL url = getClass().getClassLoader().getResource(ROOT_FOLDER);
        File parentDirectory = new File(url.getFile());
        File simpleForm = new File(parentDirectory, "simpleForm.xml");

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader1 = new FormXmlDefinitionsFileLoader(simpleForm);
        List<FormDefinition> formDefinitions11 = formXmlDefinitionsFileLoader1.load();
        Assertions.assertThat(formDefinitions11.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions11.get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions11.get(0).getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(1).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(1).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions11.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions11.get(0).getSource()).endsWith(File.separator + "simpleForm.xml");
    }

    /**
     * {@link FormXmlDefinitionsFileLoader} class test.
     */
    @Test
    public void acceptTest() {
        FileFilter fileFilter = new FormXmlDefinitionsFileLoader.DefaultFileFilter();
        URL url = getClass().getClassLoader().getResource(ROOT_FOLDER);
        File parentDirectory = new File(url.getFile());
        Assertions.assertThat(fileFilter.accept(parentDirectory)).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.xml"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.XML"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.XmL"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.xMl"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form"))).isFalse();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.txt"))).isFalse();
    }

}
