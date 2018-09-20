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
package ru.d_shap.formmodel.definition.loader.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.XmlDocumentValidator;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * Form definition loader, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
final class XmlFormDefinitionLoader implements FormModelXmlDefinitionBuilder {

    private final XmlDocumentBuilder _xmlDocumentBuilder;

    private final List<OtherNodeXmlDefinitionBuilder> _otherNodeXmlDefinitionBuilders;

    private final OtherNodeXmlDefinitionBuilder _defaultOtherNodeXmlDefinitionBuilder;

    XmlFormDefinitionLoader(final List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders) {
        super();
        _xmlDocumentBuilder = new XmlDocumentBuilder();
        if (otherNodeXmlDefinitionBuilders == null) {
            _otherNodeXmlDefinitionBuilders = new ArrayList<>();
        } else {
            _otherNodeXmlDefinitionBuilders = new ArrayList<>(otherNodeXmlDefinitionBuilders);
        }
        _defaultOtherNodeXmlDefinitionBuilder = new DefaultOtherNodeXmlDefinitionBuilder();
    }

    FormDefinition load(final InputSource inputSource, final String source) {
        Document document = _xmlDocumentBuilder.parse(inputSource);
        Element element = document.getDocumentElement();
        if (isFormDefinitionElement(element)) {
            XmlDocumentValidator.getFormModelSchemaValidator().validate(document);
            return createFormDefinition(element, source);
        } else {
            return null;
        }
    }

    private boolean isFormDefinitionElement(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && FORM_DEFINITION_ELEMENT_NAME.equals(element.getTagName());
    }

    private FormDefinition createFormDefinition(final Element element, final String source) {
        String group = getAttributeValue(element, FORM_DEFINITION_ATTRIBUTE_GROUP);
        String id = getAttributeValue(element, FORM_DEFINITION_ATTRIBUTE_ID);
        NodePath currentNodePath = new NodePath(Messages.Representation.getFormDefinitionRepresentation(source, group, id));
        List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, FORM_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
        Map<String, String> otherAttributes = getOtherAttributes(element, FORM_DEFINITION_ATTRIBUTE_NAMES);
        return new FormDefinition(group, id, nodeDefinitions, otherAttributes, source);
    }

    @Override
    public boolean isElementDefinitionElement(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && ELEMENT_DEFINITION_ELEMENT_NAME.equals(element.getTagName());
    }

    @Override
    public ElementDefinition createElementDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isElementDefinitionElement(element)) {
            String id = getAttributeValue(element, ELEMENT_DEFINITION_ATTRIBUTE_ID);
            String lookup = getAttributeValue(element, ELEMENT_DEFINITION_ATTRIBUTE_LOOKUP);
            CardinalityDefinition defaultCardinalityDefinition;
            if (isChoiceDefinitionElement(parentElement)) {
                defaultCardinalityDefinition = CardinalityDefinition.OPTIONAL;
            } else {
                defaultCardinalityDefinition = CardinalityDefinition.REQUIRED;
            }
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, ELEMENT_DEFINITION_ATTRIBUTE_TYPE, defaultCardinalityDefinition);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getElementDefinitionRepresentation(id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, ELEMENT_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element, ELEMENT_DEFINITION_ATTRIBUTE_NAMES);
            return new ElementDefinition(id, lookup, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getElementDefinitionElementIsNotValidMessage(element), nodePath);
        }
    }

    @Override
    public boolean isChoiceDefinitionElement(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && CHOICE_DEFINITION_ELEMENT_NAME.equals(element.getTagName());
    }

    @Override
    public ChoiceDefinition createChoiceDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isChoiceDefinitionElement(element)) {
            String id = getAttributeValue(element, CHOICE_DEFINITION_ATTRIBUTE_ID);
            CardinalityDefinition defaultCardinalityDefinition;
            if (isChoiceDefinitionElement(parentElement)) {
                defaultCardinalityDefinition = CardinalityDefinition.OPTIONAL;
            } else {
                defaultCardinalityDefinition = CardinalityDefinition.REQUIRED;
            }
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, CHOICE_DEFINITION_ATTRIBUTE_TYPE, defaultCardinalityDefinition);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getChoiceDefinitionRepresentation(id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, CHOICE_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element, CHOICE_DEFINITION_ATTRIBUTE_NAMES);
            return new ChoiceDefinition(id, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getChoiceDefinitionElementIsNotValidMessage(element), nodePath);
        }
    }

    @Override
    public boolean isFormReferenceDefinitionElement(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && FORM_REFERENCE_DEFINITION_ELEMENT_NAME.equals(element.getTagName());
    }

    @Override
    public FormReferenceDefinition createFormReferenceDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isFormReferenceDefinitionElement(element)) {
            String group = getAttributeValue(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_GROUP);
            String id = getAttributeValue(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_ID);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getFormReferenceDefinitionRepresentation(group, id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, FORM_REFERENCE_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_NAMES);
            return new FormReferenceDefinition(group, id, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getFormReferenceDefinitionElementIsNotValidMessage(element), nodePath);
        }
    }

    @Override
    public boolean isAttributeDefinitionElement(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && ATTRIBUTE_DEFINITION_ELEMENT_NAME.equals(element.getTagName());
    }

    @Override
    public AttributeDefinition createAttributeDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isAttributeDefinitionElement(element)) {
            String id = getAttributeValue(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_ID);
            String lookup = getAttributeValue(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_LOOKUP);
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_TYPE, CardinalityDefinition.REQUIRED);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getAttributeDefinitionRepresentation(id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, ATTRIBUTE_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_NAMES);
            return new AttributeDefinition(id, lookup, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getAttributeDefinitionElementIsNotValidMessage(element), nodePath);
        }
    }

    private String getAttributeValue(final Element element, final String attributeName) {
        if (element.hasAttribute(attributeName)) {
            return element.getAttribute(attributeName);
        } else {
            return null;
        }
    }

    private CardinalityDefinition getCardinalityDefinition(final Element element, final String attributeName, final CardinalityDefinition defaultCardinalityDefinition) {
        String attributeValue = getAttributeValue(element, attributeName);
        if (attributeValue == null) {
            return defaultCardinalityDefinition;
        } else {
            return CardinalityDefinition.getCardinalityDefinition(attributeValue);
        }
    }

    private List<NodeDefinition> getNodeDefinitions(final Element element, final Set<String> childElementNames, final NodePath nodePath) {
        List<NodeDefinition> nodeDefinitions = new ArrayList<>();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                processChildElement(element, (Element) childNode, nodeDefinitions, childElementNames, nodePath);
            }
        }
        return nodeDefinitions;
    }

    private void processChildElement(final Element parentElement, final Element element, final List<NodeDefinition> nodeDefinitions, final Set<String> childElementNames, final NodePath nodePath) {
        if (NAMESPACE.equals(element.getNamespaceURI())) {
            addNodeDefinition(parentElement, element, nodeDefinitions, childElementNames, nodePath);
        } else {
            addOtherNodeDefinition(parentElement, element, nodeDefinitions, nodePath);
        }
    }

    private void addNodeDefinition(final Element parentElement, final Element element, final List<NodeDefinition> nodeDefinitions, final Set<String> childElementNames, final NodePath nodePath) {
        String localName = element.getTagName();
        if (ELEMENT_DEFINITION_ELEMENT_NAME.equals(localName) && childElementNames.contains(localName)) {
            NodeDefinition nodeDefinition = createElementDefinition(parentElement, element, nodePath);
            nodeDefinitions.add(nodeDefinition);
            return;
        }
        if (CHOICE_DEFINITION_ELEMENT_NAME.equals(localName) && childElementNames.contains(localName)) {
            NodeDefinition nodeDefinition = createChoiceDefinition(parentElement, element, nodePath);
            nodeDefinitions.add(nodeDefinition);
            return;
        }
        if (FORM_REFERENCE_DEFINITION_ELEMENT_NAME.equals(localName) && childElementNames.contains(localName)) {
            NodeDefinition nodeDefinition = createFormReferenceDefinition(parentElement, element, nodePath);
            nodeDefinitions.add(nodeDefinition);
            return;
        }
        if (ATTRIBUTE_DEFINITION_ELEMENT_NAME.equals(localName) && childElementNames.contains(localName)) {
            NodeDefinition nodeDefinition = createAttributeDefinition(parentElement, element, nodePath);
            nodeDefinitions.add(nodeDefinition);
            return;
        }
        throw new FormDefinitionValidationException(Messages.Validation.getChildElementIsNotValidMessage(element), nodePath);
    }

    private void addOtherNodeDefinition(final Element parentElement, final Element element, final List<NodeDefinition> nodeDefinitions, final NodePath nodePath) {
        for (OtherNodeXmlDefinitionBuilder otherNodeXmlDefinitionBuilder : _otherNodeXmlDefinitionBuilders) {
            OtherNodeDefinition otherNodeDefinition = otherNodeXmlDefinitionBuilder.createOtherNodeDefinition(parentElement, element, this, nodePath);
            if (otherNodeDefinition != null) {
                nodeDefinitions.add(otherNodeDefinition);
                return;
            }
        }
        OtherNodeDefinition otherNodeDefinition = _defaultOtherNodeXmlDefinitionBuilder.createOtherNodeDefinition(parentElement, element, this, nodePath);
        nodeDefinitions.add(otherNodeDefinition);
    }

    private Map<String, String> getOtherAttributes(final Element element, final Set<String> skipAttributeNames) {
        Map<String, String> additionalAttributes = new HashMap<>();
        NamedNodeMap namedNodeMap = element.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node node = namedNodeMap.item(i);
            String attributeName = node.getNodeName();
            if (skipAttributeNames.contains(attributeName)) {
                continue;
            }
            String attributeValue = node.getNodeValue();
            additionalAttributes.put(attributeName, attributeValue);
        }
        return additionalAttributes;
    }

}
