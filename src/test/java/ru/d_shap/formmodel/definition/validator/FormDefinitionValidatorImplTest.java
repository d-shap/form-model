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
        createValidator().validateFormDefinition(new FormDefinition("group", "id", createNodeDefinitions(), createOtherAttributes(), "-source"));

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
        createValidator().validateAttributeDefinition(parentNodeDefinition, new AttributeDefinition("id", "-lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));

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

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionIdTest() {
        SingleElementDefinition parentNodeDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        OtherNodeDefinition parentNodeDefinition2 = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(null, "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
        createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition("", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
        createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));

        try {
            createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(" ", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty:  ], singleElement[@id]/element[@ ]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition("id", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], singleElement[@id]/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition("-id", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: -id], singleElement[@id]/element[@-id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition(" ", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid:  ], parent/element[@ ]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("-id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid: -id], parent/element[@-id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionLookupTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "-lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Lookup is empty], parent/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Lookup is empty], parent/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", " ", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Lookup is empty], parent/element[@id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionCardinalityDefinitionTest() {
        SingleElementDefinition parentNodeDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        OtherNodeDefinition parentNodeDefinition2 = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(null, "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
        createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(null, "lookup", CardinalityDefinition.OPTIONAL_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
        createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
        createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
        createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("id", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
        createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("id", "lookup", CardinalityDefinition.OPTIONAL_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
        createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("id", "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));

        try {
            createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(null, "lookup", null, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is empty], singleElement[@id]/element[@]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: required], singleElement[@id]/element[@]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: required+], singleElement[@id]/element[@]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition1, new ElementDefinition(null, "lookup", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: prohibited], singleElement[@id]/element[@]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition2, new ElementDefinition("id", "lookup", null, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is empty], parent/element[@id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionChildAttributesTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        AttributeDefinition attributeDefinition1 = new AttributeDefinition("id1", "lookup1", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        AttributeDefinition attributeDefinition2 = new AttributeDefinition("id2", "lookup2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());

        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition1, attributeDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition1, attributeDefinition1), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id1], parent/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(attributeDefinition2, attributeDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], parent/element[@id]");
        }

        try {
            AttributeDefinition invalidDefinition = new AttributeDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/element[@id]/attribute[@]");
        }
        try {
            AttributeDefinition invalidDefinition = new AttributeDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setAttributeDefinition(invalidDefinition);
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/repr/attribute[@]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionChildElementsTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        ElementDefinition elementDefinition1 = new ElementDefinition("id1", "lookup1", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        ElementDefinition elementDefinition2 = new ElementDefinition("id2", "lookup2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        SingleElementDefinition singleElementDefinition = new SingleElementDefinition("id2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());

        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, elementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, singleElementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, elementDefinition1), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id1], parent/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition2, elementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], parent/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition2, singleElementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], parent/element[@id]");
        }

        try {
            ElementDefinition invalidDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/element[@id]/element[@]");
        }
        try {
            ElementDefinition invalidDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            SingleElementDefinition validDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/element[@id]/singleElement[@id]/element[@id]");
        }
        try {
            ElementDefinition invalidDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setElementDefinition(invalidDefinition);
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/repr/element[@]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionChildSingleElementsTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        SingleElementDefinition singleElementDefinition1 = new SingleElementDefinition("id1", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        SingleElementDefinition singleElementDefinition2 = new SingleElementDefinition("id2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        ElementDefinition elementDefinition = new ElementDefinition("id2", "lookup2", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());

        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, elementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition1), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id1], parent/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition2, singleElementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], parent/element[@id]");
        }
        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition2, elementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not unique: id2], parent/element[@id]");
        }

        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/element[@id]/singleElement[@]");
        }
        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            SingleElementDefinition validDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/element[@id]/singleElement[@id]/singleElement[@id]");
        }
        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setSingleElementDefinition(invalidDefinition);
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/repr/singleElement[@]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionChildFormReferencesTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        FormReferenceDefinition formReferenceDefinition1 = new FormReferenceDefinition(null, "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition2 = new FormReferenceDefinition("", "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition3 = new FormReferenceDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition4 = new FormReferenceDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition5 = new FormReferenceDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes());

        createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition3), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition4), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition2, formReferenceDefinition3), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition2, formReferenceDefinition4), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition3, formReferenceDefinition4), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition3, formReferenceDefinition4), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition2, formReferenceDefinition3, formReferenceDefinition4), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @:id1], parent/element[@id]");
        }
        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2, formReferenceDefinition3), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @:id1], parent/element[@id]");
        }
        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition1, formReferenceDefinition2, formReferenceDefinition4), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @:id1], parent/element[@id]");
        }
        try {
            createValidator("", "id1", "group", "id1", "group", "id2").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(formReferenceDefinition4, formReferenceDefinition5), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference is not unique: @group:id2], parent/element[@id]");
        }

        try {
            FormReferenceDefinition invalidDefinition = new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/element[@id]/formReference[@group:]");
        }
        try {
            FormReferenceDefinition invalidDefinition = new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setFormReferenceDefinition(invalidDefinition);
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/element[@id]/repr/formReference[@group:]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateElementDefinitionChildOtherNodesTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("repr1", true);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("repr2", true);

        createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, new OtherNodeDefinitionImpl("invalid", false)), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/element[@id]/invalid");
        }

        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            AttributeDefinition validDefinition = new AttributeDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/element[@id]/attribute[@id]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            ElementDefinition validDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/element[@id]/element[@id]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            SingleElementDefinition validDefinition = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/element[@id]/singleElement[@id]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            FormReferenceDefinition validDefinition = new FormReferenceDefinition("group", "id", createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator("group", "id").validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/element[@id]/formReference[@group:id]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setOtherNodeDefinition(invalidDefinition);
            createValidator().validateElementDefinition(parentNodeDefinition, new ElementDefinition("id", "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/element[@id]/repr/invalid");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateSingleElementDefinitionIdTest() {
        SingleElementDefinition parentNodeDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        OtherNodeDefinition parentNodeDefinition2 = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
        createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition("", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
        createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));

        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(" ", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty:  ], singleElement[@id]/singleElement[@ ]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], singleElement[@id]/singleElement[@id]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition("-id", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: -id], singleElement[@id]/singleElement[@-id]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/singleElement[@]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/singleElement[@]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition(" ", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid:  ], parent/singleElement[@ ]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("-id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid: -id], parent/singleElement[@-id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateSingleElementDefinitionCardinalityDefinitionTest() {
        SingleElementDefinition parentNodeDefinition1 = new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
        OtherNodeDefinition parentNodeDefinition2 = new OtherNodeDefinitionImpl("parent", true);

        createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
        createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
        createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
        createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("id", CardinalityDefinition.PROHIBITED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));

        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(null, null, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is empty], singleElement[@id]/singleElement[@]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: required], singleElement[@id]/singleElement[@]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(null, CardinalityDefinition.REQUIRED_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: required+], singleElement[@id]/singleElement[@]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: optional+], singleElement[@id]/singleElement[@]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition1, new SingleElementDefinition(null, CardinalityDefinition.PROHIBITED, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition1));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: prohibited], singleElement[@id]/singleElement[@]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("id", null, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is empty], parent/singleElement[@id]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: required+], parent/singleElement[@id]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition2, new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL_MULTIPLE, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition2));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Cardinality is not valid: optional+], parent/singleElement[@id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateSingleElementDefinitionChildElementsTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        ElementDefinition elementDefinition1 = new ElementDefinition(null, "lookup1", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());
        ElementDefinition elementDefinition2 = new ElementDefinition(null, "lookup2", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());
        SingleElementDefinition singleElementDefinition = new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());

        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, elementDefinition1), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, elementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition2, elementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition1, singleElementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(elementDefinition2, singleElementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(new ElementDefinition("id", "lookup1", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes())), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/singleElement[@id]/element[@id]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes())), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/singleElement[@id]/singleElement[@id]");
        }

        try {
            ElementDefinition invalidDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/singleElement[@id]/element[@]/element[@]");
        }
        try {
            ElementDefinition invalidDefinition = new ElementDefinition("id", "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());
            SingleElementDefinition validDefinition = new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/singleElement[@id]/singleElement[@]/element[@id]");
        }
        try {
            ElementDefinition invalidDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setElementDefinition(invalidDefinition);
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/singleElement[@id]/repr/element[@]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateSingleElementDefinitionChildSingleElementsTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        SingleElementDefinition singleElementDefinition1 = new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());
        SingleElementDefinition singleElementDefinition2 = new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());
        ElementDefinition elementDefinition = new ElementDefinition(null, "lookup2", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());

        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition1), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, singleElementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition2, singleElementDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition1, elementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(singleElementDefinition2, elementDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes())), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/singleElement[@id]/singleElement[@id]");
        }
        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(new ElementDefinition("id", "lookup2", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes())), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/singleElement[@id]/element[@id]");
        }

        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            ElementDefinition validDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/singleElement[@id]/element[@]/singleElement[@]");
        }
        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition("id", CardinalityDefinition.OPTIONAL, createNodeDefinitions(), createOtherAttributes());
            SingleElementDefinition validDefinition = new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not empty: id], parent/singleElement[@id]/singleElement[@]/singleElement[@id]");
        }
        try {
            SingleElementDefinition invalidDefinition = new SingleElementDefinition(null, CardinalityDefinition.REQUIRED, createNodeDefinitions(), createOtherAttributes());
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setSingleElementDefinition(invalidDefinition);
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/singleElement[@id]/repr/singleElement[@]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateSingleElementDefinitionChildOtherNodesTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("repr1", true);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("repr2", true);

        createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(otherNodeDefinition1, new OtherNodeDefinitionImpl("invalid", false)), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/singleElement[@id]/invalid");
        }

        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            ElementDefinition validDefinition = new ElementDefinition(null, "lookup", CardinalityDefinition.OPTIONAL, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/singleElement[@id]/element[@]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            SingleElementDefinition validDefinition = new SingleElementDefinition(null, CardinalityDefinition.OPTIONAL, createNodeDefinitions(invalidDefinition), createOtherAttributes());
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/singleElement[@id]/singleElement[@]/invalid");
        }
        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setOtherNodeDefinition(invalidDefinition);
            createValidator().validateSingleElementDefinition(parentNodeDefinition, new SingleElementDefinition("id", CardinalityDefinition.REQUIRED, createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/singleElement[@id]/repr/invalid");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormReferenceDefinitionGroupTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);

        createValidator("", "id", "group", "id").validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition(null, "id", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id", "group", "id").validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("", "id", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
        createValidator("", "id", "group", "id").validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition(" ", "id", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Group is not valid:  ], parent/formReference[@ :id]");
        }
        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("-group", "id", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Group is not valid: -group], parent/formReference[@-group:id]");
        }

    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormReferenceDefinitionIdTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);

        createValidator("group", "id").validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", "id", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", null, createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/formReference[@group:]");
        }
        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", "", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is empty], parent/formReference[@group:]");
        }
        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", " ", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid:  ], parent/formReference[@group: ]");
        }
        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", "-id", createNodeDefinitions(), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[ID is not valid: -id], parent/formReference[@group:-id]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormReferenceDefinitionResolvedTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        FormReferenceDefinition formReferenceDefinition1 = new FormReferenceDefinition("", "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition2 = new FormReferenceDefinition("group", "id1", createNodeDefinitions(), createOtherAttributes());
        FormReferenceDefinition formReferenceDefinition3 = new FormReferenceDefinition("group", "id2", createNodeDefinitions(), createOtherAttributes());

        createValidator("", "id1").validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition1, new NodePath(parentNodeDefinition));
        createValidator("group", "id1", "group", "id2").validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition2, new NodePath(parentNodeDefinition));
        createValidator("group", "id1", "group", "id2").validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition3, new NodePath(parentNodeDefinition));

        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition1, new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference can not be resolved: @:id1], parent/formReference[@:id1]");
        }
        try {
            createValidator("", "id").validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition1, new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference can not be resolved: @:id1], parent/formReference[@:id1]");
        }
        try {
            createValidator().validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition2, new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference can not be resolved: @group:id1], parent/formReference[@group:id1]");
        }
        try {
            createValidator("group", "id2").validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition2, new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference can not be resolved: @group:id1], parent/formReference[@group:id1]");
        }
        try {
            createValidator("group", "id1").validateFormReferenceDefinition(parentNodeDefinition, formReferenceDefinition3, new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("[Form reference can not be resolved: @group:id2], parent/formReference[@group:id2]");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateFormReferenceDefinitionChildOtherNodesTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("repr1", true);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("repr2", true);

        createValidator("group", "id").validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", "id", createNodeDefinitions(otherNodeDefinition1, otherNodeDefinition2), createOtherAttributes()), new NodePath(parentNodeDefinition));

        try {
            createValidator("group", "id").validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", "id", createNodeDefinitions(new OtherNodeDefinitionImpl("invalid", false)), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/formReference[@group:id]/invalid");
        }

        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setOtherNodeDefinition(invalidDefinition);
            createValidator("group", "id").validateFormReferenceDefinition(parentNodeDefinition, new FormReferenceDefinition("group", "id", createNodeDefinitions(validDefinition), createOtherAttributes()), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/formReference[@group:id]/repr/invalid");
        }
    }

    /**
     * {@link FormDefinitionValidatorImpl} class test.
     */
    @Test
    public void validateOtherNodeDefinitionChildOtherNodesTest() {
        OtherNodeDefinition parentNodeDefinition = new OtherNodeDefinitionImpl("parent", true);
        OtherNodeDefinition otherNodeDefinition1 = new OtherNodeDefinitionImpl("repr1", true);
        OtherNodeDefinition otherNodeDefinition2 = new OtherNodeDefinitionImpl("repr2", true);

        createValidator().validateOtherNodeDefinition(parentNodeDefinition, otherNodeDefinition1, new NodePath(parentNodeDefinition));
        createValidator().validateOtherNodeDefinition(parentNodeDefinition, otherNodeDefinition2, new NodePath(parentNodeDefinition));

        try {
            createValidator().validateOtherNodeDefinition(parentNodeDefinition, new OtherNodeDefinitionImpl("repr", false), new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/repr");
        }

        try {
            OtherNodeDefinition invalidDefinition = new OtherNodeDefinitionImpl("invalid", false);
            OtherNodeDefinitionImpl validDefinition = new OtherNodeDefinitionImpl("repr", true);
            validDefinition.setOtherNodeDefinition(invalidDefinition);
            createValidator().validateOtherNodeDefinition(parentNodeDefinition, validDefinition, new NodePath(parentNodeDefinition));
            Assertions.fail("FormDefinitionValidatorImpl test fail");
        } catch (FormDefinitionValidationException ex) {
            Assertions.assertThat(ex).hasMessage("Not valid!, parent/repr/invalid");
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
