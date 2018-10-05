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

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link FormXmlDefinitionsLoader}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsLoaderTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionsLoaderTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionsLoader} class test.
     */
    @Test
    public void getXmlDocumentBuilderTest() {
        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader1 = new FormXmlDefinitionsLoaderImpl();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl().getXmlDocumentBuilder()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl().getXmlDocumentBuilder()).isNotSameAs(formXmlDefinitionsLoader1.getXmlDocumentBuilder());

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader2 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl()).getXmlDocumentBuilder()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl()).getXmlDocumentBuilder()).isNotSameAs(formXmlDefinitionsLoader2.getXmlDocumentBuilder());

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader3 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1);
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1).getXmlDocumentBuilder()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1).getXmlDocumentBuilder()).isSameAs(formXmlDefinitionsLoader3.getXmlDocumentBuilder());
    }

    /**
     * {@link FormXmlDefinitionsLoader} class test.
     */
    @Test
    public void getXmlDocumentValidatorTest() {
        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader1 = new FormXmlDefinitionsLoaderImpl();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl().getXmlDocumentValidator()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl().getXmlDocumentValidator()).isNotSameAs(formXmlDefinitionsLoader1.getXmlDocumentValidator());

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader2 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl()).getXmlDocumentValidator()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl()).getXmlDocumentValidator()).isNotSameAs(formXmlDefinitionsLoader2.getXmlDocumentValidator());

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader3 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1);
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1).getXmlDocumentValidator()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1).getXmlDocumentValidator()).isSameAs(formXmlDefinitionsLoader3.getXmlDocumentValidator());
    }

    /**
     * {@link FormXmlDefinitionsLoader} class test.
     */
    @Test
    public void getFormXmlDefinitionBuilderTest() {
        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader1 = new FormXmlDefinitionsLoaderImpl();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl().getFormXmlDefinitionBuilder()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl().getFormXmlDefinitionBuilder()).isNotSameAs(formXmlDefinitionsLoader1.getFormXmlDefinitionBuilder());

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader2 = new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl());
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl()).getFormXmlDefinitionBuilder()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(new XmlDocumentBuilderConfiguratorImpl()).getFormXmlDefinitionBuilder()).isNotSameAs(formXmlDefinitionsLoader2.getFormXmlDefinitionBuilder());

        FormXmlDefinitionsLoaderImpl formXmlDefinitionsLoader3 = new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1);
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1).getFormXmlDefinitionBuilder()).isNotNull();
        Assertions.assertThat(new FormXmlDefinitionsLoaderImpl(formXmlDefinitionsLoader1).getFormXmlDefinitionBuilder()).isSameAs(formXmlDefinitionsLoader3.getFormXmlDefinitionBuilder());
    }

}
