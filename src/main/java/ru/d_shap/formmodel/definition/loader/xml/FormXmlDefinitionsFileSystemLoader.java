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
        this(file, (FileFilter) null);
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
        if (fileFilter == null) {
            _fileFilter = new DefaultFileFilter();
        } else {
            _fileFilter = fileFilter;
        }
    }

    /**
     * Create new object.
     *
     * @param file                           the source file or directory.
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     */
    public FormXmlDefinitionsFileSystemLoader(final File file, final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator) {
        this(file, null, xmlDocumentBuilderConfigurator);
    }

    /**
     * Create new object.
     *
     * @param file                           the source file or directory.
     * @param fileFilter                     the file filter.
     * @param xmlDocumentBuilderConfigurator configurator for the XML document builder.
     */
    public FormXmlDefinitionsFileSystemLoader(final File file, final FileFilter fileFilter, final XmlDocumentBuilderConfigurator xmlDocumentBuilderConfigurator) {
        super(xmlDocumentBuilderConfigurator);
        _file = file;
        if (fileFilter == null) {
            _fileFilter = new DefaultFileFilter();
        } else {
            _fileFilter = fileFilter;
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
            try (FileInputStream inputStream = new FileInputStream(file)) {
                InputSource inputSource = new InputSource(inputStream);
                FormDefinition formDefinition = getFormDefinition(inputSource, file.getAbsolutePath().substring(fileRootPathLength));
                if (formDefinition != null) {
                    formDefinitions.add(formDefinition);
                }
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
