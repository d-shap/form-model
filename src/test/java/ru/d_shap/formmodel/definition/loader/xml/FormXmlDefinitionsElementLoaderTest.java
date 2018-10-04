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
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Tests for {@link FormXmlDefinitionsElementLoader}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsElementLoaderTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionsElementLoaderTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionsElementLoader} class test.
     */
    @Test
    public void loadTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id1' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "</ns1:form>";
        Document document1 = parse(xml1);

        FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader11 = new FormXmlDefinitionsElementLoader(document1.getDocumentElement(), "source11");
        List<FormDefinition> formDefinitions11 = formXmlDefinitionsElementLoader11.load();
        Assertions.assertThat(formDefinitions11).hasSize(1);
        Assertions.assertThat(formDefinitions11.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions11.get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions11.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions11.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions11.get(0).getSource()).isEqualTo("source11");

        FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader12 = new FormXmlDefinitionsElementLoader(formXmlDefinitionsElementLoader11, document1.getDocumentElement(), "source12");
        List<FormDefinition> formDefinitions12 = formXmlDefinitionsElementLoader12.load();
        Assertions.assertThat(formDefinitions12).hasSize(1);
        Assertions.assertThat(formDefinitions12.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions12.get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions12.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions12.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions12.get(0).getSource()).isEqualTo("source12");

        Assertions.assertThat(formXmlDefinitionsElementLoader11.getXmlDocumentBuilder()).isSameAs(formXmlDefinitionsElementLoader12.getXmlDocumentBuilder());
        Assertions.assertThat(formXmlDefinitionsElementLoader11.getXmlDocumentValidator()).isSameAs(formXmlDefinitionsElementLoader12.getXmlDocumentValidator());
        Assertions.assertThat(formXmlDefinitionsElementLoader11.getFormXmlDefinitionBuilder()).isSameAs(formXmlDefinitionsElementLoader12.getFormXmlDefinitionBuilder());

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<root xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:form id='id1'>";
        xml2 += "</ns1:form>";
        xml2 += "<ns1:form id='id2'>";
        xml2 += "</ns1:form>";
        xml2 += "<ns1:form id='id3'>";
        xml2 += "</ns1:form>";
        xml2 += "<form id='id4'>";
        xml2 += "</form>";
        xml2 += "</root>";
        Document document2 = parse(xml2);

        FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader21 = new FormXmlDefinitionsElementLoader((Element) document2.getDocumentElement().getChildNodes().item(0), "source11");
        List<FormDefinition> formDefinitions21 = formXmlDefinitionsElementLoader21.load();
        Assertions.assertThat(formDefinitions21).hasSize(1);
        Assertions.assertThat(formDefinitions21.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions21.get(0).getId()).isEqualTo("id1");
        Assertions.assertThat(formDefinitions21.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions21.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions21.get(0).getSource()).isEqualTo("source11");

        FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader22 = new FormXmlDefinitionsElementLoader((Element) document2.getDocumentElement().getChildNodes().item(1), "source12");
        List<FormDefinition> formDefinitions22 = formXmlDefinitionsElementLoader22.load();
        Assertions.assertThat(formDefinitions22).hasSize(1);
        Assertions.assertThat(formDefinitions22.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions22.get(0).getId()).isEqualTo("id2");
        Assertions.assertThat(formDefinitions22.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions22.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions22.get(0).getSource()).isEqualTo("source12");

        FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader23 = new FormXmlDefinitionsElementLoader((Element) document2.getDocumentElement().getChildNodes().item(2), "source13");
        List<FormDefinition> formDefinitions23 = formXmlDefinitionsElementLoader23.load();
        Assertions.assertThat(formDefinitions23).hasSize(1);
        Assertions.assertThat(formDefinitions23.get(0).getGroup()).isEqualTo("");
        Assertions.assertThat(formDefinitions23.get(0).getId()).isEqualTo("id3");
        Assertions.assertThat(formDefinitions23.get(0).getAllNodeDefinitions()).isEmpty();
        Assertions.assertThat(formDefinitions23.get(0).getOtherAttributeNames()).isEmpty();
        Assertions.assertThat(formDefinitions23.get(0).getSource()).isEqualTo("source13");

        FormXmlDefinitionsElementLoader formXmlDefinitionsElementLoader24 = new FormXmlDefinitionsElementLoader((Element) document2.getDocumentElement().getChildNodes().item(3), "source14");
        List<FormDefinition> formDefinitions24 = formXmlDefinitionsElementLoader24.load();
        Assertions.assertThat(formDefinitions24).hasSize(0);
    }

}
