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

import java.util.List;
import java.util.Map;

/**
 * Form model validator.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionValidator {

    private FormDefinitionValidator() {
        super();
    }

    static void validateFormReferences(final Map<String, FormDefinition> formDefinitions) {
        for (FormDefinition formDefinition : formDefinitions.values()) {
            List<NodeDefinition> nodeDefinitions = formDefinition.getChildNodeDefinitions();
            validateFormReferences(formDefinitions, formDefinition, nodeDefinitions);
        }
    }

    private static void validateFormReferences(final Map<String, FormDefinition> formDefinitions, final FormDefinition formDefinition, final List<NodeDefinition> nodeDefinitions) {
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                List<NodeDefinition> childNodeDefinitions = ((ElementDefinition) nodeDefinition).getChildNodeDefinitions();
                validateFormReferences(formDefinitions, formDefinition, childNodeDefinitions);
            }
            if (nodeDefinition instanceof FormReferenceDefinition) {
                String formId = ((FormReferenceDefinition) nodeDefinition).getReferencedFormId();
                if (formDefinitions.get(formId) == null) {
                    throw new FormDefinitionValidationException("Can not resolve form reference: " + formId, formDefinition.getSource());
                }
            }
        }
    }

}
