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
 * Node definition to define the reference to the form definition.
 *
 * @author Dmitry Shapovalov
 */
public final class FormReferenceDefinition implements NodeDefinition {

    public static final String ELEMENT_NAME = "formReference";

    public static final String ATTRIBUTE_GROUP = "group";

    public static final String ATTRIBUTE_ID = "id";

    public static final Set<String> CHILD_ELEMENT_NAMES;

    static {
        CHILD_ELEMENT_NAMES = Collections.unmodifiableSet(new HashSet<String>());
    }

    public static final Set<String> ATTRIBUTE_NAMES;

    static {
        Set<String> attributeNames = new HashSet<>();
        attributeNames.add(ATTRIBUTE_GROUP);
        attributeNames.add(ATTRIBUTE_ID);
        ATTRIBUTE_NAMES = Collections.unmodifiableSet(attributeNames);
    }

    private final String _group;

    private final String _id;

    private final NodeData _nodeData;

    /**
     * Create new object.
     *
     * @param group           the form reference's group.
     * @param id              the form reference's ID.
     * @param nodeDefinitions the form reference's node definitions.
     * @param otherAttributes the form reference's other attributes.
     */
    public FormReferenceDefinition(final String group, final String id, final List<NodeDefinition> nodeDefinitions, final Map<String, String> otherAttributes) {
        super();
        _group = NullValueHelper.getValue(group);
        _id = NullValueHelper.getValue(id);
        _nodeData = new NodeData(nodeDefinitions, CHILD_ELEMENT_NAMES, otherAttributes, ATTRIBUTE_NAMES);
    }

    /**
     * Get the form reference's group.
     *
     * @return the form reference's group.
     */
    public String getGroup() {
        return _group;
    }

    /**
     * Get the form reference's ID.
     *
     * @return the form reference's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the form reference's other child node definitions.
     *
     * @return the form reference's other child node definitions.
     */
    public List<OtherNodeDefinition> getOtherNodeDefinitions() {
        return _nodeData.getOtherNodeDefinitions();
    }

    /**
     * Get the form reference's all child node definitions.
     *
     * @return the form reference's all child node definitions.
     */
    public List<NodeDefinition> getAllNodeDefinitions() {
        return _nodeData.getAllNodeDefinitions();
    }

    /**
     * Get the form reference's other attribute names.
     *
     * @return the form reference's other attribute names.
     */
    public Set<String> getOtherAttributeNames() {
        return _nodeData.getOtherAttributeNames();
    }

    /**
     * Get the form reference's other attribute value for the specified other attribute name.
     *
     * @param otherAttributeName the specified other attribute name.
     *
     * @return the form reference's other attribute value.
     */
    public String getOtherAttributeValue(final String otherAttributeName) {
        return _nodeData.getOtherAttributeValue(otherAttributeName);
    }

    @Override
    public String toString() {
        return Messages.Representation.getFormReferenceDefinitionRepresentation(_group, _id);
    }

}
