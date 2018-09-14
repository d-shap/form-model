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
package ru.d_shap.formmodel;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML document builder.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentBuilder {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;

    static {
        DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);
    }

    private XmlDocumentBuilder() {
        super();
    }

    /**
     * Create new XML document.
     *
     * @return new XML document.
     */
    public static Document newDocument() {
        return getDocumentBuilder().newDocument();
    }

    /**
     * Parse the input source and create new XML document.
     *
     * @param inputSource the input source.
     *
     * @return new XML document.
     */
    public static Document parse(final InputSource inputSource) {
        try {
            return getDocumentBuilder().parse(inputSource);
        } catch (IOException | SAXException ex) {
            throw new InputSourceReadException(ex);
        }
    }

    private static DocumentBuilder getDocumentBuilder() {
        try {
            return DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new XmlDocumentBuilderException(ex);
        }
    }

}
