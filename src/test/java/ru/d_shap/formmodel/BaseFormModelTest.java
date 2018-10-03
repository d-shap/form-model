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
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import ru.d_shap.formmodel.definition.loader.FormDefinitionsLoader;
import ru.d_shap.formmodel.definition.loader.xml.FormXmlDefinitionsLoader;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;

/**
 * Base form model test class.
 *
 * @author Dmitry Shapovalov
 */
public class BaseFormModelTest {

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
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    public static final class DocumentBuilderFactoryConfiguratorImpl implements XmlDocumentBuilderConfigurator {

        /**
         * Create new object.
         */
        public DocumentBuilderFactoryConfiguratorImpl() {
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
    public static final class ErrorDocumentBuilderFactoryConfiguratorImpl implements XmlDocumentBuilderConfigurator {

        /**
         * Create new object.
         */
        public ErrorDocumentBuilderFactoryConfiguratorImpl() {
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
    public static final class ErrorInputStream extends InputStream {

        /**
         * Create new object.
         */
        public ErrorInputStream() {
            super();
        }

        @Override
        public int read() throws IOException {
            throw new IOException("ERROR!");
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

        @Override
        public List<FormDefinition> load() {
            return _formDefinitions;
        }

    }

}
