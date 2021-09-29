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
package ru.d_shap.fm.formmodel.binding;

import java.io.IOException;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.fm.formmodel.BaseFormModelTest;
import ru.d_shap.fm.formmodel.definition.model.NodePath;

/**
 * Tests for {@link FormBindingException}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormBindingExceptionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormBindingExceptionTest() {
        super();
    }

    /**
     * {@link FormBindingException} class test.
     */
    @Test
    public void errorMessageTest() {
        Assertions.assertThat(new FormBindingException((String) null)).hasMessage("null");
        Assertions.assertThat(new FormBindingException(null, null)).hasMessage("null");
        Assertions.assertThat(new FormBindingException(null, new NodePath())).hasMessage("null, ");
        Assertions.assertThat(new FormBindingException(null, new NodePath(new NodePath("form"), "element"))).hasMessage("null, form/element");

        Assertions.assertThat(new FormBindingException("[Binding exception message]")).hasMessage("[Binding exception message]");
        Assertions.assertThat(new FormBindingException("[Binding exception message]", null)).hasMessage("[Binding exception message]");
        Assertions.assertThat(new FormBindingException("[Binding exception message]", new NodePath())).hasMessage("[Binding exception message], ");
        Assertions.assertThat(new FormBindingException("[Binding exception message]", new NodePath(new NodePath("form"), "element"))).hasMessage("[Binding exception message], form/element");

        Assertions.assertThat(new FormBindingException((Throwable) null)).toMessage().isNull();
        Assertions.assertThat(new FormBindingException(new IOException())).toMessage().isNull();
        Assertions.assertThat(new FormBindingException(new IOException(""))).hasMessage("");
        Assertions.assertThat(new FormBindingException(new IOException(" "))).hasMessage(" ");
        Assertions.assertThat(new FormBindingException(new IOException("io error"))).hasMessage("io error");
    }

    /**
     * {@link FormBindingException} class test.
     */
    @Test
    public void errorCauseTest() {
        Assertions.assertThat(new FormBindingException((String) null)).toCause().isNull();
        Assertions.assertThat(new FormBindingException(null, null)).toCause().isNull();
        Assertions.assertThat(new FormBindingException(null, new NodePath())).toCause().isNull();
        Assertions.assertThat(new FormBindingException(null, new NodePath(new NodePath("form"), "element"))).toCause().isNull();

        Assertions.assertThat(new FormBindingException("[Binding exception message]")).toCause().isNull();
        Assertions.assertThat(new FormBindingException("[Binding exception message]", null)).toCause().isNull();
        Assertions.assertThat(new FormBindingException("[Binding exception message]", new NodePath())).toCause().isNull();
        Assertions.assertThat(new FormBindingException("[Binding exception message]", new NodePath(new NodePath("form"), "element"))).toCause().isNull();

        Assertions.assertThat(new FormBindingException((Throwable) null)).toCause().isNull();
        Assertions.assertThat(new FormBindingException(new IOException())).hasCause(IOException.class);
        Assertions.assertThat(new FormBindingException(new IOException())).toCause().toMessage().isNull();
        Assertions.assertThat(new FormBindingException(new IOException(""))).hasCause(IOException.class);
        Assertions.assertThat(new FormBindingException(new IOException(""))).hasCauseMessage("");
        Assertions.assertThat(new FormBindingException(new IOException(" "))).hasCause(IOException.class);
        Assertions.assertThat(new FormBindingException(new IOException(" "))).hasCauseMessage(" ");
        Assertions.assertThat(new FormBindingException(new IOException("io error"))).hasCause(IOException.class);
        Assertions.assertThat(new FormBindingException(new IOException("io error"))).hasCauseMessage("io error");
    }

}
