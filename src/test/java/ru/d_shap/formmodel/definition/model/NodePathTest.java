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

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link NodePath}.
 *
 * @author Dmitry Shapovalov
 */
public final class NodePathTest {

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
        Assertions.assertThat(new NodePath().toString()).isEqualTo("");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithRepresentationTest() {
        Assertions.assertThat(new NodePath("value").toString()).isEqualTo("value");
        Assertions.assertThat(new NodePath("vAlUe").toString()).isEqualTo("vAlUe");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithDefinitionTest() {
        Assertions.assertThat(new NodePath(new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).toString()).isEqualTo("{source}form[@group:id]");
        Assertions.assertThat(new NodePath(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).toString()).isEqualTo("element[@id]");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithParentAndRepresentationTest() {
        NodePath parent1 = new NodePath();
        Assertions.assertThat(new NodePath(parent1, "value").toString()).isEqualTo("value");
        Assertions.assertThat(new NodePath(parent1, "vAlUe").toString()).isEqualTo("vAlUe");

        NodePath parent2 = new NodePath("parent");
        Assertions.assertThat(new NodePath(parent2, "value").toString()).isEqualTo("parent/value");
        Assertions.assertThat(new NodePath(parent2, "vAlUe").toString()).isEqualTo("parent/vAlUe");

        NodePath parent3 = new NodePath(parent2, "interim");
        Assertions.assertThat(new NodePath(parent3, "value").toString()).isEqualTo("parent/interim/value");
        Assertions.assertThat(new NodePath(parent3, "vAlUe").toString()).isEqualTo("parent/interim/vAlUe");

        NodePath parent4 = new NodePath(new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source"));
        Assertions.assertThat(new NodePath(parent4, "value").toString()).isEqualTo("{source}form[@group:id]/value");
        Assertions.assertThat(new NodePath(parent4, "vAlUe").toString()).isEqualTo("{source}form[@group:id]/vAlUe");

        NodePath parent5 = new NodePath(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()));
        Assertions.assertThat(new NodePath(parent5, "value").toString()).isEqualTo("element[@id]/value");
        Assertions.assertThat(new NodePath(parent5, "vAlUe").toString()).isEqualTo("element[@id]/vAlUe");
    }

    /**
     * {@link NodePath} class test.
     */
    @Test
    public void toStringWithParentAndDefinitionTest() {
        NodePath parent1 = new NodePath();
        Assertions.assertThat(new NodePath(parent1, new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).toString()).isEqualTo("{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent1, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).toString()).isEqualTo("element[@id]");

        NodePath parent2 = new NodePath("parent");
        Assertions.assertThat(new NodePath(parent2, new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).toString()).isEqualTo("parent/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent2, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).toString()).isEqualTo("parent/element[@id]");

        NodePath parent3 = new NodePath(parent2, "interim");
        Assertions.assertThat(new NodePath(parent3, new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).toString()).isEqualTo("parent/interim/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent3, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).toString()).isEqualTo("parent/interim/element[@id]");

        NodePath parent4 = new NodePath(new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source"));
        Assertions.assertThat(new NodePath(parent4, new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).toString()).isEqualTo("{source}form[@group:id]/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent4, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).toString()).isEqualTo("{source}form[@group:id]/element[@id]");

        NodePath parent5 = new NodePath(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()));
        Assertions.assertThat(new NodePath(parent5, new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).toString()).isEqualTo("element[@id]/{source}form[@group:id]");
        Assertions.assertThat(new NodePath(parent5, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).toString()).isEqualTo("element[@id]/element[@id]");
    }

}
