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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.NullValueHelper;

/**
 * Node definition to define the only one child element.
 *
 * @author Dmitry Shapovalov
 */
public final class SingleElementDefinition implements NodeDefinition {

    public static final String ELEMENT_NAME = "single-element";

    public static final String ATTRIBUTE_ID = "id";

    public static final String ATTRIBUTE_TYPE = "type";

    public static final Set<String> CHILD_ELEMENT_NAMES;

    static {
        Set<String> childElementNames = new HashSet<>();
        childElementNames.add(ElementDefinition.ELEMENT_NAME);
        childElementNames.add(SingleElementDefinition.ELEMENT_NAME);
        CHILD_ELEMENT_NAMES = Collections.unmodifiableSet(childElementNames);
    }

    public static final Set<String> ATTRIBUTE_NAMES;

    static {
        Set<String> attributeNames = new HashSet<>();
        attributeNames.add(ATTRIBUTE_ID);
        attributeNames.add(ATTRIBUTE_TYPE);
        ATTRIBUTE_NAMES = Collections.unmodifiableSet(attributeNames);
    }

    private final String _id;

    private final CardinalityDefinition _cardinalityDefinition;

    private final NodeData _nodeData;

    /**
     * Create new object.
     *
     * @param id                    the single element's ID.
     * @param cardinalityDefinition the single element's cardinality.
     * @param nodeDefinitions       the single element's node definitions.
     * @param otherAttributes       the single element's other attributes.
     */
    public SingleElementDefinition(final String id, final CardinalityDefinition cardinalityDefinition, final List<NodeDefinition> nodeDefinitions, final Map<String, String> otherAttributes) {
        super();
        _id = NullValueHelper.getValue(id);
        _cardinalityDefinition = cardinalityDefinition;
        _nodeData = new NodeData(nodeDefinitions, CHILD_ELEMENT_NAMES, otherAttributes, ATTRIBUTE_NAMES);
    }

    /**
     * Get the single element's ID.
     *
     * @return the single element's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the single element's cardinality.
     *
     * @return the single element's cardinality.
     */
    public CardinalityDefinition getCardinalityDefinition() {
        return _cardinalityDefinition;
    }

    /**
     * Get the single element's child element definitions.
     *
     * @return the single element's child element definitions.
     */
    public List<ElementDefinition> getElementDefinitions() {
        return _nodeData.getElementDefinitions();
    }

    /**
     * Get the single element's child single element definitions.
     *
     * @return the single element's child single element definitions.
     */
    public List<SingleElementDefinition> getSingleElementDefinitions() {
        return _nodeData.getSingleElementDefinitions();
    }

    /**
     * Get the single element's other child node definitions.
     *
     * @return the single element's other child node definitions.
     */
    public List<OtherNodeDefinition> getOtherNodeDefinitions() {
        return _nodeData.getOtherNodeDefinitions();
    }

    /**
     * Get the single element's all child node definitions.
     *
     * @return the single element's all child node definitions.
     */
    public List<NodeDefinition> getAllNodeDefinitions() {
        return _nodeData.getAllNodeDefinitions();
    }

    /**
     * Get the single element's other attribute names.
     *
     * @return the single element's other attribute names.
     */
    public Set<String> getOtherAttributeNames() {
        return _nodeData.getOtherAttributeNames();
    }

    /**
     * Get the single element's other attribute value for the specified other attribute name.
     *
     * @param otherAttributeName the specified other attribute name.
     *
     * @return the single element's other attribute value.
     */
    public String getOtherAttributeValue(final String otherAttributeName) {
        return _nodeData.getOtherAttributeValue(otherAttributeName);
    }

    @Override
    public String toString() {
        return Messages.Representation.getSingleElementDefinitionRepresentation(_id);
    }

}
