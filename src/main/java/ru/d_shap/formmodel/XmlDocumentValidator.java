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
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * XML document validator.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentValidator {

    private static final SchemaFactory SCHEMA_FACTORY;

    static {
        SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }

    private static final String SCHEMA_PARENT_FOLDER = XmlDocumentValidator.class.getPackage().getName().replaceAll("\\.", "/");

    private static final String FORM_MODEL_SCHEMA_PATH = SCHEMA_PARENT_FOLDER + "/form-model-1_0.xsd";

    private static final String FORM_INSTANCE_SCHEMA_PATH = SCHEMA_PARENT_FOLDER + "/form-instance-1_0.xsd";

    private static final XmlDocumentValidator FORM_MODEL_SCHEMA_VALIDATOR = new XmlDocumentValidator(FORM_MODEL_SCHEMA_PATH);

    private static final XmlDocumentValidator FORM_INSTANCE_SCHEMA_VALIDATOR = new XmlDocumentValidator(FORM_INSTANCE_SCHEMA_PATH);

    private final Schema _schema;

    private final Validator _validator;

    private XmlDocumentValidator(final String schemaPath) {
        super();
        URL url = getClass().getClassLoader().getResource(schemaPath);
        try {
            try (InputStream inputStream = url.openStream()) {
                Source source = new StreamSource(inputStream);
                _schema = SCHEMA_FACTORY.newSchema(source);
                _validator = _schema.newValidator();
            }
        } catch (IOException | SAXException ex) {
            throw new XmlDocumentValidatorException(ex);
        }
    }

    /**
     * Get the form-model document validator.
     *
     * @return the form-model document validator.
     */
    public static XmlDocumentValidator getFormModelSchemaValidator() {
        return FORM_MODEL_SCHEMA_VALIDATOR;
    }

    /**
     * Get the form-instance document validator.
     *
     * @return the form-instance document validator.
     */
    public static XmlDocumentValidator getFormInstanceSchemaValidator() {
        return FORM_INSTANCE_SCHEMA_VALIDATOR;
    }

    /**
     * Get the schema.
     *
     * @return the schema.
     */
    public Schema getSchema() {
        return _schema;
    }

    /**
     * Validate the XML document against the schema.
     *
     * @param document the XML document to validate.
     */
    public void validate(final Document document) {
        try {
            _validator.validate(new DOMSource(document));
        } catch (IOException | SAXException ex) {
            throw new XmlDocumentValidatorException(ex);
        }
    }

}
