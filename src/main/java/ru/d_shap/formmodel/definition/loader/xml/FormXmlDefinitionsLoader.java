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

    private final FormXmlDefinitionBuilderImpl _formXmlDefinitionBuilder;

    /**
     * Create new object.
     */
    protected FormXmlDefinitionsLoader() {
        super();
        XmlDocumentBuilder xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder();
        XmlDocumentValidator xmlDocumentValidator = XmlDocumentValidator.getFormModelDocumentValidator();
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        _formXmlDefinitionBuilder = new FormXmlDefinitionBuilderImpl(xmlDocumentBuilder, xmlDocumentValidator, otherNodeXmlDefinitionBuilders);
    }

    /**
     * Create new object.
     *
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     */
    protected FormXmlDefinitionsLoader(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator) {
        super();
        XmlDocumentBuilder xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder(xmlDocumentBuilderConfigurator);
        XmlDocumentValidator xmlDocumentValidator = XmlDocumentValidator.getFormModelDocumentValidator();
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        _formXmlDefinitionBuilder = new FormXmlDefinitionBuilderImpl(xmlDocumentBuilder, xmlDocumentValidator, otherNodeXmlDefinitionBuilders);
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
        return _formXmlDefinitionBuilder.getFormDefinition(inputSource, source);
    }

}
