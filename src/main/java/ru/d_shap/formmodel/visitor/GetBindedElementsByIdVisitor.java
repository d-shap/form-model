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
package ru.d_shap.formmodel.visitor;

import java.util.ArrayList;
import java.util.List;

import ru.d_shap.formmodel.binding.BindedElement;
import ru.d_shap.formmodel.binding.BindedFormReference;
import ru.d_shap.formmodel.binding.BindedNode;
import ru.d_shap.formmodel.binding.BindedNodeVisitor;

/**
 * Binded node visitor to get the binded elements with the specified id.
 *
 * @param <E> generic type of the binded element.
 * @param <R> generic type of the binded form reference.
 * @param <B> generic type of the binding data.
 * @author Dmitry Shapovalov
 */
public final class GetBindedElementsByIdVisitor<E extends BindedElement<E, R, B>, R extends BindedFormReference<E, R, B>, B> implements BindedNodeVisitor<E, R, B> {

    private final String _id;

    private final List<E> _bindedElements;

    /**
     * Create new object.
     *
     * @param id the element's ID.
     */
    public GetBindedElementsByIdVisitor(final String id) {
        super();
        _id = id;
        _bindedElements = new ArrayList<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void visit(final BindedNode<E, R, B> bindedNode) {
        if (bindedNode instanceof BindedElement) {
            String id = ((BindedElement<E, R, B>) bindedNode).getId();
            if (id != null && id.equals(_id)) {
                _bindedElements.add((E) bindedNode);
            }
        }
    }

    /**
     * Get the result.
     *
     * @return the result.
     */
    public List<E> getBindedElements() {
        return _bindedElements;
    }

}
