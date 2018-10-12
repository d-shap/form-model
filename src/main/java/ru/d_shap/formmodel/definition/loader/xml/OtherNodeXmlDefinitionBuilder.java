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
package ru.d_shap.formmodel.definition.loader.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * Builder for the other node definition, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public interface OtherNodeXmlDefinitionBuilder {

    /**
     * Validate the XML node against the schema.
     *
     * @param node the XML node to validate.
     *
     * @throws SAXException if the XML node is not valid.
     */
    void validate(Node node) throws SAXException;

    /**
     * Create the other node definition for the specified XML element.
     *
     * @param parentElement            the parent XML element.
     * @param element                  the specified XML element.
     * @param formXmlDefinitionBuilder builder for the form definition, XML implementation.
     * @param nodePath                 the current node path.
     *
     * @return the other node definition.
     */
    OtherNodeDefinition createOtherNodeDefinition(Element parentElement, Element element, FormXmlDefinitionBuilder formXmlDefinitionBuilder, NodePath nodePath);

    /**
     * Get the default cardinality definition of the child attribute definition.
     *
     * @param parentElement the parent XML element.
     *
     * @return the default cardinality definition of the child attribute definition.
     */
    CardinalityDefinition getAttributeDefinitionCardinality(Element parentElement);

    /**
     * Get the default cardinality definition of the child element definition.
     *
     * @param parentElement the parent XML element.
     *
     * @return the default cardinality definition of the child element definition.
     */
    CardinalityDefinition getElementDefinitionCardinality(Element parentElement);

    /**
     * Get the default cardinality definition of the child single element definition.
     *
     * @param parentElement the parent XML element.
     *
     * @return the default cardinality definition of the child single element definition.
     */
    CardinalityDefinition getSingleElementDefinitionCardinality(Element parentElement);

}
