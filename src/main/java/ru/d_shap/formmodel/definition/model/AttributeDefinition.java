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
 * Attribute definition defines the element's attribute mapping.
 *
 * @author Dmitry Shapovalov
 */
public final class AttributeDefinition {

    private final String _id;

    private final String _lookup;

    private final CardinalityDefinition _cardinalityDefinition;

    /**
     * Create new object.
     *
     * @param id                    the attribute's ID.
     * @param lookup                the attribute's lookup string.
     * @param cardinalityDefinition the attribute's cardinality.
     */
    public AttributeDefinition(final String id, final String lookup, final CardinalityDefinition cardinalityDefinition) {
        super();
        _id = id;
        _lookup = lookup;
        _cardinalityDefinition = cardinalityDefinition;
    }

    /**
     * Get the attribute's ID.
     *
     * @return the attribute's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the attribute's lookup string.
     *
     * @return the attribute's lookup string.
     */
    public String getLookup() {
        return _lookup;
    }

    /**
     * Get the attribute's cardinality.
     *
     * @return the attribute's cardinality.
     */
    public CardinalityDefinition getCardinalityDefinition() {
        return _cardinalityDefinition;
    }

}
