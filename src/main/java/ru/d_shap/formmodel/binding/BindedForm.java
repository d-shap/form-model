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
 * Binded form.
 *
 * @param <E> binded element type.
 * @param <B> binding object type.
 * @author Dmitry Shapovalov
 */
public class BindedForm<E extends BindedElement<B>, B> {

    private final FormDefinition _formDefinition;

    private final List<BindedNode> _bindedNodes;

    /**
     * Create new object.
     *
     * @param formDefinition the form definition.
     */
    protected BindedForm(final FormDefinition formDefinition) {
        super();
        _formDefinition = formDefinition;
        _bindedNodes = new ArrayList<>();
    }

    /**
     * Get the form definition.
     *
     * @return the form definition.
     */
    public final FormDefinition getFormDefinition() {
        return _formDefinition;
    }

    /**
     * Get the form binded nodes.
     *
     * @return the form binded nodes.
     */
    public final List<BindedNode> getBindedNodes() {
        return _bindedNodes;
    }

    /**
     * Get the first binded element with the specified id.
     *
     * @param id the specified id.
     * @return the first binded element.
     */
    public final E getBindedElement(final String id) {
        List<E> bindedElements = getBindedElements(id);
        if (bindedElements.isEmpty()) {
            return null;
        } else {
            return bindedElements.get(0);
        }
    }

    /**
     * Get the binded elements with the specified id.
     *
     * @param id the specified id.
     * @return the binded elements.
     */
    public final List<E> getBindedElements(final String id) {
        GetBindedElementsByIdVisitor<E, B> visitor = new GetBindedElementsByIdVisitor<>(id);
        visit(visitor);
        return visitor._bindedElements;
    }

    /**
     * Visit every binded node with the specified visitor.
     *
     * @param bindedNodeVisitor the specified visitor.
     */
    public final void visit(final BindedNodeVisitor bindedNodeVisitor) {
        visit(_bindedNodes, bindedNodeVisitor);
    }

    private void visit(final List<BindedNode> bindedNodes, final BindedNodeVisitor bindedNodeVisitor) {
        for (BindedNode bindedNode : bindedNodes) {
            bindedNodeVisitor.visit(bindedNode);
            if (bindedNode instanceof BindedElement) {
                List<BindedNode> childBindedNodes = ((BindedElement<?>) bindedNode).getChildBindedNodes();
                visit(childBindedNodes, bindedNodeVisitor);
            }
            if (bindedNode instanceof BindedFormReference) {
                List<BindedNode> childBindedNodes = ((BindedFormReference) bindedNode).getChildBindedNodes();
                visit(childBindedNodes, bindedNodeVisitor);
            }
        }
    }

    /**
     * Binded node visitor to get the binded elements with the specified id.
     *
     * @param <E> binded element type.
     * @param <B> binding object type.
     * @author Dmitry Shapovalov
     */
    private static final class GetBindedElementsByIdVisitor<E extends BindedElement<B>, B> implements BindedNodeVisitor {

        private final String _id;

        private final List<E> _bindedElements;

        GetBindedElementsByIdVisitor(final String id) {
            super();
            _id = id;
            _bindedElements = new ArrayList<>();
        }

        @Override
        @SuppressWarnings("unchecked")
        public void visit(final BindedNode bindedNode) {
            if (bindedNode instanceof BindedElement) {
                String id = ((BindedElement<?>) bindedNode).getId();
                if (id != null && id.equals(_id)) {
                    _bindedElements.add((E) bindedNode);
                }
            }
        }

    }

}
