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

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;

/**
 * Helper class to create messages.
 *
 * @author Dmitry Shapovalov
 */
public final class Messages {

    private Messages() {
        super();
    }

    /**
     * Helper class to create node representation messages.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Representation {

        public static final String SEPARATOR = "/";

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
            return "{" + source + "}" + FormDefinition.ELEMENT_NAME + "[" + getIdRepresentation(group, id) + "]";
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
         * Get the choice definition representation.
         *
         * @param id the choice's ID.
         *
         * @return the choice definition representation.
         */
        public static String getChoiceDefinitionRepresentation(final String id) {
            return ChoiceDefinition.ELEMENT_NAME + "[" + getIdRepresentation(id) + "]";
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
         * Get the other node definition representation.
         *
         * @param element the XML element of the other node definition.
         *
         * @return the other node definition representation.
         */
        public static String getOtherNodeDefinitionRepresentation(final Element element) {
            return "{" + element.getNamespaceURI() + "}" + element.getLocalName();
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
            return "@" + group + ":" + id;
        }

        /**
         * Get the node's ID representation.
         *
         * @param id the node's ID.
         *
         * @return the node's ID representation.
         */
        public static String getIdRepresentation(final String id) {
            return "@" + id;
        }

    }

    /**
     * Helper class to create validation exception messages.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Validation {

        private Validation() {
            super();
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
         * Get the error message when the ID is not empty.
         *
         * @param id the invalid ID.
         *
         * @return the error message.
         */
        public static String getIdIsNotEmptyMessage(final String id) {
            return "[ID is not empty: " + id + "]";
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
         * Get the error message when the ID is not unique.
         *
         * @param id the invalid ID.
         *
         * @return the error message.
         */
        public static String getIdIsNotUniqueMessage(final String id) {
            return "[ID is not unique: " + id + "]";
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
         * Get the error message when the cardinality is empty.
         *
         * @return the error message.
         */
        public static String getCardinalityIsEmptyMessage() {
            return "[Cardinality is empty]";
        }

        /**
         * Get the error message when the cardinality is not valid.
         *
         * @param cardinality the invalid cardinality.
         *
         * @return the error message.
         */
        public static String getCardinalityIsNotValidMessage(final String cardinality) {
            return "[Cardinality is not valid: " + cardinality + "]";
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
         * Get the error message when the form reference is not unique.
         *
         * @param formDefinitionKey the invalid form definition key.
         *
         * @return the error message.
         */
        public static String getFormReferenceIsNotUniqueMessage(final FormDefinitionKey formDefinitionKey) {
            return "[Form reference is not unique: " + Representation.getIdRepresentation(formDefinitionKey.getGroup(), formDefinitionKey.getId()) + "]";
        }

        /**
         * Get the error message when the form reference can not be resolved.
         *
         * @param formDefinitionKey the invalid form definition key.
         *
         * @return the error message.
         */
        public static String getUnresolvedFormReferenceMessage(final FormDefinitionKey formDefinitionKey) {
            return "[Form reference can not be resolved: " + Representation.getIdRepresentation(formDefinitionKey.getGroup(), formDefinitionKey.getId()) + "]";
        }

    }

    /**
     * Helper class to create form definition load exception messages.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Load {

        private Load() {
            super();
        }

    }

}