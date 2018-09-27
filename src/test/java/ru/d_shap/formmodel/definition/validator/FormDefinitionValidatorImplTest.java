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
package ru.d_shap.formmodel.definition.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.formmodel.BaseFormModelTest;
import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.definition.FormDefinitionValidationException;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.CardinalityDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitionKey;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinitionImpl;
import ru.d_shap.formmodel.definition.model.SingleElementDefinition;

/**
 * Tests for {@link FormDefinitionValidatorImpl}.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionValidatorImplTest extends BaseFormModelTest {

    /**
     * Test class constructor.
     */
    public FormDefinitionValidatorImplTest() {
        super();
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void isEmptyStringTest() {
        Assertions.assertThat(createValidator().isEmptyString(null)).isTrue();
        Assertions.assertThat(createValidator().isEmptyString("")).isTrue();

        Assertions.assertThat(createValidator().isEmptyString(" ")).isFalse();
        Assertions.assertThat(createValidator().isEmptyString("\t")).isFalse();
        Assertions.assertThat(createValidator().isEmptyString("  \n\t   \r\n ")).isFalse();

        Assertions.assertThat(createValidator().isEmptyString("a")).isFalse();
        Assertions.assertThat(createValidator().isEmptyString("X")).isFalse();
        Assertions.assertThat(createValidator().isEmptyString(" X")).isFalse();
        Assertions.assertThat(createValidator().isEmptyString("X ")).isFalse();
        Assertions.assertThat(createValidator().isEmptyString(" X ")).isFalse();
        Assertions.assertThat(createValidator().isEmptyString("  \t \r\n X ")).isFalse();
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void isBlankStringTest() {
        Assertions.assertThat(createValidator().isBlankString(null)).isTrue();
        Assertions.assertThat(createValidator().isBlankString("")).isTrue();

        Assertions.assertThat(createValidator().isBlankString(" ")).isTrue();
        Assertions.assertThat(createValidator().isBlankString("\t")).isTrue();
        Assertions.assertThat(createValidator().isBlankString("  \n\t   \r\n ")).isTrue();

        Assertions.assertThat(createValidator().isBlankString("a")).isFalse();
        Assertions.assertThat(createValidator().isBlankString("X")).isFalse();
        Assertions.assertThat(createValidator().isBlankString(" X")).isFalse();
        Assertions.assertThat(createValidator().isBlankString("X ")).isFalse();
        Assertions.assertThat(createValidator().isBlankString(" X ")).isFalse();
        Assertions.assertThat(createValidator().isBlankString("  \t \r\n X ")).isFalse();
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void isStringHasValidCharactersTest() {
        Assertions.assertThat(createValidator().isStringHasValidCharacters("aVal")).isTrue();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("AVal")).isTrue();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("zVal")).isTrue();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("ZVal")).isTrue();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("_Val")).isTrue();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("a_Val")).isTrue();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("a-Val")).isTrue();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("a1234567890Val")).isTrue();

        Assertions.assertThat(createValidator().isStringHasValidCharacters(" Val")).isFalse();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("-Val")).isFalse();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("0Val")).isFalse();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("йVal")).isFalse();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("a Val")).isFalse();
        Assertions.assertThat(createValidator().isStringHasValidCharacters("aйVal")).isFalse();
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormDefinitionGroupTest() {
        createValidator().validateFormDefinition(new FormDefinition(null, "id", createNodeDefinitions(), createOtherAttributes(), "source"));
        createValidator().validateFormDefinition(new FormDefinition("", "id", createNodeDefinitions(), createOtherAttributes(), "source"));
        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"));

        try {
            createValidator().validateFormDefinition(new FormDefinition(" ", "id", createNodeDefinitions(), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Group is not valid:  ], {source}form[@ :id]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("-group", "id", createNodeDefinitions(), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Group is not valid: -group], {source}form[@-group:id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormDefinitionIdTest() {
        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"));

        try {
            createValidator().validateFormDefinition(new FormDefinition("group", null, createNodeDefinitions(), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "", createNodeDefinitions(), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", " ", createNodeDefinitions(), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid:  ], {source}form[@group: ]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "-id", createNodeDefinitions(), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid: -id], {source}form[@group:-id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormDefinitionSourceTest() {
        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "source"));

        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), null));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Source is empty], {}form[@group:id]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), ""));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Source is empty], {}form[@group:id]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), " "));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Source is empty], { }form[@group:id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormDefinitionChildElementsTest() {
        ElementDefinition elementDefinition1 = new ElementDefinition("id1", "lookup1", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        ElementDefinition elementDefinition2 = new ElementDefinition("id2", "lookup2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());

        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1, elementDefinition2), createOtherAttributes(), "source"));
        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1, singleElementDefinition), createOtherAttributes(), "source"));

        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition1, elementDefinition1), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id1], {source}form[@group:id]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition2, elementDefinition2), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], {source}form[@group:id]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(elementDefinition2, singleElementDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], {source}form[@group:id]");
        }

        try {
            ElementDefinition invalidDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:id]/element[@id]/element[@]");
        }
        try {
            ElementDefinition invalidDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            SingleElementDefinition validDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], {source}form[@group:id]/singleElement[@id]/element[@id]");
        }
        try {
            ElementDefinition invalidDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setElementDefinition(invalidDefinition);
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:id]/repr/element[@]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormDefinitionChildSingleElementsTest() {
        SingleElementDefinition singleElementDefinition1 = new SingleElementDefinition("id1", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        SingleElementDefinition singleElementDefinition2 = new SingleElementDefinition("id2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        ElementDefinition elementDefinition = new ElementDefinition("id2", "lookup2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());

        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), createOtherAttributes(), "source"));
        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1, elementDefinition), createOtherAttributes(), "source"));

        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition1, singleElementDefinition1), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id1], {source}form[@group:id]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition2, singleElementDefinition2), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], {source}form[@group:id]");
        }
        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(singleElementDefinition2, elementDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], {source}form[@group:id]");
        }

        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:id]/element[@id]/singleElement[@]");
        }
        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            SingleElementDefinition validDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], {source}form[@group:id]/singleElement[@id]/singleElement[@id]");
        }
        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setSingleElementDefinition(invalidDefinition);
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:id]/repr/singleElement[@]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormDefinitionChildFormReferencesTest() {
        FormReferenceDefinition formReferenceDefinition1 = new FormReferenceDefinition(null, "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition2 = new FormReferenceDefinition("", "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition3 = new FormReferenceDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition4 = new FormReferenceDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition5 = new FormReferenceDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes());

        createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition3), createOtherAttributes(), "source"));
        createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition4), createOtherAttributes(), "source"));
        createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition2, formReferenceDefinition3), createOtherAttributes(), "source"));
        createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition2, formReferenceDefinition4), createOtherAttributes(), "source"));
        createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition3, formReferenceDefinition4), createOtherAttributes(), "source"));
        createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition3, formReferenceDefinition4), createOtherAttributes(), "source"));
        createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition2, formReferenceDefinition3, formReferenceDefinition4), createOtherAttributes(), "source"));

        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @:id1], {source}form[@group:id]");
        }
        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2, formReferenceDefinition3), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @:id1], {source}form[@group:id]");
        }
        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2, formReferenceDefinition4), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @:id1], {source}form[@group:id]");
        }
        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(formReferenceDefinition4, formReferenceDefinition5), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @group:id2], {source}form[@group:id]");
        }

        try {
            FormReferenceDefinition invalidDefinition = new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:id]/element[@id]/formReference[@group:]");
        }
        try {
            FormReferenceDefinition invalidDefinition = new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setFormReferenceDefinition(invalidDefinition);
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], {source}form[@group:id]/repr/formReference[@group:]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormDefinitionChildOtherNodesTest() {
        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("repr1", true);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("repr2", true);

        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createOtherAttributes(), "source"));

        try {
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, new OtherNodeDefinitionImpl("invalid", false)), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, {source}form[@group:id]/invalid");
        }

        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, {source}form[@group:id]/element[@id]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            SingleElementDefinition validDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, {source}form[@group:id]/singleElement[@id]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            FormReferenceDefinition validDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator("group", "id").validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, {source}form[@group:id]/formReference[@group:id]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setOtherNodeDefinition(invalidDefinition);
            createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes(), "source"));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, {source}form[@group:id]/repr/invalid");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateAttributeDefinitionIdTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/attribute[@]");
        }
        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/attribute[@]");
        }
        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid:  ], parent/attribute[@ ]");
        }
        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("-id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid: -id], parent/attribute[@-id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateAttributeDefinitionLookupTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Lookup is empty], parent/attribute[@id]");
        }
        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Lookup is empty], parent/attribute[@id]");
        }
        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", " ", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Lookup is empty], parent/attribute[@id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateAttributeDefinitionCardinalityDefinitionTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", null, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is empty], parent/attribute[@id]");
        }
        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: required+], parent/attribute[@id]");
        }
        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.OPTIONAL_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: optional+], parent/attribute[@id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateAttributeDefinitionChildOtherNodesTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("repr1", true);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("repr2", true);

        createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(new OtherNodeDefinitionImpl("invalid", false)), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/attribute[@id]/invalid");
        }

        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setOtherNodeDefinition(invalidDefinition);
            createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/attribute[@id]/repr/invalid");
        }

    }

    private FormDefinitionValidatorImpl createValidator(final String... groupsAndIds) {
        Set<FormDefinitionKey> formDefinitionKeys = new HashSet<>();
        for (int i = 0; i < groupsAndIds.length; i += 2) {
            FormDefinitionKey formDefinitionKey = new FormDefinitionKey(groupsAndIds[i], groupsAndIds[i + 1]);
            formDefinitionKeys.add(formDefinitionKey);
        }
        List<OtherNodeDefinitionValidator> otherNodeDefinitionValidators = ServiceFinder.find(OtherNodeDefinitionValidator.class);
        return new FormDefinitionValidatorImpl(formDefinitionKeys, otherNodeDefinitionValidators);
    }

}
