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
package ru.d_shap.formmodel.definition;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * Tests for {@link FormDefinitionValidationException}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionValidationExceptionTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionValidationExceptionTest() {
        super();
    }

    /**
     * {@link FormDefinitionValidationException} class test.
     */
    @Test
    public void errorMessageTest() {
        FormDefinition formDefinition = new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source");
        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>());
        Assertions.assertThat(new FormDefinitionValidationException(null, new NodePath(new NodePath(formDefinition), elementDefinition))).hasMessage("null, {source}form[@group:id]/element[@id]");
        Assertions.assertThat(new FormDefinitionValidationException(Messages.Validation.getGroupIsNotValidMessage("-group"), null)).hasMessage("[Group is not valid: -group], null");
        Assertions.assertThat(new FormDefinitionValidationException(Messages.Validation.getGroupIsNotValidMessage("-group"), new NodePath(new NodePath(formDefinition), elementDefinition))).hasMessage("[Group is not valid: -group], {source}form[@group:id]/element[@id]");
        Assertions.assertThat(new FormDefinitionValidationException(Messages.Validation.getIdIsEmptyMessage(), new NodePath(formDefinition))).hasMessage("[ID is empty], {source}form[@group:id]");
    }

}
