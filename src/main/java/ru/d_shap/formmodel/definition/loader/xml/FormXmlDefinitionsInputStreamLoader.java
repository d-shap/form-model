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
package ru.d_shap.formmodel.definition.loader.xml;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import ru.d_shap.formmodel.InputSourceReadException;
import ru.d_shap.formmodel.XmlDocumentBuilderConfigurator;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Input stream loader for the form definitions, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsInputStreamLoader extends FormXmlDefinitionsLoader implements Closeable {

    private final InputStream _inputStream;

    private final String _source;

    /**
     * Create new object.
     *
     * @param inputStream the source input stream.
     * @param source      the form's source.
     */
    public FormXmlDefinitionsInputStreamLoader(final InputStream inputStream, final String source) {
        super();
        _inputStream = inputStream;
        _source = source;
    }

    /**
     * Create new object.
     *
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     * @param inputStream                    the source input stream.
     * @param source                         the form's source.
     */
    public FormXmlDefinitionsInputStreamLoader(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator, final InputStream inputStream, final String source) {
        super(xmlDocumentBuilderConfigurator);
        _inputStream = inputStream;
        _source = source;
    }

    /**
     * Create new object.
     *
     * @param formXmlDefinitionsLoader loader for the form definitions.
     * @param inputStream              the source input stream.
     * @param source                   the form's source.
     */
    public FormXmlDefinitionsInputStreamLoader(final FormXmlDefinitionsLoader formXmlDefinitionsLoader, final InputStream inputStream, final String source) {
        super(formXmlDefinitionsLoader);
        _inputStream = inputStream;
        _source = source;
    }

    @Override
    public List<FormDefinition> load() {
        try {
            try {
                InputSource inputSource = new InputSource(_inputStream);
                Document document = getXmlDocumentBuilder().parse(inputSource);
                Element element = document.getDocumentElement();
                FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader = new FormXmlDefinitionsElementLoader(this, element, _source);
                return formXmlDefinitionsElementLoader.load();
            } finally {
                close();
            }
        } catch (IOException ex) {
            throw new InputSourceReadException(ex);
        }
    }

    @Override
    public void close() throws IOException {
        _inputStream.close();
    }

}
