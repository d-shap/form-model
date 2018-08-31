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
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;

/**
 * Builder for the form model elements.
 *
 * @author Dmitry Shapovalov
 */
public interface FormModelElementBuilder {

    String NAMESPACE = "http://d-shap.ru/schema/form-model";

    String FORM_DEFINITION_ELEMENT_NAME = FormDefinition.ELEMENT_NAME;

    String FORM_DEFINITION_ATTRIBUTE_GROUP = FormDefinition.ATTRIBUTE_GROUP;

    String FORM_DEFINITION_ATTRIBUTE_ID = FormDefinition.ATTRIBUTE_ID;

    Set<String> FORM_DEFINITION_ATTRIBUTE_NAMES = FormDefinition.ATTRIBUTE_NAMES;

    String ELEMENT_DEFINITION_ELEMENT_NAME = ElementDefinition.ELEMENT_NAME;

    String ELEMENT_DEFINITION_ATTRIBUTE_ID = ElementDefinition.ATTRIBUTE_ID;

    String ELEMENT_DEFINITION_ATTRIBUTE_LOOKUP = ElementDefinition.ATTRIBUTE_LOOKUP;

    String ELEMENT_DEFINITION_ATTRIBUTE_TYPE = ElementDefinition.ATTRIBUTE_TYPE;

    Set<String> ELEMENT_DEFINITION_ATTRIBUTE_NAMES = ElementDefinition.ATTRIBUTE_NAMES;

    String CHOICE_DEFINITION_ELEMENT_NAME = ChoiceDefinition.ELEMENT_NAME;

    String CHOICE_DEFINITION_ATTRIBUTE_ID = ChoiceDefinition.ATTRIBUTE_ID;

    String CHOICE_DEFINITION_ATTRIBUTE_TYPE = ChoiceDefinition.ATTRIBUTE_TYPE;

    Set<String> CHOICE_DEFINITION_ATTRIBUTE_NAMES = ChoiceDefinition.ATTRIBUTE_NAMES;

    String FORM_REFERENCE_DEFINITION_ELEMENT_NAME = FormReferenceDefinition.ELEMENT_NAME;

    String FORM_REFERENCE_DEFINITION_ATTRIBUTE_GROUP = FormReferenceDefinition.ATTRIBUTE_GROUP;

    String FORM_REFERENCE_DEFINITION_ATTRIBUTE_ID = FormReferenceDefinition.ATTRIBUTE_ID;

    Set<String> FORM_REFERENCE_DEFINITION_ATTRIBUTE_NAMES = FormReferenceDefinition.ATTRIBUTE_NAMES;

    String ATTRIBUTE_DEFINITION_ELEMENT_NAME = AttributeDefinition.ELEMENT_NAME;

    String ATTRIBUTE_DEFINITION_ATTRIBUTE_ID = AttributeDefinition.ATTRIBUTE_ID;

    String ATTRIBUTE_DEFINITION_ATTRIBUTE_LOOKUP = AttributeDefinition.ATTRIBUTE_LOOKUP;

    String ATTRIBUTE_DEFINITION_ATTRIBUTE_TYPE = AttributeDefinition.ATTRIBUTE_TYPE;

    Set<String> ATTRIBUTE_DEFINITION_ATTRIBUTE_NAMES = AttributeDefinition.ATTRIBUTE_NAMES;

    /**
     * Create the form definition for the specified XML element.
     *
     * @param element the specified XML element.
     * @param source  the form's source.
     *
     * @return the form definition.
     */
    FormDefinition createFormDefinition(Element element, String source);

    /**
     * Create the element definition for the specified XML element.
     *
     * @param element                      the specified XML element.
     * @param defaultCardinalityDefinition default element definition's cardinality.
     *
     * @return the element definition.
     */
    ElementDefinition createElementDefinition(Element element, CardinalityDefinition defaultCardinalityDefinition);

    /**
     * Create the choice definition for the specified XML element.
     *
     * @param element                      the specified XML element.
     * @param defaultCardinalityDefinition default choice definition's cardinality.
     *
     * @return the choice definition.
     */
    ChoiceDefinition createChoiceDefinition(Element element, CardinalityDefinition defaultCardinalityDefinition);

    /**
     * Create the form reference definition for the specified XML element.
     *
     * @param element the specified XML element.
     *
     * @return the form reference definition.
     */
    FormReferenceDefinition createFormReferenceDefinition(Element element);

    /**
     * Create the attribute definition for the specified XML element.
     *
     * @param element the specified XML element.
     *
     * @return the attribute definition.
     */
    AttributeDefinition createAttributeDefinition(Element element);

}
