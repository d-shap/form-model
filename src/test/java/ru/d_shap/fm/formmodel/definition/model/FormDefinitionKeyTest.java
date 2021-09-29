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
package ru.d_shap.fm.formmodel.definition.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.fm.formmodel.BaseFormModelTest;

/**
 * Tests for {@link FormDefinitionKey}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionKeyTest extends BaseFormModelTest {

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
        Assertions.assertThat(new FormDefinitionKey((String) null).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey("id").getGroup()).isEqualTo("");

        Assertions.assertThat(new FormDefinitionKey(null, "id").getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey("", "id").getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(" ", "id").getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey("group", "id").getGroup()).isEqualTo("group");
        Assertions.assertThat(new FormDefinitionKey("-group", "id").getGroup()).isEqualTo("-group");

        Assertions.assertThat(new FormDefinitionKey((FormDefinition) null).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition("", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(" ", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("group");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition("-group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getGroup()).isEqualTo("-group");

        Assertions.assertThat(new FormDefinitionKey((FormReferenceDefinition) null).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition("", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(" ", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition("group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("group");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition("-group", "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getGroup()).isEqualTo("-group");
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new FormDefinitionKey((String) null).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey("").getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(" ").getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey("id").getId()).isEqualTo("id");
        Assertions.assertThat(new FormDefinitionKey("-id").getId()).isEqualTo("-id");

        Assertions.assertThat(new FormDefinitionKey(null, null).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(null, "").getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(null, " ").getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(null, "id").getId()).isEqualTo("id");
        Assertions.assertThat(new FormDefinitionKey(null, "-id").getId()).isEqualTo("-id");

        Assertions.assertThat(new FormDefinitionKey((FormDefinition) null).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, null, new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, " ", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo("id");
        Assertions.assertThat(new FormDefinitionKey(new FormDefinition(null, "-id", new ArrayList<NodeDefinition>(), new HashMap<String, String>(), "source")).getId()).isEqualTo("-id");

        Assertions.assertThat(new FormDefinitionKey((FormReferenceDefinition) null).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, null, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, " ", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo("id");
        Assertions.assertThat(new FormDefinitionKey(new FormReferenceDefinition(null, "-id", new ArrayList<NodeDefinition>(), new HashMap<String, String>())).getId()).isEqualTo("-id");
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void equalsTest() {
        FormDefinitionKey formDefinitionKey1 = new FormDefinitionKey(null, "id");
        Assertions.assertThat(formDefinitionKey1).isEqualTo(formDefinitionKey1);
        Assertions.assertThat(formDefinitionKey1).isEqualTo(new FormDefinitionKey("id"));
        Assertions.assertThat(formDefinitionKey1).isEqualTo(new FormDefinitionKey(null, "id"));
        Assertions.assertThat(formDefinitionKey1).isNotEqualTo(new Object());

        FormDefinitionKey formDefinitionKey2 = new FormDefinitionKey("group", null);
        Assertions.assertThat(formDefinitionKey2).isEqualTo(formDefinitionKey2);
        Assertions.assertThat(formDefinitionKey2).isEqualTo(new FormDefinitionKey("group", null));
        Assertions.assertThat(formDefinitionKey2).isNotEqualTo(new Object());

        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isEqualTo(new FormDefinitionKey("group1", "id1"));
        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isNotEqualTo(new FormDefinitionKey("group1", "id2"));
        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isNotEqualTo(new FormDefinitionKey("group2", "id1"));
        Assertions.assertThat(new FormDefinitionKey("group1", "id1")).isNotEqualTo(new FormDefinitionKey("group2", "id2"));

        Assertions.assertThat(new FormDefinitionKey("group", "id")).isNotEqualTo(new FormDefinitionKey(null, "id"));
        Assertions.assertThat(new FormDefinitionKey(null, "id")).isNotEqualTo(new FormDefinitionKey("group", "id"));

        Assertions.assertThat(new FormDefinitionKey("group", "id")).isNotEqualTo(new FormDefinitionKey("group", null));
        Assertions.assertThat(new FormDefinitionKey("group", null)).isNotEqualTo(new FormDefinitionKey("group", "id"));

        Assertions.assertThat(new FormDefinitionKey((String) null)).isEqualTo(new FormDefinitionKey((FormDefinition) null));
        Assertions.assertThat(new FormDefinitionKey((String) null)).isEqualTo(new FormDefinitionKey((FormReferenceDefinition) null));
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void hashCodeTest() {
        Assertions.assertThat(new FormDefinitionKey((String) null)).hasHashCode(0);
        Assertions.assertThat(new FormDefinitionKey("")).hasHashCode(0);
        Assertions.assertThat(new FormDefinitionKey(" ")).hasHashCode(32);
        Assertions.assertThat(new FormDefinitionKey("id")).hasHashCode(3355);
        Assertions.assertThat(new FormDefinitionKey("-id")).hasHashCode(46600);

        Assertions.assertThat(new FormDefinitionKey(null, "id")).hasHashCode(3355);
        Assertions.assertThat(new FormDefinitionKey("", "id")).hasHashCode(3355);
        Assertions.assertThat(new FormDefinitionKey(" ", "id")).hasHashCode(4091);
        Assertions.assertThat(new FormDefinitionKey("group", "id")).hasHashCode(-2026491260);
        Assertions.assertThat(new FormDefinitionKey("-group", "id")).hasHashCode(1834876249);

        Assertions.assertThat(new FormDefinitionKey("group", null)).hasHashCode(-2026494615);
        Assertions.assertThat(new FormDefinitionKey("group", "")).hasHashCode(-2026494615);
        Assertions.assertThat(new FormDefinitionKey("group", " ")).hasHashCode(-2026494583);
        Assertions.assertThat(new FormDefinitionKey("group", "id")).hasHashCode(-2026491260);
        Assertions.assertThat(new FormDefinitionKey("group", "-id")).hasHashCode(-2026448015);

        Assertions.assertThat(new FormDefinitionKey((FormDefinition) null)).hasHashCode(0);
        Assertions.assertThat(new FormDefinitionKey((FormReferenceDefinition) null)).hasHashCode(0);
    }

    /**
     * {@link FormDefinitionKey} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new FormDefinitionKey((String) null)).hasToString("@:");
        Assertions.assertThat(new FormDefinitionKey("")).hasToString("@:");
        Assertions.assertThat(new FormDefinitionKey(" ")).hasToString("@: ");
        Assertions.assertThat(new FormDefinitionKey("id")).hasToString("@:id");
        Assertions.assertThat(new FormDefinitionKey("-id")).hasToString("@:-id");

        Assertions.assertThat(new FormDefinitionKey(null, "id")).hasToString("@:id");
        Assertions.assertThat(new FormDefinitionKey("", "id")).hasToString("@:id");
        Assertions.assertThat(new FormDefinitionKey(" ", "id")).hasToString("@ :id");
        Assertions.assertThat(new FormDefinitionKey("group", "id")).hasToString("@group:id");
        Assertions.assertThat(new FormDefinitionKey("-group", "id")).hasToString("@-group:id");

        Assertions.assertThat(new FormDefinitionKey("group", null)).hasToString("@group:");
        Assertions.assertThat(new FormDefinitionKey("group", "")).hasToString("@group:");
        Assertions.assertThat(new FormDefinitionKey("group", " ")).hasToString("@group: ");
        Assertions.assertThat(new FormDefinitionKey("group", "id")).hasToString("@group:id");
        Assertions.assertThat(new FormDefinitionKey("group", "-id")).hasToString("@group:-id");

        Assertions.assertThat(new FormDefinitionKey((FormDefinition) null)).hasToString("@:");
        Assertions.assertThat(new FormDefinitionKey((FormReferenceDefinition) null)).hasToString("@:");
    }

}
