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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link StringUtils}.
 *
 * @author Dmitry Shapovalov
 */
public final class StringUtilsTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public StringUtilsTest() {
        super();
    }

    /**
     * {@link StringUtils} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(StringUtils.class).hasOnePrivateConstructor();
    }

    /**
     * {@link StringUtils} class test.
     */
    @Test
    public void isEmptyTest() {
        Assertions.assertThat(StringUtils.isEmpty(null)).isTrue();
        Assertions.assertThat(StringUtils.isEmpty("")).isTrue();

        Assertions.assertThat(StringUtils.isEmpty(" ")).isFalse();
        Assertions.assertThat(StringUtils.isEmpty("\t")).isFalse();
        Assertions.assertThat(StringUtils.isEmpty("  \n\t   \r\n ")).isFalse();

        Assertions.assertThat(StringUtils.isEmpty("a")).isFalse();
        Assertions.assertThat(StringUtils.isEmpty("X")).isFalse();
        Assertions.assertThat(StringUtils.isEmpty(" X")).isFalse();
        Assertions.assertThat(StringUtils.isEmpty("X ")).isFalse();
        Assertions.assertThat(StringUtils.isEmpty(" X ")).isFalse();
        Assertions.assertThat(StringUtils.isEmpty("  \t \r\n X ")).isFalse();
    }

    /**
     * {@link StringUtils} class test.
     */
    @Test
    public void hasValidCharactersTest() {
        Assertions.assertThat(StringUtils.hasValidCharacters("aVal")).isTrue();
        Assertions.assertThat(StringUtils.hasValidCharacters("AVal")).isTrue();
        Assertions.assertThat(StringUtils.hasValidCharacters("zVal")).isTrue();
        Assertions.assertThat(StringUtils.hasValidCharacters("ZVal")).isTrue();
        Assertions.assertThat(StringUtils.hasValidCharacters("_Val")).isTrue();
        Assertions.assertThat(StringUtils.hasValidCharacters("a_Val")).isTrue();
        Assertions.assertThat(StringUtils.hasValidCharacters("a-Val")).isTrue();
        Assertions.assertThat(StringUtils.hasValidCharacters("a1234567890Val")).isTrue();

        Assertions.assertThat(StringUtils.hasValidCharacters(" Val")).isFalse();
        Assertions.assertThat(StringUtils.hasValidCharacters("-Val")).isFalse();
        Assertions.assertThat(StringUtils.hasValidCharacters("0Val")).isFalse();
        Assertions.assertThat(StringUtils.hasValidCharacters("йVal")).isFalse();
        Assertions.assertThat(StringUtils.hasValidCharacters("a Val")).isFalse();
        Assertions.assertThat(StringUtils.hasValidCharacters("aйVal")).isFalse();
    }

}
