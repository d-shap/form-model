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
package ru.d_shap.formmodel.definition;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Form definition loader.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionLoader {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    private FormDefinitionLoader() {
        super();
    }

    public static FormDefinition load(final InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        try {
            DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
            InputSource inputSource = new InputSource(inputStream);
            Document document = builder.parse(inputSource);
            return createFormDefinition(document);
        } finally {
            inputStream.close();
        }
    }

    private static FormDefinition createFormDefinition(final Document document) {
        Element element = document.getDocumentElement();
        if (FormDefinition.ELEMENT_NAME.equals(element.getNodeName())) {
            String id = getAttributeValue(element, FormDefinition.ATTRIBUTE_ID);
            String frame = getAttributeValue(element, FormDefinition.ATTRIBUTE_FRAME);
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element);
            FormDefinition formDefinition = new FormDefinition(id, frame, nodeDefinitions);
            return formDefinition;
        } else {
            return null;
        }
    }

    private static List<NodeDefinition> getNodeDefinitions(final Element element) {
        NodeList childNodes = element.getChildNodes();
        List<NodeDefinition> nodeDefinitions = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                if (ElementDefinition.ELEMENT_NAME.equals(childElement.getNodeName())) {
                    ElementDefinition childElementDefinition = createElementDefinition(childElement);
                    nodeDefinitions.add(childElementDefinition);
                }
                if (FormReferenceDefinition.ELEMENT_NAME.equals(childElement.getNodeName())) {
                    FormReferenceDefinition childFormReferenceDefinition = createFormReferenceDefinition(childElement);
                    nodeDefinitions.add(childFormReferenceDefinition);
                }
            }
        }
        return nodeDefinitions;
    }

    private static ElementDefinition createElementDefinition(final Element element) {
        String id = getAttributeValue(element, ElementDefinition.ATTRIBUTE_ID);
        String lookup = getAttributeValue(element, ElementDefinition.ATTRIBUTE_LOOKUP);
        String type = getAttributeValue(element, ElementDefinition.ATTRIBUTE_TYPE);
        ElementDefinitionType elementDefinitionType = ElementDefinitionType.getElementDefinitionType(type);
        if (elementDefinitionType == null) {
            elementDefinitionType = ElementDefinitionType.MANDATORY;
        }
        List<NodeDefinition> childNodeDefinitions = getNodeDefinitions(element);
        ElementDefinition elementDefinition = new ElementDefinition(id, lookup, elementDefinitionType, childNodeDefinitions);
        return elementDefinition;
    }

    private static FormReferenceDefinition createFormReferenceDefinition(final Element element) {
        String referenceId = getAttributeValue(element, FormReferenceDefinition.ATTRIBUTE_REFERENCE_ID);
        return new FormReferenceDefinition(referenceId);
    }

    private static String getAttributeValue(final Element element, final String attributeName) {
        if (element.hasAttribute(attributeName)) {
            return element.getAttribute(attributeName);
        } else {
            return null;
        }
    }

}
