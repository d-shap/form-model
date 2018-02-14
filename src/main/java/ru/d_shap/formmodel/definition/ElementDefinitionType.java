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
package ru.d_shap.formmodel.definition;

/**
 * Element definition type.
 *
 * @author Dmitry Shapovalov
 */
public enum ElementDefinitionType {

    MANDATORY("mandatory"),

    MANDATORY_MULTIPLE("mandatory multiple"),

    OPTIONAL("optional"),

    OPTIONAL_MULTIPLE("optional multiple"),

    FORBIDDEN("forbidden");

    private final String _attributeValue;

    ElementDefinitionType(final String attributeValue) {
        _attributeValue = attributeValue;
    }

    /**
     * Get the element definition type for the specified attribute value.
     *
     * @param attributeValue the specified attribute value.
     * @return the element definition type.
     */
    static ElementDefinitionType getElementDefinitionType(final String attributeValue) {
        for (ElementDefinitionType elementDefinitionType : values()) {
            if (elementDefinitionType._attributeValue.equals(attributeValue)) {
                return elementDefinitionType;
            }
        }
        return null;
    }

}
