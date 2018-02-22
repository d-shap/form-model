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
package ru.d_shap.formmodel.definition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base class for all form definition elements.
 *
 * @author Dmitry Shapovalov
 */
public abstract class NodeDefinition {

    private FormDefinitions _formDefinitions;

    NodeDefinition() {
        super();
    }

    final FormDefinition getFormDefinition(final String formId) {
        return _formDefinitions.getFormDefinition(formId);
    }

    final void setFormDefinitions(final FormDefinitions formDefinitions) {
        _formDefinitions = formDefinitions;
        setChildNodesFormDefinitions();
    }

    final void setFormDefinitions(final NodeDefinition nodeDefinition) {
        nodeDefinition._formDefinitions = _formDefinitions;
        nodeDefinition.setChildNodesFormDefinitions();
    }

    abstract void setChildNodesFormDefinitions();

    final List<ElementDefinition> getElementDefinitions(final List<NodeDefinition> nodeDefinitions) {
        List<ElementDefinition> elementDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                elementDefinitions.add((ElementDefinition) nodeDefinition);
            }
        }
        return elementDefinitions;
    }

    final List<FormReferenceDefinition> getFormReferenceDefinitions(final List<NodeDefinition> nodeDefinitions) {
        List<FormReferenceDefinition> formReferenceDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof FormReferenceDefinition) {
                formReferenceDefinitions.add((FormReferenceDefinition) nodeDefinition);
            }
        }
        return formReferenceDefinitions;
    }

    final List<FormReferenceDefinition> getAllFormReferenceDefinitions(final List<NodeDefinition> nodeDefinitions) {
        List<FormReferenceDefinition> allFormReferenceDefinitions = new ArrayList<>();
        addFormReferenceDefinitions(nodeDefinitions, allFormReferenceDefinitions);
        return allFormReferenceDefinitions;
    }

    private void addFormReferenceDefinitions(final List<NodeDefinition> nodeDefinitions, final List<FormReferenceDefinition> allFormReferenceDefinitions) {
        for (NodeDefinition nodeDefinition : nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                addFormReferenceDefinitions(((ElementDefinition) nodeDefinition).getChildNodeDefinitions(), allFormReferenceDefinitions);
            }
            if (nodeDefinition instanceof FormReferenceDefinition) {
                allFormReferenceDefinitions.add((FormReferenceDefinition) nodeDefinition);
            }
        }
    }

    final Set<FormDefinition> getFormDefinitions(final List<NodeDefinition> nodeDefinitions) {
        Set<String> formIds = new HashSet<>();
        List<FormReferenceDefinition> formReferenceDefinitions = getFormReferenceDefinitions(nodeDefinitions);
        for (FormReferenceDefinition formReferenceDefinition : formReferenceDefinitions) {
            String referencedFormId = formReferenceDefinition.getReferencedFormId();
            formIds.add(referencedFormId);
        }
        return getFormDefinitions(formIds);
    }

    private Set<FormDefinition> getFormDefinitions(final Set<String> formIds) {
        Set<FormDefinition> formDefinitions = new HashSet<>();
        for (String formId : formIds) {
            FormDefinition formDefinition = _formDefinitions.getFormDefinition(formId);
            formDefinitions.add(formDefinition);
        }
        return formDefinitions;
    }

    final Set<FormDefinition> getAllFormDefinitions(final List<NodeDefinition> nodeDefinitions) {
        Set<String> formIds = new HashSet<>();
        List<FormReferenceDefinition> allFormReferenceDefinitions = getAllFormReferenceDefinitions(nodeDefinitions);
        for (FormReferenceDefinition formReferenceDefinition : allFormReferenceDefinitions) {
            String referencedFormId = formReferenceDefinition.getReferencedFormId();
            formIds.add(referencedFormId);
        }
        return getFormDefinitions(formIds);
    }

}
