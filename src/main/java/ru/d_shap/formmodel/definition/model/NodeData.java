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
 * All the data needed for the node definition.
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

    NodeData(final List<NodeDefinition> nodeDefinitions, final Set<String> childElementNames, final Map<String, String> otherAttributes, final Set<String> attributeNames) {
        super();
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        List<ElementDefinition> elementDefinitions = new ArrayList<>();
        List<ChoiceDefinition> choiceDefinitions = new ArrayList<>();
        List<FormReferenceDefinition> formReferenceDefinitions = new ArrayList<>();
        List<OtherNodeDefinition> otherNodeDefinitions = new ArrayList<>();
        if (nodeDefinitions != null) {
            for (NodeDefinition nodeDefinition : nodeDefinitions) {
                if (nodeDefinition instanceof AttributeDefinition && childElementNames.contains(AttributeDefinition.ELEMENT_NAME)) {
                    attributeDefinitions.add((AttributeDefinition) nodeDefinition);
                }
                if (nodeDefinition instanceof ElementDefinition && childElementNames.contains(ElementDefinition.ELEMENT_NAME)) {
                    elementDefinitions.add((ElementDefinition) nodeDefinition);
                }
                if (nodeDefinition instanceof ChoiceDefinition && childElementNames.contains(ChoiceDefinition.ELEMENT_NAME)) {
                    choiceDefinitions.add((ChoiceDefinition) nodeDefinition);
                }
                if (nodeDefinition instanceof FormReferenceDefinition && childElementNames.contains(FormReferenceDefinition.ELEMENT_NAME)) {
                    formReferenceDefinitions.add((FormReferenceDefinition) nodeDefinition);
                }
                if (nodeDefinition instanceof OtherNodeDefinition) {
                    otherNodeDefinitions.add((OtherNodeDefinition) nodeDefinition);
                }
            }
        }
        _attributeDefinitions = Collections.unmodifiableList(attributeDefinitions);
        _elementDefinitions = Collections.unmodifiableList(elementDefinitions);
        _choiceDefinitions = Collections.unmodifiableList(choiceDefinitions);
        _formReferenceDefinitions = Collections.unmodifiableList(formReferenceDefinitions);
        _otherNodeDefinitions = Collections.unmodifiableList(otherNodeDefinitions);
        if (otherAttributes == null) {
            _otherAttributes = Collections.unmodifiableMap(new HashMap<String, String>());
        } else {
            Map<String, String> filteredOtherAttributes = new HashMap<>(otherAttributes);
            filteredOtherAttributes.keySet().removeAll(attributeNames);
            _otherAttributes = Collections.unmodifiableMap(filteredOtherAttributes);
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
