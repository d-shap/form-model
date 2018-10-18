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
package ru.d_shap.formmodel.utils;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.Messages;

/**
 * Tests for {@link NullValueHelper}.
 *
 * @author Dmitry Shapovalov
 */
public final class NullValueHelperTest {

    /**
     * Test class constructor.
     */
    public NullValueHelperTest() {
        super();
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(NullValueHelper.class).hasOnePrivateConstructor();
    }

    /**
     * {@link NullValueHelper} class test.
     */
    @Test
    public void getValueStringTest() {
        Assertions.assertThat(NullValueHelper.getValue(null)).isEqualTo("");
        Assertions.assertThat(NullValueHelper.getValue("")).isEqualTo("");
        Assertions.assertThat(NullValueHelper.getValue(" ")).isEqualTo(" ");
        Assertions.assertThat(NullValueHelper.getValue("value")).isEqualTo("value");
    }

    /**
     * {@link NullValueHelper} class test.
     */
    @Test
    public void getValueObjectgTest() {
        Assertions.assertThat(NullValueHelper.getValue((StringBuilder) null)).isEqualTo("");
        Assertions.assertThat(NullValueHelper.getValue(new StringBuilder())).isEqualTo("");
        Assertions.assertThat(NullValueHelper.getValue(new StringBuilder(" "))).isEqualTo(" ");
        Assertions.assertThat(NullValueHelper.getValue(new StringBuilder("value"))).isEqualTo("value");
    }

}
