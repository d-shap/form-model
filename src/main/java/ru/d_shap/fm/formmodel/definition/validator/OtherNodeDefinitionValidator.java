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

import ru.d_shap.fm.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.fm.formmodel.definition.model.NodeDefinition;
import ru.d_shap.fm.formmodel.definition.model.NodePath;
import ru.d_shap.fm.formmodel.definition.model.OtherNodeDefinition;

/**
 * Validator for the other node definition.
 *
 * @author Dmitry Shapovalov
 */
public interface OtherNodeDefinitionValidator {

    /**
     * Validate the other node definition.
     *
     * @param parentNodeDefinition    the parent node definition.
     * @param otherNodeDefinition     the other node definition.
     * @param formDefinitionValidator validator for the form definition.
     * @param nodePath                the current node path.
     */
    void validate(NodeDefinition parentNodeDefinition, OtherNodeDefinition otherNodeDefinition, FormDefinitionValidator formDefinitionValidator, NodePath nodePath);

    /**
     * Get the valid cardinality definitions for the child attribute definition.
     *
     * @param parentOtherNodeDefinition the parent other node definition.
     *
     * @return the valid cardinality definitions for the child attribute definition.
     */
    CardinalityDefinition[] getAttributeDefinitionCardinalities(OtherNodeDefinition parentOtherNodeDefinition);

    /**
     * Get the valid cardinality definitions for the child element definition.
     *
     * @param parentOtherNodeDefinition the parent other node definition.
     *
     * @return the valid cardinality definitions for the child element definition.
     */
    CardinalityDefinition[] getElementDefinitionCardinalities(OtherNodeDefinition parentOtherNodeDefinition);

    /**
     * Get the valid cardinality definitions for the child single element definition.
     *
     * @param parentOtherNodeDefinition the parent other node definition.
     *
     * @return the valid cardinality definitions for the child single element definition.
     */
    CardinalityDefinition[] getSingleElementDefinitionCardinalities(OtherNodeDefinition parentOtherNodeDefinition);

}
