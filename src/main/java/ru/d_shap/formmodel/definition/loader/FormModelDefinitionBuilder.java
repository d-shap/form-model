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
package ru.d_shap.formmodel.definition.loader;

import java.util.Set;

import org.w3c.dom.Element;

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;

/**
 * Builder for the form definition elements.
 *
 * @author Dmitry Shapovalov
 */
public interface FormModelDefinitionBuilder {

    String NAMESPACE = "http://d-shap.ru/schema/form-model/1.0";

    String FORM_DEFINITION_ELEMENT_NAME = FormDefinition.ELEMENT_NAME;

    String FORM_DEFINITION_ATTRIBUTE_GROUP = FormDefinition.ATTRIBUTE_GROUP;

    String FORM_DEFINITION_ATTRIBUTE_ID = FormDefinition.ATTRIBUTE_ID;

    Set<String> FORM_DEFINITION_ATTRIBUTE_NAMES = FormDefinition.ATTRIBUTE_NAMES;

    Set<String> FORM_DEFINITION_CHILD_ELEMENT_NAMES = FormDefinition.CHILD_ELEMENT_NAMES;

    String ELEMENT_DEFINITION_ELEMENT_NAME = ElementDefinition.ELEMENT_NAME;

    String ELEMENT_DEFINITION_ATTRIBUTE_ID = ElementDefinition.ATTRIBUTE_ID;

    String ELEMENT_DEFINITION_ATTRIBUTE_LOOKUP = ElementDefinition.ATTRIBUTE_LOOKUP;

    String ELEMENT_DEFINITION_ATTRIBUTE_TYPE = ElementDefinition.ATTRIBUTE_TYPE;

    Set<String> ELEMENT_DEFINITION_ATTRIBUTE_NAMES = ElementDefinition.ATTRIBUTE_NAMES;

    Set<String> ELEMENT_DEFINITION_CHILD_ELEMENT_NAMES = ElementDefinition.CHILD_ELEMENT_NAMES;

    String CHOICE_DEFINITION_ELEMENT_NAME = ChoiceDefinition.ELEMENT_NAME;

    String CHOICE_DEFINITION_ATTRIBUTE_ID = ChoiceDefinition.ATTRIBUTE_ID;

    String CHOICE_DEFINITION_ATTRIBUTE_TYPE = ChoiceDefinition.ATTRIBUTE_TYPE;

    Set<String> CHOICE_DEFINITION_ATTRIBUTE_NAMES = ChoiceDefinition.ATTRIBUTE_NAMES;

    Set<String> CHOICE_DEFINITION_CHILD_ELEMENT_NAMES = ChoiceDefinition.CHILD_ELEMENT_NAMES;

    String FORM_REFERENCE_DEFINITION_ELEMENT_NAME = FormReferenceDefinition.ELEMENT_NAME;

    String FORM_REFERENCE_DEFINITION_ATTRIBUTE_GROUP = FormReferenceDefinition.ATTRIBUTE_GROUP;

    String FORM_REFERENCE_DEFINITION_ATTRIBUTE_ID = FormReferenceDefinition.ATTRIBUTE_ID;

    Set<String> FORM_REFERENCE_DEFINITION_ATTRIBUTE_NAMES = FormReferenceDefinition.ATTRIBUTE_NAMES;

    Set<String> FORM_REFERENCE_DEFINITION_CHILD_ELEMENT_NAMES = FormReferenceDefinition.CHILD_ELEMENT_NAMES;

    String ATTRIBUTE_DEFINITION_ELEMENT_NAME = AttributeDefinition.ELEMENT_NAME;

    String ATTRIBUTE_DEFINITION_ATTRIBUTE_ID = AttributeDefinition.ATTRIBUTE_ID;

    String ATTRIBUTE_DEFINITION_ATTRIBUTE_LOOKUP = AttributeDefinition.ATTRIBUTE_LOOKUP;

    String ATTRIBUTE_DEFINITION_ATTRIBUTE_TYPE = AttributeDefinition.ATTRIBUTE_TYPE;

    Set<String> ATTRIBUTE_DEFINITION_ATTRIBUTE_NAMES = AttributeDefinition.ATTRIBUTE_NAMES;

    Set<String> ATTRIBUTE_DEFINITION_CHILD_ELEMENT_NAMES = AttributeDefinition.CHILD_ELEMENT_NAMES;

    /**
     * Check if the specified XML element is the form definition.
     *
     * @param element the specified XML element to check.
     *
     * @return true if the specified XML element is the form definition.
     */
    boolean isFormDefinitionElement(Element element);

    /**
     * Create the form definition for the specified XML element.
     *
     * @param element  the specified XML element.
     * @param source   the form's source.
     * @param nodePath the current node path.
     *
     * @return the form definition.
     */
    FormDefinition createFormDefinition(Element element, String source, NodePath nodePath);

    /**
     * Check if the specified XML element is the element definition.
     *
     * @param element the specified XML element to check.
     *
     * @return true if the specified XML element is the element definition.
     */
    boolean isElementDefinitionElement(Element element);

    /**
     * Create the element definition for the specified XML element.
     *
     * @param parentElement the parent XML element.
     * @param element       the specified XML element.
     * @param nodePath      the current node path.
     *
     * @return the element definition.
     */
    ElementDefinition createElementDefinition(Element parentElement, Element element, NodePath nodePath);

    /**
     * Check if the specified XML element is the choice definition.
     *
     * @param element the specified XML element to check.
     *
     * @return true if the specified XML element is the choice definition.
     */
    boolean isChoiceDefinitionElement(Element element);

    /**
     * Create the choice definition for the specified XML element.
     *
     * @param parentElement the parent XML element.
     * @param element       the specified XML element.
     * @param nodePath      the current node path.
     *
     * @return the choice definition.
     */
    ChoiceDefinition createChoiceDefinition(Element parentElement, Element element, NodePath nodePath);

    /**
     * Check if the specified XML element is the form reference definition.
     *
     * @param element the specified XML element to check.
     *
     * @return true if the specified XML element is the form reference definition.
     */
    boolean isFormReferenceDefinitionElement(Element element);

    /**
     * Create the form reference definition for the specified XML element.
     *
     * @param parentElement the parent XML element.
     * @param element       the specified XML element.
     * @param nodePath      the current node path.
     *
     * @return the form reference definition.
     */
    FormReferenceDefinition createFormReferenceDefinition(Element parentElement, Element element, NodePath nodePath);

    /**
     * Check if the specified XML element is the attribute definition.
     *
     * @param element the specified XML element to check.
     *
     * @return if the specified XML element is the attribute definition.
     */
    boolean isAttributeDefinitionElement(Element element);

    /**
     * Create the attribute definition for the specified XML element.
     *
     * @param parentElement the parent XML element.
     * @param element       the specified XML element.
     * @param nodePath      the current node path.
     *
     * @return the attribute definition.
     */
    AttributeDefinition createAttributeDefinition(Element parentElement, Element element, NodePath nodePath);

}
