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

import java.io.File;
import java.io.FileFilter;
import java.net.URL;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;

/**
 * Tests for {@link FormXmlDefinitionsFileSystemLoader}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsFileSystemLoaderTest extends BaseFormModelTest {

    public static final String ROOT_FOLDER = FormXmlDefinitionsFileSystemLoaderTest.class.getPackage().getName().replaceAll("\\.", "/");

    /**
     * Test class constructor.
     */
    public FormXmlDefinitionsFileSystemLoaderTest() {
        super();
    }

    /**
     * {@link FormXmlDefinitionsFileSystemLoader} class test.
     */
    @Test
    public void loadTest() {

    }

    /**
     * {@link FormXmlDefinitionsFileSystemLoader} class test.
     */
    @Test
    public void acceptTest() {
        FileFilter fileFilter = new FormXmlDefinitionsFileSystemLoader.DefaultFileFilter();
        URL url = getClass().getClassLoader().getResource(ROOT_FOLDER);
        File parentDirectory = new File(url.getFile());
        Assertions.assertThat(fileFilter.accept(parentDirectory)).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.xml"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.XML"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.XmL"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.xMl"))).isTrue();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form"))).isFalse();
        Assertions.assertThat(fileFilter.accept(new File(parentDirectory, "form.txt"))).isFalse();
    }

}
