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

import ru.d_shap.formmodel.definition.FormDefinition;

/**
 * Abstraction for the binded form.
 *
 * @param <E> generic type of the binded element.
 * @param <R> generic type of the binded form reference.
 * @param <B> generic type of the binding data.
 * @author Dmitry Shapovalov
 */
public class BindedForm<E extends BindedElement<E, R, B>, R extends BindedFormReference<E, R, B>, B> extends BindedNode<E, R, B> {

    private final FormDefinition _formDefinition;

    /**
     * Create new object.
     *
     * @param formDefinition the form definition.
     */
    protected BindedForm(final FormDefinition formDefinition) {
        super();
        _formDefinition = formDefinition;
    }

    /**
     * Get the form's ID.
     *
     * @return the form's ID.
     */
    public final String getId() {
        return _formDefinition.getId();
    }

    /**
     * Get the form definition.
     *
     * @return the form definition.
     */
    public final FormDefinition getFormDefinition() {
        return _formDefinition;
    }

}
