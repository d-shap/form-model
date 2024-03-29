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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.fm.formmodel.BaseFormModelTest;

/**
 * Tests for {@link NodeData}.
 *
 * @author Dmitry Shapovalov
 */
public final class NodeDataTest extends BaseFormModelTest {

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
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);

        Assertions.assertThat(new NodeData(createNodeDefinitions(), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition1 = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1), createValidElements("value"), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions().get(0)).isSameAs(attributeDefinition1);
        AttributeDefinition attributeDefinition2 = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1, attributeDefinition2), createValidElements("value"), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1, attributeDefinition2), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1, attributeDefinition2), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions().get(0)).isSameAs(attributeDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition1, attributeDefinition2), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions().get(1)).isSameAs(attributeDefinition2);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), createValidElements(), null, createSkipAttributes()).getAttributeDefinitions()).hasSize(0);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getAttributeDefinitionsUpdateFailTest() {
        new NodeData(null, createValidElements(), null, createSkipAttributes()).getAttributeDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getElementDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);

        Assertions.assertThat(new NodeData(createNodeDefinitions(), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);

        ElementDefinition elementDefinition1 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1), createValidElements("value"), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1), createValidElements(), null, createSkipAttributes()).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        ElementDefinition elementDefinition2 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1, elementDefinition2), createValidElements("value"), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1, elementDefinition2), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1, elementDefinition2), createValidElements(), null, createSkipAttributes()).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition1, elementDefinition2), createValidElements(), null, createSkipAttributes()).getElementDefinitions().get(1)).isSameAs(elementDefinition2);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), createValidElements(), null, createSkipAttributes()).getElementDefinitions()).hasSize(0);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getElementDefinitionsUpdateFailTest() {
        new NodeData(null, createValidElements(), null, createSkipAttributes()).getElementDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getSingleElementDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);

        Assertions.assertThat(new NodeData(createNodeDefinitions(), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition1), createValidElements("value"), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition1), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition1), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        SingleElementDefinition singleElementDefinition2 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), createValidElements("value"), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions().get(1)).isSameAs(singleElementDefinition2);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions()).hasSize(0);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getSingleElementDefinitionsUpdateFailTest() {
        new NodeData(null, createValidElements(), null, createSkipAttributes()).getSingleElementDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getFormReferenceDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);

        Assertions.assertThat(new NodeData(createNodeDefinitions(), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition1 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1), createValidElements("value"), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        FormReferenceDefinition formReferenceDefinition2 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), createValidElements("value"), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions().get(1)).isSameAs(formReferenceDefinition2);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions()).hasSize(0);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getFormReferenceDefinitionsUpdateFailTest() {
        new NodeData(null, createValidElements(), null, createSkipAttributes()).getFormReferenceDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new NodeData(createNodeDefinitions(), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions()).hasSize(0);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getOtherNodeDefinitionsUpdateFailTest() {
        new NodeData(null, createValidElements(), null, createSkipAttributes()).getOtherNodeDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getAllNodeDefinitionsTest() {
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new NodeData(createNodeDefinitions(), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(0)).isSameAs(attributeDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition), createValidElements("value"), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(0)).isSameAs(elementDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(elementDefinition), createValidElements("value"), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(0)).isSameAs(singleElementDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(singleElementDefinition), createValidElements("value"), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(0)).isSameAs(formReferenceDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(formReferenceDefinition), createValidElements("value"), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(0)).isSameAs(otherNodeDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements("value"), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new NodeData(createNodeDefinitions(otherNodeDefinition), createValidElements("value"), null, createSkipAttributes()).getAllNodeDefinitions().get(0)).isSameAs(otherNodeDefinition);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);
        Assertions.assertThat(new NodeData(createNodeDefinitions(nodeDefinition), createValidElements("value"), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions()).hasSize(5);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(0)).isSameAs(attributeDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(1)).isSameAs(elementDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(2)).isSameAs(singleElementDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(3)).isSameAs(formReferenceDefinition);
        Assertions.assertThat(new NodeData(createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().get(4)).isSameAs(otherNodeDefinition);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getAllNodeDefinitionsUpdateFailTest() {
        new NodeData(null, createValidElements(), null, createSkipAttributes()).getAllNodeDefinitions().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes(), createSkipAttributes()).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name", "value"), createSkipAttributes()).getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2"), createSkipAttributes()).getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes()).getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1")).getOtherAttributeNames()).containsExactly("name2", "name3");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2")).getOtherAttributeNames()).containsExactly("name3");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2", "name3")).getOtherAttributeNames()).hasSize(0);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void getOtherAttributeNamesUpdateFailTest() {
        new NodeData(null, createValidElements(), null, createSkipAttributes()).getOtherAttributeNames().add(null);
    }

    /**
     * {@link NodeData} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), null, createSkipAttributes()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes(), createSkipAttributes()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes(), createSkipAttributes()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes(), createSkipAttributes()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name", "value"), createSkipAttributes()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name", "value"), createSkipAttributes()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name", "value"), createSkipAttributes()).getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes()).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes()).getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes()).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes()).getOtherAttributeValue("name3")).isEqualTo("value3");

        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1")).getOtherAttributeValue("name1")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1")).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1")).getOtherAttributeValue("name3")).isEqualTo("value3");

        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2")).getOtherAttributeValue("name1")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2")).getOtherAttributeValue("name2")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2")).getOtherAttributeValue("name3")).isEqualTo("value3");

        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2", "name3")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2", "name3")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2", "name3")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2", "name3")).getOtherAttributeValue("name1")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2", "name3")).getOtherAttributeValue("name2")).isNull();
        Assertions.assertThat(new NodeData(null, createValidElements(), createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), createSkipAttributes("name1", "name2", "name3")).getOtherAttributeValue("name3")).isNull();
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = NullPointerException.class)
    public void validElementsNullFailTest() {
        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        new NodeData(createNodeDefinitions(elementDefinition), null, createOtherAttributes(), createSkipAttributes());
    }

    /**
     * {@link NodeData} class test.
     */
    @Test(expected = NullPointerException.class)
    public void skipAttributesNullFailTest() {
        new NodeData(createNodeDefinitions(), createValidElements(), createOtherAttributes("name", "value"), null);
    }

}
