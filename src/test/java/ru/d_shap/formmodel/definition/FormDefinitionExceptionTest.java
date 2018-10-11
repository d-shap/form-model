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

/**
 * Tests for {@link FormDefinitionException}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionExceptionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionExceptionTest() {
        super();
    }

    /**
     * {@link FormDefinitionException} class test.
     */
    @Test
    public void errorMessageTest() {
        Assertions.assertThat(new FormDefinitionException(null)).toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException("")).hasMessage("");
        Assertions.assertThat(new FormDefinitionException(" ")).hasMessage(" ");
        Assertions.assertThat(new FormDefinitionException("error")).hasMessage("error");
    }

    /**
     * {@link FormDefinitionException} class test.
     */
    @Test
    public void errorCauseTest() {
        Assertions.assertThat(new FormDefinitionException("")).toCause().isNull();
        Assertions.assertThat(new FormDefinitionException(" ")).toCause().isNull();
        Assertions.assertThat(new FormDefinitionException("error")).toCause().isNull();
    }

}
