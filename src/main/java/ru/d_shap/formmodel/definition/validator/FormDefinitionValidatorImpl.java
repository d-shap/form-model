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
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;
import ru.d_shap.formmodel.utils.EmptyStringHelper;

/**
 * Validator for the form definition.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionValidatorImpl implements FormDefinitionValidator {

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

    private static final CardinalityDefinition[] ATTRIBUTE_DEFAULT_CARDINALITY = new CardinalityDefinition[]{CardinalityDefinition.REQUIRED, CardinalityDefinition.OPTIONAL, CardinalityDefinition.PROHIBITED};

    private static final CardinalityDefinition[] ELEMENT_DEFAULT_CARDINALITY = CardinalityDefinition.values();

    private static final CardinalityDefinition[] ELEMENT_SINGLE_ELEMENT_CARDINALITY = new CardinalityDefinition[]{CardinalityDefinition.OPTIONAL, CardinalityDefinition.OPTIONAL_MULTIPLE};

    private static final CardinalityDefinition[] SINGLE_ELEMENT_DEFAULT_CARDINALITY = CardinalityDefinition.values();

    private static final CardinalityDefinition[] SINGLE_ELEMENT_SINGLE_ELEMENT_CARDINALITY = new CardinalityDefinition[]{CardinalityDefinition.OPTIONAL, CardinalityDefinition.OPTIONAL_MULTIPLE};

    private final Set<FormDefinitionKey> _allFormDefinitionKeys;

    private final List<OtherNodeDefinitionValidator> _otherNodeDefinitionValidators;

    FormDefinitionValidatorImpl(final Set<FormDefinitionKey> allFormDefinitionKeys, final List<OtherNodeDefinitionValidator> otherNodeDefinitionValidators) {
        super();
        _allFormDefinitionKeys = new HashSet<>(allFormDefinitionKeys);
        _otherNodeDefinitionValidators = new ArrayList<>(otherNodeDefinitionValidators);
    }

    @Override
    public boolean isEmptyString(final String str) {
        return EmptyStringHelper.isEmpty(str);
    }

    @Override
    public boolean isBlankString(final String str) {
        return EmptyStringHelper.isBlank(str);
    }

    @Override
    public boolean isStringHasValidCharacters(final String str) {
        int idx = 0;
        if (!VALID_START_CHARACTERS.contains(str.charAt(idx))) {
            return false;
        }
        idx++;
        while (idx < str.length()) {
            if (!VALID_CHARACTERS.contains(str.charAt(idx))) {
                return false;
            }
            idx++;
        }
        return true;
    }

    @Override
    public void validateSource(final String source, final NodePath nodePath) {
        if (isBlankString(source)) {
            throw new FormDefinitionValidationException(Messages.Validation.getSourceIsEmptyMessage(), nodePath);
        }
    }

    @Override
    public void validateGroup(final String group, final NodePath nodePath) {
        if (!isEmptyString(group) && !isStringHasValidCharacters(group)) {
            throw new FormDefinitionValidationException(Messages.Validation.getGroupIsNotValidMessage(group), nodePath);
        }
    }

    @Override
    public void validateId(final String id, final boolean emptyIsValid, final NodePath nodePath) {
        if (emptyIsValid) {
            if (!isEmptyString(id) && !isStringHasValidCharacters(id)) {
                throw new FormDefinitionValidationException(Messages.Validation.getIdIsNotValidMessage(id), nodePath);
            }
        } else {
            if (isEmptyString(id)) {
                throw new FormDefinitionValidationException(Messages.Validation.getIdIsEmptyMessage(), nodePath);
            }
            if (!isStringHasValidCharacters(id)) {
                throw new FormDefinitionValidationException(Messages.Validation.getIdIsNotValidMessage(id), nodePath);
            }
        }
    }

    @Override
    public void validateLookup(final String lookup, final NodePath nodePath) {
        if (isBlankString(lookup)) {
            throw new FormDefinitionValidationException(Messages.Validation.getLookupIsEmptyMessage(), nodePath);
        }
    }

    @Override
    public void validateCardinalityDefinition(final CardinalityDefinition cardinalityDefinition, final CardinalityDefinition[] validCardinalityDefinitions, final NodePath nodePath) {
        if (cardinalityDefinition == null) {
            throw new FormDefinitionValidationException(Messages.Validation.getCardinalityDefinitionIsEmptyMessage(), nodePath);
        }
        boolean isCardinalityDefinitionValid = false;
        for (CardinalityDefinition validCardinalityDefinition : validCardinalityDefinitions) {
            if (cardinalityDefinition == validCardinalityDefinition) {
                isCardinalityDefinitionValid = true;
                break;
            }
        }
        if (!isCardinalityDefinitionValid) {
            throw new FormDefinitionValidationException(Messages.Validation.getCardinalityDefinitionIsNotValidMessage(cardinalityDefinition.getCardinality()), nodePath);
        }
    }

    @Override
    public void validateFormDefinitionKey(final FormDefinitionKey formDefinitionKey, final NodePath nodePath) {
        if (!_allFormDefinitionKeys.contains(formDefinitionKey)) {
            throw new FormDefinitionValidationException(Messages.Validation.getFormDefinitionKeyIsNotValidMessage(formDefinitionKey), nodePath);
        }
    }

    void validateFormDefinition(final FormDefinition formDefinition) {
        NodePath currentNodePath = new NodePath(formDefinition);

        validateSource(formDefinition.getSource(), currentNodePath);
        validateGroup(formDefinition.getGroup(), currentNodePath);
        validateId(formDefinition.getId(), false, currentNodePath);

        for (ElementDefinition childElementDefinition : formDefinition.getElementDefinitions()) {
            validateElementDefinition(formDefinition, childElementDefinition, currentNodePath);
        }
        for (SingleElementDefinition childSingleElementDefinition : formDefinition.getSingleElementDefinitions()) {
            validateSingleElementDefinition(formDefinition, childSingleElementDefinition, currentNodePath);
        }
        for (FormReferenceDefinition childFormReferenceDefinition : formDefinition.getFormReferenceDefinitions()) {
            validateFormReferenceDefinition(formDefinition, childFormReferenceDefinition, currentNodePath);
        }
        for (OtherNodeDefinition childOtherNodeDefinition : formDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(formDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validateAttributeDefinition(final NodeDefinition parentNodeDefinition, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, attributeDefinition);

        validateId(attributeDefinition.getId(), true, currentNodePath);
        validateLookup(attributeDefinition.getLookup(), currentNodePath);
        CardinalityDefinition[] validCardinalityDefinitions = getAttributeDefinitionCardinalities(parentNodeDefinition);
        validateCardinalityDefinition(attributeDefinition.getCardinalityDefinition(), validCardinalityDefinitions, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : attributeDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(attributeDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    private CardinalityDefinition[] getAttributeDefinitionCardinalities(final NodeDefinition parentNodeDefinition) {
        CardinalityDefinition[] validCardinalityDefinitions;
        if (parentNodeDefinition instanceof OtherNodeDefinition) {
            for (OtherNodeDefinitionValidator otherNodeDefinitionValidator : _otherNodeDefinitionValidators) {
                validCardinalityDefinitions = otherNodeDefinitionValidator.getAttributeDefinitionCardinalities((OtherNodeDefinition) parentNodeDefinition);
                if (validCardinalityDefinitions != null) {
                    return validCardinalityDefinitions;
                }
            }
        }
        return ATTRIBUTE_DEFAULT_CARDINALITY;
    }

    @Override
    public void validateElementDefinition(final NodeDefinition parentNodeDefinition, final ElementDefinition elementDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, elementDefinition);

        validateId(elementDefinition.getId(), true, currentNodePath);
        validateLookup(elementDefinition.getLookup(), currentNodePath);
        CardinalityDefinition[] validCardinalityDefinitions = getElementDefinitionCardinalities(parentNodeDefinition);
        validateCardinalityDefinition(elementDefinition.getCardinalityDefinition(), validCardinalityDefinitions, currentNodePath);

        for (AttributeDefinition childAttributeDefinition : elementDefinition.getAttributeDefinitions()) {
            validateAttributeDefinition(elementDefinition, childAttributeDefinition, currentNodePath);
        }
        for (ElementDefinition childElementDefinition : elementDefinition.getElementDefinitions()) {
            validateElementDefinition(elementDefinition, childElementDefinition, currentNodePath);
        }
        for (SingleElementDefinition childSingleElementDefinition : elementDefinition.getSingleElementDefinitions()) {
            validateSingleElementDefinition(elementDefinition, childSingleElementDefinition, currentNodePath);
        }
        for (FormReferenceDefinition childFormReferenceDefinition : elementDefinition.getFormReferenceDefinitions()) {
            validateFormReferenceDefinition(elementDefinition, childFormReferenceDefinition, currentNodePath);
        }
        for (OtherNodeDefinition childOtherNodeDefinition : elementDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(elementDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    private CardinalityDefinition[] getElementDefinitionCardinalities(final NodeDefinition parentNodeDefinition) {
        CardinalityDefinition[] validCardinalityDefinitions;
        if (parentNodeDefinition instanceof OtherNodeDefinition) {
            for (OtherNodeDefinitionValidator otherNodeDefinitionValidator : _otherNodeDefinitionValidators) {
                validCardinalityDefinitions = otherNodeDefinitionValidator.getElementDefinitionCardinalities((OtherNodeDefinition) parentNodeDefinition);
                if (validCardinalityDefinitions != null) {
                    return validCardinalityDefinitions;
                }
            }
        }
        if (parentNodeDefinition instanceof SingleElementDefinition) {
            return ELEMENT_SINGLE_ELEMENT_CARDINALITY;
        } else {
            return ELEMENT_DEFAULT_CARDINALITY;
        }
    }

    @Override
    public void validateSingleElementDefinition(final NodeDefinition parentNodeDefinition, final SingleElementDefinition singleElementDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, singleElementDefinition);

        validateId(singleElementDefinition.getId(), true, currentNodePath);
        CardinalityDefinition[] validCardinalityDefinitions = getSingleElementDefinitionCardinalities(parentNodeDefinition);
        validateCardinalityDefinition(singleElementDefinition.getCardinalityDefinition(), validCardinalityDefinitions, currentNodePath);

        for (ElementDefinition childElementDefinition : singleElementDefinition.getElementDefinitions()) {
            validateElementDefinition(singleElementDefinition, childElementDefinition, currentNodePath);
        }
        for (SingleElementDefinition childSingleElementDefinition : singleElementDefinition.getSingleElementDefinitions()) {
            validateSingleElementDefinition(singleElementDefinition, childSingleElementDefinition, currentNodePath);
        }
        for (OtherNodeDefinition childOtherNodeDefinition : singleElementDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(singleElementDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    private CardinalityDefinition[] getSingleElementDefinitionCardinalities(final NodeDefinition parentNodeDefinition) {
        CardinalityDefinition[] validCardinalityDefinitions;
        if (parentNodeDefinition instanceof OtherNodeDefinition) {
            for (OtherNodeDefinitionValidator otherNodeDefinitionValidator : _otherNodeDefinitionValidators) {
                validCardinalityDefinitions = otherNodeDefinitionValidator.getSingleElementDefinitionCardinalities((OtherNodeDefinition) parentNodeDefinition);
                if (validCardinalityDefinitions != null) {
                    return validCardinalityDefinitions;
                }
            }
        }
        if (parentNodeDefinition instanceof SingleElementDefinition) {
            return SINGLE_ELEMENT_SINGLE_ELEMENT_CARDINALITY;
        } else {
            return SINGLE_ELEMENT_DEFAULT_CARDINALITY;
        }
    }

    @Override
    public void validateFormReferenceDefinition(final NodeDefinition parentNodeDefinition, final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, formReferenceDefinition);

        validateGroup(formReferenceDefinition.getGroup(), currentNodePath);
        validateId(formReferenceDefinition.getId(), false, currentNodePath);
        FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formReferenceDefinition);
        validateFormDefinitionKey(formDefinitionKey, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : formReferenceDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(formReferenceDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validateOtherNodeDefinition(final NodeDefinition parentNodeDefinition, final OtherNodeDefinition otherNodeDefinition, final NodePath nodePath) {
        for (OtherNodeDefinitionValidator otherNodeDefinitionValidator : _otherNodeDefinitionValidators) {
            otherNodeDefinitionValidator.validate(parentNodeDefinition, otherNodeDefinition, this, nodePath);
        }
    }

}
