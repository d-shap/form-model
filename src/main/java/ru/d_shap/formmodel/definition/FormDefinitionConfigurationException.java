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

/**
 * Exception is thrown when the form model configuration is not valid.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionConfigurationException extends FormDefinitionException {

    private static final long serialVersionUID = 1L;

    /**
     * Create new object.
     *
     * @param message   exception message.
     * @param throwable cause exception.
     */
    public FormDefinitionConfigurationException(final String message, final Throwable throwable) {
        super(message);
    }

}