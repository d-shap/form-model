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
package ru.d_shap.formmodel.definition;

import java.util.ArrayList;
import java.util.List;

public final class ElementDefinition extends NodeDefinition {

    private final String _id;

    private final String _lookup;

    private final ElementDefinitionType _elementDefinitionType;

    private final List<NodeDefinition> _childNodeDefinitions;

    public ElementDefinition(final String id, final String lookup, final ElementDefinitionType elementDefinitionType) {
        super();
        _id = id;
        _lookup = lookup;
        _elementDefinitionType = elementDefinitionType;
        _childNodeDefinitions = new ArrayList<>();
    }

    public String getId() {
        return _id;
    }

    public String getLookup() {
        return _lookup;
    }

    public ElementDefinitionType getElementDefinitionType() {
        return _elementDefinitionType;
    }

    public List<NodeDefinition> getChildNodeDefinitions() {
        return _childNodeDefinitions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ELEMENT: {");
        if (_id != null) {
            result.append("id=").append(_id).append(",");
        }
        result.append("lookup=").append(_lookup).append(",");
        result.append("type=").append(_elementDefinitionType);
        if (!_childNodeDefinitions.isEmpty()) {
            result.append(",nodes=").append(_childNodeDefinitions);
        }
        result.append("}");
        return result.toString();
    }

}
