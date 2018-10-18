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
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link EmptyStringHelper}.
 *
 * @author Dmitry Shapovalov
 */
public final class EmptyStringHelperTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public EmptyStringHelperTest() {
        super();
    }

    /**
     * {@link EmptyStringHelper} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(EmptyStringHelper.class).hasOnePrivateConstructor();
    }

    /**
     * {@link EmptyStringHelper} class test.
     */
    @Test
    public void isEmptyTest() {
        Assertions.assertThat(EmptyStringHelper.isEmpty(null)).isTrue();
        Assertions.assertThat(EmptyStringHelper.isEmpty("")).isTrue();

        Assertions.assertThat(EmptyStringHelper.isEmpty(" ")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isEmpty("\t")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isEmpty("  \n\t   \r\n ")).isFalse();

        Assertions.assertThat(EmptyStringHelper.isEmpty("a")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isEmpty("X")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isEmpty(" X")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isEmpty("X ")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isEmpty(" X ")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isEmpty("  \t \r\n X ")).isFalse();
    }

    /**
     * {@link EmptyStringHelper} class test.
     */
    @Test
    public void isBlankTest() {
        Assertions.assertThat(EmptyStringHelper.isBlank(null)).isTrue();
        Assertions.assertThat(EmptyStringHelper.isBlank("")).isTrue();

        Assertions.assertThat(EmptyStringHelper.isBlank(" ")).isTrue();
        Assertions.assertThat(EmptyStringHelper.isBlank("\t")).isTrue();
        Assertions.assertThat(EmptyStringHelper.isBlank("  \n\t   \r\n ")).isTrue();

        Assertions.assertThat(EmptyStringHelper.isBlank("a")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isBlank("X")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isBlank(" X")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isBlank("X ")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isBlank(" X ")).isFalse();
        Assertions.assertThat(EmptyStringHelper.isBlank("  \t \r\n X ")).isFalse();
    }

}
