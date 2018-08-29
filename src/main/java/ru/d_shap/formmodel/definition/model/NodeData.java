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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Node data contains all the data needed for the node definition.
 *
 * @author Dmitry Shapovalov
 */
final class NodeData {

    private final List<AttributeDefinition> _attributeDefinitions;

    private final List<ElementDefinition> _elementDefinitions;

    private final List<ChoiceDefinition> _choiceDefinitions;

    private final List<FormReferenceDefinition> _formReferenceDefinitions;

    private final List<OtherNodeDefinition> _otherNodeDefinitions;

    private final Map<String, String> _otherAttributes;

    NodeData(final List<AttributeDefinition> attributeDefinitions, final List<NodeDefinition> nodeDefinitions, final Map<String, String> otherAttributes) {
        super();
        if (attributeDefinitions == null) {
            _attributeDefinitions = Collections.unmodifiableList(new ArrayList<AttributeDefinition>());
        } else {
            _attributeDefinitions = Collections.unmodifiableList(new ArrayList<>(attributeDefinitions));
        }
        List<ElementDefinition> elementDefinitions = new ArrayList<>();
        List<ChoiceDefinition> choiceDefinitions = new ArrayList<>();
        List<FormReferenceDefinition> formReferenceDefinitions = new ArrayList<>();
        List<OtherNodeDefinition> otherNodeDefinitions = new ArrayList<>();
        if (nodeDefinitions != null) {
            for (NodeDefinition nodeDefinition : nodeDefinitions) {
                if (nodeDefinition instanceof ElementDefinition) {
                    elementDefinitions.add((ElementDefinition) nodeDefinition);
                }
                if (nodeDefinition instanceof ChoiceDefinition) {
                    choiceDefinitions.add((ChoiceDefinition) nodeDefinition);
                }
                if (nodeDefinition instanceof FormReferenceDefinition) {
                    formReferenceDefinitions.add((FormReferenceDefinition) nodeDefinition);
                }
                if (nodeDefinition instanceof OtherNodeDefinition) {
                    otherNodeDefinitions.add((OtherNodeDefinition) nodeDefinition);
                }
            }
        }
        _elementDefinitions = Collections.unmodifiableList(elementDefinitions);
        _choiceDefinitions = Collections.unmodifiableList(choiceDefinitions);
        _formReferenceDefinitions = Collections.unmodifiableList(formReferenceDefinitions);
        _otherNodeDefinitions = Collections.unmodifiableList(otherNodeDefinitions);
        if (otherAttributes == null) {
            _otherAttributes = Collections.unmodifiableMap(new HashMap<String, String>());
        } else {
            _otherAttributes = Collections.unmodifiableMap(new HashMap<>(otherAttributes));
        }
    }

    List<AttributeDefinition> getAttributeDefinitions() {
        return _attributeDefinitions;
    }

    List<ElementDefinition> getElementDefinitions() {
        return _elementDefinitions;
    }

    List<ChoiceDefinition> getChoiceDefinitions() {
        return _choiceDefinitions;
    }

    List<FormReferenceDefinition> getFormReferenceDefinitions() {
        return _formReferenceDefinitions;
    }

    List<OtherNodeDefinition> getOtherNodeDefinitions() {
        return _otherNodeDefinitions;
    }

    Set<String> getOtherAttributeNames() {
        return _otherAttributes.keySet();
    }

    String getOtherAttributeValue(final String otherAttributeName) {
        return _otherAttributes.get(otherAttributeName);
    }

}
