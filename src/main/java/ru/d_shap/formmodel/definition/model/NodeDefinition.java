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

/**
 * Node definition is the base class for all form definition elements.
 *
 * @author Dmitry Shapovalov
 */
public class NodeDefinition {

    private final FormDefinitions _formDefinitions;

    private final NodeListDefinition _nodeListDefinition;

    private final AnyDefinition _anyDefinition;

    NodeDefinition(final FormDefinitions formDefinitions) {
        super();
        _formDefinitions = formDefinitions;
        _nodeListDefinition = new NodeListDefinition();
        _anyDefinition = new AnyDefinition();
    }

    final FormDefinitions getFormDefinitions() {
        return _formDefinitions;
    }

    final NodeListDefinition getNodeListDefinition() {
        return _nodeListDefinition;
    }

    final AnyDefinition getAnyDefinition() {
        return _anyDefinition;
    }

}
