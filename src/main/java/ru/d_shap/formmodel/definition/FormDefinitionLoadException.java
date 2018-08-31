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

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Exception is thrown when the form definition can not be loaded.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionLoadException extends FormDefinitionException {

    private static final long serialVersionUID = 1L;

    /**
     * Create new object.
     *
     * @param message exception message.
     */
    public FormDefinitionLoadException(final String message) {
        super(message);
    }

    /**
     * Create new object.
     *
     * @param message   exception message.
     * @param exception cause exception.
     */
    public FormDefinitionLoadException(final String message, final IOException exception) {
        super(message, exception);
    }

    /**
     * Create new object.
     *
     * @param message   exception message.
     * @param exception cause exception.
     */
    public FormDefinitionLoadException(final String message, final ParserConfigurationException exception) {
        super(message, exception);
    }

    /**
     * Create new object.
     *
     * @param message   exception message.
     * @param exception cause exception.
     */
    public FormDefinitionLoadException(final String message, final SAXException exception) {
        super(message, exception);
    }

}
