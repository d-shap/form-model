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
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public final class FormDefinitionsLoader {

    private FormDefinitionsLoader() {
        super();
    }

    public static FormDefinitions load(final File file) throws ParserConfigurationException, SAXException, IOException {
        List<FormDefinition> formDefinitions = new ArrayList<>();
        if (file.isDirectory()) {
            processDirectory(file, formDefinitions);
        } else {
            processFile(file, formDefinitions);
        }
        Map<String, FormDefinition> formDefinitionsMap = new HashMap<>();
        for (FormDefinition formDefinition : formDefinitions) {
            formDefinitionsMap.put(formDefinition.getId(), formDefinition);
        }
        return new FormDefinitions(formDefinitionsMap);
    }

    private static void processDirectory(final File file, final List<FormDefinition> formDefinitions) throws ParserConfigurationException, SAXException, IOException {
        File[] childFiles = file.listFiles(new Filter());
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    processDirectory(childFile, formDefinitions);
                } else {
                    processFile(childFile, formDefinitions);
                }
            }
        }
    }

    private static void processFile(final File file, final List<FormDefinition> formDefinitions) throws ParserConfigurationException, SAXException, IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FormDefinition formDefinition = FormDefinitionLoader.load(fileInputStream);
            formDefinitions.add(formDefinition);
        }
    }

    private static final class Filter implements FilenameFilter {

        Filter() {
            super();
        }

        @Override
        public boolean accept(final File dir, final String name) {
            return name.toLowerCase().endsWith(".xml");
        }

    }

}
