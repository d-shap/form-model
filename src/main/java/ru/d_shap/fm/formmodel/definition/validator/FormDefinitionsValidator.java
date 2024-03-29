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
package ru.d_shap.fm.formmodel.definition.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.d_shap.fm.formmodel.Messages;
import ru.d_shap.fm.formmodel.ServiceFinder;
import ru.d_shap.fm.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.fm.formmodel.definition.model.FormDefinition;
import ru.d_shap.fm.formmodel.definition.model.FormDefinitionKey;

/**
 * Validator for the form definitions.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsValidator {

    private final List<OtherNodeDefinitionValidator> _otherNodeDefinitionValidators;

    /**
     * Create new object.
     */
    public FormDefinitionsValidator() {
        super();
        _otherNodeDefinitionValidators = ServiceFinder.find(OtherNodeDefinitionValidator.class);
    }

    /**
     * Validate the specified form definitions.
     *
     * @param formSources     currently validated form definition sources.
     * @param formDefinitions the specified form definitions.
     */
    public void validate(final Map<FormDefinitionKey, String> formSources, final List<FormDefinition> formDefinitions) {
        Map<FormDefinitionKey, String> allFormSources = new HashMap<>(formSources);
        for (FormDefinition formDefinition : formDefinitions) {
            FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formDefinition);
            if (allFormSources.containsKey(formDefinitionKey)) {
                String source1 = allFormSources.get(formDefinitionKey);
                String source2 = formDefinition.getSource();
                throw new FormDefinitionValidationException(Messages.Validation.getFormDefinitionIsNotUniqueMessage(formDefinitionKey, source1, source2));
            } else {
                allFormSources.put(formDefinitionKey, formDefinition.getSource());
            }
        }

        FormDefinitionValidatorImpl formDefinitionValidator = new FormDefinitionValidatorImpl(allFormSources.keySet(), _otherNodeDefinitionValidators);
        for (FormDefinition formDefinition : formDefinitions) {
            formDefinitionValidator.validateFormDefinition(formDefinition);
        }
    }

}
