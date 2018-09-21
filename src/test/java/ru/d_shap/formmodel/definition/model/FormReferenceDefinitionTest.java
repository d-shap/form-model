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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link FormReferenceDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormReferenceDefinitionTest {

    /**
     * Test class constructor.
     */
    public FormReferenceDefinitionTest() {
        super();
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getGroupTest() {
        Assertions.assertThat(new FormReferenceDefinition(null, "id", createNodeDefinitions(), createOtherAttributeNames()).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormReferenceDefinition("", "id", createNodeDefinitions(), createOtherAttributeNames()).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormReferenceDefinition(" ", "id", createNodeDefinitions(), createOtherAttributeNames()).getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames()).getGroup()).isEqualTo("group");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributeNames()).getId()).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "", createNodeDefinitions(), createOtherAttributeNames()).getId()).isEqualTo("");
        Assertions.assertThat(new FormReferenceDefinition("group", " ", createNodeDefinitions(), createOtherAttributeNames()).getId()).isEqualTo(" ");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames()).getId()).isEqualTo("id");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherNodeDefinitions()).isEmpty();

        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), null).getOtherNodeDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(attributeDefinition), null).getOtherNodeDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(elementDefinition), null).getOtherNodeDefinitions()).isEmpty();

        ChoiceDefinition choiceDefinition = new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(choiceDefinition), null).getOtherNodeDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null).getOtherNodeDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(nodeDefinition), null).getOtherNodeDefinitions()).isEmpty();
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames()).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name", "value")).getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2")).getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name", "value")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name", "value")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name", "value")).getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name3")).isEqualTo("value3");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new FormReferenceDefinition(null, "id", createNodeDefinitions(), createOtherAttributeNames())).hasToString("form[@:id]");
        Assertions.assertThat(new FormReferenceDefinition("", "id", createNodeDefinitions(), createOtherAttributeNames())).hasToString("form[@:id]");
        Assertions.assertThat(new FormReferenceDefinition(" ", "id", createNodeDefinitions(), createOtherAttributeNames())).hasToString("form[@ :id]");
        Assertions.assertThat(new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributeNames())).hasToString("form[@group:]");
        Assertions.assertThat(new FormReferenceDefinition("group", "", createNodeDefinitions(), createOtherAttributeNames())).hasToString("form[@group:]");
        Assertions.assertThat(new FormReferenceDefinition("group", " ", createNodeDefinitions(), createOtherAttributeNames())).hasToString("form[@group: ]");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames())).hasToString("form[@group:id]");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void attributeNamesTest() {
        Assertions.assertThat(FormReferenceDefinition.ATTRIBUTE_NAMES).containsExactly("group", "id");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void childElementNamesTest() {
        Assertions.assertThat(FormReferenceDefinition.CHILD_ELEMENT_NAMES).containsExactly();
    }

    private List<NodeDefinition> createNodeDefinitions(final NodeDefinition... nodeDefinitions) {
        return Arrays.asList(nodeDefinitions);
    }

    private Map<String, String> createOtherAttributeNames(final String... keyValues) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            result.put(keyValues[i], keyValues[i + 1]);
        }
        return result;
    }

}
