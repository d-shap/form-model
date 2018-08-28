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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Node data contains all the data needed to bind the element.
 *
 * @author Dmitry Shapovalov
 */
final class NodeData {

    private final List<NodeDefinition> _nodeDefinitions;

    private final List<AttributeDefinition> _attributeDefinitions;

    private final Map<String, String> _otherAttributes;

    NodeData() {
        super();
        _nodeDefinitions = new ArrayList<>();
        _attributeDefinitions = new ArrayList<>();
        _otherAttributes = new HashMap<>();
    }

    void addNodeDefinition(final NodeDefinition nodeDefinition) {
        _nodeDefinitions.add(nodeDefinition);
    }

    void addAttributeDefinition(final AttributeDefinition attributeDefinition) {
        _attributeDefinitions.add(attributeDefinition);
    }

    void addOtherAttribute(final String attributeName, final String attributeValue) {
        _otherAttributes.put(attributeName, attributeValue);
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

    List<OtherNodeDefinition> getOtherNodeDefinitions() {
        List<OtherNodeDefinition> otherNodeDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : _nodeDefinitions) {
            if (nodeDefinition instanceof OtherNodeDefinition) {
                otherNodeDefinitions.add((OtherNodeDefinition) nodeDefinition);
            }
        }
        return otherNodeDefinitions;
    }

    List<AttributeDefinition> getAttributeDefinitions() {
        return new ArrayList<>(_attributeDefinitions);
    }

    Set<String> getOtherAttributeNames() {
        return new HashSet<>(_otherAttributes.keySet());
    }

    String getOtherAttributeValue(final String otherAttributeName) {
        return _otherAttributes.get(otherAttributeName);
    }

}
