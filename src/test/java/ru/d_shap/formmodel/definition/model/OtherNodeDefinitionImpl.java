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
 * Implementation of the {@link OtherNodeDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class OtherNodeDefinitionImpl implements OtherNodeDefinition {

    private final String _representation;

    private final boolean _valid;

    private AttributeDefinition _attributeDefinition;

    private ElementDefinition _elementDefinition;

    private SingleElementDefinition _singleElementDefinition;

    private FormReferenceDefinition _formReferenceDefinition;

    private OtherNodeDefinition _otherNodeDefinition;

    /**
     * Create new object.
     *
     * @param representation the string representation of this definition.
     * @param valid          true if this definition is valid.
     */
    public OtherNodeDefinitionImpl(final String representation, final boolean valid) {
        super();
        _representation = representation;
        _valid = valid;
    }

    /**
     * Get the string representation of this definition.
     *
     * @return the string representation of this definition.
     */
    public String getRepresentation() {
        return _representation;
    }

    /**
     * Define if this definition is valid.
     *
     * @return true if this definition is valid.
     */
    public boolean isValid() {
        return _valid;
    }

    /**
     * Get the child attribute definition.
     *
     * @return the child attribute definition.
     */
    public AttributeDefinition getAttributeDefinition() {
        return _attributeDefinition;
    }

    /**
     * Set the child attribute definition.
     *
     * @param attributeDefinition the child attribute definition.
     */
    public void setAttributeDefinition(final AttributeDefinition attributeDefinition) {
        _attributeDefinition = attributeDefinition;
    }

    /**
     * Get the child element definition.
     *
     * @return the child element definition.
     */
    public ElementDefinition getElementDefinition() {
        return _elementDefinition;
    }

    /**
     * Set the child element definition.
     *
     * @param elementDefinition the child element definition.
     */
    public void setElementDefinition(final ElementDefinition elementDefinition) {
        _elementDefinition = elementDefinition;
    }

    /**
     * Get the child single element definition.
     *
     * @return the child single element definition.
     */
    public SingleElementDefinition getSingleElementDefinition() {
        return _singleElementDefinition;
    }

    /**
     * Set the child single element definition.
     *
     * @param singleElementDefinition the child single element definition.
     */
    public void setSingleElementDefinition(final SingleElementDefinition singleElementDefinition) {
        _singleElementDefinition = singleElementDefinition;
    }

    /**
     * Get the child form reference definition.
     *
     * @return the child form reference definition.
     */
    public FormReferenceDefinition getFormReferenceDefinition() {
        return _formReferenceDefinition;
    }

    /**
     * Set the child form reference definition.
     *
     * @param formReferenceDefinition the child form reference definition.
     */
    public void setFormReferenceDefinition(final FormReferenceDefinition formReferenceDefinition) {
        _formReferenceDefinition = formReferenceDefinition;
    }

    /**
     * Get the child other node definition.
     *
     * @return the child other node definition.
     */
    public OtherNodeDefinition getOtherNodeDefinition() {
        return _otherNodeDefinition;
    }

    /**
     * Set the child other node definition.
     *
     * @param otherNodeDefinition the child other node definition.
     */
    public void setOtherNodeDefinition(final OtherNodeDefinition otherNodeDefinition) {
        _otherNodeDefinition = otherNodeDefinition;
    }

    @Override
    public String toString() {
        return String.valueOf(_representation);
    }

}
