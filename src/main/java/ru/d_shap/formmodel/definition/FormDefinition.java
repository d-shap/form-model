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
 * Abstraction for the form definition.
 * </p>
 * <p>
 * Form is a set of elements. Form is used to obtain inputs from the user and to output the results.
 * </p>
 * <p>
 * Binded form can be used to access the binded elements, defined in this form.
 * </p>
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinition {

    static final String ELEMENT_NAME = "form";

    static final String ATTRIBUTE_ID = "id";

    static final Set<String> DEFINED_ATTRIBUTE_NAMES;

    static {
        Set<String> attributeNames = new HashSet<>();
        attributeNames.add(ATTRIBUTE_ID);
        DEFINED_ATTRIBUTE_NAMES = Collections.unmodifiableSet(attributeNames);
    }

    private final String _id;

    private final Map<String, String> _additionalAttributes;

    private final List<NodeDefinition> _nodeDefinitions;

    private final Object _source;

    FormDefinition(final String id, final Map<String, String> additionalAttributes, final List<NodeDefinition> nodeDefinitions, final Object source) {
        super();
        _id = id;
        Map<String, String> additionalAttributesCopy = new HashMap<>(additionalAttributes);
        _additionalAttributes = Collections.unmodifiableMap(additionalAttributesCopy);
        List<NodeDefinition> nodeDefinitionsCopy = new ArrayList<>(nodeDefinitions);
        _nodeDefinitions = Collections.unmodifiableList(nodeDefinitionsCopy);
        _source = source;
    }

    /**
     * Get the form's ID.
     *
     * @return the form's ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the form's additional attribute value for the specified additional attribute name.
     *
     * @param additionalAttributeName the specified additional attribute name.
     * @return the form's additional attribute value.
     */
    public String getAdditionalAttribute(final String additionalAttributeName) {
        return _additionalAttributes.get(additionalAttributeName);
    }

    /**
     * Get the form's additional attributes.
     *
     * @return the form's additional attributes.
     */
    public Map<String, String> getAdditionalAttributes() {
        return _additionalAttributes;
    }

    /**
     * Get the form's element definitions.
     *
     * @return the form's element definitions.
     */
    public List<ElementDefinition> getElementDefinitions() {
        List<ElementDefinition> elementDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : _nodeDefinitions) {
            if (nodeDefinition instanceof ElementDefinition) {
                elementDefinitions.add((ElementDefinition) nodeDefinition);
            }
        }
        return elementDefinitions;
    }

    /**
     * Get the form's form reference definitions.
     *
     * @return the form's form reference definitions.
     */
    public List<FormReferenceDefinition> getFormReferenceDefinitions() {
        List<FormReferenceDefinition> formReferenceDefinitions = new ArrayList<>();
        for (NodeDefinition nodeDefinition : _nodeDefinitions) {
            if (nodeDefinition instanceof FormReferenceDefinition) {
                formReferenceDefinitions.add((FormReferenceDefinition) nodeDefinition);
            }
        }
        return formReferenceDefinitions;
    }

    /**
     * Get the form's node definitions.
     *
     * @return the form's node definitions.
     */
    public List<NodeDefinition> getNodeDefinitions() {
        return _nodeDefinitions;
    }

    /**
     * Get the form's source.
     *
     * @return the form's source.
     */
    public Object getSource() {
        return _source;
    }

    /**
     * Get all form definitions, referenced from this form definition.
     *
     * @param formDefinitions all form definitions.
     * @return all form definitions, referenced from this form definition.
     */
    public List<FormDefinition> getAllReferencedFormDefinitions(final FormDefinitions formDefinitions) {
        List<FormDefinition> allReferencedFormDefinitions = new ArrayList<>();
        addReferencedFormDefinitions(formDefinitions, this, allReferencedFormDefinitions);
        return allReferencedFormDefinitions;
    }

    private void addReferencedFormDefinitions(final FormDefinitions formDefinitions, final FormDefinition formDefinition, final List<FormDefinition> allReferencedFormDefinitions) {
        allReferencedFormDefinitions.add(formDefinition);
        List<FormReferenceDefinition> formReferenceDefinitions = getFormReferenceDefinitions();
        for (FormReferenceDefinition formReferenceDefinition : formReferenceDefinitions) {
            String referenceId = formReferenceDefinition.getReferenceId();
            FormDefinition referencedFormDefinition = formDefinitions.getFormDefinition(referenceId);
            addReferencedFormDefinitions(formDefinitions, referencedFormDefinition, allReferencedFormDefinitions);
        }
    }

    /**
     * Get additional attribute values of all form definitions, referenced from this form definition,
     * for the specified additional attribute name.
     *
     * @param formDefinitions         all form definitions.
     * @param additionalAttributeName the specified additional attribute name.
     * @return additional attribute values of all form definitions, referenced from this form definition.
     */
    public Set<String> getAllAdditionalAttributes(final FormDefinitions formDefinitions, final String additionalAttributeName) {
        Set<String> additionalAttributes = new HashSet<>();
        for (FormDefinition formDefinition : getAllReferencedFormDefinitions(formDefinitions)) {
            String additionalAttribute = formDefinition.getAdditionalAttribute(additionalAttributeName);
            if (additionalAttribute != null) {
                additionalAttributes.add(additionalAttributeName);
            }
        }
        return additionalAttributes;
    }

}
