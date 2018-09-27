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

/**
 * Validator for the form definition.
 *
 * @author Dmitry Shapovalov
 */
final class FormDefinitionValidatorImpl implements FormDefinitionValidator {

    private final Set<FormDefinitionKey> _allFormDefinitionKeys;

    private final List<OtherNodeDefinitionValidator> _otherNodeDefinitionValidators;

    FormDefinitionValidatorImpl(final Set<FormDefinitionKey> allFormDefinitionKeys, final List<OtherNodeDefinitionValidator> otherNodeDefinitionValidators) {
        super();
        _allFormDefinitionKeys = new HashSet<>(allFormDefinitionKeys);
        _otherNodeDefinitionValidators = new ArrayList<>(otherNodeDefinitionValidators);
    }

    @Override
    public boolean isEmptyString(final String str) {
        return StringUtils.isEmpty(str);
    }

    @Override
    public boolean isBlankString(final String str) {
        return StringUtils.isBlank(str);
    }

    @Override
    public boolean isStringHasValidCharacters(final String str) {
        return StringUtils.hasValidCharacters(str);
    }

    void validateFormDefinition(final FormDefinition formDefinition) {
        NodePath currentNodePath = new NodePath(formDefinition);

        validateGroup(formDefinition.getGroup(), currentNodePath);
        validateId(formDefinition.getId(), currentNodePath);
        validateSource(formDefinition.getSource(), currentNodePath);

        List<String> childNodeIds = new ArrayList<>();
        for (ElementDefinition childElementDefinition : formDefinition.getElementDefinitions()) {
            validateElementDefinition(formDefinition, childElementDefinition, currentNodePath);
            childNodeIds.add(childElementDefinition.getId());
        }
        for (SingleElementDefinition childSingleElementDefinition : formDefinition.getSingleElementDefinitions()) {
            validateSingleElementDefinition(formDefinition, childSingleElementDefinition, currentNodePath);
            childNodeIds.add(childSingleElementDefinition.getId());
        }
        validateUniqueNodeIds(childNodeIds, currentNodePath);

        List<FormDefinitionKey> childFormReferences = new ArrayList<>();
        for (FormReferenceDefinition childFormReferenceDefinition : formDefinition.getFormReferenceDefinitions()) {
            validateFormReferenceDefinition(formDefinition, childFormReferenceDefinition, currentNodePath);
            childFormReferences.add(new FormDefinitionKey(childFormReferenceDefinition));
        }
        validateUniqueFormReferences(childFormReferences, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : formDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(formDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validateAttributeDefinition(final NodeDefinition parentNodeDefinition, final AttributeDefinition attributeDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, attributeDefinition);

        validateId(attributeDefinition.getId(), currentNodePath);
        validateLookup(attributeDefinition.getLookup(), currentNodePath);
        validateCardinalityDefinition(attributeDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.REQUIRED, CardinalityDefinition.OPTIONAL, CardinalityDefinition.PROHIBITED);

        for (OtherNodeDefinition childOtherNodeDefinition : attributeDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(attributeDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validateElementDefinition(final NodeDefinition parentNodeDefinition, final ElementDefinition elementDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, elementDefinition);

        if (parentNodeDefinition instanceof SingleElementDefinition) {
            validateEmptyId(elementDefinition.getId(), currentNodePath);
        } else {
            validateId(elementDefinition.getId(), currentNodePath);
        }
        validateLookup(elementDefinition.getLookup(), currentNodePath);
        if (parentNodeDefinition instanceof SingleElementDefinition) {
            validateCardinalityDefinition(elementDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.OPTIONAL, CardinalityDefinition.OPTIONAL_MULTIPLE);
        } else {
            validateCardinalityDefinition(elementDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.values());
        }

        List<String> childAttributeIds = new ArrayList<>();
        for (AttributeDefinition childAttributeDefinition : elementDefinition.getAttributeDefinitions()) {
            validateAttributeDefinition(elementDefinition, childAttributeDefinition, currentNodePath);
            childAttributeIds.add(childAttributeDefinition.getId());
        }
        validateUniqueNodeIds(childAttributeIds, currentNodePath);

        List<String> childNodeIds = new ArrayList<>();
        for (ElementDefinition childElementDefinition : elementDefinition.getElementDefinitions()) {
            validateElementDefinition(elementDefinition, childElementDefinition, currentNodePath);
            childNodeIds.add(childElementDefinition.getId());
        }
        for (SingleElementDefinition childSingleElementDefinition : elementDefinition.getSingleElementDefinitions()) {
            validateSingleElementDefinition(elementDefinition, childSingleElementDefinition, currentNodePath);
            childNodeIds.add(childSingleElementDefinition.getId());
        }
        validateUniqueNodeIds(childNodeIds, currentNodePath);

        List<FormDefinitionKey> childFormReferences = new ArrayList<>();
        for (FormReferenceDefinition childFormReferenceDefinition : elementDefinition.getFormReferenceDefinitions()) {
            validateFormReferenceDefinition(elementDefinition, childFormReferenceDefinition, currentNodePath);
            childFormReferences.add(new FormDefinitionKey(childFormReferenceDefinition));
        }
        validateUniqueFormReferences(childFormReferences, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : elementDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(elementDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validateSingleElementDefinition(final NodeDefinition parentNodeDefinition, final SingleElementDefinition singleElementDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, singleElementDefinition);

        if (parentNodeDefinition instanceof SingleElementDefinition) {
            validateEmptyId(singleElementDefinition.getId(), currentNodePath);
        } else {
            validateId(singleElementDefinition.getId(), currentNodePath);
        }
        if (parentNodeDefinition instanceof SingleElementDefinition) {
            validateCardinalityDefinition(singleElementDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.OPTIONAL);
        } else {
            validateCardinalityDefinition(singleElementDefinition.getCardinalityDefinition(), currentNodePath, CardinalityDefinition.REQUIRED, CardinalityDefinition.OPTIONAL, CardinalityDefinition.PROHIBITED);
        }

        List<String> childNodeIds = new ArrayList<>();
        for (ElementDefinition childElementDefinition : singleElementDefinition.getElementDefinitions()) {
            validateElementDefinition(singleElementDefinition, childElementDefinition, currentNodePath);
            childNodeIds.add(childElementDefinition.getId());
        }
        for (SingleElementDefinition childSingleElementDefinition : singleElementDefinition.getSingleElementDefinitions()) {
            validateSingleElementDefinition(singleElementDefinition, childSingleElementDefinition, currentNodePath);
            childNodeIds.add(childSingleElementDefinition.getId());
        }
        validateUniqueNodeIds(childNodeIds, currentNodePath);

        for (OtherNodeDefinition childOtherNodeDefinition : singleElementDefinition.getOtherNodeDefinitions()) {
            validateOtherNodeDefinition(singleElementDefinition, childOtherNodeDefinition, currentNodePath);
        }
    }

    @Override
    public void validateFormReferenceDefinition(final NodeDefinition parentNodeDefinition, final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
        NodePath currentNodePath = new NodePath(nodePath, formReferenceDefinition);

        validateGroup(formReferenceDefinition.getGroup(), currentNodePath);
        validateId(formReferenceDefinition.getId(), currentNodePath);
        validateFormReference(formReferenceDefinition, currentNodePath);

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

    private void validateGroup(final String group, final NodePath nodePath) {
        if (!isEmptyString(group) && !isStringHasValidCharacters(group)) {
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
            throw new FormDefinitionValidationException(Messages.Validation.getIdIsEmptyMessage(), nodePath);
        }
        if (!isStringHasValidCharacters(id)) {
            throw new FormDefinitionValidationException(Messages.Validation.getIdIsNotValidMessage(id), nodePath);
        }
    }

    private void validateLookup(final String lookup, final NodePath nodePath) {
        if (isBlankString(lookup)) {
            throw new FormDefinitionValidationException(Messages.Validation.getLookupIsEmptyMessage(), nodePath);
        }
    }

    private void validateCardinalityDefinition(final CardinalityDefinition cardinalityDefinition, final NodePath nodePath, final CardinalityDefinition... validCardinalityDefinitions) {
        if (cardinalityDefinition == null) {
            throw new FormDefinitionValidationException(Messages.Validation.getCardinalityIsEmptyMessage(), nodePath);
        }
        boolean isCardinalityDefinitionValid = false;
        for (CardinalityDefinition validCardinalityDefinition : validCardinalityDefinitions) {
            if (cardinalityDefinition == validCardinalityDefinition) {
                isCardinalityDefinitionValid = true;
                break;
            }
        }
        if (!isCardinalityDefinitionValid) {
            throw new FormDefinitionValidationException(Messages.Validation.getCardinalityIsNotValidMessage(cardinalityDefinition.getCardinality()), nodePath);
        }
    }

    private void validateSource(final String source, final NodePath nodePath) {
        if (isBlankString(source)) {
            throw new FormDefinitionValidationException(Messages.Validation.getSourceIsEmptyMessage(), nodePath);
        }
    }

    private void validateUniqueNodeIds(final List<String> nodeIds, final NodePath nodePath) {
        Set<String> uniqueNodeIds = new HashSet<>();
        for (String nodeId : nodeIds) {
            if (!uniqueNodeIds.add(nodeId)) {
                throw new FormDefinitionValidationException(Messages.Validation.getIdIsNotUniqueMessage(nodeId), nodePath);
            }
        }
    }

    private void validateUniqueFormReferences(final List<FormDefinitionKey> formReferences, final NodePath nodePath) {
        Set<FormDefinitionKey> uniqueFormReferences = new HashSet<>();
        for (FormDefinitionKey formReference : formReferences) {
            if (!uniqueFormReferences.add(formReference)) {
                throw new FormDefinitionValidationException(Messages.Validation.getFormReferenceIsNotUniqueMessage(formReference), nodePath);
            }
        }
    }

    private void validateFormReference(final FormReferenceDefinition formReferenceDefinition, final NodePath nodePath) {
        FormDefinitionKey formDefinitionKey = new FormDefinitionKey(formReferenceDefinition);
        if (!_allFormDefinitionKeys.contains(formDefinitionKey)) {
            throw new FormDefinitionValidationException(Messages.Validation.getUnresolvedFormReferenceMessage(formDefinitionKey), nodePath);
        }
    }

}
