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
package ru.d_shap.formmodel.definition.validator;

import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinitionImpl;

/**
 * Implementation of the {@link OtherNodeDefinitionValidator}.
 *
 * @author Dmitry Shapovalov
 */
public final class OtherNodeDefinitionValidatorImpl implements OtherNodeDefinitionValidator {

    private static final CardinalityDefinition[] CARDINALITY_DEFINITIONS = new CardinalityDefinition[]{CardinalityDefinition.OPTIONAL_MULTIPLE, CardinalityDefinition.PROHIBITED};

    /**
     * Create new object.
     */
    public OtherNodeDefinitionValidatorImpl() {
        super();
    }

    @Override
    public void validate(final NodeDefinition parentNodeDefinition, final OtherNodeDefinition otherNodeDefinition, final FormDefinitionValidator formDefinitionValidator, final NodePath nodePath) {
        if (!(otherNodeDefinition instanceof OtherNodeDefinitionImpl)) {
            return;
        }

        NodePath currentNodePath = new NodePath(nodePath, otherNodeDefinition);
        if (!((OtherNodeDefinitionImpl) otherNodeDefinition).isValid()) {
            throw new FormDefinitionValidationException("[Not valid!]", currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getAttributeDefinition() != null) {
            formDefinitionValidator.validateAttributeDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getAttributeDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getElementDefinition() != null) {
            formDefinitionValidator.validateElementDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getElementDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getSingleElementDefinition() != null) {
            formDefinitionValidator.validateSingleElementDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getSingleElementDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getFormReferenceDefinition() != null) {
            formDefinitionValidator.validateFormReferenceDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getFormReferenceDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getOtherNodeDefinition() != null) {
            formDefinitionValidator.validateOtherNodeDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getOtherNodeDefinition(), currentNodePath);
        }
    }

    @Override
    public CardinalityDefinition[] getAttributeDefinitionCardinalities(final OtherNodeDefinition parentOtherNodeDefinition) {
        if (parentOtherNodeDefinition instanceof OtherNodeDefinitionImpl) {
            return CARDINALITY_DEFINITIONS;
        } else {
            return null;
        }
    }

    @Override
    public CardinalityDefinition[] getElementDefinitionCardinalities(final OtherNodeDefinition parentOtherNodeDefinition) {
        if (parentOtherNodeDefinition instanceof OtherNodeDefinitionImpl) {
            return CARDINALITY_DEFINITIONS;
        } else {
            return null;
        }
    }

    @Override
    public CardinalityDefinition[] getSingleElementDefinitionCardinalities(final OtherNodeDefinition parentOtherNodeDefinition) {
        if (parentOtherNodeDefinition instanceof OtherNodeDefinitionImpl) {
            return CARDINALITY_DEFINITIONS;
        } else {
            return null;
        }
    }

}
