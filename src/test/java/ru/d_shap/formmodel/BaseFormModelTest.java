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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ChoiceDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormReferenceDefinition;
import ru.d_shap.formmodel.definition.model.NodeDefinition;

/**
 * Base form model test class.
 *
 * @author Dmitry Shapovalov
 */
public class BaseFormModelTest {

    /**
     * Create new object.
     */
    protected BaseFormModelTest() {
        super();
    }

    /**
     * Create the node definition list.
     *
     * @param nodeDefinitions the node definitions.
     *
     * @return the node definition list.
     */
    protected final List<NodeDefinition> createNodeDefinitions(final NodeDefinition... nodeDefinitions) {
        return Arrays.asList(nodeDefinitions);
    }

    /**
     * Create the other attributes map.
     *
     * @param attributes the attribute names and values.
     *
     * @return the other attributes map.
     */
    protected final Map<String, String> createOtherAttributes(final String... attributes) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < attributes.length; i += 2) {
            result.put(attributes[i], attributes[i + 1]);
        }
        return result;
    }

    /**
     * Create the valid child element set.
     *
     * @param elements the valid child elements.
     *
     * @return the valid child element set.
     */
    protected final Set<String> createValidElements(final String... elements) {
        if (elements.length == 0) {
            Set<String> result = new HashSet<>();
            result.add(AttributeDefinition.ELEMENT_NAME);
            result.add(ElementDefinition.ELEMENT_NAME);
            result.add(ChoiceDefinition.ELEMENT_NAME);
            result.add(FormReferenceDefinition.ELEMENT_NAME);
            return result;
        } else {
            return new HashSet<>(Arrays.asList(elements));
        }
    }

    /**
     * Create the skip attributes set.
     *
     * @param attributes the attribute names.
     *
     * @return the skip attributes set.
     */
    protected final Set<String> createSkipAttributes(final String... attributes) {
        return new HashSet<>(Arrays.asList(attributes));
    }

}
