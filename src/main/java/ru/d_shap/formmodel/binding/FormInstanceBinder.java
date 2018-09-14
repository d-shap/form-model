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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.binding.api.Binder;
import ru.d_shap.formmodel.binding.api.BindingSource;
import ru.d_shap.formmodel.binding.api.FormModelInstanceBuilder;
import ru.d_shap.formmodel.binding.api.OtherNodeInstanceBuilder;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * The form instance binder.
 *
 * @author Dmitry Shapovalov
 */
final class FormInstanceBinder implements FormModelInstanceBuilder {

    private final FormDefinitions _formDefinitions;

    private final BindingSource _bindingSource;

    private final Binder _binder;

    private final List<OtherNodeInstanceBuilder> _otherNodeInstanceBuilders;

    private final Document _document;

    FormInstanceBinder(final FormDefinitions formDefinitions, final BindingSource bindingSource, final Binder binder, final List<OtherNodeInstanceBuilder> otherNodeInstanceBuilders) {
        super();
        _formDefinitions = formDefinitions;
        _bindingSource = bindingSource;
        _binder = binder;
        if (otherNodeInstanceBuilders == null) {
            _otherNodeInstanceBuilders = new ArrayList<>();
        } else {
            _otherNodeInstanceBuilders = new ArrayList<>(otherNodeInstanceBuilders);
        }
        _document = XmlDocumentBuilder.newDocument();
    }

    @Override
    public BindingSource getBindingSource() {
        return _bindingSource;
    }

    @Override
    public Document getDocument() {
        return _document;
    }

    @Override
    public void addFormInstance(final FormDefinition formDefinition, final NodePath nodePath) {
        _binder.bindFormDefinition(_bindingSource, formDefinition);

        Element element = _document.createElementNS(NAMESPACE, FORM_INSTANCE_ELEMENT_NAME);
        element.setAttribute(FORM_INSTANCE_ATTRIBUTE_GROUP, formDefinition.getGroup());
        element.setAttribute(FORM_INSTANCE_ATTRIBUTE_ID, formDefinition.getId());
        for (String otherAttributeName : formDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, formDefinition.getOtherAttributeValue(otherAttributeName));
        }
        _document.appendChild(element);

        NodePath currentNodePath = new NodePath(nodePath, formDefinition);
        for (ElementDefinition childElementDefinition : formDefinition.getElementDefinitions()) {
            addElementInstance(element, childElementDefinition, currentNodePath);
        }
        for (ChoiceDefinition childChoiceDefinition : formDefinition.getChoiceDefinitions()) {
            addChoiceInstance(element, childChoiceDefinition, currentNodePath);
        }
        for (FormReferenceDefinition childFormReferenceDefinition : formDefinition.getFormReferenceDefinitions()) {
            addFormReferenceInstance(element, childFormReferenceDefinition, currentNodePath);
        }
        for (OtherNodeDefinition childOtherNodeDefinition : formDefinition.getOtherNodeDefinitions()) {
            addOtherNodeInstance(element, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void addElementInstance(final Element parentElement, final ElementDefinition elementDefinition, final NodePath nodePath) {
        Element element = _document.createElementNS(NAMESPACE, ELEMENT_INSTANCE_ELEMENT_NAME);
        element.setAttribute(ELEMENT_INSTANCE_ATTRIBUTE_ID, elementDefinition.getId());
        for (String otherAttributeName : elementDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, elementDefinition.getOtherAttributeValue(otherAttributeName));
        }
        parentElement.appendChild(element);

        NodePath currentNodePath = new NodePath(nodePath, elementDefinition);
        for (AttributeDefinition childAttributeDefinition : elementDefinition.getAttributeDefinitions()) {
            addAttributeInstance(element, childAttributeDefinition, currentNodePath);
        }
        for (ElementDefinition childElementDefinition : elementDefinition.getElementDefinitions()) {
            addElementInstance(element, childElementDefinition, currentNodePath);
        }
        for (ChoiceDefinition childChoiceDefinition : elementDefinition.getChoiceDefinitions()) {
            addChoiceInstance(element, childChoiceDefinition, currentNodePath);
        }
        for (FormReferenceDefinition childFormReferenceDefinition : elementDefinition.getFormReferenceDefinitions()) {
            addFormReferenceInstance(element, childFormReferenceDefinition, currentNodePath);
        }
        for (OtherNodeDefinition childOtherNodeDefinition : elementDefinition.getOtherNodeDefinitions()) {
            addOtherNodeInstance(element, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void addChoiceInstance(final Element parentElement, final ChoiceDefinition choiceDefinition, final NodePath nodePath) {
        Element element = _document.createElementNS(NAMESPACE, CHOICE_INSTANCE_ELEMENT_NAME);
        element.setAttribute(CHOICE_INSTANCE_ATTRIBUTE_ID, choiceDefinition.getId());
        for (String otherAttributeName : choiceDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, choiceDefinition.getOtherAttributeValue(otherAttributeName));
        }
        parentElement.appendChild(element);

        NodePath currentNodePath = new NodePath(nodePath, choiceDefinition);
        for (ElementDefinition childElementDefinition : choiceDefinition.getElementDefinitions()) {
            addElementInstance(element, childElementDefinition, currentNodePath);
        }
        for (ChoiceDefinition childChoiceDefinition : choiceDefinition.getChoiceDefinitions()) {
            addChoiceInstance(element, childChoiceDefinition, currentNodePath);
        }
        for (OtherNodeDefinition childOtherNodeDefinition : choiceDefinition.getOtherNodeDefinitions()) {
            addOtherNodeInstance(element, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void addFormReferenceInstance(final Element parentElement, final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(formReferenceDefinition.getGroup(), formReferenceDefinition.getId());
        _binder.bindFormDefinition(_bindingSource, formDefinition);

        Element element = _document.createElementNS(NAMESPACE, FORM_REFERENCE_INSTANCE_ELEMENT_NAME);
        element.setAttribute(FORM_REFERENCE_INSTANCE_ATTRIBUTE_GROUP, formReferenceDefinition.getGroup());
        element.setAttribute(FORM_REFERENCE_INSTANCE_ATTRIBUTE_ID, formReferenceDefinition.getId());
        for (String otherAttributeName : formReferenceDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, formReferenceDefinition.getOtherAttributeValue(otherAttributeName));
        }
        parentElement.appendChild(element);

        NodePath currentNodePath = new NodePath(nodePath, formReferenceDefinition);
        for (OtherNodeDefinition childOtherNodeDefinition : formReferenceDefinition.getOtherNodeDefinitions()) {
            addOtherNodeInstance(element, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void addAttributeInstance(final Element parentElement, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
        Element element = _document.createElementNS(NAMESPACE, ATTRIBUTE_INSTANCE_ELEMENT_NAME);
        element.setAttribute(ATTRIBUTE_INSTANCE_ATTRIBUTE_ID, attributeDefinition.getId());
        for (String otherAttributeName : attributeDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, attributeDefinition.getOtherAttributeValue(otherAttributeName));
        }
        parentElement.appendChild(element);

        NodePath currentNodePath = new NodePath(nodePath, attributeDefinition);
        for (OtherNodeDefinition childOtherNodeDefinition : attributeDefinition.getOtherNodeDefinitions()) {
            addOtherNodeInstance(element, childOtherNodeDefinition, currentNodePath);
        }
    }

    private void addOtherNodeInstance(final Element parentElement, final OtherNodeDefinition otherNodeDefinition, final NodePath nodePath) {
        for (OtherNodeInstanceBuilder otherNodeInstanceBuilder : _otherNodeInstanceBuilders) {
            otherNodeInstanceBuilder.addOtherNodeInstance(parentElement, otherNodeDefinition, this, nodePath);
        }
    }

}
