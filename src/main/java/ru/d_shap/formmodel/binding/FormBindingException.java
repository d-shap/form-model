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

import ru.d_shap.formmodel.FormModelException;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * Exception is thrown when the binding source can not be binded with the form definition.
 *
 * @author Dmitry Shapovalov
 */
public final class FormBindingException extends FormModelException {

    private static final long serialVersionUID = 1L;

    /**
     * Create new object.
     *
     * @param message exception message.
     */
    public FormBindingException(final String message) {
        this(message, null);
    }

    /**
     * Create new object.
     *
     * @param message  exception message.
     * @param nodePath node path.
     */
    public FormBindingException(final String message, final NodePath nodePath) {
        super(getMessage(message, nodePath));
    }

    /**
     * Create new object.
     *
     * @param throwable cause exception.
     */
    public FormBindingException(final Throwable throwable) {
        super(throwable);
    }

    private static String getMessage(final String message, final NodePath nodePath) {
        if (nodePath == null) {
            return String.valueOf(message);
        } else {
            return message + ", " + nodePath;
        }
    }

}
