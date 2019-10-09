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
package ru.d_shap.formmodel.definition.loader;

import java.util.List;

import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

/**
 * Base loader for the form definitions.
 *
 * @author Dmitry Shapovalov
 */
public abstract class FormDefinitionsLoader {

    /**
     * Create new object.
     */
    protected FormDefinitionsLoader() {
        super();
    }

    /**
     * Load the form definitions.
     *
     * @return the loaded form definitions.
     */
    public abstract List<FormDefinition> load();

    /**
     * Load the form definitions and add them to the specified container for all form definitions.
     *
     * @param formDefinitions the specified container for all form definitions.
     */
    public final void load(final FormDefinitions formDefinitions) {
        List<FormDefinition> loadedFormDefinition = load();
        formDefinitions.addFormDefinitions(loadedFormDefinition);
    }

}
