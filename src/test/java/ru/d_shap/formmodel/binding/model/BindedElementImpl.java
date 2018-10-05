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
package ru.d_shap.formmodel.binding.model;

/**
 * Implementation of the {@link BindedElement}.
 *
 * @author Dmitry Shapovalov
 */
public final class BindedElementImpl implements BindedElement {

    private final String _representation;

    private final int _index;

    /**
     * Create new object.
     *
     * @param representation the string representation of the element.
     * @param index          the index of the element.
     */
    public BindedElementImpl(final String representation, final int index) {
        super();
        _representation = representation;
        _index = index;
    }

    @Override
    public String toString() {
        return "Element: " + _representation + "[" + _index + "]";
    }

}
