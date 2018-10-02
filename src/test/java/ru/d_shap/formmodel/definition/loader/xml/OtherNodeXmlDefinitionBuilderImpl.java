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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

    private static final String NAMESPACE = "http://d-shap.ru/schema/form-model-other-node/1.0";

    private static final String LOCAL_NAME = "otherNode";

    /**
     * Create new object.
     */
    public OtherNodeXmlDefinitionBuilderImpl() {
        super();
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
        NodePath currentNodePath = new NodePath(nodePath, Messages.Representation.getXmlElementRepresentation(element));
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
    public CardinalityDefinition getChildElementDefinitionDefaultCardinality(final Element element) {
        return CardinalityDefinition.PROHIBITED;
    }

    @Override
    public CardinalityDefinition getChildSingleElementDefinitionDefaultCardinality(final Element element) {
        return CardinalityDefinition.PROHIBITED;
    }

}
