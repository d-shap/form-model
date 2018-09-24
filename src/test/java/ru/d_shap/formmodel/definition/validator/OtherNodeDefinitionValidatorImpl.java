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

    /**
     * Create new object.
     */
    public OtherNodeDefinitionValidatorImpl() {
        super();
    }

    @Override
    public void validate(final NodeDefinition parentNodeDefinition, final OtherNodeDefinition otherNodeDefinition, final FormModelDefinitionValidator formModelDefinitionValidator, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, otherNodeDefinition);

        if (!((OtherNodeDefinitionImpl) otherNodeDefinition).isValid()) {
            throw new FormDefinitionValidationException("Not valid!", currentNodePath);
        }

        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getAttributeDefinition() != null) {
            formModelDefinitionValidator.validateAttributeDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getAttributeDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getElementDefinition() != null) {
            formModelDefinitionValidator.validateElementDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getElementDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getChoiceDefinition() != null) {
            formModelDefinitionValidator.validateChoiceDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getChoiceDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getFormReferenceDefinition() != null) {
            formModelDefinitionValidator.validateFormReferenceDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getFormReferenceDefinition(), currentNodePath);
        }
        if (((OtherNodeDefinitionImpl) otherNodeDefinition).getOtherNodeDefinition() != null) {
            formModelDefinitionValidator.validateOtherNodeDefinition(otherNodeDefinition, ((OtherNodeDefinitionImpl) otherNodeDefinition).getOtherNodeDefinition(), currentNodePath);
        }
    }

}