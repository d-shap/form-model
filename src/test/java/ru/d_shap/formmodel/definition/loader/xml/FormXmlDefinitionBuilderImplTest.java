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

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.ServiceFinder;

/**
 * Tests for {@link FormXmlDefinitionBuilderImpl}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionBuilderImplTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionBuilderImplTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isFormDefinitionTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);
        Assertions.assertThat(createBuilder().isFormDefinition(document1.getDocumentElement())).isTrue();

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<form xmlns='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "</form>";
        Document document2 = parse(xml2);
        Assertions.assertThat(createBuilder().isFormDefinition(document2.getDocumentElement())).isTrue();

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form xmlns:ns1='http://example.com'>";
        xml3 += "</ns1:form>";
        Document document3 = parse(xml3);
        Assertions.assertThat(createBuilder().isFormDefinition(document3.getDocumentElement())).isFalse();

        String xml4 = "<?xml version='1.0'?>\n";
        xml4 += "<ns1:FORM xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml4 += "</ns1:FORM>";
        Document document4 = parse(xml4);
        Assertions.assertThat(createBuilder().isFormDefinition(document4.getDocumentElement())).isFalse();

        String xml5 = "<?xml version='1.0'?>\n";
        xml5 += "<form>";
        xml5 += "</form>";
        Document document5 = parse(xml5);
        Assertions.assertThat(createBuilder().isFormDefinition(document5.getDocumentElement())).isFalse();
    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isAttributeDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createAttributeDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isElementDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createElementDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isSingleElementDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createSingleElementDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isFormReferenceDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createFormReferenceDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void isOtherNodeDefinitionTest() {

    }

    /**
     * {@link FormXmlDefinitionBuilderImpl} class test.
     */
    @Test
    public void createOtherNodeDefinitionTest() {

    }

    private FormXmlDefinitionBuilderImpl createBuilder() {
        List<OtherNodeXmlDefinitionBuilder> otherNodeXmlDefinitionBuilders = ServiceFinder.find(OtherNodeXmlDefinitionBuilder.class);
        return new FormXmlDefinitionBuilderImpl(otherNodeXmlDefinitionBuilders);
    }

}
