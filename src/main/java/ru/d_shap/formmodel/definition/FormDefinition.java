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
import java.util.Collections;
import java.util.List;

/**
 * Form definition.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinition {

    static final String ELEMENT_NAME = "form";

    static final String ATTRIBUTE_ID = "id";

    static final String ATTRIBUTE_FRAME = "frame";

    private final String _id;

    private final String _frame;

    private final List<NodeDefinition> _nodeDefinitions;

    FormDefinition(final String id, final String frame, final List<NodeDefinition> nodeDefinitions) {
        super();
        _id = id;
        _frame = frame;
        List<NodeDefinition> nodeDefinitionsCopy = new ArrayList<>(nodeDefinitions);
        _nodeDefinitions = Collections.unmodifiableList(nodeDefinitionsCopy);
    }

    /**
     * Get the form ID.
     *
     * @return the form ID.
     */
    public String getId() {
        return _id;
    }

    /**
     * Get the form frame.
     *
     * @return the form frame.
     */
    public String getFrame() {
        return _frame;
    }

    /**
     * Get the form's node definitions.
     *
     * @return the form's node definitions.
     */
    public List<NodeDefinition> getNodeDefinitions() {
        return _nodeDefinitions;
    }

}
