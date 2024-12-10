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
package ru.d_shap.fm.formmodel;

import java.io.IOException;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link XmlDocumentBuilderConfigurationException}.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentBuilderConfigurationExceptionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public XmlDocumentBuilderConfigurationExceptionTest() {
        super();
    }

    /**
     * {@link XmlDocumentBuilderConfigurationException} class test.
     */
    @Test
    public void errorMessageTest() {
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(null)).messageIsNull();
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException())).messageIsNull();
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException(""))).hasMessage("");
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException(" "))).hasMessage(" ");
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException("io error"))).hasMessage("io error");
    }

    /**
     * {@link XmlDocumentBuilderConfigurationException} class test.
     */
    @Test
    public void errorCauseTest() {
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(null)).causeIsNull();
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException())).hasCause(IOException.class);
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException())).causeMessageIsNull();
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException(""))).hasCause(IOException.class);
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException(""))).hasCauseMessage("");
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException(" "))).hasCause(IOException.class);
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException(" "))).hasCauseMessage(" ");
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException("io error"))).hasCause(IOException.class);
        Assertions.assertThat(new XmlDocumentBuilderConfigurationException(new IOException("io error"))).hasCauseMessage("io error");
    }

}