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
package ru.d_shap.fm.formmodel.definition.validator;

import ru.d_shap.fm.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.fm.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.fm.formmodel.definition.model.ElementDefinition;
import ru.d_shap.fm.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.fm.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.fm.formmodel.definition.model.NodeDefinition;
import ru.d_shap.fm.formmodel.definition.model.NodePath;
import ru.d_shap.fm.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.fm.formmodel.definition.model.SingleElementDefinition;

/**
 * Validator for the form definition.
 *
 * @author Dmitry Shapovalov
 */
public interface FormDefinitionValidator {

    /**
     * Check if the string is empty.
     *
     * @param str the string to check.
     *
     * @return true if the string is empty.
     */
    boolean isEmptyString(String str);

    /**
     * Check if the string is blank (empty or contains only whitespaces).
     *
     * @param str the string to check.
     *
     * @return true if the string is blank.
     */
    boolean isBlankString(String str);

    /**
     * Check if the string has only valid characters.
     *
     * @param str the string to check.
     *
     * @return true if the string has only valid characters.
     */
    boolean isStringHasValidCharacters(String str);

    /**
     * Validate the node's source.
     *
     * @param source   the node's source.
     * @param nodePath the current node path.
     */
    void validateSource(String source, NodePath nodePath);

    /**
     * Validate the node's group.
     *
     * @param group    the node's group.
     * @param nodePath the current node path.
     */
    void validateGroup(String group, NodePath nodePath);

    /**
     * Validate the node's ID.
     *
     * @param id           the node's ID.
     * @param emptyIsValid true if empty ID is valid.
     * @param nodePath     the current node path.
     */
    void validateId(String id, boolean emptyIsValid, NodePath nodePath);

    /**
     * Validate the node's lookup.
     *
     * @param lookup   the node's lookup string.
     * @param nodePath the current node path.
     */
    void validateLookup(String lookup, NodePath nodePath);

    /**
     * Validate the node's cardinality.
     *
     * @param cardinalityDefinition       the node's cardinality.
     * @param validCardinalityDefinitions the node's valid cardinalities.
     * @param nodePath                    the current node path.
     */
    void validateCardinalityDefinition(CardinalityDefinition cardinalityDefinition, CardinalityDefinition[] validCardinalityDefinitions, NodePath nodePath);

    /**
     * Validate the node's form definition key.
     *
     * @param formDefinitionKey the node's form definition key.
     * @param nodePath          the current node path.
     */
    void validateFormDefinitionKey(FormDefinitionKey formDefinitionKey, NodePath nodePath);

    /**
     * Validate the attribute definition.
     *
     * @param parentNodeDefinition the parent node definition.
     * @param attributeDefinition  the attribute definition.
     * @param nodePath             the current node path.
     */
    void validateAttributeDefinition(NodeDefinition parentNodeDefinition, AttributeDefinition attributeDefinition, NodePath nodePath);

    /**
     * Validate the element definition.
     *
     * @param parentNodeDefinition the parent node definition.
     * @param elementDefinition    the element definition.
     * @param nodePath             the current node path.
     */
    void validateElementDefinition(NodeDefinition parentNodeDefinition, ElementDefinition elementDefinition, NodePath nodePath);

    /**
     * Validate the single element definition.
     *
     * @param parentNodeDefinition    the parent node definition.
     * @param singleElementDefinition the single element definition.
     * @param nodePath                the current node path.
     */
    void validateSingleElementDefinition(NodeDefinition parentNodeDefinition, SingleElementDefinition singleElementDefinition, NodePath nodePath);

    /**
     * Validate the form reference definition.
     *
     * @param parentNodeDefinition    the parent node definition.
     * @param formReferenceDefinition the form reference definition.
     * @param nodePath                the current node path.
     */
    void validateFormReferenceDefinition(NodeDefinition parentNodeDefinition, FormReferenceDefinition formReferenceDefinition, NodePath nodePath);

    /**
     * Validate the other node definition.
     *
     * @param parentNodeDefinition the parent node definition.
     * @param otherNodeDefinition  the other node definition.
     * @param nodePath             the current node path.
     */
    void validateOtherNodeDefinition(NodeDefinition parentNodeDefinition, OtherNodeDefinition otherNodeDefinition, NodePath nodePath);

}
