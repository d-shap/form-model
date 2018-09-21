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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link AttributeDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public class AttributeDefinitionTest {

    /**
     * Test class constructor.
     */
    public AttributeDefinitionTest() {
        super();
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new AttributeDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getId()).isNull();
        Assertions.assertThat(new AttributeDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getId()).isEqualTo("");
        Assertions.assertThat(new AttributeDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getId()).isEqualTo(" ");
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getId()).isEqualTo("id");
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void getLookupTest() {
        Assertions.assertThat(new AttributeDefinition("id", null, CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getLookup()).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getLookup()).isEqualTo("");
        Assertions.assertThat(new AttributeDefinition("id", " ", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getLookup()).isEqualTo(" ");
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getLookup()).isEqualTo("lookup");
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void getCardinalityDefinitionTest() {
        Assertions.assertThat(new AttributeDefinition("id", "lookup", null, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getCardinalityDefinition()).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED_MULTIPLE, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.OPTIONAL, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.OPTIONAL_MULTIPLE, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL_MULTIPLE);
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, null, new HashMap<String, String>()).getOtherNodeDefinitions()).isEmpty();

        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), new HashMap<String, String>()).getOtherNodeDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1), new HashMap<String, String>()).getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1), new HashMap<String, String>()).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);

        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), new HashMap<String, String>()).getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), new HashMap<String, String>()).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), new HashMap<String, String>()).getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), null).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames()).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name", "value")).getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2")).getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), null).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), null).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), null).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name", "value")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name", "value")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name", "value")).getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name3")).isEqualTo("value3");
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new AttributeDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).hasToString("attribute[@]");
        Assertions.assertThat(new AttributeDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).hasToString("attribute[@]");
        Assertions.assertThat(new AttributeDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).hasToString("attribute[@ ]");
        Assertions.assertThat(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>())).hasToString("attribute[@id]");
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void attributeNamesTest() {
        Assertions.assertThat(AttributeDefinition.ATTRIBUTE_NAMES).containsExactly("id", "lookup", "type");
    }

    /**
     * {@link AttributeDefinition} class test.
     */
    @Test
    public void childElementNamesTest() {
        Assertions.assertThat(AttributeDefinition.CHILD_ELEMENT_NAMES).containsExactly();
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
