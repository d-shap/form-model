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
 * <p>
 * Abstraction for the form reference definition.
 * </p>
 * <p>
 * Form reference is a link to another form, defined elsewhere. Form definition, containing the form
 * reference definition, contains all element definitions of the referenced form definition.
 * </p>
 * <p>
 * Binded form reference can be used to access the binded elements, defined in the referenced form.
 * </p>
 *
 * @author Dmitry Shapovalov
 */
public final class FormReferenceDefinition extends NodeDefinition {

    static final String ELEMENT_NAME = "form";

    static final String ATTRIBUTE_REFERENCED_FORM_ID = "refid";

    private final String _referencedFormId;

    FormReferenceDefinition(final String referencedFormId) {
        super();
        _referencedFormId = referencedFormId;
    }

    @Override
    void setChildNodesFormDefinitions() {
        // No child nodes
    }

    /**
     * Get the referenced form ID.
     *
     * @return the referenced form ID.
     */
    public String getReferencedFormId() {
        return _referencedFormId;
    }

    /**
     * Get the referenced form definition.
     *
     * @return the referenced form definition.
     */
    public FormDefinition getReferencedFormDefinition() {
        return getFormDefinition(_referencedFormId);
    }

}
