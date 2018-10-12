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

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.XmlDocumentBuilderConfigurator;
import ru.d_shap.formmodel.XmlDocumentValidator;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.loader.FormDefinitionsLoader;

/**
 * Base loader for the form definitions, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public abstract class FormXmlDefinitionsLoader extends FormDefinitionsLoader {

    private final XmlDocumentBuilder _xmlDocumentBuilder;

    private final XmlDocumentValidator _xmlDocumentValidator;

    final List<OtherNodeXmlDefinitionBuilder> _otherNodeXmlDefinitionBuilders;

    private final FormXmlDefinitionBuilderImpl _formXmlDefinitionBuilder;

    /**
     * Create new object.
     */
    protected FormXmlDefinitionsLoader() {
        super();
        _xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder(null);
        _xmlDocumentValidator = XmlDocumentValidator.getFormModelDocumentValidator();
        _otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        _formXmlDefinitionBuilder = new FormXmlDefinitionBuilderImpl(_otherNodeXmlDefinitionBuilders);
    }

    /**
     * Create new object.
     *
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     */
    protected FormXmlDefinitionsLoader(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator) {
        super();
        _xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder(xmlDocumentBuilderConfigurator);
        _xmlDocumentValidator = XmlDocumentValidator.getFormModelDocumentValidator();
        _otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        _formXmlDefinitionBuilder = new FormXmlDefinitionBuilderImpl(_otherNodeXmlDefinitionBuilders);
    }

    /**
     * Create new object.
     *
     * @param formXmlDefinitionsLoader loader for the form definitions.
     */
    protected FormXmlDefinitionsLoader(final FormXmlDefinitionsLoader formXmlDefinitionsLoader) {
        super();
        _xmlDocumentBuilder = formXmlDefinitionsLoader._xmlDocumentBuilder;
        _xmlDocumentValidator = formXmlDefinitionsLoader._xmlDocumentValidator;
        _otherNodeXmlDefinitionBuilders = formXmlDefinitionsLoader._otherNodeXmlDefinitionBuilders;
        _formXmlDefinitionBuilder = formXmlDefinitionsLoader._formXmlDefinitionBuilder;
    }

    /**
     * Parse the input source and create new XML document.
     *
     * @param inputSource the input source.
     *
     * @return new XML document.
     */
    protected final Document parse(final InputSource inputSource) {
        return _xmlDocumentBuilder.parse(inputSource);
    }

    /**
     * Validate the XML node against the schema.
     *
     * @param node the XML node to validate.
     */
    protected final void validate(final Node node) {
        try {
            _xmlDocumentValidator.validate(node);
            for (OtherNodeXmlDefinitionBuilder otherNodeXmlDefinitionBuilder : _otherNodeXmlDefinitionBuilders) {
                otherNodeXmlDefinitionBuilder.validate(node);
            }
        } catch (SAXException ex) {
            throw new FormDefinitionValidationException(ex);
        }
    }

    /**
     * Get the builder for the form definition.
     *
     * @return the builder for the form definition.
     */
    protected final FormXmlDefinitionBuilderImpl getFormXmlDefinitionBuilder() {
        return _formXmlDefinitionBuilder;
    }

}
