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
import org.xml.sax.helpers.DefaultHandler;

/**
 * XML document builder.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentBuilder {

    private final DocumentBuilder _documentBuilder;

    /**
     * Create new object.
     */
    public XmlDocumentBuilder() {
        this(null);
    }

    /**
     * Create new object.
     *
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     */
    public XmlDocumentBuilder(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator) {
        super();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            if (xmlDocumentBuilderConfigurator != null) {
                xmlDocumentBuilderConfigurator.configure(documentBuilderFactory);
            }
            documentBuilderFactory.setCoalescing(true);
            documentBuilderFactory.setIgnoringElementContentWhitespace(true);
            documentBuilderFactory.setNamespaceAware(true);
            _documentBuilder = documentBuilderFactory.newDocumentBuilder();
            _documentBuilder.setErrorHandler(new DefaultHandler());
        } catch (ParserConfigurationException ex) {
            throw new XmlDocumentBuilderConfigurationException(ex);
        }
    }

    /**
     * Get the XML document builder instance.
     *
     * @return the XML document builder instance.
     */
    public static XmlDocumentBuilder getDocumentBuilder() {
        return new XmlDocumentBuilder();
    }

    /**
     * Get the XML document builder instance.
     *
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     *
     * @return the XML document builder instance.
     */
    public static XmlDocumentBuilder getDocumentBuilder(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator) {
        return new XmlDocumentBuilder(xmlDocumentBuilderConfigurator);
    }

    /**
     * Create new XML document.
     *
     * @return new XML document.
     */
    public Document newDocument() {
        return _documentBuilder.newDocument();
    }

    /**
     * Parse the input source and create new XML document.
     *
     * @param inputSource the input source.
     *
     * @return new XML document.
     */
    public Document parse(final InputSource inputSource) {
        try {
            return _documentBuilder.parse(inputSource);
        } catch (IOException | SAXException ex) {
            throw new InputSourceException(ex);
        }
    }

}
