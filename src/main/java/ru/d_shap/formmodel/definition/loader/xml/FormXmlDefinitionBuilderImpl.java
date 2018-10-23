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

import javax.xml.XMLConstants;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;

/**
 * Builder for the form definition, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
final class FormXmlDefinitionBuilderImpl implements FormXmlDefinitionBuilder {

    private final List<OtherNodeXmlDefinitionBuilder> _otherNodeXmlDefinitionBuilders;

    FormXmlDefinitionBuilderImpl(final List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders) {
        super();
        _otherNodeXmlDefinitionBuilders = new ArrayList<>(otherNodeXmlDefinitionBuilders);
    }

    boolean isFormDefinition(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && FORM_DEFINITION_ELEMENT_NAME.equals(element.getLocalName());
    }

    FormDefinition createFormDefinition(final Element element, final String source) {
        if (isFormDefinition(element)) {
            String group = getAttributeValue(element, FORM_DEFINITION_ATTRIBUTE_GROUP);
            String id = getAttributeValue(element, FORM_DEFINITION_ATTRIBUTE_ID);
            NodePath currentNodePath = new NodePath(Messages.Representation.getFormDefinitionRepresentation(source, group, id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, FORM_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element);
            return new FormDefinition(group, id, nodeDefinitions, otherAttributes, source);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getFormDefinitionIsNotValidMessage(element));
        }
    }

    @Override
    public boolean isAttributeDefinition(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && ATTRIBUTE_DEFINITION_ELEMENT_NAME.equals(element.getLocalName());
    }

    @Override
    public AttributeDefinition createAttributeDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isAttributeDefinition(element)) {
            String id = getAttributeValue(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_ID);
            String lookup = getAttributeValue(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_LOOKUP);
            CardinalityDefinition defaultCardinalityDefinition = getAttributeDefinitionCardinality(parentElement);
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_TYPE, defaultCardinalityDefinition);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getAttributeDefinitionRepresentation(id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, ATTRIBUTE_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element);
            return new AttributeDefinition(id, lookup, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getAttributeDefinitionIsNotValidMessage(element), nodePath);
        }
    }

    private CardinalityDefinition getAttributeDefinitionCardinality(final Element parentElement) {
        CardinalityDefinition defaultCardinalityDefinition;
        if (isOtherNodeDefinition(parentElement)) {
            for (OtherNodeXmlDefinitionBuilder otherNodeXmlDefinitionBuilder : _otherNodeXmlDefinitionBuilders) {
                defaultCardinalityDefinition = otherNodeXmlDefinitionBuilder.getAttributeDefinitionCardinality(parentElement);
                if (defaultCardinalityDefinition != null) {
                    return defaultCardinalityDefinition;
                }
            }
        }
        return CardinalityDefinition.REQUIRED;
    }

    @Override
    public boolean isElementDefinition(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && ELEMENT_DEFINITION_ELEMENT_NAME.equals(element.getLocalName());
    }

    @Override
    public ElementDefinition createElementDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isElementDefinition(element)) {
            String id = getAttributeValue(element, ELEMENT_DEFINITION_ATTRIBUTE_ID);
            String lookup = getAttributeValue(element, ELEMENT_DEFINITION_ATTRIBUTE_LOOKUP);
            CardinalityDefinition defaultCardinalityDefinition = getElementDefinitionCardinality(parentElement);
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, ELEMENT_DEFINITION_ATTRIBUTE_TYPE, defaultCardinalityDefinition);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getElementDefinitionRepresentation(id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, ELEMENT_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element);
            return new ElementDefinition(id, lookup, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getElementDefinitionIsNotValidMessage(element), nodePath);
        }
    }

    private CardinalityDefinition getElementDefinitionCardinality(final Element parentElement) {
        CardinalityDefinition defaultCardinalityDefinition;
        if (isOtherNodeDefinition(parentElement)) {
            for (OtherNodeXmlDefinitionBuilder otherNodeXmlDefinitionBuilder : _otherNodeXmlDefinitionBuilders) {
                defaultCardinalityDefinition = otherNodeXmlDefinitionBuilder.getElementDefinitionCardinality(parentElement);
                if (defaultCardinalityDefinition != null) {
                    return defaultCardinalityDefinition;
                }
            }
        }
        if (isSingleElementDefinition(parentElement)) {
            return CardinalityDefinition.OPTIONAL;
        } else {
            return CardinalityDefinition.REQUIRED;
        }
    }

    @Override
    public boolean isSingleElementDefinition(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && SINGLE_ELEMENT_DEFINITION_ELEMENT_NAME.equals(element.getLocalName());
    }

    @Override
    public SingleElementDefinition createSingleElementDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isSingleElementDefinition(element)) {
            String id = getAttributeValue(element, SINGLE_ELEMENT_DEFINITION_ATTRIBUTE_ID);
            CardinalityDefinition defaultCardinalityDefinition = getSingleElementDefinitionCardinality(parentElement);
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, SINGLE_ELEMENT_DEFINITION_ATTRIBUTE_TYPE, defaultCardinalityDefinition);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getSingleElementDefinitionRepresentation(id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, SINGLE_ELEMENT_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element);
            return new SingleElementDefinition(id, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getSingleElementDefinitionIsNotValidMessage(element), nodePath);
        }
    }

    private CardinalityDefinition getSingleElementDefinitionCardinality(final Element parentElement) {
        CardinalityDefinition defaultCardinalityDefinition;
        if (isOtherNodeDefinition(parentElement)) {
            for (OtherNodeXmlDefinitionBuilder otherNodeXmlDefinitionBuilder : _otherNodeXmlDefinitionBuilders) {
                defaultCardinalityDefinition = otherNodeXmlDefinitionBuilder.getSingleElementDefinitionCardinality(parentElement);
                if (defaultCardinalityDefinition != null) {
                    return defaultCardinalityDefinition;
                }
            }
        }
        if (isSingleElementDefinition(parentElement)) {
            return CardinalityDefinition.OPTIONAL;
        } else {
            return CardinalityDefinition.REQUIRED;
        }
    }

    @Override
    public boolean isFormReferenceDefinition(final Element element) {
        return NAMESPACE.equals(element.getNamespaceURI()) && FORM_REFERENCE_DEFINITION_ELEMENT_NAME.equals(element.getLocalName());
    }

    @Override
    public FormReferenceDefinition createFormReferenceDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isFormReferenceDefinition(element)) {
            String group = getAttributeValue(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_GROUP);
            String id = getAttributeValue(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_ID);
            NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getFormReferenceDefinitionRepresentation(group, id));
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, FORM_REFERENCE_DEFINITION_CHILD_ELEMENT_NAMES, currentNodePath);
            Map<String, String> otherAttributes = getOtherAttributes(element);
            return new FormReferenceDefinition(group, id, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getFormReferenceDefinitionIsNotValidMessage(element), nodePath);
        }
    }

    @Override
    public boolean isOtherNodeDefinition(final Element element) {
        return element.getNamespaceURI() != null && !NAMESPACE.equals(element.getNamespaceURI());
    }

    @Override
    public OtherNodeDefinition createOtherNodeDefinition(final Element parentElement, final Element element, final NodePath nodePath) {
        if (isOtherNodeDefinition(element)) {
            for (OtherNodeXmlDefinitionBuilder otherNodeXmlDefinitionBuilder : _otherNodeXmlDefinitionBuilders) {
                OtherNodeDefinition otherNodeDefinition = otherNodeXmlDefinitionBuilder.createOtherNodeDefinition(parentElement, element, this, nodePath);
                if (otherNodeDefinition != null) {
                    return otherNodeDefinition;
                }
            }
            return new DefaultOtherNodeXmlDefinition(element);
        } else {
            throw new FormDefinitionValidationException(Messages.Validation.getOtherNodeDefinitionIsNotValidMessage(element), nodePath);
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
        if (NAMESPACE.equals(element.getNamespaceURI()) && childElementNames.contains(element.getLocalName())) {
            if (ATTRIBUTE_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
                NodeDefinition nodeDefinition = createAttributeDefinition(parentElement, element, nodePath);
                nodeDefinitions.add(nodeDefinition);
                return;
            }
            if (ELEMENT_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
                NodeDefinition nodeDefinition = createElementDefinition(parentElement, element, nodePath);
                nodeDefinitions.add(nodeDefinition);
                return;
            }
            if (SINGLE_ELEMENT_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
                NodeDefinition nodeDefinition = createSingleElementDefinition(parentElement, element, nodePath);
                nodeDefinitions.add(nodeDefinition);
                return;
            }
            if (FORM_REFERENCE_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
                NodeDefinition nodeDefinition = createFormReferenceDefinition(parentElement, element, nodePath);
                nodeDefinitions.add(nodeDefinition);
                return;
            }
        }
        if (isOtherNodeDefinition(element)) {
            NodeDefinition nodeDefinition = createOtherNodeDefinition(parentElement, element, nodePath);
            nodeDefinitions.add(nodeDefinition);
            return;
        }
        throw new FormDefinitionValidationException(Messages.Validation.getChildElementIsNotValidMessage(element), nodePath);
    }

    private Map<String, String> getOtherAttributes(final Element element) {
        Map<String, String> additionalAttributes = new HashMap<>();
        NamedNodeMap namedNodeMap = element.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node node = namedNodeMap.item(i);
            if (XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(node.getNamespaceURI())) {
                continue;
            }
            String attributeName = Messages.Representation.getNodeRepresentation(node);
            String attributeValue = node.getNodeValue();
            additionalAttributes.put(attributeName, attributeValue);
        }
        return additionalAttributes;
    }

}
