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
 * Tests for {@link NodePath}.
 *
 * @author Dmitry Shapovalov
 */
public final class NodePathTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public NodePathTest() {
        super();
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new NodePath()).hasToString("");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithRepresentationTest() {
        Assertions.assertThat(new NodePath("value")).hasToString("value");
        Assertions.assertThat(new NodePath("vAlUe")).hasToString("vAlUe");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithDefinitionTest() {
        Assertions.assertThat(new NodePath(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"))).hasToString("{source}form[@group:id]");
        Assertions.assertThat(new NodePath(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()))).hasToString("element[@id]");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithParentAndRepresentationTest() {
        NodePath parent1 = new NodePath();
        Assertions.assertThat(new NodePath(parent1, "value")).hasToString("value");
        Assertions.assertThat(new NodePath(parent1, "vAlUe")).hasToString("vAlUe");

        NodePath parent2 = new NodePath("parent");
        Assertions.assertThat(new NodePath(parent2, "value")).hasToString("parent/value");
        Assertions.assertThat(new NodePath(parent2, "vAlUe")).hasToString("parent/vAlUe");

        NodePath parent3 = new NodePath(parent2, "interim");
        Assertions.assertThat(new NodePath(parent3, "value")).hasToString("parent/interim/value");
        Assertions.assertThat(new NodePath(parent3, "vAlUe")).hasToString("parent/interim/vAlUe");

        NodePath parent4 = new NodePath(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"));
        Assertions.assertThat(new NodePath(parent4, "value")).hasToString("{source}form[@group:id]/value");
        Assertions.assertThat(new NodePath(parent4, "vAlUe")).hasToString("{source}form[@group:id]/vAlUe");

        NodePath parent5 = new NodePath(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()));
        Assertions.assertThat(new NodePath(parent5, "value")).hasToString("element[@id]/value");
        Assertions.assertThat(new NodePath(parent5, "vAlUe")).hasToString("element[@id]/vAlUe");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithParentAndDefinitionTest() {
        NodePath parent1 = new NodePath();
        Assertions.assertThat(new NodePath(parent1, new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"))).hasToString("{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent1, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()))).hasToString("element[@id]");

        NodePath parent2 = new NodePath("parent");
        Assertions.assertThat(new NodePath(parent2, new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"))).hasToString("parent/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent2, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()))).hasToString("parent/element[@id]");

        NodePath parent3 = new NodePath(parent2, "interim");
        Assertions.assertThat(new NodePath(parent3, new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"))).hasToString("parent/interim/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent3, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()))).hasToString("parent/interim/element[@id]");

        NodePath parent4 = new NodePath(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"));
        Assertions.assertThat(new NodePath(parent4, new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"))).hasToString("{source}form[@group:id]/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent4, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()))).hasToString("{source}form[@group:id]/element[@id]");

        NodePath parent5 = new NodePath(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()));
        Assertions.assertThat(new NodePath(parent5, new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"))).hasToString("element[@id]/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent5, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()))).hasToString("element[@id]/element[@id]");
    }

}
