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

import java.util.List;

import org.w3c.dom.Element;

import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Form instance binder binds form model definition with the binding source.
 *
 * @author Dmitry Shapovalov
 */
public interface FormInstanceBinder {

    String BINDED_OBJECT_KEY = "__BINDED_OBJECT_KEY__";

    String ELEMENT_DEFINITION_KEY = "__ELEMENT_DEFINITION_KEY__";

    String FORM_DEFINITION_KEY = "__FORM_DEFINITION_KEY__";

    /**
     * Bind the form definition with the binding source.
     *
     * @param bindingSource     the binding source.
     * @param lastBindedForm    the last binded form.
     * @param lastBindedElement the last binded element.
     * @param parentElement     the parent XML element.
     * @param formDefinition    the form definition.
     *
     * @return the binded form.
     */
    BindedForm bindFormDefinition(BindingSource bindingSource, BindedForm lastBindedForm, BindedElement lastBindedElement, Element parentElement, FormDefinition formDefinition);

    /**
     * Bind the element definition with the binding source.
     *
     * @param bindingSource     the binding source.
     * @param lastBindedForm    the last binded form.
     * @param lastBindedElement the last binded element.
     * @param parentElement     the parent XML element.
     * @param elementDefinition the element definition.
     *
     * @return the binded elements.
     */
    List<BindedElement> bindElementDefinition(BindingSource bindingSource, BindedForm lastBindedForm, BindedElement lastBindedElement, Element parentElement, ElementDefinition elementDefinition);

    /**
     * Bind the attribute definition with the binding source.
     *
     * @param bindingSource       the binding source.
     * @param lastBindedForm      the last binded form.
     * @param lastBindedElement   the last binded element.
     * @param parentElement       the parent XML element.
     * @param attributeDefinition the attribute definition.
     *
     * @return the binded attribute.
     */
    BindedAttribute bindAttributeDefinition(BindingSource bindingSource, BindedForm lastBindedForm, BindedElement lastBindedElement, Element parentElement, AttributeDefinition attributeDefinition);

}
