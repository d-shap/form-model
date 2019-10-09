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
package ru.d_shap.formmodel.definition.model;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link CardinalityDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class CardinalityDefinitionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public CardinalityDefinitionTest() {
        super();
    }

    /**
     * {@link CardinalityDefinition} class test.
     */
    @Test
    public void valueCountTest() {
        Assertions.assertThat(CardinalityDefinition.class).asEnum().hasValueCount(5);
    }

    /**
     * {@link CardinalityDefinition} class test.
     */
    @Test
    public void getCardinalityTest() {
        Assertions.assertThat(CardinalityDefinition.REQUIRED.getCardinality()).isEqualTo("required");
        Assertions.assertThat(CardinalityDefinition.REQUIRED_MULTIPLE.getCardinality()).isEqualTo("required+");
        Assertions.assertThat(CardinalityDefinition.OPTIONAL.getCardinality()).isEqualTo("optional");
        Assertions.assertThat(CardinalityDefinition.OPTIONAL_MULTIPLE.getCardinality()).isEqualTo("optional+");
        Assertions.assertThat(CardinalityDefinition.PROHIBITED.getCardinality()).isEqualTo("prohibited");
    }

    /**
     * {@link CardinalityDefinition} class test.
     */
    @Test
    public void getCardinalityDefinitionTest() {
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition(null)).isNull();
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition("")).isNull();
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition(" ")).isNull();
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition("some wrong cardinality")).isNull();
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition("required")).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition("required+")).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition("optional")).isSameAs(CardinalityDefinition.OPTIONAL);
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition("optional+")).isSameAs(CardinalityDefinition.OPTIONAL_MULTIPLE);
        Assertions.assertThat(CardinalityDefinition.getCardinalityDefinition("prohibited")).isSameAs(CardinalityDefinition.PROHIBITED);
    }

}
