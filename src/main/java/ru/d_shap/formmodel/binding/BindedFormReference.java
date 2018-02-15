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

import ru.d_shap.formmodel.definition.FormDefinition;

/**
 * Binded form reference.
 *
 * @author Dmitry Shapovalov
 */
public final class BindedFormReference extends BindedNode {

    private final FormDefinition _formDefinition;

    private final List<BindedNode> _childBindedNodes;

    /**
     * Create new object.
     *
     * @param formDefinition the form definition.
     */
    BindedFormReference(final FormDefinition formDefinition) {
        super();
        _formDefinition = formDefinition;
        _childBindedNodes = new ArrayList<>();
    }

    /**
     * Get the form definition.
     *
     * @return the form definition.
     */
    public FormDefinition getFormDefinition() {
        return _formDefinition;
    }

    /**
     * Get the child binded nodes.
     *
     * @return the child binded nodes.
     */
    public List<BindedNode> getChildBindedNodes() {
        return _childBindedNodes;
    }

}
