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

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * Validator for the form model definition elements.
 *
 * @author Dmitry Shapovalov
 */
public interface FormModelDefinitionValidator {

    /**
     * Validate the form definition.
     *
     * @param formDefinition the form definition.
     * @param nodePath       the current node path.
     */
    void validate(FormDefinition formDefinition, NodePath nodePath);

    /**
     * Validate the element definition.
     *
     * @param parentNodeDefinition the parent node definition.
     * @param elementDefinition    the element definition.
     * @param nodePath             the current node path.
     */
    void validate(NodeDefinition parentNodeDefinition, ElementDefinition elementDefinition, NodePath nodePath);

    /**
     * Validate the choice definition.
     *
     * @param parentNodeDefinition the parent node definition.
     * @param choiceDefinition     the choice definition.
     * @param nodePath             the current node path.
     */
    void validate(NodeDefinition parentNodeDefinition, ChoiceDefinition choiceDefinition, NodePath nodePath);

    /**
     * Validate the form reference definition.
     *
     * @param parentNodeDefinition    the parent node definition.
     * @param formReferenceDefinition the form reference definition.
     * @param nodePath                the current node path.
     */
    void validate(NodeDefinition parentNodeDefinition, FormReferenceDefinition formReferenceDefinition, NodePath nodePath);

    /**
     * Validate the attribute definition.
     *
     * @param parentNodeDefinition the parent node definition.
     * @param attributeDefinition  the attribute definition.
     * @param nodePath             the current node path.
     */
    void validate(NodeDefinition parentNodeDefinition, AttributeDefinition attributeDefinition, NodePath nodePath);

}
