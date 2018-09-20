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

import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link ServiceFinder}.
 *
 * @author Dmitry Shapovalov
 */
public final class ServiceFinderTest {

    /**
     * Test class constructor.
     */
    public ServiceFinderTest() {
        super();
    }

    /**
     * {@link ServiceFinder} class test.
     */
    @Test
    public void findServiceTest() {
        List<ServiceInterface> services = ServiceFinder.find(ServiceInterface.class);
        Assertions.assertThat(services).hasSize(1);
        Assertions.assertThat(services.get(0)).isInstanceOf(ServiceImplementation.class);
    }

    /**
     * {@link ServiceFinder} class test.
     */
    @Test
    public void findNoServiceTest() {
        List<XmlDocumentBuilderConfigurator> services = ServiceFinder.find(XmlDocumentBuilderConfigurator.class);
        Assertions.assertThat(services).isEmpty();
    }

}
