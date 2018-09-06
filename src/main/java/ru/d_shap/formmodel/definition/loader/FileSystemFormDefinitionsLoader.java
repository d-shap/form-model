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

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;

import ru.d_shap.formmodel.definition.FormDefinitionLoadException;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

/**
 * Loader to load the form definitions from the file system.
 *
 * @author Dmitry Shapovalov
 */
public final class FileSystemFormDefinitionsLoader extends FormDefinitionsLoader {

    /**
     * Create new object.
     *
     * @param formDefinitions             container for all form definitions.
     * @param otherNodeDefinitionBuilders builders for the other node definition.
     */
    public FileSystemFormDefinitionsLoader(final FormDefinitions formDefinitions, final List<OtherNodeDefinitionBuilder> otherNodeDefinitionBuilders) {
        super(formDefinitions, otherNodeDefinitionBuilders);
    }

    /**
     * Load the form definitions from the specified file or directory.
     *
     * @param file the specified file or directory.
     */
    public void load(final File file) {
        load(file, new DefaultFileFilter());
    }

    /**
     * Load the form definitions from the specified file or directory.
     *
     * @param file       the specified file or directory.
     * @param fileFilter the file filter.
     */
    public void load(final File file, final FileFilter fileFilter) {
        List<FormDefinition> formDefinitions = new ArrayList<>();
        if (file.isDirectory()) {
            processDirectory(file, fileFilter, formDefinitions);
        } else {
            processFile(file, formDefinitions);
        }
        addFormDefinitions(formDefinitions);
    }

    private void processDirectory(final File file, final FileFilter fileFilter, final List<FormDefinition> formDefinitions) {
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

    private void processFile(final File file, final List<FormDefinition> formDefinitions) {
        try {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                InputSource inputSource = new InputSource(inputStream);
                FormDefinition formDefinition = loadFormDefinition(inputSource, file.getAbsolutePath());
                formDefinitions.add(formDefinition);
            }
        } catch (IOException ex) {
            throw new FormDefinitionLoadException("Failed to load form definition", ex);
        }
    }

    /**
     * Default file filter
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
