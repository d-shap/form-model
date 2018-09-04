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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.d_shap.formmodel.Messages;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * Validator for the form definition.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionValidator implements FormModelDefinitionValidator {

    private static final Set<Character> VALID_START_CHARACTERS;

    private static final Set<Character> VALID_CHARACTERS;

    static {
        VALID_START_CHARACTERS = new HashSet<>();
        for (char ch = 'a'; ch < 'z' + 1; ch++) {
            VALID_START_CHARACTERS.add(ch);
        }
        for (char ch = 'A'; ch < 'Z' + 1; ch++) {
            VALID_START_CHARACTERS.add(ch);
        }
        VALID_START_CHARACTERS.add('_');

        VALID_CHARACTERS = new HashSet<>(VALID_START_CHARACTERS);
        for (char ch = '0'; ch < '9' + 1; ch++) {
            VALID_CHARACTERS.add(ch);
        }
        VALID_CHARACTERS.add('-');
    }

    private final Set<FormDefinitionKey> _allFormDefinitionKeys;

    private final List<OtherNodeDefinitionValidator> _otherNodeDefinitionValidators;

    FormDefinitionValidator(final Set<FormDefinitionKey> allFormDefinitionKeys, final List<OtherNodeDefinitionValidator> otherNodeDefinitionValidators) {
        super();
        if (allFormDefinitionKeys == null) {
            _allFormDefinitionKeys = new HashSet<>();
        } else {
            _allFormDefinitionKeys = new HashSet<>(allFormDefinitionKeys);
        }
        if (otherNodeDefinitionValidators == null) {
            _otherNodeDefinitionValidators = new ArrayList<>();
        } else {
            _otherNodeDefinitionValidators = new ArrayList<>(otherNodeDefinitionValidators);
        }
    }

    @Override
    public void validate(final FormDefinition formDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, formDefinition);

        validateGroup(formDefinition.getGroup(), currentNodePath);
        validateId(formDefinition.getId(), currentNodePath);
        validateSource(formDefinition.getSource(), currentNodePath);

        List<String> childNodeIds = new ArrayList<>();
        for (ElementDefinition childElementDefinition : formDefinition.getElementDefinitions()) {
            validate(formDefinition, childElementDefinition, currentNodePath);
            childNodeIds.add(childElementDefinition.getId());
        }
        for (ChoiceDefinition childChoiceDefinition : formDefinition.getChoiceDefinitions()) {
            validate(formDefinition, childChoiceDefinition, currentNodePath);
            childNodeIds.add(childChoiceDefinition.getId());
        }
        validateUniqueNodeIds(childNodeIds, currentNodePath);

        List<FormDefinitionKey> childFormReferences = new ArrayList<>();
        for (FormReferenceDefinition childFormReferenceDefinition : formDefinition.getFormReferenceDefinitions()) {
            validate(formDefinition, childFormReferenceDefinition, currentNodePath);
            childFormReferences.add(new FormDefinitionKey(childFormReferenceDefinition));
        }
        validateUniqueFormReferences(childFormReferences, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : formDefinition.getOtherNodeDefinitions()) {
            validate(formDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validate(final NodeDefinition parentNodeDefinition, final ElementDefinition elementDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, elementDefinition);

        if (parentNodeDefinition instanceof ChoiceDefinition) {
            validateEmptyId(elementDefinition.getId(), currentNodePath);
        } else {
            validateId(elementDefinition.getId(), currentNodePath);
        }
        validateLookup(elementDefinition.getLookup(), currentNodePath);
        if (parentNodeDefinition instanceof ChoiceDefinition) {
            validateCardinalityDefinition(elementDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.OPTIONAL, CardinalityDefinition.OPTIONAL_MULTIPLE);
        } else {
            validateCardinalityDefinition(elementDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.values());
        }

        List<String> childAttributeIds = new ArrayList<>();
        for (AttributeDefinition childAttributeDefinition : elementDefinition.getAttributeDefinitions()) {
            validate(elementDefinition, childAttributeDefinition, currentNodePath);
            childAttributeIds.add(childAttributeDefinition.getId());
        }
        validateUniqueNodeIds(childAttributeIds, currentNodePath);

        List<String> childNodeIds = new ArrayList<>();
        for (ElementDefinition childElementDefinition : elementDefinition.getElementDefinitions()) {
            validate(elementDefinition, childElementDefinition, currentNodePath);
            childNodeIds.add(childElementDefinition.getId());
        }
        for (ChoiceDefinition childChoiceDefinition : elementDefinition.getChoiceDefinitions()) {
            validate(elementDefinition, childChoiceDefinition, currentNodePath);
            childNodeIds.add(childChoiceDefinition.getId());
        }
        validateUniqueNodeIds(childNodeIds, currentNodePath);

        List<FormDefinitionKey> childFormReferences = new ArrayList<>();
        for (FormReferenceDefinition childFormReferenceDefinition : elementDefinition.getFormReferenceDefinitions()) {
            validate(elementDefinition, childFormReferenceDefinition, currentNodePath);
            childFormReferences.add(new FormDefinitionKey(childFormReferenceDefinition));
        }
        validateUniqueFormReferences(childFormReferences, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : elementDefinition.getOtherNodeDefinitions()) {
            validate(elementDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validate(final NodeDefinition parentNodeDefinition, final ChoiceDefinition choiceDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, choiceDefinition);

        validateId(choiceDefinition.getId(), currentNodePath);
        validateCardinalityDefinition(choiceDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.values());

        List<String> childNodeIds = new ArrayList<>();
        for (ElementDefinition childElementDefinition : choiceDefinition.getElementDefinitions()) {
            validate(choiceDefinition, childElementDefinition, currentNodePath);
            childNodeIds.add(childElementDefinition.getId());
        }
        validateUniqueNodeIds(childNodeIds, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : choiceDefinition.getOtherNodeDefinitions()) {
            validate(choiceDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validate(final NodeDefinition parentNodeDefinition, final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, formReferenceDefinition);

        validateGroup(formReferenceDefinition.getGroup(), currentNodePath);
        validateId(formReferenceDefinition.getId(), currentNodePath);
        validateFormReference(formReferenceDefinition, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : formReferenceDefinition.getOtherNodeDefinitions()) {
            validate(formReferenceDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validate(final NodeDefinition parentNodeDefinition, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, attributeDefinition);

        validateId(attributeDefinition.getId(), currentNodePath);
        validateLookup(attributeDefinition.getLookup(), currentNodePath);
        validateCardinalityDefinition(attributeDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.REQUIRED, CardinalityDefinition.OPTIONAL, CardinalityDefinition.PROHIBITED);

        for (OtherNodeDefinition childOtherNodeDefinition : attributeDefinition.getOtherNodeDefinitions()) {
            validate(attributeDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    private void validate(final NodeDefinition parentNodeDefinition, final OtherNodeDefinition otherNodeDefinition, final NodePath nodePath) {
        for (OtherNodeDefinitionValidator otherNodeDefinitionValidator : _otherNodeDefinitionValidators) {
            otherNodeDefinitionValidator.validate(parentNodeDefinition, otherNodeDefinition, this, nodePath);
        }
    }

    private void validateGroup(final String group, final NodePath nodePath) {
        if (!isEmptyString(group) && !isValidCharactersInString(group)) {
            throw new FormDefinitionValidationException(Messages.Validation.getGroupIsNotValidMessage(group), nodePath);
        }
    }

    private void validateEmptyId(final String id, final NodePath nodePath) {
        if (!isEmptyString(id)) {
            throw new FormDefinitionValidationException(Messages.Validation.getIdIsNotEmptyMessage(id), nodePath);
        }
    }

    private void validateId(final String id, final NodePath nodePath) {
        if (isEmptyString(id)) {
            throw new FormDefinitionValidationException(Messages.Validation.getIdIsEmptyMessage(id), nodePath);
        }
        if (!isValidCharactersInString(id)) {
            throw new FormDefinitionValidationException(Messages.Validation.getIdIsNotValidMessage(id), nodePath);
        }
    }

    private void validateLookup(final String lookup, final NodePath nodePath) {

    }

    private void validateCardinalityDefinition(final CardinalityDefinition cardinalityDefinition, final NodePath nodePath, final CardinalityDefinition... validCardinalityDefinitions) {

    }

    private void validateSource(final String source, final NodePath nodePath) {

    }

    private void validateUniqueNodeIds(final List<String> nodeIds, final NodePath nodePath) {

    }

    private void validateUniqueFormReferences(final List<FormDefinitionKey> formReferences, final NodePath nodePath) {

    }

    private void validateFormReference(final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
        FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formReferenceDefinition);
        _allFormDefinitionKeys.contains(formDefinitionKey);
    }

    private boolean isEmptyString(final String str) {
        if (str == null) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidCharactersInString(final String str) {
        if (!VALID_START_CHARACTERS.contains(str.charAt(0))) {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            if (!VALID_CHARACTERS.contains(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
