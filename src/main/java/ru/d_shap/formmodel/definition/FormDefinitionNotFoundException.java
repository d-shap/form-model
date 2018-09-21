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

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;

/**
 * Exception is thrown when the form definition is not found.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionNotFoundException extends FormDefinitionException {

    private static final long serialVersionUID = 1L;

    /**
     * Create new object.
     *
     * @param formDefinitionKey the form definition key.
     */
    public FormDefinitionNotFoundException(final FormDefinitionKey formDefinitionKey) {
        super("[Form definition was not found: " + formDefinitionKey + "]");
    }

    /**
     * Create new object.
     *
     * @param group the form's group.
     * @param id    the form's ID.
     */
    public FormDefinitionNotFoundException(final String group, final String id) {
        super("[Form definition was not found: " + Messages.Representation.getIdRepresentation(group, id) + "]");
    }

}
