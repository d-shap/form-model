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
 * Form-model binding model.
 * </p>
 * <p>
 * The {@link ru.d_shap.fm.formmodel.binding.FormInstanceBinder} binds the
 * {@link ru.d_shap.fm.formmodel.binding.model.BindingSource} instance with the
 * {@link ru.d_shap.fm.formmodel.definition.model.FormDefinition} instance.
 * The {@link ru.d_shap.fm.formmodel.binding.FormInstanceBinder} creates the instances of the
 * {@link ru.d_shap.fm.formmodel.binding.model.BindedForm}, the {@link ru.d_shap.fm.formmodel.binding.model.BindedElement}
 * and the {@link ru.d_shap.fm.formmodel.binding.model.BindedAttribute}. This instances are
 * attached as the user data to the elements of the result XML DOM Document.
 * </p>
 */
package ru.d_shap.fm.formmodel.binding.model;
