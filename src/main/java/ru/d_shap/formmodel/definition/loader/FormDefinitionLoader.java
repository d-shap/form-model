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
package ru.d_shap.formmodel.definition.loader;

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

import ru.d_shap.formmodel.definition.FormDefinitionBuildException;
import ru.d_shap.formmodel.definition.FormDefinitionLoadException;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * Form definition loader.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionLoader implements FormModelElementBuilder {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;

    static {
        DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);
    }

    private static final SchemaFactory SCHEMA_FACTORY;

    static {
        SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }

    private static final String CLASS_PATH = FormDefinition.class.getPackage().getName().replaceAll("\\.", "/");

    private static final String SCHEMA_LOCATION = CLASS_PATH + "/form-model.xsd";

    private final Validator _validator;

    private final List<OtherNodeDefinitionBuilder> _otherNodeDefinitionLoaders;

    FormDefinitionLoader(final List<OtherNodeDefinitionBuilder> otherNodeDefinitionLoaders) {
        super();
        _validator = createValidator(SCHEMA_LOCATION);
        _otherNodeDefinitionLoaders = new ArrayList<>(otherNodeDefinitionLoaders);
    }

    Validator createValidator(final String schemaLocation) {
        try {
            InputStream inputStream = null;
            try {
                inputStream = FormDefinitionLoader.class.getClassLoader().getResourceAsStream(schemaLocation);
                Schema schema = SCHEMA_FACTORY.newSchema(new StreamSource(inputStream));
                return schema.newValidator();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException ex) {
            throw new FormDefinitionLoadException("", ex);
        } catch (SAXException ex) {
            throw new FormDefinitionLoadException("", ex);
        }
    }

    /**
     * Load the form definition from the specified source.
     *
     * @param inputSource the specified source.
     * @param source      the specified source ID.
     *
     * @return the form definition
     */
    FormDefinition load(final InputSource inputSource, final String source) {
        try {
            DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
            Document document = builder.parse(inputSource);
            _validator.validate(new DOMSource(document));
            Element element = document.getDocumentElement();
            return createFormDefinition(element, source);
        } catch (ParserConfigurationException ex) {
            throw new FormDefinitionLoadException("", ex);
        } catch (IOException ex) {
            throw new FormDefinitionLoadException("", ex);
        } catch (SAXException ex) {
            throw new FormDefinitionLoadException("", ex);
        }
    }

    @Override
    public FormDefinition createFormDefinition(final Element element, final String source) {
        if (element.getNamespaceURI().equals(NAMESPACE) && element.getLocalName().equals(FORM_DEFINITION_ELEMENT_NAME)) {
            String group = getAttributeValue(element, FORM_DEFINITION_ATTRIBUTE_GROUP);
            String id = getAttributeValue(element, FORM_DEFINITION_ATTRIBUTE_ID);
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, CardinalityDefinition.REQUIRED, FORM_DEFINITION_CHILD_ELEMENT_NAMES);
            Map<String, String> otherAttributes = getOtherAttributes(element, FORM_DEFINITION_ATTRIBUTE_NAMES);
            return new FormDefinition(group, id, nodeDefinitions, otherAttributes, source);
        } else {
            throw new FormDefinitionBuildException("");
        }
    }

    @Override
    public ElementDefinition createElementDefinition(final Element element, final CardinalityDefinition defaultCardinalityDefinition) {
        if (element.getNamespaceURI().equals(NAMESPACE) && element.getLocalName().equals(ELEMENT_DEFINITION_ELEMENT_NAME)) {
            String id = getAttributeValue(element, ELEMENT_DEFINITION_ATTRIBUTE_ID);
            String lookup = getAttributeValue(element, ELEMENT_DEFINITION_ATTRIBUTE_LOOKUP);
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, ELEMENT_DEFINITION_ATTRIBUTE_TYPE, defaultCardinalityDefinition);
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, CardinalityDefinition.REQUIRED, ELEMENT_DEFINITION_CHILD_ELEMENT_NAMES);
            Map<String, String> otherAttributes = getOtherAttributes(element, ELEMENT_DEFINITION_ATTRIBUTE_NAMES);
            return new ElementDefinition(id, lookup, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionBuildException("");
        }
    }

    @Override
    public ChoiceDefinition createChoiceDefinition(final Element element) {
        if (element.getNamespaceURI().equals(NAMESPACE) && element.getLocalName().equals(CHOICE_DEFINITION_ELEMENT_NAME)) {
            String id = getAttributeValue(element, CHOICE_DEFINITION_ATTRIBUTE_ID);
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, CHOICE_DEFINITION_ATTRIBUTE_TYPE, CardinalityDefinition.REQUIRED);
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, CardinalityDefinition.OPTIONAL, CHOICE_DEFINITION_CHILD_ELEMENT_NAMES);
            Map<String, String> otherAttributes = getOtherAttributes(element, CHOICE_DEFINITION_ATTRIBUTE_NAMES);
            return new ChoiceDefinition(id, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionBuildException("");
        }
    }

    @Override
    public FormReferenceDefinition createFormReferenceDefinition(final Element element) {
        if (element.getNamespaceURI().equals(NAMESPACE) && element.getLocalName().equals(FORM_REFERENCE_DEFINITION_ELEMENT_NAME)) {
            String group = getAttributeValue(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_GROUP);
            String id = getAttributeValue(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_ID);
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, null, FORM_REFERENCE_DEFINITION_CHILD_ELEMENT_NAMES);
            Map<String, String> otherAttributes = getOtherAttributes(element, FORM_REFERENCE_DEFINITION_ATTRIBUTE_NAMES);
            return new FormReferenceDefinition(group, id, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionBuildException("");
        }
    }

    @Override
    public AttributeDefinition createAttributeDefinition(final Element element) {
        if (element.getNamespaceURI().equals(NAMESPACE) && element.getLocalName().equals(ATTRIBUTE_DEFINITION_ELEMENT_NAME)) {
            String id = getAttributeValue(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_ID);
            String lookup = getAttributeValue(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_LOOKUP);
            CardinalityDefinition cardinalityDefinition = getCardinalityDefinition(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_TYPE, CardinalityDefinition.REQUIRED);
            List<NodeDefinition> nodeDefinitions = getNodeDefinitions(element, null, ATTRIBUTE_DEFINITION_CHILD_ELEMENT_NAMES);
            Map<String, String> otherAttributes = getOtherAttributes(element, ATTRIBUTE_DEFINITION_ATTRIBUTE_NAMES);
            return new AttributeDefinition(id, lookup, cardinalityDefinition, nodeDefinitions, otherAttributes);
        } else {
            throw new FormDefinitionBuildException("");
        }
    }

    private String getAttributeValue(final Element element, final String attributeName) {
        if (element.hasAttribute(attributeName)) {
            return element.getAttribute(attributeName);
        } else {
            return null;
        }
    }

    private CardinalityDefinition getCardinalityDefinition(final Element element, final String attributeName, final CardinalityDefinition defaultValue) {
        String attributeValue = getAttributeValue(element, attributeName);
        if (attributeValue == null) {
            return defaultValue;
        } else {
            return CardinalityDefinition.getCardinalityDefinition(attributeValue);
        }
    }

    private List<NodeDefinition> getNodeDefinitions(final Element element, final CardinalityDefinition defaultCardinalityDefinition, final Set<String> validElementNames) {
        List<NodeDefinition> nodeDefinitions = new ArrayList<>();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                if (childElement.getNamespaceURI().equals(NAMESPACE)) {
                    if (validElementNames.contains(childElement.getLocalName())) {
                        addNodeDefinition(childElement, defaultCardinalityDefinition, nodeDefinitions);
                    } else {
                        throw new FormDefinitionBuildException("");
                    }
                } else {
                    addOtherNodeDefinition(childElement, nodeDefinitions);
                }
            }
        }
        return nodeDefinitions;
    }

    private void addNodeDefinition(final Element element, final CardinalityDefinition defaultCardinalityDefinition, final List<NodeDefinition> nodeDefinitions) {
        if (ELEMENT_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
            NodeDefinition nodeDefinition = createElementDefinition(element, defaultCardinalityDefinition);
            nodeDefinitions.add(nodeDefinition);
        }
        if (CHOICE_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
            NodeDefinition nodeDefinition = createChoiceDefinition(element);
            nodeDefinitions.add(nodeDefinition);
        }
        if (FORM_REFERENCE_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
            NodeDefinition nodeDefinition = createFormReferenceDefinition(element);
            nodeDefinitions.add(nodeDefinition);
        }
        if (ATTRIBUTE_DEFINITION_ELEMENT_NAME.equals(element.getLocalName())) {
            NodeDefinition nodeDefinition = createAttributeDefinition(element);
            nodeDefinitions.add(nodeDefinition);
        }
    }

    private void addOtherNodeDefinition(final Element element, final List<NodeDefinition> nodeDefinitions) {
        for (OtherNodeDefinitionBuilder otherNodeDefinitionBuilder : _otherNodeDefinitionLoaders) {
            OtherNodeDefinition otherNodeDefinition = otherNodeDefinitionBuilder.createOtherNodeDefinition(element, this);
            if (otherNodeDefinition != null) {
                nodeDefinitions.add(otherNodeDefinition);
                return;
            }
        }
        OtherNodeDefinition otherNodeDefinition = new DefaultOtherNodeDefinitionBuilder().createOtherNodeDefinition(element, this);
        nodeDefinitions.add(otherNodeDefinition);
    }

    private Map<String, String> getOtherAttributes(final Element element, final Set<String> skipAttributeNames) {
        Map<String, String> additionalAttributes = new HashMap<>();
        NamedNodeMap namedNodeMap = element.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node node = namedNodeMap.item(i);
            String attributeName = node.getLocalName();
            if (!skipAttributeNames.contains(attributeName)) {
                String attributeValue = node.getNodeValue();
                additionalAttributes.put(attributeName, attributeValue);
            }
        }
        return additionalAttributes;
    }

}
