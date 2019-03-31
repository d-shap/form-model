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
import java.nio.file.InvalidPathException;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.InputSourceException;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

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

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader11 = new FormXmlDefinitionsFileLoader(simpleForm);
        List<FormDefinition> formDefinitions11 = formXmlDefinitionsFileLoader11.load();
        Assertions.assertThat(formDefinitions11.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions11.get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions11.get(0).getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(1).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions11.get(0).getFormReferenceDefinitions().get(1).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions11.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions11.get(0).getSource()).endsWith(File.separator + "simpleForm.xml");

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader12 = new FormXmlDefinitionsFileLoader(simpleForm, new SkipFileFilter());
        List<FormDefinition> formDefinitions12 = formXmlDefinitionsFileLoader12.load();
        Assertions.assertThat(formDefinitions12).hasSize(0);

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader13 = new FormXmlDefinitionsFileLoader(new XmlDocumentBuilderConfiguratorImpl(), simpleForm);
        List<FormDefinition> formDefinitions13 = formXmlDefinitionsFileLoader13.load();
        Assertions.assertThat(formDefinitions13.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions13.get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions13.get(0).getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions13.get(0).getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions13.get(0).getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions13.get(0).getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions13.get(0).getFormReferenceDefinitions().get(1).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions13.get(0).getFormReferenceDefinitions().get(1).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions13.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions13.get(0).getSource()).endsWith(File.separator + "simpleForm.xml");

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader14 = new FormXmlDefinitionsFileLoader(new XmlDocumentBuilderConfiguratorImpl(), simpleForm, new SkipFileFilter());
        List<FormDefinition> formDefinitions14 = formXmlDefinitionsFileLoader14.load();
        Assertions.assertThat(formDefinitions14).hasSize(0);

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader15 = new FormXmlDefinitionsFileLoader(formXmlDefinitionsFileLoader11, simpleForm);
        List<FormDefinition> formDefinitions15 = formXmlDefinitionsFileLoader15.load();
        Assertions.assertThat(formDefinitions15.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions15.get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions15.get(0).getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions15.get(0).getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions15.get(0).getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions15.get(0).getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions15.get(0).getFormReferenceDefinitions().get(1).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions15.get(0).getFormReferenceDefinitions().get(1).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions15.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions15.get(0).getSource()).endsWith(File.separator + "simpleForm.xml");

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader16 = new FormXmlDefinitionsFileLoader(formXmlDefinitionsFileLoader12, simpleForm, new SkipFileFilter());
        List<FormDefinition> formDefinitions16 = formXmlDefinitionsFileLoader16.load();
        Assertions.assertThat(formDefinitions16).hasSize(0);

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader21 = new FormXmlDefinitionsFileLoader(parentDirectory);
        FormDefinitions formDefinitions21 = new FormDefinitions();
        formXmlDefinitionsFileLoader21.load(formDefinitions21);
        Assertions.assertThat(formDefinitions21.getFormDefinitions().size()).isEqualTo(4);
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getFormReferenceDefinitions().get(1).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getFormReferenceDefinitions().get(1).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions21.getFormDefinition("id").getSource()).endsWith(File.separator + "simpleForm.xml");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id1").getSource()).endsWith(File.separator + "subforms" + File.separator + "form1.xml");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "id2").getSource()).endsWith(File.separator + "subforms" + File.separator + "form2.xml");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getId()).isEqualTo("noFileNameForm");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions21.getFormDefinition("subforms", "noFileNameForm").getSource()).endsWith(File.separator + "subforms" + File.separator + ".xml");

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader22 = new FormXmlDefinitionsFileLoader(parentDirectory, new SkipFileFilter());
        FormDefinitions formDefinitions22 = new FormDefinitions();
        formXmlDefinitionsFileLoader22.load(formDefinitions22);
        Assertions.assertThat(formDefinitions22.getFormDefinitions().size()).isEqualTo(0);

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader23 = new FormXmlDefinitionsFileLoader(new XmlDocumentBuilderConfiguratorImpl(), parentDirectory);
        FormDefinitions formDefinitions23 = new FormDefinitions();
        formXmlDefinitionsFileLoader23.load(formDefinitions23);
        Assertions.assertThat(formDefinitions23.getFormDefinitions().size()).isEqualTo(4);
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getFormReferenceDefinitions().get(1).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getFormReferenceDefinitions().get(1).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions23.getFormDefinition("id").getSource()).endsWith(File.separator + "simpleForm.xml");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id1").getSource()).endsWith(File.separator + "subforms" + File.separator + "form1.xml");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "id2").getSource()).endsWith(File.separator + "subforms" + File.separator + "form2.xml");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getId()).isEqualTo("noFileNameForm");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions23.getFormDefinition("subforms", "noFileNameForm").getSource()).endsWith(File.separator + "subforms" + File.separator + ".xml");

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader24 = new FormXmlDefinitionsFileLoader(new XmlDocumentBuilderConfiguratorImpl(), parentDirectory, new SkipFileFilter());
        FormDefinitions formDefinitions24 = new FormDefinitions();
        formXmlDefinitionsFileLoader24.load(formDefinitions24);
        Assertions.assertThat(formDefinitions24.getFormDefinitions().size()).isEqualTo(0);

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader25 = new FormXmlDefinitionsFileLoader(formXmlDefinitionsFileLoader21, parentDirectory);
        FormDefinitions formDefinitions25 = new FormDefinitions();
        formXmlDefinitionsFileLoader25.load(formDefinitions25);
        Assertions.assertThat(formDefinitions25.getFormDefinitions().size()).isEqualTo(4);
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getFormReferenceDefinitions().get(1).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getFormReferenceDefinitions().get(1).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions25.getFormDefinition("id").getSource()).endsWith(File.separator + "simpleForm.xml");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id1").getSource()).endsWith(File.separator + "subforms" + File.separator + "form1.xml");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getAllNodeDefinitions()).hasSize(2);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getFormReferenceDefinitions().get(0).getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getFormReferenceDefinitions().get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "id2").getSource()).endsWith(File.separator + "subforms" + File.separator + "form2.xml");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getGroup()).isEqualTo("subforms");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getId()).isEqualTo("noFileNameForm");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions().get(0).getId()).isEqualTo("id");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getElementDefinitions().get(0).getLookup()).isEqualTo("lookup");
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions25.getFormDefinition("subforms", "noFileNameForm").getSource()).endsWith(File.separator + "subforms" + File.separator + ".xml");

        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader26 = new FormXmlDefinitionsFileLoader(formXmlDefinitionsFileLoader22, parentDirectory, new SkipFileFilter());
        FormDefinitions formDefinitions26 = new FormDefinitions();
        formXmlDefinitionsFileLoader26.load(formDefinitions26);
        Assertions.assertThat(formDefinitions26.getFormDefinitions().size()).isEqualTo(0);
    }

    /**
     * {@link FormXmlDefinitionsFileLoader} class test.
     */
    @Test
    public void loadInvalidPathFailTest() {
        try {
            File file = new File("invalid\u0000path.xml");
            FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader = new FormXmlDefinitionsFileLoader(file);
            formXmlDefinitionsFileLoader.load();
            Assertions.fail("FormXmlDefinitionsFileLoader test fail");
        } catch (InputSourceException ex) {
            Assertions.assertThat(ex).hasCause(InvalidPathException.class);
        }
    }

    /**
     * {@link FormXmlDefinitionsFileLoader} class test.
     */
    @Test
    public void loadNullListFilesTest() {
        File file = new EmptyDirectoryFile();
        FormXmlDefinitionsFileLoader formXmlDefinitionsFileLoader = new FormXmlDefinitionsFileLoader(file);
        List<FormDefinition> formDefinitions = formXmlDefinitionsFileLoader.load();
        Assertions.assertThat(formDefinitions).hasSize(0);
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
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, ".xml"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form"))).isFalse();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.txt"))).isFalse();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "xml"))).isFalse();
    }

}
