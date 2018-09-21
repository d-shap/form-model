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
 * Tests for {@link NodeData}.
 *
 * @author Dmitry Shapovalov
 */
public final class NodeDataTest {

    /**
     * Test class constructor.
     */
    public NodeDataTest() {
        super();
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getAttributeDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, null).getAttributeDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition1 = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1), null).getAttributeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1), null).getAttributeDefinitions().get(0)).isSameAs(attributeDefinition1);
        AttributeDefinition attributeDefinition2 = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1, attributeDefinition2), null).getAttributeDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1, attributeDefinition2), null).getAttributeDefinitions().get(0)).isSameAs(attributeDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1, attributeDefinition2), null).getAttributeDefinitions().get(1)).isSameAs(attributeDefinition2);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), null).getAttributeDefinitions()).isEmpty();

        ChoiceDefinition choiceDefinition = new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition), null).getAttributeDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), null).getAttributeDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), null).getAttributeDefinitions()).isEmpty();

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), null).getAttributeDefinitions()).isEmpty();
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getAttributeDefinitionsUpdateFailTest() {
        new NodeData(null, null).getAttributeDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getElementDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, null).getElementDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), null).getElementDefinitions()).isEmpty();

        ElementDefinition elementDefinition1 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1), null).getElementDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1), null).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        ElementDefinition elementDefinition2 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions().get(1)).isSameAs(elementDefinition2);

        ChoiceDefinition choiceDefinition = new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition), null).getElementDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), null).getElementDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), null).getElementDefinitions()).isEmpty();

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), null).getElementDefinitions()).isEmpty();
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getElementDefinitionsUpdateFailTest() {
        new NodeData(null, null).getElementDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getChoiceDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, null).getChoiceDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), null).getChoiceDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), null).getChoiceDefinitions()).isEmpty();

        ChoiceDefinition choiceDefinition1 = new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition1), null).getChoiceDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition1), null).getChoiceDefinitions().get(0)).isSameAs(choiceDefinition1);
        ChoiceDefinition choiceDefinition2 = new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition1, choiceDefinition2), null).getChoiceDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition1, choiceDefinition2), null).getChoiceDefinitions().get(0)).isSameAs(choiceDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition1, choiceDefinition2), null).getChoiceDefinitions().get(1)).isSameAs(choiceDefinition2);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), null).getChoiceDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), null).getChoiceDefinitions()).isEmpty();

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), null).getChoiceDefinitions()).isEmpty();
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getChoiceDefinitionsUpdateFailTest() {
        new NodeData(null, null).getChoiceDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getFormReferenceDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, null).getFormReferenceDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), null).getFormReferenceDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), null).getFormReferenceDefinitions()).isEmpty();

        ChoiceDefinition choiceDefinition = new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition), null).getFormReferenceDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition1 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1), null).getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1), null).getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        FormReferenceDefinition formReferenceDefinition2 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null).getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null).getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null).getFormReferenceDefinitions().get(1)).isSameAs(formReferenceDefinition2);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), null).getFormReferenceDefinitions()).isEmpty();

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), null).getFormReferenceDefinitions()).isEmpty();
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getFormReferenceDefinitionsUpdateFailTest() {
        new NodeData(null, null).getFormReferenceDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, null).getOtherNodeDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), null).getOtherNodeDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), null).getOtherNodeDefinitions()).isEmpty();

        ChoiceDefinition choiceDefinition = new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(choiceDefinition), null).getOtherNodeDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributeNames());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), null).getOtherNodeDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), null).getOtherNodeDefinitions()).isEmpty();
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getOtherNodeDefinitionsUpdateFailTest() {
        new NodeData(null, null).getOtherNodeDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new NodeData(null, null).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames()).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name", "value")).getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2")).getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getOtherAttributeNamesUpdateFailTest() {
        new NodeData(null, null).getOtherAttributeNames().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new NodeData(null, null).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, null).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, null).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new NodeData(null, createOtherAttributeNames()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name", "value")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name", "value")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name", "value")).getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new NodeData(null, createOtherAttributeNames("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name3")).isEqualTo("value3");
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
