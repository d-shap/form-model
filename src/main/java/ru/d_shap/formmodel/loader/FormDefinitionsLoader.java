package ru.d_shap.formmodel.loader;

import org.xml.sax.SAXException;
import ru.d_shap.formmodel.definition.FormDefinition;
import ru.d_shap.formmodel.definition.FormDefinitions;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public final class FormDefinitionsLoader {

    private FormDefinitionsLoader() {
        super();
    }

    public static FormDefinitions load(final File file) throws ParserConfigurationException, SAXException, IOException {
        FormDefinitions formDefinitions = new FormDefinitions();
        if (file.isDirectory()) {
            processDirectory(file, formDefinitions);
        } else {
            processFile(file, formDefinitions);
        }
        return formDefinitions;
    }

    private static void processDirectory(final File file, FormDefinitions formDefinitions) throws ParserConfigurationException, SAXException, IOException {
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

    private static void processFile(final File file, FormDefinitions formDefinitions) throws ParserConfigurationException, SAXException, IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FormDefinition formDefinition = FormDefinitionLoader.load(fileInputStream);
            formDefinitions.getFormDefinitions().put(formDefinition.getId(), formDefinition);
        }
    }

    private static final class Filter implements FilenameFilter {

        Filter() {
            super();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".xml");
        }

    }

}
