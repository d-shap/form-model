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

/**
 * Choice definition defines the choice of elements.
 *
 * @author Dmitry Shapovalov
 */
public final class ChoiceDefinition implements NodeDefinition {

    public static final String ELEMENT_NAME = "choice";

    public static final String ATTRIBUTE_ID = "id";

    public static final String ATTRIBUTE_TYPE = "type";

    public static final Set<String> ATTRIBUTE_NAMES;

    static {
        Set<String> attributeNames = new HashSet<>();
        attributeNames.add(ATTRIBUTE_ID);
        attributeNames.add(ATTRIBUTE_TYPE);
        ATTRIBUTE_NAMES = Collections.unmodifiableSet(attributeNames);
    }

    private static final Set<String> CHILD_ELEMENT_NAMES;

    static {
        Set<String> childElementNames = new HashSet<>();
        childElementNames.add(ElementDefinition.ELEMENT_NAME);
        CHILD_ELEMENT_NAMES = Collections.unmodifiableSet(childElementNames);
    }

    private final String _id;

    private final CardinalityDefinition _cardinalityDefinition;

    private final NodeData _nodeData;

    /**
     * Create new object.
     *
     * @param id                    the choice's ID.
     * @param cardinalityDefinition the choice's cardinality.
     * @param nodeDefinitions       the choice's node definitions.
     * @param otherAttributes       the choice's other attributes.
     */
    public ChoiceDefinition(final String id, final CardinalityDefinition cardinalityDefinition, final List<NodeDefinition> nodeDefinitions, final Map<String, String> otherAttributes) {
        super();
        _id = id;
        _cardinalityDefinition = cardinalityDefinition;
        _nodeData = new NodeData(nodeDefinitions, otherAttributes);
    }

    /**
     * Get the choice's ID.
     *
     * @return the choice's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the choice's cardinality.
     *
     * @return the choice's cardinality.
     */
    public CardinalityDefinition getCardinalityDefinition() {
        return _cardinalityDefinition;
    }

    /**
     * Get the choice's element definitions.
     *
     * @return the choice's element definitions.
     */
    public List<ElementDefinition> getElementDefinitions() {
        return _nodeData.getElementDefinitions();
    }

    /**
     * Get the choice's other node definitions.
     *
     * @return the choice's other node definitions.
     */
    public List<OtherNodeDefinition> getOtherNodeDefinitions() {
        return _nodeData.getOtherNodeDefinitions();
    }

    /**
     * Get the choice's other attribute names.
     *
     * @return the choice's other attribute names.
     */
    public Set<String> getOtherAttributeNames() {
        return _nodeData.getOtherAttributeNames();
    }

    /**
     * Get the choice's other attribute value for the specified other attribute name.
     *
     * @param otherAttributeName the specified other attribute name.
     *
     * @return the choice's other attribute value.
     */
    public String getOtherAttributeValue(final String otherAttributeName) {
        return _nodeData.getOtherAttributeValue(otherAttributeName);
    }

}
