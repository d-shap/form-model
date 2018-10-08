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
import java.util.List;

import org.w3c.dom.Element;

import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedAttributeImpl;
import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedElementImpl;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindedFormImpl;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.binding.model.BindingSourceImpl;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Implementation of the {@link FormInstanceBinder}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormInstanceBinderImpl implements FormInstanceBinder {

    /**
     * Create new object.
     */
    public FormInstanceBinderImpl() {
        super();
    }

    @Override
    public void preBind(final BindingSource bindingSource, final FormDefinition formDefinition) {
        // Ignore
    }

    @Override
    public BindedForm bindFormDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final FormDefinition formDefinition) {
        String representation = ((BindingSourceImpl) bindingSource).getRepresentation();
        if (representation == null) {
            return null;
        } else {
            return new BindedFormImpl(representation);
        }
    }

    @Override
    public List<BindedElement> bindElementDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final ElementDefinition elementDefinition) {
        String representation = elementDefinition.getOtherAttributeValue("repr");
        int count = Integer.parseInt(elementDefinition.getOtherAttributeValue("count"));
        if (representation == null || count < 0) {
            return null;
        }
        List<BindedElement> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            BindedElement bindedElement = new BindedElementImpl(representation, i);
            result.add(bindedElement);
        }
        return result;
    }

    @Override
    public BindedAttribute bindAttributeDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final AttributeDefinition attributeDefinition) {
        String representation = attributeDefinition.getOtherAttributeValue("repr");
        int count = Integer.parseInt(attributeDefinition.getOtherAttributeValue("count"));
        if (representation == null || count <= 0) {
            return null;
        }
        return new BindedAttributeImpl(representation);
    }

    @Override
    public void postBind(final BindingSource bindingSource, final FormDefinition formDefinition) {
        // Ignore
    }

}
