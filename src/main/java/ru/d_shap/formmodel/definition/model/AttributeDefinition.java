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

/**
 * Node definition to define the element's attribute mapping.
 *
 * @author Dmitry Shapovalov
 */
public final class AttributeDefinition implements NodeDefinition {

    public static final String ELEMENT_NAME = "attribute";

    public static final String ATTRIBUTE_ID = "id";

    public static final String ATTRIBUTE_LOOKUP = "lookup";

    public static final String ATTRIBUTE_TYPE = "type";

    public static final Set<String> ATTRIBUTE_NAMES;

    static {
        Set<String> attributeNames = new HashSet<>();
        attributeNames.add(ATTRIBUTE_ID);
        attributeNames.add(ATTRIBUTE_LOOKUP);
        attributeNames.add(ATTRIBUTE_TYPE);
        ATTRIBUTE_NAMES = Collections.unmodifiableSet(attributeNames);
    }

    public static final Set<String> CHILD_ELEMENT_NAMES;

    static {
        CHILD_ELEMENT_NAMES = Collections.unmodifiableSet(new HashSet<String>());
    }

    private final String _id;

    private final String _lookup;

    private final CardinalityDefinition _cardinalityDefinition;

    private final NodeData _nodeData;

    /**
     * Create new object.
     *
     * @param id                    the attribute's ID.
     * @param lookup                the attribute's lookup string.
     * @param cardinalityDefinition the attribute's cardinality.
     * @param nodeDefinitions       the attribute's node definitions.
     * @param otherAttributes       the attribute's other attributes.
     */
    public AttributeDefinition(final String id, final String lookup, final CardinalityDefinition cardinalityDefinition, final List<NodeDefinition> nodeDefinitions, final Map<String, String> otherAttributes) {
        super();
        _id = id;
        _lookup = lookup;
        _cardinalityDefinition = cardinalityDefinition;
        _nodeData = new NodeData(nodeDefinitions, CHILD_ELEMENT_NAMES, otherAttributes, ATTRIBUTE_NAMES);
    }

    /**
     * Get the attribute's ID.
     *
     * @return the attribute's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the attribute's lookup string.
     *
     * @return the attribute's lookup string.
     */
    public String getLookup() {
        return _lookup;
    }

    /**
     * Get the attribute's cardinality.
     *
     * @return the attribute's cardinality.
     */
    public CardinalityDefinition getCardinalityDefinition() {
        return _cardinalityDefinition;
    }

    /**
     * Get the attribute's other node definitions.
     *
     * @return the attribute's other node definitions.
     */
    public List<OtherNodeDefinition> getOtherNodeDefinitions() {
        return _nodeData.getOtherNodeDefinitions();
    }

    /**
     * Get the attribute's other attribute names.
     *
     * @return the attribute's other attribute names.
     */
    public Set<String> getOtherAttributeNames() {
        return _nodeData.getOtherAttributeNames();
    }

    /**
     * Get the attribute's other attribute value for the specified other attribute name.
     *
     * @param otherAttributeName the specified other attribute name.
     *
     * @return the attribute's other attribute value.
     */
    public String getOtherAttributeValue(final String otherAttributeName) {
        return _nodeData.getOtherAttributeValue(otherAttributeName);
    }

    @Override
    public String toString() {
        return Messages.Representation.getAttributeDefinitionRepresentation(_id);
    }

}
