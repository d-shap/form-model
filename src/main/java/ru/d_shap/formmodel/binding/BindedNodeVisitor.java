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

/**
 * Visitor for the binded nodes.
 *
 * @param <E> generic type of the binded element.
 * @param <R> generic type of the binded form reference.
 * @param <B> generic type of the binding data.
 * @author Dmitry Shapovalov
 */
public interface BindedNodeVisitor<E extends BindedElement<E, R, B>, R extends BindedFormReference<E, R, B>, B> {

    /**
     * Visit the binded node.
     *
     * @param bindedNode the binded node.
     */
    void visit(BindedNode<E, R, B> bindedNode);

}
