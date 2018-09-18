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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;

import ru.d_shap.formmodel.InputSourceReadException;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;

/**
 * Loader to load the form definitions from the file system, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public final class XmlFormDefinitionsFileSystemLoader extends XmlFormDefinitionsLoader {

    private static final String DEFAULT_EXTENSION = "xml";

    /**
     * Create new object.
     *
     * @param formDefinitions container for all form definitions.
     */
    public XmlFormDefinitionsFileSystemLoader(final FormDefinitions formDefinitions) {
        super(formDefinitions);
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
        int fileRootPathLength = file.getAbsolutePath().length();
        List<FormDefinition> formDefinitions = new ArrayList<>();
        if (file.isDirectory()) {
            processDirectory(file, fileRootPathLength, fileFilter, formDefinitions);
        } else {
            processFile(file, fileRootPathLength, formDefinitions);
        }
        addFormDefinitions(formDefinitions);
    }

    private void processDirectory(final File file, final int fileRootPathLength, final FileFilter fileFilter, final List<FormDefinition> formDefinitions) {
        File[] childFiles = file.listFiles(fileFilter);
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    processDirectory(childFile, fileRootPathLength, fileFilter, formDefinitions);
                } else {
                    processFile(childFile, fileRootPathLength, formDefinitions);
                }
            }
        }
    }

    private void processFile(final File file, final int fileRootPathLength, final List<FormDefinition> formDefinitions) {
        try {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                InputSource inputSource = new InputSource(inputStream);
                FormDefinition formDefinition = loadFormDefinition(inputSource, file.getAbsolutePath().substring(fileRootPathLength));
                formDefinitions.add(formDefinition);
            }
        } catch (IOException ex) {
            throw new InputSourceReadException(ex);
        }
    }

    /**
     * Default file filter.
     *
     * @author Dmitry Shapovalov
     */
    static final class DefaultFileFilter implements FileFilter {

        DefaultFileFilter() {
            super();
        }

        @Override
        public boolean accept(final File file) {
            if (file.isDirectory()) {
                return true;
            }
            String fileName = file.getName();
            int idx = fileName.lastIndexOf('.');
            if (idx < 0) {
                return false;
            }
            String extension = fileName.substring(idx + 1);
            return DEFAULT_EXTENSION.equalsIgnoreCase(extension);
        }

    }

}
