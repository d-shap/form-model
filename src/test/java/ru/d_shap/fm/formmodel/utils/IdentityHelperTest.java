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
package ru.d_shap.fm.formmodel.utils;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link IdentityHelper}.
 *
 * @author Dmitry Shapovalov
 */
public final class IdentityHelperTest {

    /**
     * Test class constructor.
     */
    public IdentityHelperTest() {
        super();
    }

    /**
     * {@link IdentityHelper} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(IdentityHelper.class).hasOnePrivateConstructor();
    }

    /**
     * {@link IdentityHelper} class test.
     */
    @Test
    public void containsTest() {
        Integer val1 = 100000;
        Integer val2 = 100000;
        Integer val3 = 100000;
        List<Integer> list = Arrays.asList(val1, val1, val2);

        Assertions.assertThat(list.contains(val1)).isTrue();
        Assertions.assertThat(IdentityHelper.contains(list, val1)).isTrue();

        Assertions.assertThat(val2).isEqualTo(val1);
        Assertions.assertThat(val2).isNotSameAs(val1);
        Assertions.assertThat(list.contains(val2)).isTrue();
        Assertions.assertThat(IdentityHelper.contains(list, val2)).isTrue();

        Assertions.assertThat(val3).isEqualTo(val1);
        Assertions.assertThat(val3).isNotSameAs(val1);
        Assertions.assertThat(list.contains(val3)).isTrue();
        Assertions.assertThat(IdentityHelper.contains(list, val3)).isFalse();
    }

}
