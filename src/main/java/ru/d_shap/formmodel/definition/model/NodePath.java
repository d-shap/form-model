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

import ru.d_shap.formmodel.definition.Messages;

/**
 * Node path in the form definition.
 *
 * @author Dmitry Shapovalov
 */
public class NodePath {

    private final List<NodePathEntry> _nodePathEntries;

    /**
     * Create new object.
     *
     * @param representation the string representation of the node definition.
     */
    public NodePath(final String representation) {
        super();
        _nodePathEntries = new ArrayList<>();
        NodePathEntry nodePathEntry = new NodePathEntry(representation);
        _nodePathEntries.add(nodePathEntry);
    }

    /**
     * Create new object
     *
     * @param nodeDefinition the node definition.
     */
    public NodePath(final NodeDefinition nodeDefinition) {
        this(nodeDefinition.toString());
    }

    /**
     * Create new object
     *
     * @param nodePath       current node path.
     * @param representation the string representation of the node definition.
     */
    public NodePath(final NodePath nodePath, final String representation) {
        super();
        _nodePathEntries = new ArrayList<>(nodePath._nodePathEntries);
        NodePathEntry nodePathEntry = new NodePathEntry(representation);
        _nodePathEntries.add(nodePathEntry);
    }

    /**
     * Create new object
     *
     * @param nodePath       current node path.
     * @param nodeDefinition the node definition.
     */
    public NodePath(final NodePath nodePath, final NodeDefinition nodeDefinition) {
        this(nodePath, nodeDefinition.toString());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < _nodePathEntries.size(); i++) {
            if (i > 0) {
                result.append(Messages.Representation.SEPARATOR);
            }
            result.append(_nodePathEntries.get(i));
        }
        return result.toString();
    }

}
