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
package ru.d_shap.formmodel.binding;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
     * @param document the source document.
     * @param lookup   the XPath expression.
     *
     * @return the elements found.
     *
     * @throws XPathExpressionException XPath expression exception.
     */
    public static List<Element> lookupElements(final Document document, final String lookup) throws XPathExpressionException {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = xpath.compile(lookup);
        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                elements.add((Element) node);
            }
        }
        return elements;
    }

    /**
     * Perform XPath lookup and return the binded elements found.
     *
     * @param document the source document.
     * @param lookup   the XPath expression.
     *
     * @return the binded elements found.
     *
     * @throws XPathExpressionException XPath expression exception.
     */
    public static List<BindedElement> lookupBindedElements(final Document document, final String lookup) throws XPathExpressionException {
        List<Element> elements = lookupElements(document, lookup);
        List<BindedElement> bindedElements = new ArrayList<>();
        for (Element element : elements) {
            Object object = element.getUserData(FormInstanceBuilder.USER_DATA_BINDED_OBJECT);
            if (object instanceof BindedElement) {
                bindedElements.add((BindedElement) object);
            }
        }
        return bindedElements;
    }

}
