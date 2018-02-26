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
package ru.d_shap.formmodel.binding;

import java.util.List;

import ru.d_shap.formmodel.FormModelException;
import ru.d_shap.formmodel.definition.ElementDefinition;
import ru.d_shap.formmodel.definition.FormDefinition;
import ru.d_shap.formmodel.definition.NodeDefinition;

/**
 * Exception is thrown when the actual form can not be binded to the form definition.
 *
 * @author Dmitry Shapovalov
 */
public final class BindingException extends FormModelException {

    private static final long serialVersionUID = 1L;

    BindingException(final BindingExceptionType bindingErrorType, final List<NodeDefinition> nodeDefinitions) {
        super(createErrorMessage(bindingErrorType, nodeDefinitions));
    }

    private static String createErrorMessage(final BindingExceptionType bindingErrorType, final List<NodeDefinition> nodeDefinitions) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(bindingErrorType).append(": ");
        boolean first = true;
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (first) {
                first = false;
            } else {
                errorMessage.append(".");
            }

            if (nodeDefinition instanceof ElementDefinition) {
                errorMessage.append("[E id=").append(((ElementDefinition) nodeDefinition).getId()).append("]");
            }
            if (nodeDefinition instanceof FormDefinition) {
                errorMessage.append("[F id=").append(((FormDefinition) nodeDefinition).getId()).append("]");
            }
        }
        return errorMessage.toString();
    }

}
