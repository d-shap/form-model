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
    public void getFormDefinitionForIdTest() {

    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void getFormDefinitionForGroupAndTest() {

    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void getFormDefinitionForFormReferenceTest() {

    }

}
