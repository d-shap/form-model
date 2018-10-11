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
package ru.d_shap.formmodel;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;

/**
 * Helper class to create messages.
 *
 * @author Dmitry Shapovalov
 */
public final class Messages {

    public static final String SEPARATOR = "/";

    private Messages() {
        super();
    }

    /**
     * Helper class to create node representation messages.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Representation {

        private Representation() {
            super();
        }

        /**
         * Get the form definition representation.
         *
         * @param source the form's source.
         * @param group  the form's group.
         * @param id     the form's ID.
         *
         * @return the form definition representation.
         */
        public static String getFormDefinitionRepresentation(final String source, final String group, final String id) {
            return "{" + NullValueHelper.getValue(source) + "}" + FormDefinition.ELEMENT_NAME + "[" + getIdRepresentation(group, id) + "]";
        }

        /**
         * Get the attribute definition representation.
         *
         * @param id the attribute's ID.
         *
         * @return the attribute definition representation.
         */
        public static String getAttributeDefinitionRepresentation(final String id) {
            return AttributeDefinition.ELEMENT_NAME + "[" + getIdRepresentation(id) + "]";
        }

        /**
         * Get the element definition representation.
         *
         * @param id the element's ID.
         *
         * @return the element definition representation.
         */
        public static String getElementDefinitionRepresentation(final String id) {
            return ElementDefinition.ELEMENT_NAME + "[" + getIdRepresentation(id) + "]";
        }

        /**
         * Get the single element definition representation.
         *
         * @param id the single element's ID.
         *
         * @return the single element definition representation.
         */
        public static String getSingleElementDefinitionRepresentation(final String id) {
            return SingleElementDefinition.ELEMENT_NAME + "[" + getIdRepresentation(id) + "]";
        }

        /**
         * Get the form reference definition representation.
         *
         * @param group the form reference's group.
         * @param id    the form reference's ID.
         *
         * @return the form reference definition representation.
         */
        public static String getFormReferenceDefinitionRepresentation(final String group, final String id) {
            return FormReferenceDefinition.ELEMENT_NAME + "[" + getIdRepresentation(group, id) + "]";
        }

        /**
         * Get the node's ID representation.
         *
         * @param id the node's ID.
         *
         * @return the node's ID representation.
         */
        public static String getIdRepresentation(final String id) {
            return "@" + NullValueHelper.getValue(id);
        }

        /**
         * Get the node's ID representation.
         *
         * @param group the node's group.
         * @param id    the node's ID.
         *
         * @return the node's ID representation.
         */
        public static String getIdRepresentation(final String group, final String id) {
            return "@" + NullValueHelper.getValue(group) + ":" + NullValueHelper.getValue(id);
        }

        /**
         * Get the node representation.
         *
         * @param node the node.
         *
         * @return the node representation.
         */
        public static String getNodeRepresentation(final Node node) {
            if (node == null) {
                return String.valueOf((Object) null);
            } else {
                if (node.getNamespaceURI() == null) {
                    return node.getNodeName();
                } else {
                    return "{" + node.getNamespaceURI() + "}" + node.getLocalName();
                }
            }
        }

    }

    /**
     * Helper class to create form definition validation messages.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Validation {

        private Validation() {
            super();
        }

        /**
         * Get the error message when the form definition is not valid.
         *
         * @param element the invalid form definition.
         *
         * @return the error message.
         */
        public static String getFormDefinitionIsNotValidMessage(final Element element) {
            return "[Form definition is not valid: " + Representation.getNodeRepresentation(element) + "]";
        }

        /**
         * Get the error message when the attribute definition is not valid.
         *
         * @param element the invalid attribute definition.
         *
         * @return the error message.
         */
        public static String getAttributeDefinitionIsNotValidMessage(final Element element) {
            return "[Attribute definition is not valid: " + Representation.getNodeRepresentation(element) + "]";
        }

        /**
         * Get the error message when the element definition is not valid.
         *
         * @param element the invalid element definition.
         *
         * @return the error message.
         */
        public static String getElementDefinitionIsNotValidMessage(final Element element) {
            return "[Element definition is not valid: " + Representation.getNodeRepresentation(element) + "]";
        }

        /**
         * Get the error message when the single element definition is not valid.
         *
         * @param element the invalid single element definition.
         *
         * @return the error message.
         */
        public static String getSingleElementDefinitionIsNotValidMessage(final Element element) {
            return "[Single element definition is not valid: " + Representation.getNodeRepresentation(element) + "]";
        }

        /**
         * Get the error message when the form reference definition is not valid.
         *
         * @param element the invalid form reference definition.
         *
         * @return the error message.
         */
        public static String getFormReferenceDefinitionIsNotValidMessage(final Element element) {
            return "[Form reference definition is not valid: " + Representation.getNodeRepresentation(element) + "]";
        }

        /**
         * Get the error message when the other node definition is not valid.
         *
         * @param element the invalid other node definition.
         *
         * @return the error message.
         */
        public static String getOtherNodeDefinitionIsNotValidMessage(final Element element) {
            return "[Other node definition is not valid: " + Representation.getNodeRepresentation(element) + "]";
        }

        /**
         * Get the error message when the child XML element is not valid.
         *
         * @param element the invalid child XML element.
         *
         * @return the error message.
         */
        public static String getChildElementIsNotValidMessage(final Element element) {
            return "[Child element is not valid: " + Representation.getNodeRepresentation(element) + "]";
        }

        /**
         * Get the error message when the form definition is not unique.
         *
         * @param formDefinitionKey the invalid form definition key.
         * @param source1           the source of the first form definition.
         * @param source2           the source of the second form definition.
         *
         * @return the error message.
         */
        public static String getFormDefinitionIsNotUniqueMessage(final FormDefinitionKey formDefinitionKey, final String source1, final String source2) {
            return "[Form definition is not unique: " + formDefinitionKey + ", (" + source1 + "), (" + source2 + ")]";
        }

        /**
         * Get the error message when the source is empty.
         *
         * @return the error message.
         */
        public static String getSourceIsEmptyMessage() {
            return "[Source is empty]";
        }

        /**
         * Get the error message when the group is not valid.
         *
         * @param group the invalid group.
         *
         * @return the error message.
         */
        public static String getGroupIsNotValidMessage(final String group) {
            return "[Group is not valid: " + group + "]";
        }

        /**
         * Get the error message when the ID is empty.
         *
         * @return the error message.
         */
        public static String getIdIsEmptyMessage() {
            return "[ID is empty]";
        }

        /**
         * Get the error message when the ID is not valid.
         *
         * @param id the invalid ID.
         *
         * @return the error message.
         */
        public static String getIdIsNotValidMessage(final String id) {
            return "[ID is not valid: " + id + "]";
        }

        /**
         * Get the error message when the lookup is empty.
         *
         * @return the error message.
         */
        public static String getLookupIsEmptyMessage() {
            return "[Lookup is empty]";
        }

        /**
         * Get the error message when the cardinality definition is empty.
         *
         * @return the error message.
         */
        public static String getCardinalityDefinitionIsEmptyMessage() {
            return "[Cardinality definition is empty]";
        }

        /**
         * Get the error message when the cardinality definition is not valid.
         *
         * @param cardinality the invalid cardinality definition.
         *
         * @return the error message.
         */
        public static String getCardinalityDefinitionIsNotValidMessage(final String cardinality) {
            return "[Cardinality definition is not valid: " + cardinality + "]";
        }

        /**
         * Get the error message when the form definition key is not valid.
         *
         * @param formDefinitionKey the invalid form definition key.
         *
         * @return the error message.
         */
        public static String getFormDefinitionKeyIsNotValidMessage(final FormDefinitionKey formDefinitionKey) {
            return "[Form definition key is not valid: " + formDefinitionKey + "]";
        }

    }

    /**
     * Helper class to create form binding messages.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Binding {

        private Binding() {
            super();
        }

        /**
         * Get the error message when the form is not present.
         *
         * @param formDefinition the form definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getFormIsNotPresentMessage(final FormDefinition formDefinition) {
            return "[Form is not present: " + formDefinition + "]";
        }

        /**
         * Get the error message when the required attribute is not present.
         *
         * @param attributeDefinition the attribute definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getRequiredAttributeIsNotPresentMessage(final AttributeDefinition attributeDefinition) {
            return "[Required attribute is not present: " + attributeDefinition + "]";
        }

        /**
         * Get the error message when the prohibited attribute is present.
         *
         * @param attributeDefinition the attribute definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getProhibitedAttributeIsPresentMessage(final AttributeDefinition attributeDefinition) {
            return "[Prohibited attribute is present: " + attributeDefinition + "]";
        }

        /**
         * Get the error message when the required element is not present.
         *
         * @param elementDefinition the element definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getRequiredElementIsNotPresentMessage(final ElementDefinition elementDefinition) {
            return "[Required element is not present: " + elementDefinition + "]";
        }

        /**
         * Get the error message when the required element is present more than once.
         *
         * @param elementDefinition the element definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getRequiredElementIsPresentMoreThanOnceMessage(final ElementDefinition elementDefinition) {
            return "[Required element is present more than once: " + elementDefinition + "]";
        }

        /**
         * Get the error message when the optional element is present more than once.
         *
         * @param elementDefinition the element definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getOptionalElementIsPresentMoreThanOnceMessage(final ElementDefinition elementDefinition) {
            return "[Optional element is present more than once: " + elementDefinition + "]";
        }

        /**
         * Get the error message when the prohibited element is present.
         *
         * @param elementDefinition the element definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getProhibitedElementIsPresentMessage(final ElementDefinition elementDefinition) {
            return "[Prohibited element is present: " + elementDefinition + "]";
        }

        /**
         * Get the error message when multiple single element elements are present.
         *
         * @param singleElementDefinition the single element definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getMultipleSingleElementsArePresentMessage(final SingleElementDefinition singleElementDefinition) {
            return "[Multiple single elements are present: " + singleElementDefinition + "]";
        }

        /**
         * Get the error message when the required single element is not present.
         *
         * @param singleElementDefinition the single element definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getRequiredSingleElementIsNotPresentMessage(final SingleElementDefinition singleElementDefinition) {
            return "[Required single element is not present: " + singleElementDefinition + "]";
        }

        /**
         * Get the error message when the prohibited single element is present.
         *
         * @param singleElementDefinition the single element definition that can not be binded.
         *
         * @return the error message.
         */
        public static String getProhibitedSingleElementIsPresentMessage(final SingleElementDefinition singleElementDefinition) {
            return "[Prohibited single element is present: " + singleElementDefinition + "]";
        }

    }

}
