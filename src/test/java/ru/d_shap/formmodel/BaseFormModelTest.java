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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedElementImpl;
import ru.d_shap.formmodel.definition.loader.FormDefinitionsLoader;
import ru.d_shap.formmodel.definition.loader.xml.FormXmlDefinitionsElementLoader;
import ru.d_shap.formmodel.definition.loader.xml.FormXmlDefinitionsLoader;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;
import ru.d_shap.formmodel.document.DocumentLookup;
import ru.d_shap.formmodel.document.DocumentProcessor;

/**
 * Base form model test class.
 *
 * @author Dmitry Shapovalov
 */
public class BaseFormModelTest {

    public static final String ENCODING_UTF_8 = StandardCharsets.UTF_8.name();

    public static final String ENCODING_UTF_16 = StandardCharsets.UTF_16.name();

    /**
     * Create new object.
     */
    protected BaseFormModelTest() {
        super();
    }

    /**
     * Create the node definition list.
     *
     * @param nodeDefinitions the node definitions.
     *
     * @return the node definition list.
     */
    protected final List<NodeDefinition> createNodeDefinitions(final NodeDefinition... nodeDefinitions) {
        return Arrays.asList(nodeDefinitions);
    }

    /**
     * Create the other attributes map.
     *
     * @param attributes the attribute names and values.
     *
     * @return the other attributes map.
     */
    protected final Map<String, String> createOtherAttributes(final String... attributes) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < attributes.length; i += 2) {
            result.put(attributes[i], attributes[i + 1]);
        }
        return result;
    }

    /**
     * Create the valid child element set.
     *
     * @param elements the valid child elements.
     *
     * @return the valid child element set.
     */
    protected final Set<String> createValidElements(final String... elements) {
        if (elements.length == 0) {
            Set<String> result = new HashSet<>();
            result.add(AttributeDefinition.ELEMENT_NAME);
            result.add(ElementDefinition.ELEMENT_NAME);
            result.add(SingleElementDefinition.ELEMENT_NAME);
            result.add(FormReferenceDefinition.ELEMENT_NAME);
            return result;
        } else {
            return new HashSet<>(Arrays.asList(elements));
        }
    }

    /**
     * Create the skip attributes set.
     *
     * @param attributes the attribute names.
     *
     * @return the skip attributes set.
     */
    protected final Set<String> createSkipAttributes(final String... attributes) {
        return new HashSet<>(Arrays.asList(attributes));
    }

    /**
     * Create new XML document.
     *
     * @return new XML document.
     */
    protected final Document newDocument() {
        return XmlDocumentBuilder.getDocumentBuilder().newDocument();
    }

    /**
     * Parse XML document.
     *
     * @param xml XML document to parse.
     *
     * @return XML document.
     */
    protected final Document parse(final String xml) {
        XmlDocumentBuilder documentBuilder = XmlDocumentBuilder.getDocumentBuilder();
        Reader reader = new StringReader(xml);
        InputSource inputSource = new InputSource(reader);
        return documentBuilder.parse(inputSource);
    }

    /**
     * Create the list of the form definitions.
     *
     * @param formDefinitions the form definitions.
     *
     * @return the list of the form definitions.
     */
    protected final List<FormDefinition> createFormDefinitionList(final FormDefinition... formDefinitions) {
        return Arrays.asList(formDefinitions);
    }

    /**
     * Create container for all form definitions.
     *
     * @param xmls the source XML documents.
     *
     * @return container for all form definitions.
     */
    protected final FormDefinitions createFormDefinitionsFromXml(final String... xmls) {
        List<FormDefinition> formDefinitions1 = new ArrayList<>();
        for (String xml : xmls) {
            Document document = parse(xml);
            FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader = new FormXmlDefinitionsElementLoader(document.getDocumentElement(), "source");
            List<FormDefinition> formDefinitions2 = formXmlDefinitionsElementLoader.load();
            formDefinitions1.addAll(formDefinitions2);
        }
        FormDefinitions formDefinitions = new FormDefinitions();
        formDefinitions.addFormDefinitions(formDefinitions1);
        return formDefinitions;
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class XmlDocumentBuilderConfiguratorImpl implements XmlDocumentBuilderConfigurator {

        /**
         * Create new object.
         */
        public XmlDocumentBuilderConfiguratorImpl() {
            super();
        }

        @Override
        public void configure(final DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
            documentBuilderFactory.setNamespaceAware(true);
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class ErrorXmlDocumentBuilderConfiguratorImpl implements XmlDocumentBuilderConfigurator {

        /**
         * Create new object.
         */
        public ErrorXmlDocumentBuilderConfiguratorImpl() {
            super();
        }

        @Override
        public void configure(final DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setFeature("some fake feature", true);
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class ReadErrorInputStream extends InputStream {

        /**
         * Create new object.
         */
        public ReadErrorInputStream() {
            super();
        }

        @Override
        public int read() throws IOException {
            throw new IOException("READ ERROR!");
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class CloseErrorInputStream extends InputStream {

        /**
         * Create new object.
         */
        public CloseErrorInputStream() {
            super();
        }

        @Override
        public int read() throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
            throw new IOException("CLOSE ERROR!");
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class CloseableInputStream extends InputStream {

        private final InputStream _inputStream;

        private boolean _closed;

        /**
         * Create new object.
         *
         * @param inputStream input stream.
         */
        public CloseableInputStream(final InputStream inputStream) {
            super();
            _inputStream = inputStream;
            _closed = false;
        }

        @Override
        public int read() throws IOException {
            return _inputStream.read();
        }

        @Override
        public void close() throws IOException {
            _inputStream.close();
            _closed = true;
        }

        /**
         * Check if input stream is closed.
         *
         * @return true if input stream is closed.
         */
        public boolean isClosed() {
            return _closed;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class WriteErrorWriter extends Writer {

        /**
         * Create new object.
         */
        public WriteErrorWriter() {
            super();
        }

        @Override
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            throw new IOException("WRITE ERROR!");
        }

        @Override
        public void flush() throws IOException {
            // Ignore
        }

        @Override
        public void close() throws IOException {
            // Ignore
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class CloseableWriter extends Writer {

        private final Writer _writer;

        private boolean _closed;

        /**
         * Create new object.
         *
         * @param writer writer
         */
        public CloseableWriter(final Writer writer) {
            super();
            _writer = writer;
            _closed = false;
        }

        @Override
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            _writer.write(cbuf, off, len);
        }

        @Override
        public void flush() throws IOException {
            _writer.flush();
        }

        @Override
        public void close() throws IOException {
            _writer.close();
            _closed = true;
        }

        /**
         * Check if input stream is closed.
         *
         * @return true if input stream is closed.
         */
        public boolean isClosed() {
            return _closed;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class AnotherNodeDefinition implements OtherNodeDefinition {

        /**
         * Create new object.
         */
        public AnotherNodeDefinition() {
            super();
        }

        @Override
        public String toString() {
            return "another";
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class AnotherBindedElement implements BindedElement {

        /**
         * Create new object.
         */
        public AnotherBindedElement() {
            super();
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class AnotherBindedAttribute implements BindedAttribute {

        /**
         * Create new object.
         */
        public AnotherBindedAttribute() {
            super();
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class FormDefinitionsLoaderImpl extends FormDefinitionsLoader {

        private final List<FormDefinition> _formDefinitions;

        /**
         * Create new object.
         *
         * @param formDefinitions the form definitions for this loader.
         */
        public FormDefinitionsLoaderImpl(final FormDefinition... formDefinitions) {
            super();
            _formDefinitions = Arrays.asList(formDefinitions);
        }

        @Override
        public List<FormDefinition> load() {
            return _formDefinitions;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class FormXmlDefinitionsLoaderImpl extends FormXmlDefinitionsLoader {

        private final List<FormDefinition> _formDefinitions;

        /**
         * Create new object.
         *
         * @param formDefinitions the form definitions for this loader.
         */
        public FormXmlDefinitionsLoaderImpl(final FormDefinition... formDefinitions) {
            super();
            _formDefinitions = Arrays.asList(formDefinitions);
        }

        /**
         * Create new object.
         *
         * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
         * @param formDefinitions                the form definitions for this loader.
         */
        public FormXmlDefinitionsLoaderImpl(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator, final FormDefinition... formDefinitions) {
            super(xmlDocumentBuilderConfigurator);
            _formDefinitions = Arrays.asList(formDefinitions);
        }

        /**
         * Create new object.
         *
         * @param formXmlDefinitionsLoader loader for the form definitions.
         * @param formDefinitions          the form definitions for this loader.
         */
        public FormXmlDefinitionsLoaderImpl(final FormXmlDefinitionsLoader formXmlDefinitionsLoader, final FormDefinition... formDefinitions) {
            super(formXmlDefinitionsLoader);
            _formDefinitions = Arrays.asList(formDefinitions);
        }

        @Override
        public List<FormDefinition> load() {
            return _formDefinitions;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class EmptyDirectoryFile extends File {

        private static final long serialVersionUID = 1L;

        /**
         * Create new object.
         */
        public EmptyDirectoryFile() {
            super("file");
        }

        @Override
        public boolean isDirectory() {
            return true;
        }

        @Override
        public File[] listFiles() {
            return null;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class SkipFileFilter implements FileFilter {

        /**
         * Create new object.
         */
        public SkipFileFilter() {
            super();
        }

        @Override
        public boolean accept(final File file) {
            return false;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class DocumentProcessorImpl implements DocumentProcessor<List<String>> {

        private final String _id;

        /**
         * Create new object.
         *
         * @param id element's ID to looup.
         */
        public DocumentProcessorImpl(final String id) {
            super();
            _id = id;
        }

        @Override
        public List<String> process(final Document document) {
            DocumentLookup documentLookup = DocumentLookup.getDocumentLookup();
            List<Element> elements = documentLookup.getElementsWithId(document, _id);
            List<BindedElementImpl> bindedElements = documentLookup.getBindedElements(elements, BindedElementImpl.class);
            List<String> result = new ArrayList<>();
            for (BindedElementImpl bindedElement : bindedElements) {
                result.add(bindedElement.toString());
            }
            return result;
        }

    }

}
