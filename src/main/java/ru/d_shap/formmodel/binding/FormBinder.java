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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.w3c.dom.Document;

import ru.d_shap.formmodel.binding.api.BindingSource;
import ru.d_shap.formmodel.binding.api.OtherNodeInstanceBuilder;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * The form binder.
 *
 * @author Dmitry Shapovalov
 */
public final class FormBinder {

    private static final ServiceLoader<OtherNodeInstanceBuilder> SERVICE_LOADER = ServiceLoader.load(OtherNodeInstanceBuilder.class);

    private final FormDefinitions _formDefinitions;

    private final List<OtherNodeInstanceBuilder> _otherNodeInstanceBuilders;

    /**
     * Create new object.
     *
     * @param formDefinitions container for all form definitions.
     */
    public FormBinder(final FormDefinitions formDefinitions) {
        super();
        _formDefinitions = formDefinitions;
        _otherNodeInstanceBuilders = new ArrayList<>();
        Iterator<OtherNodeInstanceBuilder> iterator = SERVICE_LOADER.iterator();
        while (iterator.hasNext()) {
            OtherNodeInstanceBuilder otherNodeInstanceBuilder = iterator.next();
            _otherNodeInstanceBuilders.add(otherNodeInstanceBuilder);
        }
    }

    public Document bind(final BindingSource bindingSource, final String group, final String id) {
        FormInstanceBinder formInstanceBinder = new FormInstanceBinder(bindingSource, _otherNodeInstanceBuilders);
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(group, id);
        formInstanceBinder.addFormInstance(formDefinition, new NodePath());
        return formInstanceBinder.getDocument();
    }

}
