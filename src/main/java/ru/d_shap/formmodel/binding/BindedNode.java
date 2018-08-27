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

import ru.d_shap.formmodel.visitor.GetBindedElementsByIdVisitor;

/**
 * Base class for all binded form elements.
 *
 * @param <E> generic type of the binded element.
 * @param <R> generic type of the binded form reference.
 * @param <B> generic type of the binding data.
 *
 * @author Dmitry Shapovalov
 */
public class BindedNode<E extends BindedElement<E, R, B>, R extends BindedFormReference<E, R, B>, B> {

    private final List<BindedNode<E, R, B>> _bindedNodes;

    BindedNode() {
        super();
        _bindedNodes = new ArrayList<>();
    }

    /**
     * Get the child binded elements.
     *
     * @return the child binded elements.
     */
    public final List<E> getChildBindedElements() {
        return getChildBindedElements(true);
    }

    /**
     * Get the child binded elements.
     *
     * @param expandFormReferences true to get binded elements from child form references, false otherwise.
     *
     * @return the child binded elements.
     */
    @SuppressWarnings("unchecked")
    public final List<E> getChildBindedElements(final boolean expandFormReferences) {
        List<E> bindedElements = new ArrayList<>();
        for (BindedNode<E, R, B> bindedNode : _bindedNodes) {
            if (bindedNode instanceof BindedElement) {
                bindedElements.add((E) bindedNode);
            }
            if (expandFormReferences && bindedNode instanceof BindedFormReference) {
                List<E> referenceBindedElements = bindedNode.getChildBindedElements(expandFormReferences);
                bindedElements.addAll(referenceBindedElements);
            }
        }
        return bindedElements;
    }

    /**
     * Get the child binded nodes.
     *
     * @return the child binded nodes.
     */
    public final List<BindedNode<E, R, B>> getChildBindedNodes() {
        return _bindedNodes;
    }

    /**
     * Visit every binded node with the specified visitor.
     *
     * @param bindedNodeVisitor the specified visitor.
     */
    public final void visit(final BindedNodeVisitor<E, R, B> bindedNodeVisitor) {
        visit(_bindedNodes, bindedNodeVisitor);
    }

    private void visit(final List<BindedNode<E, R, B>> bindedNodes, final BindedNodeVisitor<E, R, B> bindedNodeVisitor) {
        for (BindedNode<E, R, B> bindedNode : bindedNodes) {
            bindedNodeVisitor.visit(bindedNode);
            visit(bindedNode._bindedNodes, bindedNodeVisitor);
        }
    }

    /**
     * Get the first binded element with the specified element's ID.
     *
     * @param id the specified element's ID.
     *
     * @return the first binded element.
     */
    public final E lookupBindedElement(final String id) {
        List<E> bindedElements = lookupBindedElements(id);
        if (bindedElements.isEmpty()) {
            return null;
        } else {
            return bindedElements.get(0);
        }
    }

    /**
     * Get all binded elements with the specified element's ID.
     *
     * @param id the specified element's ID.
     *
     * @return all binded elements.
     */
    public final List<E> lookupBindedElements(final String id) {
        GetBindedElementsByIdVisitor<E, R, B> visitor = new GetBindedElementsByIdVisitor<>(id);
        visit(visitor);
        return visitor.getBindedElements();
    }

}
