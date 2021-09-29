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
package ru.d_shap.fm.formmodel.definition.loader.xml;

import org.w3c.dom.Element;

import ru.d_shap.fm.formmodel.Messages;
import ru.d_shap.fm.formmodel.definition.model.OtherNodeDefinition;

/**
 * Default other node definition, XML implementation.
 *
 * @author Dmitry Shapovalov
 */
public final class DefaultOtherNodeXmlDefinition implements OtherNodeDefinition {

    private final Element _element;

    /**
     * Create new object.
     *
     * @param element the XML element.
     */
    public DefaultOtherNodeXmlDefinition(final Element element) {
        super();
        _element = element;
    }

    /**
     * Get the XML element.
     *
     * @return the XML element.
     */
    public Element getElement() {
        return _element;
    }

    @Override
    public String toString() {
        return Messages.Representation.getNodeRepresentation(_element);
    }

}
