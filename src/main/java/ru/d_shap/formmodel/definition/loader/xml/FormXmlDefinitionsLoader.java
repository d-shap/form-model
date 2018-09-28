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
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.XmlDocumentBuilderConfigurator;
import ru.d_shap.formmodel.XmlDocumentValidator;
import ru.d_shap.formmodel.definition.loader.FormDefinitionsLoader;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Base loader for the form definitions, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public abstract class FormXmlDefinitionsLoader extends FormDefinitionsLoader {

    private final XmlDocumentBuilder _xmlDocumentBuilder;

    private final XmlDocumentValidator _xmlDocumentValidator;

    private final FormXmlDefinitionBuilderImpl _formXmlDefinitionBuilder;

    /**
     * Create new object.
     */
    protected FormXmlDefinitionsLoader() {
        this((XmlDocumentBuilderConfigurator) null);
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
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        _formXmlDefinitionBuilder = new FormXmlDefinitionBuilderImpl(otherNodeXmlDefinitionBuilders);
    }

    /**
     * Create new object.
     *
     * @param formDefinitionsLoader another loader for the form definitions.
     */
    protected FormXmlDefinitionsLoader(final FormXmlDefinitionsLoader formDefinitionsLoader) {
        super();
        _xmlDocumentBuilder = formDefinitionsLoader._xmlDocumentBuilder;
        _xmlDocumentValidator = formDefinitionsLoader._xmlDocumentValidator;
        _formXmlDefinitionBuilder = formDefinitionsLoader._formXmlDefinitionBuilder;
    }

    /**
     * Get the form definition from the specified input source.
     *
     * @param inputSource the specified input source.
     * @param source      the form's source.
     *
     * @return the form definition.
     */
    protected final FormDefinition getFormDefinition(final InputSource inputSource, final String source) {
        Document document = _xmlDocumentBuilder.parse(inputSource);
        Element element = document.getDocumentElement();
        if (_formXmlDefinitionBuilder.isFormDefinition(element)) {
            _xmlDocumentValidator.validate(document);
            return _formXmlDefinitionBuilder.createFormDefinition(element, source);
        } else {
            return null;
        }
    }

}
