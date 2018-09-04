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
 * The node's cardinality.
 *
 * @author Dmitry Shapovalov
 */
public enum CardinalityDefinition {

    REQUIRED("required"),

    REQUIRED_MULTIPLE("required+"),

    OPTIONAL("optional"),

    OPTIONAL_MULTIPLE("optional+"),

    PROHIBITED("prohibited");

    private final String _cardinality;

    CardinalityDefinition(final String cardinality) {
        _cardinality = cardinality;
    }

    /**
     * Get the cardinality.
     *
     * @return the cardinality.
     */
    public String getCardinality() {
        return _cardinality;
    }

    /**
     * Get the cardinality definition for the specified cardinality.
     *
     * @param cardinality the specified cardinality.
     *
     * @return the cardinality definition.
     */
    public static CardinalityDefinition getCardinalityDefinition(final String cardinality) {
        for (CardinalityDefinition cardinalityDefinition : values()) {
            if (cardinalityDefinition._cardinality.equals(cardinality)) {
                return cardinalityDefinition;
            }
        }
        return null;
    }

}
