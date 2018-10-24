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
import ru.d_shap.formmodel.binding.model.BindedObject;
import ru.d_shap.formmodel.utils.IdentityHelper;

/**
 * Document lookup helper class.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentLookup {

    private final XPath _xPath;

    /**
     * Create new object.
     */
    public DocumentLookup() {
        super();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        _xPath = xPathFactory.newXPath();
    }

    /**
     * Get the document lookup instance.
     *
     * @return the document lookup instance.
     */
    public static DocumentLookup getDocumentLookup() {
        return new DocumentLookup();
    }

    /**
     * Perform lookup and return the XML elements found.
     *
     * @param node   the source node.
     * @param lookup the XPath lookup expression.
     *
     * @return the XML elements found.
     */
    public List<Element> getElements(final Node node, final String lookup) {
        try {
            XPathExpression xPathExpression = _xPath.compile(lookup);
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
     * Perform lookup and return the XML elements with the specified ID.
     *
     * @param node the source node.
     * @param id   the specified ID.
     *
     * @return the XML elements found.
     */
    public List<Element> getElementsWithId(final Node node, final String id) {
        String namespaceCondition = "namespace-uri() = '" + FormInstanceBuilder.NAMESPACE + "'";
        String attributeCondition = "local-name() = '" + FormInstanceBuilder.ATTRIBUTE_INSTANCE_ELEMENT_NAME + "' and @" + FormInstanceBuilder.ATTRIBUTE_INSTANCE_ATTRIBUTE_ID + " = '" + id + "'";
        String elementCondition = "local-name() = '" + FormInstanceBuilder.ELEMENT_INSTANCE_ELEMENT_NAME + "' and @" + FormInstanceBuilder.ELEMENT_INSTANCE_ATTRIBUTE_ID + " = '" + id + "'";
        String singleElementCondition = "local-name() = '" + FormInstanceBuilder.SINGLE_ELEMENT_INSTANCE_ELEMENT_NAME + "' and @" + FormInstanceBuilder.SINGLE_ELEMENT_INSTANCE_ATTRIBUTE_ID + " = '" + id + "'";
        String lookup = ".//*[" + namespaceCondition + " and (" + attributeCondition + " or " + elementCondition + " or " + singleElementCondition + ")]";
        return getElements(node, lookup);
    }

    /**
     * Perform lookup and return the XML elements with the specified attribute value for the specified attribute name.
     *
     * @param node           the source node.
     * @param attributeName  the specified attribute name.
     * @param attributeValue the specified attribute value.
     *
     * @return the XML elements found.
     */
    public List<Element> getElementsWithAttribute(final Node node, final String attributeName, final String attributeValue) {
        String namespaceCondition = "namespace-uri() = '" + FormInstanceBuilder.NAMESPACE + "'";
        String attributeCondition = "local-name() = '" + FormInstanceBuilder.ATTRIBUTE_INSTANCE_ELEMENT_NAME + "'";
        String elementCondition = "local-name() = '" + FormInstanceBuilder.ELEMENT_INSTANCE_ELEMENT_NAME + "'";
        String singleElementCondition = "local-name() = '" + FormInstanceBuilder.SINGLE_ELEMENT_INSTANCE_ELEMENT_NAME + "'";
        String lookupCondition = "@" + attributeName + " = '" + attributeValue + "'";
        String lookup = ".//*[" + namespaceCondition + " and (" + attributeCondition + " or " + elementCondition + " or " + singleElementCondition + ") and " + lookupCondition + "]";
        return getElements(node, lookup);
    }

    /**
     * Obtain the binded objects from the specified XML elements.
     *
     * @param elements the specified XML elements.
     *
     * @return the binded objects.
     */
    public List<BindedObject> getBindedObjects(final List<Element> elements) {
        return getBindedObjects(elements, BindedObject.class);
    }

    /**
     * Obtain the binded objects from the specified XML elements.
     *
     * @param elements the specified XML elements.
     * @param clazz    the specified class of the binded object.
     * @param <T>      the generic type of the specified class of the binded object.
     *
     * @return the binded objects.
     */
    public <T extends BindedObject> List<T> getBindedObjects(final List<Element> elements, final Class<T> clazz) {
        List<T> bindedObjects = new ArrayList<>();
        for (Element element : elements) {
            getBindedObjects(element, bindedObjects, clazz);
        }
        return bindedObjects;
    }

    private <T extends BindedObject> void getBindedObjects(final Element element, final List<T> bindedObjects, final Class<T> bindedObjectClass) {
        Object bindedObject = element.getUserData(FormInstanceBuilder.USER_DATA_BINDED_OBJECT);
        if (bindedObject != null) {
            if (bindedObjectClass.isInstance(bindedObject)) {
                T castedBindedObject = bindedObjectClass.cast(bindedObject);
                if (!IdentityHelper.contains(bindedObjects, castedBindedObject)) {
                    bindedObjects.add(castedBindedObject);
                }
            }
            return;
        }
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if (item instanceof Element) {
                getBindedObjects((Element) item, bindedObjects, bindedObjectClass);
            }
        }
    }

    /**
     * Obtain the binded elements from the specified XML elements.
     *
     * @param elements the specified XML elements.
     *
     * @return the binded elements.
     */
    public List<BindedElement> getBindedElements(final List<Element> elements) {
        return getBindedObjects(elements, BindedElement.class);
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
    public <T extends BindedElement> List<T> getBindedElements(final List<Element> elements, final Class<T> clazz) {
        return getBindedObjects(elements, clazz);
    }

    /**
     * Obtain the binded attributes from the specified XML elements.
     *
     * @param elements the specified XML elements.
     *
     * @return the binded attributes.
     */
    public List<BindedAttribute> getBindedAttributes(final List<Element> elements) {
        return getBindedObjects(elements, BindedAttribute.class);
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
    public <T extends BindedAttribute> List<T> getBindedAttributes(final List<Element> elements, final Class<T> clazz) {
        return getBindedObjects(elements, clazz);
    }

}
