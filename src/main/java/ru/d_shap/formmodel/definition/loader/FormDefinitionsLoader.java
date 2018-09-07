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
package ru.d_shap.formmodel.definition.loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.xml.sax.InputSource;

import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

/**
 * Base class for all form definitions loaders.
 *
 * @author Dmitry Shapovalov
 */
public class FormDefinitionsLoader {

    private static final ServiceLoader<OtherNodeDefinitionBuilder> SERVICE_LOADER = ServiceLoader.load(OtherNodeDefinitionBuilder.class);

    private final FormDefinitions _formDefinitions;

    private final FormDefinitionLoader _formDefinitionLoader;

    /**
     * Create new object.
     *
     * @param formDefinitions container for all form definitions.
     */
    protected FormDefinitionsLoader(final FormDefinitions formDefinitions) {
        super();
        _formDefinitions = formDefinitions;
        List<OtherNodeDefinitionBuilder> otherNodeDefinitionBuilders = new ArrayList<>();
        for (Iterator<OtherNodeDefinitionBuilder> iterator = SERVICE_LOADER.iterator(); iterator.hasNext(); ) {
            OtherNodeDefinitionBuilder otherNodeDefinitionBuilder = iterator.next();
            otherNodeDefinitionBuilders.add(otherNodeDefinitionBuilder);
        }
        _formDefinitionLoader = new FormDefinitionLoader(otherNodeDefinitionBuilders);
    }

    /**
     * Add the specified form definitions to the container for all form definitions.
     *
     * @param formDefinitions the specified form definitions.
     */
    protected final void addFormDefinitions(final List<FormDefinition> formDefinitions) {
        List<FormDefinition> list = new ArrayList<>();
        for (FormDefinition formDefinition : formDefinitions) {
            if (formDefinition != null) {
                list.add(formDefinition);
            }
        }
        _formDefinitions.addFormDefinitions(list);
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
        return _formDefinitionLoader.load(inputSource, source);
    }

}
