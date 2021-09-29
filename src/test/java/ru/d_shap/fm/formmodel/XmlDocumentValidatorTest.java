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
package ru.d_shap.fm.formmodel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.xml.sax.SAXException;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link XmlDocumentValidator}.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlDocumentValidatorTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public XmlDocumentValidatorTest() {
        super();
    }

    /**
     * {@link XmlDocumentValidator} class test.
     */
    @Test
    public void createNewObjectTest() {
        Assertions.assertThat(new XmlDocumentValidator(XmlDocumentValidator.FORM_MODEL_SCHEMA_PATH)).isNotNull();
        Assertions.assertThat(new XmlDocumentValidator(XmlDocumentValidator.FORM_INSTANCE_SCHEMA_PATH)).isNotNull();

        Assertions.assertThat(new XmlDocumentValidator(XmlDocumentValidator.class.getClassLoader().getResourceAsStream(XmlDocumentValidator.FORM_MODEL_SCHEMA_PATH))).isNotNull();
        Assertions.assertThat(new XmlDocumentValidator(XmlDocumentValidator.class.getClassLoader().getResourceAsStream(XmlDocumentValidator.FORM_INSTANCE_SCHEMA_PATH))).isNotNull();

        try {
            String xml = "<?xml version='1.0'?>\n";
            xml += "<xs:schema xmlns:xs='http://www.w3.org/2001/XMLSchema'>";
            xml += "<xs:element name='element' type='elementType' />";
            xml += "</xs:schema>";
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            new XmlDocumentValidator(inputStream);
            Assertions.fail("XmlDocumentValidator test fail");
        } catch (XmlDocumentValidatorConfigurationException ex) {
            Assertions.assertThat(ex).hasCause(SAXException.class);
        }

        try {
            new XmlDocumentValidator(new ReadErrorInputStream());
            Assertions.fail("XmlDocumentValidator test fail");
        } catch (XmlDocumentValidatorConfigurationException ex) {
            Assertions.assertThat(ex).hasCause(SAXException.class);
        }
    }

    /**
     * {@link XmlDocumentValidator} class test.
     */
    @Test
    public void getFormModelDocumentValidatorTest() {
        Assertions.assertThat(XmlDocumentValidator.getFormModelDocumentValidator()).isNotNull();
        Assertions.assertThat(XmlDocumentValidator.getFormModelDocumentValidator()).isNotSameAs(XmlDocumentValidator.getFormModelDocumentValidator());
    }

    /**
     * {@link XmlDocumentValidator} class test.
     */
    @Test
    public void getFormInstanceDocumentValidatorTest() {
        Assertions.assertThat(XmlDocumentValidator.getFormInstanceDocumentValidator()).isNotNull();
        Assertions.assertThat(XmlDocumentValidator.getFormInstanceDocumentValidator()).isNotSameAs(XmlDocumentValidator.getFormInstanceDocumentValidator());
    }

    /**
     * {@link XmlDocumentValidator} class test.
     */
    @Test
    public void validateNodeTest() {
        String validXml = "<?xml version='1.0'?>\n";
        validXml += "<fm:form xmlns:fm='http://d-shap.ru/schema/form-model/1.0'>";
        validXml += "</fm:form>";
        try {
            XmlDocumentValidator.getFormModelDocumentValidator().validate(parse(validXml));
        } catch (SAXException ex) {
            Assertions.fail("XmlDocumentValidator test fail");
        }
        try {
            XmlDocumentValidator.getFormModelDocumentValidator().validate(parse(validXml).getDocumentElement());
        } catch (SAXException ex) {
            Assertions.fail("XmlDocumentValidator test fail");
        }

        try {
            String invalidXml = "<?xml version='1.0'?>\n";
            invalidXml += "<fm:formS xmlns:fm='http://d-shap.ru/schema/form-model/1.0'>";
            invalidXml += "</fm:formS>";
            XmlDocumentValidator.getFormModelDocumentValidator().validate(parse(invalidXml));
            Assertions.fail("XmlDocumentValidator test fail");
        } catch (SAXException ex) {
            Assertions.assertThat(ex).isNotNull();
        }
        try {
            String invalidXml = "<?xml version='1.0'?>\n";
            invalidXml += "<fm:formS xmlns:fm='http://d-shap.ru/schema/form-model/1.0'>";
            invalidXml += "</fm:formS>";
            XmlDocumentValidator.getFormModelDocumentValidator().validate(parse(invalidXml).getDocumentElement());
            Assertions.fail("XmlDocumentValidator test fail");
        } catch (SAXException ex) {
            Assertions.assertThat(ex).isNotNull();
        }
    }

    /**
     * {@link XmlDocumentValidator} class test.
     */
    @Test
    public void validateSourceTest() {
        String validXml = "<?xml version='1.0'?>\n";
        validXml += "<fm:form xmlns:fm='http://d-shap.ru/schema/form-model/1.0'>";
        validXml += "</fm:form>";
        try {
            XmlDocumentValidator.getFormModelDocumentValidator().validate(new StreamSource(new StringReader(validXml)));
        } catch (SAXException ex) {
            Assertions.fail("XmlDocumentValidator test fail");
        }
        try {
            String invalidXml = "<?xml version='1.0'?>\n";
            invalidXml += "<fm:formS xmlns:fm='http://d-shap.ru/schema/form-model/1.0'>";
            invalidXml += "</fm:formS>";
            XmlDocumentValidator.getFormModelDocumentValidator().validate(new StreamSource(new StringReader(invalidXml)));
            Assertions.fail("XmlDocumentValidator test fail");
        } catch (SAXException ex) {
            Assertions.assertThat(ex).isNotNull();
        }
        try {
            try {
                XmlDocumentValidator.getFormModelDocumentValidator().validate(new StreamSource(new ReadErrorInputStream()));
                Assertions.fail("XmlDocumentValidator test fail");
            } catch (SAXException ex) {
                Assertions.fail("XmlDocumentValidator test fail");
            }
        } catch (InputSourceException ex) {
            Assertions.assertThat(ex).hasMessage("READ ERROR!");
            Assertions.assertThat(ex).hasCause(IOException.class);
        }
    }

    /**
     * {@link XmlDocumentValidator} class test.
     */
    @Test
    public void closeTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<xs:schema xmlns:xs='http://www.w3.org/2001/XMLSchema'>";
        xml += "<xs:element name='element' type='xs:string' />";
        xml += "</xs:schema>";
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        CloseableInputStream closeableInputStream = new CloseableInputStream(inputStream);
        Assertions.assertThat(closeableInputStream.isClosed()).isFalse();
        new XmlDocumentValidator(closeableInputStream);
        Assertions.assertThat(closeableInputStream.isClosed()).isTrue();
    }

}
