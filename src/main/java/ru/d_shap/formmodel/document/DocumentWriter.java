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

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import ru.d_shap.formmodel.OutputResultException;

/**
 * Document writer helper class.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentWriter {

    private DocumentWriter() {
        super();
    }

    /**
     * Create new document writer with the XML declaration.
     *
     * @return document writer for the next invocation.
     */
    public static DocumentWriterImpl withXmlDeclaration() {
        DocumentWriterImpl documentWriter = new DocumentWriterImpl();
        documentWriter._xmlDeclaration = true;
        return documentWriter;
    }

    /**
     * Create new document writer with the specified encoding.
     *
     * @param encoding the specified encoding.
     *
     * @return document writer for the next invocation.
     */
    public static DocumentWriterImpl withEncoding(final String encoding) {
        DocumentWriterImpl documentWriter = new DocumentWriterImpl();
        documentWriter._encoding = encoding;
        return documentWriter;
    }

    /**
     * Create new document writer with the standalone declaration.
     *
     * @return document writer for the next invocation.
     */
    public static DocumentWriterImpl withStandalone() {
        DocumentWriterImpl documentWriter = new DocumentWriterImpl();
        documentWriter._standalone = true;
        return documentWriter;
    }

    /**
     * Create new document writer with the indentation.
     *
     * @return document writer for the next invocation.
     */
    public static DocumentWriterImpl withIndent() {
        DocumentWriterImpl documentWriter = new DocumentWriterImpl();
        documentWriter._indent = true;
        return documentWriter;
    }

    /**
     * Write the XML node to the specified writer.
     *
     * @param node   the XML node.
     * @param writer the specified writer
     */
    public static void writeTo(final Node node, final Writer writer) {
        DocumentWriterImpl documentWriter = new DocumentWriterImpl();
        documentWriter.writeTo(node, writer);
    }

    /**
     * Write the XML node to the specified output stream.
     *
     * @param node         the XML node.
     * @param outputStream the specified output stream.
     * @param encoding     the encoding the specified output stream.
     */
    public static void writeTo(final Node node, final OutputStream outputStream, final String encoding) {
        DocumentWriterImpl documentWriter = new DocumentWriterImpl();
        documentWriter.writeTo(node, outputStream, encoding);
    }

    /**
     * Get the string representation of the XML node.
     *
     * @param node the XML node.
     *
     * @return the string representation of the XML node.
     */
    public static String getAsString(final Node node) {
        DocumentWriterImpl documentWriter = new DocumentWriterImpl();
        return documentWriter.getAsString(node);
    }

    /**
     * Document writer implementation.
     *
     * @author Dmitry Shapovalov
     */
    public static final class DocumentWriterImpl {

        private boolean _xmlDeclaration;

        private String _encoding;

        private boolean _standalone;

        private boolean _indent;

        DocumentWriterImpl() {
            super();
        }

        private DocumentWriterImpl(final DocumentWriterImpl documentWriter) {
            super();
            _xmlDeclaration = documentWriter._xmlDeclaration;
            _encoding = documentWriter._encoding;
            _standalone = documentWriter._standalone;
            _indent = documentWriter._indent;
        }

        /**
         * Create new document writer with the XML declaration.
         *
         * @return document writer for the next invocation.
         */
        public DocumentWriterImpl andXmlDeclaration() {
            DocumentWriterImpl documentWriter = new DocumentWriterImpl(this);
            documentWriter._xmlDeclaration = true;
            return documentWriter;
        }

        /**
         * Create new document writer with the specified encoding.
         *
         * @param encoding the specified encoding.
         *
         * @return document writer for the next invocation.
         */
        public DocumentWriterImpl andEncoding(final String encoding) {
            DocumentWriterImpl documentWriter = new DocumentWriterImpl(this);
            documentWriter._encoding = encoding;
            return documentWriter;
        }

        /**
         * Create new document writer with the standalone declaration.
         *
         * @return document writer for the next invocation.
         */
        public DocumentWriterImpl andStandalone() {
            DocumentWriterImpl documentWriter = new DocumentWriterImpl(this);
            documentWriter._standalone = true;
            return documentWriter;
        }

        /**
         * Create new document writer with the indentation.
         *
         * @return document writer for the next invocation.
         */
        public DocumentWriterImpl andIndent() {
            DocumentWriterImpl documentWriter = new DocumentWriterImpl(this);
            documentWriter._indent = true;
            return documentWriter;
        }

        /**
         * Write the XML node to the specified writer.
         *
         * @param node   the XML node.
         * @param writer the specified writer
         */
        public void writeTo(final Node node, final Writer writer) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setErrorListener(new SkipErrorListener());

                if (_xmlDeclaration) {
                    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                } else {
                    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                }
                if (_encoding != null) {
                    transformer.setOutputProperty(OutputKeys.ENCODING, _encoding);
                }
                if (_standalone) {
                    transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
                } else {
                    transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
                }
                if (_indent) {
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                } else {
                    transformer.setOutputProperty(OutputKeys.INDENT, "no");
                }

                transformer.transform(new DOMSource(node), new StreamResult(writer));
            } catch (TransformerException ex) {
                throw new OutputResultException(ex);
            }
        }

        /**
         * Write the XML node to the specified output stream.
         *
         * @param node         the XML node.
         * @param outputStream the specified output stream.
         * @param encoding     the encoding the specified output stream.
         */
        public void writeTo(final Node node, final OutputStream outputStream, final String encoding) {
            try {
                andEncoding(encoding).writeTo(node, new OutputStreamWriter(outputStream, encoding));
            } catch (IOException ex) {
                throw new OutputResultException(ex);
            }
        }

        /**
         * Get the string representation of the XML node.
         *
         * @param node the XML node.
         *
         * @return the string representation of the XML node.
         */
        public String getAsString(final Node node) {
            Writer writer = new StringWriter();
            writeTo(node, writer);
            return writer.toString();
        }

    }

    /**
     * Error listener to prevent write to System error stream.
     *
     * @author Dmitry Shapovalov
     */
    static final class SkipErrorListener implements ErrorListener {

        SkipErrorListener() {
            super();
        }

        @Override
        public void warning(final TransformerException exception) throws TransformerException {
            // Ignore
        }

        @Override
        public void error(final TransformerException exception) throws TransformerException {
            // Ignore
        }

        @Override
        public void fatalError(final TransformerException exception) throws TransformerException {
            // Ignore
        }

    }

}
