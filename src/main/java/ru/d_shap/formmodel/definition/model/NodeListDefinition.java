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
package ru.d_shap.formmodel.definition.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Node list definition contains a list of the child node definitions.
 *
 * @author Dmitry Shapovalov
 */
final class NodeListDefinition {

    private final List<NodeDefinition> _nodeDefinitions;

    NodeListDefinition() {
        super();
        _nodeDefinitions = new ArrayList<>();
    }

    void addNodeDefinition(final NodeDefinition nodeDefinition) {
        _nodeDefinitions.add(nodeDefinition);
    }

    List<ElementDefinition> getElementDefinitions() {
        List<ElementDefinition> elementDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : _nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                elementDefinitions.add((ElementDefinition) nodeDefinition);
            }
        }
        return elementDefinitions;
    }

    List<ChoiceDefinition> getChoiceDefinitions() {
        List<ChoiceDefinition> choiceDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : _nodeDefinitions) {
            if (nodeDefinition instanceof ChoiceDefinition) {
                choiceDefinitions.add((ChoiceDefinition) nodeDefinition);
            }
        }
        return choiceDefinitions;
    }

    List<FormReferenceDefinition> getFormReferenceDefinitions() {
        List<FormReferenceDefinition> formReferenceDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : _nodeDefinitions) {
            if (nodeDefinition instanceof FormReferenceDefinition) {
                formReferenceDefinitions.add((FormReferenceDefinition) nodeDefinition);
            }
        }
        return formReferenceDefinitions;
    }

}
