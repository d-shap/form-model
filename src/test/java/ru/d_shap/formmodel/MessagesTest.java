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
package ru.d_shap.formmodel;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.w3c.dom.Document;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.NodeDefinition;

/**
 * Tests for {@link Messages}.
 *
 * @author Dmitry Shapovalov
 */
public final class MessagesTest {

    /**
     * Test class constructor.
     */
    public MessagesTest() {
        super();
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(Messages.class).hasOnePrivateConstructor();
        Assertions.assertThat(Messages.Representation.class).hasOnePrivateConstructor();
        Assertions.assertThat(Messages.Validation.class).hasOnePrivateConstructor();
        Assertions.assertThat(Messages.Binding.class).hasOnePrivateConstructor();
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getFormDefinitionRepresentationTest() {
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation(null, "group", "id")).isEqualTo("{}form[@group:id]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("", "group", "id")).isEqualTo("{}form[@group:id]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation(" ", "group", "id")).isEqualTo("{ }form[@group:id]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("source", null, "id")).isEqualTo("{source}form[@:id]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("source", "", "id")).isEqualTo("{source}form[@:id]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("source", " ", "id")).isEqualTo("{source}form[@ :id]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("source", "group", null)).isEqualTo("{source}form[@group:]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("source", "group", "")).isEqualTo("{source}form[@group:]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("source", "group", " ")).isEqualTo("{source}form[@group: ]");
        Assertions.assertThat(Messages.Representation.getFormDefinitionRepresentation("source", "group", "id")).isEqualTo("{source}form[@group:id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getElementDefinitionRepresentationTest() {
        Assertions.assertThat(Messages.Representation.getElementDefinitionRepresentation(null)).isEqualTo("element[@]");
        Assertions.assertThat(Messages.Representation.getElementDefinitionRepresentation("")).isEqualTo("element[@]");
        Assertions.assertThat(Messages.Representation.getElementDefinitionRepresentation(" ")).isEqualTo("element[@ ]");
        Assertions.assertThat(Messages.Representation.getElementDefinitionRepresentation("id")).isEqualTo("element[@id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getChoiceDefinitionRepresentationTest() {
        Assertions.assertThat(Messages.Representation.getChoiceDefinitionRepresentation(null)).isEqualTo("choice[@]");
        Assertions.assertThat(Messages.Representation.getChoiceDefinitionRepresentation("")).isEqualTo("choice[@]");
        Assertions.assertThat(Messages.Representation.getChoiceDefinitionRepresentation(" ")).isEqualTo("choice[@ ]");
        Assertions.assertThat(Messages.Representation.getChoiceDefinitionRepresentation("id")).isEqualTo("choice[@id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getFormReferenceDefinitionRepresentationTest() {
        Assertions.assertThat(Messages.Representation.getFormReferenceDefinitionRepresentation(null, "id")).isEqualTo("form[@:id]");
        Assertions.assertThat(Messages.Representation.getFormReferenceDefinitionRepresentation("", "id")).isEqualTo("form[@:id]");
        Assertions.assertThat(Messages.Representation.getFormReferenceDefinitionRepresentation(" ", "id")).isEqualTo("form[@ :id]");
        Assertions.assertThat(Messages.Representation.getFormReferenceDefinitionRepresentation("group", null)).isEqualTo("form[@group:]");
        Assertions.assertThat(Messages.Representation.getFormReferenceDefinitionRepresentation("group", "")).isEqualTo("form[@group:]");
        Assertions.assertThat(Messages.Representation.getFormReferenceDefinitionRepresentation("group", " ")).isEqualTo("form[@group: ]");
        Assertions.assertThat(Messages.Representation.getFormReferenceDefinitionRepresentation("group", "id")).isEqualTo("form[@group:id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getAttributeDefinitionRepresentationTest() {
        Assertions.assertThat(Messages.Representation.getAttributeDefinitionRepresentation(null)).isEqualTo("attribute[@]");
        Assertions.assertThat(Messages.Representation.getAttributeDefinitionRepresentation("")).isEqualTo("attribute[@]");
        Assertions.assertThat(Messages.Representation.getAttributeDefinitionRepresentation(" ")).isEqualTo("attribute[@ ]");
        Assertions.assertThat(Messages.Representation.getAttributeDefinitionRepresentation("id")).isEqualTo("attribute[@id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getIdRepresentationTest() {
        Assertions.assertThat(Messages.Representation.getIdRepresentation(null)).isEqualTo("@");
        Assertions.assertThat(Messages.Representation.getIdRepresentation("")).isEqualTo("@");
        Assertions.assertThat(Messages.Representation.getIdRepresentation(" ")).isEqualTo("@ ");
        Assertions.assertThat(Messages.Representation.getIdRepresentation("id")).isEqualTo("@id");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getIdWithGroupRepresentationTest() {
        Assertions.assertThat(Messages.Representation.getIdRepresentation(null, "id")).isEqualTo("@:id");
        Assertions.assertThat(Messages.Representation.getIdRepresentation("", "id")).isEqualTo("@:id");
        Assertions.assertThat(Messages.Representation.getIdRepresentation(" ", "id")).isEqualTo("@ :id");
        Assertions.assertThat(Messages.Representation.getIdRepresentation("group", null)).isEqualTo("@group:");
        Assertions.assertThat(Messages.Representation.getIdRepresentation("group", "")).isEqualTo("@group:");
        Assertions.assertThat(Messages.Representation.getIdRepresentation("group", " ")).isEqualTo("@group: ");
        Assertions.assertThat(Messages.Representation.getIdRepresentation("group", "id")).isEqualTo("@group:id");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getXmlElementRepresentationTest() {
        Document document = new XmlDocumentBuilder().newDocument();
        Assertions.assertThat(Messages.Representation.getXmlElementRepresentation(null)).isEqualTo("null");
        Assertions.assertThat(Messages.Representation.getXmlElementRepresentation(document.createElement("someElement"))).isEqualTo("someElement");
        Assertions.assertThat(Messages.Representation.getXmlElementRepresentation(document.createElementNS("http://example.com", "someElement"))).isEqualTo("{http://example.com}someElement");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getFormDefinitionElementIsNotValidMessageTest() {
        Document document = new XmlDocumentBuilder().newDocument();
        Assertions.assertThat(Messages.Validation.getFormDefinitionElementIsNotValidMessage(null)).isEqualTo("[Form definition element is not valid: null]");
        Assertions.assertThat(Messages.Validation.getFormDefinitionElementIsNotValidMessage(document.createElement("someElement"))).isEqualTo("[Form definition element is not valid: someElement]");
        Assertions.assertThat(Messages.Validation.getFormDefinitionElementIsNotValidMessage(document.createElementNS("http://example.com", "someElement"))).isEqualTo("[Form definition element is not valid: {http://example.com}someElement]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getElementDefinitionElementIsNotValidMessageTest() {
        Document document = new XmlDocumentBuilder().newDocument();
        Assertions.assertThat(Messages.Validation.getElementDefinitionElementIsNotValidMessage(null)).isEqualTo("[Element definition element is not valid: null]");
        Assertions.assertThat(Messages.Validation.getElementDefinitionElementIsNotValidMessage(document.createElement("someElement"))).isEqualTo("[Element definition element is not valid: someElement]");
        Assertions.assertThat(Messages.Validation.getElementDefinitionElementIsNotValidMessage(document.createElementNS("http://example.com", "someElement"))).isEqualTo("[Element definition element is not valid: {http://example.com}someElement]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getChoiceDefinitionElementIsNotValidMessageTest() {
        Document document = new XmlDocumentBuilder().newDocument();
        Assertions.assertThat(Messages.Validation.getChoiceDefinitionElementIsNotValidMessage(null)).isEqualTo("[Choice definition element is not valid: null]");
        Assertions.assertThat(Messages.Validation.getChoiceDefinitionElementIsNotValidMessage(document.createElement("someElement"))).isEqualTo("[Choice definition element is not valid: someElement]");
        Assertions.assertThat(Messages.Validation.getChoiceDefinitionElementIsNotValidMessage(document.createElementNS("http://example.com", "someElement"))).isEqualTo("[Choice definition element is not valid: {http://example.com}someElement]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getFormReferenceDefinitionElementIsNotValidMessageTest() {
        Document document = new XmlDocumentBuilder().newDocument();
        Assertions.assertThat(Messages.Validation.getFormReferenceDefinitionElementIsNotValidMessage(null)).isEqualTo("[Form reference definition element is not valid: null]");
        Assertions.assertThat(Messages.Validation.getFormReferenceDefinitionElementIsNotValidMessage(document.createElement("someElement"))).isEqualTo("[Form reference definition element is not valid: someElement]");
        Assertions.assertThat(Messages.Validation.getFormReferenceDefinitionElementIsNotValidMessage(document.createElementNS("http://example.com", "someElement"))).isEqualTo("[Form reference definition element is not valid: {http://example.com}someElement]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getAttributeDefinitionElementIsNotValidMessageTest() {
        Document document = new XmlDocumentBuilder().newDocument();
        Assertions.assertThat(Messages.Validation.getAttributeDefinitionElementIsNotValidMessage(null)).isEqualTo("[Attribute definition element is not valid: null]");
        Assertions.assertThat(Messages.Validation.getAttributeDefinitionElementIsNotValidMessage(document.createElement("someElement"))).isEqualTo("[Attribute definition element is not valid: someElement]");
        Assertions.assertThat(Messages.Validation.getAttributeDefinitionElementIsNotValidMessage(document.createElementNS("http://example.com", "someElement"))).isEqualTo("[Attribute definition element is not valid: {http://example.com}someElement]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getChildElementIsNotValidMessageTest() {
        Document document = new XmlDocumentBuilder().newDocument();
        Assertions.assertThat(Messages.Validation.getChildElementIsNotValidMessage(null)).isEqualTo("[Child element is not valid: null]");
        Assertions.assertThat(Messages.Validation.getChildElementIsNotValidMessage(document.createElement("someElement"))).isEqualTo("[Child element is not valid: someElement]");
        Assertions.assertThat(Messages.Validation.getChildElementIsNotValidMessage(document.createElementNS("http://example.com", "someElement"))).isEqualTo("[Child element is not valid: {http://example.com}someElement]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getFormIsNotUniqueMessageTest() {
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(null, "source1", "source2")).isEqualTo("[Form is not unique: null, (source1), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey(null, "id"), "source1", "source2")).isEqualTo("[Form is not unique: @:id, (source1), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("", "id"), "source1", "source2")).isEqualTo("[Form is not unique: @:id, (source1), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey(" ", "id"), "source1", "source2")).isEqualTo("[Form is not unique: @ :id, (source1), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", null), "source1", "source2")).isEqualTo("[Form is not unique: @group:, (source1), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", ""), "source1", "source2")).isEqualTo("[Form is not unique: @group:, (source1), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", " "), "source1", "source2")).isEqualTo("[Form is not unique: @group: , (source1), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", "id"), null, "source2")).isEqualTo("[Form is not unique: @group:id, (null), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", "id"), "", "source2")).isEqualTo("[Form is not unique: @group:id, (), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", "id"), " ", "source2")).isEqualTo("[Form is not unique: @group:id, ( ), (source2)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", "id"), "source1", null)).isEqualTo("[Form is not unique: @group:id, (source1), (null)]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", "id"), "source1", "")).isEqualTo("[Form is not unique: @group:id, (source1), ()]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", "id"), "source1", " ")).isEqualTo("[Form is not unique: @group:id, (source1), ( )]");
        Assertions.assertThat(Messages.Validation.getFormIsNotUniqueMessage(new FormDefinitionKey("group", "id"), "source1", "source2")).isEqualTo("[Form is not unique: @group:id, (source1), (source2)]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getGroupIsNotValidMessageTest() {
        Assertions.assertThat(Messages.Validation.getGroupIsNotValidMessage(null)).isEqualTo("[Group is not valid: null]");
        Assertions.assertThat(Messages.Validation.getGroupIsNotValidMessage("")).isEqualTo("[Group is not valid: ]");
        Assertions.assertThat(Messages.Validation.getGroupIsNotValidMessage(" ")).isEqualTo("[Group is not valid:  ]");
        Assertions.assertThat(Messages.Validation.getGroupIsNotValidMessage("group")).isEqualTo("[Group is not valid: group]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getIdIsNotEmptyMessageTest() {
        Assertions.assertThat(Messages.Validation.getIdIsNotEmptyMessage(null)).isEqualTo("[ID is not empty: null]");
        Assertions.assertThat(Messages.Validation.getIdIsNotEmptyMessage("")).isEqualTo("[ID is not empty: ]");
        Assertions.assertThat(Messages.Validation.getIdIsNotEmptyMessage(" ")).isEqualTo("[ID is not empty:  ]");
        Assertions.assertThat(Messages.Validation.getIdIsNotEmptyMessage("id")).isEqualTo("[ID is not empty: id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getIdIsEmptyMessageTest() {
        Assertions.assertThat(Messages.Validation.getIdIsEmptyMessage()).isEqualTo("[ID is empty]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getIdIsNotValidMessageTest() {
        Assertions.assertThat(Messages.Validation.getIdIsNotValidMessage(null)).isEqualTo("[ID is not valid: null]");
        Assertions.assertThat(Messages.Validation.getIdIsNotValidMessage("")).isEqualTo("[ID is not valid: ]");
        Assertions.assertThat(Messages.Validation.getIdIsNotValidMessage(" ")).isEqualTo("[ID is not valid:  ]");
        Assertions.assertThat(Messages.Validation.getIdIsNotValidMessage("id")).isEqualTo("[ID is not valid: id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getIdIsNotUniqueMessageTest() {
        Assertions.assertThat(Messages.Validation.getIdIsNotUniqueMessage(null)).isEqualTo("[ID is not unique: null]");
        Assertions.assertThat(Messages.Validation.getIdIsNotUniqueMessage("")).isEqualTo("[ID is not unique: ]");
        Assertions.assertThat(Messages.Validation.getIdIsNotUniqueMessage(" ")).isEqualTo("[ID is not unique:  ]");
        Assertions.assertThat(Messages.Validation.getIdIsNotUniqueMessage("id")).isEqualTo("[ID is not unique: id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getLookupIsEmptyMessageTest() {
        Assertions.assertThat(Messages.Validation.getLookupIsEmptyMessage()).isEqualTo("[Lookup is empty]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getCardinalityIsEmptyMessageTest() {
        Assertions.assertThat(Messages.Validation.getCardinalityIsEmptyMessage()).isEqualTo("[Cardinality is empty]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getCardinalityIsNotValidMessageTest() {
        Assertions.assertThat(Messages.Validation.getCardinalityIsNotValidMessage(null)).isEqualTo("[Cardinality is not valid: null]");
        Assertions.assertThat(Messages.Validation.getCardinalityIsNotValidMessage("")).isEqualTo("[Cardinality is not valid: ]");
        Assertions.assertThat(Messages.Validation.getCardinalityIsNotValidMessage(" ")).isEqualTo("[Cardinality is not valid:  ]");
        Assertions.assertThat(Messages.Validation.getCardinalityIsNotValidMessage("cardinality")).isEqualTo("[Cardinality is not valid: cardinality]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getSourceIsEmptyMessageTest() {
        Assertions.assertThat(Messages.Validation.getSourceIsEmptyMessage()).isEqualTo("[Source is empty]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getFormReferenceIsNotUniqueMessageTest() {
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(null)).isEqualTo("[Form reference is not unique: null]");
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(new FormDefinitionKey(null, "id"))).isEqualTo("[Form reference is not unique: @:id]");
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(new FormDefinitionKey("", "id"))).isEqualTo("[Form reference is not unique: @:id]");
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(new FormDefinitionKey(" ", "id"))).isEqualTo("[Form reference is not unique: @ :id]");
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(new FormDefinitionKey("group", null))).isEqualTo("[Form reference is not unique: @group:]");
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(new FormDefinitionKey("group", ""))).isEqualTo("[Form reference is not unique: @group:]");
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(new FormDefinitionKey("group", " "))).isEqualTo("[Form reference is not unique: @group: ]");
        Assertions.assertThat(Messages.Validation.getFormReferenceIsNotUniqueMessage(new FormDefinitionKey("group", "id"))).isEqualTo("[Form reference is not unique: @group:id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getUnresolvedFormReferenceMessageTest() {
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(null)).isEqualTo("[Form reference can not be resolved: null]");
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(new FormDefinitionKey(null, "id"))).isEqualTo("[Form reference can not be resolved: @:id]");
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(new FormDefinitionKey("", "id"))).isEqualTo("[Form reference can not be resolved: @:id]");
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(new FormDefinitionKey(" ", "id"))).isEqualTo("[Form reference can not be resolved: @ :id]");
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(new FormDefinitionKey("group", null))).isEqualTo("[Form reference can not be resolved: @group:]");
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(new FormDefinitionKey("group", ""))).isEqualTo("[Form reference can not be resolved: @group:]");
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(new FormDefinitionKey("group", " "))).isEqualTo("[Form reference can not be resolved: @group: ]");
        Assertions.assertThat(Messages.Validation.getUnresolvedFormReferenceMessage(new FormDefinitionKey("group", "id"))).isEqualTo("[Form reference can not be resolved: @group:id]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getRequiredElementIsNotPresentMessageTest() {
        Assertions.assertThat(Messages.Binding.getRequiredElementIsNotPresentMessage(null)).isEqualTo("[Required element is not present: null]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsNotPresentMessage(new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is not present: element[@]]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsNotPresentMessage(new ElementDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is not present: element[@]]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsNotPresentMessage(new ElementDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is not present: element[@ ]]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsNotPresentMessage(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is not present: element[@id]]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getRequiredElementIsPresentMoreThanOnceMessageTest() {
        Assertions.assertThat(Messages.Binding.getRequiredElementIsPresentMoreThanOnceMessage(null)).isEqualTo("[Required element is present more than once: null]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsPresentMoreThanOnceMessage(new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is present more than once: element[@]]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsPresentMoreThanOnceMessage(new ElementDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is present more than once: element[@]]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsPresentMoreThanOnceMessage(new ElementDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is present more than once: element[@ ]]");
        Assertions.assertThat(Messages.Binding.getRequiredElementIsPresentMoreThanOnceMessage(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required element is present more than once: element[@id]]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getOptionalElementIsPresentMoreThanOnceMessageTest() {
        Assertions.assertThat(Messages.Binding.getOptionalElementIsPresentMoreThanOnceMessage(null)).isEqualTo("[Optional element is present more than once: null]");
        Assertions.assertThat(Messages.Binding.getOptionalElementIsPresentMoreThanOnceMessage(new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Optional element is present more than once: element[@]]");
        Assertions.assertThat(Messages.Binding.getOptionalElementIsPresentMoreThanOnceMessage(new ElementDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Optional element is present more than once: element[@]]");
        Assertions.assertThat(Messages.Binding.getOptionalElementIsPresentMoreThanOnceMessage(new ElementDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Optional element is present more than once: element[@ ]]");
        Assertions.assertThat(Messages.Binding.getOptionalElementIsPresentMoreThanOnceMessage(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Optional element is present more than once: element[@id]]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getProhibitedElementIsPresentMessageTest() {
        Assertions.assertThat(Messages.Binding.getProhibitedElementIsPresentMessage(null)).isEqualTo("[Prohibited element is present: null]");
        Assertions.assertThat(Messages.Binding.getProhibitedElementIsPresentMessage(new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited element is present: element[@]]");
        Assertions.assertThat(Messages.Binding.getProhibitedElementIsPresentMessage(new ElementDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited element is present: element[@]]");
        Assertions.assertThat(Messages.Binding.getProhibitedElementIsPresentMessage(new ElementDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited element is present: element[@ ]]");
        Assertions.assertThat(Messages.Binding.getProhibitedElementIsPresentMessage(new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited element is present: element[@id]]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getRequiredAttributeIsNotPresentMessageTest() {
        Assertions.assertThat(Messages.Binding.getRequiredAttributeIsNotPresentMessage(null)).isEqualTo("[Required attribute is not present: null]");
        Assertions.assertThat(Messages.Binding.getRequiredAttributeIsNotPresentMessage(new AttributeDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required attribute is not present: attribute[@]]");
        Assertions.assertThat(Messages.Binding.getRequiredAttributeIsNotPresentMessage(new AttributeDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required attribute is not present: attribute[@]]");
        Assertions.assertThat(Messages.Binding.getRequiredAttributeIsNotPresentMessage(new AttributeDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required attribute is not present: attribute[@ ]]");
        Assertions.assertThat(Messages.Binding.getRequiredAttributeIsNotPresentMessage(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Required attribute is not present: attribute[@id]]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getProhibitedAttributeIsPresentMessageTest() {
        Assertions.assertThat(Messages.Binding.getProhibitedAttributeIsPresentMessage(null)).isEqualTo("[Prohibited attribute is present: null]");
        Assertions.assertThat(Messages.Binding.getProhibitedAttributeIsPresentMessage(new AttributeDefinition(null, "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited attribute is present: attribute[@]]");
        Assertions.assertThat(Messages.Binding.getProhibitedAttributeIsPresentMessage(new AttributeDefinition("", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited attribute is present: attribute[@]]");
        Assertions.assertThat(Messages.Binding.getProhibitedAttributeIsPresentMessage(new AttributeDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited attribute is present: attribute[@ ]]");
        Assertions.assertThat(Messages.Binding.getProhibitedAttributeIsPresentMessage(new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Prohibited attribute is present: attribute[@id]]");
    }

    /**
     * {@link Messages} class test.
     */
    @Test
    public void getMultipleChoiceElementsArePresentMessageTest() {
        Assertions.assertThat(Messages.Binding.getMultipleChoiceElementsArePresentMessage(null)).isEqualTo("[Multiple choice elements are present: null]");
        Assertions.assertThat(Messages.Binding.getMultipleChoiceElementsArePresentMessage(new ChoiceDefinition(null, CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Multiple choice elements are present: choice[@]]");
        Assertions.assertThat(Messages.Binding.getMultipleChoiceElementsArePresentMessage(new ChoiceDefinition("", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Multiple choice elements are present: choice[@]]");
        Assertions.assertThat(Messages.Binding.getMultipleChoiceElementsArePresentMessage(new ChoiceDefinition(" ", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Multiple choice elements are present: choice[@ ]]");
        Assertions.assertThat(Messages.Binding.getMultipleChoiceElementsArePresentMessage(new ChoiceDefinition("id", CardinalityDefinition.REQUIRED, new ArrayList<NodeDefinition>(), new HashMap<String, String>()))).isEqualTo("[Multiple choice elements are present: choice[@id]]");
    }

}
