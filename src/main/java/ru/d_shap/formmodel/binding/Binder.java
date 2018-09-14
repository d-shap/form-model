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

import org.w3c.dom.Document;

import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * The binder.
 *
 * @param <S> generic type of the source.
 * @param <B> generic type of the binding data.
 *
 * @author Dmitry Shapovalov
 */
public final class Binder<S, B> {

    private final FormDefinitions _formDefinitions;

    /**
     * Create new object.
     *
     * @param formDefinitions container for all form definitions.
     */
    public Binder(final FormDefinitions formDefinitions) {
        super();
        _formDefinitions = formDefinitions;
    }

    public Document bind(final S source, final String group, final String id) {
        FormInstanceBinder<S, B> formInstanceBinder = new FormInstanceBinder<>(source, null);
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(group, id);
        formInstanceBinder.addFormInstance(formDefinition, new NodePath());
        return formInstanceBinder.getDocument();
    }

}
