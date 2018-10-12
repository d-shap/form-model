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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * XML document validator.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentValidator {

    public static final String SCHEMA_PARENT_FOLDER = XmlDocumentValidator.class.getPackage().getName().replaceAll("\\.", "/");

    public static final String FORM_MODEL_SCHEMA_PATH = SCHEMA_PARENT_FOLDER + "/form-model-1_0.xsd";

    public static final String FORM_INSTANCE_SCHEMA_PATH = SCHEMA_PARENT_FOLDER + "/form-instance-1_0.xsd";

    private final Validator _validator;

    /**
     * Create new object.
     *
     * @param schemaPath path to the schema.
     */
    public XmlDocumentValidator(final String schemaPath) {
        this(XmlDocumentValidator.class.getClassLoader().getResourceAsStream(schemaPath));
    }

    /**
     * Create new object.
     *
     * @param inputStream input stream with the schema.
     */
    public XmlDocumentValidator(final InputStream inputStream) {
        super();
        try {
            try {
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Source source = new StreamSource(inputStream);
                Schema schema = schemaFactory.newSchema(source);
                _validator = schema.newValidator();
            } finally {
                inputStream.close();
            }
        } catch (IOException | SAXException ex) {
            throw new XmlDocumentValidatorConfigurationException(ex);
        }
    }

    /**
     * Get the form-model document validator instance.
     *
     * @return the form-model document validator instance.
     */
    public static XmlDocumentValidator getFormModelDocumentValidator() {
        return new XmlDocumentValidator(FORM_MODEL_SCHEMA_PATH);
    }

    /**
     * Get the form-instance document validator instance.
     *
     * @return the form-instance document validator instance.
     */
    public static XmlDocumentValidator getFormInstanceDocumentValidator() {
        return new XmlDocumentValidator(FORM_INSTANCE_SCHEMA_PATH);
    }

    /**
     * Validate the XML node against the schema.
     *
     * @param node the XML node to validate.
     *
     * @throws SAXException if the XML node is not valid.
     */
    public void validate(final Node node) throws SAXException {
        validate(new DOMSource(node));
    }

    /**
     * Validate the XML source against the schema.
     *
     * @param source the XML source to validate.
     *
     * @throws SAXException if the XML source is not valid.
     */
    public void validate(final Source source) throws SAXException {
        try {
            _validator.validate(source);
        } catch (IOException ex) {
            throw new InputSourceException(ex);
        }
    }

}
