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
package ru.d_shap.formmodel.definition.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.d_shap.formmodel.definition.FormDefinitionNotFoundException;
import ru.d_shap.formmodel.definition.validator.FormDefinitionsValidator;

/**
 * Container for all form definitions.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitions {

    private final Map<FormDefinitionKey, String> _formSources;

    private final Map<FormDefinitionKey, FormDefinition> _formDefinitions;

    private final FormDefinitionsValidator _formDefinitionsValidator;

    /**
     * Create new object.
     */
    public FormDefinitions() {
        super();
        _formSources = new HashMap<>();
        _formDefinitions = new HashMap<>();
        _formDefinitionsValidator = new FormDefinitionsValidator();
    }

    /**
     * Add the specified form definitions to this container.
     *
     * @param formDefinitions the specified form definitions.
     */
    public void addFormDefinitions(final List<FormDefinition> formDefinitions) {
        _formDefinitionsValidator.validate(_formSources, formDefinitions);
        for (FormDefinition formDefinition : formDefinitions) {
            FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formDefinition);
            _formSources.put(formDefinitionKey, formDefinition.getSource());
            _formDefinitions.put(formDefinitionKey, formDefinition);
        }
    }

    /**
     * Get the form definition for the specified form's ID.
     *
     * @param id the specified form's ID.
     *
     * @return the form definition.
     */
    public FormDefinition getFormDefinition(final String id) {
        return getFormDefinition(null, id);
    }

    /**
     * Get the form definition for the specified form's group and form's ID.
     *
     * @param group the specified form's group.
     * @param id    the specified form's ID.
     *
     * @return the form definition.
     */
    public FormDefinition getFormDefinition(final String group, final String id) {
        FormDefinitionKey formDefinitionKey = new FormDefinitionKey(group, id);
        FormDefinition formDefinition = _formDefinitions.get(formDefinitionKey);
        if (formDefinition == null) {
            throw new FormDefinitionNotFoundException(group, id);
        } else {
            return formDefinition;
        }
    }

}
