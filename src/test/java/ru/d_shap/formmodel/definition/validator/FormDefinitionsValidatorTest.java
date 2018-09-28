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
package ru.d_shap.formmodel.definition.validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;

/**
 * Tests for {@link FormDefinitionsValidator}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsValidatorTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionsValidatorTest() {
        super();
    }

    /**
     * {@link FormDefinitionsValidator} class test.
     */
    @Test
    public void validateNoFormDefinitionsTest() {
        FormDefinitionsValidator formDefinitionsValidator = new FormDefinitionsValidator();
        formDefinitionsValidator.validate(new HashMap<FormDefinitionKey, String>(), createFormDefinitions());
    }

    /**
     * {@link FormDefinitionsValidator} class test.
     */
    @Test
    public void validateSingleFormDefinitionTest() {
        FormDefinitionsValidator formDefinitionsValidator = new FormDefinitionsValidator();
        FormDefinition formDefinition = new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source");
        formDefinitionsValidator.validate(new HashMap<FormDefinitionKey, String>(), createFormDefinitions(formDefinition));
    }

    /**
     * {@link FormDefinitionsValidator} class test.
     */
    @Test
    public void validateMultipleFormDefinitionsTest() {
        FormDefinitionsValidator formDefinitionsValidator = new FormDefinitionsValidator();
        FormDefinition formDefinition1 = new FormDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes(), "source1");
        FormDefinition formDefinition2 = new FormDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes(), "source2");
        FormDefinition formDefinition3 = new FormDefinition("group", "id3", createNodeDefinitions(), createOtherAttributes(), "source3");
        formDefinitionsValidator.validate(new HashMap<FormDefinitionKey, String>(), createFormDefinitions(formDefinition1, formDefinition2, formDefinition3));
    }

    /**
     * {@link FormDefinitionsValidator} class test.
     */
    @Test(expected = NullPointerException.class)
    public void validateNullFormSourcesFailTest() {
        FormDefinitionsValidator formDefinitionsValidator = new FormDefinitionsValidator();
        formDefinitionsValidator.validate(null, createFormDefinitions());
    }

    /**
     * {@link FormDefinitionsValidator} class test.
     */
    @Test(expected = NullPointerException.class)
    public void validateNullFormDefinitionsFailTest() {
        FormDefinitionsValidator formDefinitionsValidator = new FormDefinitionsValidator();
        formDefinitionsValidator.validate(new HashMap<FormDefinitionKey, String>(), null);
    }

    /**
     * {@link FormDefinitionsValidator} class test.
     */
    @Test
    public void validateDuplicateFormDefinitionsTest() {
        try {
            FormDefinitionsValidator formDefinitionsValidator = new FormDefinitionsValidator();
            FormDefinition formDefinition1 = new FormDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes(), "source1");
            FormDefinition formDefinition2 = new FormDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes(), "source1");
            formDefinitionsValidator.validate(new HashMap<FormDefinitionKey, String>(), createFormDefinitions(formDefinition1, formDefinition2));
            Assertions.fail("FormDefinitionsValidator test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form is not unique: @group:id1, (source1), (source1)]");
        }
    }

    private List<FormDefinition> createFormDefinitions(final FormDefinition... formDefinitions) {
        return Arrays.asList(formDefinitions);
    }

}
