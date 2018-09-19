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

import ru.d_shap.formmodel.Messages;

/**
 * Form definition key.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionKey {

    private final String _group;

    private final String _id;

    /**
     * Create new object.
     *
     * @param group the form's group.
     * @param id    the form's ID.
     */
    public FormDefinitionKey(final String group, final String id) {
        super();
        if (group == null) {
            _group = "";
        } else {
            _group = group;
        }
        _id = id;
    }

    /**
     * Create new object.
     *
     * @param formDefinition the form definition.
     */
    public FormDefinitionKey(final FormDefinition formDefinition) {
        this(formDefinition.getGroup(), formDefinition.getId());
    }

    /**
     * Create new object.
     *
     * @param formReferenceDefinition the form reference definition.
     */
    public FormDefinitionKey(final FormReferenceDefinition formReferenceDefinition) {
        this(formReferenceDefinition.getGroup(), formReferenceDefinition.getId());
    }

    /**
     * Get the form's group.
     *
     * @return the form's group.
     */
    public String getGroup() {
        return _group;
    }

    /**
     * Get the form's ID.
     *
     * @return the form's ID.
     */
    public String getId() {
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
        return _group.equals(((FormDefinitionKey) other)._group) && _id.equals(((FormDefinitionKey) other)._id);
    }

    @Override
    public int hashCode() {
        return _group.hashCode() * 13 + _id.hashCode();
    }

    @Override
    public String toString() {
        return Messages.Representation.getIdRepresentation(_group, _id);
    }

}
