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

import ru.d_shap.formmodel.definition.ElementDefinition;

/**
 * Abstraction for the binded element.
 *
 * @param <E> generic type of the binded element.
 * @param <R> generic type of the binded form reference.
 * @param <B> generic type of the binding data.
 *
 * @author Dmitry Shapovalov
 */
public class BindedElement<E extends BindedElement<E, R, B>, R extends BindedFormReference<E, R, B>, B> extends BindedNode<E, R, B> {

    private final ElementDefinition _elementDefinition;

    private final B _bindingData;

    /**
     * Create new object.
     *
     * @param elementDefinition the element definition.
     * @param bindingData       the binding data.
     */
    protected BindedElement(final ElementDefinition elementDefinition, final B bindingData) {
        super();
        _elementDefinition = elementDefinition;
        _bindingData = bindingData;
    }

    /**
     * Get the element's ID.
     *
     * @return the element's ID.
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
     * Get the binding data.
     *
     * @return the binding data.
     */
    public final B getBindingData() {
        return _bindingData;
    }

}
