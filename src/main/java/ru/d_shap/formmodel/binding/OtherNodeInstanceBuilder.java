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
import org.w3c.dom.Element;

import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * Builder for the other node instance.
 *
 * @author Dmitry Shapovalov
 */
public interface OtherNodeInstanceBuilder {

    /**
     * Check if this other node instance builder is compatible with the specified form instance binder.
     *
     * @param formInstanceBinder the specified form instance binder.
     *
     * @return true if this other node instance builder is compatible with the specified form instance binder.
     */
    boolean isCompatible(FormInstanceBinder formInstanceBinder);

    /**
     * Create the binded XML element for the specified other node definition and attach it to the owner document.
     *
     * @param bindingSource       the binding source.
     * @param document            the owner document.
     * @param lastBindedForm      the last binded form.
     * @param lastBindedElement   the last binded element.
     * @param parentElement       the parent XML element.
     * @param otherNodeDefinition the specified other node definition.
     * @param formInstanceBuilder builder for the form instance.
     * @param nodePath            the current node path.
     */
    void buildOtherNodeInstance(BindingSource bindingSource, Document document, BindedForm lastBindedForm, BindedElement lastBindedElement, Element parentElement, OtherNodeDefinition otherNodeDefinition, FormInstanceBuilder formInstanceBuilder, NodePath nodePath);

}
