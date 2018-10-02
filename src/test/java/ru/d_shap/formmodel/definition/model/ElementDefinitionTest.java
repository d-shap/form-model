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
 * Tests for {@link ElementDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class ElementDefinitionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public ElementDefinitionTest() {
        super();
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("");
        Assertions.assertThat(new ElementDefinition("", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("");
        Assertions.assertThat(new ElementDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo(" ");
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("id");
        Assertions.assertThat(new ElementDefinition("-id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("-id");
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getLookupTest() {
        Assertions.assertThat(new ElementDefinition("id", null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getLookup()).isNull();
        Assertions.assertThat(new ElementDefinition("id", "", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getLookup()).isEqualTo("");
        Assertions.assertThat(new ElementDefinition("id", " ", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getLookup()).isEqualTo(" ");
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getLookup()).isEqualTo("lookup");
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getCardinalityDefinitionTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", null, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED_MULTIPLE, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.REQUIRED_MULTIPLE);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.OPTIONAL_MULTIPLE, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.OPTIONAL_MULTIPLE);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), createOtherAttributes()).getCardinalityDefinition()).isSameAs(CardinalityDefinition.PROHIBITED);
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getAttributeDefinitionsTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getAttributeDefinitions()).hasSize(0);

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), null).getAttributeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition1 = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition1), null).getAttributeDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition1), null).getAttributeDefinitions().get(0)).isSameAs(attributeDefinition1);
        AttributeDefinition attributeDefinition2 = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition1, attributeDefinition2), null).getAttributeDefinitions()).hasSize(2);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition1, attributeDefinition2), null).getAttributeDefinitions().get(0)).isSameAs(attributeDefinition1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition1, attributeDefinition2), null).getAttributeDefinitions().get(1)).isSameAs(attributeDefinition2);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition), null).getAttributeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition), null).getAttributeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition), null).getAttributeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition), null).getAttributeDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(nodeDefinition), null).getAttributeDefinitions()).hasSize(0);
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getElementDefinitionsTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getElementDefinitions()).hasSize(0);

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), null).getElementDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition), null).getElementDefinitions()).hasSize(0);

        ElementDefinition elementDefinition1 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition1), null).getElementDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition1), null).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        ElementDefinition elementDefinition2 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions()).hasSize(2);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition1, elementDefinition2), null).getElementDefinitions().get(1)).isSameAs(elementDefinition2);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition), null).getElementDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition), null).getElementDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition), null).getElementDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(nodeDefinition), null).getElementDefinitions()).hasSize(0);
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getSingleElementDefinitionsTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getSingleElementDefinitions()).hasSize(0);

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), null).getSingleElementDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition), null).getSingleElementDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition), null).getSingleElementDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition1), null).getSingleElementDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition1), null).getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        SingleElementDefinition singleElementDefinition2 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null).getSingleElementDefinitions()).hasSize(2);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null).getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null).getSingleElementDefinitions().get(1)).isSameAs(singleElementDefinition2);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition), null).getSingleElementDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition), null).getSingleElementDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(nodeDefinition), null).getSingleElementDefinitions()).hasSize(0);
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getFormReferenceDefinitionsTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getFormReferenceDefinitions()).hasSize(0);

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), null).getFormReferenceDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition), null).getFormReferenceDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition), null).getFormReferenceDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition), null).getFormReferenceDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition1 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition1), null).getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition1), null).getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        FormReferenceDefinition formReferenceDefinition2 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null).getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null).getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null).getFormReferenceDefinitions().get(1)).isSameAs(formReferenceDefinition2);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition), null).getFormReferenceDefinitions()).hasSize(0);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(nodeDefinition), null).getFormReferenceDefinitions()).hasSize(0);
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getOtherNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), null).getOtherNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(nodeDefinition), null).getOtherNodeDefinitions()).hasSize(0);
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getAllNodeDefinitionsTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), null).getAllNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(attributeDefinition);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(elementDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(elementDefinition);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(singleElementDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(singleElementDefinition);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(formReferenceDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(formReferenceDefinition);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(otherNodeDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(otherNodeDefinition);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(nodeDefinition), null).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions()).hasSize(5);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(attributeDefinition);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(1)).isSameAs(elementDefinition);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(2)).isSameAs(singleElementDefinition);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(3)).isSameAs(formReferenceDefinition);
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(4)).isSameAs(otherNodeDefinition);
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes()).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name", "value")).getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2")).getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, null).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name", "value")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name", "value")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name", "value")).getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name3")).isEqualTo("value3");
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("element[@]");
        Assertions.assertThat(new ElementDefinition("", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("element[@]");
        Assertions.assertThat(new ElementDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("element[@ ]");
        Assertions.assertThat(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("element[@id]");
        Assertions.assertThat(new ElementDefinition("-id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes())).hasToString("element[@-id]");
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void childElementNamesTest() {
        Assertions.assertThat(ElementDefinition.CHILD_ELEMENT_NAMES).containsExactly("attribute", "element", "singleElement", "formReference");
    }

    /**
     * {@link ElementDefinition} class test.
     */
    @Test
    public void attributeNamesTest() {
        Assertions.assertThat(ElementDefinition.ATTRIBUTE_NAMES).containsExactly("id", "lookup", "type");
    }

}
