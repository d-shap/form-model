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
package ru.d_shap.formmodel.loader;

import java.io.IOException;
import java.io.InputStream;
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

import ru.d_shap.formmodel.definition.ElementDefinition;
import ru.d_shap.formmodel.definition.ElementDefinitionType;
import ru.d_shap.formmodel.definition.FormDefinition;
import ru.d_shap.formmodel.definition.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.NodeDefinition;

public final class FormDefinitionLoader {

    private FormDefinitionLoader() {
        super();
    }

    public static FormDefinition load(final InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(inputStream);
        Document document = builder.parse(inputSource);
        return createFormDefinition(document);
    }

    private static FormDefinition createFormDefinition(final Document document) {
        Element element = document.getDocumentElement();
        if ("form".equals(element.getNodeName())) {
            String id = element.getAttribute("id");
            FormDefinition formDefinition = new FormDefinition(id);
            processChildNodes(element, formDefinition.getNodeDefinitions());
            return formDefinition;
        } else {
            return null;
        }
    }

    private static void processChildNodes(final Element element, final List<NodeDefinition> nodeDefinitions) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                Element childElement = (Element) childNode;
                if ("element".equals(childElement.getNodeName())) {
                    ElementDefinition childElementDefinition = createElementDefinition(childElement);
                    nodeDefinitions.add(childElementDefinition);
                }
                if ("form".equals(childElement.getNodeName())) {
                    FormReferenceDefinition childFormReferenceDefinition = createFormReferenceDefinition(childElement);
                    nodeDefinitions.add(childFormReferenceDefinition);
                }
            }
        }
    }

    private static ElementDefinition createElementDefinition(final Element element) {
        String id = element.getAttribute("id");
        String lookup = element.getAttribute("lookup");
        String type = element.getAttribute("type");
        ElementDefinition elementDefinition = new ElementDefinition(id, lookup, ElementDefinitionType.getElementDefinitionType(type));
        processChildNodes(element, elementDefinition.getChildNodeDefinitions());
        return elementDefinition;
    }

    private static FormReferenceDefinition createFormReferenceDefinition(final Element element) {
        String refId = element.getAttribute("refid");
        return new FormReferenceDefinition(refId);
    }

}
