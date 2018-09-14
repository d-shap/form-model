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
package ru.d_shap.formmodel.binding.api;

import java.util.List;

import org.w3c.dom.Element;

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Binder bind form model definition elements with the binding source.
 *
 * @author Dmitry Shapovalov
 */
public interface Binder {

    /**
     * Bind the form definition with the binding source.
     *
     * @param bindingSource  the binding source.
     * @param formDefinition the form definition.
     */
    void bindFormDefinition(BindingSource bindingSource, FormDefinition formDefinition);

    /**
     * Bind the element definition with the binding source.
     *
     * @param bindingSource     the binding source.
     * @param element           the parent binded element.
     * @param elementDefinition the element definition.
     *
     * @return the binding result.
     */
    List<BindingResult> bindElementDefinition(BindingSource bindingSource, Element element, ElementDefinition elementDefinition);

    /**
     * Bind the attribute definition with the binding source.
     *
     * @param bindingSource       the binding source.
     * @param element             the parent binded element.
     * @param attributeDefinition the attribute definition.
     *
     * @return the binding result.
     */
    List<BindingResult> bindAttributeDefinition(BindingSource bindingSource, Element element, AttributeDefinition attributeDefinition);

}
