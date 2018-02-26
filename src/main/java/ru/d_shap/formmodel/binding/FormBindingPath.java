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
package ru.d_shap.formmodel.binding;

import java.util.ArrayList;
import java.util.List;

import ru.d_shap.formmodel.definition.NodeDefinition;

/**
 * Class contains the full path to the binding element.
 *
 * @author Dmitry Shapovalov
 */
final class FormBindingPath {

    private final List<NodeDefinition> _nodeDefinitions;

    FormBindingPath(final NodeDefinition nodeDefinition) {
        super();
        _nodeDefinitions = new ArrayList<>();
        _nodeDefinitions.add(nodeDefinition);
    }

    FormBindingPath(final FormBindingPath formBindingPath, final NodeDefinition nodeDefinition) {
        super();
        _nodeDefinitions = new ArrayList<>();
        _nodeDefinitions.addAll(formBindingPath._nodeDefinitions);
        _nodeDefinitions.add(nodeDefinition);
    }

    List<NodeDefinition> getNodeDefinitions() {
        return _nodeDefinitions;
    }

}
