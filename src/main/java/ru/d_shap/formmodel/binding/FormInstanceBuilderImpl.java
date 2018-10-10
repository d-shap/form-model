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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;

/**
 * Builder for the form instance.
 *
 * @author Dmitry Shapovalov
 */
final class FormInstanceBuilderImpl implements FormInstanceBuilder {

    private final FormDefinitions _formDefinitions;

    private final FormInstanceBinder _formInstanceBinder;

    private final List<OtherNodeInstanceBuilder> _otherNodeInstanceBuilders;

    FormInstanceBuilderImpl(final FormDefinitions formDefinitions, final FormInstanceBinder formInstanceBinder, final List<OtherNodeInstanceBuilder> otherNodeInstanceBuilders) {
        super();
        _formDefinitions = formDefinitions;
        _formInstanceBinder = formInstanceBinder;
        _otherNodeInstanceBuilders = new ArrayList<>(otherNodeInstanceBuilders);
    }

    void buildFormInstance(final BindingSource bindingSource, final Document document, final FormDefinition formDefinition) {
        BindedForm bindedForm = _formInstanceBinder.bindFormDefinition(bindingSource, null, null, null, formDefinition);
        validateBindedForm(bindedForm, formDefinition);
        Element element = createFormInstanceElement(document, formDefinition, bindedForm);
        document.appendChild(element);
        NodePath currentNodePath = new NodePath(formDefinition);
        bindNodeDefinitions(bindingSource, document, bindedForm, null, element, formDefinition.getAllNodeDefinitions(), currentNodePath);
    }

    private Element createFormInstanceElement(final Document document, final FormDefinition formDefinition, final BindedForm bindedForm) {
        Element element = document.createElementNS(NAMESPACE, FORM_INSTANCE_ELEMENT_NAME);
        element.setAttribute(FORM_INSTANCE_ATTRIBUTE_GROUP, formDefinition.getGroup());
        element.setAttribute(FORM_INSTANCE_ATTRIBUTE_ID, formDefinition.getId());
        for (String otherAttributeName : formDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, formDefinition.getOtherAttributeValue(otherAttributeName));
        }
        element.setUserData(USER_DATA_FORM_DEFINITION, formDefinition, null);
        element.setUserData(USER_DATA_NODE_DEFINITION, formDefinition, null);
        element.setUserData(USER_DATA_BINDED_OBJECT, bindedForm, null);
        return element;
    }

    private void validateBindedForm(final BindedForm bindedForm, final FormDefinition formDefinition) {
        if (bindedForm == null) {
            throw new FormBindingException(Messages.Binding.getFormIsNotPresentMessage(formDefinition));
        }
    }

    @Override
    public void buildAttributeInstance(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
        BindedAttribute bindedAttribute = _formInstanceBinder.bindAttributeDefinition(bindingSource, lastBindedForm, lastBindedElement, parentElement, attributeDefinition);
        validateBindedAttribute(bindedAttribute, attributeDefinition, nodePath);
        if (bindedAttribute != null) {
            Element element = createAttributeInstanceElement(document, (FormDefinition) parentElement.getUserData(USER_DATA_FORM_DEFINITION), attributeDefinition, bindedAttribute);
            parentElement.appendChild(element);
            NodePath currentNodePath = new NodePath(nodePath, attributeDefinition);
            bindNodeDefinitions(bindingSource, document, lastBindedForm, lastBindedElement, element, attributeDefinition.getAllNodeDefinitions(), currentNodePath);
        }
    }

    private Element createAttributeInstanceElement(final Document document, final FormDefinition formDefinition, final AttributeDefinition attributeDefinition, final BindedAttribute bindedAttribute) {
        Element element = document.createElementNS(NAMESPACE, ATTRIBUTE_INSTANCE_ELEMENT_NAME);
        element.setAttribute(ATTRIBUTE_INSTANCE_ATTRIBUTE_ID, attributeDefinition.getId());
        for (String otherAttributeName : attributeDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, attributeDefinition.getOtherAttributeValue(otherAttributeName));
        }
        element.setUserData(USER_DATA_FORM_DEFINITION, formDefinition, null);
        element.setUserData(USER_DATA_NODE_DEFINITION, attributeDefinition, null);
        element.setUserData(USER_DATA_BINDED_OBJECT, bindedAttribute, null);
        return element;
    }

    private void validateBindedAttribute(final BindedAttribute bindedAttribute, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
        if (attributeDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED && bindedAttribute == null) {
            throw new FormBindingException(Messages.Binding.getRequiredAttributeIsNotPresentMessage(attributeDefinition), nodePath);
        }
        if (attributeDefinition.getCardinalityDefinition() == CardinalityDefinition.PROHIBITED && bindedAttribute != null) {
            throw new FormBindingException(Messages.Binding.getProhibitedAttributeIsPresentMessage(attributeDefinition), nodePath);
        }
    }

    @Override
    public void buildElementInstance(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final ElementDefinition elementDefinition, final NodePath nodePath) {
        List<BindedElement> bindedElements = _formInstanceBinder.bindElementDefinition(bindingSource, lastBindedForm, lastBindedElement, parentElement, elementDefinition);
        validateBindedElement(bindedElements, elementDefinition, nodePath);
        if (bindedElements != null) {
            for (BindedElement bindedElement : bindedElements) {
                Element element = createElementInstanceElement(document, (FormDefinition) parentElement.getUserData(USER_DATA_FORM_DEFINITION), elementDefinition, bindedElement);
                parentElement.appendChild(element);
                NodePath currentNodePath = new NodePath(nodePath, elementDefinition);
                bindNodeDefinitions(bindingSource, document, lastBindedForm, bindedElement, element, elementDefinition.getAllNodeDefinitions(), currentNodePath);
            }
        }
    }

    private Element createElementInstanceElement(final Document document, final FormDefinition formDefinition, final ElementDefinition elementDefinition, final BindedElement bindedElement) {
        Element element = document.createElementNS(NAMESPACE, ELEMENT_INSTANCE_ELEMENT_NAME);
        element.setAttribute(ELEMENT_INSTANCE_ATTRIBUTE_ID, elementDefinition.getId());
        for (String otherAttributeName : elementDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, elementDefinition.getOtherAttributeValue(otherAttributeName));
        }
        element.setUserData(USER_DATA_FORM_DEFINITION, formDefinition, null);
        element.setUserData(USER_DATA_NODE_DEFINITION, elementDefinition, null);
        element.setUserData(USER_DATA_BINDED_OBJECT, bindedElement, null);
        return element;
    }

    private void validateBindedElement(final List<BindedElement> bindedElements, final ElementDefinition elementDefinition, final NodePath nodePath) {
        if (elementDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED && bindedElements == null) {
            throw new FormBindingException(Messages.Binding.getRequiredElementIsNotPresentMessage(elementDefinition), nodePath);
        }
        if (elementDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED && bindedElements.isEmpty()) {
            throw new FormBindingException(Messages.Binding.getRequiredElementIsNotPresentMessage(elementDefinition), nodePath);
        }
        if (elementDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED && bindedElements.size() > 1) {
            throw new FormBindingException(Messages.Binding.getRequiredElementIsPresentMoreThanOnceMessage(elementDefinition), nodePath);
        }
        if (elementDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED_MULTIPLE && bindedElements == null) {
            throw new FormBindingException(Messages.Binding.getRequiredElementIsNotPresentMessage(elementDefinition), nodePath);
        }
        if (elementDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED_MULTIPLE && bindedElements.isEmpty()) {
            throw new FormBindingException(Messages.Binding.getRequiredElementIsNotPresentMessage(elementDefinition), nodePath);
        }
        if (elementDefinition.getCardinalityDefinition() == CardinalityDefinition.OPTIONAL && bindedElements != null && bindedElements.size() > 1) {
            throw new FormBindingException(Messages.Binding.getOptionalElementIsPresentMoreThanOnceMessage(elementDefinition), nodePath);
        }
        if (elementDefinition.getCardinalityDefinition() == CardinalityDefinition.PROHIBITED && bindedElements != null && !bindedElements.isEmpty()) {
            throw new FormBindingException(Messages.Binding.getProhibitedElementIsPresentMessage(elementDefinition), nodePath);
        }
    }

    @Override
    public void buildSingleElementInstance(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final SingleElementDefinition singleElementDefinition, final NodePath nodePath) {
        Element element = createSingleElementInstanceElement(document, (FormDefinition) parentElement.getUserData(USER_DATA_FORM_DEFINITION), singleElementDefinition);
        NodePath currentNodePath = new NodePath(nodePath, singleElementDefinition);
        bindNodeDefinitions(bindingSource, document, lastBindedForm, lastBindedElement, element, singleElementDefinition.getAllNodeDefinitions(), currentNodePath);
        validateBindedSingleElementDefinition(element, singleElementDefinition, nodePath);
        if (element.hasChildNodes()) {
            parentElement.appendChild(element);
        }
    }

    private Element createSingleElementInstanceElement(final Document document, final FormDefinition formDefinition, final SingleElementDefinition singleElementDefinition) {
        Element element = document.createElementNS(NAMESPACE, SINGLE_ELEMENT_INSTANCE_ELEMENT_NAME);
        element.setAttribute(SINGLE_ELEMENT_INSTANCE_ATTRIBUTE_ID, singleElementDefinition.getId());
        for (String otherAttributeName : singleElementDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, singleElementDefinition.getOtherAttributeValue(otherAttributeName));
        }
        element.setUserData(USER_DATA_FORM_DEFINITION, formDefinition, null);
        element.setUserData(USER_DATA_NODE_DEFINITION, singleElementDefinition, null);
        return element;
    }

    private void validateBindedSingleElementDefinition(final Element element, final SingleElementDefinition singleElementDefinition, final NodePath nodePath) {
        List<ElementDefinition> uniqueElementDefinitions = new ArrayList<>();
        addUniqueElementDefinitions(element, uniqueElementDefinitions);
        if (uniqueElementDefinitions.size() > 1) {
            throw new FormBindingException(Messages.Binding.getMultipleSingleElementsArePresentMessage(singleElementDefinition), nodePath);
        }
        if (singleElementDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED && uniqueElementDefinitions.size() == 0) {
            throw new FormBindingException(Messages.Binding.getRequiredSingleElementIsNotPresentMessage(singleElementDefinition), nodePath);
        }
        if (singleElementDefinition.getCardinalityDefinition() == CardinalityDefinition.PROHIBITED && uniqueElementDefinitions.size() == 1) {
            throw new FormBindingException(Messages.Binding.getProhibitedSingleElementIsPresentMessage(singleElementDefinition), nodePath);
        }
    }

    private void addUniqueElementDefinitions(final Element element, final List<ElementDefinition> uniqueElementDefinitions) {
        Object object = element.getUserData(USER_DATA_NODE_DEFINITION);
        if (object instanceof ElementDefinition) {
            if (!uniqueElementDefinitions.contains(object)) {
                uniqueElementDefinitions.add((ElementDefinition) object);
            }
            return;
        }
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                addUniqueElementDefinitions((Element) node, uniqueElementDefinitions);
            }
        }
    }

    @Override
    public void buildFormReferenceInstance(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(formReferenceDefinition);
        BindedForm bindedForm = _formInstanceBinder.bindFormDefinition(bindingSource, lastBindedForm, lastBindedElement, parentElement, formDefinition);
        validateBindedForm(bindedForm, formDefinition);
        Element element = createFormReferenceInstanceElement(document, formDefinition, formReferenceDefinition);
        NodePath currentNodePath = new NodePath(nodePath, formReferenceDefinition);
        bindNodeDefinitions(bindingSource, document, bindedForm, lastBindedElement, element, formDefinition.getAllNodeDefinitions(), currentNodePath);
        if (element.hasChildNodes()) {
            parentElement.appendChild(element);
        }
    }

    private Element createFormReferenceInstanceElement(final Document document, final FormDefinition formDefinition, final FormReferenceDefinition formReferenceDefinition) {
        Element element = document.createElementNS(NAMESPACE, FORM_REFERENCE_INSTANCE_ELEMENT_NAME);
        element.setAttribute(FORM_REFERENCE_INSTANCE_ATTRIBUTE_GROUP, formReferenceDefinition.getGroup());
        element.setAttribute(FORM_REFERENCE_INSTANCE_ATTRIBUTE_ID, formReferenceDefinition.getId());
        for (String otherAttributeName : formReferenceDefinition.getOtherAttributeNames()) {
            element.setAttribute(otherAttributeName, formReferenceDefinition.getOtherAttributeValue(otherAttributeName));
        }
        element.setUserData(USER_DATA_FORM_DEFINITION, formDefinition, null);
        element.setUserData(USER_DATA_NODE_DEFINITION, formReferenceDefinition, null);
        return element;
    }

    @Override
    public void buildOtherNodeInstance(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final OtherNodeDefinition otherNodeDefinition, final NodePath nodePath) {
        for (OtherNodeInstanceBuilder otherNodeInstanceBuilder : _otherNodeInstanceBuilders) {
            otherNodeInstanceBuilder.buildOtherNodeInstance(bindingSource, document, lastBindedForm, lastBindedElement, parentElement, otherNodeDefinition, this, nodePath);
        }
    }

    private void bindNodeDefinitions(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final List<NodeDefinition> nodeDefinitions, final NodePath nodePath) {
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof AttributeDefinition) {
                buildAttributeInstance(bindingSource, document, lastBindedForm, lastBindedElement, parentElement, (AttributeDefinition) nodeDefinition, nodePath);
            }
            if (nodeDefinition instanceof ElementDefinition) {
                buildElementInstance(bindingSource, document, lastBindedForm, lastBindedElement, parentElement, (ElementDefinition) nodeDefinition, nodePath);
            }
            if (nodeDefinition instanceof SingleElementDefinition) {
                buildSingleElementInstance(bindingSource, document, lastBindedForm, lastBindedElement, parentElement, (SingleElementDefinition) nodeDefinition, nodePath);
            }
            if (nodeDefinition instanceof FormReferenceDefinition) {
                buildFormReferenceInstance(bindingSource, document, lastBindedForm, lastBindedElement, parentElement, (FormReferenceDefinition) nodeDefinition, nodePath);
            }
            if (nodeDefinition instanceof OtherNodeDefinition) {
                buildOtherNodeInstance(bindingSource, document, lastBindedForm, lastBindedElement, parentElement, (OtherNodeDefinition) nodeDefinition, nodePath);
            }
        }
    }

}
