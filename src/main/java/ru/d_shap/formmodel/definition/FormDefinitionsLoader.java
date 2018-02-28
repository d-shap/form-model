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

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Form definitions loader.
 *
 * @author Dmitry Shapovalov
 */
public final class FormDefinitionsLoader {

    private FormDefinitionsLoader() {
        super();
    }

    /**
     * Load the form definitions from the specified source.
     *
     * @param file the root directory to search for the form model sources.
     * @return the loaded form definitions.
     */
    public static FormDefinitions load(final File file) {
        return load(file, new DefaultFileFilter());
    }

    /**
     * Load the form definitions from the specified source.
     *
     * @param file       the root directory to search for the form model sources.
     * @param fileFilter the model source filter.
     * @return the loaded form definitions.
     */
    public static FormDefinitions load(final File file, final FileFilter fileFilter) {
        List<FormDefinition> allFormDefinitions = new ArrayList<>();
        if (file.isDirectory()) {
            processDirectory(file, fileFilter, allFormDefinitions);
        } else {
            processFile(file, allFormDefinitions);
        }

        Map<String, FormDefinition> formDefinitionsMap = new HashMap<>();
        for (FormDefinition formDefinition : allFormDefinitions) {
            String formId = formDefinition.getId();
            if (formDefinitionsMap.containsKey(formId)) {
                throw new DuplicateFormDefinitionException(formId, formDefinition.getSource(), formDefinitionsMap.get(formId).getSource());
            }
            formDefinitionsMap.put(formId, formDefinition);
        }
        FormDefinitionValidator.validateFormReferences(formDefinitionsMap);

        FormDefinitions formDefinitions = new FormDefinitions(formDefinitionsMap);
        for (FormDefinition formDefinition : formDefinitions.getFormDefinitions()) {
            formDefinition.setFormDefinitions(formDefinitions);
        }
        return formDefinitions;
    }

    private static void processDirectory(final File file, final FileFilter fileFilter, final List<FormDefinition> formDefinitions) {
        File[] childFiles = file.listFiles(fileFilter);
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    processDirectory(childFile, fileFilter, formDefinitions);
                } else {
                    processFile(childFile, formDefinitions);
                }
            }
        }
    }

    private static void processFile(final File file, final List<FormDefinition> formDefinitions) {
        try {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                FormDefinition formDefinition = FormDefinitionLoader.load(fileInputStream, file);
                formDefinitions.add(formDefinition);
            }
        } catch (IOException ex) {
            throw new FormDefinitionLoadException(ex);
        }
    }

    /**
     * Default file filter.
     *
     * @author Dmitry Shapovalov
     */
    private static final class DefaultFileFilter implements FileFilter {

        DefaultFileFilter() {
            super();
        }

        @Override
        public boolean accept(final File file) {
            return file.isDirectory() || file.getName().endsWith(".xml");
        }

    }

}
