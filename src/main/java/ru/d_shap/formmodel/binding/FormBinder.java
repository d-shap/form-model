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
package ru.d_shap.formmodel.binding;

import java.util.List;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ru.d_shap.formmodel.ServiceFinder;
import ru.d_shap.formmodel.XmlDocumentBuilder;
import ru.d_shap.formmodel.XmlDocumentValidator;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.FormDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinitions;
import ru.d_shap.formmodel.document.DocumentProcessor;

/**
 * The form binder.
 *
 * @author Dmitry Shapovalov
 */
public final class FormBinder {

    private final FormDefinitions _formDefinitions;

    private final FormInstanceBuilderImpl _formInstanceBuilder;

    /**
     * Create new object.
     *
     * @param formDefinitions    container for all form definitions.
     * @param formInstanceBinder form instance binder to bind form definition with the binding source.
     */
    public FormBinder(final FormDefinitions formDefinitions, final FormInstanceBinder formInstanceBinder) {
        super();
        _formDefinitions = formDefinitions.copyOf();
        List<OtherNodeInstanceBuilder> otherNodeInstanceBuilders = ServiceFinder.find(OtherNodeInstanceBuilder.class);
        _formInstanceBuilder = new FormInstanceBuilderImpl(_formDefinitions, formInstanceBinder, otherNodeInstanceBuilders);
    }

    /**
     * Bind the specified form definition with the specified binding source.
     *
     * @param bindingSource the specified binding source.
     * @param id            the specified form's ID.
     *
     * @return the binded form instance.
     */
    public Document bind(final BindingSource bindingSource, final String id) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(id);
        return bind(bindingSource, formDefinition);
    }

    /**
     * Bind the specified form definition with the specified binding source.
     *
     * @param bindingSource the specified binding source.
     * @param group         the specified form's group.
     * @param id            the specified form's ID.
     *
     * @return the binding result.
     */
    public Document bind(final BindingSource bindingSource, final String group, final String id) {
        FormDefinition formDefinition = _formDefinitions.getFormDefinition(group, id);
        return bind(bindingSource, formDefinition);
    }

    /**
     * Bind the specified form definition with the specified binding source and process the result document.
     *
     * @param bindingSource     the specified binding source.
     * @param id                the specified form's ID.
     * @param documentProcessor the document processor.
     * @param <T>               the generic type of the result of the document processing.
     *
     * @return the result of the document processing.
     */
    public <T> T bind(final BindingSource bindingSource, final String id, final DocumentProcessor<T> documentProcessor) {
        Document document = bind(bindingSource, id);
        return documentProcessor.process(document);
    }

    /**
     * Bind the specified form definition with the specified binding source and process the result document.
     *
     * @param bindingSource     the specified binding source.
     * @param group             the specified form's group.
     * @param id                the specified form's ID.
     * @param documentProcessor the document processor.
     * @param <T>               the generic type of the result of the document processing.
     *
     * @return the result of the document processing.
     */
    public <T> T bind(final BindingSource bindingSource, final String group, final String id, final DocumentProcessor<T> documentProcessor) {
        Document document = bind(bindingSource, group, id);
        return documentProcessor.process(document);
    }

    private Document bind(final BindingSource bindingSource, final FormDefinition formDefinition) {
        XmlDocumentBuilder xmlDocumentBuilder = XmlDocumentBuilder.getDocumentBuilder();
        Document newDocument = xmlDocumentBuilder.newDocument();
        Document bindedDocument = null;
        try {
            _formInstanceBuilder.preBind(bindingSource, formDefinition);
            _formInstanceBuilder.buildFormInstance(bindingSource, newDocument, formDefinition);
            bindedDocument = newDocument;
            validate(bindedDocument);
        } finally {
            _formInstanceBuilder.postBind(bindingSource, formDefinition, bindedDocument);
        }
        return bindedDocument;
    }

    private void validate(final Document document) {
        try {
            XmlDocumentValidator.getFormInstanceDocumentValidator().validate(document);
        } catch (SAXException ex) {
            throw new FormBindingException(ex);
        }
    }

}
