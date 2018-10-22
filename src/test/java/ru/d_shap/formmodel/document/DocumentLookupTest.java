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
package ru.d_shap.formmodel.document;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.binding.FormBinder;
import ru.d_shap.formmodel.binding.FormInstanceBinderImpl;
import ru.d_shap.formmodel.binding.FormInstanceBuilder;
import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedElementImpl;
import ru.d_shap.formmodel.binding.model.BindingSourceImpl;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

/**
 * Tests for {@link DocumentLookup}.
 *
 * @author Dmitry Shapovalov
 */
public final class DocumentLookupTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public DocumentLookupTest() {
        super();
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void createNewObjectTest() {
        Assertions.assertThat(new DocumentLookup()).isNotNull();
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getDocumentLookupTest() {
        Assertions.assertThat(DocumentLookup.getDocumentLookup()).isNotNull();
        Assertions.assertThat(DocumentLookup.getDocumentLookup()).isNotSameAs(DocumentLookup.getDocumentLookup());
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<document>";
        xml += "<element>value1</element>";
        xml += "<element>value2</element>";
        xml += "</document>";
        Document document = parse(xml);
        List<Element> elements1 = DocumentLookup.getDocumentLookup().getElements(document, "/document");
        Assertions.assertThat(elements1).hasSize(1);
        Assertions.assertThat(elements1.get(0).getTextContent()).isEqualTo("value1value2");

        List<Element> elements2 = DocumentLookup.getDocumentLookup().getElements(document, "//element");
        Assertions.assertThat(elements2).hasSize(2);
        Assertions.assertThat(elements2.get(0).getTextContent()).isEqualTo("value1");
        Assertions.assertThat(elements2.get(1).getTextContent()).isEqualTo("value2");
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWrongReturnTypeTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<document>";
        xml1 += "<element attr='val'>value</element>";
        xml1 += "<!--COMMENT TEXT-->";
        xml1 += "</document>";
        Document document1 = parse(xml1);
        List<Element> elements1 = DocumentLookup.getDocumentLookup().getElements(document1, "//element/@attr");
        Assertions.assertThat(elements1).hasSize(0);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<document>";
        xml2 += "<element>value</element>";
        xml2 += "<!--COMMENT TEXT-->";
        xml2 += "</document>";
        Document document2 = parse(xml2);
        List<Element> elements2 = DocumentLookup.getDocumentLookup().getElements(document2, "//element/following::comment()[1]");
        Assertions.assertThat(elements2).hasSize(0);
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWrongXPathFailTest() {
        try {
            String xml = "<?xml version='1.0'?>\n";
            xml += "<document>";
            xml += "<element>value</element>";
            xml += "<!--COMMENT TEXT-->";
            xml += "</document>";
            Document document = parse(xml);
            DocumentLookup.getDocumentLookup().getElements(document, "//element/wrongaxisname::comment()[1]");
            Assertions.fail("DocumentLookup test fail");
        } catch (DocumentLookupException ex) {
            Assertions.assertThat(ex).hasCause(XPathExpressionException.class);
        }
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWithIdTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='fid' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='seid' type='required' addSAttr='addSVal'>";
        xml1 += "<ns1:element id='eid1' lookup='lookup' type='optional+' repr='repr1' count='3' addAttr='addVal'>";
        xml1 += "</ns1:element>";
        xml1 += "<ns1:element id='eid2' lookup='lookup' type='optional+' repr='repr2' count='0'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "fid");
        List<Element> elements11 = DocumentLookup.getDocumentLookup().getElementsWithId(document1, "eid1");
        Assertions.assertThat(elements11).hasSize(3);
        Assertions.assertThat(elements11.get(0).getAttribute("addAttr")).isEqualTo("addVal");
        Assertions.assertThat(elements11.get(1).getAttribute("addAttr")).isEqualTo("addVal");
        Assertions.assertThat(elements11.get(2).getAttribute("addAttr")).isEqualTo("addVal");
        List<Element> elements12 = DocumentLookup.getDocumentLookup().getElementsWithId(document1, "seid");
        Assertions.assertThat(elements12).hasSize(1);
        Assertions.assertThat(elements12.get(0).getAttribute("addSAttr")).isEqualTo("addSVal");

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='fid' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id' lookup='lookup' repr='repr1' count='1'>";
        xml2 += "</ns1:element>";
        xml2 += "<ns1:element id='id' lookup='lookup' repr='repr2' count='1'>";
        xml2 += "</ns1:element>";
        xml2 += "<ns1:element id='id' lookup='lookup' repr='repr3' count='1'>";
        xml2 += "<ns1:attribute id='id' lookup='lookup' repr='repr4' count='1'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
        FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
        Document document2 = formBinder2.bind(new BindingSourceImpl("source"), "fid");
        List<Element> elements2 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id");
        Assertions.assertThat(elements2).hasSize(4);
        Assertions.assertThat(elements2.get(0).getAttribute("repr")).isEqualTo("repr1");
        Assertions.assertThat(elements2.get(1).getAttribute("repr")).isEqualTo("repr2");
        Assertions.assertThat(elements2.get(2).getAttribute("repr")).isEqualTo("repr3");
        Assertions.assertThat(elements2.get(3).getAttribute("repr")).isEqualTo("repr4");
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getElementsWithAttributeTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='fid' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml1 += "<ns1:single-element id='seid' type='required' addSAttr='addSVal'>";
        xml1 += "<ns1:element id='eid1' lookup='lookup' type='optional+' repr='repr1' count='3' addAttr='addVal'>";
        xml1 += "</ns1:element>";
        xml1 += "<ns1:element id='eid2' lookup='lookup' type='optional+' repr='repr2' count='0'>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "fid");
        List<Element> elements11 = DocumentLookup.getDocumentLookup().getElementsWithAttribute(document1, "addAttr", "addVal");
        Assertions.assertThat(elements11).hasSize(3);
        Assertions.assertThat(elements11.get(0).getAttribute("addAttr")).isEqualTo("addVal");
        Assertions.assertThat(elements11.get(1).getAttribute("addAttr")).isEqualTo("addVal");
        Assertions.assertThat(elements11.get(2).getAttribute("addAttr")).isEqualTo("addVal");
        List<Element> elements12 = DocumentLookup.getDocumentLookup().getElementsWithAttribute(document1, "addSAttr", "addSVal");
        Assertions.assertThat(elements12).hasSize(1);
        Assertions.assertThat(elements12.get(0).getAttribute("addSAttr")).isEqualTo("addSVal");
        List<Element> elements13 = DocumentLookup.getDocumentLookup().getElementsWithAttribute(document1, "addSAttr", "addVal");
        Assertions.assertThat(elements13).hasSize(0);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='fid' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0'>";
        xml2 += "<ns1:element id='id' lookup='lookup' repr='repr1' count='1'>";
        xml2 += "</ns1:element>";
        xml2 += "<ns1:element id='id' lookup='lookup' repr='repr2' count='1'>";
        xml2 += "</ns1:element>";
        xml2 += "<ns1:element id='id' lookup='lookup' repr='repr3' count='1'>";
        xml2 += "<ns1:attribute id='id' lookup='lookup' repr='repr4' count='1'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
        FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
        Document document2 = formBinder2.bind(new BindingSourceImpl("source"), "fid");
        List<Element> elements21 = DocumentLookup.getDocumentLookup().getElementsWithAttribute(document2, "id", "id");
        Assertions.assertThat(elements21).hasSize(4);
        Assertions.assertThat(elements21.get(0).getAttribute("repr")).isEqualTo("repr1");
        Assertions.assertThat(elements21.get(1).getAttribute("repr")).isEqualTo("repr2");
        Assertions.assertThat(elements21.get(2).getAttribute("repr")).isEqualTo("repr3");
        Assertions.assertThat(elements21.get(3).getAttribute("repr")).isEqualTo("repr4");
        List<Element> elements22 = DocumentLookup.getDocumentLookup().getElementsWithAttribute(document2, "id", "fid");
        Assertions.assertThat(elements22).hasSize(0);
        List<Element> elements23 = DocumentLookup.getDocumentLookup().getElementsWithAttribute(document2, "id", "sid");
        Assertions.assertThat(elements23).hasSize(0);
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedElementsTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' repr='repr1' count='1'>";
        xml1 += "<ns1:single-element id='id'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr2' count='3'>";
        xml1 += "<ns1:attribute id='id' lookup='lookup' repr='repr3' count='1'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "<ns1:element id='id' lookup='lookup' repr='repr4' count='0'>";
        xml1 += "</ns1:element>";
        xml1 += "<ns2:otherNode repr='insertInvalidNodeDefinition' valid='true'>";
        xml1 += "</ns2:otherNode>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "id");
        document1.getDocumentElement().getFirstChild().getFirstChild().appendChild(document1.createComment("COMMENT TEXT"));
        List<Element> elements11 = DocumentLookup.getDocumentLookup().getElementsWithId(document1, "id");
        Assertions.assertThat(elements11).hasSize(8);
        List<BindedElement> bindedElements11 = DocumentLookup.getDocumentLookup().getBindedElements(elements11);
        Assertions.assertThat(bindedElements11).hasSize(4);
        Assertions.assertThat(bindedElements11.get(0)).hasToString("Element: repr1[0]");
        Assertions.assertThat(bindedElements11.get(1)).hasToString("Element: repr2[0]");
        Assertions.assertThat(bindedElements11.get(2)).hasToString("Element: repr2[1]");
        Assertions.assertThat(bindedElements11.get(3)).hasToString("Element: repr2[2]");
        List<Element> elements12 = DocumentLookup.getDocumentLookup().getElementsWithId(document1, "wrong id");
        Assertions.assertThat(elements12).hasSize(0);
        List<BindedElement> bindedElements12 = DocumentLookup.getDocumentLookup().getBindedElements(elements12);
        Assertions.assertThat(bindedElements12).hasSize(0);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
        xml2 += "<ns1:single-element id='id2'>";
        xml2 += "<ns1:element id='id3' lookup='lookup' type='optional+' repr='repr2' count='3'>";
        xml2 += "<ns1:attribute id='id4' lookup='lookup' repr='repr3' count='1'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "<ns1:element id='id5' lookup='lookup' repr='repr4' count='0'>";
        xml2 += "</ns1:element>";
        xml2 += "<ns2:otherNode repr='insertInvalidNodeDefinition' valid='true'>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
        FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
        Document document2 = formBinder2.bind(new BindingSourceImpl("source"), "id");
        document2.getDocumentElement().getFirstChild().getFirstChild().appendChild(document2.createComment("COMMENT TEXT"));
        List<Element> elements21 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id1");
        Assertions.assertThat(elements21).hasSize(1);
        List<BindedElement> bindedElements21 = DocumentLookup.getDocumentLookup().getBindedElements(elements21);
        Assertions.assertThat(bindedElements21).hasSize(1);
        Assertions.assertThat(bindedElements21.get(0)).hasToString("Element: repr1[0]");
        List<Element> elements22 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id2");
        Assertions.assertThat(elements22).hasSize(1);
        List<BindedElement> bindedElements22 = DocumentLookup.getDocumentLookup().getBindedElements(elements22);
        Assertions.assertThat(bindedElements22).hasSize(3);
        Assertions.assertThat(bindedElements22.get(0)).hasToString("Element: repr2[0]");
        Assertions.assertThat(bindedElements22.get(1)).hasToString("Element: repr2[1]");
        Assertions.assertThat(bindedElements22.get(2)).hasToString("Element: repr2[2]");
        List<Element> elements23 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id3");
        Assertions.assertThat(elements23).hasSize(3);
        List<BindedElement> bindedElements23 = DocumentLookup.getDocumentLookup().getBindedElements(elements23);
        Assertions.assertThat(bindedElements23).hasSize(3);
        Assertions.assertThat(bindedElements23.get(0)).hasToString("Element: repr2[0]");
        Assertions.assertThat(bindedElements23.get(1)).hasToString("Element: repr2[1]");
        Assertions.assertThat(bindedElements23.get(2)).hasToString("Element: repr2[2]");
        List<Element> elements24 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id4");
        Assertions.assertThat(elements24).hasSize(3);
        List<BindedElement> bindedElements24 = DocumentLookup.getDocumentLookup().getBindedElements(elements24);
        Assertions.assertThat(bindedElements24).hasSize(0);
        List<Element> elements25 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id5");
        Assertions.assertThat(elements25).hasSize(0);
        List<BindedElement> bindedElements25 = DocumentLookup.getDocumentLookup().getBindedElements(elements25);
        Assertions.assertThat(bindedElements25).hasSize(0);

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml3 += "<ns1:single-element id='id'>";
        xml3 += "<ns1:element id='id' lookup='lookup' repr='repr' count='0'>";
        xml3 += "</ns1:element>";
        xml3 += "<ns2:otherNode repr='otherNodeRepr' valid='true'>";
        xml3 += "</ns2:otherNode>";
        xml3 += "</ns1:single-element>";
        xml3 += "</ns1:form>";
        FormDefinitions formDefinitions3 = createFormDefinitionsFromXml(xml3);
        FormBinder formBinder3 = new FormBinder(formDefinitions3, new FormInstanceBinderImpl());
        Document document3 = formBinder3.bind(new BindingSourceImpl("source"), "id");
        document3.getDocumentElement().getFirstChild().getFirstChild().appendChild(document3.createComment("COMMENT TEXT"));
        List<Element> elements31 = DocumentLookup.getDocumentLookup().getElementsWithId(document3, "id");
        Assertions.assertThat(elements31).hasSize(1);
        List<BindedElement> bindedElements31 = DocumentLookup.getDocumentLookup().getBindedElements(elements31);
        Assertions.assertThat(bindedElements31).hasSize(0);
        Assertions.assertThat(elements31.get(0).getFirstChild().getUserData(FormInstanceBuilder.USER_DATA_NODE_DEFINITION)).hasToString("otherNodeRepr");
        Assertions.assertThat(elements31.get(0).getFirstChild().getUserData(FormInstanceBuilder.USER_DATA_BINDED_OBJECT)).isNull();
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedElementsWithClassTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' repr='repr1' count='1'>";
        xml1 += "<ns1:single-element id='id'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr2' count='3'>";
        xml1 += "<ns1:attribute id='id' lookup='lookup' repr='repr3' count='1'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "<ns1:element id='id' lookup='lookup' repr='repr4' count='0'>";
        xml1 += "</ns1:element>";
        xml1 += "<ns2:otherNode repr='insertInvalidNodeDefinition' valid='true'>";
        xml1 += "</ns2:otherNode>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "id");
        document1.getDocumentElement().getFirstChild().getFirstChild().appendChild(document1.createComment("COMMENT TEXT"));
        List<Element> elements1 = DocumentLookup.getDocumentLookup().getElementsWithId(document1, "id");
        Assertions.assertThat(elements1).hasSize(8);
        List<BindedElement> bindedElements11 = DocumentLookup.getDocumentLookup().getBindedElements(elements1, BindedElement.class);
        Assertions.assertThat(bindedElements11).hasSize(4);
        Assertions.assertThat(bindedElements11.get(0)).hasToString("Element: repr1[0]");
        Assertions.assertThat(bindedElements11.get(1)).hasToString("Element: repr2[0]");
        Assertions.assertThat(bindedElements11.get(2)).hasToString("Element: repr2[1]");
        Assertions.assertThat(bindedElements11.get(3)).hasToString("Element: repr2[2]");
        List<BindedElementImpl> bindedElements12 = DocumentLookup.getDocumentLookup().getBindedElements(elements1, BindedElementImpl.class);
        Assertions.assertThat(bindedElements12).hasSize(4);
        Assertions.assertThat(bindedElements12.get(0)).hasToString("Element: repr1[0]");
        Assertions.assertThat(bindedElements12.get(1)).hasToString("Element: repr2[0]");
        Assertions.assertThat(bindedElements12.get(2)).hasToString("Element: repr2[1]");
        Assertions.assertThat(bindedElements12.get(3)).hasToString("Element: repr2[2]");
        List<AnotherBindedElement> bindedElements13 = DocumentLookup.getDocumentLookup().getBindedElements(elements1, AnotherBindedElement.class);
        Assertions.assertThat(bindedElements13).hasSize(0);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
        xml2 += "<ns1:single-element id='id2'>";
        xml2 += "<ns1:element id='id3' lookup='lookup' type='optional+' repr='repr2' count='3'>";
        xml2 += "<ns1:attribute id='id4' lookup='lookup' repr='repr3' count='1'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "<ns1:element id='id5' lookup='lookup' repr='repr4' count='0'>";
        xml2 += "</ns1:element>";
        xml2 += "<ns2:otherNode repr='insertInvalidNodeDefinition' valid='true'>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
        FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
        Document document2 = formBinder2.bind(new BindingSourceImpl("source"), "id");
        document2.getDocumentElement().getFirstChild().getFirstChild().appendChild(document2.createComment("COMMENT TEXT"));
        List<Element> elements21 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id1");
        Assertions.assertThat(elements21).hasSize(1);
        List<BindedElement> bindedElements211 = DocumentLookup.getDocumentLookup().getBindedElements(elements21, BindedElement.class);
        Assertions.assertThat(bindedElements211).hasSize(1);
        Assertions.assertThat(bindedElements211.get(0)).hasToString("Element: repr1[0]");
        List<BindedElementImpl> bindedElements212 = DocumentLookup.getDocumentLookup().getBindedElements(elements21, BindedElementImpl.class);
        Assertions.assertThat(bindedElements212).hasSize(1);
        Assertions.assertThat(bindedElements212.get(0)).hasToString("Element: repr1[0]");
        List<AnotherBindedElement> bindedElements213 = DocumentLookup.getDocumentLookup().getBindedElements(elements21, AnotherBindedElement.class);
        Assertions.assertThat(bindedElements213).hasSize(0);
        List<Element> elements22 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id2");
        Assertions.assertThat(elements22).hasSize(1);
        List<BindedElement> bindedElements221 = DocumentLookup.getDocumentLookup().getBindedElements(elements22, BindedElement.class);
        Assertions.assertThat(bindedElements221).hasSize(3);
        Assertions.assertThat(bindedElements221.get(0)).hasToString("Element: repr2[0]");
        Assertions.assertThat(bindedElements221.get(1)).hasToString("Element: repr2[1]");
        Assertions.assertThat(bindedElements221.get(2)).hasToString("Element: repr2[2]");
        List<BindedElementImpl> bindedElements222 = DocumentLookup.getDocumentLookup().getBindedElements(elements22, BindedElementImpl.class);
        Assertions.assertThat(bindedElements222).hasSize(3);
        Assertions.assertThat(bindedElements222.get(0)).hasToString("Element: repr2[0]");
        Assertions.assertThat(bindedElements222.get(1)).hasToString("Element: repr2[1]");
        Assertions.assertThat(bindedElements222.get(2)).hasToString("Element: repr2[2]");
        List<AnotherBindedElement> bindedElements223 = DocumentLookup.getDocumentLookup().getBindedElements(elements22, AnotherBindedElement.class);
        Assertions.assertThat(bindedElements223).hasSize(0);
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getBindedElementsWithNullClassFailTest() {
        String xml = "<?xml version='1.0'?>\n";
        xml += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml += "<ns1:element id='id' lookup='lookup' repr='repr1' count='1'>";
        xml += "<ns1:single-element id='id'>";
        xml += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr2' count='3'>";
        xml += "<ns1:attribute id='id' lookup='lookup' repr='repr3' count='1'>";
        xml += "</ns1:attribute>";
        xml += "</ns1:element>";
        xml += "<ns1:element id='id' lookup='lookup' repr='repr4' count='0'>";
        xml += "</ns1:element>";
        xml += "<ns2:otherNode repr='insertInvalidNodeDefinition' valid='true'>";
        xml += "</ns2:otherNode>";
        xml += "</ns1:single-element>";
        xml += "</ns1:element>";
        xml += "</ns1:form>";
        FormDefinitions formDefinitions = createFormDefinitionsFromXml(xml);
        FormBinder formBinder = new FormBinder(formDefinitions, new FormInstanceBinderImpl());
        Document document = formBinder.bind(new BindingSourceImpl("source"), "id");
        List<Element> elements = DocumentLookup.getDocumentLookup().getElementsWithId(document, "id");
        Assertions.assertThat(elements).hasSize(8);
        DocumentLookup.getDocumentLookup().getBindedElements(elements, null);
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedAttributesTest() {
        String xml1 = "<?xml version='1.0'?>\n";
        xml1 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml1 += "<ns1:element id='id' lookup='lookup' repr='repr1' count='1'>";
        xml1 += "<ns1:single-element id='id'>";
        xml1 += "<ns1:element id='id' lookup='lookup' type='optional+' repr='repr2' count='3'>";
        xml1 += "<ns1:attribute id='id' lookup='lookup' repr='repr3' count='1'>";
        xml1 += "</ns1:attribute>";
        xml1 += "</ns1:element>";
        xml1 += "<ns1:element id='id' lookup='lookup' repr='repr4' count='0'>";
        xml1 += "</ns1:element>";
        xml1 += "<ns2:otherNode repr='insertInvalidNodeDefinition' valid='true'>";
        xml1 += "</ns2:otherNode>";
        xml1 += "</ns1:single-element>";
        xml1 += "</ns1:element>";
        xml1 += "</ns1:form>";
        FormDefinitions formDefinitions1 = createFormDefinitionsFromXml(xml1);
        FormBinder formBinder1 = new FormBinder(formDefinitions1, new FormInstanceBinderImpl());
        Document document1 = formBinder1.bind(new BindingSourceImpl("source"), "id");
        document1.getDocumentElement().getFirstChild().getFirstChild().appendChild(document1.createComment("COMMENT TEXT"));
        List<Element> elements11 = DocumentLookup.getDocumentLookup().getElementsWithId(document1, "id");
        Assertions.assertThat(elements11).hasSize(8);
        List<BindedAttribute> bindedAttributes11 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements11);
        Assertions.assertThat(bindedAttributes11).hasSize(3);
        Assertions.assertThat(bindedAttributes11.get(0)).hasToString("Attribute: repr3");
        Assertions.assertThat(bindedAttributes11.get(1)).hasToString("Attribute: repr3");
        Assertions.assertThat(bindedAttributes11.get(2)).hasToString("Attribute: repr3");
        List<Element> elements12 = DocumentLookup.getDocumentLookup().getElementsWithId(document1, "wrong id");
        Assertions.assertThat(elements12).hasSize(0);
        List<BindedAttribute> bindedAttributes12 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements12);
        Assertions.assertThat(bindedAttributes12).hasSize(0);

        String xml2 = "<?xml version='1.0'?>\n";
        xml2 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml2 += "<ns1:element id='id1' lookup='lookup' repr='repr1' count='1'>";
        xml2 += "<ns1:single-element id='id2'>";
        xml2 += "<ns1:element id='id3' lookup='lookup' type='optional+' repr='repr2' count='3'>";
        xml2 += "<ns1:attribute id='id4' lookup='lookup' repr='repr3' count='1'>";
        xml2 += "</ns1:attribute>";
        xml2 += "</ns1:element>";
        xml2 += "<ns1:element id='id5' lookup='lookup' repr='repr4' count='0'>";
        xml2 += "</ns1:element>";
        xml2 += "<ns2:otherNode repr='insertInvalidNodeDefinition' valid='true'>";
        xml2 += "</ns2:otherNode>";
        xml2 += "</ns1:single-element>";
        xml2 += "</ns1:element>";
        xml2 += "</ns1:form>";
        FormDefinitions formDefinitions2 = createFormDefinitionsFromXml(xml2);
        FormBinder formBinder2 = new FormBinder(formDefinitions2, new FormInstanceBinderImpl());
        Document document2 = formBinder2.bind(new BindingSourceImpl("source"), "id");
        document2.getDocumentElement().getFirstChild().getFirstChild().appendChild(document2.createComment("COMMENT TEXT"));
        List<Element> elements21 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id1");
        Assertions.assertThat(elements21).hasSize(1);
        List<BindedAttribute> bindedAttributes21 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements21);
        Assertions.assertThat(bindedAttributes21).hasSize(0);
        List<Element> elements22 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id2");
        Assertions.assertThat(elements22).hasSize(1);
        List<BindedAttribute> bindedAttributes22 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements22);
        Assertions.assertThat(bindedAttributes22).hasSize(0);
        List<Element> elements23 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id3");
        Assertions.assertThat(elements23).hasSize(3);
        List<BindedAttribute> bindedAttributes23 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements23);
        Assertions.assertThat(bindedAttributes23).hasSize(0);
        List<Element> elements24 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id4");
        Assertions.assertThat(elements24).hasSize(3);
        List<BindedAttribute> bindedAttributes24 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements24);
        Assertions.assertThat(bindedAttributes24).hasSize(3);
        Assertions.assertThat(bindedAttributes24.get(0)).hasToString("Attribute: repr3");
        Assertions.assertThat(bindedAttributes24.get(1)).hasToString("Attribute: repr3");
        Assertions.assertThat(bindedAttributes24.get(2)).hasToString("Attribute: repr3");
        List<Element> elements25 = DocumentLookup.getDocumentLookup().getElementsWithId(document2, "id5");
        Assertions.assertThat(elements25).hasSize(0);
        List<BindedAttribute> bindedAttributes25 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements25);
        Assertions.assertThat(bindedAttributes25).hasSize(0);

        String xml3 = "<?xml version='1.0'?>\n";
        xml3 += "<ns1:form id='id' xmlns:ns1='http://d-shap.ru/schema/form-model/1.0' xmlns:ns2='http://d-shap.ru/schema/form-model-other-node/1.0'>";
        xml3 += "<ns1:single-element id='id'>";
        xml3 += "<ns1:element id='id' lookup='lookup' repr='repr' count='0'>";
        xml3 += "</ns1:element>";
        xml3 += "<ns2:otherNode repr='otherNodeRepr' valid='true'>";
        xml3 += "</ns2:otherNode>";
        xml3 += "</ns1:single-element>";
        xml3 += "</ns1:form>";
        FormDefinitions formDefinitions3 = createFormDefinitionsFromXml(xml3);
        FormBinder formBinder3 = new FormBinder(formDefinitions3, new FormInstanceBinderImpl());
        Document document3 = formBinder3.bind(new BindingSourceImpl("source"), "id");
        document3.getDocumentElement().getFirstChild().getFirstChild().appendChild(document3.createComment("COMMENT TEXT"));
        List<Element> elements31 = DocumentLookup.getDocumentLookup().getElementsWithId(document3, "id");
        Assertions.assertThat(elements31).hasSize(1);
        List<BindedAttribute> bindedAttributes31 = DocumentLookup.getDocumentLookup().getBindedAttributes(elements31);
        Assertions.assertThat(bindedAttributes31).hasSize(0);
        Assertions.assertThat(elements31.get(0).getFirstChild().getUserData(FormInstanceBuilder.USER_DATA_NODE_DEFINITION)).hasToString("otherNodeRepr");
        Assertions.assertThat(elements31.get(0).getFirstChild().getUserData(FormInstanceBuilder.USER_DATA_BINDED_OBJECT)).isNull();
    }

    /**
     * {@link DocumentLookup} class test.
     */
    @Test
    public void getBindedAttributesWithClassTest() {

    }

}
