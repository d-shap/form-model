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

import java.util.ArrayList;
import java.util.List;

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * Validator for the form definition.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionValidator implements FormModelDefinitionValidator {

    private final List<OtherNodeDefinitionValidator> _otherNodeDefinitionValidators;

    private final OtherNodeDefinitionValidator _defaultOtherNodeDefinitionValidator;

    FormDefinitionValidator(final List<OtherNodeDefinitionValidator> otherNodeDefinitionValidators) {
        super();
        if (otherNodeDefinitionValidators == null) {
            _otherNodeDefinitionValidators = new ArrayList<>();
        } else {
            _otherNodeDefinitionValidators = new ArrayList<>(otherNodeDefinitionValidators);
        }
        _defaultOtherNodeDefinitionValidator = new DefaultOtherNodeDefinitionValidator();
    }

    @Override
    public void validate(final FormDefinition formDefinition, final NodePath nodePath) {

    }

    @Override
    public void validate(final ElementDefinition elementDefinition, final NodePath nodePath) {

    }

    @Override
    public void validate(final ChoiceDefinition choiceDefinition, final NodePath nodePath) {

    }

    @Override
    public void validate(final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {

    }

    @Override
    public void validate(final AttributeDefinition attributeDefinition, final NodePath nodePath) {

    }

}
