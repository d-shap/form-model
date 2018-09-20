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

import java.io.IOException;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link FormDefinitionException}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionExceptionTest {

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
        Assertions.assertThat(new FormDefinitionException((String) null)).toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException("")).hasMessage("");
        Assertions.assertThat(new FormDefinitionException(" ")).hasMessage(" ");
        Assertions.assertThat(new FormDefinitionException("error")).hasMessage("error");

        Assertions.assertThat(new FormDefinitionException(null, null)).toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException("", null)).hasMessage("");
        Assertions.assertThat(new FormDefinitionException(" ", null)).hasMessage(" ");
        Assertions.assertThat(new FormDefinitionException("error", null)).hasMessage("error");
        Assertions.assertThat(new FormDefinitionException("error", new IOException("io error"))).hasMessage("error");

        Assertions.assertThat(new FormDefinitionException(null, new IOException())).toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException("", new IOException())).hasMessage("");
        Assertions.assertThat(new FormDefinitionException(" ", new IOException())).hasMessage(" ");
        Assertions.assertThat(new FormDefinitionException("error", new IOException())).hasMessage("error");
        Assertions.assertThat(new FormDefinitionException("error", new IOException())).hasMessage("error");

        Assertions.assertThat(new FormDefinitionException(null, new IOException("io error"))).toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException("", new IOException("io error"))).hasMessage("");
        Assertions.assertThat(new FormDefinitionException(" ", new IOException("io error"))).hasMessage(" ");
        Assertions.assertThat(new FormDefinitionException("error", new IOException("io error"))).hasMessage("error");
        Assertions.assertThat(new FormDefinitionException("error", new IOException("io error"))).hasMessage("error");

        Assertions.assertThat(new FormDefinitionException((Throwable) null)).toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException(new IOException())).toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException(new IOException(""))).hasMessage("");
        Assertions.assertThat(new FormDefinitionException(new IOException(" "))).hasMessage(" ");
        Assertions.assertThat(new FormDefinitionException(new IOException("io error"))).hasMessage("io error");
    }

    /**
     * {@link FormDefinitionException} class test.
     */
    @Test
    public void errorCauseTest() {
        Assertions.assertThat(new FormDefinitionException("")).toCause().isNull();
        Assertions.assertThat(new FormDefinitionException(" ")).toCause().isNull();
        Assertions.assertThat(new FormDefinitionException("error")).toCause().isNull();

        Assertions.assertThat(new FormDefinitionException("", null)).toCause().isNull();
        Assertions.assertThat(new FormDefinitionException(" ", null)).toCause().isNull();
        Assertions.assertThat(new FormDefinitionException("error", null)).toCause().isNull();

        Assertions.assertThat(new FormDefinitionException("", new IOException())).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException("", new IOException())).toCause().toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException(" ", new IOException())).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException(" ", new IOException())).toCause().toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException("error", new IOException())).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException("error", new IOException())).toCause().toMessage().isNull();

        Assertions.assertThat(new FormDefinitionException("", new IOException("io error"))).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException("", new IOException("io error"))).hasCauseMessage("io error");
        Assertions.assertThat(new FormDefinitionException(" ", new IOException("io error"))).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException(" ", new IOException("io error"))).hasCauseMessage("io error");
        Assertions.assertThat(new FormDefinitionException("error", new IOException("io error"))).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException("error", new IOException("io error"))).hasCauseMessage("io error");

        Assertions.assertThat(new FormDefinitionException((Throwable) null)).toCause().isNull();
        Assertions.assertThat(new FormDefinitionException(new IOException())).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException(new IOException())).toCause().toMessage().isNull();
        Assertions.assertThat(new FormDefinitionException(new IOException("io error"))).hasCause(IOException.class);
        Assertions.assertThat(new FormDefinitionException(new IOException("io error"))).hasCauseMessage("io error");
    }

}
