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
 * Tests for {@link FormDefinitionKey}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionKeyTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionKeyTest() {
        super();
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void getGroupTest() {
        Assertions.assertThat(new FormDefinitionKey(null, "id").getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey("", "id").getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(" ", "id").getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey("\t", "id").getGroup()).isEqualTo("\t");
        Assertions.assertThat(new FormDefinitionKey("value", "id").getGroup()).isEqualTo("value");

        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition("", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(" ", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition("\t", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("\t");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition("value", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("value");

        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition("", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(" ", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition("\t", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("\t");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition("value", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("value");
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new FormDefinitionKey(null, null).getId()).isNull();
        Assertions.assertThat(new FormDefinitionKey(null, "").getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(null, " ").getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(null, "id").getId()).isEqualTo("id");
        Assertions.assertThat(new FormDefinitionKey(null, "ID").getId()).isEqualTo("ID");

        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, null, new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isNull();
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, " ", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo("id");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "ID", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo("ID");

        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, null, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isNull();
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, " ", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo("id");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "ID", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo("ID");
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void equalsTest() {
        FormDefinitionKey formDefinitionKey = new FormDefinitionKey(null, "id");
        Assertions.assertThat(formDefinitionKey).isEqualTo(formDefinitionKey);
        Assertions.assertThat(formDefinitionKey).isEqualTo(new FormDefinitionKey(null, "id"));
        Assertions.assertThat(formDefinitionKey).isNotEqualTo(new Object());

        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isEqualTo(new FormDefinitionKey("group1", "id1"));
        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isNotEqualTo(new FormDefinitionKey("group1", "id2"));
        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isNotEqualTo(new FormDefinitionKey("group2", "id1"));
        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isNotEqualTo(new FormDefinitionKey("group2", "id2"));

        Assertions.assertThat(new FormDefinitionKey("group", "id")).isNotEqualTo(new FormDefinitionKey("group", null));
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test(expected = NullPointerException.class)
    public void equalsNullIdFailTest() {
        new FormDefinitionKey("group", null).equals(new FormDefinitionKey("group", "id"));
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void hashCodeTest() {
        Assertions.assertThat(new FormDefinitionKey(null, "id")).hasHashCode(3355);
        Assertions.assertThat(new FormDefinitionKey("", "id")).hasHashCode(3355);
        Assertions.assertThat(new FormDefinitionKey(" ", "id")).hasHashCode(3771);
        Assertions.assertThat(new FormDefinitionKey("group", "id")).hasHashCode(1282183566);
        Assertions.assertThat(new FormDefinitionKey("group", "ID")).hasHashCode(1282182542);
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test(expected = NullPointerException.class)
    public void hashCodeNullIdFailTest() {
        new FormDefinitionKey("group", null).hashCode();
    }

}
