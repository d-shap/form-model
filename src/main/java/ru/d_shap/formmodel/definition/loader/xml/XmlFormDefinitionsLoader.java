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
import ru.d_shap.formmodel.definition.loader.FormDefinitionsLoader;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

/**
 * Base class for all form definitions loaders, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public class XmlFormDefinitionsLoader extends FormDefinitionsLoader {

    private final XmlFormDefinitionLoader _xmlFormDefinitionLoader;

    /**
     * Create new object.
     *
     * @param formDefinitions container for all form definitions.
     */
    protected XmlFormDefinitionsLoader(final FormDefinitions formDefinitions) {
        super(formDefinitions);
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        XmlDocumentBuilder xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder();
        _xmlFormDefinitionLoader = new XmlFormDefinitionLoader(xmlDocumentBuilder, otherNodeXmlDefinitionBuilders);
    }

    /**
     * Create new object.
     *
     * @param formDefinitions                container for all form definitions.
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     */
    protected XmlFormDefinitionsLoader(final FormDefinitions formDefinitions, final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator) {
        super(formDefinitions);
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        XmlDocumentBuilder xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder(xmlDocumentBuilderConfigurator);
        _xmlFormDefinitionLoader = new XmlFormDefinitionLoader(xmlDocumentBuilder, otherNodeXmlDefinitionBuilders);
    }

    /**
     * Load the form definition from the specified input source.
     *
     * @param inputSource the specified input source.
     * @param source      the form's source.
     *
     * @return the loaded form definition.
     */
    protected final FormDefinition loadFormDefinition(final InputSource inputSource, final String source) {
        return _xmlFormDefinitionLoader.load(inputSource, source);
    }

}
