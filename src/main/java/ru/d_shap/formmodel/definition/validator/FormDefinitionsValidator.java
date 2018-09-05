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
package ru.d_shap.formmodel.definition.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * Validator for the form definitions.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsValidator {

    private final List<OtherNodeDefinitionValidator> _otherNodeDefinitionValidators;

    /**
     * Create new object.
     *
     * @param otherNodeDefinitionValidators validators for the other node definitions.
     */
    public FormDefinitionsValidator(final List<OtherNodeDefinitionValidator> otherNodeDefinitionValidators) {
        super();
        if (otherNodeDefinitionValidators == null) {
            _otherNodeDefinitionValidators = new ArrayList<>();
        } else {
            _otherNodeDefinitionValidators = new ArrayList<>(otherNodeDefinitionValidators);
        }
    }

    /**
     * Validate the specified form definitions.
     *
     * @param formSources     currently validated form definition sources.
     * @param formDefinitions the specified form definitions.
     */
    public void validate(final Map<FormDefinitionKey, String> formSources, final List<FormDefinition> formDefinitions) {
        NodePath currentNodePath = new NodePath();

        Set<FormDefinitionKey> allFormDefinitionKeys = new HashSet<>(formSources.keySet());
        for (FormDefinition formDefinition : formDefinitions) {
            FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formDefinition);
            if (formSources.containsKey(formDefinitionKey)) {
                String source1 = formSources.get(formDefinitionKey);
                String source2 = formDefinition.getSource();
                throw new FormDefinitionValidationException(Messages.Validation.getFormIsNotUniqueMessage(formDefinitionKey, source1, source2), currentNodePath);
            } else {
                allFormDefinitionKeys.add(formDefinitionKey);
            }
        }

        FormDefinitionValidator formDefinitionValidator = new FormDefinitionValidator(allFormDefinitionKeys, _otherNodeDefinitionValidators);
        for (FormDefinition formDefinition : formDefinitions) {
            formDefinitionValidator.validate(formDefinition, currentNodePath);
        }
    }

}
