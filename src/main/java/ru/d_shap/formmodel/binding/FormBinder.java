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
import org.w3c.dom.Element;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.XmlDocumentValidator;
import ru.d_shap.formmodel.binding.api.Binder;
import ru.d_shap.formmodel.binding.api.FormModelInstanceBuilder;
import ru.d_shap.formmodel.binding.api.OtherNodeInstanceBuilder;
import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * The form binder.
 *
 * @author Dmitry Shapovalov
 */
public final class FormBinder {

    private static final ServiceLoader<OtherNodeInstanceBuilder> SERVICE_LOADER = ServiceLoader.load(OtherNodeInstanceBuilder.class);

    private final FormDefinitions _formDefinitions;

    private final Binder _binder;

    private final List<OtherNodeInstanceBuilder> _otherNodeInstanceBuilders;

    /**
     * Create new object.
     *
     * @param formDefinitions container for all form definitions.
     * @param binder          the binder instance to bind form model definition elements with the binding source.
     */
    public FormBinder(final FormDefinitions formDefinitions, final Binder binder) {
        super();
        _formDefinitions = formDefinitions;
        _binder = binder;
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
     * @param id            the specified form's ID.
     *
     * @return the binded form instance.
     */
    public Document bind(final BindingSource bindingSource, final String id) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(id);
        return bind(bindingSource, formDefinition);
    }

    /**
     * Bind the specified form definition with the specified binding source.
     *
     * @param bindingSource the specified binding source.
     * @param group         the specified form's group.
     * @param id            the specified form's ID.
     *
     * @return the binding result.
     */
    public Document bind(final BindingSource bindingSource, final String group, final String id) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(group, id);
        return bind(bindingSource, formDefinition);
    }

    private Document bind(final BindingSource bindingSource, final FormDefinition formDefinition) {
        Document document = XmlDocumentBuilder.newDocument();
        FormBinderImpl formInstanceBinder = new FormBinderImpl(bindingSource, document);
        formInstanceBinder.bindFormInstance(formDefinition);
        XmlDocumentValidator.getFormInstanceSchemaValidator().validate(document);
        return document;
    }

    /**
     * The form binder implementation.
     *
     * @author Dmitry Shapovalov
     */
    private final class FormBinderImpl implements FormModelInstanceBuilder {

        private final BindingSource _bindingSource;

        private final Document _document;

        FormBinderImpl(final BindingSource bindingSource, final Document document) {
            super();
            _bindingSource = bindingSource;
            _document = document;
        }

        @Override
        public BindingSource getBindingSource() {
            return _bindingSource;
        }

        @Override
        public Document getDocument() {
            return _document;
        }

        void bindFormInstance(final FormDefinition formDefinition) {
            BindedForm bindedForm = _binder.bindFormDefinition(_bindingSource, null, null, null, formDefinition);
            Element element = addXmlElement(formDefinition);
            _document.appendChild(element);
            NodePath currentNodePath = new NodePath(formDefinition);
            for (ElementDefinition childElementDefinition : formDefinition.getElementDefinitions()) {
                bindElementInstance(bindedForm, null, element, childElementDefinition, currentNodePath);
            }
            for (ChoiceDefinition childChoiceDefinition : formDefinition.getChoiceDefinitions()) {
                bindChoiceInstance(bindedForm, null, element, childChoiceDefinition, currentNodePath);
            }
            for (FormReferenceDefinition childFormReferenceDefinition : formDefinition.getFormReferenceDefinitions()) {
                bindFormReferenceInstance(bindedForm, null, element, childFormReferenceDefinition, currentNodePath);
            }
            for (OtherNodeDefinition childOtherNodeDefinition : formDefinition.getOtherNodeDefinitions()) {
                bindOtherNodeInstance(bindedForm, null, element, childOtherNodeDefinition, currentNodePath);
            }
        }

        @Override
        public void bindElementInstance(final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final ElementDefinition elementDefinition, final NodePath nodePath) {
            List<BindedElement> bindedElements = _binder.bindElementDefinition(_bindingSource, lastBindedForm, lastBindedElement, parentElement, elementDefinition);
            validateBindedElement(bindedElements, elementDefinition, nodePath);
            for (BindedElement bindedElement : bindedElements) {
                Element element = addXmlElement(elementDefinition);
                parentElement.appendChild(element);
                element.setUserData(Binder.BINDED_OBJECT_KEY, bindedElement, null);
                NodePath currentNodePath = new NodePath(nodePath, elementDefinition);
                for (AttributeDefinition childAttributeDefinition : elementDefinition.getAttributeDefinitions()) {
                    bindAttributeInstance(lastBindedForm, bindedElement, element, childAttributeDefinition, currentNodePath);
                }
                for (ElementDefinition childElementDefinition : elementDefinition.getElementDefinitions()) {
                    bindElementInstance(lastBindedForm, bindedElement, element, childElementDefinition, currentNodePath);
                }
                for (ChoiceDefinition childChoiceDefinition : elementDefinition.getChoiceDefinitions()) {
                    bindChoiceInstance(lastBindedForm, bindedElement, element, childChoiceDefinition, currentNodePath);
                }
                for (FormReferenceDefinition childFormReferenceDefinition : elementDefinition.getFormReferenceDefinitions()) {
                    bindFormReferenceInstance(lastBindedForm, bindedElement, element, childFormReferenceDefinition, currentNodePath);
                }
                for (OtherNodeDefinition childOtherNodeDefinition : elementDefinition.getOtherNodeDefinitions()) {
                    bindOtherNodeInstance(lastBindedForm, bindedElement, element, childOtherNodeDefinition, currentNodePath);
                }
            }
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
        public void bindChoiceInstance(final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final ChoiceDefinition choiceDefinition, final NodePath nodePath) {
            Element element = addXmlElement(choiceDefinition);
            NodePath currentNodePath = new NodePath(nodePath, choiceDefinition);
            for (ElementDefinition childElementDefinition : choiceDefinition.getElementDefinitions()) {
                bindElementInstance(lastBindedForm, lastBindedElement, element, childElementDefinition, currentNodePath);
            }
            for (ChoiceDefinition childChoiceDefinition : choiceDefinition.getChoiceDefinitions()) {
                bindChoiceInstance(lastBindedForm, lastBindedElement, element, childChoiceDefinition, currentNodePath);
            }
            for (OtherNodeDefinition childOtherNodeDefinition : choiceDefinition.getOtherNodeDefinitions()) {
                bindOtherNodeInstance(lastBindedForm, lastBindedElement, element, childOtherNodeDefinition, currentNodePath);
            }
            validateBindedChoice(element, choiceDefinition, nodePath);
            if (element.hasChildNodes()) {
                parentElement.appendChild(element);
            }
        }

        private void validateBindedChoice(final Element element, final ChoiceDefinition choiceDefinition, final NodePath nodePath) {
            if (element.getChildNodes().getLength() > 1) {
                throw new FormBindingException(Messages.Binding.getMultipleChoiceElementsArePresentMessage(choiceDefinition), nodePath);
            }
        }

        @Override
        public void bindFormReferenceInstance(final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
            FormDefinition formDefinition = _formDefinitions.getFormDefinition(formReferenceDefinition);
            BindedForm bindedForm = _binder.bindFormDefinition(_bindingSource, lastBindedForm, lastBindedElement, parentElement, formDefinition);
            Element element = addXmlElement(formReferenceDefinition);
            NodePath currentNodePath = new NodePath(nodePath, formReferenceDefinition);
            for (ElementDefinition childElementDefinition : formDefinition.getElementDefinitions()) {
                bindElementInstance(bindedForm, lastBindedElement, element, childElementDefinition, currentNodePath);
            }
            for (ChoiceDefinition childChoiceDefinition : formDefinition.getChoiceDefinitions()) {
                bindChoiceInstance(bindedForm, lastBindedElement, element, childChoiceDefinition, currentNodePath);
            }
            for (FormReferenceDefinition childFormReferenceDefinition : formDefinition.getFormReferenceDefinitions()) {
                bindFormReferenceInstance(bindedForm, lastBindedElement, element, childFormReferenceDefinition, currentNodePath);
            }
            for (OtherNodeDefinition childOtherNodeDefinition : formDefinition.getOtherNodeDefinitions()) {
                bindOtherNodeInstance(bindedForm, lastBindedElement, element, childOtherNodeDefinition, currentNodePath);
            }
            for (OtherNodeDefinition childOtherNodeDefinition : formReferenceDefinition.getOtherNodeDefinitions()) {
                bindOtherNodeInstance(bindedForm, lastBindedElement, element, childOtherNodeDefinition, currentNodePath);
            }
            if (element.hasChildNodes()) {
                parentElement.appendChild(element);
            }
        }

        @Override
        public void bindAttributeInstance(final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
            BindedAttribute bindedAttribute = _binder.bindAttributeDefinition(_bindingSource, lastBindedForm, lastBindedElement, parentElement, attributeDefinition);
            validateBindedAttribute(bindedAttribute, attributeDefinition, nodePath);
            if (bindedAttribute != null) {
                Element element = addXmlElement(attributeDefinition);
                parentElement.appendChild(element);
                element.setUserData(Binder.BINDED_OBJECT_KEY, bindedAttribute, null);
                NodePath currentNodePath = new NodePath(nodePath, attributeDefinition);
                for (OtherNodeDefinition childOtherNodeDefinition : attributeDefinition.getOtherNodeDefinitions()) {
                    bindOtherNodeInstance(lastBindedForm, lastBindedElement, element, childOtherNodeDefinition, currentNodePath);
                }
            }
        }

        private void validateBindedAttribute(final BindedAttribute bindedAttribute, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
            if (attributeDefinition.getCardinalityDefinition() == CardinalityDefinition.REQUIRED && bindedAttribute == null) {
                throw new FormBindingException(Messages.Binding.getRequiredAttributeIsNotPresentMessage(attributeDefinition), nodePath);
            }
            if (attributeDefinition.getCardinalityDefinition() == CardinalityDefinition.PROHIBITED && bindedAttribute != null) {
                throw new FormBindingException(Messages.Binding.getProhibitedAttributeIsPresentMessage(attributeDefinition), nodePath);
            }
        }

        private void bindOtherNodeInstance(final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final OtherNodeDefinition otherNodeDefinition, final NodePath nodePath) {
            for (OtherNodeInstanceBuilder otherNodeInstanceBuilder : _otherNodeInstanceBuilders) {
                otherNodeInstanceBuilder.addOtherNodeInstance(lastBindedForm, lastBindedElement, parentElement, otherNodeDefinition, this, nodePath);
            }
        }

        private Element addXmlElement(final FormDefinition formDefinition) {
            Element element = _document.createElementNS(NAMESPACE, FORM_INSTANCE_ELEMENT_NAME);
            element.setAttribute(FORM_INSTANCE_ATTRIBUTE_GROUP, formDefinition.getGroup());
            element.setAttribute(FORM_INSTANCE_ATTRIBUTE_ID, formDefinition.getId());
            for (String otherAttributeName : formDefinition.getOtherAttributeNames()) {
                element.setAttribute(otherAttributeName, formDefinition.getOtherAttributeValue(otherAttributeName));
            }
            return element;
        }

        private Element addXmlElement(final ElementDefinition elementDefinition) {
            Element element = _document.createElementNS(NAMESPACE, ELEMENT_INSTANCE_ELEMENT_NAME);
            element.setAttribute(ELEMENT_INSTANCE_ATTRIBUTE_ID, elementDefinition.getId());
            for (String otherAttributeName : elementDefinition.getOtherAttributeNames()) {
                element.setAttribute(otherAttributeName, elementDefinition.getOtherAttributeValue(otherAttributeName));
            }
            return element;
        }

        private Element addXmlElement(final ChoiceDefinition choiceDefinition) {
            Element element = _document.createElementNS(NAMESPACE, CHOICE_INSTANCE_ELEMENT_NAME);
            element.setAttribute(CHOICE_INSTANCE_ATTRIBUTE_ID, choiceDefinition.getId());
            for (String otherAttributeName : choiceDefinition.getOtherAttributeNames()) {
                element.setAttribute(otherAttributeName, choiceDefinition.getOtherAttributeValue(otherAttributeName));
            }
            return element;
        }

        private Element addXmlElement(final FormReferenceDefinition formReferenceDefinition) {
            Element element = _document.createElementNS(NAMESPACE, FORM_REFERENCE_INSTANCE_ELEMENT_NAME);
            element.setAttribute(FORM_REFERENCE_INSTANCE_ATTRIBUTE_GROUP, formReferenceDefinition.getGroup());
            element.setAttribute(FORM_REFERENCE_INSTANCE_ATTRIBUTE_ID, formReferenceDefinition.getId());
            for (String otherAttributeName : formReferenceDefinition.getOtherAttributeNames()) {
                element.setAttribute(otherAttributeName, formReferenceDefinition.getOtherAttributeValue(otherAttributeName));
            }
            return element;
        }

        private Element addXmlElement(final AttributeDefinition attributeDefinition) {
            Element element = _document.createElementNS(NAMESPACE, ATTRIBUTE_INSTANCE_ELEMENT_NAME);
            element.setAttribute(ATTRIBUTE_INSTANCE_ATTRIBUTE_ID, attributeDefinition.getId());
            for (String otherAttributeName : attributeDefinition.getOtherAttributeNames()) {
                element.setAttribute(otherAttributeName, attributeDefinition.getOtherAttributeValue(otherAttributeName));
            }
            return element;
        }

    }

}
