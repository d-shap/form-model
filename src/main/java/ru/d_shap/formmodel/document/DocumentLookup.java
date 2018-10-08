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
     * Perform XPath lookup and return the elements found.
     *
     * @param node   the source node.
     * @param lookup the XPath expression.
     *
     * @return the elements found.
     */
    public static List<Element> lookupElements(final Node node, final String lookup) {
        try {
            XPath xPath = createXPath();
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

    private static XPath createXPath() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        return xPathFactory.newXPath();
    }

    /**
     * Perform XPath lookup and return the binded elements found.
     *
     * @param node   the source node.
     * @param lookup the XPath expression.
     *
     * @return the binded elements found.
     */
    public static List<BindedElement> lookupBindedElements(final Node node, final String lookup) {
        List<Element> elements = lookupElements(node, lookup);
        List<BindedElement> bindedElements = new ArrayList<>();
        for (Element element : elements) {
            Object object = element.getUserData(FormInstanceBuilder.USER_DATA_BINDED_OBJECT);
            if (object instanceof BindedElement) {
                bindedElements.add((BindedElement) object);
            }
        }
        return bindedElements;
    }

    /**
     * Filter the list of the binded elements and return the binded elements with the specified class.
     *
     * @param bindedElements the list of the binded elements.
     * @param clazz          the specified class.
     * @param <T>            the generic type of the specified class.
     *
     * @return the binded elements with the specified class.
     */
    public static <T extends BindedElement> List<T> getBindedElements(final List<BindedElement> bindedElements, final Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (BindedElement bindedElement : bindedElements) {
            if (clazz.isInstance(bindedElement)) {
                result.add(clazz.cast(bindedElement));
            }
        }
        return result;
    }

}
