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

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.NodePath;
import ru.d_shap.formmodel.definition.model.OtherNodeDefinition;

/**
 * Implementation of the {@link OtherNodeInstanceBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class OtherNodeCommentInstanceBuilderImpl implements OtherNodeInstanceBuilder {

    public static final String COPMATIBLE_BUILDER = "__COPMATIBLE_BUILDER__";

    /**
     * Create new object.
     */
    public OtherNodeCommentInstanceBuilderImpl() {
        super();
    }

    /**
     * Set system property to make this instance builder compatible with any binder.
     */
    public static void setCopmatibleBuilder() {
        System.setProperty(COPMATIBLE_BUILDER, "true");
    }

    /**
     * Clear system property to make this instance builder compatible with any binder.
     */
    public static void clearCopmatibleBuilder() {
        System.getProperties().remove(COPMATIBLE_BUILDER);
    }

    @Override
    public boolean isCompatible(final Class<? extends FormInstanceBinder> clazz) {
        return "true".equalsIgnoreCase(System.getProperty(COPMATIBLE_BUILDER));
    }

    @Override
    public void buildOtherNodeInstance(final BindingSource bindingSource, final Document document, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final OtherNodeDefinition otherNodeDefinition, final FormInstanceBuilder formInstanceBuilder, final NodePath nodePath) {
        Comment comment = document.createComment("COMMENT TEXT!");
        parentElement.appendChild(comment);
    }

}
