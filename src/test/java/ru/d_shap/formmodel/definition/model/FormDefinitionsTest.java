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
package ru.d_shap.formmodel.definition.model;

import java.util.Arrays;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.definition.FormDefinitionNotFoundException;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;

/**
 * Tests for {@link FormDefinitions}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionsTest() {
        super();
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void addFormDefinitionsTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes(), "source2");

        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));

        try {
            formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));
            Assertions.fail("FormDefinitions test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form is not unique: @group:id1, (source1), (source1)]");
        }
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void copyOfTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes(), "source2");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));

        FormDefinitions formDefinitionsCopy = formDefinitions.copyOf();
        Assertions.assertThat(formDefinitionsCopy.getFormDefinition("group", "id1")).isSameAs(formDefinition1);
        Assertions.assertThat(formDefinitionsCopy.getFormDefinition("group", "id2")).isSameAs(formDefinition2);

        FormDefinition formDefinition3 = new FormDefinition("group", "id3", createNodeDefinitions(), createOtherAttributes(), "source3");
        FormDefinition formDefinition4 = new FormDefinition("group", "id4", createNodeDefinitions(), createOtherAttributes(), "source4");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition3, formDefinition4));

        try {
            formDefinitionsCopy.getFormDefinition("group", "id3");
            Assertions.fail("FormDefinitions test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @group:id3]");
        }
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void getFormDefinitionForIdTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source2");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));

        Assertions.assertThat(formDefinitions.getFormDefinition("id")).isNotNull();
        Assertions.assertThat(formDefinitions.getFormDefinition("id")).isSameAs(formDefinition1);

        try {
            formDefinitions.getFormDefinition("wrong id");
            Assertions.fail("FormDefinitions test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @:wrong id]");
        }
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getFormDefinitionForNullIdFailTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source2");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));
        formDefinitions.getFormDefinition((String) null);
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void getFormDefinitionForGroupAndIdTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source2");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));

        Assertions.assertThat(formDefinitions.getFormDefinition(null, "id")).isNotNull();
        Assertions.assertThat(formDefinitions.getFormDefinition(null, "id")).isSameAs(formDefinition1);

        Assertions.assertThat(formDefinitions.getFormDefinition("group", "id")).isNotNull();
        Assertions.assertThat(formDefinitions.getFormDefinition("group", "id")).isSameAs(formDefinition2);

        try {
            formDefinitions.getFormDefinition("group", "wrong id");
            Assertions.fail("FormDefinitions test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @group:wrong id]");
        }
        try {
            formDefinitions.getFormDefinition("wrong group", "id");
            Assertions.fail("FormDefinitions test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:id]");
        }
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getFormDefinitionForGroupAndNullIdFailTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source2");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));
        formDefinitions.getFormDefinition("group", null);
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void getFormDefinitionForFormReferenceTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source2");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));

        Assertions.assertThat(formDefinitions.getFormDefinition(new FormReferenceDefinition(null, "id", createNodeDefinitions(), createOtherAttributes()))).isNotNull();
        Assertions.assertThat(formDefinitions.getFormDefinition(new FormReferenceDefinition(null, "id", createNodeDefinitions(), createOtherAttributes()))).isSameAs(formDefinition1);

        Assertions.assertThat(formDefinitions.getFormDefinition(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes()))).isNotNull();
        Assertions.assertThat(formDefinitions.getFormDefinition(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes()))).isSameAs(formDefinition2);

        try {
            formDefinitions.getFormDefinition(new FormReferenceDefinition("group", "wrong id", createNodeDefinitions(), createOtherAttributes()));
            Assertions.fail("FormDefinitions test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @group:wrong id]");
        }
        try {
            formDefinitions.getFormDefinition(new FormReferenceDefinition("wrong group", "id", createNodeDefinitions(), createOtherAttributes()));
            Assertions.fail("FormDefinitions test fail");
        } catch (FormDefinitionNotFoundException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition was not found: @wrong group:id]");
        }
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getFormDefinitionForNullFormReferenceFailTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source2");
        formDefinitions.addFormDefinitions(Arrays.asList(formDefinition1, formDefinition2));
        formDefinitions.getFormDefinition((FormReferenceDefinition) null);
    }

}
