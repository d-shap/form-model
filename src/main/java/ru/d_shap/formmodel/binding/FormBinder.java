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
 * Form binder.
 *
 * @param <F> binded form type.
 * @param <E> binded element type.
 * @param <B> binding object type.
 * @author Dmitry Shapovalov
 */
public abstract class FormBinder<F extends BindedForm<E, B>, E extends BindedElement<B>, B> {

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
     * @return the form definition.
     */
    protected final FormDefinition getFormDefinition(final String formId) {
        return _formDefinitions.getFormDefinition(formId);
    }

    /**
     * Bind the form definition with the form instance.
     *
     * @param formId the form ID.
     * @return the form instance.
     */
    protected final F doBind(final String formId) {
        init(formId);
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(formId);
        F bindedForm = getBindedFormInstance(formDefinition);
        List<NodeDefinition> nodeDefinitions = formDefinition.getChildNodeDefinitions();
        List<NodeDefinition> parentNodeDefinitions = new ArrayList<>();
        parentNodeDefinitions.add(formDefinition);
        List<BindedNode> bindedNodes = getBindedNodes(null, nodeDefinitions, parentNodeDefinitions);
        bindedForm.getBindedNodes().addAll(bindedNodes);
        return bindedForm;
    }

    private List<BindedNode> getBindedNodes(final E parentBindedElement, final List<NodeDefinition> nodeDefinitions, final List<NodeDefinition> parentNodeDefinitions) {
        List<BindedNode> bindedNodes = new ArrayList<>();
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                List<E> bindedElements = createBindedElements(parentBindedElement, (ElementDefinition) nodeDefinition, parentNodeDefinitions);
                bindedNodes.addAll(bindedElements);
            }
            if (nodeDefinition instanceof FormReferenceDefinition) {
                BindedFormReference bindedFormReference = createBindedFormReference(parentBindedElement, (FormReferenceDefinition) nodeDefinition, parentNodeDefinitions);
                bindedNodes.add(bindedFormReference);
            }
        }
        return bindedNodes;
    }

    private List<E> createBindedElements(final E parentBindedElement, final ElementDefinition elementDefinition, final List<NodeDefinition> parentNodeDefinitions) {
        String lookup = elementDefinition.getLookup();
        List<B> bindingObjects = getBindingObjectInstances(parentBindedElement, lookup);

        List<NodeDefinition> parentNodeDefinitionsCopy = new ArrayList<>(parentNodeDefinitions);
        parentNodeDefinitionsCopy.add(elementDefinition);

        if (elementDefinition.isMandatory() && !elementDefinition.isMultiple() && bindingObjects.isEmpty()) {
            throw new BindingException(BindingExceptionType.MANDATORY_ELEMENT_NOT_PRESENT, parentNodeDefinitionsCopy);
        }
        if (elementDefinition.isMandatory() && !elementDefinition.isMultiple() && bindingObjects.size() > 1) {
            throw new BindingException(BindingExceptionType.MANDATORY_ELEMENT_MULTIPLE_PRESENT, parentNodeDefinitionsCopy);
        }
        if (elementDefinition.isMandatory() && elementDefinition.isMultiple() && bindingObjects.isEmpty()) {
            throw new BindingException(BindingExceptionType.MANDATORY_MULTIPLE_ELEMENT_NOT_PRESENT, parentNodeDefinitionsCopy);
        }
        if (elementDefinition.isForbidden() && !bindingObjects.isEmpty()) {
            throw new BindingException(BindingExceptionType.FORBIDDEN_ELEMENT_PRESENT, parentNodeDefinitionsCopy);
        }

        List<E> bindedElements = new ArrayList<>();
        for (B bindingObject : bindingObjects) {
            E bindedElement = getBindedElementInstance(elementDefinition, bindingObject);
            List<BindedNode> bindedNodes = getBindedNodes(bindedElement, elementDefinition.getChildNodeDefinitions(), parentNodeDefinitionsCopy);
            bindedElement.getChildBindedNodes().addAll(bindedNodes);
            bindedElements.add(bindedElement);
        }
        return bindedElements;
    }

    private BindedFormReference createBindedFormReference(final E parentBindedElement, final FormReferenceDefinition formReferenceDefinition, final List<NodeDefinition> parentNodeDefinitions) {
        FormDefinition formDefinition = formReferenceDefinition.getReferencedFormDefinition();
        BindedFormReference bindedFormReference = getBindedFormReferenceInstance(formDefinition);
        List<NodeDefinition> parentNodeDefinitionsCopy = new ArrayList<>(parentNodeDefinitions);
        parentNodeDefinitionsCopy.add(formDefinition);
        List<BindedNode> bindedNodes = getBindedNodes(parentBindedElement, formDefinition.getChildNodeDefinitions(), parentNodeDefinitionsCopy);
        bindedFormReference.getChildBindedNodes().addAll(bindedNodes);
        return bindedFormReference;
    }

    /**
     * Init form binder to perform bindings.
     *
     * @param formId the form ID.
     */
    protected abstract void init(String formId);

    /**
     * Create the binded form instance.
     *
     * @param formDefinition the form definition.
     * @return the binded form instance.
     */
    protected abstract F getBindedFormInstance(FormDefinition formDefinition);

    /**
     * Create the binding objects for the lookup string.
     *
     * @param parentElement the parent binded element.
     * @param lookup        the lookup string.
     * @return the binding objects.
     */
    protected abstract List<B> getBindingObjectInstances(E parentElement, String lookup);

    /**
     * Create the binded element instance.
     *
     * @param elementDefinition the element definition.
     * @param bindingObject     the binding object.
     * @return the binded element instance.
     */
    protected abstract E getBindedElementInstance(ElementDefinition elementDefinition, B bindingObject);

    private BindedFormReference getBindedFormReferenceInstance(final FormDefinition formDefinition) {
        return new BindedFormReference(formDefinition);
    }

}
