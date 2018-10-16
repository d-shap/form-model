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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinitionImpl;

/**
 * Implementation of the {@link OtherNodeInstanceBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class OtherNodeInstanceBuilderImpl implements OtherNodeInstanceBuilder {

    private static final String NAMESPACE = "http://d-shap.ru/schema/form-instance-other-node/1.0";

    private static final String LOCAL_NAME = "otherNode";

    private static final String INSERT_INVALID_ELEMENT_REPRESENTATION = "insertInvalidElement";

    /**
     * Create new object.
     */
    public OtherNodeInstanceBuilderImpl() {
        super();
    }

    @Override
    public boolean isCompatible(final Class<? extends FormInstanceBinder> clazz) {
        return clazz == FormInstanceBinderImpl.class;
    }

    @Override
    public void buildOtherNodeInstance(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final OtherNodeDefinition otherNodeDefinition, final FormInstanceBuilder formInstanceBuilder, final NodePath nodePath) {
        if (otherNodeDefinition instanceof OtherNodeDefinitionImpl) {
            String representation = ((OtherNodeDefinitionImpl) otherNodeDefinition).getRepresentation();
            Element element;
            if (INSERT_INVALID_ELEMENT_REPRESENTATION.equalsIgnoreCase(representation)) {
                element = document.createElementNS(FormInstanceBuilder.NAMESPACE, LOCAL_NAME);
            } else {
                element = document.createElementNS(NAMESPACE, LOCAL_NAME);
            }
            element.setAttribute("repr", representation);
            element.setUserData(FormInstanceBuilder.USER_DATA_FORM_DEFINITION, formInstanceBuilder.getParentElementUserData(parentElement, FormInstanceBuilder.USER_DATA_FORM_DEFINITION), null);
            element.setUserData(FormInstanceBuilder.USER_DATA_NODE_DEFINITION, otherNodeDefinition, null);
            parentElement.appendChild(element);

            NodePath currentNodePath = new NodePath(otherNodeDefinition);
            if (((OtherNodeDefinitionImpl) otherNodeDefinition).getAttributeDefinition() != null) {
                formInstanceBuilder.buildAttributeInstance(bindingSource, document, lastBindedForm, lastBindedElement, element, ((OtherNodeDefinitionImpl) otherNodeDefinition).getAttributeDefinition(), currentNodePath);
            }
            if (((OtherNodeDefinitionImpl) otherNodeDefinition).getElementDefinition() != null) {
                formInstanceBuilder.buildElementInstance(bindingSource, document, lastBindedForm, lastBindedElement, element, ((OtherNodeDefinitionImpl) otherNodeDefinition).getElementDefinition(), currentNodePath);
            }
            if (((OtherNodeDefinitionImpl) otherNodeDefinition).getSingleElementDefinition() != null) {
                formInstanceBuilder.buildSingleElementInstance(bindingSource, document, lastBindedForm, lastBindedElement, element, ((OtherNodeDefinitionImpl) otherNodeDefinition).getSingleElementDefinition(), currentNodePath);
            }
            if (((OtherNodeDefinitionImpl) otherNodeDefinition).getFormReferenceDefinition() != null) {
                formInstanceBuilder.buildFormReferenceInstance(bindingSource, document, lastBindedForm, lastBindedElement, element, ((OtherNodeDefinitionImpl) otherNodeDefinition).getFormReferenceDefinition(), currentNodePath);
            }
            if (((OtherNodeDefinitionImpl) otherNodeDefinition).getOtherNodeDefinition() != null) {
                formInstanceBuilder.buildOtherNodeInstance(bindingSource, document, lastBindedForm, lastBindedElement, element, ((OtherNodeDefinitionImpl) otherNodeDefinition).getOtherNodeDefinition(), currentNodePath);
            }
        }
    }

}
