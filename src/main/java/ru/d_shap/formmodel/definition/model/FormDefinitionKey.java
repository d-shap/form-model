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
 * Form definition key.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionKey {

    private final String _group;

    private final String _id;

    FormDefinitionKey(final String group, final String id) {
        super();
        _group = group;
        _id = id;
    }

    String getGroup() {
        return _group;
    }

    String getId() {
        return _id;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FormDefinitionKey)) {
            return false;
        }
        FormDefinitionKey otherKey = (FormDefinitionKey) other;
        if (_group == null && otherKey._group != null) {
            return false;
        }
        if (_group != null && !_group.equals(otherKey._group)) {
            return false;
        }
        return _id.equals(otherKey._id);
    }

    @Override
    public int hashCode() {
        int result = _id.hashCode();
        if (_group != null) {
            result = result * 13 + _group.hashCode();
        }
        return result;
    }

}
