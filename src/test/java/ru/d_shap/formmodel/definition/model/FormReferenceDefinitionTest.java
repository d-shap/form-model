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
 * Tests for {@link FormReferenceDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormReferenceDefinitionTest extends BaseFormModelTest {

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
        Assertions.assertThat(new FormReferenceDefinition(null, "id", createNodeDefinitions(), createOtherAttributes()).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormReferenceDefinition("", "id", createNodeDefinitions(), createOtherAttributes()).getGroup()).isEqualTo("");
        Assertions.assertThat(new FormReferenceDefinition(" ", "id", createNodeDefinitions(), createOtherAttributes()).getGroup()).isEqualTo(" ");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes()).getGroup()).isEqualTo("group");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getIdTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributes()).getId()).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "", createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("");
        Assertions.assertThat(new FormReferenceDefinition("group", " ", createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo(" ");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes()).getId()).isEqualTo("id");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getOtherNodeDefinitionsTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), null).getOtherNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(attributeDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(elementDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(singleElementDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null).getOtherNodeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions()).hasSize(2);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(0)).isSameAs(otherNodeDefinition1);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), null).getOtherNodeDefinitions().get(1)).isSameAs(otherNodeDefinition2);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(nodeDefinition), null).getOtherNodeDefinitions()).hasSize(0);
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getAllNodeDefinitionsTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), null).getAllNodeDefinitions()).hasSize(0);

        AttributeDefinition attributeDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(attributeDefinition), null).getAllNodeDefinitions()).hasSize(0);

        ElementDefinition elementDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(elementDefinition), null).getAllNodeDefinitions()).hasSize(0);

        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(singleElementDefinition), null).getAllNodeDefinitions()).hasSize(0);

        FormReferenceDefinition formReferenceDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes());
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(formReferenceDefinition), null).getAllNodeDefinitions()).hasSize(0);

        OtherNodeDefinition otherNodeDefinition = new OtherNodeDefinitionImpl("", true);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(otherNodeDefinition);

        NodeDefinition nodeDefinition = new NodeDefinitionImpl();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(nodeDefinition), null).getAllNodeDefinitions()).hasSize(0);

        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions()).hasSize(1);
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(attributeDefinition, elementDefinition, singleElementDefinition, formReferenceDefinition, otherNodeDefinition, nodeDefinition), null).getAllNodeDefinitions().get(0)).isSameAs(otherNodeDefinition);
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getOtherAttributeNamesTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes()).getOtherAttributeNames()).containsExactly();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name", "value")).getOtherAttributeNames()).containsExactly("name");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2")).getOtherAttributeNames()).containsExactly("name1", "name2");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeNames()).containsExactly("name1", "name2", "name3");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void getOtherAttributeValueTest() {
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, null).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes()).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes()).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes()).getOtherAttributeValue("name")).isNull();

        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name", "value")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name", "value")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name", "value")).getOtherAttributeValue("name")).isEqualTo("value");

        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue(null)).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name")).isNull();
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name1")).isEqualTo("value1");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name2")).isEqualTo("value2");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", null, createOtherAttributes("name1", "value1", "name2", "value2", "name3", "value3")).getOtherAttributeValue("name3")).isEqualTo("value3");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void toStringTest() {
        Assertions.assertThat(new FormReferenceDefinition(null, "id", createNodeDefinitions(), createOtherAttributes())).hasToString("formReference[@:id]");
        Assertions.assertThat(new FormReferenceDefinition("", "id", createNodeDefinitions(), createOtherAttributes())).hasToString("formReference[@:id]");
        Assertions.assertThat(new FormReferenceDefinition(" ", "id", createNodeDefinitions(), createOtherAttributes())).hasToString("formReference[@ :id]");
        Assertions.assertThat(new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributes())).hasToString("formReference[@group:]");
        Assertions.assertThat(new FormReferenceDefinition("group", "", createNodeDefinitions(), createOtherAttributes())).hasToString("formReference[@group:]");
        Assertions.assertThat(new FormReferenceDefinition("group", " ", createNodeDefinitions(), createOtherAttributes())).hasToString("formReference[@group: ]");
        Assertions.assertThat(new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes())).hasToString("formReference[@group:id]");
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void childElementNamesTest() {
        Assertions.assertThat(FormReferenceDefinition.CHILD_ELEMENT_NAMES).containsExactly();
    }

    /**
     * {@link FormReferenceDefinition} class test.
     */
    @Test
    public void attributeNamesTest() {
        Assertions.assertThat(FormReferenceDefinition.ATTRIBUTE_NAMES).containsExactly("group", "id");
    }

}
