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
 * Tests for {@link SingleElementDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class SingleElementDefinitionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public SingleElementDefinitionTest() {
        super();
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("");
        Assertions.assertThat(new SingleElementDefinition("", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("");
        Assertions.assertThat(new SingleElementDefinition(" ", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo(" ");
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("id");
        Assertions.assertThat(new SingleElementDefinition("-id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("-id");
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getCardinalityDefinitionTest() {
        Assertions.assertThat(new SingleElementDefinition("id", null, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED_MULTIPLE, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL_MULTIPLE, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL_MULTIPLE);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getElementDefinitionsTest() {
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getElementDefinitions()).hasSize(0);

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), null).getElementDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition), null).getElementDefinitions()).hasSize(0);

        ElementDefinition elementDefinition1 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1), null).getElementDefinitions()).hasSize(1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1), null).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        ElementDefinition elementDefinition2 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions()).hasSize(2);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions().get(1)).isSameAs(elementDefinition2);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition), null).getElementDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition), null).getElementDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition), null).getElementDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(nodeDefinition), null).getElementDefinitions()).hasSize(0);
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getSingleElementDefinitionsTest() {
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getSingleElementDefinitions()).hasSize(0);

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), null).getSingleElementDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition), null).getSingleElementDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition), null).getSingleElementDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1), null).getSingleElementDefinitions()).hasSize(1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1), null).getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        SingleElementDefinition singleElementDefinition2 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null).getSingleElementDefinitions()).hasSize(2);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null).getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null).getSingleElementDefinitions().get(1)).isSameAs(singleElementDefinition2);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition), null).getSingleElementDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition), null).getSingleElementDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(nodeDefinition), null).getSingleElementDefinitions()).hasSize(0);
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getOtherNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), null).getOtherNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(nodeDefinition), null).getOtherNodeDefinitions()).hasSize(0);
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getAllNodeDefinitionsTest() {
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), null).getAllNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition), null).getAllNodeDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(elementDefinition);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(singleElementDefinition);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition), null).getAllNodeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(otherNodeDefinition);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(nodeDefinition), null).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions()).hasSize(3);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(elementDefinition);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(1)).isSameAs(singleElementDefinition);
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(2)).isSameAs(otherNodeDefinition);
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes()).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name", "value")).getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2")).getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, null).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name", "value")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name", "value")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name", "value")).getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name3")).isEqualTo("value3");
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("single-element[@]");
        Assertions.assertThat(new SingleElementDefinition("", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("single-element[@]");
        Assertions.assertThat(new SingleElementDefinition(" ", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("single-element[@ ]");
        Assertions.assertThat(new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("single-element[@id]");
        Assertions.assertThat(new SingleElementDefinition("-id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("single-element[@-id]");
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void childElementNamesTest() {
        Assertions.assertThat(SingleElementDefinition.CHILD_ELEMENT_NAMES).containsExactly("element", "single-element");
    }

    /**
     * {@link SingleElementDefinition} class test.
     */
    @Test
    public void attributeNamesTest() {
        Assertions.assertThat(SingleElementDefinition.ATTRIBUTE_NAMES).containsExactly("id", "type");
    }

}
