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

import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinitionImpl;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;

/**
 * Implementation of the {@link OtherNodeXmlDefinitionBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class OtherNodeXmlDefinitionBuilderImpl implements OtherNodeXmlDefinitionBuilder {

    public static final String NAMESPACE = "http://d-shap.ru/schema/form-model-other-node/1.0";

    public static final String LOCAL_NAME = "otherNode";

    public static final String RETURN_NOT_NULL_CARDINALITY = "__RETURN_NOT_NULL_CARDINALITY__";

    /**
     * Create new object.
     */
    public OtherNodeXmlDefinitionBuilderImpl() {
        super();
    }

    /**
     * Set system property to return not null cardinality definition for other nodes.
     */
    public static void setReturnNotNullCardinality() {
        System.setProperty(RETURN_NOT_NULL_CARDINALITY, "true");
    }

    /**
     * Clear system property to return not null cardinality definition for other nodes.
     */
    public static void clearReturnNotNullCardinality() {
        System.getProperties().remove(RETURN_NOT_NULL_CARDINALITY);
    }

    @Override
    public void validate(final Node node) throws SAXException {
        if (hasInvalidComment(node)) {
            throw new SAXException("Invalid comment found!");
        }
    }

    private boolean hasInvalidComment(final Node node) {
        if (node instanceof Comment) {
            return "INVALID!".equals(node.getTextContent());
        }
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (hasInvalidComment(nodeList.item(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public OtherNodeDefinition createOtherNodeDefinition(final Element parentElement, final Element element, final FormXmlDefinitionBuilder formXmlDefinitionBuilder, final NodePath nodePath) {
        if (!NAMESPACE.equals(element.getNamespaceURI()) || !LOCAL_NAME.equals(element.getLocalName())) {
            return null;
        }
        String representation = element.getAttribute("repr");
        boolean valid = Boolean.parseBoolean(element.getAttribute("valid"));
        OtherNodeDefinitionImpl otherNodeDefinition = new OtherNodeDefinitionImpl(representation, valid);

        NodeList childNodes = element.getChildNodes();
        NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getNodeRepresentation(element));
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                processChildElement(otherNodeDefinition, element, (Element) childNode, formXmlDefinitionBuilder, currentNodePath);
            }
        }

        return otherNodeDefinition;
    }

    private void processChildElement(final OtherNodeDefinitionImpl otherNodeDefinition, final Element parentElement, final Element element, final FormXmlDefinitionBuilder formXmlDefinitionBuilder, final NodePath nodePath) {
        if (formXmlDefinitionBuilder.isAttributeDefinition(element)) {
            AttributeDefinition attributeDefinition = formXmlDefinitionBuilder.createAttributeDefinition(parentElement, element, nodePath);
            otherNodeDefinition.setAttributeDefinition(attributeDefinition);
        }
        if (formXmlDefinitionBuilder.isElementDefinition(element)) {
            ElementDefinition elementDefinition = formXmlDefinitionBuilder.createElementDefinition(parentElement, element, nodePath);
            otherNodeDefinition.setElementDefinition(elementDefinition);
        }
        if (formXmlDefinitionBuilder.isSingleElementDefinition(element)) {
            SingleElementDefinition singleElementDefinition = formXmlDefinitionBuilder.createSingleElementDefinition(parentElement, element, nodePath);
            otherNodeDefinition.setSingleElementDefinition(singleElementDefinition);
        }
        if (formXmlDefinitionBuilder.isFormReferenceDefinition(element)) {
            FormReferenceDefinition formReferenceDefinition = formXmlDefinitionBuilder.createFormReferenceDefinition(parentElement, element, nodePath);
            otherNodeDefinition.setFormReferenceDefinition(formReferenceDefinition);
        }
        if (formXmlDefinitionBuilder.isOtherNodeDefinition(element)) {
            OtherNodeDefinition childOtherNodeDefinition = formXmlDefinitionBuilder.createOtherNodeDefinition(parentElement, element, nodePath);
            otherNodeDefinition.setOtherNodeDefinition(childOtherNodeDefinition);
        }
    }

    @Override
    public CardinalityDefinition getAttributeDefinitionCardinality(final Element parentElement) {
        if (NAMESPACE.equals(parentElement.getNamespaceURI()) && LOCAL_NAME.equals(parentElement.getLocalName())) {
            return getThisNodeCardinality();
        } else {
            return getThatNodeCardinality();
        }
    }

    @Override
    public CardinalityDefinition getElementDefinitionCardinality(final Element parentElement) {
        if (NAMESPACE.equals(parentElement.getNamespaceURI()) && LOCAL_NAME.equals(parentElement.getLocalName())) {
            return getThisNodeCardinality();
        } else {
            return getThatNodeCardinality();
        }
    }

    @Override
    public CardinalityDefinition getSingleElementDefinitionCardinality(final Element parentElement) {
        if (NAMESPACE.equals(parentElement.getNamespaceURI()) && LOCAL_NAME.equals(parentElement.getLocalName())) {
            return getThisNodeCardinality();
        } else {
            return getThatNodeCardinality();
        }
    }

    private CardinalityDefinition getThisNodeCardinality() {
        return CardinalityDefinition.PROHIBITED;
    }

    private CardinalityDefinition getThatNodeCardinality() {
        if ("true".equalsIgnoreCase(System.getProperty(RETURN_NOT_NULL_CARDINALITY))) {
            return CardinalityDefinition.REQUIRED_MULTIPLE;
        } else {
            return null;
        }
    }

}
