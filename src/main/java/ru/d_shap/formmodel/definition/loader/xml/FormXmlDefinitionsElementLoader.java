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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Element loader for the form definitions, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsElementLoader extends FormXmlDefinitionsLoader {

    private final Element _element;

    private final String _source;

    /**
     * Create new object.
     *
     * @param element the source element.
     * @param source  the form's source.
     */
    public FormXmlDefinitionsElementLoader(final Element element, final String source) {
        super();
        _element = element;
        _source = source;
    }

    /**
     * Create new object.
     *
     * @param formXmlDefinitionsLoader loader for the form definitions.
     * @param element                  the source element.
     * @param source                   the form's source.
     */
    public FormXmlDefinitionsElementLoader(final FormXmlDefinitionsLoader formXmlDefinitionsLoader, final Element element, final String source) {
        super(formXmlDefinitionsLoader);
        _element = element;
        _source = source;
    }

    @Override
    public List<FormDefinition> load() {
        List<FormDefinition> formDefinitions = new ArrayList<>();
        if (getFormXmlDefinitionBuilder().isFormDefinition(_element)) {
            validate(_element);
            FormDefinition formDefinition = getFormXmlDefinitionBuilder().createFormDefinition(_element, _source);
            formDefinitions.add(formDefinition);
        }
        return formDefinitions;
    }

}
