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
package ru.d_shap.formmodel.binding.api;

import java.util.List;

import org.w3c.dom.Element;

/**
 * Binder for the form model definition elements.
 *
 * @param <S> generic type of the source.
 * @param <B> generic type of the binding data.
 *
 * @author Dmitry Shapovalov
 */
public interface Binder<S, B> {

    /**
     * Get the binding data for the specified lookup.
     *
     * @param source the source of the binding data.
     * @param lookup the specified lookup.
     *
     * @return the binding data.
     */
    List<B> getBindingData(S source, String lookup);

    /**
     * Get the binding data for the specified lookup.
     *
     * @param source  the source of the binding data.
     * @param element the parent binded element.
     * @param lookup  the specified lookup.
     *
     * @return the binding data.
     */
    List<B> getBindingData(S source, Element element, String lookup);

}
