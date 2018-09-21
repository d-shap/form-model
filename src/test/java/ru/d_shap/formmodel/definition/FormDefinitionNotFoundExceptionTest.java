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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;

/**
 * Tests for {@link FormDefinitionNotFoundException}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionNotFoundExceptionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionNotFoundExceptionTest() {
        super();
    }

    /**
     * {@link FormDefinitionNotFoundException} class test.
     */
    @Test
    public void errorMessageTest() {
        Assertions.assertThat(new FormDefinitionNotFoundException(null)).hasMessage("[Form definition was not found: null]");
        Assertions.assertThat(new FormDefinitionNotFoundException(new FormDefinitionKey(null, "id"))).hasMessage("[Form definition was not found: @:id]");
        Assertions.assertThat(new FormDefinitionNotFoundException(new FormDefinitionKey("", "id"))).hasMessage("[Form definition was not found: @:id]");
        Assertions.assertThat(new FormDefinitionNotFoundException(new FormDefinitionKey(" ", "id"))).hasMessage("[Form definition was not found: @ :id]");
        Assertions.assertThat(new FormDefinitionNotFoundException(new FormDefinitionKey("group", null))).hasMessage("[Form definition was not found: @group:]");
        Assertions.assertThat(new FormDefinitionNotFoundException(new FormDefinitionKey("group", ""))).hasMessage("[Form definition was not found: @group:]");
        Assertions.assertThat(new FormDefinitionNotFoundException(new FormDefinitionKey("group", " "))).hasMessage("[Form definition was not found: @group: ]");
        Assertions.assertThat(new FormDefinitionNotFoundException(new FormDefinitionKey("group", "id"))).hasMessage("[Form definition was not found: @group:id]");
    }

}
