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

import ru.d_shap.formmodel.definition.ElementDefinition;
import ru.d_shap.formmodel.definition.FormDefinition;
import ru.d_shap.formmodel.definition.FormDefinitions;
import ru.d_shap.formmodel.definition.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.NodeDefinition;

/**
 * Base class for the form binder.
 *
 * @param <F> generic type of the binded form.
 * @param <E> generic type of the binded element.
 * @param <R> generic type of the binded form reference.
 * @param <B> generic type of the binding data.
 *
 * @author Dmitry Shapovalov
 */
public abstract class FormBinder<F extends BindedForm<E, R, B>, E extends BindedElement<E, R, B>, R extends BindedFormReference<E, R, B>, B> {

    private final FormDefinitions _formDefinitions;

    /**
     * Create new object.
     *
     * @param formDefinitions the form definitions.
     */
    protected FormBinder(final FormDefinitions formDefinitions) {
        super();
        _formDefinitions = formDefinitions;
    }

    /**
     * Get the form definition for the specified form ID.
     *
     * @param formId the specified form ID.
     *
     * @return the form definition.
     */
    protected final FormDefinition getFormDefinition(final String formId) {
        return _formDefinitions.getFormDefinition(formId);
    }

    /**
     * Bind the actual form.
     *
     * @param formId the form ID.
     *
     * @return the binded form.
     */
    protected final F doBind(final String formId) {
        FormDefinition formDefinition = getFormDefinition(formId);
        F bindedForm = createBindedFormInstance(formDefinition);
        List<NodeDefinition> nodeDefinitions = formDefinition.getChildNodeDefinitions();
        FormBindingPath formBindingPath = new FormBindingPath(formDefinition);
        List<BindedNode<E, R, B>> bindedNodes = getBindedNodes(null, nodeDefinitions, formBindingPath);
        bindedForm.getChildBindedNodes().addAll(bindedNodes);
        return bindedForm;
    }

    private List<BindedNode<E, R, B>> getBindedNodes(final E parentBindedElement, final List<NodeDefinition> nodeDefinitions, final FormBindingPath formBindingPath) {
        List<BindedNode<E, R, B>> bindedNodes = new ArrayList<>();
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                List<E> bindedElements = createBindedElements(parentBindedElement, (ElementDefinition) nodeDefinition, formBindingPath);
                bindedNodes.addAll(bindedElements);
            }
            if (nodeDefinition instanceof FormReferenceDefinition) {
                BindedFormReference<E, R, B> bindedFormReference = createBindedFormReference(parentBindedElement, (FormReferenceDefinition) nodeDefinition, formBindingPath);
                bindedNodes.add(bindedFormReference);
            }
        }
        return bindedNodes;
    }

    private List<E> createBindedElements(final E parentBindedElement, final ElementDefinition elementDefinition, final FormBindingPath formBindingPath) {
        String lookup = elementDefinition.getLookup();
        List<B> bindingDatas = createBindingDataInstances(parentBindedElement, lookup);
        FormBindingPath nextFormBindingPath = new FormBindingPath(formBindingPath, elementDefinition);

        if (elementDefinition.isMandatory() && !elementDefinition.isMultiple() && bindingDatas.isEmpty()) {
            throw new FormBindingException(FormBindingExceptionType.MANDATORY_ELEMENT_NOT_PRESENT, nextFormBindingPath);
        }
        if (elementDefinition.isMandatory() && !elementDefinition.isMultiple() && bindingDatas.size() > 1) {
            throw new FormBindingException(FormBindingExceptionType.MANDATORY_ELEMENT_PRESENT_MORE_THEN_ONCE, nextFormBindingPath);
        }
        if (elementDefinition.isMandatory() && elementDefinition.isMultiple() && bindingDatas.isEmpty()) {
            throw new FormBindingException(FormBindingExceptionType.MANDATORY_MULTIPLE_ELEMENT_NOT_PRESENT, nextFormBindingPath);
        }
        if (elementDefinition.isForbidden() && !bindingDatas.isEmpty()) {
            throw new FormBindingException(FormBindingExceptionType.FORBIDDEN_ELEMENT_PRESENT, nextFormBindingPath);
        }

        List<E> bindedElements = new ArrayList<>();
        for (B bindingData : bindingDatas) {
            E bindedElement = createBindedElementInstance(elementDefinition, bindingData);
            List<BindedNode<E, R, B>> bindedNodes = getBindedNodes(bindedElement, elementDefinition.getChildNodeDefinitions(), nextFormBindingPath);
            bindedElement.getChildBindedNodes().addAll(bindedNodes);
            bindedElements.add(bindedElement);
        }
        return bindedElements;
    }

    private BindedFormReference<E, R, B> createBindedFormReference(final E parentBindedElement, final FormReferenceDefinition formReferenceDefinition, final FormBindingPath formBindingPath) {
        BindedFormReference<E, R, B> bindedFormReference = createBindedFormReferenceInstance(formReferenceDefinition);
        FormDefinition formDefinition = formReferenceDefinition.getReferencedFormDefinition();
        List<NodeDefinition> nodeDefinitions = formDefinition.getChildNodeDefinitions();
        FormBindingPath nextFormBindingPath = new FormBindingPath(formBindingPath, formDefinition);
        List<BindedNode<E, R, B>> bindedNodes = getBindedNodes(parentBindedElement, nodeDefinitions, nextFormBindingPath);
        bindedFormReference.getChildBindedNodes().addAll(bindedNodes);
        return bindedFormReference;
    }

    /**
     * Create the binded form instance.
     *
     * @param formDefinition the form definition.
     *
     * @return the binded form instance.
     */
    protected abstract F createBindedFormInstance(FormDefinition formDefinition);

    /**
     * Create the binding data instances.
     *
     * @param parentBindedElement the parent binded element.
     * @param lookup              the lookup string.
     *
     * @return the binding data instances.
     */
    protected abstract List<B> createBindingDataInstances(E parentBindedElement, String lookup);

    /**
     * Create the binded element instance.
     *
     * @param elementDefinition the element definition.
     * @param bindingData       the binding data.
     *
     * @return the binded element instance.
     */
    protected abstract E createBindedElementInstance(ElementDefinition elementDefinition, B bindingData);

    /**
     * Create the binded form reference instance.
     *
     * @param formReferenceDefinition the form reference definition.
     *
     * @return the binded form reference instance.
     */
    protected abstract R createBindedFormReferenceInstance(FormReferenceDefinition formReferenceDefinition);

}
