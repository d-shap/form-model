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
 * Tests for {@link FormDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionTest() {
        super();
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getGroupTest() {
        Assertions.assertThat(new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source").getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinition("", "id", createNodeDefinitions(), createOtherAttributes(), "source").getGroup()).isEqualTo("");
        Assertions.assertThat(new FormDefinition(" ", "id", createNodeDefinitions(), createOtherAttributes(), "source").getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source").getGroup()).isEqualTo("group");
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new FormDefinition("group", null, createNodeDefinitions(), createOtherAttributes(), "source").getId()).isNull();
        Assertions.assertThat(new FormDefinition("group", "", createNodeDefinitions(), createOtherAttributes(), "source").getId()).isEqualTo("");
        Assertions.assertThat(new FormDefinition("group", " ", createNodeDefinitions(), createOtherAttributes(), "source").getId()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source").getId()).isEqualTo("id");
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getElementDefinitionsTest() {
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getElementDefinitions()).isEmpty();

        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), null, "source").getElementDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition), null, "source").getElementDefinitions()).isEmpty();

        ElementDefinition elementDefinition1 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1), null, "source").getElementDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1), null, "source").getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        ElementDefinition elementDefinition2 = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1, elementDefinition2), null, "source").getElementDefinitions()).hasSize(2);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1, elementDefinition2), null, "source").getElementDefinitions().get(0)).isSameAs(elementDefinition1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1, elementDefinition2), null, "source").getElementDefinitions().get(1)).isSameAs(elementDefinition2);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition), null, "source").getElementDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null, "source").getElementDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition), null, "source").getElementDefinitions()).isEmpty();

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(nodeDefinition), null, "source").getElementDefinitions()).isEmpty();
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getSingleElementDefinitionsTest() {
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getSingleElementDefinitions()).isEmpty();

        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), null, "source").getSingleElementDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition), null, "source").getSingleElementDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition), null, "source").getSingleElementDefinitions()).isEmpty();

        SingleElementDefinition singleElementDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1), null, "source").getSingleElementDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1), null, "source").getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        SingleElementDefinition singleElementDefinition2 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null, "source").getSingleElementDefinitions()).hasSize(2);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null, "source").getSingleElementDefinitions().get(0)).isSameAs(singleElementDefinition1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), null, "source").getSingleElementDefinitions().get(1)).isSameAs(singleElementDefinition2);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null, "source").getSingleElementDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition), null, "source").getSingleElementDefinitions()).isEmpty();

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(nodeDefinition), null, "source").getSingleElementDefinitions()).isEmpty();
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getFormReferenceDefinitionsTest() {
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getFormReferenceDefinitions()).isEmpty();

        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), null, "source").getFormReferenceDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition), null, "source").getFormReferenceDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition), null, "source").getFormReferenceDefinitions()).isEmpty();

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition), null, "source").getFormReferenceDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition1 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1), null, "source").getFormReferenceDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1), null, "source").getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        FormReferenceDefinition formReferenceDefinition2 = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null, "source").getFormReferenceDefinitions()).hasSize(2);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null, "source").getFormReferenceDefinitions().get(0)).isSameAs(formReferenceDefinition1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), null, "source").getFormReferenceDefinitions().get(1)).isSameAs(formReferenceDefinition2);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition), null, "source").getFormReferenceDefinitions()).isEmpty();

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(nodeDefinition), null, "source").getFormReferenceDefinitions()).isEmpty();
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getOtherNodeDefinitions()).isEmpty();

        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), null, "source").getOtherNodeDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition), null, "source").getOtherNodeDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition), null, "source").getOtherNodeDefinitions()).isEmpty();

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition), null, "source").getOtherNodeDefinitions()).isEmpty();

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null, "source").getOtherNodeDefinitions()).isEmpty();

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1), null, "source").getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1), null, "source").getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null, "source").getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null, "source").getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null, "source").getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(nodeDefinition), null, "source").getOtherNodeDefinitions()).isEmpty();
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getAllNodeDefinitions() {
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getAllNodeDefinitions()).isEmpty();

        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), null, "source").getAllNodeDefinitions()).isEmpty();

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition), null, "source").getAllNodeDefinitions()).isEmpty();

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition), null, "source").getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition), null, "source").getAllNodeDefinitions().get(0)).isSameAs(elementDefinition);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition), null, "source").getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition), null, "source").getAllNodeDefinitions().get(0)).isSameAs(singleElementDefinition);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null, "source").getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null, "source").getAllNodeDefinitions().get(0)).isSameAs(formReferenceDefinition);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition), null, "source").getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition), null, "source").getAllNodeDefinitions().get(0)).isSameAs(otherNodeDefinition);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(nodeDefinition), null, "source").getAllNodeDefinitions()).isEmpty();

        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null, "source").getAllNodeDefinitions()).hasSize(4);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null, "source").getAllNodeDefinitions().get(0)).isSameAs(elementDefinition);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null, "source").getAllNodeDefinitions().get(1)).isSameAs(singleElementDefinition);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null, "source").getAllNodeDefinitions().get(2)).isSameAs(formReferenceDefinition);
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null, "source").getAllNodeDefinitions().get(3)).isSameAs(otherNodeDefinition);
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes(), "source").getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name", "value"), "source").getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2"), "source").getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), "source").getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, null, "source").getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes(), "source").getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes(), "source").getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes(), "source").getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name", "value"), "source").getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name", "value"), "source").getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name", "value"), "source").getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), "source").getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), "source").getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), "source").getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), "source").getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), "source").getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new FormDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3"), "source").getOtherAttributeValue("name3")).isEqualTo("value3");
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void getSourceTest() {
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), null).getSource()).isNull();
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "").getSource()).isEqualTo("");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), " ").getSource()).isEqualTo(" ");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source").getSource()).isEqualTo("source");
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source")).hasToString("{source}form[@:id]");
        Assertions.assertThat(new FormDefinition("", "id", createNodeDefinitions(), createOtherAttributes(), "source")).hasToString("{source}form[@:id]");
        Assertions.assertThat(new FormDefinition(" ", "id", createNodeDefinitions(), createOtherAttributes(), "source")).hasToString("{source}form[@ :id]");
        Assertions.assertThat(new FormDefinition("group", null, createNodeDefinitions(), createOtherAttributes(), "source")).hasToString("{source}form[@group:]");
        Assertions.assertThat(new FormDefinition("group", "", createNodeDefinitions(), createOtherAttributes(), "source")).hasToString("{source}form[@group:]");
        Assertions.assertThat(new FormDefinition("group", " ", createNodeDefinitions(), createOtherAttributes(), "source")).hasToString("{source}form[@group: ]");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), null)).hasToString("{}form[@group:id]");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "")).hasToString("{}form[@group:id]");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), " ")).hasToString("{ }form[@group:id]");
        Assertions.assertThat(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source")).hasToString("{source}form[@group:id]");
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void childElementNamesTest() {
        Assertions.assertThat(FormDefinition.CHILD_ELEMENT_NAMES).containsExactly("element", "singleElement", "formReference");
    }

    /**
     * {@link FormDefinition} class test.
     */
    @Test
    public void attributeNamesTest() {
        Assertions.assertThat(FormDefinition.ATTRIBUTE_NAMES).containsExactly("group", "id");
    }

}
