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

import ru.d_shap.formmodel.definition.ElementDefinition;

/**
 * Binded element.
 *
 * @param <B> binding object type.
 * @author Dmitry Shapovalov
 */
public class BindedElement<B> extends BindedNode {

    private final ElementDefinition _elementDefinition;

    private final B _bindingObject;

    private final List<BindedNode> _childBindedNodes;

    /**
     * Create new object.
     *
     * @param elementDefinition the element definition.
     * @param bindingObject     the binding object.
     */
    protected BindedElement(final ElementDefinition elementDefinition, final B bindingObject) {
        super();
        _elementDefinition = elementDefinition;
        _bindingObject = bindingObject;
        _childBindedNodes = new ArrayList<>();
    }

    /**
     * Get the element ID.
     *
     * @return the element ID.
     */
    public final String getId() {
        return _elementDefinition.getId();
    }

    /**
     * Get the element definition.
     *
     * @return the element definition.
     */
    public final ElementDefinition getElementDefinition() {
        return _elementDefinition;
    }

    /**
     * Get the binding object.
     *
     * @return the binding object.
     */
    public final B getBindingObject() {
        return _bindingObject;
    }

    /**
     * Get the child binded nodes.
     *
     * @return the child binded nodes.
     */
    public final List<BindedNode> getChildBindedNodes() {
        return _childBindedNodes;
    }

}
