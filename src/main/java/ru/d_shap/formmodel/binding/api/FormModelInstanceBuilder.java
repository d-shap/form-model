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
package ru.d_shap.formmodel.binding.api;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * Builder for the form instance elements.
 *
 * @author Dmitry Shapovalov
 */
public interface FormModelInstanceBuilder {

    String NAMESPACE = "http://d-shap.ru/schema/form-instance/1.0";

    String FORM_INSTANCE_ELEMENT_NAME = FormDefinition.ELEMENT_NAME;

    String FORM_INSTANCE_ATTRIBUTE_GROUP = FormDefinition.ATTRIBUTE_GROUP;

    String FORM_INSTANCE_ATTRIBUTE_ID = FormDefinition.ATTRIBUTE_ID;

    String ELEMENT_INSTANCE_ELEMENT_NAME = ElementDefinition.ELEMENT_NAME;

    String ELEMENT_INSTANCE_ATTRIBUTE_ID = ElementDefinition.ATTRIBUTE_ID;

    String CHOICE_INSTANCE_ELEMENT_NAME = ChoiceDefinition.ELEMENT_NAME;

    String CHOICE_INSTANCE_ATTRIBUTE_ID = ChoiceDefinition.ATTRIBUTE_ID;

    String FORM_REFERENCE_INSTANCE_ELEMENT_NAME = FormReferenceDefinition.ELEMENT_NAME;

    String FORM_REFERENCE_INSTANCE_ATTRIBUTE_GROUP = FormReferenceDefinition.ATTRIBUTE_GROUP;

    String FORM_REFERENCE_INSTANCE_ATTRIBUTE_ID = FormReferenceDefinition.ATTRIBUTE_ID;

    String ATTRIBUTE_INSTANCE_ELEMENT_NAME = AttributeDefinition.ELEMENT_NAME;

    String ATTRIBUTE_INSTANCE_ATTRIBUTE_ID = AttributeDefinition.ATTRIBUTE_ID;

    /**
     * Create the binded XML element for the specified form definition and attach it to the owner document.
     *
     * @param document       the owner document.
     * @param formDefinition the specified form definition.
     * @param nodePath       the current node path.
     */
    void addFormInstance(Document document, FormDefinition formDefinition, NodePath nodePath);

    /**
     * Create the binded XML element for the specified element definition and attach it to the owner document.
     *
     * @param document          the owner document.
     * @param parentElement     the parent binded XML element.
     * @param elementDefinition the specified element definition.
     * @param nodePath          the current node path.
     */
    void addElementInstance(Document document, Element parentElement, ElementDefinition elementDefinition, NodePath nodePath);

    /**
     * Create the binded XML element for the specified choice definition and attach it to the owner document.
     *
     * @param document         the owner document.
     * @param parentElement    the parent binded XML element.
     * @param choiceDefinition the specified choice definition.
     * @param nodePath         the current node path.
     */
    void addChoiceInstance(Document document, Element parentElement, ChoiceDefinition choiceDefinition, NodePath nodePath);

    /**
     * Create the binded XML element for the specified form reference definition and attach it to the owner document.
     *
     * @param document                the owner document.
     * @param parentElement           the parent binded XML element.
     * @param formReferenceDefinition the specified form reference definition.
     * @param nodePath                the current node path.
     */
    void addFormReferenceInstance(Document document, Element parentElement, FormReferenceDefinition formReferenceDefinition, NodePath nodePath);

    /**
     * Create the binded XML element for the specified attribute definition and attach it to the owner document.
     *
     * @param document            the owner document.
     * @param parentElement       the parent binded XML element.
     * @param attributeDefinition the specified attribute definition.
     * @param nodePath            the current node path.
     */
    void addAttributeInstance(Document document, Element parentElement, AttributeDefinition attributeDefinition, NodePath nodePath);

}
