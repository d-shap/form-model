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

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link DefaultOtherNodeXmlDefinition}.
 *
 * @author Dmitry Shapovalov
 */
public final class DefaultOtherNodeXmlDefinitionTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public DefaultOtherNodeXmlDefinitionTest() {
        super();
    }

    /**
     * {@link DefaultOtherNodeXmlDefinition} class test.
     */
    @Test
    public void getElementTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document xmlns:ns1='http://example.com'>";
        xml += "<ns1:element>value</ns1:element>";
        xml += "</document>";
        Document document = parse(xml);

        DefaultOtherNodeXmlDefinition otherNodeXmlDefinition0 = new DefaultOtherNodeXmlDefinition(null);
        Assertions.assertThat(otherNodeXmlDefinition0.getElement()).isNull();

        Element element1 = document.getDocumentElement();
        DefaultOtherNodeXmlDefinition otherNodeXmlDefinition1 = new DefaultOtherNodeXmlDefinition(element1);
        Assertions.assertThat(otherNodeXmlDefinition1.getElement()).isSameAs(element1);

        Element element2 = (Element) element1.getFirstChild();
        DefaultOtherNodeXmlDefinition otherNodeXmlDefinition2 = new DefaultOtherNodeXmlDefinition(element2);
        Assertions.assertThat(otherNodeXmlDefinition2.getElement()).isSameAs(element2);
    }

    /**
     * {@link DefaultOtherNodeXmlDefinition} class test.
     */
    @Test
    public void toStringTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document xmlns:ns1='http://example.com'>";
        xml += "<ns1:element>value</ns1:element>";
        xml += "</document>";
        Document document = parse(xml);

        DefaultOtherNodeXmlDefinition otherNodeXmlDefinition0 = new DefaultOtherNodeXmlDefinition(null);
        Assertions.assertThat(otherNodeXmlDefinition0).hasToString("null");

        Element element1 = document.getDocumentElement();
        DefaultOtherNodeXmlDefinition otherNodeXmlDefinition1 = new DefaultOtherNodeXmlDefinition(element1);
        Assertions.assertThat(otherNodeXmlDefinition1).hasToString("document");

        Element element2 = (Element) element1.getFirstChild();
        DefaultOtherNodeXmlDefinition otherNodeXmlDefinition2 = new DefaultOtherNodeXmlDefinition(element2);
        Assertions.assertThat(otherNodeXmlDefinition2).hasToString("{http://example.com}ns1:element");
    }

}
