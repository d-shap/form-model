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

import ru.d_shap.formmodel.definition.ElementDefinition;

import java.util.ArrayList;
import java.util.List;

public final class Element<T extends BindingObject> {

    private final ElementDefinition _elementDefinition;

    private final T _bindingObject;

    private final List<Element<T>> _childElements;

    Element(final ElementDefinition elementDefinition, final T bindingObject) {
        super();
        _elementDefinition = elementDefinition;
        _bindingObject = bindingObject;
        _childElements = new ArrayList<>();
    }

    public ElementDefinition getElementDefinition() {
        return _elementDefinition;
    }

    public T getBindingObject() {
        return _bindingObject;
    }

    public List<Element<T>> getChildElements() {
        return _childElements;
    }

}
