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

import ru.d_shap.formmodel.binding.api.Binder;
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

    /**
     * Bind the specified form definition with the specified binding source.
     *
     * @param bindingSource the specified binding source.
     * @param binder        the binder.
     * @param id            the form's ID.
     *
     * @return the binding result.
     */
    public Document bind(final BindingSource bindingSource, final Binder binder, final String id) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(id);
        return bind(bindingSource, binder, formDefinition);
    }

    /**
     * Bind the specified form definition with the specified binding source.
     *
     * @param bindingSource the specified binding source.
     * @param binder        the binder.
     * @param group         the form's group.
     * @param id            the form's ID.
     *
     * @return the binding result.
     */
    public Document bind(final BindingSource bindingSource, final Binder binder, final String group, final String id) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(group, id);
        return bind(bindingSource, binder, formDefinition);
    }

    public Document bind(final BindingSource bindingSource, final Binder binder, final FormDefinition formDefinition) {
        FormInstanceBinder formInstanceBinder = new FormInstanceBinder(_formDefinitions, bindingSource, binder, _otherNodeInstanceBuilders);
        formInstanceBinder.addFormInstance(formDefinition, new NodePath());
        return formInstanceBinder.getDocument();
    }

}
