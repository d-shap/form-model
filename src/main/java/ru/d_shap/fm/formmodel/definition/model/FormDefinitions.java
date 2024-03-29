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
package ru.d_shap.fm.formmodel.definition.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.d_shap.fm.formmodel.definition.FormDefinitionNotFoundException;
import ru.d_shap.fm.formmodel.definition.validator.FormDefinitionsValidator;
import ru.d_shap.fm.formmodel.utils.NullValueHelper;

/**
 * Container for all form definitions.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitions {

    private final FormDefinitionsValidator _formDefinitionsValidator;

    private final Map<FormDefinitionKey, String> _formSources;

    private final Map<FormDefinitionKey, FormDefinition> _formDefinitions;

    /**
     * Create new object.
     */
    public FormDefinitions() {
        super();
        _formDefinitionsValidator = new FormDefinitionsValidator();
        _formSources = new HashMap<>();
        _formDefinitions = new HashMap<>();
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
     * Create the copy of the form definitions container.
     *
     * @return the copy of the form definitions container.
     */
    public FormDefinitions copyOf() {
        FormDefinitions formDefinitions = new FormDefinitions();
        formDefinitions._formSources.putAll(_formSources);
        formDefinitions._formDefinitions.putAll(_formDefinitions);
        return formDefinitions;
    }

    /**
     * Get the form definition for the specified form's ID.
     *
     * @param id the specified form's ID.
     *
     * @return the form definition.
     */
    public FormDefinition getFormDefinition(final String id) {
        FormDefinitionKey formDefinitionKey = new FormDefinitionKey(id);
        return getFormDefinition(formDefinitionKey);
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
        return getFormDefinition(formDefinitionKey);
    }

    /**
     * Get the form definition for the specified form reference definition.
     *
     * @param formReferenceDefinition the specified form reference definition.
     *
     * @return the form definition.
     */
    public FormDefinition getFormDefinition(final FormReferenceDefinition formReferenceDefinition) {
        FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formReferenceDefinition);
        return getFormDefinition(formDefinitionKey);
    }

    private FormDefinition getFormDefinition(final FormDefinitionKey formDefinitionKey) {
        FormDefinition formDefinition = _formDefinitions.get(formDefinitionKey);
        if (formDefinition == null) {
            throw new FormDefinitionNotFoundException(formDefinitionKey);
        } else {
            return formDefinition;
        }
    }

    /**
     * Get all form definition groups.
     *
     * @return all form definition groups.
     */
    public Set<String> getGroups() {
        Set<String> result = new HashSet<>();
        for (FormDefinition formDefinition : _formDefinitions.values()) {
            result.add(formDefinition.getGroup());
        }
        return result;
    }

    /**
     * Get all form definitions.
     *
     * @return all form definitions.
     */
    public List<FormDefinition> getFormDefinitions() {
        return new ArrayList<>(_formDefinitions.values());
    }

    /**
     * Get all form definitions for the specified form's group.
     *
     * @param group the specified form's group.
     *
     * @return all form definitions.
     */
    public List<FormDefinition> getFormDefinitions(final String group) {
        List<FormDefinition> result = new ArrayList<>();
        String validGroup = NullValueHelper.getValue(group);
        for (FormDefinition formDefinition : _formDefinitions.values()) {
            if (validGroup.equals(formDefinition.getGroup())) {
                result.add(formDefinition);
            }
        }
        return result;
    }

}
