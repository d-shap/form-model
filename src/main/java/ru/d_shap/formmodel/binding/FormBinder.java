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

    /**
     * Create new object.
     */
    protected FormBinder() {
        super();
    }

    /**
     * Bind the form definition with the form instance.
     *
     * @param formDefinitions the form definitions.
     * @param formId          the form ID.
     * @return the form instance.
     */
    protected final F doBind(final FormDefinitions formDefinitions, final String formId) {
        init(formDefinitions, formId);
        FormDefinition formDefinition = formDefinitions.getFormDefinitions().get(formId);
        F bindedForm = getBindedFormInstance(formDefinition);
        List<NodeDefinition> nodeDefinitions = formDefinition.getNodeDefinitions();
        List<BindedNode> bindedNodes = getBindedNodes(formDefinitions, null, nodeDefinitions);
        bindedForm.getBindedNodes().addAll(bindedNodes);
        return bindedForm;
    }

    private List<BindedNode> getBindedNodes(final FormDefinitions formDefinitions, final E parentBindedElement, final List<NodeDefinition> nodeDefinitions) {
        List<BindedNode> bindedNodes = new ArrayList<>();
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                List<E> bindedElements = createBindedElements(formDefinitions, parentBindedElement, (ElementDefinition) nodeDefinition);
                bindedNodes.addAll(bindedElements);
            }
            if (nodeDefinition instanceof FormReferenceDefinition) {
                BindedFormReference bindedFormReference = createBindedFormReference(formDefinitions, parentBindedElement, (FormReferenceDefinition) nodeDefinition);
                bindedNodes.add(bindedFormReference);
            }
        }
        return bindedNodes;
    }

    private List<E> createBindedElements(final FormDefinitions formDefinitions, final E parentBindedElement, final ElementDefinition elementDefinition) {
        String lookup = elementDefinition.getLookup();
        List<B> bindingObjects = getBindingObjectInstances(parentBindedElement, lookup);

        List<E> bindedElements = new ArrayList<>();
        for (B bindingObject : bindingObjects) {
            E bindedElement = getBindedElementInstance(elementDefinition, bindingObject);
            List<BindedNode> bindedNodes = getBindedNodes(formDefinitions, bindedElement, elementDefinition.getChildNodeDefinitions());
            bindedElement.getChildBindedNodes().addAll(bindedNodes);
            bindedElements.add(bindedElement);
        }
        return bindedElements;
    }

    private BindedFormReference createBindedFormReference(final FormDefinitions formDefinitions, final E parentBindedElement, final FormReferenceDefinition formReferenceDefinition) {
        String formId = formReferenceDefinition.getReferenceId();
        FormDefinition formDefinition = formDefinitions.getFormDefinitions().get(formId);
        BindedFormReference bindedFormReference = getBindedFormReferenceInstance(formDefinition);
        List<BindedNode> bindedNodes = getBindedNodes(formDefinitions, parentBindedElement, formDefinition.getNodeDefinitions());
        bindedFormReference.getChildBindedNodes().addAll(bindedNodes);
        return bindedFormReference;
    }

    /**
     * Init form binder to perform bindings.
     *
     * @param formDefinitions the form definitions.
     * @param formId          the form ID.
     */
    protected abstract void init(FormDefinitions formDefinitions, String formId);

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
