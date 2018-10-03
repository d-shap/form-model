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

import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.XmlDocumentBuilderConfigurator;
import ru.d_shap.formmodel.XmlDocumentValidator;
import ru.d_shap.formmodel.definition.loader.FormDefinitionsLoader;

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
        super();
        _xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder(null);
        _xmlDocumentValidator = XmlDocumentValidator.getFormModelDocumentValidator();
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        _formXmlDefinitionBuilder = new FormXmlDefinitionBuilderImpl(otherNodeXmlDefinitionBuilders);
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
     * @param formXmlDefinitionsLoader loader for the form definitions.
     */
    protected FormXmlDefinitionsLoader(final FormXmlDefinitionsLoader formXmlDefinitionsLoader) {
        super();
        _xmlDocumentBuilder = formXmlDefinitionsLoader._xmlDocumentBuilder;
        _xmlDocumentValidator = formXmlDefinitionsLoader._xmlDocumentValidator;
        _formXmlDefinitionBuilder = formXmlDefinitionsLoader._formXmlDefinitionBuilder;
    }

    /**
     * Get the XML document builder.
     *
     * @return the XML document builder.
     */
    protected XmlDocumentBuilder getXmlDocumentBuilder() {
        return _xmlDocumentBuilder;
    }

    /**
     * Get the XML document validator.
     *
     * @return the XML document validator.
     */
    protected XmlDocumentValidator getXmlDocumentValidator() {
        return _xmlDocumentValidator;
    }

    /**
     * Get the builder for the form definition.
     *
     * @return the builder for the form definition.
     */
    protected FormXmlDefinitionBuilderImpl getFormXmlDefinitionBuilder() {
        return _formXmlDefinitionBuilder;
    }

}
