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

public final class FormDefinition {

    private final String _id;

    private final List<NodeDefinition> _nodeDefinitions;

    public FormDefinition(final String id) {
        super();
        _id = id;
        _nodeDefinitions = new ArrayList<>();
    }

    public String getId() {
        return _id;
    }

    public List<NodeDefinition> getNodeDefinitions() {
        return _nodeDefinitions;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("FORM: {");
        result.append("id=").append(_id).append(",");
        result.append("nodes=").append(_nodeDefinitions);
        result.append("}");
        return result.toString();
    }

}
