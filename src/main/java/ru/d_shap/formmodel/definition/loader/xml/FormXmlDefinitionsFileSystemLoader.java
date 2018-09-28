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

import ru.d_shap.formmodel.InputSourceReadException;
import ru.d_shap.formmodel.XmlDocumentBuilderConfigurator;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * File system loader for the form definitions, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public final class FormXmlDefinitionsFileSystemLoader extends FormXmlDefinitionsLoader {

    private static final String DEFAULT_EXTENSION = "xml";

    private final File _file;

    private final FileFilter _fileFilter;

    /**
     * Create new object.
     *
     * @param file the source file or directory.
     */
    public FormXmlDefinitionsFileSystemLoader(final File file) {
        super();
        _file = file;
        _fileFilter = getFileFilter(null);
    }

    /**
     * Create new object.
     *
     * @param file       the source file or directory.
     * @param fileFilter the file filter.
     */
    public FormXmlDefinitionsFileSystemLoader(final File file, final FileFilter fileFilter) {
        super();
        _file = file;
        _fileFilter = getFileFilter(fileFilter);
    }

    /**
     * Create new object.
     *
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     * @param file                           the source file or directory.
     */
    public FormXmlDefinitionsFileSystemLoader(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator, final File file) {
        super(xmlDocumentBuilderConfigurator);
        _file = file;
        _fileFilter = getFileFilter(null);
    }

    /**
     * Create new object.
     *
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     * @param file                           the source file or directory.
     * @param fileFilter                     the file filter.
     */
    public FormXmlDefinitionsFileSystemLoader(final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator, final File file, final FileFilter fileFilter) {
        super(xmlDocumentBuilderConfigurator);
        _file = file;
        _fileFilter = getFileFilter(fileFilter);
    }

    /**
     * Create new object.
     *
     * @param formDefinitionsLoader another loader for the form definitions.
     * @param file                  the source file or directory.
     */
    public FormXmlDefinitionsFileSystemLoader(final FormXmlDefinitionsLoader formDefinitionsLoader, final File file) {
        super(formDefinitionsLoader);
        _file = file;
        _fileFilter = getFileFilter(null);
    }

    /**
     * Create new object.
     *
     * @param formDefinitionsLoader another loader for the form definitions.
     * @param file                  the source file or directory.
     * @param fileFilter            the file filter.
     */
    public FormXmlDefinitionsFileSystemLoader(final FormXmlDefinitionsLoader formDefinitionsLoader, final File file, final FileFilter fileFilter) {
        super(formDefinitionsLoader);
        _file = file;
        _fileFilter = getFileFilter(fileFilter);
    }

    private FileFilter getFileFilter(final FileFilter fileFilter) {
        if (fileFilter == null) {
            return new DefaultFileFilter();
        } else {
            return fileFilter;
        }
    }

    @Override
    public List<FormDefinition> load() {
        int fileRootPathLength = _file.getAbsolutePath().length();
        List<FormDefinition> formDefinitions = new ArrayList<>();
        if (_file.isDirectory()) {
            processDirectory(_file, fileRootPathLength, _fileFilter, formDefinitions);
        } else {
            processFile(_file, fileRootPathLength, formDefinitions);
        }
        return formDefinitions;
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
            String source = file.getAbsolutePath().substring(fileRootPathLength);
            try (FormXmlDefinitionsInputStreamLoader loader = new FormXmlDefinitionsInputStreamLoader(this, new FileInputStream(file), source)) {
                formDefinitions.addAll(loader.load());
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
