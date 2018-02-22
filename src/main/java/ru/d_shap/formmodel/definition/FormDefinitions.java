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
package ru.d_shap.formmodel.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Abstraction for the form definitions.
 * </p>
 * <p>
 * This is a set of form definition objects.
 * </p>
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitions {

    private final Map<String, FormDefinition> _formIdToFormDefinitionMap;

    private final List<FormDefinition> _formDefinitions;

    FormDefinitions(final Map<String, FormDefinition> formDefinitions) {
        super();
        Map<String, FormDefinition> formIdToFormDefinitionMapCopy = new HashMap<>(formDefinitions);
        _formIdToFormDefinitionMap = Collections.unmodifiableMap(formIdToFormDefinitionMapCopy);
        List<FormDefinition> formDefinitionsCopy = new ArrayList<>(formDefinitions.values());
        _formDefinitions = Collections.unmodifiableList(formDefinitionsCopy);
    }

    /**
     * Get the form definition for the specified form ID.
     *
     * @param formId the specified form ID.
     * @return the form definition.
     */
    public FormDefinition getFormDefinition(final String formId) {
        FormDefinition formDefinition = _formIdToFormDefinitionMap.get(formId);
        if (formDefinition == null) {
            throw new UndefinedFormDefinitionException(formId);
        } else {
            return formDefinition;
        }
    }

    List<FormDefinition> getFormDefinitions() {
        return _formDefinitions;
    }

}
