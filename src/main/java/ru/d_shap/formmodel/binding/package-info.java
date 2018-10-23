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
/**
 * <p>
 * Form-model binding.
 * </p>
 * <p>
 * Classes provide the framework for binding the binding source with the form definition.
 * The form definition is represented with the {@link ru.d_shap.formmodel.definition.model.FormDefinition} class.
 * The binding extension defines the binding source, the binding result and the binding process itself.
 * The binding source is represented with the {@link ru.d_shap.formmodel.binding.model.BindingSource} instance.
 * The binding result is represented with the {@link ru.d_shap.formmodel.binding.model.BindedForm},
 * the {@link ru.d_shap.formmodel.binding.model.BindedElement} and the {@link ru.d_shap.formmodel.binding.model.BindedAttribute}
 * instances.
 * The binding extension should also provide an instance of the {@link ru.d_shap.formmodel.binding.FormInstanceBinder}
 * class. This instance defines the binding process, how to match the binding source with the definition]
 * and provide the binding result.
 * </p>
 */
package ru.d_shap.formmodel.binding;
