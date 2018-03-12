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
package ru.d_shap.formmodel.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link FormDefinitions}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionsTest() {
        super();
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void createNewObjectTest() {

    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void getFormDefinitionTest() {
        List<FormDefinition> formDefinitionList1 = new ArrayList<>();
        FormDefinitions formDefinitions1 = new FormDefinitions(formDefinitionList1);
        try {
            formDefinitions1.getFormDefinition(null);
            Assertions.fail("FormDefinitions test fail");
        } catch (UndefinedFormDefinitionException ex) {
            Assertions.assertThat(ex).hasMessage("Form definition is undefined: null");
        }

        List<FormDefinition> formDefinitionList2 = new ArrayList<>();
        FormDefinition formDefinition2 = new FormDefinition("id", new HashMap<String, String>(), new ArrayList<NodeDefinition>(), new Object());
        formDefinitionList2.add(formDefinition2);
        FormDefinitions formDefinitions2 = new FormDefinitions(formDefinitionList2);
        Assertions.assertThat(formDefinitions2.getFormDefinition("id")).isSameAs(formDefinition2);
        try {
            formDefinitions2.getFormDefinition(null);
            Assertions.fail("FormDefinitions test fail");
        } catch (UndefinedFormDefinitionException ex) {
            Assertions.assertThat(ex).hasMessage("Form definition is undefined: null");
        }
        try {
            formDefinitions2.getFormDefinition("wrong id");
            Assertions.fail("FormDefinitions test fail");
        } catch (UndefinedFormDefinitionException ex) {
            Assertions.assertThat(ex).hasMessage("Form definition is undefined: wrong id");
        }

        List<FormDefinition> formDefinitionList3 = new ArrayList<>();
        FormDefinition formDefinition31 = new FormDefinition("id1", new HashMap<String, String>(), new ArrayList<NodeDefinition>(), new Object());
        formDefinitionList3.add(formDefinition31);
        FormDefinition formDefinition32 = new FormDefinition("id2", new HashMap<String, String>(), new ArrayList<NodeDefinition>(), new Object());
        formDefinitionList3.add(formDefinition32);
        FormDefinitions formDefinitions3 = new FormDefinitions(formDefinitionList3);
        Assertions.assertThat(formDefinitions3.getFormDefinition("id1")).isSameAs(formDefinition31);
        Assertions.assertThat(formDefinitions3.getFormDefinition("id2")).isSameAs(formDefinition32);
        try {
            formDefinitions3.getFormDefinition(null);
            Assertions.fail("FormDefinitions test fail");
        } catch (UndefinedFormDefinitionException ex) {
            Assertions.assertThat(ex).hasMessage("Form definition is undefined: null");
        }
        try {
            formDefinitions3.getFormDefinition("wrong id");
            Assertions.fail("FormDefinitions test fail");
        } catch (UndefinedFormDefinitionException ex) {
            Assertions.assertThat(ex).hasMessage("Form definition is undefined: wrong id");
        }
    }

    /**
     * {@link FormDefinitions} class test.
     */
    @Test
    public void getFormDefinitionsTest() {

    }

}
