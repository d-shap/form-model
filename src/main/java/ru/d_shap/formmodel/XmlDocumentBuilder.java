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

    private static final DocumentBuilderFactoryConfigurator DOCUMENT_BUILDER_FACTORY_CONFIGURATOR = new DefaultDocumentBuilderFactoryConfigurator();

    private XmlDocumentBuilder() {
        super();
    }

    /**
     * Create new XML document.
     *
     * @return new XML document.
     */
    public static Document newDocument() {
        return getDocumentBuilder(DOCUMENT_BUILDER_FACTORY_CONFIGURATOR).newDocument();
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
            return getDocumentBuilder(DOCUMENT_BUILDER_FACTORY_CONFIGURATOR).parse(inputSource);
        } catch (IOException | SAXException ex) {
            throw new InputSourceReadException(ex);
        }
    }

    static DocumentBuilder getDocumentBuilder(final DocumentBuilderFactoryConfigurator documentBuilderFactoryConfigurator) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactoryConfigurator.configure(documentBuilderFactory);
            return documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new XmlDocumentBuilderException(ex);
        }
    }

    /**
     * Configurator for the XML document builder factory.
     *
     * @author Dmitry Shapovalov
     */
    interface DocumentBuilderFactoryConfigurator {

        void configure(DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException;

    }

    /**
     * Default configurator for the XML document builder factory.
     *
     * @author Dmitry Shapovalov
     */
    static final class DefaultDocumentBuilderFactoryConfigurator implements DocumentBuilderFactoryConfigurator {

        DefaultDocumentBuilderFactoryConfigurator() {
            super();
        }

        @Override
        public void configure(final DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
            documentBuilderFactory.setNamespaceAware(true);
        }

    }

}
