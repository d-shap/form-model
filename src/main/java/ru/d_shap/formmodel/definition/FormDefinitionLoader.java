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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Form definition loader.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionLoader {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;

    static {
        DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);
    }

    private static final SchemaFactory SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    private static final String CLASS_PATH = FormDefinitionLoader.class.getPackage().getName().replaceAll("\\.", "/");

    private static final String SCHEMA_LOCATION = CLASS_PATH + "/form-model.xsd";

    private FormDefinitionLoader() {
        super();
    }

    /**
     * Load the form definition from the specified source.
     *
     * @param inputStream stream with form definition.
     * @param source      the specified source.
     *
     * @return the loaded form definition.
     */
    static FormDefinition load(final InputStream inputStream, final Object source) {
        try {
            try {
                DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
                InputSource inputSource = new InputSource(inputStream);
                Document document = builder.parse(inputSource);

                InputStream schemaInputStream = FormDefinitionLoader.class.getClassLoader().getResourceAsStream(SCHEMA_LOCATION);
                Schema schema = SCHEMA_FACTORY.newSchema(new StreamSource(schemaInputStream));

                Validator validator = schema.newValidator();
                validator.validate(new DOMSource(document));

                return createFormDefinition(document, source);
            } finally {
                inputStream.close();
            }
        } catch (ParserConfigurationException ex) {
            throw new FormDefinitionLoadException(ex);
        } catch (IOException ex) {
            throw new FormDefinitionLoadException(ex);
        } catch (SAXException ex) {
            throw new FormDefinitionLoadException(ex);
        }
    }

    private static FormDefinition createFormDefinition(final Document document, final Object source) {
        Element element = document.getDocumentElement();
        if (FormDefinition.ELEMENT_NAME.equals(element.getNodeName())) {
            String id = getAttributeValue(element, FormDefinition.ATTRIBUTE_ID);
            if (id == null) {
                throw new FormDefinitionValidationException("Form ID is not defined", source);
            }
            Map<String, String> additionalAttributes = getAdditionalAttributes(element, FormDefinition.DEFINED_ATTRIBUTE_NAMES);
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, source);
            return new FormDefinition(id, additionalAttributes, nodeDefinitions, source);
        } else {
            throw new FormDefinitionValidationException("Wrong root element: " + element.getNodeName(), source);
        }
    }

    private static List<NodeDefinition> getNodeDefinitions(final Element element, final Object source) {
        List<NodeDefinition> nodeDefinitions = new ArrayList<>();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                if (ElementDefinition.ELEMENT_NAME.equals(childElement.getNodeName())) {
                    NodeDefinition nodeDefinition = createElementDefinition(childElement, source);
                    nodeDefinitions.add(nodeDefinition);
                }
                if (FormReferenceDefinition.ELEMENT_NAME.equals(childElement.getNodeName())) {
                    NodeDefinition nodeDefinition = createFormReferenceDefinition(childElement, source);
                    nodeDefinitions.add(nodeDefinition);
                }
            }
        }
        return nodeDefinitions;
    }

    private static ElementDefinition createElementDefinition(final Element element, final Object source) {
        String id = getAttributeValue(element, ElementDefinition.ATTRIBUTE_ID);
        String lookup = getAttributeValue(element, ElementDefinition.ATTRIBUTE_LOOKUP);
        String type = getAttributeValue(element, ElementDefinition.ATTRIBUTE_ELEMENT_DEFINITION_TYPE);
        ElementDefinitionType elementDefinitionType;
        if (type == null) {
            elementDefinitionType = ElementDefinitionType.MANDATORY;
        } else {
            elementDefinitionType = ElementDefinitionType.getElementDefinitionType(type);
            if (elementDefinitionType == null) {
                throw new FormDefinitionValidationException("Wrong form definition type: " + type, source);
            }
        }
        Map<String, String> additionalAttributes = getAdditionalAttributes(element, ElementDefinition.DEFINED_ATTRIBUTE_NAMES);
        List<NodeDefinition> childNodeDefinitions = getNodeDefinitions(element, source);
        return new ElementDefinition(id, lookup, elementDefinitionType, additionalAttributes, childNodeDefinitions);
    }

    private static FormReferenceDefinition createFormReferenceDefinition(final Element element, final Object source) {
        String referenceId = getAttributeValue(element, FormReferenceDefinition.ATTRIBUTE_REFERENCED_FORM_ID);
        if (referenceId == null) {
            throw new FormDefinitionValidationException("Form reference id is null", source);
        }
        return new FormReferenceDefinition(referenceId);
    }

    private static String getAttributeValue(final Element element, final String attributeName) {
        if (element.hasAttribute(attributeName)) {
            return element.getAttribute(attributeName);
        } else {
            return null;
        }
    }

    private static Map<String, String> getAdditionalAttributes(final Element element, final Set<String> skipAttributeNames) {
        Map<String, String> additionalAttributes = new HashMap<>();
        NamedNodeMap namedNodeMap = element.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node node = namedNodeMap.item(i);
            String attributeName = node.getNodeName();
            if (!skipAttributeNames.contains(attributeName)) {
                String attributeValue = node.getNodeValue();
                additionalAttributes.put(attributeName, attributeValue);
            }
        }
        return additionalAttributes;
    }

}
