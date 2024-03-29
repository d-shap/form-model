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
package ru.d_shap.fm.formmodel.binding;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.fm.formmodel.binding.model.BindedAttribute;
import ru.d_shap.fm.formmodel.binding.model.BindedAttributeImpl;
import ru.d_shap.fm.formmodel.binding.model.BindedElement;
import ru.d_shap.fm.formmodel.binding.model.BindedElementImpl;
import ru.d_shap.fm.formmodel.binding.model.BindedForm;
import ru.d_shap.fm.formmodel.binding.model.BindedFormImpl;
import ru.d_shap.fm.formmodel.binding.model.BindingSource;
import ru.d_shap.fm.formmodel.binding.model.BindingSourceImpl;
import ru.d_shap.fm.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.fm.formmodel.definition.model.ElementDefinition;
import ru.d_shap.fm.formmodel.definition.model.FormDefinition;

/**
 * Implementation of the {@link FormInstanceBinder}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormInstanceBinderImpl implements FormInstanceBinder {

    private static final String ATTRIBUTE_REPR = "repr";

    private static final String ATTRIBUTE_COUNT = "count";

    private BindingSource _bindingSource;

    private FormDefinition _formDefinition;

    private Document _document;

    /**
     * Create new object.
     */
    public FormInstanceBinderImpl() {
        super();
    }

    /**
     * Get the binding source.
     *
     * @return the binding source.
     */
    public BindingSource getBindingSource() {
        return _bindingSource;
    }

    /**
     * Get the form definition.
     *
     * @return the form definition.
     */
    public FormDefinition getFormDefinition() {
        return _formDefinition;
    }

    /**
     * Get the binded document.
     *
     * @return the binded document.
     */
    public Document getDocument() {
        return _document;
    }

    @Override
    public void preBind(final BindingSource bindingSource, final FormDefinition formDefinition) {
        Assertions.assertThat(_bindingSource).isNull();
        Assertions.assertThat(bindingSource).isNotNull();
        _bindingSource = bindingSource;

        Assertions.assertThat(_formDefinition).isNull();
        Assertions.assertThat(formDefinition).isNotNull();
        _formDefinition = formDefinition;
    }

    @Override
    public void postBind(final BindingSource bindingSource, final FormDefinition formDefinition, final Document document) {
        Assertions.assertThat(_bindingSource).isNotNull();
        Assertions.assertThat(bindingSource).isNotNull();
        Assertions.assertThat(_bindingSource).isSameAs(bindingSource);
        _bindingSource = null;

        Assertions.assertThat(_formDefinition).isNotNull();
        Assertions.assertThat(formDefinition).isNotNull();
        Assertions.assertThat(_formDefinition).isSameAs(formDefinition);
        _formDefinition = null;

        _document = document;
    }

    @Override
    public BindedForm bindFormDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final FormDefinition formDefinition) {
        String representation = formDefinition.getOtherAttributeValue(ATTRIBUTE_REPR);
        if (representation == null) {
            representation = ((BindingSourceImpl) bindingSource).getRepresentation();
        }
        if (representation == null) {
            return null;
        } else {
            return new BindedFormImpl(representation);
        }
    }

    @Override
    public List<BindedElement> bindElementDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final ElementDefinition elementDefinition) {
        String representation = elementDefinition.getOtherAttributeValue(ATTRIBUTE_REPR);
        String countStr = elementDefinition.getOtherAttributeValue(ATTRIBUTE_COUNT);

        if (representation == null && countStr == null) {
            BindedElement bindedElement = new BindedElementImpl(representation, 0);
            List<BindedElement> result = new ArrayList<>();
            result.add(bindedElement);
            return result;
        }

        int count;
        if (countStr == null) {
            count = -1;
        } else {
            count = Integer.parseInt(countStr);
        }
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
        String representation = attributeDefinition.getOtherAttributeValue(ATTRIBUTE_REPR);
        String countStr = attributeDefinition.getOtherAttributeValue(ATTRIBUTE_COUNT);

        if (representation == null && countStr == null) {
            return new BindedAttributeImpl(representation);
        }

        int count;
        if (countStr == null) {
            count = -1;
        } else {
            count = Integer.parseInt(countStr);
        }
        if (representation == null || count <= 0) {
            return null;
        }

        return new BindedAttributeImpl(representation);
    }

}
