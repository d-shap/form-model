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

import ru.d_shap.formmodel.FormModelException;

/**
 * Exception is thrown if form model can not be loaded.
 *
 * @author Dmitry Shapovalov
 */
public final class FormModelLoadException extends FormModelException {

    private static final long serialVersionUID = 1L;

    /**
     * Create new object.
     *
     * @param exception cause exception.
     */
    FormModelLoadException(final ParserConfigurationException exception) {
        super("Parser configuration exception", exception);
    }

    /**
     * Create new object.
     *
     * @param exception cause exception.
     */
    FormModelLoadException(final IOException exception) {
        super("IO exception", exception);
    }

    /**
     * Create new object.
     *
     * @param exception cause exception.
     */
    FormModelLoadException(final SAXException exception) {
        super("XML parse exception", exception);
    }

}
