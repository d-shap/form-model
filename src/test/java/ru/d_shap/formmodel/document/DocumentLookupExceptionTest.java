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
package ru.d_shap.formmodel.document;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link DocumentLookupException}.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentLookupExceptionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public DocumentLookupExceptionTest() {
        super();
    }

    /**
     * {@link DocumentLookupException} class test.
     */
    @Test
    public void errorMessageTest() {
        Assertions.assertThat(new DocumentLookupException(null)).toMessage().isNull();
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException(""))).hasMessage("");
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException(" "))).hasMessage(" ");
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException("io error"))).hasMessage("io error");
    }

    /**
     * {@link DocumentLookupException} class test.
     */
    @Test
    public void errorCauseTest() {
        Assertions.assertThat(new DocumentLookupException(null)).toCause().isNull();
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException(""))).hasCause(XPathExpressionException.class);
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException(""))).hasCauseMessage("");
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException(" "))).hasCause(XPathExpressionException.class);
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException(" "))).hasCauseMessage(" ");
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException("xpath expression error"))).hasCause(XPathExpressionException.class);
        Assertions.assertThat(new DocumentLookupException(new XPathExpressionException("xpath expression error"))).hasCauseMessage("xpath expression error");
    }

}
