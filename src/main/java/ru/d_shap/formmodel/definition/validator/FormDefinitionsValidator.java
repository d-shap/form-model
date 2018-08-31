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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;

/**
 * Form definitions validator validates form definitions.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsValidator {

    private FormDefinitionsValidator() {
        super();
    }

    /**
     * Validate the specified form definitions.
     *
     * @param formSources     currently validated form definition sources.
     * @param formDefinitions the specified form definitions.
     */
    public static void validate(final Map<FormDefinitionKey, String> formSources, final List<FormDefinition> formDefinitions) {
        for (FormDefinition formDefinition : formDefinitions) {
            FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formDefinition);
            if (formSources.containsKey(formDefinitionKey)) {
                throw new FormDefinitionValidationException(Collections.singletonList("Duplicate form"));
            }
        }

        Set<FormDefinitionKey> allFormDefinitionKeys = new HashSet<>(formSources.keySet());
        for (FormDefinition formDefinition : formDefinitions) {
            FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formDefinition);
            allFormDefinitionKeys.add(formDefinitionKey);
        }

        List<FormDefinitionKey> references = new ArrayList<>();
        for (FormDefinition formDefinition : formDefinitions) {
            for (FormReferenceDefinition formReferenceDefinition : formDefinition.getFormReferenceDefinitions()) {
                FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formReferenceDefinition);
                references.add(formDefinitionKey);
            }
            for (ElementDefinition elementDefinition : formDefinition.getElementDefinitions()) {
                addFormReferences(elementDefinition, references);
            }
        }

        for (FormDefinitionKey formDefinitionKey : references) {
            if (!allFormDefinitionKeys.contains(formDefinitionKey)) {
                throw new FormDefinitionValidationException(Collections.singletonList("unresolved reference"));
            }
        }
    }

    private static void addFormReferences(final ElementDefinition elementDefinition, final List<FormDefinitionKey> references) {
        for (FormReferenceDefinition formReferenceDefinition : elementDefinition.getFormReferenceDefinitions()) {
            FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formReferenceDefinition);
            references.add(formDefinitionKey);
        }
        for (ElementDefinition childElementDefinition : elementDefinition.getElementDefinitions()) {
            addFormReferences(childElementDefinition, references);
        }
    }

}
