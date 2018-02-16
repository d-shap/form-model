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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Abstraction for the element definition.
 * </p>
 * <p>
 * Element definition is a meaningful element of the form. This can be buttons or hyperlinks, text edit
 * fields or areas, menus or menu items, labels and so on.
 * </p>
 * <p>
 * Binded element can be used to access properties (for example, the text edit field value) or to perform
 * actions (for example, to press the button).
 * </p>
 *
 * @author Dmitry Shapovalov
 */
public final class ElementDefinition extends NodeDefinition {

    static final String ELEMENT_NAME = "element";

    static final String ATTRIBUTE_ID = "id";

    static final String ATTRIBUTE_LOOKUP = "lookup";

    static final String ATTRIBUTE_TYPE = "type";

    static final Set<String> DEFINED_ATTRIBUTE_NAMES;

    static {
        Set<String> attributeNames = new HashSet<>();
        attributeNames.add(ATTRIBUTE_ID);
        attributeNames.add(ATTRIBUTE_LOOKUP);
        attributeNames.add(ATTRIBUTE_TYPE);
        DEFINED_ATTRIBUTE_NAMES = Collections.unmodifiableSet(attributeNames);
    }

    private final String _id;

    private final String _lookup;

    private final ElementDefinitionType _elementDefinitionType;

    private final Map<String, String> _additionalAttributes;

    private final List<NodeDefinition> _childNodeDefinitions;

    ElementDefinition(final String id, final String lookup, final ElementDefinitionType elementDefinitionType, final Map<String, String> additionalAttributes, final List<NodeDefinition> childNodeDefinitions) {
        super();
        _id = id;
        _lookup = lookup;
        _elementDefinitionType = elementDefinitionType;
        Map<String, String> additionalAttributesCopy = new HashMap<>(additionalAttributes);
        _additionalAttributes = Collections.unmodifiableMap(additionalAttributesCopy);
        List<NodeDefinition> childNodeDefinitionsCopy = new ArrayList<>(childNodeDefinitions);
        _childNodeDefinitions = Collections.unmodifiableList(childNodeDefinitionsCopy);
    }

    /**
     * Check if the element's ID is defined or not.
     *
     * @return true if the element's ID is defined, false otherwise.
     */
    public boolean isIdDefined() {
        return _id != null;
    }

    /**
     * Get the element's ID.
     *
     * @return the element's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Check if the element's lookup string is defined or not.
     *
     * @return true if the element's lookup string is defined, false otherwise.
     */
    public boolean isLookupDefined() {
        return _lookup != null;
    }

    /**
     * Get the element's lookup string.
     *
     * @return the element's lookup string.
     */
    public String getLookup() {
        return _lookup;
    }

    /**
     * Check if the element is mandatory or not.
     *
     * @return true if the element is mandatory, false otherwise.
     */
    public boolean isMandatory() {
        return _elementDefinitionType == ElementDefinitionType.MANDATORY || _elementDefinitionType == ElementDefinitionType.MANDATORY_MULTIPLE;
    }

    /**
     * Check if the element is optional or not.
     *
     * @return true if the element is optional, false otherwise.
     */
    public boolean isOptional() {
        return _elementDefinitionType == ElementDefinitionType.OPTIONAL || _elementDefinitionType == ElementDefinitionType.OPTIONAL_MULTIPLE;
    }

    /**
     * Check if the element is forbidden or not.
     *
     * @return true if the element is forbidden, false otherwise.
     */
    public boolean isForbidden() {
        return _elementDefinitionType == ElementDefinitionType.FORBIDDEN;
    }

    /**
     * Check if the element is multiple or not.
     *
     * @return true if the element is multiple, false otherwise.
     */
    public boolean isMultiple() {
        return _elementDefinitionType == ElementDefinitionType.MANDATORY_MULTIPLE || _elementDefinitionType == ElementDefinitionType.OPTIONAL_MULTIPLE;
    }

    /**
     * Get the element's definition type.
     *
     * @return the element's definition type.
     */
    public ElementDefinitionType getElementDefinitionType() {
        return _elementDefinitionType;
    }

    /**
     * Get the element's additional attribute value for the specified additional attribute name.
     *
     * @param additionalAttributeName the specified additional attribute name.
     * @return the element's additional attribute value.
     */
    public String getAdditionalAttribute(final String additionalAttributeName) {
        return _additionalAttributes.get(additionalAttributeName);
    }

    /**
     * Get the element's additional attributes.
     *
     * @return the element's additional attributes.
     */
    public Map<String, String> getAdditionalAttributes() {
        return _additionalAttributes;
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
