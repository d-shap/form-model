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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Element definition.
 *
 * @author Dmitry Shapovalov
 */
public final class ElementDefinition extends NodeDefinition {

    static final String ELEMENT_NAME = "element";

    static final String ATTRIBUTE_ID = "id";

    static final String ATTRIBUTE_LOOKUP = "lookup";

    static final String ATTRIBUTE_TYPE = "type";

    private final String _id;

    private final String _lookup;

    private final ElementDefinitionType _elementDefinitionType;

    private final List<NodeDefinition> _childNodeDefinitions;

    ElementDefinition(final String id, final String lookup, final ElementDefinitionType elementDefinitionType, final List<NodeDefinition> childNodeDefinitions) {
        super();
        _id = id;
        _lookup = lookup;
        _elementDefinitionType = elementDefinitionType;
        List<NodeDefinition> childNodeDefinitionsCopy = new ArrayList<>(childNodeDefinitions);
        _childNodeDefinitions = Collections.unmodifiableList(childNodeDefinitionsCopy);
    }

    /**
     * Get the element ID.
     *
     * @return the element ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the element lookup string.
     *
     * @return the element lookup string.
     */
    public String getLookup() {
        return _lookup;
    }

    /**
     * Get the element definition type.
     *
     * @return the element definition type.
     */
    public ElementDefinitionType getElementDefinitionType() {
        return _elementDefinitionType;
    }

    /**
     * Get the element's child node definitions.
     *
     * @return the element's child node definitions.
     */
    public List<NodeDefinition> getChildNodeDefinitions() {
        return _childNodeDefinitions;
    }

}
