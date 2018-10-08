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
package ru.d_shap.formmodel.document;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.d_shap.formmodel.binding.FormInstanceBuilder;
import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedElement;

/**
 * Document lookup helper class.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentLookup {

    private DocumentLookup() {
        super();
    }

    /**
     * Perform XPath lookup and return the XML elements found.
     *
     * @param node   the source node.
     * @param lookup the XPath lookup expression.
     *
     * @return the XML elements found.
     */
    public static List<Element> getElements(final Node node, final String lookup) {
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression xPathExpression = xPath.compile(lookup);
            NodeList nodeList = (NodeList) xPathExpression.evaluate(node, XPathConstants.NODESET);
            List<Element> elements = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node item = nodeList.item(i);
                if (item instanceof Element) {
                    elements.add((Element) item);
                }
            }
            return elements;
        } catch (XPathExpressionException ex) {
            throw new DocumentLookupException(ex);
        }
    }

    /**
     * Perform XPath lookup and return the XML elements with the specified ID.
     *
     * @param node the source node.
     * @param id   the specified ID.
     *
     * @return the XML elements with the specified ID.
     */
    public static List<Element> getElementsWithId(final Node node, final String id) {
        String namespaceCondition = "namespace-uri() = '" + FormInstanceBuilder.NAMESPACE + "'";
        String attributeCondition = "local-name() = '" + FormInstanceBuilder.ATTRIBUTE_INSTANCE_ELEMENT_NAME + "' and @" + FormInstanceBuilder.ATTRIBUTE_INSTANCE_ATTRIBUTE_ID + " = '" + id + "'";
        String elementCondition = "local-name() = '" + FormInstanceBuilder.ELEMENT_INSTANCE_ELEMENT_NAME + "' and @" + FormInstanceBuilder.ELEMENT_INSTANCE_ATTRIBUTE_ID + " = '" + id + "'";
        String singleElementCondition = "local-name() = '" + FormInstanceBuilder.SINGLE_ELEMENT_INSTANCE_ELEMENT_NAME + "' and @" + FormInstanceBuilder.SINGLE_ELEMENT_INSTANCE_ATTRIBUTE_ID + " = '" + id + "'";
        String lookup = "//*[" + namespaceCondition + " and (" + attributeCondition + " or " + elementCondition + " or " + singleElementCondition + ")]";
        return getElements(node, lookup);
    }

    /**
     * Obtain the binded elements from the specified XML elements.
     *
     * @param elements the specified XML elements.
     *
     * @return the binded elements.
     */
    public static List<BindedElement> getBindedElements(final List<Element> elements) {
        return getBindedElements(elements, BindedElement.class);
    }

    /**
     * Obtain the binded elements from the specified XML elements.
     *
     * @param elements the specified XML elements.
     * @param clazz    the specified class of the binded element.
     * @param <T>      the generic type of the specified class of the binded element.
     *
     * @return the binded elements.
     */
    public static <T extends BindedElement> List<T> getBindedElements(final List<Element> elements, final Class<T> clazz) {
        List<T> bindedElements = new ArrayList<>();
        for (Element element : elements) {
            doGetBindedElements(element, bindedElements, clazz);
        }
        return bindedElements;
    }

    private static <T extends BindedElement> void doGetBindedElements(final Element element, final List<T> bindedElements, final Class<T> clazz) {
        if (FormInstanceBuilder.NAMESPACE.equals(element.getNamespaceURI())) {
            if (FormInstanceBuilder.ELEMENT_INSTANCE_ELEMENT_NAME.equals(element.getLocalName())) {
                Object bindedElement = element.getUserData(FormInstanceBuilder.USER_DATA_BINDED_OBJECT);
                if (clazz.isInstance(bindedElement)) {
                    bindedElements.add(clazz.cast(bindedElement));
                }
            } else if (FormInstanceBuilder.SINGLE_ELEMENT_INSTANCE_ELEMENT_NAME.equals(element.getLocalName())) {
                NodeList nodeList = element.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node item = nodeList.item(i);
                    if (item instanceof Element) {
                        doGetBindedElements((Element) item, bindedElements, clazz);
                    }
                }
            }
        }
    }

    /**
     * Obtain the binded attributes from the specified XML elements.
     *
     * @param elements the specified XML elements.
     *
     * @return the binded attributes.
     */
    public static List<BindedAttribute> getBindedAttributes(final List<Element> elements) {
        return getBindedAttributes(elements, BindedAttribute.class);
    }

    /**
     * Obtain the binded attributes from the specified XML elements.
     *
     * @param elements the specified XML elements.
     * @param clazz    the specified class of the binded attribute.
     * @param <T>      the generic type of the specified class of the binded attribute.
     *
     * @return the binded attributes.
     */
    public static <T extends BindedAttribute> List<T> getBindedAttributes(final List<Element> elements, final Class<T> clazz) {
        List<T> bindedAttributes = new ArrayList<>();
        for (Element element : elements) {
            doGetBindedAttributes(element, bindedAttributes, clazz);
        }
        return bindedAttributes;
    }

    private static <T extends BindedAttribute> void doGetBindedAttributes(final Element element, final List<T> bindedAttributes, final Class<T> clazz) {
        if (FormInstanceBuilder.NAMESPACE.equals(element.getNamespaceURI()) && FormInstanceBuilder.ATTRIBUTE_INSTANCE_ELEMENT_NAME.equals(element.getLocalName())) {
            Object bindedAttribute = element.getUserData(FormInstanceBuilder.USER_DATA_BINDED_OBJECT);
            if (clazz.isInstance(bindedAttribute)) {
                bindedAttributes.add(clazz.cast(bindedAttribute));
            }
        }
    }

}
