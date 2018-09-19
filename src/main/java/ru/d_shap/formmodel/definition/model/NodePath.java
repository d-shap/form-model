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

import ru.d_shap.formmodel.Messages;

/**
 * Path of the nodes within the form.
 *
 * @author Dmitry Shapovalov
 */
public final class NodePath {

    private final List<NodePathEntry> _nodePathEntries;

    /**
     * Create new object.
     */
    public NodePath() {
        super();
        _nodePathEntries = new ArrayList<>();
    }

    /**
     * Create new object.
     *
     * @param representation the string representation of the node definition.
     */
    public NodePath(final String representation) {
        this();
        NodePathEntry nodePathEntry = new NodePathEntry(representation);
        _nodePathEntries.add(nodePathEntry);
    }

    /**
     * Create new object.
     *
     * @param nodeDefinition the node definition.
     */
    public NodePath(final NodeDefinition nodeDefinition) {
        this(nodeDefinition.toString());
    }

    /**
     * Create new object.
     *
     * @param nodePath       the parent node path.
     * @param representation the string representation of the node definition.
     */
    public NodePath(final NodePath nodePath, final String representation) {
        this();
        _nodePathEntries.addAll(nodePath._nodePathEntries);
        NodePathEntry nodePathEntry = new NodePathEntry(representation);
        _nodePathEntries.add(nodePathEntry);
    }

    /**
     * Create new object.
     *
     * @param nodePath       the parent node path.
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
                result.append(Messages.SEPARATOR);
            }
            result.append(_nodePathEntries.get(i));
        }
        return result.toString();
    }

}
