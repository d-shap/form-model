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
package ru.d_shap.formmodel.definition.loader;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

/**
 * Tests for {@link FormDefinitionsLoader}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsLoaderTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionsLoaderTest() {
        super();
    }

    /**
     * {@link FormDefinitionsLoader} class test.
     */
    @Test
    public void loadTest() {
        FormDefinitions formDefinitions = new FormDefinitions();
        FormDefinition formDefinition1 = new FormDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes(), "source2");
        FormDefinition formDefinition3 = new FormDefinition("group", "id3", createNodeDefinitions(), createOtherAttributes(), "source3");
        FormDefinition formDefinition4 = new FormDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes(), "source1");

        FormDefinitionsLoaderImpl formDefinitionsLoader1 = new FormDefinitionsLoaderImpl(formDefinition1, formDefinition2);
        formDefinitionsLoader1.load(formDefinitions);
        Assertions.assertThat(formDefinitions.getGroups()).containsExactly("group");
        Assertions.assertThat(formDefinitions.getFormDefinition("group", "id1")).isSameAs(formDefinition1);
        Assertions.assertThat(formDefinitions.getFormDefinition("group", "id2")).isSameAs(formDefinition2);

        try {
            FormDefinitionsLoaderImpl formDefinitionsLoader2 = new FormDefinitionsLoaderImpl(formDefinition3, formDefinition4);
            formDefinitionsLoader2.load(formDefinitions);
            Assertions.fail("FormDefinitionsLoader test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form definition is not unique: @group:id1, (source1), (source1)]");
        }
    }

}
